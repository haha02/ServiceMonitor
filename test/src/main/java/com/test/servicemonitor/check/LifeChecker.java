package com.test.servicemonitor.check;

/**
 * Interface of remote system life status checking machinery.
 * 
 * Implementation of this interface is supposed to be used single-threaded thus
 * can be implemented statefully.
 * <p>
 * A {@link LifeChecker} instance will be used to monitor one specific remote
 * system continuously. Hence it can keep record of previous checking result and
 * use it to decide the ultimate result to return. For example, when connecting
 * to a remote system is failed, a checker can return checking results with fail
 * level "transient" for the first three check and then return a checking result
 * with fail level "fatal" for the fourth check.
 */
public interface LifeChecker {
	/**
	 * Check the life status of targeted system
	 * <p>
	 * If the implemented method throws an exception, it will be considered as a
	 * failed life checking with fail level {@link FailLevel#FATAL}.
	 * 
	 * @return the checking result
	 */
	CheckResult check();

}
