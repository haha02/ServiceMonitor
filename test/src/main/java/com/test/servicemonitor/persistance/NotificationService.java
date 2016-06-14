package com.test.servicemonitor.persistance;

import java.util.List;

public interface NotificationService {
	List<Notification> getAll();

	List<Notification> getBySystemId(Notification entity);

	Notification get(String systemId, String notifyType, String userGroup);

	void create(Notification entity);

	void delete(String systemId, String notifyType, String userGroup);

	void update(Notification entity);
}
