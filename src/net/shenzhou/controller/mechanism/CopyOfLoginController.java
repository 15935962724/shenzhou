/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shenzhou.Message;
import net.shenzhou.Pageable;
import net.shenzhou.Principal;
import net.shenzhou.Setting;
import net.shenzhou.Setting.AccountLockType;
import net.shenzhou.Setting.CaptchaType;
import net.shenzhou.entity.BaseEntity.Save;
import net.shenzhou.entity.Admin;
import net.shenzhou.entity.Cart;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Feedback;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismRole;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Role;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.User;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.FeedbackService;
import net.shenzhou.service.MechanismRoleService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.RSAService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MapUtil;
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
@Controller("userController")
@RequestMapping("/user")
public class CopyOfLoginController extends BaseController {

	
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
	@Resource(name = "feedbackServiceImpl")
	private FeedbackService feedbackService;
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
		System.out.println("进入注册页面1");
		model.addAttribute("genders", Gender.values());
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "mechanism/register";
	}

	/**
	 * 登录页面
	 * @param redirectUrl
	 * @param request
	 * @param model
	 * @return
	 */
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
	 * 注册提交
	 * @param captchaId
	 * @param captcha
	 * @param username
	 * @param email
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(String captchaId, String captcha, String username, String email, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String password = rsaService.decryptParameter("enPassword", request);
		rsaService.removePrivateKey(request);

		if (!captchaService.isValid(CaptchaType.doctorRegister, captchaId, captcha)) {
			return Message.error("shop.captcha.invalid");
		}
		Setting setting = SettingUtils.get();
		if (!setting.getIsRegisterEnabled()) {
			return Message.error("shop.register.disabled");
		}
		if (!isValid(User.class, "username", username, Save.class) || !isValid(User.class, "password", password, Save.class) || !isValid(User.class, "email", email, Save.class)) {
			return Message.error("shop.common.invalid");
		}
		if (username.length() < setting.getUsernameMinLength() || username.length() > setting.getUsernameMaxLength()) {
			return Message.error("shop.common.invalid");
		}
		if (password.length() < setting.getPasswordMinLength() || password.length() > setting.getPasswordMaxLength()) {
			return Message.error("shop.common.invalid");
		}
		if (userService.usernameDisabled(username) || userService.usernameExists(username)) {
			return Message.error("shop.register.disabledExist");
		}
		if (!setting.getIsDuplicateEmail() && userService.emailExists(email)) {
			return Message.error("shop.register.emailExist");
		}

		User user = new User();
		
		user.setUsername(username.toLowerCase());
		user.setPassword(DigestUtils.md5Hex(password));
		user.setEmail(email);
		user.setIsEnabled(true);
		user.setIsLocked(false);
		user.setLoginFailureCount(0);
		user.setLockedDate(null);
		user.setRegisterIp(request.getRemoteAddr());
		user.setLoginIp(request.getRemoteAddr());
		user.setLoginDate(new Date());
		user.setIsSystem(true);
		user.setIsDeleted(false);
		userService.save(user);
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		Enumeration<?> keys = session.getAttributeNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			attributes.put(key, session.getAttribute(key));
		}
		session.invalidate();
		session = request.getSession();
		for (Entry<String, Object> entry : attributes.entrySet()) {
			session.setAttribute(entry.getKey(), entry.getValue());
		}

		session.setAttribute(User.PRINCIPAL_ATTRIBUTE_NAME, new Principal(user.getId(), user.getUsername()));
		WebUtils.addCookie(request, response, User.USERNAME_COOKIE_NAME, user.getUsername());
		
		return Message.success("shop.register.success");
	}
	
	
	/**
	 * 登录提交
	 */
