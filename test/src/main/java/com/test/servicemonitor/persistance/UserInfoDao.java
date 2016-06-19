package com.test.servicemonitor.persistance;

import java.util.List;

/**
 * DAO interface of entity {@link UserInfo}
 *
 */
public interface UserInfoDao extends GenericCrudDao<UserInfo> {

	/**
	 * Get all user info entity that is in the given user ID list
	 * 
	 * @param user_Ids
	 *            user IDs
	 * @return the entities
	 */
	List<UserInfo> getByIds(List<String> user_Ids);

}
