/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Setting;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.SafeKey;
import net.shenzhou.entity.Verification;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.RSAService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.RSAUtils;
import net.shenzhou.util.SettingUtils;
import net.shenzhou.util.ShortMessageUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

/**
 * Controller - 会员注册
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appRegisterController")
@RequestMapping("/app/register")
public class RegisterController extends BaseController {


	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "verificationServiceImpl")
	private VerificationService verificationService;

	
	/**
	 * 发送验证码
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/send_code", method = RequestMethod.GET)
	public void sendCode(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
			String mobile = json.getString("mobile");//手机号
			int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);// 生成验证码
			
			if (verificationService.mobileExists(mobile)) {
				SendSmsResponse data = new SendSmsResponse();
				String messageType = "";
				if(json.containsKey("messageType")){
					messageType=json.getString("messageType");
					data = ShortMessageUtil.seng_message(mobile,mobile_code,messageType);
				}else{
					data = ShortMessageUtil.seng_message(mobile,mobile_code,messageType);
				}
				/*JSONObject jsonObject = JSONObject.fromObject(data);
				String code = jsonObject.get("respCode").toString();*/
				if(data.getCode().equals("OK")){
					Verification verification = verificationService.findByMobile(mobile);
					verification.setCode(String.valueOf(mobile_code));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					System.out.println("当前时间：" + sdf.format(now));
					Date afterDate = new Date(now .getTime() + 300000);
					
//					verification.setCreateDate(DateUtil.getMonth(12));//验证码有效期为12个月
					verification.setValid(afterDate);//设置验证码有效期
					verificationService.update(verification);
					
					
					map.put("status", "200");
					map.put("message", "验证码发送成功");
					map.put("data", new Object());
					printWriter.write(JSONObject.fromObject(map).toString()) ;
					return;
				}
				map.put("status", "400");
				map.put("message", "获取验证码太频繁");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			} else{
				SendSmsResponse data = new SendSmsResponse();
				String messageType = "";
				if(json.containsKey("messageType")){
					messageType=json.getString("messageType");
					data = ShortMessageUtil.seng_message(mobile,mobile_code,messageType);
				}else{
					data = ShortMessageUtil.seng_message(mobile,mobile_code,messageType);
				}
				if(data.getCode().equals("OK")){
					Verification verification = new Verification();
					verification.setMobile(mobile);
					verification.setCode(String.valueOf(mobile_code));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					System.out.println("当前时间：" + sdf.format(now));
					Date afterDate = new Date(now .getTime() + 60000);
					verification.setValid(afterDate);
					verificationService.save(verification);

					map.put("status", "200");
					map.put("message", "验证码发送成功");
					map.put("data", new Object());
					printWriter.write(JSONObject.fromObject(map).toString()) ;
					return;
				}
				map.put("status", "400");
				map.put("message", "获取验证码太频繁");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JsonUtils.toJson(map)) ;
			return;
		}
		
	}
	
	
	
	
	/**
	 * 发送验证码
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/send_code_forget", method = RequestMethod.GET)
	public void send_code_forget(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
			String mobile = json.getString("mobile");//手机号
			int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);// 生成验证码
			
			Member member =memberService.findByMobile(mobile);
			if(member==null){
				map.put("status", "400");
				map.put("message", "该手机号尚未注册");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			
			if (verificationService.mobileExists(mobile)) {
				SendSmsResponse data = new SendSmsResponse();
				String messageType = "";
				if(json.containsKey("messageType")){
					messageType=json.getString("messageType");
					data = ShortMessageUtil.seng_message(mobile,mobile_code,messageType);
				}else{
					data = ShortMessageUtil.seng_message(mobile,mobile_code,messageType);
				}
				/*JSONObject jsonObject = JSONObject.fromObject(data);
				String code = jsonObject.get("respCode").toString();*/
				if(data.getCode().equals("OK")){
					Verification verification = verificationService.findByMobile(mobile);
					verification.setCode(String.valueOf(mobile_code));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					System.out.println("当前时间：" + sdf.format(now));
					Date afterDate = new Date(now .getTime() + 300000);
					
//					verification.setCreateDate(DateUtil.getMonth(12));//验证码有效期为12个月
					verification.setValid(afterDate);//设置验证码有效期
					verificationService.update(verification);
					
					
					map.put("status", "200");
					map.put("message", "验证码发送成功");
					map.put("data", new Object());
					printWriter.write(JSONObject.fromObject(map).toString()) ;
					return;
				}
				map.put("status", "400");
				map.put("message", "获取验证码太频繁");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			} else{
				SendSmsResponse data = new SendSmsResponse();
				String messageType = "";
				if(json.containsKey("messageType")){
					messageType=json.getString("messageType");
					data = ShortMessageUtil.seng_message(mobile,mobile_code,messageType);
				}else{
					data = ShortMessageUtil.seng_message(mobile,mobile_code,messageType);
				}
				if(data.getCode().equals("OK")){
					Verification verification = new Verification();
					verification.setMobile(mobile);
					verification.setCode(String.valueOf(mobile_code));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					System.out.println("当前时间：" + sdf.format(now));
					Date afterDate = new Date(now .getTime() + 60000);
					verification.setValid(afterDate);
					verificationService.save(verification);

					map.put("status", "200");
					map.put("message", "验证码发送成功");
					map.put("data", new Object());
					printWriter.write(JSONObject.fromObject(map).toString()) ;
					return;
				}
				map.put("status", "400");
				map.put("message", "获取验证码太频繁");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JsonUtils.toJson(map)) ;
			return;
		}
		
	}
	
	
	
	
	
	/**
	 * 注册提交
	 * @throws IOException 
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public void submit(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
		String mobile = json.getString("mobile");
		String password = json.getString("password");
		String code = json.getString("code");
		
		if (memberService.usernameExists(mobile)) {
			map.put("status", "400");
			map.put("message", "该手机号已注册");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
		Verification verification = verificationService.findByMobile(mobile);
		if(verification==null){
			map.put("status", "400");
			map.put("message", "验证码输入有误");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
		
		if(new Date().after(verification.getValid())){
			map.put("status", "400");
			map.put("message", "验证码已失效");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
		if(!code.equals(verification.getCode())){
			map.put("status", "400");
			map.put("message", "验证码输入有误");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}	
		
		map.put("status", "200");
		map.put("message", "注册成功");
		map.put("data", new Object());
		printWriter.write(JSONObject.fromObject(map).toString()) ;
		return;
	}

}
