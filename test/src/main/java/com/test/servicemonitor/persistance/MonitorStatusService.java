package com.test.servicemonitor.persistance;

import java.util.List;

public interface MonitorStatusService {

	MonitorStatus get(String system_id);

	List<MonitorStatus> getAll();

	void create(MonitorStatus entity);

	void update(MonitorStatus entity);

	void delete(String system_id);
	
	void updateMonitoring(String system_id, boolean monitoring);
	
	void updateAlive(String system_id, boolean alive);
}
