package com.test.servicemonitor.persistance;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link MonitorStatusService} implementation
 *
 */
@Service
public class MonitorStatusServiceImpl implements MonitorStatusService {

	@Autowired
	private MonitorStatusDao dao;

	@Override
	public MonitorStatus get(String system_id) {
		MonitorStatus entity = new MonitorStatus();
		entity.setSystem_id(system_id);
		return dao.get(entity);
	}

	@Override
	public List<MonitorStatus> getAll() {
		return dao.getAll();
	}

	@Override
	public void create(MonitorStatus entity) {
		dao.save(entity);
	}

	@Override
	public void update(MonitorStatus entity) {
		dao.update(entity);
	}

	@Override
	public void delete(String system_id) {
		MonitorStatus entity = new MonitorStatus();
		entity.setSystem_id(system_id);
		dao.delete(entity);
	}

	@Override
	public void updateMonitoring(String system_id, boolean monitoring) {
		MonitorStatus entity = new MonitorStatus();
		entity.setSystem_id(system_id);
		entity.setMonitoring(monitoring);
		dao.updateMonitoring(entity);
	}

	@Override
	public void updateAlive(String system_id, boolean alive) {
		MonitorStatus entity = new MonitorStatus();
		entity.setSystem_id(system_id);
		entity.setAlive(alive);
		entity.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
		dao.updateAlive(entity);
	}

}
