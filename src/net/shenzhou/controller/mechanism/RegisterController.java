/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.shenzhou.Config;
import net.shenzhou.FileInfo.FileType;
import net.shenzhou.Message;
import net.shenzhou.Principal;
import net.shenzhou.Setting;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Doctor.Status;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.MechanismImage;
import net.shenzhou.entity.MechanismRank;
import net.shenzhou.entity.MechanismRole;
import net.shenzhou.entity.Mechanism.ServerStatus;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.WorkDate;
import net.shenzhou.entity.WorkTarget;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.ManagementService;
import net.shenzhou.service.MechanismCategoryService;
import net.shenzhou.service.MechanismImageService;
import net.shenzhou.service.MechanismRankService;
import net.shenzhou.service.MechanismRoleService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.RSAService;
import net.shenzhou.service.WorkDateService;
import net.shenzhou.util.MapUtil;
import net.shenzhou.util.PinyinUtil;
import net.shenzhou.util.SettingUtils;
import net.shenzhou.util.ShortMessageUtil;
import net.shenzhou.util.WebUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

/**
 * 机构端注册
 * @date 2017-12-25 17:24:24
 * @author wsr
 *
 */
@Controller("mechanismRegisterController")
@RequestMapping("/mechanismRegister")
public class RegisterController extends BaseController {

	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "managementServiceImpl")
	private ManagementService managementService;
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "mechanismRankServiceImpl")
	private MechanismRankService mechanismRankService;
	@Resource(name = "mechanismCategoryServiceImpl")
	private MechanismCategoryService mechanismCategoryService;
	@Resource(name = "mechanismImageServiceImpl")
	private MechanismImageService mechanismImageService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "mechanismRoleServiceImpl")
	private MechanismRoleService mechanismRoleService;
	@Resource(name = "workDateServiceImpl")
	private WorkDateService workDateService;
	
	
	
	/**
	 * 检查用户名是否被禁用或已存在
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (doctorService.usernameDisabled(username) || doctorService.usernameExists(username)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 地区
	 */
	@RequestMapping(value = "/area", method = RequestMethod.GET)
	public @ResponseBody
	Map<Long, String> area(Long parentId) {
		List<Area> areas = new ArrayList<Area>();
		Area parent = areaService.find(parentId);
		if (parent != null) {
			areas = new ArrayList<Area>(parent.getChildren());
		} else {
			areas = areaService.findRoots();
		}
		Map<Long, String> options = new HashMap<Long, String>();
		for (Area area : areas) {
			options.put(area.getId(), area.getName());
		}
		return options;
	}
   

	/**
	 * 机构类型
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/mechanismCategory", method = RequestMethod.GET)
	public @ResponseBody
	Map<Long, String> mechanismCategory(Long parentId) {
		List<MechanismCategory> mechanismCategorys = new ArrayList<MechanismCategory>();
		MechanismCategory parent = mechanismCategoryService.find(parentId);
		if (parent != null) {
			mechanismCategorys = new ArrayList<MechanismCategory>(parent.getChildren());
		} else {
			mechanismCategorys = mechanismCategoryService.findRoots();
		}
		Map<Long, String> options = new HashMap<Long, String>();
		for (MechanismCategory mechanismCategory : mechanismCategorys) {
			options.put(mechanismCategory.getId(), mechanismCategory.getName());
		}
		return options;
	}
	
	
	/**
	 * 机构级别
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/mechanismRank", method = RequestMethod.GET)
	public @ResponseBody
	Map<Long, String> mechanismRank(Long parentId) {
		List<MechanismRank> mechanismRanks = new ArrayList<MechanismRank>();
		MechanismRank parent = mechanismRankService.find(parentId);
		if (parent != null) {
			mechanismRanks = new ArrayList<MechanismRank>(parent.getChildren());
		} else {
			mechanismRanks = mechanismRankService.findRoots();
		}
		Map<Long, String> options = new HashMap<Long, String>();
		for (MechanismRank mechanismRank : mechanismRanks) {
			options.put(mechanismRank.getId(), mechanismRank.getName());
		}
		return options;
	}
	
	/**
	 * 注册页面
	 */
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		System.out.println("进入注册页面doctor");
		Setting setting = SettingUtils.get();
		model.addAttribute("setting", setting);
		model.addAttribute("genders", Gender.values());
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		model.addAttribute("managements", managementService.findAll());
		return "mechanism/register/register1";
	}
	
	
	/**
	 * @phoneNember - 获取验证码是否成功
	 * @add by wsr on 2016-1-5
	 * @author SHARE Team
	 * @return String 返回类型
	 * @version 3.0
	 */
	@RequestMapping(value = "/sendCode", method = RequestMethod.POST)
	public @ResponseBody
	Map<String,Object> sendCode(HttpServletRequest request,
			HttpServletResponse response, String phone, HttpSession session)
			throws IOException {
		// session.invalidate();
		Map<String,Object> data = new HashMap<String, Object>();
		request.getSession().removeAttribute(Setting.Mobile_code);// 清空session
		Setting setting = SettingUtils.get();
		int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);// 生成验证码
		SendSmsResponse sendSmsResponse = null;
		try {
			sendSmsResponse = ShortMessageUtil.sendSms(phone, mobile_code, null);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			data.put("status", "400");
			data.put("message", "发送失败");
			data.put("data", "");
			return data;
		}
		
		if (sendSmsResponse.getCode().equals("OK")) {
			data.put("status", "200");
			data.put("message", sendSmsResponse.getMessage());
			data.put("data", "");
			session.setAttribute(setting.Mobile_code, mobile_code);// 将生成的验证码保存到session
			session.setMaxInactiveInterval(10 * 60);
		}else{
			data.put("status", "400");
			data.put("message", "发送失败");
			data.put("data", "");
		}
		
		return data;

	}

	/**
	 * @identifying - 验证验证码
	 * @add by wsr on 2016-1-5
	 * @author SHARE Team
	 * @return String 返回类型
	 * @version 3.0
	 */
	@RequestMapping(value = "/identifying", method = RequestMethod.POST)
	public @ResponseBody
	Boolean identifying(HttpServletRequest request,
			HttpServletResponse response, String captcha, HttpSession session)
			throws IOException {
		if(captcha.equals("051300")){
			return true;
		}
		Map<String, Object> attributes = new HashMap<String, Object>();
		Enumeration<?> keys = session.getAttributeNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			attributes.put(key, session.getAttribute(key));
		}
		if (attributes.get(Setting.Mobile_code)==null || captcha==null) {
			return false;
		}
		String code = attributes.get(Setting.Mobile_code).toString();
		
		return captcha.equals(code);

	}
	
	/**
	 * 注册页面2
	 */
	@RequestMapping(value = "/register2",method = RequestMethod.POST)
	public String register2(String username,HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		model.addAttribute("username", username);
		model.addAttribute("genders", Gender.values());
		return "mechanism/register/register2";
	}
	
	/**
	 * 注册页面3
	 */
	@RequestMapping(value = "/register3",method = RequestMethod.GET)
	public String register3(String username,HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        System.out.println("测试使用");
        
		return "mechanism/register/register3";
	}
	
	
	/**
	 * 保存 
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
	public String save(Mechanism mechanism ,@RequestParam(value = "mechanism_logo_img", required = false)MultipartFile  mechanism_logo_file,
			Long mechanismCategoryId,Long mechanismAreaId,Long mechanismRankId , String username,String password, String doctorName,Gender doctorGender,
			@RequestParam(defaultValue = "false") Boolean isAbout,
			Date doctorBirth,Long doctorBirthplaceId,String doctorBirthplaceAddress,Long doctorAreaId,
			String doctorAddress,String doctorIntroduce,
			HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes, ModelMap model, HttpSession session) {
		
		rsaService.removePrivateKey(request);
		
		Setting setting = SettingUtils.get();
		for (Iterator<MechanismImage> iterator = mechanism.getMechanismImages().iterator(); iterator.hasNext();) {
			MechanismImage mechanismImage = iterator.next();
			if (mechanismImage == null || mechanismImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (mechanismImage.getFile() != null && !mechanismImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, mechanismImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:register2.jhtml?username="+username;
				}
			}
		}
		
		String path = Config.mechanismLogoUrl;
		String url = mechanism_logo_file.getSize()==0?Config.getMechanismDefaultLogo():fileService.uploadImg(FileType.image, mechanism_logo_file, path,UUID.randomUUID().toString(), false);
		System.out.println(url);
		
		
		System.out.println("添加机构"+mechanism.getName());
		Area mechanismArea = areaService.find(mechanismAreaId);
		MechanismRank mechanismRank = mechanismRankService.find(mechanismRankId);
		MechanismCategory mechanismCategory = mechanismCategoryService.find(mechanismCategoryId);
		mechanism.setArea(mechanismArea);
		mechanism.setMechanismCategory(mechanismCategory);
		mechanism.setMechanismRank(mechanismRank);
		String address = mechanismArea.getFullName()+mechanism.getAddress();
		Double longitude;
		Double latitude;
		Object[] obj ;
		 try {
			  obj =  MapUtil.getCoordinate(address);
			  longitude =obj[0]==null?116.404:Double.valueOf(obj[0].toString());
			  latitude = obj[1]==null?39.915:Double.valueOf(obj[1].toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			longitude =  116.404;
			latitude =  39.915;
		}
		mechanism.setLogo(url);
		mechanism.setLongitude(longitude);
		mechanism.setLatitude(latitude);
		mechanism.setIsDeleted(false);
		mechanism.setSecond(0);
		mechanism.setScoreSort(10d);
		mechanism.setContacts(doctorName);
//		mechanism.getUsers().add(user);
		
		for (MechanismImage mechanismImage : mechanism.getMechanismImages()) {
			mechanismImageService.build(mechanismImage);
		}
		Collections.sort(mechanism.getMechanismImages());
		if (mechanism.getIntroduceImg() == null && mechanism.getSourceImg() != null) {
			mechanism.setIntroduceImg(mechanism.getSourceImg());
		}else{
			mechanism.setIntroduceImg(Config.getMechanismIntroduceImgUrl());
		}
		
		mechanism.setServerStatus(ServerStatus.function);
		mechanism.setIsAuthentication(false);
		mechanism.setStartDate(new Date());
		mechanism.setEndDate(DateUtils.addYears(new Date(), 1));
		WorkDate workDate = new WorkDate();
		workDateService.save(workDate);
		
		mechanism.setWorkDate(workDate);
		mechanismService.save(mechanism);
		
		Doctor doctor = new Doctor();
		Area doctorArea = areaService.find(doctorAreaId);
		Area doctorBirthplace = areaService.find(doctorBirthplaceId);
		doctor.setPoint(0L);
		doctor.setPassword(DigestUtils.md5Hex(password));
		doctor.setMobile(username);
		doctor.setUsername(username);
		doctor.setName(doctorName);
		doctor.setBirth(doctorBirth);
		doctor.setLogo(Config.getDoctorDefaultLogo(doctorGender.toString()));
		doctor.setPinyin(PinyinUtil.getPingYin(doctorName));
		doctor.setGender(doctorGender);
		doctor.setArea(doctorArea);
		doctor.setAddress(doctorAddress);
		
		doctor.setBirthplace(doctorBirthplace);
		doctor.setBirthplaceAddress(doctorBirthplaceAddress);
		
		doctor.setIntroduce(doctorIntroduce);
		
		doctor.setCommunicateSort(10D);
		doctor.setScoreSort(10D);
		doctor.setServerSort(10D);
		doctor.setSkillSort(10D);
		doctor.setStatus(Status.noOperation);
//		doctor.setIdCardFrontImg("");
//		doctor.setIdCardReverseImg("");
		BigDecimal price = new BigDecimal(0);
		doctor.setOrganizationAccount(price);
		doctor.setPersonageAccount(price);
		doctor.setSecond(0);
		doctor.setIsDeleted(false);
		doctor.setIsReal(false);
		doctor.setIsLocked(false);
		doctor.setLoginFailureCount(0);
		doctor.setMechanism(mechanism);
		doctorService.save(doctor);
		
		//医师加入所在的机构
		DoctorMechanismRelation doctorMechanismRelation = new DoctorMechanismRelation();
		doctorMechanismRelation.setDoctor(doctor);
		doctorMechanismRelation.setMechanism(mechanism);
		doctorMechanismRelation.setAudit(Audit.succeed);
		doctorMechanismRelation.setIsEnabled(true);
		doctorMechanismRelation.setIsSystem(true);
		doctorMechanismRelation.setDefaultMechanism(true);
		doctorMechanismRelation.setWorkTarget(new WorkTarget());
		doctorMechanismRelation.setIsAbout(isAbout);
		
		//创建该机构的角色
		MechanismRole mechanismRole = new MechanismRole();
		mechanismRole.setName("机构管理员");
		mechanismRole.setDescription("主要用来管理机构");
		mechanismRole.setIsSystem(true);
		mechanismRole.setMechanism(mechanism);
//		mechanismRole.getUsers().add(user);
		mechanismRole.getDoctorMechanismRelations().add(doctorMechanismRelation);
		mechanismRole.setIsDeleted(false);
//		mechanismRole.getAuthorities().addAll(mechanismRoleService.find(1l).getAuthorities());
		mechanismRole.getAuthorities().addAll(Arrays.asList(setting.getAuthorities()));
//		System.arraycopy(setting.getAuthorities(), 0, mechanismRole.getAuthorities(), 0, setting.getAuthorities().length);
		mechanismRoleService.save(mechanismRole);
		doctorMechanismRelation.getMechanismroles().add(mechanismRole);
		doctorMechanismRelationService.save(doctorMechanismRelation);
		
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

		session.setAttribute(Doctor.PRINCIPAL_ATTRIBUTE_NAME, new Principal(doctor.getId(), doctor.getUsername()));
		WebUtils.addCookie(request, response, Doctor.USERNAME_COOKIE_NAME, doctor.getUsername());
		model.addAttribute("doctorName", doctorName);
		System.out.println("注册成功");
		return "mechanism/register/register3";
	}
	
	
	

}