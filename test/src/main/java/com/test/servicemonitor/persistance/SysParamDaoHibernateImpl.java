package com.test.servicemonitor.persistance;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

@Repository
public class SysParamDaoHibernateImpl extends AbstractGenericDaoHibernateImpl<SysParam> implements SysParamDao {

	@Override
	protected Serializable acquireId(SysParam entity) {
		assertEntityNotNull(entity);
		return entity.getKey();
	}

	@Override
	protected Class<SysParam> getEntityClass() {
		return SysParam.class;
	}

}
