package com.test.servicemonitor.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.test.servicemonitor.persistance.Notification;

/**
 * Utility class
 *
 */
public class Utils {

	public static List<String> getAllMessages(Throwable t) {
		List<String> list = new ArrayList<>();
		while (t != null) {
			if (t instanceof SQLException) {
				SQLException se = (SQLException) t;
				while (se != null) {
					list.add(se.getMessage());
					se = se.getNextException();
				}
			} else {
				list.add(t.getMessage());
			}
			t = t.getCause();
		}
		return list;
	}

	public static String concatAllMessages(Throwable t) {
		if (t == null)
			return "null";
		List<String> msgList = getAllMessages(t);
		if (msgList.size() == 1)
			return msgList.get(0);
		Iterator<String> msgIt = msgList.iterator();
		StringBuilder sb = new StringBuilder("{");
		while (msgIt.hasNext()) {
			sb.append(msgIt.next());
			if (msgIt.hasNext()) {
				sb.append("}, caused by {");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		Class.forName(null);
	}

	public static Set<String> extractNotificationTypes(List<Notification> notifications) {
		Set<String> set = new HashSet<>();
		for (Notification n : notifications) {
			set.add(n.getKey().getNotify_type());
		}
		return set;
	}
}
