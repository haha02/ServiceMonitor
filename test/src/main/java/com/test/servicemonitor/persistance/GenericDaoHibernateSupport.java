package com.test.servicemonitor.persistance;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

public abstract class GenericDaoHibernateSupport {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("hibernateSessionFactory")
	private SessionFactory sessionFactory;

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected void assertEntityNotNull(Object entity) {
		assertNotNull(entity, "entity should not be null");
	}

	protected void assertNotNull(Object obj, String message) {
		Assert.notNull(obj, message);
	}

	protected void addEqRestriction(Criteria criteria, String propertyName, Object value) {
		criteria.add(Restrictions.eq(propertyName, value));
	}

}