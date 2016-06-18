package com.test.servicemonitor.persistance;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
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

	protected abstract Serializable acquireId(E entity);

	protected abstract Class<E> getEntityClass();

	protected Criteria createCriteria() {
		Class<E> entityClass = getEntityClass();
		Assert.state(entityClass != null, "getEntityClass() must not return null.");
		return getCurrentSession().createCriteria(getEntityClass()).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}

}
