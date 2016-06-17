package com.test.servicemonitor.integration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.Assert;
import com.test.servicemonitor.check.CheckResult;
import com.test.servicemonitor.check.FailLevel;
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

		Assert.notNull(getTargetType(), "getTargetType() must not return null");

		notifications = filterOutNonTarget(notifications);

		FailLevel fLevel = checkResult.getFailLevel();
		int threshold = fLevel.getWeight();
		for (Notification n : notifications) {
			int nLevelWeight = n.getLevel().getWeight();
			if (nLevelWeight < threshold) {
				logger.debug("The weight of check result fail leve [{}] "
						+ "is below the weight of configured threshold level of notification group [{}],"
						+ " skip sending notification(s).", n.getLevel(), fLevel);
				continue;
			}
			String group_id = n.getKey().getUser_group();

			UserGroup userGroup = userGroupService.get(group_id);
			List<UserInfo> users = userGroup.getUsers();

			logger.info("[{}]: Sending notification(s) to users in user group [{}].", systemId, group_id);
			sendToUsers(systemId, checkResult, users);
		}
		logger.info("[{}]: Notification(s) has been sent.", systemId);
	}

	private List<Notification> filterOutNonTarget(List<Notification> notifications) {
		Notification.Types targetType = getTargetType();
		for (int i = notifications.size(); i >= 0; i--) {
			Notification.Types nType = notifications.get(i).getKey().getNotify_type();
			if (!targetType.equals(nType)) {
				notifications.remove(i);
			}
		}
		return notifications;
	}

	protected abstract Notification.Types getTargetType();

	protected abstract void sendToUsers(String systemId, CheckResult checkResult, List<UserInfo> users);
}
