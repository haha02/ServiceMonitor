package com.test.servicemonitor.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.test.servicemonitor.check.FailLevel;
import com.test.servicemonitor.persistance.GroupMember;
import com.test.servicemonitor.persistance.MonitorStatus;
import com.test.servicemonitor.persistance.MonitorStatusService;
import com.test.servicemonitor.persistance.Notification;
import com.test.servicemonitor.persistance.NotificationService;
import com.test.servicemonitor.persistance.RemoteSystem;
import com.test.servicemonitor.persistance.RemoteSystem.PeriodUnit;
import com.test.servicemonitor.persistance.RemoteSystemService;
import com.test.servicemonitor.persistance.UserGroup;
import com.test.servicemonitor.persistance.UserGroupService;
import com.test.servicemonitor.persistance.UserInfo;
import com.test.servicemonitor.persistance.UserInfoService;

/**
 * Initializing database and triggering system monitoring on system startup.
 *
 */
@Component
public class Initializer {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private RemoteSystemService remoteSystemService;

	@Autowired
	private MonitorStatusService monitorStatusService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	@Qualifier("hibernateConfig")
	private Properties hibernateConfig;

	@Autowired
	private MainScheduler mainScheduler;

	/**
	 * Initializing method to be called after dependency injection
	 */
	@PostConstruct
	public void init() {
		if ("create".equalsIgnoreCase(hibernateConfig.getProperty("hibernate.hbm2ddl.auto"))) {
			insertDefaultData();
		}
		initializeAllMonitorStatus();
		startMonitorIfNeeded();
	}

	private List<GroupMember> getMembers(UserGroup group, UserInfo... users) {
		List<GroupMember> list = new ArrayList<>();
		for (UserInfo u : users) {
			GroupMember gm = new GroupMember();
			gm.setUser(u);
			gm.setGroup(group);
			list.add(gm);
		}
		return list;
	}

	/**
	 * Correct or add missing {@link MonitorStatus} entries.
	 * <p>
	 * Invalid {@link MonitorStatus} state may result from previous system crush or inappropriate shut down, something like that. That's where this method comes
	 * to rescue.
	 */
	protected void initializeAllMonitorStatus() {
		List<MonitorStatus> statusList = monitorStatusService.getAll();
		List<MonitorStatus> statusToUpdate = new ArrayList<>(statusList.size());
		for (RemoteSystem rs : remoteSystemService.getAll()) {
			MonitorStatus monitorStatus = null;
			for (MonitorStatus ms : statusList) {
				if (ms.getSystem_id().equals(rs.getSystem_id())) {
					monitorStatus = ms;
					break;
				}
			} // end for each monitor status

			if (monitorStatus == null) {
				monitorStatusService.create(contructNewMonitorStatus(rs.getSystem_id()));
				continue;
			}

			boolean needUpdate = false;
			Boolean monitoring = monitorStatus.getMonitoring();
			if (monitoring != null && monitoring) {
				monitorStatus.setMonitoring(false);
				needUpdate = true;
			}
			if (monitorStatus.getLast_check_time() == null) {
				needUpdate = monitorStatus.getAlive() != null;
				if (needUpdate)
					monitorStatus.setAlive(null);
			}
			if (needUpdate) {
				statusToUpdate.add(monitorStatus);
			}
		} // end for each remote system

		if (statusToUpdate.size() > 0) {
			monitorStatusService.updateAll(statusToUpdate);
		}
	}// end method

	private MonitorStatus contructNewMonitorStatus(String system_id) {
		MonitorStatus ms = new MonitorStatus();
		ms.setSystem_id(system_id);
		ms.setMonitoring(false);
		return ms;
	}

	/**
	 * Start monitor remote systems if the system is configured to do so.
	 */
	protected void startMonitorIfNeeded() {
		if (systemConfig.isMonitorOnStarup()) {
			logger.info("Monitor on startup is enabled, starting all enabled system monitor.");
			mainScheduler.startAll(true);
			logger.info("All monitored system is {}", mainScheduler.getMonitoredSystems());
		}
	}

