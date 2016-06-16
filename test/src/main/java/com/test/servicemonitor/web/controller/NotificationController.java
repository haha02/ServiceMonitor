package com.test.servicemonitor.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.servicemonitor.persistance.Notification;
import com.test.servicemonitor.persistance.NotificationService;
import com.test.servicemonitor.persistance.RemoteSystem;
import com.test.servicemonitor.persistance.RemoteSystemService;
import com.test.servicemonitor.persistance.UserGroup;
import com.test.servicemonitor.persistance.UserGroupService;
import com.test.servicemonitor.web.form.NotificationForm;

@Controller
@RequestMapping("/notification")
public class NotificationController {

	private static final String ROOT_PATH = "notification/main";
	private static final String REDIRECT_ROOT_PATH = "redirect:/notification/";
	private static final String EDIT_PATH = "notification/edit";
	private static final String ADD_PATH = "notification/add";
	private static final String DELETE_PATH = "notification/delete";

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private RemoteSystemService remoteSystemService;

	@ModelAttribute("form")
	public NotificationForm initForm() {
		return new NotificationForm();
	}

	@RequestMapping("/")
	public String root(ModelMap model) {
		List<Notification> notifications = notificationService.getAll();
		model.put("notifications", notifications);
		return ROOT_PATH;
	}

	@RequestMapping("/add")
	public String add(ModelMap model) {
		List<RemoteSystem> remoteSystems = remoteSystemService.getAll();
		model.put("remoteSystems", remoteSystems);

		List<UserGroup> userGroups = userGroupService.getAll();
		model.put("userGroups", userGroups);
		return ADD_PATH;
	}

	@RequestMapping("/edit")
	public String edit(@Valid @ModelAttribute("form") NotificationForm form, BindingResult bindingResult,
			ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
			return REDIRECT_ROOT_PATH;
		}
		Notification notification = notificationService.get(form.getSystem_id(), form.getNotify_type(), form.getUser_group());
		form.fromNotification(notification);
		return EDIT_PATH;
	}

	@RequestMapping("/delete")
	public String delete(@Valid @ModelAttribute("form") NotificationForm form, BindingResult bindingResult,
			ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
			return REDIRECT_ROOT_PATH;
		}
		Notification notification = notificationService.get(form.getSystem_id(), form.getNotify_type(), form.getUser_group());
		form.fromNotification(notification);
		return DELETE_PATH;
	}

	@RequestMapping("/addSubmit")
	public String addSubmit(@Valid @ModelAttribute("form") NotificationForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
		} else {
			Notification notification = form.toNotification();
			notificationService.create(notification);
			model.put("msg", "Notification [" + form.getSystem_id() + ", " + form.getNotify_type() + "," + form.getUser_group() + "] added.");
		}
		return REDIRECT_ROOT_PATH;
	}

	@RequestMapping("/editSubmit")
	public String editSubmit(@Valid @ModelAttribute("form") NotificationForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
		} else {
			Notification notification = form.toNotification();
			notificationService.update(notification);
			model.put("msg", "Notification [" + form.getSystem_id() + ", " + form.getNotify_type() + "," + form.getUser_group() + "] edited.");
		}
		return REDIRECT_ROOT_PATH;
	}

	@RequestMapping("/deleteSubmit")
	public String deleteSubmit(@Valid @ModelAttribute("form") NotificationForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
		} else {
			notificationService.delete(form.getSystem_id(), form.getNotify_type(), form.getUser_group());
			model.put("msg", "Notification [" + form.getSystem_id() + ", " + form.getNotify_type() + "," + form.getUser_group() + "] deleted.");
		}
		return REDIRECT_ROOT_PATH;
	}

}
