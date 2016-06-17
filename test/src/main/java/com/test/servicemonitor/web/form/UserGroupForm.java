package com.test.servicemonitor.web.form;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.test.servicemonitor.persistance.UserGroup;
import com.test.servicemonitor.persistance.UserInfo;

public class UserGroupForm {

	@NotNull
	private String group_id;
	private String group_name;

	private List<UserInfo> users;
	private List<String> userIds;

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public List<UserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}

	public void fromUserGroup(UserGroup userGroup) {
		Assert.notNull(userGroup);
		this.group_id = userGroup.getGroup_id();
		this.group_name = userGroup.getGroup_name();
		this.users = userGroup.getUsers();
	}

	public UserGroup toUserGroup() {
		UserGroup userGroup = new UserGroup();
		userGroup.setGroup_id(group_id);
		userGroup.setGroup_name(group_name);
		userGroup.setUsers(users);
		return userGroup;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserGroupForm [group_id=");
		builder.append(group_id);
		builder.append(", group_name=");
		builder.append(group_name);
		builder.append(", users=");
		builder.append(users);
		builder.append(", userIds=");
		builder.append(userIds);
		builder.append("]");
		return builder.toString();
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

}