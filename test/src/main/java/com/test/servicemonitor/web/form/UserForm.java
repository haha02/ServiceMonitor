package com.test.servicemonitor.web.form;

import com.test.servicemonitor.persistance.UserInfo;

public class UserForm {
	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
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
