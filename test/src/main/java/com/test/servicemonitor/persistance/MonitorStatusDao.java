package com.test.servicemonitor.persistance;

/**
 * DAO interface of entity {@link MonitorStatus}
 *
 */
public interface MonitorStatusDao extends GenericCrudDao<MonitorStatus> {
	/**
	 * Update only the alive flag and last check time of the entity.
	 * 
	 * @param entity
	 *            the entity
	 */
	void updateAlive(MonitorStatus entity);

	/**
	 * Update only the monitoring flag of the entity.
	 * 
	 * @param entity
	 *            the entity
	 */
	void updateMonitoring(MonitorStatus entity);

}
