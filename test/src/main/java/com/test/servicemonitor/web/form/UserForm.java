package com.test.servicemonitor.web.form;

import java.util.List;

import com.test.servicemonitor.persistance.UserInfo;

public class UserForm {
	private UserInfo userInfo;

	private List<UserInfo> users;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<UserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserForm [userInfo=");
		builder.append(userInfo);
		builder.append("]");
		return builder.toString();
	}

}
