package com.test.servicemonitor.main;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.servicemonitor.persistance.MonitorStatus;
import com.test.servicemonitor.persistance.MonitorStatusService;
import com.test.servicemonitor.persistance.RemoteSystem;
import com.test.servicemonitor.persistance.RemoteSystemService;

/**
 * Initializing database and triggering system monitoring on system startup.
 *
 */
@Component
public class Initializer {

	@Autowired
	private RemoteSystemService remoteSystemService;

	@Autowired
	private MonitorStatusService monitorStatusService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private MainScheduler mainScheduler;

	/**
	 * Initializing method to be called after dependency injection
	 */
	@PostConstruct
	public void init() {
		insertDefaultData();
		initializeAllMonitorStatus();
		startMonitorIfNeeded();
	}

	private void insertDefaultData() {
		// TODO

	}

	/**
	 * Correct or add missing {@link MonitorStatus} entries.
	 * <p>
	 * Invalid {@link MonitorStatus} state may result from previous system crush or inappropriate shut down, something like that. That's where this method comes
	 * to rescue.
	 */
	protected void initializeAllMonitorStatus() {
		List<MonitorStatus> statusList = monitorStatusService.getAll();
		List<MonitorStatus> statusToUpdate = new ArrayList<>(statusList.size());
		for (RemoteSystem rs : remoteSystemService.getAll()) {
			MonitorStatus monitorStatus = null;
			for (MonitorStatus ms : statusList) {
				if (ms.getSystem_id().equals(rs.getSystem_id())) {
					monitorStatus = ms;
					break;
				}
			} // end for each monitor status

			if (monitorStatus == null) {
				monitorStatusService.create(contructNewMonitorStatus(rs.getSystem_id()));
				continue;
			}

			boolean needUpdate = false;
			Boolean monitoring = monitorStatus.getMonitoring();
			if (monitoring != null && monitoring) {
				monitorStatus.setMonitoring(false);
				needUpdate = true;
			}
			if (monitorStatus.getLast_check_time() == null) {
				needUpdate = monitorStatus.getAlive() != null;
				if (needUpdate)
					monitorStatus.setAlive(null);
			}
			if (needUpdate) {
				statusToUpdate.add(monitorStatus);
			}
		} // end for each remote system

		if (statusToUpdate.size() > 0) {
			monitorStatusService.updateAll(statusToUpdate);
		}
	}// end method

	private MonitorStatus contructNewMonitorStatus(String system_id) {
		MonitorStatus ms = new MonitorStatus();
		ms.setSystem_id(system_id);
		ms.setMonitoring(false);
		return ms;
	}

	/**
	 * Start monitor remote systems if the system is configured to do so.
	 */
	protected void startMonitorIfNeeded() {
		if (systemConfig.isMonitorOnStarup()) {
			mainScheduler.startAll();
		}
	}
}
