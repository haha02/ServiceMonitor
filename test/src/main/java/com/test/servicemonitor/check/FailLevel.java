package com.test.servicemonitor.check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Remote system life checking fail level enumeration
 *
 */
public enum FailLevel {
	/**
	 * The check failed because of transient error (e.g. remote system is busy).
	 */
	TRANSIENT(1),
	/**
	 * The check failed because of medium error (may or may not recover).
	 */
	MEDIUM(10),
	/**
	 * The check failed because of fatal error.
	 */
	FATAL(100);

	private int weight;

	private FailLevel(int weight) {
		this.weight = weight;
	}

	/**
	 * Get the weight of current fail level
	 * 
	 * @return the weight, the higher the value the more severe the fail is.
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Get all values of this enumeration, order by weight, ascending.
	 * 
	 * @return the weight, the higher the value the more severe the fail is.
	 */
	public static FailLevel[] valuesByWeight() {
		List<FailLevel> list = new ArrayList<>(Arrays.asList(values()));
		Collections.sort(list, new Comparator<FailLevel>() {
			@Override
			public int compare(FailLevel o1, FailLevel o2) {
				return o1.getWeight() - o2.getWeight();
			}
		});
		return list.toArray(new FailLevel[list.size()]);
	}

}
