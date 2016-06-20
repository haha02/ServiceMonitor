package com.test.servicemonitor.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * Stop all monitoring task before context close
 *
 */
@Component
public class Finalizer implements ApplicationListener<ContextClosedEvent> {

	@Autowired
	private MainScheduler mainScheduler;

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		mainScheduler.stopAll();
	}

}
