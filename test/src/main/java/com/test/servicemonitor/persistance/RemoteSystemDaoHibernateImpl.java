package com.test.servicemonitor.persistance;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class RemoteSystemDaoHibernateImpl extends GenericDaoHibernateSupport implements RemoteSystemDao {

	@Override
	public RemoteSystem get(RemoteSystem entity) {
		assertEntityNotNull(entity);
		return (RemoteSystem) getCurrentSession().get(RemoteSystem.class, entity.getSystem_id());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemoteSystem> getAll() {
		return getCurrentSession().createCriteria(RemoteSystem.class).list();
	}

	@Override
	public void save(RemoteSystem entity) {
		assertEntityNotNull(entity);
		getCurrentSession().save(entity);
	}

	@Override
	public void update(RemoteSystem entity) {
		assertEntityNotNull(entity);
		getCurrentSession().update(entity);
	}

	@Override
	public void delete(RemoteSystem entity) {
		assertEntityNotNull(entity);
		getCurrentSession().delete(entity);
	}

}
