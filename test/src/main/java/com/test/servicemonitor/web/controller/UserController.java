package com.test.servicemonitor.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.servicemonitor.persistance.UserInfo;
import com.test.servicemonitor.persistance.UserInfoService;
import com.test.servicemonitor.web.form.UserForm;

/**
 * Controller of {@link UserInfo} CRUD pages
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends ControllerSupport {

	private static final String ROOT_PATH = "user/main";
	private static final String REDIRECT_ROOT_PATH = "redirect:/user/";
	private static final String EDIT_PATH = "user/edit";
	private static final String ADD_PATH = "user/add";
	private static final String DELETE_PATH = "user/delete";

	@Autowired
	private UserInfoService userInfoService;

	@ModelAttribute("form")
	public UserForm initForm() {
		return new UserForm();
	}

	@RequestMapping("/")
	public String root(ModelMap model) {
		List<UserInfo> users = userInfoService.getAll();
		model.put("users", users);
		return ROOT_PATH;
	}

	@RequestMapping("/add")
	public String add() {
		return ADD_PATH;
	}

	@RequestMapping("/edit")
	public String edit(@Valid @ModelAttribute("form") UserForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
			return REDIRECT_ROOT_PATH;
		}
		UserInfo userInfo = userInfoService.get(form.getUser_id());
		form.fromUserInfo(userInfo);
		return EDIT_PATH;
	}

	@RequestMapping("/delete")
	public String delete(@Valid @ModelAttribute("form") UserForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
			return REDIRECT_ROOT_PATH;
		}
		UserInfo userInfo = userInfoService.get(form.getUser_id());
		form.fromUserInfo(userInfo);
		return DELETE_PATH;
	}

	@RequestMapping("/addSubmit")
	public String addSubmit(@Valid @ModelAttribute("form") UserForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
		} else {
			UserInfo userInfo = form.toUserInfo();
			userInfoService.create(userInfo);
			model.put("msg", "User [" + form.getUser_id() + "] added.");
		}
		return REDIRECT_ROOT_PATH;
	}

	@RequestMapping("/editSubmit")
	public String editSubmit(@Valid @ModelAttribute("form") UserForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
		} else {
			UserInfo userInfo = form.toUserInfo();
			userInfoService.update(userInfo);
			model.put("msg", "User [" + form.getUser_id() + "] edited.");
		}
		return REDIRECT_ROOT_PATH;
	}

	@RequestMapping("/deleteSubmit")
	public String deleteSubmit(@ModelAttribute("form") UserForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
		} else {
			userInfoService.delete(form.getUser_id());
			model.put("msg", "User [" + form.getUser_id() + "] deleted.");
		}
		return REDIRECT_ROOT_PATH;
	}

}
