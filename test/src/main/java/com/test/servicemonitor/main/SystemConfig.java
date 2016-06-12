package com.test.servicemonitor.main;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("config")
	private Properties config;

	private boolean logSQL;
	private boolean monitorOnStarup;

	@PostConstruct
	public void init() {
		
		logSQL = "true".equalsIgnoreCase(config.getProperty("servicemonitor.log.sql"));
		logger.info("log SQL feature enabled: {}", logSQL);
		
		monitorOnStarup = "true".equalsIgnoreCase(config.getProperty("servicemonitor.monitor.on.startup"));
		logger.info("Monitor on startup: {}", monitorOnStarup);
	}

	public boolean isLogSQL() {
		return logSQL;
	}

	public boolean isMonitorOnStarup() {
		return monitorOnStarup;
	}

}
