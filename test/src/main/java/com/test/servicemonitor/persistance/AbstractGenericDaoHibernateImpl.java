package com.test.servicemonitor.persistance;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.util.Assert;

/**
 * Abstract generic DAO implemented using Hibernate
 *
 * @param <E>
 *            the entity type
 */
public abstract class AbstractGenericDaoHibernateImpl<E> extends HibernateDaoSupport
		implements GenericCrudDao<E> {

	@SuppressWarnings("unchecked")
	@Override
	public E get(E entity) {
		assertEntityNotNull(entity);
		Class<E> entityClass = getEntityClass();
		Assert.state(entityClass != null, "getEntityClass() must not return null.");
		Serializable id = acquireId(entity);
		return (E) getCurrentSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> getAll() {
		return createCriteria().list();
	}

	@Override
	public void save(E entity) {
		assertEntityNotNull(entity);
		Session session = getCurrentSession();
		session.save(entity);
	}

	@Override
	public void update(E entity) {
		assertEntityNotNull(entity);
		getCurrentSession().update(entity);
	}

	@Override
	public void delete(E entity) {
		assertEntityNotNull(entity);
		getCurrentSession().delete(entity);
	}

	/**
	 * Acquire the ID from the entity
	 * 
	 * @param entity
	 *            the entity
	 * @return the ID of the entity
	 */
	protected abstract Serializable acquireId(E entity);

	/**
	 * Get the class of the entity this DAO handles
	 * 
	 * @return the entity class
	 */
	protected abstract Class<E> getEntityClass();

	/**
	 * Convenient method to create a hibernate {@link Criteria} using the entity class and set the result transformer to
	 * {@link CriteriaSpecification#DISTINCT_ROOT_ENTITY}.
	 * 
	 * @return the created criteria
	 */
	protected Criteria createCriteria() {
		Class<E> entityClass = getEntityClass();
		Assert.state(entityClass != null, "getEntityClass() must not return null.");
		return getCurrentSession().createCriteria(getEntityClass()).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	}

}
