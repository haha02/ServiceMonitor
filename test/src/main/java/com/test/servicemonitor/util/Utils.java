package com.test.servicemonitor.util;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.test.servicemonitor.persistance.Notification;

/**
 * Utility class
 *
 */
public class Utils {

	/**
	 * Collect all message of the {@link Throwable} and its causations in a {@link List}
	 * 
	 */
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

	/**
	 * Collect all message of the {@link Throwable} and its causations and concatenate into a single message.
	 * 
	 */
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

	/**
	 * Return a {@link Set} containing all appeared {@link Types} in the given notification list.
	 * 
	 */
	public static Set<Notification.Types> extractNotificationTypes(List<Notification> notifications) {
		Set<Notification.Types> set = new HashSet<>();
		for (Notification n : notifications) {
			set.add(n.getKey().getNotify_type());
		}
		return set;
	}

	/**
	 * Parse remote system hints from {@link String} to {@link Properties}
	 * 
	 */
	public static Properties parseHints(String hints) {
		Properties p = new Properties();
		if (StringUtils.hasText(hints)) {
			hints = hints.replace(",", "\n");
			try (StringReader sr = new StringReader(hints);) {
				p.load(sr);
			} catch (Exception e) {
				throw new IllegalArgumentException("Failed to parse hints, the original hints string is [" + hints + "]", e);
			}
		}
		return p;
	}
}
