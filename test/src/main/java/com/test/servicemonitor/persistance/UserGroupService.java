package com.test.servicemonitor.persistance;

import java.util.List;

public interface UserGroupService {
	List<UserGroup> getAll();

	UserGroup get(String group_id);

	void create(UserGroup entity);

	void delete(String group_id);

	void update(UserGroup entity);
}