	private void insertDefaultData() {
		// Add users
		UserInfo user1 = new UserInfo("USER01", "Tom", "aaa@aotter.net", "+886987654321");
		userInfoService.create(user1);
		UserInfo user2 = new UserInfo("USER02", "John", "bbb@aotter.net", "+886912345678");
		userInfoService.create(user2);
		UserInfo user3 = new UserInfo("USER03", "Jack", "ccc@aotter.net", "+886913572468");
		userInfoService.create(user3);
		UserInfo user4 = new UserInfo("USER04", "Pamela", "ddd@aotter.net", "+886924681357");
		userInfoService.create(user4);

		// Add user group
		UserGroup group1 = new UserGroup();
		group1.setGroup_id("GROUP_1");
		group1.setGroup_name("All users");
		group1.setMembers(getMembers(group1, user1, user2, user3, user4));
		userGroupService.create(group1);
		UserGroup group2 = new UserGroup();
		group2.setGroup_id("GROUP_2");
		group2.setGroup_name("Men");
		group2.setMembers(getMembers(group1, user1, user2, user3));
		userGroupService.create(group2);
		UserGroup group3 = new UserGroup();
		group3.setGroup_id("GROUP_3");
		group3.setGroup_name("Women");
		group3.setMembers(getMembers(group1, user4));
		userGroupService.create(group3);

		// Add remote system
		RemoteSystem rs1 = new RemoteSystem();
		rs1.setSystem_id("System_1_SQL");
		rs1.setChecker_type("SQL");
		rs1.setConnection_string("jdbc:h2:~/serviceMonitor");
		rs1.setCheck_period(60);
		rs1.setPeriod_unit(PeriodUnit.SECOND);
		rs1.setHints("driverClass=org.h2.Driver,user=user,password=password");
		rs1.setAuto_start(true);
		remoteSystemService.create(rs1);
		RemoteSystem rs2 = new RemoteSystem();
		rs2.setSystem_id("System_2_REST");
		rs2.setChecker_type("REST");
		rs2.setConnection_string("http://localhost:8080/service-monitor/echo");
		rs2.setCheck_period(40);
		rs2.setPeriod_unit(PeriodUnit.SECOND);
		rs2.setHints("param1=value1,param2=value2");
		rs2.setAuto_start(true);
		remoteSystemService.create(rs2);
		RemoteSystem rs3 = new RemoteSystem();
		rs3.setSystem_id("System_3_ALWAYS_FAIL");
		rs3.setChecker_type("ALWAYS_FAIL_MEDIUM");
		rs3.setConnection_string("whatever");
		rs3.setCheck_period(2);
		rs3.setPeriod_unit(PeriodUnit.MINUTE);
		rs3.setAuto_start(true);
		remoteSystemService.create(rs3);

		// Add notification
		Notification n1 = new Notification();
		Notification.PK pk1 = new Notification.PK();
		pk1.setSystem_id("System_1_SQL");
		pk1.setNotify_type(Notification.Types.EMAIL);
		pk1.setUser_group("GROUP_1");
		n1.setKey(pk1);
		n1.setLevel(FailLevel.TRANSIENT);
		notificationService.create(n1);
		Notification n2 = new Notification();
		Notification.PK pk2 = new Notification.PK();
		pk2.setSystem_id("System_2_REST");
		pk2.setNotify_type(Notification.Types.SMS);
		pk2.setUser_group("GROUP_2");
		n2.setKey(pk2);
		n2.setLevel(FailLevel.FATAL);
		notificationService.create(n2);
		Notification n3 = new Notification();
		Notification.PK pk3 = new Notification.PK();
		pk3.setSystem_id("System_3_ALWAYS_FAIL");
		pk3.setNotify_type(Notification.Types.SMS);
		pk3.setUser_group("GROUP_3");
		n3.setKey(pk3);
		n3.setLevel(FailLevel.MEDIUM);
		notificationService.create(n3);
		Notification n4 = new Notification();
		Notification.PK pk4 = new Notification.PK();
		pk4.setSystem_id("System_3_ALWAYS_FAIL");
		pk4.setNotify_type(Notification.Types.SMS);
		pk4.setUser_group("GROUP_1");
		n4.setKey(pk4);
		n4.setLevel(FailLevel.FATAL);
		notificationService.create(n4);
	}
}
