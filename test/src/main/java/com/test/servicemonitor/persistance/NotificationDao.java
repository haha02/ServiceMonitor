package com.test.servicemonitor.persistance;

import java.util.List;

public interface NotificationDao {

	Notification get(Notification entity);

	List<Notification> getAll();

	List<Notification> getBySystemId(Notification entity);

	void save(Notification entity);

	void update(Notification entity);

	void delete(Notification entity);

}
