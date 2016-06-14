package com.test.servicemonitor.persistance;

import java.util.List;

public interface NotificationDao extends GenericCrudDao<Notification> {

	List<Notification> getBySystemId(Notification entity);

}
