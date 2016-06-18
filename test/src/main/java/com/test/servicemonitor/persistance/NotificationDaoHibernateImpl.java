package com.test.servicemonitor.persistance;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class NotificationDaoHibernateImpl extends AbstractGenericDaoHibernateImpl<Notification>
		implements NotificationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> getBySystemId(Notification entity) {
		assertEntityNotNull(entity);
		Notification.PK key = entity.getKey();
		Assert.notNull(key, "entity key must not be null");
		Criteria c = getCurrentSession().createCriteria(Notification.class);
		c.add(Restrictions.eq("key.system_id", key.getSystem_id()));
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
