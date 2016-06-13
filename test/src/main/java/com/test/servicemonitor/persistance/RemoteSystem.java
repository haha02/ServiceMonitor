package com.test.servicemonitor.persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REMOTE_SYSTEM")
public class RemoteSystem {

	public static enum PeriodUnit {
		SECOND(1000), MINUTE(60000), HOUR(3600000);

		private long miliSec;

		private PeriodUnit(long miliSec) {
			this.miliSec = miliSec;
		}

		public String getDbValue() {
			return toString();
		}

		public long getUnitMiliSec() {
			return miliSec;
		}
	}

	private String system_id;
	private String checker_type;
	private String connection_string;
	private String hints;
	private int check_period;
	private String period_unit;
	private String disabled;

	@Id
	@Column(name = "SYSTEM_ID")
	public String getSystem_id() {
		return system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	@Column(name = "CHECKER_TYPE")
	public String getChecker_type() {
		return checker_type;
	}

	public void setChecker_type(String checker_type) {
		this.checker_type = checker_type;
	}

	@Column(name = "CONNECTION_STRING")
	public String getConnection_string() {
		return connection_string;
	}

	public void setConnection_string(String connection_string) {
		this.connection_string = connection_string;
	}

	@Column(name = "HINTS")
	public String getHints() {
		return hints;
	}

	public void setHints(String hints) {
		this.hints = hints;
	}

	@Column(name = "CHECK_PERIOD")
	public int getCheck_period() {
		return check_period;
	}

	public void setCheck_period(int check_period) {
		this.check_period = check_period;
	}

	@Column(name = "PERIOD_UNIT")
	public String getPeriod_unit() {
		return period_unit;
	}

	public void setPeriod_unit(String period_unit) {
		this.period_unit = period_unit;
	}

	@Column(name = "DISABLED")
	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RemoteSystem [systemId=");
		builder.append(system_id);
		builder.append(", checkerType=");
		builder.append(checker_type);
		builder.append(", connectionString=");
		builder.append(connection_string);
		builder.append(", hints=");
		builder.append(hints);
		builder.append(", checkPeriod=");
		builder.append(check_period);
		builder.append(", periodUnit=");
		builder.append(period_unit);
		builder.append(", disabled=");
		builder.append(disabled);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + check_period;
		result = prime * result + ((checker_type == null) ? 0 : checker_type.hashCode());
		result = prime * result + ((connection_string == null) ? 0 : connection_string.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((hints == null) ? 0 : hints.hashCode());
		result = prime * result + ((period_unit == null) ? 0 : period_unit.hashCode());
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
		RemoteSystem other = (RemoteSystem) obj;
		if (check_period != other.check_period)
			return false;
		if (checker_type == null) {
			if (other.checker_type != null)
				return false;
		} else if (!checker_type.equals(other.checker_type))
			return false;
		if (connection_string == null) {
			if (other.connection_string != null)
				return false;
		} else if (!connection_string.equals(other.connection_string))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (hints == null) {
			if (other.hints != null)
				return false;
		} else if (!hints.equals(other.hints))
			return false;
		if (period_unit == null) {
			if (other.period_unit != null)
				return false;
		} else if (!period_unit.equals(other.period_unit))
			return false;
		if (system_id == null) {
			if (other.system_id != null)
				return false;
		} else if (!system_id.equals(other.system_id))
			return false;
		return true;
	}

}
