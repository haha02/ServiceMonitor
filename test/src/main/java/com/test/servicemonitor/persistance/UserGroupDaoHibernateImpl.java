package com.test.servicemonitor.persistance;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

/**
 * {@link UserGroupDao} hibernate based implementation
 *
 */
@Repository
public class UserGroupDaoHibernateImpl extends AbstractGenericDaoHibernateImpl<UserGroup> implements UserGroupDao {

	@Override
	protected Serializable acquireId(UserGroup entity) {
		assertEntityNotNull(entity);
		return entity.getGroup_id();
	}

	@Override
	protected Class<UserGroup> getEntityClass() {
		return UserGroup.class;
	}

}
