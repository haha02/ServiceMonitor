package com.test.servicemonitor.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.test.servicemonitor.persistance.UserInfo;
import com.test.servicemonitor.persistance.UserInfoService;
import com.test.servicemonitor.web.form.UserForm;

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

	@Override
	protected void initBinder(DataBinder binder) {
		super.initBinder(binder);
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
	public String edit(@ModelAttribute("form") UserForm form, ModelMap model) {
		UserInfo userInfo = form.getUserInfo();
		userInfo = userInfoService.get(userInfo.getUser_id());
		form.setUserInfo(userInfo);
		return EDIT_PATH;
	}

	@RequestMapping("/delete")
	public String delete(@ModelAttribute("form") UserForm form, BindingResult bindingResult, ModelMap model) {
		UserInfo userInfo = form.getUserInfo();
		userInfo = userInfoService.get(userInfo.getUser_id());
		form.setUserInfo(userInfo);
		return DELETE_PATH;
	}

	@RequestMapping("/addSubmit")
	public String addSubmit(@ModelAttribute("form") UserForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
		} else {
			UserInfo userInfo = form.getUserInfo();
			userInfoService.save(userInfo);
			model.put("msg", "User [" + userInfo.getUser_id() + "] added.");
		}
		return REDIRECT_ROOT_PATH;
	}

	@RequestMapping("/editSubmit")
	public String editSubmit(@ModelAttribute("form") UserForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
		} else {
			UserInfo userInfo = form.getUserInfo();
			userInfoService.update(userInfo);
			model.put("msg", "User [" + userInfo.getUser_id() + "] edited.");
		}
		return REDIRECT_ROOT_PATH;
	}

	@RequestMapping("/deleteSubmit")
	public String deleteSubmit(@ModelAttribute("form") UserForm form, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", bindingResult.getAllErrors());
		} else {
			UserInfo userInfo = form.getUserInfo();
			userInfoService.delete(userInfo.getUser_id());
			model.put("msg", "User [" + userInfo.getUser_id() + "] deleted.");
		}
		return REDIRECT_ROOT_PATH;
	}

	@Override
	protected Validator[] getValidators() {
		Validator validator = new Validator() {
			@Override
			public void validate(Object target, Errors errors) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userInfo.user_id", "user_id.missing");
			}
			@Override
			public boolean supports(Class<?> clazz) {
				return UserForm.class.isAssignableFrom(clazz);
			}
		};
		return new Validator[] { validator };
	}

}
