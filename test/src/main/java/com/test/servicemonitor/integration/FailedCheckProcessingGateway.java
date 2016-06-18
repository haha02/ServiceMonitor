package com.test.servicemonitor.integration;

import org.springframework.messaging.handler.annotation.Header;

import com.test.servicemonitor.check.CheckResult;

/**
 * Gateway to Spring Integration based failed check result processing work flow.
 */
public interface FailedCheckProcessingGateway {
	/**
	 * Process the check result
	 * 
	 * @param systemId
	 *            the system ID
	 * @param checkResult
	 *            the check result to be processed
	 */
	void process(@Header("systemId") String systemId, CheckResult checkResult);
}
