package com.test.servicemonitor.persistance;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> getByIds(List<String> user_Ids) {
		Assert.notNull(user_Ids, "user_Ids must not be null");
		Criteria c = createCriteria();
		c.add(Restrictions.in("user_id", user_Ids));
		return c.list();
	}

}
