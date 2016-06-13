package com.test.servicemonitor.persistance;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationDaoHibernateImpl extends GenericDaoHibernateSupport implements NotificationDao {

	@Override
	public Notification get(Notification entity) {
		assertEntityNotNull(entity);
		return (Notification) getCurrentSession().get(Notification.class, entity.getKey());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> getAll() {
		return getCurrentSession().createCriteria(Notification.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> getBySystemId(Notification entity) {
		Criteria c = getCurrentSession().createCriteria(Notification.class);
		addEqRestriction(c, "key.system_id", entity.getKey().getSystem_id());
		return c.list();
	}

	@Override
	public void save(Notification entity) {
		assertEntityNotNull(entity);
		getCurrentSession().save(entity);
	}

	@Override
	public void update(Notification entity) {
		assertEntityNotNull(entity);
		getCurrentSession().update(entity);
	}

	@Override
	public void delete(Notification entity) {
		assertEntityNotNull(entity);
		getCurrentSession().delete(entity);
	}

}
