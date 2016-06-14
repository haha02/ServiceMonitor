package com.test.servicemonitor.persistance;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.springframework.util.Assert;

public abstract class AbstractGenericDaoHibernateImpl<T> extends GenericDaoHibernateSupport
		implements GenericCrudDao<T> {

	@SuppressWarnings("unchecked")
	@Override
	public T get(T entity) {
		assertEntityNotNull(entity);
		Class<T> entityClass = getEntityClass();
		Assert.state(entityClass != null, "getEntityClass() must not return null.");
		Serializable id = acquireId(entity);
		return (T) getCurrentSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		return createCriteria().list();
	}

	@Override
	public void save(T entity) {
		assertEntityNotNull(entity);
		getCurrentSession().save(entity);
	}

	@Override
	public void update(T entity) {
		assertEntityNotNull(entity);
		getCurrentSession().update(entity);
	}

	@Override
	public void delete(T entity) {
		assertEntityNotNull(entity);
		getCurrentSession().delete(entity);
	}

	protected abstract Serializable acquireId(T entity);

	protected abstract Class<T> getEntityClass();

	protected Criteria createCriteria() {
		Class<T> entityClass = getEntityClass();
		Assert.state(entityClass != null, "getEntityClass() must not return null.");
		return getCurrentSession().createCriteria(getEntityClass());
	}

}
