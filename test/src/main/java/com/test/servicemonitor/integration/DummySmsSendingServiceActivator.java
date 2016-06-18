package com.test.servicemonitor.integration;

import java.util.List;

import com.test.servicemonitor.check.CheckResult;
import com.test.servicemonitor.persistance.Notification.Types;
import com.test.servicemonitor.persistance.UserInfo;

/**
 * Dummy SMS sending service activator, just print some log without doing anything.
 *
 */
public class DummySmsSendingServiceActivator extends AbstractNotificationSendingServiceActivator {

	@Override
	protected Types getTargetType() {
		return Types.SMS;
	}

	@Override
	protected void sendToUsers(String systemId, CheckResult checkResult, List<UserInfo> users) {
		try {
			logger.debug(
					"Pretending I am busy at sending SMS nofitications. They won't see this until they set the log level down to debug. How clever!");
			Thread.sleep(1000);
			logger.debug("Pretending I am busy at sending SMS nofitications, again.");
			Thread.sleep(2000);
			logger.debug("Pretending I am busy at sending SMS nofitications, one more time.");
			Thread.sleep(2000);
			logger.debug("Now I think it's OK to tell them the job has been done.");
		} catch (InterruptedException e) {
			// ignore anyway
		}
	}
}
