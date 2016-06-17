package com.test.servicemonitor.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.test.servicemonitor.check.CheckResult;
import com.test.servicemonitor.persistance.Notification;
import com.test.servicemonitor.persistance.Notification.Types;
import com.test.servicemonitor.persistance.UserInfo;

public class MailSendingServiceActivator extends AbstractNotificationSendingServiceActivator {

	@Autowired
	private MailSendingGateway mailSendingGateway;

	@Override
	protected Types getTargetType() {
		return Notification.Types.EMAIL;
	}

	@Override
	protected void sendToUsers(String systemId, CheckResult checkResult, List<UserInfo> users) {
		String subject = systemId + " life checking failed.";
		checkResult.getFailLevel();

		for (UserInfo user : users) {
			mailSendingGateway.send(user, subject, checkResult.getFailMessage());
		}
	}
}
