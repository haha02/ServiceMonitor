package com.test.servicemonitor.persistance;

import java.util.List;

/**
 * Service interface for interacting with the persistence layer of entity {@link UserInfo}
 *
 */
public interface UserInfoService {
	/**
	 * Get the entity of the given user from storage
	 * 
	 * @param user_id
	 *            the user ID
	 * @return the entity
	 */
	UserInfo get(String user_id);

	/**
	 * Get all entity
	 * 
	 * @return the entities
	 */
	List<UserInfo> getAll();

	/**
	 * Get all user info entity that is in the given user ID list
	 * 
	 * @param user_Ids
	 *            user IDs
	 * @return the entities
	 */
	List<UserInfo> getByIds(List<String> user_Ids);

	/**
	 * Create the given entity in storage
	 * 
	 * @param entity
	 *            the entity
	 */
	void create(UserInfo entity);

	/**
	 * Update the given entity in storage
	 * 
	 * @param entity
	 *            the entity
	 */
	void update(UserInfo entity);

	/**
	 * Delete the given entity of the given user from storage
	 * 
	 * @param user_id
	 *            the user ID
	 */
	void delete(String user_id);
}
