package com.test.servicemonitor.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.test.servicemonitor.check.CheckResult;
import com.test.servicemonitor.check.FailLevel;
import com.test.servicemonitor.check.LifeChecker;
import com.test.servicemonitor.check.ThrowableEncolsingCheckResult;
import com.test.servicemonitor.integration.FailedCheckProcessingGateway;
import com.test.servicemonitor.persistance.MonitorStatus;
import com.test.servicemonitor.persistance.MonitorStatusService;

public class MonitorTask implements Runnable {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String systemId;

	private LifeChecker lifeChecker;

	private String taskId;

	private boolean terminated;

	private FailedCheckProcessingGateway failedCheckProcessingGateway;

	private MonitorStatusService statusService;

	private MainScheduler mainScheduler;

	public MonitorTask(String systemId, LifeChecker lifeChecker, FailedCheckProcessingGateway failedCheckProcessingGateway,
			MonitorStatusService statusService, MainScheduler mainScheduler) {
		Assert.notNull(systemId, "systemId must not be null");
		Assert.notNull(lifeChecker, "lifeChecker must not be null");
		Assert.notNull(failedCheckProcessingGateway, "failedCheckProcessingGateway must not be null");
		Assert.notNull(statusService, "statusService must not be null");
		Assert.notNull(mainScheduler, "mainScheduler must not be null");
		this.systemId = systemId;
		this.lifeChecker = lifeChecker;
		this.taskId = systemId + "@Task:" + Integer.toHexString(hashCode());
		this.failedCheckProcessingGateway = failedCheckProcessingGateway;
		this.statusService = statusService;
		this.mainScheduler = mainScheduler;
	}

	@Override
	public void run() {
		if (terminated) {
			logger.info("[{}]: traminated flag is true, return immediately.", taskId);
			return;
		}
		CheckResult cr;
		try {
			cr = lifeChecker.check();
		} catch (Exception e) {
			cr = new ThrowableEncolsingCheckResult(e);// with level FATAL
		}
		MonitorStatus status = statusService.get(systemId);
		if (cr.isPassed()) {
			logger.info("[{}]: check passed, remote system is alive.", taskId);
			doWhenPassed(cr, status);
		} else {
			logger.info("[{}]: check not passed, pass check result to failure processing.", taskId);
			doWhenFailed(cr, status);
		}

	}

	private void doWhenPassed(CheckResult cr, MonitorStatus status) {
		logger.debug("[{}]: Updating status to [alive=true].", taskId);
		statusService.updateAlive(systemId, true);
	}

	private void doWhenFailed(CheckResult cr, MonitorStatus status) {
		logger.info("[{}]: The fail level is [{}], fail message is [{}].", taskId, cr.getFailLevel(), cr.getFailMessage());

		if (FailLevel.FATAL.equals(cr.getFailLevel())) {
			logger.info("[{}]: The fail leve is FATAL, stop this monitoring task.", taskId);
			mainScheduler.stop(systemId);
		}
		
		logger.debug("[{}]: Updating status to [alive=false].", taskId);
		statusService.updateAlive(systemId, false);
		
		logger.debug("[{}]: Passing check result to failed check processing flow.", taskId);
		failedCheckProcessingGateway.process(systemId, cr);
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public LifeChecker getLifeChecker() {
		return lifeChecker;
	}

	public void setLifeChecker(LifeChecker lifeChecker) {
		this.lifeChecker = lifeChecker;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	public void setFailedCheckProcessingGateway(FailedCheckProcessingGateway failedCheckProcessingGateway) {
		this.failedCheckProcessingGateway = failedCheckProcessingGateway;
	}

}
