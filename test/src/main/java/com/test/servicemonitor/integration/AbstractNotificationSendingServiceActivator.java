package com.test.servicemonitor.integration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.test.servicemonitor.check.CheckResult;
import com.test.servicemonitor.persistance.Notification;
import com.test.servicemonitor.persistance.UserGroup;
import com.test.servicemonitor.persistance.UserGroupService;
import com.test.servicemonitor.persistance.UserInfo;

public abstract class AbstractNotificationSendingServiceActivator {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserGroupService userGroupService;

	public void send(@Header("systemId") String systemId, @Header("notifications") List<Notification> notifications,
			CheckResult checkResult) {
		
		Notification.Types targetType = getTargetType();
		Assert.notNull(targetType, "getTargetType() must not return null");

		Notification desiredNotification = null;
		for (Notification n : notifications) {
			if (targetType.equals(n.getKey().getNotify_type())) {
				desiredNotification = n;
				break;
			}
		}

		if (desiredNotification == null) {
			logger.warn(
					"System [{}] does not configured to send type [{}] notification when check failed, finishing without doing anything."
							+ " The check failed result: {}",
					systemId, targetType, checkResult);

			return;
		}

		String group_id = desiredNotification.getKey().getUser_group();
		UserGroup userGroup = userGroupService.get(group_id);
		List<UserInfo> users = userGroup.getUsers();

		if (CollectionUtils.isEmpty(users)) {
			logger.info(
					"System [{}] configured to send mail notification to group [{}] but the group is empty. "
							+ " The check failed result: {}",
					systemId, group_id, checkResult);
			return;
		}
		logger.info("Starts to send notification(s) to users in user group [{}].", systemId, group_id);
		sendToUsers(systemId, notifications, checkResult, users);
		logger.info("Notification(s) of system [{}] has been sent.", systemId);
	}

	protected abstract Notification.Types getTargetType();

	protected abstract void sendToUsers(String systemId, List<Notification> notifications, CheckResult checkResult,
			List<UserInfo> users);
}
