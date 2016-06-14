package com.test.servicemonitor.persistance;

import java.util.List;

public interface SysParamDao {
	SysParam get(SysParam entity);

	List<SysParam> getAll();

	void save(SysParam entity);

	void update(SysParam entity);

	void delete(SysParam entity);
}
