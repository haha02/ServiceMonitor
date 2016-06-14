package com.test.servicemonitor.persistance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupServiceImpl implements UserGroupService {
	
	@Autowired
	private UserGroupDao dao;

	@Override
	public List<UserGroup> getAll() {
		return dao.getAll();
	}

	@Override
	public UserGroup get(String group_id) {
		UserGroup entity = new UserGroup();
		entity.setGroup_id(group_id);
		return dao.get(entity);
	}

	@Override
	public void create(UserGroup entity) {
		// TODO Auto-generated method stub
		dao.save(entity);
	}

	@Override
	public void delete(String group_id) {
		UserGroup entity = new UserGroup();
		entity.setGroup_id(group_id);
		dao.delete(entity);
	}

	@Override
	public void update(UserGroup entity) {
		// TODO Auto-generated method stub
		dao.update(entity);
	}

}
