package com.test.servicemonitor.persistance;

import java.util.List;

public interface RemoteSystemService {

	List<RemoteSystem> getAll();

	RemoteSystem get(String systemId);

	void create(RemoteSystem entity);

	void delete(String systemId);

	void update(RemoteSystem entity);

}
