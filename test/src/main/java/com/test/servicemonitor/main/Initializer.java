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

	@PostConstruct
	public void init() {
		initializeAllMonitorStatus();
		startMonitorIfNeeded();
	}

	private void initializeAllMonitorStatus() {
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

	private void startMonitorIfNeeded() {
		if (systemConfig.isMonitorOnStarup()) {
			mainScheduler.startAll();
		}
	}

	private MonitorStatus contructNewMonitorStatus(String system_id) {
		MonitorStatus ms = new MonitorStatus();
		ms.setSystem_id(system_id);
		ms.setMonitoring(false);
		return ms;
	}
}
