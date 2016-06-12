package com.test.servicemonitor.persistance;

import java.util.List;

public interface RemoteSystemDao {

	RemoteSystem get(RemoteSystem entity);

	List<RemoteSystem> getAll();

	void save(RemoteSystem entity);

	void update(RemoteSystem entity);

	void delete(RemoteSystem entity);
}
