package com.test.servicemonitor.persistance;

import java.io.Serializable;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * {@link MonitorStatusDao} hibernate based implementation
 *
 */
@Repository
public class MonitorStatusDaoHibernateImpl extends AbstractGenericDaoHibernateImpl<MonitorStatus> implements MonitorStatusDao {

	@Override
	protected Serializable acquireId(MonitorStatus entity) {
		return entity.getSystem_id();
	}

	@Override
	protected Class<MonitorStatus> getEntityClass() {
		return MonitorStatus.class;
	}

	@Override
	public void updateAlive(MonitorStatus entity) {
		assertEntityNotNull(entity);
		Query q = getCurrentSession().createQuery("UPDATE MonitorStatus "
				+ "SET alive=:alive,last_check_time=:last_check_time "
				+ "WHERE system_id=:system_id");
		q.setParameter("alive", entity.getAlive());
		q.setParameter("last_check_time", entity.getLast_check_time());
		q.setParameter("system_id", entity.getSystem_id());
		q.executeUpdate();
	}

	@Override
	public void updateMonitoring(MonitorStatus entity) {
		assertEntityNotNull(entity);
		Query q = getCurrentSession().createQuery("UPDATE MonitorStatus "
				+ "SET monitoring=:monitoring "
				+ "WHERE system_id=:system_id");
		q.setParameter("monitoring", entity.getMonitoring());
		q.setParameter("system_id", entity.getSystem_id());
		q.executeUpdate();
	}

}
