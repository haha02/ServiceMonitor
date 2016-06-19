package com.test.servicemonitor.persistance;

import java.util.List;

/**
 * Service interface for interacting with the persistence layer of entity {@link RemoteSystem}
 *
 */
public interface RemoteSystemService {
	/**
	 * Get all entity
	 * 
	 * @return the entities
	 */
	List<RemoteSystem> getAll();

	/**
	 * Get the entity of the given remote system from storage
	 * 
	 * @param system_id
	 *            the remote system ID
	 * @return the entity
	 */
	RemoteSystem get(String system_id);

	/**
	 * Create the given entity in storage
	 * 
	 * @param entity
	 *            the entity
	 */
	void create(RemoteSystem entity);

	/**
	 * Delete the given entity of the given remote system from storage
	 * 
	 * @param system_id
	 *            the remote system ID
	 */
	void delete(String system_id);

	/**
	 * Update the given entity in storage
	 * 
	 * @param entity
	 *            the entity
	 */
	void update(RemoteSystem entity);

}
