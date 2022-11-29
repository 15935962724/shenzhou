/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.shenzhou.Message;
import net.shenzhou.Pageable;
import net.shenzhou.Principal;
import net.shenzhou.Setting;
import net.shenzhou.Setting.AccountLockType;
import net.shenzhou.Setting.CaptchaType;
import net.shenzhou.entity.BaseEntity.Save;
import net.shenzhou.entity.Admin;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismRole;
import net.shenzhou.entity.Role;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.User;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismRoleService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.RSAService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.SettingUtils;
import net.shenzhou.util.WebUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 机构用户
 * 2017-06-22 16:40:53
 * @author wsr
 *
 */
@Controller("mechanismUserController")
@RequestMapping("/mechanism/user")
public class UserController extends BaseController {

	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "mechanismRoleServiceImpl")
	private MechanismRoleService mechanismRoleService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	/**
	 * 检查用户名是否被禁用或已存在
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (userService.usernameDisabled(username) || userService.usernameExists(username)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检查E-mail是否存在
	 */
	@RequestMapping(value = "/check_email", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		if (userService.emailExists(email)) {
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String build(ModelMap model) {
		System.out.println("进入注册页面");
		model.addAttribute("genders", Gender.values());
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "mechanism/register";
	}

	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String login(String redirectUrl, HttpServletRequest request, ModelMap model) {
		Setting setting = SettingUtils.get();
		if (redirectUrl != null && !redirectUrl.equalsIgnoreCase(setting.getSiteUrl()) && !redirectUrl.startsWith(request.getContextPath() + "/") && !redirectUrl.startsWith(setting.getSiteUrl() + "/")) {
			redirectUrl = null;
		}
		model.addAttribute("redirectUrl", redirectUrl);
		model.addAttribute("genders", Gender.values());
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		System.out.println("进入登录页面");
		return "mechanism/login";
	}
	
	
	
	
	
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,String name, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();		
		Map<String,Object> query_map = new HashMap<String, Object>();
		pageable.setSearchProperty("name");
		pageable.setSearchValue(name);
		query_map.put("mechanism", mechanism);
		query_map.put("pageable", pageable);
//		query_map.put("nameOrusername", nameOrusername);
		model.addAttribute("page", userService.getMechanismUsers(query_map));
		return "/mechanism/user/list";
	}
	
	
	/**
	 * 添加
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		Mechanism mechanism = mechanismService.getCurrent();
		model.addAttribute("mechanismRoles", mechanism.getMechanismRoles());
		return "/mechanism/user/add";
	}
	
	/**
	 * 
	 * @param admin
	 * @param roleIds
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(User user, Long[] roleIds,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Mechanism mechanism = mechanismService.getCurrent();
		System.out.println(">>>>"+mechanism.getId());
		user.setMechanismroles(new HashSet<MechanismRole>(mechanismRoleService.findList(roleIds)));
//		if (!isValid(user, User.class)) {
//			return ERROR_VIEW;
//		}
		if (userService.usernameExists(user.getUsername())) {
			return ERROR_VIEW;
		}
		user.setIsSystem(false);
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		user.setIsLocked(false);
		user.setLoginFailureCount(0);
		user.setLockedDate(null);
		user.setLoginDate(null);
		user.setLoginIp(null);
		user.setRegisterIp(request.getRemoteAddr());
		user.setIsDeleted(false);
		user.setMechanism(userService.getCurrent().getMechanism());
		userService.save(user);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
//		User user = userService.getCurrent();
//		System.out.println("自身有"+user.getMechanismroles().size()+"个权限");
//		System.out.println("该机构"+mechanismService.getCurrent().getMechanismRoles().size()+"个权限");
		
		model.addAttribute("mechanismRoles", mechanismService.getCurrent().getMechanismRoles());
		model.addAttribute("pUser", userService.find(id));
		return "/mechanism/user/edit";
	}
	
	

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(User user, Long[] roleIds, RedirectAttributes redirectAttributes) {
		user.setMechanismroles(new HashSet<MechanismRole>(mechanismRoleService.findList(roleIds)));
		if (!isValid(user)) {
			return ERROR_VIEW;
		}
		User pUser = userService.find(user.getId());
		if (pUser == null) {
			return ERROR_VIEW;
		}
		if (StringUtils.isNotEmpty(user.getPassword())) {
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		} else {
			user.setPassword(pUser.getPassword());
		}
		
		if (user.getIsLocked()==null?false:user.getIsLocked()) {
			user.setLoginFailureCount(pUser.getLoginFailureCount());
			user.setLockedDate(new Date());
		}else{
			user.setIsLocked(false);
			user.setLoginFailureCount(0);
			user.setLockedDate(null);
		}
		
		user.setIsEnabled(user.getIsEnabled()==null?false:user.getIsEnabled());
		
		
		userService.update(user, "username", "loginDate", "loginIp","isDeleted","mechanism");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	

}