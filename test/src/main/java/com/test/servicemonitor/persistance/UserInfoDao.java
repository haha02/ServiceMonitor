package com.test.servicemonitor.persistance;

import java.util.List;

public interface UserInfoDao extends GenericCrudDao<UserInfo>{

	List<UserInfo> getByIds(List<String> user_Ids);

}
