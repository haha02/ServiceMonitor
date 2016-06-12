package com.test.servicemonitor.check;

/**
 * Interface of checking result returned by {@link LifeChecker}
 *
 */
public interface CheckResult {
	/**
	 * Whether the check was passed (i.e. the remote system is alive)
	 * 
	 * @return {@code true} if passed, {@code false} otherwise.
	 */
	boolean isPassed();

	/**
	 * Get the fail level of the checking
	 * 
	 * @return the fail level, {@code null} if the check was passed
	 */
	FailLevel getFailLevel();

	/**
	 * Get the fail message of the checking
	 * 
	 * @return the fail message, {@code null} if the check was passed
	 */
	String getFailMessage();
}
