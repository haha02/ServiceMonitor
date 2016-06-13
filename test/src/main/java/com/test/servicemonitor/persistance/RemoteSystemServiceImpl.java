package com.test.servicemonitor.persistance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("remoteSystemService")
public class RemoteSystemServiceImpl implements RemoteSystemService {

	@Autowired
	private RemoteSystemDao dao;

	@Override
	public List<RemoteSystem> getAll() {
		return dao.getAll();
	}

	@Override
	public RemoteSystem get(String systemId) {
		RemoteSystem entity = new RemoteSystem();
		entity.setSystem_id(systemId);
		return dao.get(entity);
	}

	@Override
	public void create(RemoteSystem entity) {
		Assert.notNull(entity, "entity must not be null");
		dao.save(entity);
	}

	@Override
	public void delete(String systemId) {
		RemoteSystem entity = new RemoteSystem();
		entity.setSystem_id(systemId);
		dao.delete(entity);
	}

	@Override
	public void update(RemoteSystem entity) {
		Assert.notNull(entity, "entity must not be null");
		dao.update(entity);
	}

}
