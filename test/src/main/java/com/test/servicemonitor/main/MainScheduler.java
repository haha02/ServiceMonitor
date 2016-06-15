package com.test.servicemonitor.main;

import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.test.servicemonitor.check.CheckerFactory;
import com.test.servicemonitor.check.LifeChecker;
import com.test.servicemonitor.integration.FailedCheckProcessingGateway;
import com.test.servicemonitor.persistance.RemoteSystem;
import com.test.servicemonitor.persistance.RemoteSystemService;
import com.test.servicemonitor.util.Constants;

@Component
public class MainScheduler {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TaskScheduler taskScheduler;

	@Autowired
	private RemoteSystemService remoteSystemService;

	@Autowired
	private CheckerFactory checkerFactory;

	@Autowired
	private SystemConfig systemConfig;
	
	@Autowired
	private FailedCheckProcessingGateway failedCheckProcessingGateway;

	// key: systemId, value: current task & trigger
	private Map<String, TaskAndTrigger> monitoredSystems = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() {
		if (systemConfig.isMonitorOnStarup()) {
			logger.info("Monitor on startup is enabled, starting all enabled system monitor.");
			startAll();
			logger.info("All monitored system is {}", getMonitoredSystems());
		}
	}

	/**
	 * Start the monitor of the all system.
	 * 
	 */
	public void startAll() {
		if (monitoredSystems.size() != 0) {
			stopAll();
		}

		List<RemoteSystem> rsList = remoteSystemService.getAll();
		for (RemoteSystem rs : rsList) {
			if (!rs.getDisabled()) {
				start(rs);
			}
		}
	}

	/**
	 * Start the monitor of the specified system.
	 * 
	 * @param systemId
	 *            the system ID
	 * @return {@code false} if the system ID does not exist in storage
	 */
	public boolean start(String systemId) {
		RemoteSystem rs = remoteSystemService.get(systemId);
		if (rs != null) {// XXX throw exception when null instead?
			return start(rs);
		}
		return false;
	}

	private boolean start(RemoteSystem rs) {
		String systemId = rs.getSystem_id();
		String checkerType = rs.getChecker_type();
		if (!checkerFactory.isSupport(checkerType)) {
			// XXX throws exception?
			logger.warn("System [{}] is mapped to an unsupported checker type [{}], skip monitoring this system.",
					systemId, checkerType);
			return false;
		}

		Properties hints = getHintsProperties(rs);
		LifeChecker checker = checkerFactory.getChecker(systemId, checkerType, rs.getConnection_string(), hints);

		MonitorTask task = new MonitorTask(systemId, checker, failedCheckProcessingGateway);
		MonitorTrigger trigger = new MonitorTrigger(systemId, remoteSystemService);
		taskScheduler.schedule(task, trigger);
		monitoredSystems.put(systemId, new TaskAndTrigger(task, trigger));
		return true;
	}

	private Properties getHintsProperties(RemoteSystem rs) {
		String hints = rs.getHints();
		Properties p = new Properties();
		if (StringUtils.hasText(hints)) {
			hints = hints.replace(",", "\n");
			try (StringReader sr = new StringReader(hints);) {
				p.load(sr);
			} catch (Exception e) {
				throw new IllegalArgumentException("Failed to parse hints, the entity is [" + rs + "]", e);
			}
		}
		return p;
	}

	/**
	 * Packing {@link MonitorTask} with {@link MonitorTrigger}, for internal
	 * use.
	 */
	private class TaskAndTrigger {
		MonitorTask task;
		MonitorTrigger trigger;

		public TaskAndTrigger(MonitorTask task, MonitorTrigger trigger) {
			super();
			this.task = task;
			this.trigger = trigger;
		}
	}

	/**
	 * Restart the monitor of the specified system. If the checker type of given
	 * system is changed, it needs to be restart for the new checker type to
	 * taking effect.
	 * 
	 * @param systemId
	 *            the system ID
	 */
	public void restart(String systemId) {
		stop(systemId);
		start(systemId);
	}

	/**
	 * Stop all system monitoring.
	 * 
	 */
	public void stopAll() {
		// avoid using key set directly
		Set<String> monitoredSystems = getMonitoredSystems();

		for (String systemId : monitoredSystems) {
			stop(systemId);
		}
	}

	/**
	 * Stop the monitor of the specified system.
	 * 
	 * @param systemId
	 *            the system ID
	 * @return {@code false} if the system does not being monitored.
	 */
	public boolean stop(String systemId) {
		TaskAndTrigger tat = monitoredSystems.remove(systemId);
		if (tat != null) {
			tat.task.setTerminated(true);
			tat.trigger.setTraminated(true);
			return true;
		}
		return false;
	}

	/**
	 * Get all monitored system ID.
	 * 
	 * @param systemId
	 *            the system ID
	 * @return a set containing all monitored system ID
	 */
	public Set<String> getMonitoredSystems() {
		return new HashSet<>(monitoredSystems.keySet());
	}

	/**
	 * Whether the specified system is being monitored.
	 * 
	 * @param systemId
	 *            the system ID
	 * @return {@code true} if the given system is being monitored,
	 *         {@code false} otherwise.
	 */
	public boolean isMonitored(String systemId) {
		return monitoredSystems.containsKey(systemId);
	}
}
