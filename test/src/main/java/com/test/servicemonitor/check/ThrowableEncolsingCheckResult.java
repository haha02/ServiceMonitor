package com.test.servicemonitor.check;

/**
 * {@link CheckResult} implementation that wrapped a {@link Throwable} and use the Throwable's message as fail message.
 *
 */
public class ThrowableEncolsingCheckResult implements CheckResult {

	private Throwable t;
	private FailLevel level;

	public ThrowableEncolsingCheckResult(Throwable t) {
		this(t, null);
	}

	public ThrowableEncolsingCheckResult(Throwable t, FailLevel level) {
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
