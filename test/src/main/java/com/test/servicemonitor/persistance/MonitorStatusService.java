package com.test.servicemonitor.persistance;

import java.util.List;

/**
 * Service interface for interacting with the persistence layer of entity {@link MonitorStatus}
 *
 */
public interface MonitorStatusService {
	/**
	 * Get the entity of the given remote system from storage
	 * 
	 * @param system_id
	 *            the remote system ID
	 * @return the entity
	 */
	MonitorStatus get(String system_id);

	/**
	 * Get all entity
	 * 
	 * @return the entities
	 */
	List<MonitorStatus> getAll();

	/**
	 * Create the given entity in storage
	 * 
	 * @param entity
	 *            the entity
	 */
	void create(MonitorStatus entity);

	/**
	 * Update the given entity in storage
	 * 
	 * @param entity
	 *            the entity
	 */
	void update(MonitorStatus entity);

	/**
	 * Update all the given entity in storage
	 * 
	 * @param entity
	 *            the entities
	 */
	void updateAll(List<MonitorStatus> entities);

	/**
	 * Delete the given entity of the given remote system from storage
	 * 
	 * @param system_id
	 *            the remote system ID
	 */
	void delete(String system_id);

	/**
	 * Update the monitoring flag of the given remote system.
	 * 
	 * @param system_id
	 *            the remote system ID
	 * @param monitoring
	 *            the monitoring flag
	 */
	void updateMonitoring(String system_id, boolean monitoring);

	/**
	 * Update the alive flag of the given remote system. Also update the last check time of the system by using the current system time.
	 * 
	 * @param system_id
	 *            the remote system ID
	 * @param monitoring
	 *            the alive flag
	 */
	void updateAlive(String system_id, boolean alive);
}
