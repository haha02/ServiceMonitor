package com.test.servicemonitor.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.servicemonitor.persistance.UserGroup;
import com.test.servicemonitor.persistance.UserGroupService;
import com.test.servicemonitor.persistance.UserInfo;
import com.test.servicemonitor.persistance.UserInfoService;
import com.test.servicemonitor.web.form.UserGroupForm;

@Controller
@RequestMapping("/userGroup")
public class UserGroupController extends ControllerSupport {
	private static final String ROOT_PATH = "userGroup/main";
	private static final String REDIRECT_ROOT_PATH = "redirect:/userGroup/";
	private static final String EDIT_PATH = "userGroup/edit";
	private static final String ADD_PATH = "userGroup/add";
	private static final String DELETE_PATH = "userGroup/delete";

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private UserInfoService userInfoService;

	@ModelAttribute("form")
	public UserGroupForm initForm() {
		return new UserGroupForm();
	}

	@RequestMapping("/")
	public String root(ModelMap model) {
		List<UserGroup> userGroups = userGroupService.getAll();
		model.put("userGroups", userGroups);
		return ROOT_PATH;
	}
	
	@RequestMapping("/add")
	public String add(ModelMap model) {
		List<UserInfo> userInfos = userInfoService.getAll();
		model.put("userInfos", userInfos);
		return ADD_PATH;
	}

	@RequestMapping("/edit")
	public String edit(@Valid @ModelAttribute("form") UserGroupForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", toErrorMsg(bindingResult.getAllErrors()));
			return REDIRECT_ROOT_PATH;
		}
		UserGroup userGroup = userGroupService.get(form.getGroup_id());
		form.fromUserGroup(userGroup);
		
		List<UserInfo> userInfos = userInfoService.getAll();
		model.put("userInfos", userInfos);
		return EDIT_PATH;
	}

	@RequestMapping("/delete")
	public String delete(@Valid @ModelAttribute("form") UserGroupForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", toErrorMsg(bindingResult.getAllErrors()));
			return REDIRECT_ROOT_PATH;
		}
		UserGroup userGroup = userGroupService.get(form.getGroup_id());
		form.fromUserGroup(userGroup);
		return DELETE_PATH;
	}

	@RequestMapping("/addSubmit")
	public String addSubmit(@Valid @ModelAttribute("form") UserGroupForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
		} else {
			UserGroup userGroup = getUserGroupForPersistance(form);
			userGroupService.create(userGroup);
			model.put("msg", "User [" + form.getGroup_id() + "] added.");
		}
		return REDIRECT_ROOT_PATH;
	}

	@RequestMapping("/editSubmit")
	public String editSubmit(@Valid @ModelAttribute("form") UserGroupForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
		} else {
			UserGroup userGroup = getUserGroupForPersistance(form);
			userGroupService.update(userGroup);
			model.put("msg", "User [" + form.getGroup_id() + "] edited.");
		}
		return REDIRECT_ROOT_PATH;
	}

	@RequestMapping("/deleteSubmit")
	public String deleteSubmit(@ModelAttribute("form") UserGroupForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
		} else {
			userInfoService.delete(form.getGroup_id());
			model.put("msg", "User [" + form.getGroup_id() + "] deleted.");
		}
		return REDIRECT_ROOT_PATH;
	}

	private UserGroup getUserGroupForPersistance(UserGroupForm form) {
		UserGroup userGroup = form.toUserGroup();
		List<String> userIds = form.getUserIds();
		if (userIds != null) {
			List<UserInfo> users = userInfoService.getByIds(userIds);
			userGroup.setUsers(users);
		}
		return userGroup;
	}
}
