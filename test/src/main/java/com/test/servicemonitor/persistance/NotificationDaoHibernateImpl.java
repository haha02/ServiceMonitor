package com.test.servicemonitor.persistance;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationDaoHibernateImpl extends AbstractGenericDaoHibernateImpl<Notification>
		implements NotificationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> getBySystemId(Notification entity) {
		Criteria c = getCurrentSession().createCriteria(Notification.class);
		addEqRestriction(c, "key.system_id", entity.getKey().getSystem_id());
		return c.list();
	}

	@Override
	protected Serializable acquireId(Notification entity) {
		assertEntityNotNull(entity);
		return entity.getKey();
	}

	@Override
	protected Class<Notification> getEntityClass() {
		return Notification.class;
	}

}
