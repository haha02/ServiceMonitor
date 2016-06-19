package com.test.servicemonitor.persistance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link UserInfoService} implementation
 *
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDao dao;

	@Override
	public UserInfo get(String user_id) {
		UserInfo entity = new UserInfo();
		entity.setUser_id(user_id);
		return dao.get(entity);
	}

	@Override
	public List<UserInfo> getAll() {
		return dao.getAll();
	}

	@Override
	public void create(UserInfo entity) {
		dao.save(entity);
	}

	@Override
	public void update(UserInfo entity) {
		dao.update(entity);
	}

	@Override
	public void delete(String user_id) {
		UserInfo entity = new UserInfo();
		entity.setUser_id(user_id);
		dao.delete(entity);
	}

	@Override
	public List<UserInfo> getByIds(List<String> user_Ids) {
		return dao.getByIds(user_Ids);
	}

}
