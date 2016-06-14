package com.test.servicemonitor.persistance;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDaoHibernateImpl extends AbstractGenericDaoHibernateImpl<UserInfo> implements UserInfoDao {

	@Override
	protected Serializable acquireId(UserInfo entity) {
		assertEntityNotNull(entity);
		return entity.getUser_id();
	}

	@Override
	protected Class<UserInfo> getEntityClass() {
		return UserInfo.class;
	}

}
