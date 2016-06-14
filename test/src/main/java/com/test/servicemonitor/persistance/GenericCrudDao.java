package com.test.servicemonitor.persistance;

import java.util.List;

public interface GenericCrudDao<T> {
	T get(T entity);

	List<T> getAll();

	void save(T entity);

	void update(T entity);

	void delete(T entity);
}
