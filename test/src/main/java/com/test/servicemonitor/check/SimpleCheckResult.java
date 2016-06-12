package com.test.servicemonitor.check;

public class SimpleCheckResult implements CheckResult {
	private boolean passed;
	private String failMessage;
	private FailLevel failLevel;

	public SimpleCheckResult(boolean passed) {
		this(passed, null, null);
	}

	public SimpleCheckResult(boolean passed, String failMessage, FailLevel failLevel) {
		super();
		this.passed = passed;
		this.failMessage = failMessage;
		this.failLevel = failLevel;
	}

	@Override
	public boolean isPassed() {
		return passed;
	}

	@Override
	public FailLevel getFailLevel() {
		return failLevel;
	}

	@Override
	public String getFailMessage() {
		return failMessage;
	}

}
