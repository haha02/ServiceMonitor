package com.test.servicemonitor.main;

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

	@PostConstruct
	public void init() {
		List<RemoteSystem> remoteSystems = remoteSystemService.getAll();
		List<MonitorStatus> monitorStatusList = monitorStatusService.getAll();
		for (RemoteSystem rs : remoteSystems) {
			boolean found = false;
			for (MonitorStatus ms : monitorStatusList) {
				if (ms.getSystem_id().equals(rs.getSystem_id())) {
					found = true;
					break;
				}
			}
			if (!found) {
				MonitorStatus ms = contructNewMonitorStatus(rs.getSystem_id());
				monitorStatusService.create(ms);
			}
		}
	}

	private MonitorStatus contructNewMonitorStatus(String system_id) {
		MonitorStatus ms = new MonitorStatus();
		ms.setSystem_id(system_id);
		ms.setAlive(false);
		ms.setMonitoring(false);
		return ms;
	}
}
