/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.shenzhou.Setting;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Information;
import net.shenzhou.entity.Information.DisposeState;
import net.shenzhou.entity.Information.InformationType;
import net.shenzhou.entity.Information.StateType;
import net.shenzhou.entity.Information.UserType;
import net.shenzhou.entity.Member;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.InformationService;
import net.shenzhou.service.MemberService;
import net.shenzhou.util.HttpUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MobileUtil;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 医生账户
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appAuthenticationController")
@RequestMapping("/app/authentication")
public class AuthenticationController extends BaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "informationServiceImpl")
	private InformationService informationService;
	/**
	 * 医生实名认证
	 * @throws IOException 
	 */
	@RequestMapping(value = "/doctorRealNameAuthentication", method = RequestMethod.GET)
	public void doctorRealNameAuthentication(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
        String safeKeyValue = json.getString("safeKeyValue");
        String realName = json.getString("realName");
        String card = json.getString("card");
        
        if(StringUtils.isEmpty(safeKeyValue)){
			map.put("status", "400");
			map.put("message", "还没登录请重新登录");
			map.put("data", new Object());
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        
		Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
        if(doctor == null){
			map.put("status", "400");
			map.put("message", "秘钥失效,请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		if(doctor.getSafeKey().hasExpired()){
			map.put("status", "400");
			map.put("message", "秘钥失效,请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        Map<String,String> map_data = new HashMap<String, String>();
        
        Setting setting = SettingUtils.get();
		String param="key="+setting.getCardKey()+"&cardNo="+card+"&realName="+realName+"&information=";
		String returnStr = HttpUtil.post(setting.getCardUrl(), param);
		JSONObject obj = JSONObject.fromObject(returnStr);
        String key = obj.getString("error_code");
        String reason =  obj.getString("reason");
        
        if(!key.equals("0")){
        	map.put("status", "400");
    		map.put("message", reason);
    		map.put("data", JsonUtils.toJson(map_data));
    		printWriter.write(JsonUtils.toString(map)) ;
    		return;
        }
        
        doctor.setIsReal(true);
        doctor.setEntityCode(card);
        if(!doctor.getName().equals(realName)){
        	doctor.setName(realName);
        }
        
        doctorService.update(doctor);
        
        //实名认证发送通知消息
        Information information = new Information();
		information.setMessage("您的实名认证申请已通过。");
		information.setHeadline("实名认证通知");
		information.setInformationType(InformationType.system);
		information.setState(StateType.unread);
		information.setDoctor(doctor);
		information.setIsDeleted(false);
		information.setDisposeState(DisposeState.unDispose);
		information.setUserType(UserType.doctor);
		informationService.save(information);
        
		map.put("status", "200");
		map.put("message", reason);
		map.put("data", JsonUtils.toJson(map_data));
		printWriter.write(JsonUtils.toString(map)) ;
		return;
        
	}
	
	/**
	 * 用户实名认证
	 * @throws IOException 
	 */
	@RequestMapping(value = "/memberRealNameAuthentication", method = RequestMethod.GET)
	public void memberRealNameAuthentication(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
        String safeKeyValue = json.getString("safeKeyValue");
        String realName = json.getString("realName");
        String card = json.getString("card");
        
        if(StringUtils.isEmpty(safeKeyValue)){
			map.put("status", "400");
			map.put("message", "还没登录请重新登录");
			map.put("data", new Object());
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        
		Member member = memberService.findBySafeKeyValue(safeKeyValue);
        if(member == null){
			map.put("status", "400");
			map.put("message", "秘钥失效,请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		if(member.getSafeKey().hasExpired()){
			map.put("status", "400");
			map.put("message", "秘钥失效,请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
//		if(memberService.cardIdExists(card)){
//			map.put("status", "400");
//			map.put("message", "该身份证已实名认证");
//			map.put("data", "{}");
//			printWriter.write(JsonUtils.toString(map)) ;
//			return;
//		}
		
		if(member.getIsReal()){
			map.put("status", "400");
			map.put("message", "该身份证已实名认证");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
        Map<String,String> map_data = new HashMap<String, String>();
        Setting setting = SettingUtils.get();
		String param="key="+setting.getCardKey()+"&cardNo="+card+"&realName="+realName+"&information=";
		String returnStr = HttpUtil.post(setting.getCardUrl(), param);
		JSONObject obj = JSONObject.fromObject(returnStr);
        String key = obj.getString("error_code");
        String reason =  obj.getString("reason");
		
        if(!key.equals("0")){
        	map.put("status", "400");
    		map.put("message", reason);
    		map.put("data", JsonUtils.toJson(map_data));
    		printWriter.write(JsonUtils.toString(map)) ;
    		return;
        }
        
        member.setIsReal(true);
        member.setCardId(card);
        if(!member.getName().equals(realName)){
        	member.setName(realName);
        }
        
        Information information = new Information();
		information.setMessage("您的实名认证申请已通过。");
		information.setHeadline("实名认证通知");
		information.setInformationType(InformationType.system);
		information.setState(StateType.unread);
		information.setMember(member); 
		information.setIsDeleted(false);
		information.setDisposeState(DisposeState.unDispose);
		information.setUserType(UserType.member);
		informationService.save(information);
        
        memberService.update(member);
        
		map.put("status", "200");
		map.put("message", reason);
		map.put("data", JsonUtils.toJson(map_data));
		printWriter.write(JsonUtils.toString(map)) ;
		return;
        
	}
	
	
	/**
	 * 医生实名回显
	 * @throws IOException 
	 */
	@RequestMapping(value = "/doctorRealNameEcho", method = RequestMethod.GET)
	public void doctorRealNameEcho(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
        String safeKeyValue = json.getString("safeKeyValue");
        
        if(StringUtils.isEmpty(safeKeyValue)){
			map.put("status", "400");
			map.put("message", "还没登录请重新登录");
			map.put("data", new Object());
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        
		Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
        if(doctor == null){
			map.put("status", "400");
			map.put("message", "秘钥失效,请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		if(doctor.getSafeKey().hasExpired()){
			map.put("status", "400");
			map.put("message", "秘钥失效,请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        
		Map<String,Object> map_data = new HashMap<String, Object>();
		map_data.put("doctorRealName", doctor.getName());
		map_data.put("card", doctor.getEntityCode()==null?"":MobileUtil.getStarString2(doctor.getEntityCode(), 1, 1));
		map_data.put("isReal",doctor.getIsReal()==null?false:doctor.getIsReal());
		
		map.put("status", "200");
		map.put("message","回显数据" );
		map.put("data", JsonUtils.toJson(map_data));
		printWriter.write(JsonUtils.toString(map)) ;
		return;
        
	}
	
	/**
	 * 用户实名回显
	 * @throws IOException 
	 */
	@RequestMapping(value = "/memberRealNameEcho", method = RequestMethod.GET)
	public void memberRealNameEcho(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
        String safeKeyValue = json.getString("safeKeyValue");
        
        if(StringUtils.isEmpty(safeKeyValue)){
			map.put("status", "300");
			map.put("message", "还没登录请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        
		Member member = memberService.findBySafeKeyValue(safeKeyValue);
        if(member == null){
			map.put("status", "300");
			map.put("message", "秘钥失效,请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		if(member.getSafeKey().hasExpired()){
			map.put("status", "300");
			map.put("message", "秘钥失效,请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        
		Map<String,Object> map_data = new HashMap<String, Object>();
		map_data.put("doctorRealName", member.getName());
		map_data.put("isReal",member.getIsReal()==null?false:member.getIsReal());
		map_data.put("card", member.getCardId()==null?"":MobileUtil.getStarString2(member.getCardId(), 1, 1));
		
		map.put("status", "200");
		map.put("message","回显数据");
		map.put("data", JsonUtils.toJson(map_data));
		printWriter.write(JsonUtils.toString(map)) ;
		return;
        
	}
	
}



















