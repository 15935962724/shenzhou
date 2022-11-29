/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.Setting;
import net.shenzhou.controller.shop.BaseController;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Member;
import net.shenzhou.service.DoctorService;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 机构端 - 密码
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("mechanismDoctorPasswordController")
@RequestMapping("/mechanism/password")
public class PasswordController extends BaseController {

	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;

	/**
	 * 验证当前密码
	 */
	@RequestMapping(value = "/check_current_password", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkCurrentPassword(String currentPassword) {
		if (StringUtils.isEmpty(currentPassword)) {
			return false;
		}
		Doctor doctor = doctorService.getCurrent();
		if (StringUtils.equals(DigestUtils.md5Hex(currentPassword), doctor.getPassword())) {
			return true;
		} else {
			return false;
		}
	}

	

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(String currentPassword, String password, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (StringUtils.isEmpty(password) || StringUtils.isEmpty(currentPassword)) {
			return ERROR_VIEW;
		}
		if (!isValid(Doctor.class, "password", password)) {
			return ERROR_VIEW;
		}
		Setting setting = SettingUtils.get();
		if (password.length() < setting.getPasswordMinLength() || password.length() > setting.getPasswordMaxLength()) {
			return ERROR_VIEW;
		}
		Doctor doctor = doctorService.getCurrent();
		if (!StringUtils.equals(DigestUtils.md5Hex(currentPassword), doctor.getPassword())) {
			return ERROR_VIEW;
		}
		doctor.setPassword(DigestUtils.md5Hex(password));
		doctorService.update(doctor);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/mechanism/common/main.jhtml";
	}

}