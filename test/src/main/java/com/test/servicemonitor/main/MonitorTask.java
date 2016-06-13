package com.test.servicemonitor.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.test.servicemonitor.check.CheckResult;
import com.test.servicemonitor.check.LifeChecker;
import com.test.servicemonitor.check.ThrowableEncolsingCeckResult;
import com.test.servicemonitor.gateway.FailedCheckProcessingGateway;

public class MonitorTask implements Runnable {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String systemId;

	private LifeChecker lifeChecker;

	private String taskId;

	private boolean terminated;

	private FailedCheckProcessingGateway failedCheckProcessingGateway;

	public MonitorTask(String systemId, LifeChecker lifeChecker, FailedCheckProcessingGateway failedCheckProcessingGateway) {
		super();
		Assert.notNull(systemId, "systemId must not be null");
		Assert.notNull(lifeChecker, "lifeChecker must not be null");
		Assert.notNull(failedCheckProcessingGateway, "failedCheckProcessingGateway must not be null");
		this.systemId = systemId;
		this.lifeChecker = lifeChecker;
		this.taskId = systemId + "@Task" + Integer.toHexString(hashCode());
		this.failedCheckProcessingGateway = failedCheckProcessingGateway;
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
			cr = new ThrowableEncolsingCeckResult(e);// with level FATAL
		}

		if (cr.isPassed()) {
			logger.info("[{}]: check passed, remote system is alive.", taskId);
		} else {
			logger.info("[{}]: check not passed, pass check result to failure processing.", taskId);
			failedCheckProcessingGateway.process(systemId, cr);
		}
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
