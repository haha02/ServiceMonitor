package com.test.servicemonitor.persistance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SysParamServiceImpl implements SysParamService {

	@Autowired
	private SysParamDao dao;

	@Override
	public List<SysParam> getAll() {
		return dao.getAll();
	}

	@Override
	public SysParam get(String systemId) {
		SysParam entity = new SysParam();
		entity.setKey(systemId);
		return dao.get(entity);
	}

	@Override
	public void create(SysParam entity) {
		Assert.notNull(entity, "entity must not be null");
		dao.save(entity);
	}

	@Override
	public void delete(String systemId) {
		SysParam entity = new SysParam();
		entity.setKey(systemId);
		dao.delete(entity);
	}

	@Override
	public void update(SysParam entity) {
		Assert.notNull(entity, "entity must not be null");
		dao.update(entity);
	}

}
