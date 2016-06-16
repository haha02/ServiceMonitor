package com.test.servicemonitor.persistance;

import java.util.List;

public interface UserInfoService {

	UserInfo get(String user_id);

	List<UserInfo> getAll();
	
	List<UserInfo> getByIds(List<String> user_Ids);

	void create(UserInfo entity);

	void update(UserInfo entity);

	void delete(String user_id);
}
