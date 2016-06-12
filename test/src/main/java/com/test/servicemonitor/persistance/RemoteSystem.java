package com.test.servicemonitor.persistance;

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

	private String systemId;
	private String checkerType;
	private String connectionString;
	private String hints;
	private int checkPeriod;
	private String periodUnit;
	private boolean disabled;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getCheckerType() {
		return checkerType;
	}

	public void setCheckerType(String checkerType) {
		this.checkerType = checkerType;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public String getHints() {
		return hints;
	}

	public void setHints(String hints) {
		this.hints = hints;
	}

	public int getCheckPeriod() {
		return checkPeriod;
	}

	public void setCheckPeriod(int checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	public String getPeriodUnit() {
		return periodUnit;
	}

	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
	}
	
	

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RemoteSystem [systemId=");
		builder.append(systemId);
		builder.append(", checkerType=");
		builder.append(checkerType);
		builder.append(", connectionString=");
		builder.append(connectionString);
		builder.append(", hints=");
		builder.append(hints);
		builder.append(", checkPeriod=");
		builder.append(checkPeriod);
		builder.append(", periodUnit=");
		builder.append(periodUnit);
		builder.append(", disabled=");
		builder.append(disabled);
		builder.append("]");
		return builder.toString();
	}

}
