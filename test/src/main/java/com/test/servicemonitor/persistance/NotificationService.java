package com.test.servicemonitor.persistance;

import java.util.List;

/**
 * Service interface for interacting with the persistence layer of entity {@link Notification}
 *
 */
public interface NotificationService {

	/**
	 * Get all entity
	 * 
	 * @return the entities
	 */
	List<Notification> getAll();

	/**
	 * Get all notification of the given remote system
	 * 
	 * @param system_id
	 *            remote system ID
	 * @return the entities
	 */
	List<Notification> getBySystemId(String system_id);

	/**
	 * Get the entity of the given remote system from storage
	 * 
	 * @param system_id
	 *            remote system ID
	 * @param notify_type
	 *            notify type
	 * @param user_group
	 *            user group
	 * @return the entity
	 */
	Notification get(String system_id, Notification.Types notify_type, String user_group);

	/**
	 * Create the given entity in storage
	 * 
	 * @param entity
	 *            the entity
	 */
	void create(Notification entity);

	/**
	 * Delete the given entity of the given remote system from storage
	 * 
	 * @param system_id
	 *            remote system ID
	 * @param notify_type
	 *            notify type
	 * @param user_group
	 *            user group
	 */
	void delete(String system_id, Notification.Types notify_type, String user_group);

	/**
	 * Update the given entity in storage
	 * 
	 * @param entity
	 *            the entity
	 */
	void update(Notification entity);
}
