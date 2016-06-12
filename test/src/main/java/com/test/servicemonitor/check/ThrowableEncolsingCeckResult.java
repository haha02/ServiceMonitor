package com.test.servicemonitor.check;

public class ThrowableEncolsingCeckResult implements CheckResult {

	private Throwable t;
	private FailLevel level;

	public ThrowableEncolsingCeckResult(Throwable t) {
		this(t, null);
	}

	public ThrowableEncolsingCeckResult(Throwable t, FailLevel level) {
		super();
		this.t = t;
		this.level = (level != null) ? level : FailLevel.FATAL;
	}

	@Override
	public boolean isPassed() {
		return t == null;
	}

	@Override
	public FailLevel getFailLevel() {
		return level;
	}

	@Override
	public String getFailMessage() {
		return t == null ? null : t.getMessage();
	}

}
