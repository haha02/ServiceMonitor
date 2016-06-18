package com.test.servicemonitor.integration;

import org.springframework.messaging.handler.annotation.Header;

import com.test.servicemonitor.persistance.UserInfo;

/**
 * Gateway to Spring integration based mail sending machinery.
 *
 */
public interface MailSendingGateway {
	/**
	 * Send mail
	 * 
	 * @param userInfo
	 *            the mail receiver
	 * @param subject
	 *            the mail subject
	 * @param content
	 *            the mail content
	 */
	void send(@Header("userInfo") UserInfo userInfo, @Header("subject") String subject, String content);
}
