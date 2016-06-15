package com.test.servicemonitor.persistance;

import java.util.List;

public interface UserInfoService {

	UserInfo get(String user_id);

	List<UserInfo> getAll();

	void create(UserInfo entity);

	void update(UserInfo entity);

	void delete(String user_id);
}
