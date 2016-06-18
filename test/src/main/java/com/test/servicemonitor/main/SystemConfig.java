package com.test.servicemonitor.main;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * System configuration resolving class.
 *
 */
@Component
public class SystemConfig {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("config")
	private Properties config;

	private boolean monitorOnStarup;

	/**
	 * Initializing method to be called after dependency injection.
	 * 
	 */
	@PostConstruct
	public void init() {
		monitorOnStarup = "true".equalsIgnoreCase(config.getProperty("servicemonitor.monitor.on.startup"));
		logger.info("Monitor on startup: {}", monitorOnStarup);
	}

	public boolean isMonitorOnStarup() {
		return monitorOnStarup;
	}

}
