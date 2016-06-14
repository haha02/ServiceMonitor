package com.test.servicemonitor.integration;

import org.springframework.messaging.handler.annotation.Header;

import com.test.servicemonitor.persistance.UserInfo;

public interface MailSendingGateway {
	void send(@Header("userInfo") UserInfo userInfo, @Header("subject") String subject, String content);
}
