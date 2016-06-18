package com.test.servicemonitor.persistance;

import java.util.List;

/**
 * Generic CRUD data access object interface
 *
 * @param <E>
 *            the entity type
 */
public interface GenericCrudDao<E> {
	E get(E entity);

	List<E> getAll();

	void save(E entity);

	void update(E entity);

	void delete(E entity);
}
