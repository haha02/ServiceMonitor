package com.test.servicemonitor.persistance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NOTIFICATION")
public class Notification {

	public static enum Types {
		EMAIL, SMS;
		public String getDbValue() {
			return toString();
		}
	}

	private PK key;
	private String level;

	@EmbeddedId
	public PK getKey() {
		return key;
	}

	public void setKey(PK key) {
		this.key = key;
	}

	@Column(name = "LEVEL")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Embeddable
	public static class PK implements Serializable {

		private static final long serialVersionUID = 1L;

		private String system_id;
		private String notify_type;
		private String user_group;

		@Column(name = "SYSTEM_ID")
		public String getSystem_id() {
			return system_id;
		}

		public void setSystem_id(String system_id) {
			this.system_id = system_id;
		}

		@Column(name = "NOTIFY_TYPE")
		public String getNotify_type() {
			return notify_type;
		}

		public void setNotify_type(String notify_type) {
			this.notify_type = notify_type;
		}

		@Column(name = "USER_GROUP")
		public String getUser_group() {
			return user_group;
		}

		public void setUser_group(String user_group) {
			this.user_group = user_group;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((notify_type == null) ? 0 : notify_type.hashCode());
			result = prime * result + ((system_id == null) ? 0 : system_id.hashCode());
			result = prime * result + ((user_group == null) ? 0 : user_group.hashCode());
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
			PK other = (PK) obj;
			if (notify_type == null) {
				if (other.notify_type != null)
					return false;
			} else if (!notify_type.equals(other.notify_type))
				return false;
			if (system_id == null) {
				if (other.system_id != null)
					return false;
			} else if (!system_id.equals(other.system_id))
				return false;
			if (user_group == null) {
				if (other.user_group != null)
					return false;
			} else if (!user_group.equals(other.user_group))
				return false;
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Notification$PK [system_id=");
			builder.append(system_id);
			builder.append(", notify_type=");
			builder.append(notify_type);
			builder.append(", user_group=");
			builder.append(user_group);
			builder.append("]");
			return builder.toString();
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		Notification other = (Notification) obj;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Notification [pk=");
		builder.append(key);
		builder.append(", level=");
		builder.append(level);
		builder.append("]");
		return builder.toString();
	}

}
