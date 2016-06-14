package com.test.servicemonitor.persistance;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

@Repository
public class RemoteSystemDaoHibernateImpl extends AbstractGenericDaoHibernateImpl<RemoteSystem> implements RemoteSystemDao {

	@Override
	protected Serializable acquireId(RemoteSystem entity) {
		assertEntityNotNull(entity);
		return entity.getSystem_id();
	}

	@Override
	protected Class<RemoteSystem> getEntityClass() {
		return RemoteSystem.class;
	}


}
