package com.test.servicemonitor.persistance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

/**
 * Convenient super class for Hibernate-based data access objects.
 *
 */
public abstract class HibernateDaoSupport {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("hibernateSessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * Get current {@link Session} from the referenced {@link SessionFactory}
	 * 
	 */
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Set {@link SessionFactory} to be used
	 * 
	 * @param sessionFactory
	 *            the session factory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Convenient method to check if a given entity is {@code null}, throws {@link IllegalArgumentException} when it is.
	 * 
	 * @param entity
	 *            the entity to be check
	 */
	protected void assertEntityNotNull(Object entity) {
		Assert.notNull(entity, "entity should not be null");
	}

}