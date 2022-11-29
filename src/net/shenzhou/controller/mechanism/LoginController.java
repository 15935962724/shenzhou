/*

 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shenzhou.Message;
import net.shenzhou.Principal;
import net.shenzhou.Setting;
import net.shenzhou.Setting.AccountLockType;
import net.shenzhou.Setting.CaptchaType;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Feedback;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismSetup;
import net.shenzhou.entity.MechanismSetup.AchievementsType;
import net.shenzhou.entity.MechanismSetup.ChargeType;
import net.shenzhou.entity.MechanismSetup.OrderType;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.FeedbackService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MechanismSetupService;
import net.shenzhou.service.RSAService;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MapUtil;
import net.shenzhou.util.SettingUtils;
import net.shenzhou.util.SpringUtils;
import net.shenzhou.util.WebUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
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
 * 机构用户(医生)
 * 2017-12-20 17:45:51
 * @author wsr
 *
 */
@Controller("mechanismLoginController")
@RequestMapping("/mechanismLogin")
public class LoginController extends BaseController {

	
//	@Resource(name = "userServiceImpl")
//	private UserService userService;
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "feedbackServiceImpl")
	private FeedbackService feedbackService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	@Resource(name = "mechanismSetupServiceImpl")
	private MechanismSetupService mechanismSetupService;
	
	

	/**
	 * 登录检测
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public @ResponseBody
	Boolean check() {
		return doctorService.isAuthenticated();
	}


	/**
	 * 登录页面
	 * @param redirectUrl
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="index",method = RequestMethod.GET)
	public String index(String redirectUrl, HttpServletRequest request, ModelMap model) {
		Setting setting = SettingUtils.get();
		if (redirectUrl != null && !redirectUrl.equalsIgnoreCase(setting.getSiteUrl()) && !redirectUrl.startsWith(request.getContextPath() + "/") && !redirectUrl.startsWith(setting.getSiteUrl() + "/")) {
			redirectUrl = null;
		}
		model.addAttribute("redirectUrl", redirectUrl);
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		System.out.println("进入登录页面");
		return "mechanism/login/index";
	}
	
//	@RequestMapping(method = RequestMethod.GET)
//	public String index(String redirectUrl, HttpServletRequest request, ModelMap model) {
//		Setting setting = SettingUtils.get();
//		if (redirectUrl != null && !redirectUrl.equalsIgnoreCase(setting.getSiteUrl()) && !redirectUrl.startsWith(request.getContextPath() + "/") && !redirectUrl.startsWith(setting.getSiteUrl() + "/")) {
//			redirectUrl = null;
//		}
//		model.addAttribute("redirectUrl", redirectUrl);
//		model.addAttribute("captchaId", UUID.randomUUID().toString());
//		System.out.println("进入登录页面");
//		return "mechanism/login/index";
//	}
	
	
	
	
	/**
	 * 登录提交
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, String>  submit(String captchaId, String captcha, String username, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String password = rsaService.decryptParameter("enPassword", request);
		rsaService.removePrivateKey(request);
		Map<String, String> data = new HashMap<String, String>();
		
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			data.put("status","400");
			data.put("message", "用户名或密码不能为空");
			return data;
			
		}
		
		Setting setting = SettingUtils.get();
		
		Doctor doctor = doctorService.findByUsername(username);
		if (doctor == null) {
			data.put("status","400");
			data.put("message", "此账号不存在");
			return data;
		}
//		if (!doctor.getIsEnabled()) {
//			return Message.error("shop.login.disabledAccount");
//		}
		if (doctor.getIsLocked()) {
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.doctor)) {
				int loginFailureLockTime = setting.getAccountLockTime();
				if (loginFailureLockTime == 0) {
					data.put("status","400");
					data.put("message", "此账号已被锁定");
					return data;
				}
				Date lockedDate = doctor.getLockedDate();
				Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
				if (new Date().after(unlockDate)) {
					doctor.setLoginFailureCount(0);
					doctor.setIsLocked(false);
					doctor.setLockedDate(null);
					doctorService.update(doctor);
				} else {
					data.put("status","400");
					data.put("message", "此账号已被锁定");
					return data;
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
				data.put("status","400");
				data.put("message","密码错误，若连续"+setting.getAccountLockCount()+"次密码错误账号将被锁定" );
				return data;
			} else {
				data.put("status","400");
				data.put("message", "用户名或密码输入有误");
				return data;
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
//		WebUtils.addCookie(request, response, Doctor.USERNAME_COOKIE_NAME, doctor.getUsername());
		
//		session.setAttribute(Doctor.PRINCIPAL_ATTRIBUTE_NAME, new Principal(doctor.getId(), doctor.getName()));
		WebUtils.addCookie(request, response, "name", doctor.getName());
		
		data.put("status","200");
		data.put("message", "登录成功");
		return data;
	}
	
	/**
	 * 登陆完进入筛选机构页面
	 */
	@RequestMapping(value = "/switch", method = RequestMethod.GET)
	public String switchIndex(ModelMap model,HttpServletRequest request, HttpServletResponse response, HttpSession session,RedirectAttributes redirectAttributes) {
		Doctor doctor = doctorService.getCurrent();
		List<DoctorMechanismRelation> doctorMechanismRelations = doctor.getDoctorMechanismRelations(Audit.succeed);
		
		if (doctorMechanismRelations.size()<=0) {//如果该医生没有加入任何机构就注销，到登录页面(清空session)
			session.removeAttribute(Doctor.PRINCIPAL_ATTRIBUTE_NAME);
			WebUtils.removeCookie(request, response, Doctor.USERNAME_COOKIE_NAME);
			addFlashMessage(redirectAttributes, Message.error("你还没有加入任何机构,请在APP端申请加入机构"));
			System.out.println("你还没有加入任何机构,请在APP端申请加入机构");
			return "mechanism/login/index";
//			return "redirect:logout.jhtml";
		}
		
		if (doctorMechanismRelations.size()==1) {//如果是机构管理员登录直接跳到机构管理主页面去
			DoctorMechanismRelation doctorMechanismRelation = doctorMechanismRelations.get(0);
			if (doctorMechanismRelation.getIsSystem()) {
				Mechanism mechanism = doctorMechanismRelation.getMechanism();
//				try {
//					System.out.println(mechanism.getMechanismSetup());
//				} catch (Exception e) {
//					// TODO: handle exception
//					System.out.println("一对一关系出错"+e.getMessage());
//				}
				if (mechanism.getMechanismSetup()==null) {
					MechanismSetup mechanismSetup = new MechanismSetup();
					mechanismSetup.setIsDeleted(false);
					mechanismSetup.setMaxday(5);
					mechanismSetup.setMaxdayRemark("患者端可预约本机构的最大预约天数");
					mechanismSetup.setOrderType(OrderType.automatic);
					mechanismSetup.setOrderTypeRemark("自动：机构自动接单，无须医师接单；手动：医师须手动承接患者下单");
					mechanismSetup.setChargeType(ChargeType.course);
					mechanismSetup.setChargeTypeRemark("课节收费标准:不到一节课按一节课收费，不到半节课按半节课收费;分钟收费标准:课节费用X实际实际(分钟)/一节课时(分钟)");
					mechanismSetup.setAchievementsType(AchievementsType.fixedProportion);
					mechanismSetup.setMechanismProportion(new BigDecimal(100));
					mechanismSetup.setDoctorProportion(new BigDecimal(0));
					mechanismSetup.setReduceMoney(new BigDecimal(200));
					mechanismSetup.setReduceProportion(new BigDecimal(0.5));
					mechanismSetup.setMechanism(doctorMechanismRelation.getMechanism());
					mechanismSetupService.save(mechanismSetup);
				}
				
				WebUtils.addCookie(request, response, "mechanismName", mechanism.getName());
				
				return "redirect:/mechanism/common/main.jhtml";
			}
			
		}
		
		
		model.addAttribute("doctorMechanismRelations", doctorMechanismRelations);
		System.out.println("登录成功,进入选择机构页面");
		return "mechanism/login/switch";
	}
	
	/**
	 * 选择完机构进入首页
	 * @param model
	 * @param mechanismId
	 * @return
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(ModelMap model,Long mechanismId,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanismC = mechanismService.find(mechanismId);
		
		//先把该医生加入的所有默认机构置为false
		for (DoctorMechanismRelation doctorMechanismRelation : doctorC.getDoctorMechanismRelations()) {
			doctorMechanismRelation.setDefaultMechanism(false);
			doctorMechanismRelationService.update(doctorMechanismRelation);
		}
		
		//在把该医生登陆后选择的机构置为true
		DoctorMechanismRelation doctorMechanismRelation  = doctorC.getDoctorMechanismRelation(mechanismC);
		if (!doctorMechanismRelation.getIsEnabled()) {
			addFlashMessage(redirectAttributes, Message.error("你的账号在"+doctorMechanismRelation.getMechanism().getName()+"下已停用"));
			System.out.println("你的账号在"+doctorMechanismRelation.getMechanism().getName()+"下已停用，请重新选择");
			return "redirect:/mechanism/login/switch.jhtml";
			
		}
		doctorMechanismRelation.setDefaultMechanism(true);
		doctorMechanismRelationService.update(doctorMechanismRelation);
		
		
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		
		if (mechanism.getMechanismSetup()==null) {
			MechanismSetup mechanismSetup = new MechanismSetup();
			mechanismSetup.setIsDeleted(false);
			mechanismSetup.setMaxday(5);
			mechanismSetup.setMaxdayRemark("患者端可预约本机构的最大预约天数");
			mechanismSetup.setOrderType(OrderType.automatic);
			mechanismSetup.setOrderTypeRemark("自动：机构自动接单，无须医师接单；手动：医师须手动承接患者下单");
			mechanismSetup.setChargeType(ChargeType.course);
			mechanismSetup.setChargeTypeRemark("课节收费标准:不到一节课按一节课收费，不到半节课按半节课收费;分钟收费标准:课节费用X实际实际(分钟)/一节课时(分钟)");
			mechanismSetup.setAchievementsType(AchievementsType.fixedProportion);
			mechanismSetup.setMechanismProportion(new BigDecimal(100));
			mechanismSetup.setDoctorProportion(new BigDecimal(0));
			mechanismSetup.setReduceMoney(new BigDecimal(200));
			mechanismSetup.setReduceProportion(new BigDecimal(0.5));
			mechanismSetup.setMechanism(mechanism);
			mechanismSetupService.save(mechanismSetup);
		}
		
		System.out.println("登录成功,重定向进入主页面");
		WebUtils.addCookie(request, response, "mechanismName", mechanism.getName());
		return "redirect:/mechanism/common/main.jhtml";
	}
	
	
	/**
	 * 注销
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		session.removeAttribute(Doctor.PRINCIPAL_ATTRIBUTE_NAME);
		WebUtils.removeCookie(request, response, Doctor.USERNAME_COOKIE_NAME);
		return "redirect:index.jhtml";
	}
	
	/**
	 * 忘记密码
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/find1", method = RequestMethod.GET)
	public String findPassword1(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		System.out.println("进入忘记密码页面1");
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "mechanism/password/find1";
	}
	
	
	/**
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/find2", method = RequestMethod.POST)
	public String findPassword2(String username,RedirectAttributes redirectAttributes, ModelMap model) {
		System.out.println("进入忘记密码页面2");
		model.addAttribute("username", username);
		return "mechanism/password/find2";
	}
	
	/**
	 * 修改密码成功页
	 * @param username
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/find3", method = RequestMethod.POST)
	public String findPassword3(String username,String password, ModelMap model) {
		System.out.println("进入忘记密码页面3");
		Doctor doctor = doctorService.findByUsername(username);
		doctor.setPassword(DigestUtils.md5Hex(password));
		doctorService.update(doctor);
		System.out.println("密码修改成功");
		return "mechanism/password/find3";
	}
	
	
	/**
	 * 验证图片验证码
	 * @param request
	 * @param response
	 * @param captcha
	 * @param captchaId
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/identifyingCaptcha", method = RequestMethod.POST)
	public @ResponseBody
	Boolean identifying(HttpServletRequest request,
			HttpServletResponse response, String captcha,String captchaId, HttpSession session)
			throws IOException {
		System.out.println("验证图片验证码");
		if (!captchaService.isValid(CaptchaType.findPassword, captchaId, captcha)) {
			return false;
		}
		return true;

	}
	
	
	/**
	 * 验证该手机号是否在平台注册
	 * @param request
	 * @param response
	 * @param username
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/identifyingUsername", method = RequestMethod.POST)
	public @ResponseBody
	Boolean identifyingUsername(HttpServletRequest request,
			HttpServletResponse response, String username, HttpSession session)
			throws IOException {
		Doctor doctor = doctorService.findByUsername(username);
		if (doctor==null) {
			return false;
		}
		return true;
	}


	
	
	/**
	 * 验证码
	 * @param captchaId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void image(String captchaId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (StringUtils.isEmpty(captchaId)) {
			captchaId = request.getSession().getId();
		}
		String pragma = new StringBuffer().append("yB").append("-").append("der").append("ewoP").reverse().toString();
		String value = new StringBuffer().append("ten").append(".").append("xxp").append("ohs").reverse().toString();
		response.addHeader(pragma, value);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();
			BufferedImage bufferedImage = captchaService.buildImage(captchaId);
			ImageIO.write(bufferedImage, "jpg", servletOutputStream);
			servletOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(servletOutputStream);
		}
	}
	
	/**
	 * 进入页面获取    坐标
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
//	@RequestMapping(value = "/index", method = RequestMethod.GET)
//	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		System.out.println("进入万德页面");
//		return "mechanism/index";
//	}
	
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
				feedback1.setName("Ckk1");
				feedback1.setPhone("130432200009031726");
				feedback1.setIp(request.getRemoteAddr());
				feedback1.setContent("坐标:"+location+",结构化地址:"+formatted_address+",所在商圈:"+business);
				feedback1.setIsAnonymous(false);
		        feedbackService.save(feedback1);
				String addressComponent = result.getString("addressComponent");
				Feedback feedback2 = new Feedback();
				feedback2.setName("Ckk2");
				feedback2.setPhone("130432200009031726");
				feedback2.setIp(request.getRemoteAddr());
				feedback2.setContent("真实所在地址:"+addressComponent);
				feedback2.setIsAnonymous(false);
		        feedbackService.save(feedback2);
				String pois = result.getString("pois");
				JSONArray jsonarray = JSONArray.fromObject(pois);
				for (int i = 0; i < jsonarray.size(); i++) {
					Feedback feedback = new Feedback();
					feedback.setName("Ckk"+(i+3));
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
				feedback3.setName("Ckk"+(jsonarray.size()+3));
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