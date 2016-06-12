package com.test.servicemonitor.gateway;

import com.test.servicemonitor.check.CheckResult;

public interface FailedCheckProcessingGateway {
	void process(CheckResult checkResult);
}