//	@RequestMapping(value = "/submit", method = RequestMethod.POST)
//	public @ResponseBody
//	Message submit(String captchaId, String captcha, String username, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		String password = rsaService.decryptParameter("enPassword", request);
//		rsaService.removePrivateKey(request);
//
//		if (!captchaService.isValid(CaptchaType.userLogin, captchaId, captcha)) {
//			return Message.error("shop.captcha.invalid");
//		}
//		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
//			return Message.error("用户名或密码不能为空");
//		}
//		User user;
//		Setting setting = SettingUtils.get();
//		
//		user = userService.findByUsername(username);
//		if (user == null) {
//			return Message.error("shop.login.unknownAccount");
//		}
//		if (!user.getIsEnabled()) {
//			return Message.error("shop.login.disabledAccount");
//		}
//		if (user.getIsLocked()) {
//			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.user)) {
//				int loginFailureLockTime = setting.getAccountLockTime();
//				if (loginFailureLockTime == 0) {
//					return Message.error("shop.login.lockedAccount");
//				}
//				Date lockedDate = user.getLockedDate();
//				Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
//				if (new Date().after(unlockDate)) {
//					user.setLoginFailureCount(0);
//					user.setIsLocked(false);
//					user.setLockedDate(null);
//					userService.update(user);
//				} else {
//					return Message.error("shop.login.lockedAccount");
//				}
//			} else {
//				user.setLoginFailureCount(0);
//				user.setIsLocked(false);
//				user.setLockedDate(null);
//				userService.update(user);
//			}
//		}
//
//		if (!DigestUtils.md5Hex(password).equals(user.getPassword())) {
//			int loginFailureCount = user.getLoginFailureCount() + 1;
//			if (loginFailureCount >= setting.getAccountLockCount()) {
//				user.setIsLocked(true);
//				user.setLockedDate(new Date());
//			}
//			user.setLoginFailureCount(loginFailureCount);
//			userService.update(user);
//			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.user)) {
//				return Message.error("shop.login.accountLockCount", setting.getAccountLockCount());
//			} else {
//				return Message.error("shop.login.incorrectCredentials");
//			}
//		}
//		user.setLoginIp(request.getRemoteAddr());
//		user.setLoginDate(new Date());
//		user.setLoginFailureCount(0);
//		userService.update(user);
//
//
//		Map<String, Object> attributes = new HashMap<String, Object>();
//		Enumeration<?> keys = session.getAttributeNames();
//		while (keys.hasMoreElements()) {
//			String key = (String) keys.nextElement();
//			attributes.put(key, session.getAttribute(key));
//		}
//		session.invalidate();
//		session = request.getSession();
//		for (Entry<String, Object> entry : attributes.entrySet()) {
//			session.setAttribute(entry.getKey(), entry.getValue());
//		}
//
//		session.setAttribute(User.PRINCIPAL_ATTRIBUTE_NAME, new Principal(user.getId(), username));
//		WebUtils.addCookie(request, response, User.USERNAME_COOKIE_NAME, user.getUsername());
//
//		List<String>authorities = userService.findAuthorities(user.getId());
//		System.out.println("拥有"+authorities.size()+"个权限");
//		return SUCCESS_MESSAGE;
//	}
	
	/**
	 * 登录提交
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public @ResponseBody
	Message submit(String captchaId, String captcha, String username, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String password = rsaService.decryptParameter("enPassword", request);
		rsaService.removePrivateKey(request);

		if (!captchaService.isValid(CaptchaType.doctorLogin, captchaId, captcha)) {
			return Message.error("shop.captcha.invalid");
		}
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return Message.error("用户名或密码不能为空");
		}
		Doctor doctor;
		Setting setting = SettingUtils.get();
		
		doctor = doctorService.findByUsername(username);
		if (doctor == null) {
			return Message.error("shop.login.unknownAccount");
		}
//		if (!doctor.getIsEnabled()) {
//			return Message.error("shop.login.disabledAccount");
//		}
		if (doctor.getIsLocked()) {
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.doctor)) {
				int loginFailureLockTime = setting.getAccountLockTime();
				if (loginFailureLockTime == 0) {
					return Message.error("shop.login.lockedAccount");
				}
				Date lockedDate = doctor.getLockedDate();
				Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
				if (new Date().after(unlockDate)) {
					doctor.setLoginFailureCount(0);
					doctor.setIsLocked(false);
					doctor.setLockedDate(null);
					doctorService.update(doctor);
				} else {
					return Message.error("shop.login.lockedAccount");
				}
			} else {
				doctor.setLoginFailureCount(0);
				doctor.setIsLocked(false);
				doctor.setLockedDate(null);
				doctorService.update(doctor);
			}
		}

		if (!DigestUtils.md5Hex(password).equals(doctor.getPassword())) {
			int loginFailureCount = doctor.getLoginFailureCount() + 1;
			if (loginFailureCount >= setting.getAccountLockCount()) {
				doctor.setIsLocked(true);
				doctor.setLockedDate(new Date());
			}
			doctor.setLoginFailureCount(loginFailureCount);
			doctorService.update(doctor);
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.doctor)) {
				return Message.error("shop.login.accountLockCount", setting.getAccountLockCount());
			} else {
				return Message.error("shop.login.incorrectCredentials");
			}
		}
		doctor.setLoginIp(request.getRemoteAddr());
		doctor.setLoginDate(new Date());
		doctor.setLoginFailureCount(0);
		doctorService.update(doctor);


		Map<String, Object> attributes = new HashMap<String, Object>();
		Enumeration<?> keys = session.getAttributeNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			attributes.put(key, session.getAttribute(key));
		}
		session.invalidate();
		session = request.getSession();
		for (Entry<String, Object> entry : attributes.entrySet()) {
			session.setAttribute(entry.getKey(), entry.getValue());
		}

		session.setAttribute(Doctor.PRINCIPAL_ATTRIBUTE_NAME, new Principal(doctor.getId(), username));
		WebUtils.addCookie(request, response, Doctor.USERNAME_COOKIE_NAME, doctor.getUsername());

//		List<String>authorities = userService.findAuthorities(doctor.getId());
//		System.out.println("拥有"+authorities.size()+"个权限");
		return SUCCESS_MESSAGE;
	}
	
	
	/**
	 * 注销
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		session.removeAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
		WebUtils.removeCookie(request, response, User.USERNAME_COOKIE_NAME);
		return "redirect:login.jhtml";
	}
	
	
	/**
	 * 进入页面获取    程可可    坐标
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		System.out.println("进入万德页面");
		return "mechanism/index";
	}
	
	/**
	 * 
	 * @param captchaId
	 * @param captcha
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/address", method = RequestMethod.POST)
	public @ResponseBody
	Message address(String lat ,String lng, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			String data = MapUtil.getAddress(lat, lng);
			    
			data = data.substring(29,data.length()-1);
			JSONObject json = JsonUtils.toJSONObject(data);
			if (json.getString("status").equals("0")) {
				JSONObject result = JsonUtils.toJSONObject(json.getString("result"));
				String location = result.getString("location");
				
				String formatted_address = result.getString("formatted_address");
				String business = result.getString("business");
				Feedback feedback1 = new Feedback();
				feedback1.setName("程可可1");
				feedback1.setPhone("130432200009031726");
				feedback1.setIp(request.getRemoteAddr());
				feedback1.setContent("坐标:"+location+",机构化地址:"+formatted_address+",所在商圈:"+business);
				feedback1.setIsAnonymous(false);
		        feedbackService.save(feedback1);
				String addressComponent = result.getString("addressComponent");
				Feedback feedback2 = new Feedback();
				feedback2.setName("程可可2");
				feedback2.setPhone("130432200009031726");
				feedback2.setIp(request.getRemoteAddr());
				feedback2.setContent("真实所在地址:"+addressComponent);
				feedback2.setIsAnonymous(false);
		        feedbackService.save(feedback2);
				String pois = result.getString("pois");
				JSONArray jsonarray = JSONArray.fromObject(pois);
				for (int i = 0; i < jsonarray.size(); i++) {
					Feedback feedback = new Feedback();
					feedback.setName("程可可"+(i+3));
					feedback.setPhone("130432200009031726");
					feedback.setIp(request.getRemoteAddr());
					JSONObject poi = JsonUtils.toJSONObject(jsonarray.get(i).toString());
					feedback.setContent("周边地址:"+poi.getString("addr")+",方向:"+poi.getString("direction"));
					feedback.setIsAnonymous(false);
			        feedbackService.save(feedback);
				}
//				String roads = json.getString("roads");
//				String poiRegions = json.getString("poiRegions");
				String sematic_description = result.getString("sematic_description");
				String cityCode = result.getString("cityCode");
				Feedback feedback3 = new Feedback();
				feedback3.setName("程可可"+(jsonarray.size()+3));
				feedback3.setPhone("130432200009031726");
				feedback3.setIp(request.getRemoteAddr());
				feedback3.setContent("周边地址:"+sematic_description+",编码:"+cityCode);
				feedback3.setIsAnonymous(false);
		        feedbackService.save(feedback3);
		        System.out.println("解析完毕");
			}else{
				System.out.println("数据错误");
			}
			
			String realPath = request.getSession().
			                getServletContext().getRealPath("/");
			File writename = new File(realPath+"\\数据结果.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
	           writename.createNewFile(); // 创建新文件  
	           BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
	           out.write("数据结果:"+data); // \r\n即为换行  
	           out.flush(); // 把缓存区内容压入文件  
	           out.close(); // 最后记得关闭文件  
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return SUCCESS_MESSAGE;
	}
	
}