package com.test.servicemonitor.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.test.servicemonitor.main.SystemConfig;

/**
 * 
 * Abstract controller support.
 * <p>
 * Provides default binder setting, reference of logger and system
 * configuration, and methods to get request/session/servlet context.
 *
 */
public abstract class ControllerSupport {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected SystemConfig systemConfig;

	@InitBinder
	protected void initBinder(DataBinder binder) {
		// class ValueOfInvokingPropertyEditorSupport extends
		// PropertyEditorSupport {
		// private Class<?> clazz;
		//
		// ValueOfInvokingPropertyEditorSupport(Class<?> clazz) {
		// this.clazz = clazz;
		// }
		//
		// @Override
		// public String getAsText() {
		// Object value = getValue();
		// return (value == null) ? "" : value.toString();
		// }
		//
		// @Override
		// public void setAsText(String text) throws IllegalArgumentException {
		// if (StringUtils.isEmpty(text)) {
		// return;
		// }
		// try {
		// setValue(clazz.getMethod("valueOf", String.class).invoke(null,
		// text));
		// } catch (Exception e) {
		// setValue(null);
		// }
		// }
		// }
		// binder.registerCustomEditor(Date.class, new
		// ValueOfInvokingPropertyEditorSupport(Date.class));
		// binder.registerCustomEditor(Timestamp.class, new
		// ValueOfInvokingPropertyEditorSupport(Timestamp.class));
		binder.setAutoGrowCollectionLimit(1000);
		Validator validator = getValidator();
		if (validator != null) {
			binder.setValidator(validator);
		}
	}

	/**
	 * Acquire current request
	 * 
	 * @return request
	 */
	protected final HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * Acquire current session
	 * 
	 * @return session
	 */
	protected final HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * Acquire servlet context
	 * 
	 * @return servlet context
	 */
	protected final ServletContext getServletContext() {
		return getRequest().getServletContext();
	}

	/**
	 * Overrride this method to provide {@link Validator} implementations to be
	 * used by {@link DataBinder}, if any.
	 * <p>
	 * The default implementation of this method simply returns {@code null}.
	 * 
	 * @return valiators, if the returned array is {@code null} or empty, it is
	 *         ignored.
	 */
	protected Validator getValidator() {
		return null;
	}

	protected String toErrorMsg(List<ObjectError> list) {
		StringBuilder sb = new StringBuilder();
		for (ObjectError oe : list) {
			sb.append(Arrays.asList(oe.getCodes())).append(oe.getDefaultMessage()).append(";<br />");
		}
		return sb.toString();
	}
}
