package com.test.servicemonitor.persistance;

import java.util.List;

/**
 * Service interface for interacting with the persistence layer of entity {@link UserGroup}
 *
 */
public interface UserGroupService {
	/**
	 * Get all entity
	 * 
	 * @return the entities
	 */
	List<UserGroup> getAll();

	/**
	 * Get the entity of the given group from storage
	 * 
	 * @param group_id
	 *            the group ID
	 * @return the entity
	 */
	UserGroup get(String group_id);

	/**
	 * Create the given entity in storage
	 * 
	 * @param entity
	 *            the entity
	 */
	void create(UserGroup entity);

	/**
	 * Delete the given entity of the given group from storage
	 * 
	 * @param group_id
	 *            the group ID
	 */
	void delete(String group_id);

	/**
	 * Update the given entity in storage
	 * 
	 * @param entity
	 *            the entity
	 */
	void update(UserGroup entity);
}
