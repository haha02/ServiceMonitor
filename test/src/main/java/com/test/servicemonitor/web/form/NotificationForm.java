package com.test.servicemonitor.web.form;

import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.test.servicemonitor.check.FailLevel;
import com.test.servicemonitor.persistance.Notification;
import com.test.servicemonitor.persistance.Notification.PK;

public class NotificationForm {
	
	@NotNull
	private String system_id;
	
	@NotNull
	private Notification.Types notify_type;
	
	@NotNull
	private String user_group;
	
	private FailLevel level;

	public String getSystem_id() {
		return system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	public Notification.Types getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(Notification.Types notify_type) {
		this.notify_type = notify_type;
	}

	public String getUser_group() {
		return user_group;
	}

	public void setUser_group(String user_group) {
		this.user_group = user_group;
	}

	public FailLevel getLevel() {
		return level;
	}

	public void setLevel(FailLevel level) {
		this.level = level;
	}

	public void fromNotification(Notification notification) {
		Assert.notNull(notification);
		PK key = notification.getKey();
		if (key != null) {
			this.system_id = key.getSystem_id();
			this.notify_type = key.getNotify_type();
			this.user_group = key.getUser_group();
		}
		this.level = notification.getLevel();
	}

	public Notification toNotification() {
		Notification notification = new Notification();
		PK key = new PK();
		key.setSystem_id(system_id);
		key.setNotify_type(notify_type);
		key.setUser_group(user_group);
		notification.setKey(key);
		notification.setLevel(level);
		return notification;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotificationForm [system_id=");
		builder.append(system_id);
		builder.append(", notify_type=");
		builder.append(notify_type);
		builder.append(", user_group=");
		builder.append(user_group);
		builder.append(", level=");
		builder.append(level);
		builder.append("]");
		return builder.toString();
	}

}
