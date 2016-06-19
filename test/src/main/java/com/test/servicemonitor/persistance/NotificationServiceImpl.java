package com.test.servicemonitor.persistance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link NotificationService} implementation
 *
 */
@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationDao dao;

	@Override
	public List<Notification> getAll() {
		return dao.getAll();
	}

	@Override
	public Notification get(String systemId, Notification.Types notifyType, String userGroup) {
		Notification entity = getEntity(systemId, notifyType, userGroup);
		return dao.get(entity);
	}

	@Override
	public void create(Notification entity) {
		dao.save(entity);
	}

	@Override
	public void delete(String systemId, Notification.Types notifyType, String userGroup) {
		Notification entity = getEntity(systemId, notifyType, userGroup);
		dao.delete(entity);
	}

	@Override
	public void update(Notification entity) {
		dao.update(entity);
	}

	@Override
	public List<Notification> getBySystemId(String systemId) {
		Notification entity = getEntity(systemId, null, null);
		return dao.getBySystemId(entity);
	}

	private Notification getEntity(String systemId, Notification.Types notifyType, String userGroup) {
		Notification.PK key = new Notification.PK();
		key.setSystem_id(systemId);
		key.setNotify_type(notifyType);
		key.setUser_group(userGroup);

		Notification entity = new Notification();
		entity.setKey(key);
		return entity;
	}

}
