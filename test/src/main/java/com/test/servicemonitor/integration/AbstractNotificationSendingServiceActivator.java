package com.test.servicemonitor.integration;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.Assert;
import com.test.servicemonitor.check.CheckResult;
import com.test.servicemonitor.check.FailLevel;
import com.test.servicemonitor.persistance.GroupMember;
import com.test.servicemonitor.persistance.Notification;
import com.test.servicemonitor.persistance.UserGroup;
import com.test.servicemonitor.persistance.UserGroupService;
import com.test.servicemonitor.persistance.UserInfo;

/**
 * Abstract notification sending service activator.
 * <p>
 * This implementation checks if the fail level weight is equal or greater than a notification setting's fail level threshold. If not, the notification is
 * ignored. After fail level threshold checking, the implementation acquire users of the current notification setting entry and pass the check result and
 * acquired users to {@link #sendToUsers(String, CheckResult, List)} to send notification to those users. This method will be called once for each notification
 * setting entry.
 *
 */
public abstract class AbstractNotificationSendingServiceActivator {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserGroupService userGroupService;

	public void send(@Header("systemId") String systemId, @Header("notifications") List<Notification> notifications,
			CheckResult checkResult) {

		Assert.notNull(getTargetType(), "getTargetType() must not return null");

		notifications = filterOutNonTarget(notifications);

		FailLevel fLevel = checkResult.getFailLevel();
		int checkResultLevelWeight = fLevel.getWeight();
		for (Notification n : notifications) {
			int nLevelWeight = n.getLevel().getWeight();
			if (checkResultLevelWeight < nLevelWeight) {
				logger.debug("The weight of check result fail leve [{}] "
						+ "is below the weight of configured threshold level of notification group [{}],"
						+ " skip sending notification(s).", n.getLevel(), fLevel);
				continue;
			}
			String group_id = n.getKey().getUser_group();
			List<UserInfo> users = getUserOfGroup(group_id);
			logger.info("[{}]: Sending notification(s) to users in user group [{}].", systemId, group_id);
			sendToUsers(systemId, checkResult, users);
		}
		logger.info("[{}]: Notification(s) has been sent.", systemId);
	}

	private List<Notification> filterOutNonTarget(List<Notification> notifications) {
		Notification.Types targetType = getTargetType();
		for (int i = notifications.size() - 1; i >= 0; i--) {
			Notification.Types nType = notifications.get(i).getKey().getNotify_type();
			if (!targetType.equals(nType)) {
				notifications.remove(i);
			}
		}
		return notifications;
	}

	private List<UserInfo> getUserOfGroup(String group_id) {
		UserGroup userGroup = userGroupService.get(group_id);
		List<GroupMember> members = userGroup.getMembers();
		List<UserInfo> users = new ArrayList<>(members.size());
		for (GroupMember gm : members) {
			users.add(gm.getUser());
		}
		return users;
	}

	/**
	 * Get the notification type this implementation is targeting.
	 * 
	 * @return the notification type
	 */
	protected abstract Notification.Types getTargetType();

	/**
	 * Send notification to users in one notification setting entry.
	 * 
	 * @param systemId
	 *            the system ID
	 * @param checkResult
	 *            the check result
	 * @param users
	 *            the users in one notification setting entry
	 */
	protected abstract void sendToUsers(String systemId, CheckResult checkResult, List<UserInfo> users);
}
