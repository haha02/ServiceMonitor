package com.test.servicemonitor.integration;

import org.springframework.messaging.handler.annotation.Header;

import com.test.servicemonitor.check.CheckResult;

public interface FailedCheckProcessingGateway {
	void process(@Header("systemId") String systemId, CheckResult checkResult);
}
