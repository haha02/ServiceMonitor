package com.test.servicemonitor.check.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.servicemonitor.check.LifeChecker;

public abstract class AbstractLifeChecker implements LifeChecker {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private String systemId;
	private String connectionString;

	public AbstractLifeChecker(String systemId, String connectionString) {
		super();
		this.systemId = systemId;
		this.connectionString = connectionString;
	}

	public String getSystemId() {
		return systemId;
	}

	public String getConnectionString() {
		return connectionString;
	}

}
