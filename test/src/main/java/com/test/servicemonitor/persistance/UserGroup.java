package com.test.servicemonitor.persistance;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "USER_GROUP")
public class UserGroup {

	private String group_id;
	private String group_name;

	private List<UserInfo> users;

	@Id
	@Column(name = "GROUP_ID")
	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	@Column(name = "GROUP_NAME")
	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "GROUP_MEMBER", joinColumns = {
			@JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") })
	public List<UserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserGroup [group_id=");
		builder.append(group_id);
		builder.append(", group_name=");
		builder.append(group_name);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group_id == null) ? 0 : group_id.hashCode());
		result = prime * result + ((group_name == null) ? 0 : group_name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGroup other = (UserGroup) obj;
		if (group_id == null) {
			if (other.group_id != null)
				return false;
		} else if (!group_id.equals(other.group_id))
			return false;
		if (group_name == null) {
			if (other.group_name != null)
				return false;
		} else if (!group_name.equals(other.group_name))
			return false;
		return true;
	}

}
