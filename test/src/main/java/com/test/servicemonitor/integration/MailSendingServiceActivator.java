package com.test.servicemonitor.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.test.servicemonitor.check.CheckResult;
import com.test.servicemonitor.persistance.Notification;
import com.test.servicemonitor.persistance.Notification.Types;
import com.test.servicemonitor.persistance.UserInfo;

/**
 * Email sending service activator
 * <p>
 * Use the check result to generate mail subject and content then pass to {@link MailSendingGateway} to send mail to user.
 *
 */
public class MailSendingServiceActivator extends AbstractNotificationSendingServiceActivator {

	@Autowired
	private MailSendingGateway mailSendingGateway;

	@Override
	protected Types getTargetType() {
		return Notification.Types.EMAIL;
	}

	@Override
	protected void sendToUsers(String systemId, CheckResult checkResult, List<UserInfo> users) {
		String level = checkResult.getFailLevel().toString();
		String subject = "[" + level + "][" + systemId + "]: Life checking failed.";
		StringBuilder msg = new StringBuilder();
		msg.append("Level: ").append(level).append("\n");
		msg.append("Message: ").append(checkResult.getFailMessage());
		for (UserInfo user : users) {
			mailSendingGateway.send(user, subject, msg.toString());
		}
	}
}
