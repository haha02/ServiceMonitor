package com.test.servicemonitor.persistance;

/**
 * DAO interface of entity {@link MonitorStatus}
 *
 */
public interface MonitorStatusDao extends GenericCrudDao<MonitorStatus> {

	void updateAlive(MonitorStatus entity);

	void updateMonitoring(MonitorStatus entity);

}
