package com.test.servicemonitor.web.form;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.Assert;

import com.test.servicemonitor.persistance.UserInfo;

/**
 * 
 * Form bean of {@link UserInfo} CRUD pages
 *
 */
public class UserForm {
	@NotBlank
	private String user_id;
	private String user_name;
	private String email;
	private String sms;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public void fromUserInfo(UserInfo userInfo) {
		Assert.notNull(userInfo);
		this.user_id = userInfo.getUser_id();
		this.user_name = userInfo.getUser_name();
		this.email = userInfo.getEmail();
		this.sms = userInfo.getSms();
	}

	public UserInfo toUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUser_id(user_id);
		userInfo.setUser_name(user_name);
		userInfo.setEmail(email);
		userInfo.setSms(sms);
		return userInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserForm [user_id=");
		builder.append(user_id);
		builder.append(", user_name=");
		builder.append(user_name);
		builder.append(", email=");
		builder.append(email);
		builder.append(", sms=");
		builder.append(sms);
		builder.append("]");
		return builder.toString();
	}

}
