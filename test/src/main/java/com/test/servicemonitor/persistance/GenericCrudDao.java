package com.test.servicemonitor.persistance;

import java.util.List;

/**
 * Generic CRUD data access object interface
 *
 * @param <E>
 *            the entity type
 */
public interface GenericCrudDao<E> {
	/**
	 * Get the persisted state of the entity by using the argument entity's ID.
	 * 
	 * @param entity
	 *            the entity
	 * @return the entity with the persisted state.
	 */
	E get(E entity);

	/**
	 * Get all persisted entities of the entity type
	 * 
	 * @return all entities
	 */
	List<E> getAll();

	/**
	 * Save the state of the entity
	 * 
	 * @param entity
	 *            the entity to save
	 */
	void save(E entity);

	/**
	 * Update the state of the entity
	 * 
	 * @param entity
	 *            the entity to update
	 */
	void update(E entity);

	/**
	 * Delete the entity from the storage
	 * 
	 * @param entity
	 *            the entity to delete
	 */
	void delete(E entity);
}
