package com.test.servicemonitor.persistance;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MONITOR_STATUS")
public class MonitorStatus {
	private String system_id;

	private Boolean monitoring;

	private Boolean alive;

	private Timestamp last_check_time;

	@Id
	@Column(name = "SYSTEM_ID")
	public String getSystem_id() {
		return system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	@Column(name = "MONITORING")
	public Boolean getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Boolean monitoring) {
		this.monitoring = monitoring;
	}

	@Column(name = "ALIVE")
	public Boolean getAlive() {
		return alive;
	}

	public void setAlive(Boolean alive) {
		this.alive = alive;
	}

	@Column(name = "LAST_CHECK_TIME")
	public Timestamp getLast_check_time() {
		return last_check_time;
	}

	public void setLast_check_time(Timestamp last_check_time) {
		this.last_check_time = last_check_time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alive == null) ? 0 : alive.hashCode());
		result = prime * result + ((last_check_time == null) ? 0 : last_check_time.hashCode());
		result = prime * result + ((monitoring == null) ? 0 : monitoring.hashCode());
		result = prime * result + ((system_id == null) ? 0 : system_id.hashCode());
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
		MonitorStatus other = (MonitorStatus) obj;
		if (alive == null) {
			if (other.alive != null)
				return false;
		} else if (!alive.equals(other.alive))
			return false;
		if (last_check_time == null) {
			if (other.last_check_time != null)
				return false;
		} else if (!last_check_time.equals(other.last_check_time))
			return false;
		if (monitoring == null) {
			if (other.monitoring != null)
				return false;
		} else if (!monitoring.equals(other.monitoring))
			return false;
		if (system_id == null) {
			if (other.system_id != null)
				return false;
		} else if (!system_id.equals(other.system_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SystemStatus [system_id=");
		builder.append(system_id);
		builder.append(", monitoring=");
		builder.append(monitoring);
		builder.append(", alive=");
		builder.append(alive);
		builder.append(", last_check_time=");
		builder.append(last_check_time);
		builder.append("]");
		return builder.toString();
	}

}
