package com.test.servicemonitor.persistance;

import java.util.List;

public interface SysParamService {
	
	List<SysParam> getAll();

	SysParam get(String key);

	void create(SysParam entity);

	void delete(String key);

	void update(SysParam entity);

}
