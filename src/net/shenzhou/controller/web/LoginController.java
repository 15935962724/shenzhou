/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Pageable;
import net.shenzhou.Principal;
import net.shenzhou.FileInfo.FileType;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.User;
import net.shenzhou.entity.Verification;
import net.shenzhou.entity.Member.Calendar;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.EvaluateService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.service.VisitMessageService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.ShortMessageUtil;
import net.shenzhou.util.WebUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
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
/**
 * ??????
 * @date 2017-10-25 11:53:31
 * @author fl
 *
 */
@Controller("webLoginController")
@RequestMapping("/web/login")
public class LoginController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService ;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService ;
	@Resource(name = "visitMessageServiceImpl")
	private VisitMessageService visitMessageService ;
	@Resource(name = "evaluateServiceImpl")
	private EvaluateService evaluateService ;
	@Resource(name = "verificationServiceImpl")
	private VerificationService verificationService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	/**
	 * ?????????
	 */
	@RequestMapping(value = "/toLogin", method = RequestMethod.GET)
	public String list(Pageable pageable,String nameOrphone,Long doctorCategorieId ,Gender gender,Long serverProjectCategorieId, ModelMap model) {
		return "/web/login/login";
	}
	
	/**
	 * ??????????????????????????????????????????
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (memberService.usernameDisabled(username) || memberService.usernameExists(username)) {
			return false;
		} else {
			return true;
		}
	}

	
	/**
	 * ??????
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response,String phones , ModelMap model,String passwords,HttpSession session) {
		
		if (StringUtils.isEmpty(phones) || StringUtils.isEmpty(passwords)) {
			model.addAttribute("errorMessage", "???????????????????????????");
			return "/web/login/login";
		}
		Member member = memberService.findByUsername(phones);
		if(member==null){
			model.addAttribute("errorMessage", "????????????????????????");
			return "/web/login/login";
		}
		if(!DigestUtils.md5Hex(passwords).equals(member.getPassword())){
			model.addAttribute("errorMessage", "????????????????????????");
			return "/web/login/login";
		}
		
		member.setLoginDate(new Date());
		member.setLoginIp(request.getRemoteAddr());
		memberService.update(member);
		
		session = request.getSession();
		session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), phones));
		WebUtils.addCookie(request, response, Member.USERNAME_COOKIE_NAME, member.getUsername());
		
		return "/web/login/homePage";
	}
	
	/**
	 * ??????
	 */
	@RequestMapping(value = "/homePage", method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		Member member = memberService.getCurrent();
		System.out.println(member.getName());
		return "/web/login/homePage";
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/toRegister", method = RequestMethod.GET)
	public String toRegister(ModelMap model) {
		return "/web/login/register";
	}
	
	/**
	 * ??????
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(ModelMap model,String authCode,String phones,String keyword) {
		if (doctorService.mobileExists(phones)) {
			model.addAttribute("errorMessage", "?????????????????????");
			return "/web/login/register";
		}
		Verification verification = verificationService.findByMobile(phones);
		if (verification==null) {
			model.addAttribute("errorMessage", "?????????????????????");
			return "/web/login/register";
		}
		
		if(new Date().after(verification.getValid())){
			model.addAttribute("errorMessage", "??????????????????");
			return "/web/login/register";
		}
		if(!authCode.equals(verification.getCode())){
			model.addAttribute("errorMessage", "?????????????????????");
			return "/web/login/register";
		}	
		
		List<Area> area_list = areaService.findRoots();
		
		model.addAttribute("errorMessage", "???????????????????????????");
		model.addAttribute("phone", phones);
		model.addAttribute("passWord", keyword);
		model.addAttribute("top_area", area_list);
		return "/web/login/perfectData";
	}
	
	
	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/toForgetPassword", method = RequestMethod.GET)
	public String toForgetPassword(ModelMap model) {
		return "/web/login/forgetPassword";
	}
	
	/**
	 * ???????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/send_code", method = RequestMethod.GET)
	public @ResponseBody
	boolean sendCode(String file,HttpServletRequest request ,HttpServletResponse response , String phone) throws IOException{
		try {
			int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);// ???????????????
			
			if (verificationService.mobileExists(phone)) {
				SendSmsResponse data = new SendSmsResponse();
				data = ShortMessageUtil.seng_message(phone,mobile_code,"");
				if(data.getCode().equals("OK")){
					Verification verification = verificationService.findByMobile(phone);
					verification.setCode(String.valueOf(mobile_code));
					Date afterDate = DateUtils.addMinutes(new Date(), 10);
					verification.setValid(afterDate);//????????????????????????
					verificationService.update(verification);
					return true;
				}
				return false;
			} else{
				SendSmsResponse data = ShortMessageUtil.seng_message(phone,mobile_code,"");
				if(data.getCode().equals("OK")){
					Verification verification = new Verification();
					verification.setMobile(phone);
					verification.setCode(String.valueOf(mobile_code));
					Date afterDate = DateUtils.addMinutes(new Date(), 10);
					verification.setValid(afterDate);
					verificationService.save(verification);
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public String forgetPassword(ModelMap model,String codeInput , String phones ,String keyword) {
		
		if (StringUtils.isEmpty(phones) || StringUtils.isEmpty(keyword)) {
			model.addAttribute("errorMessage", "???????????????????????????");
			return "/web/login/forgetPassword";
		}
		Member member = memberService.findByUsername(phones);
		if(member==null){
			model.addAttribute("errorMessage", "?????????????????????");
			return "/web/login/forgetPassword";
		}
		
		Verification verification = verificationService.findByMobile(phones);
		if(verification==null){
			model.addAttribute("errorMessage", "?????????????????????");
			return "/web/login/forgetPassword";
		}
		
		if(new Date().after(verification.getValid())){
			model.addAttribute("errorMessage", "??????????????????");
			return "/web/login/forgetPassword";
		}
		if(!codeInput.equals(verification.getCode())){
			model.addAttribute("errorMessage", "?????????????????????");
			return "/web/login/forgetPassword";
		}	
		
		member.setPassword(DigestUtils.md5Hex(keyword));
		memberService.update(member);
		model.addAttribute("errorMessage", "????????????");
		return "/web/login/login";
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
	public@ResponseBody 
	String imgUpload(ModelMap model,String fieldNameHere,@RequestParam(value = "upfile", required = false)MultipartFile  file,HttpServletRequest request ,HttpServletResponse response) {
		String path = Config.memberLogoUrl;
	    String name = DateUtil.getStatetime()+path;
	    String fileUrl = fileService.uploadImg(FileType.image, file, path, UUID.randomUUID().toString(), false);
		return fileUrl;
	}
	
	/**
	 * ????????????(??????)
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getArea", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public@ResponseBody
	String getArea(String file,Long areaId,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Set<Area> areas = areaService.find(areaId).getChildren();
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for(Area area : areas){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", area.getId());
			map.put("areaName", area.getName());
			data_list.add(map);
		}
		
		return JsonUtils.toJson(data_list);
	}
	
}