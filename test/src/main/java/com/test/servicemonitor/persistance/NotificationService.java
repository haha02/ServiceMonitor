package com.test.servicemonitor.persistance;

import java.util.List;

public interface NotificationService {
	List<Notification> getAll();

	List<Notification> getBySystemId(String systemId);

	Notification get(String systemId, Notification.Types notifyType, String userGroup);

	void create(Notification entity);

	void delete(String systemId, Notification.Types notifyType, String userGroup);

	void update(Notification entity);
}
