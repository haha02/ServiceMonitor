package com.test.servicemonitor.persistance;

import java.util.List;

/**
 * DAO interface of entity {@link Notification}
 *
 */
public interface NotificationDao extends GenericCrudDao<Notification> {
	/**
	 * Get all notification of the given remote system, determined by the system_id of the given entity.
	 * 
	 * @param entity
	 *            the entity
	 * @return the notifications
	 */
	List<Notification> getBySystemId(Notification entity);

}
