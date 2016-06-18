package com.test.servicemonitor.check.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.servicemonitor.check.LifeChecker;

/**
 * Abstract {@link LifeChecker} implementation
 *
 */
public abstract class AbstractLifeChecker implements LifeChecker {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String systemId;
	private String connectionString;

	public AbstractLifeChecker(String systemId, String connectionString) {
		super();
		this.systemId = systemId;
		this.connectionString = connectionString;
	}

	/**
	 * Get the ID of targeted system
	 * 
	 * @return the ID
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * Get the connection string for this checker to use
	 * 
	 * @return the connection string
	 */
	public String getConnectionString() {
		return connectionString;
	}

}
