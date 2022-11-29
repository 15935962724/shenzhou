/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.Setting;
import net.shenzhou.entity.DoctorWx;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.SafeKey;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.service.BeforehandLoginService;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.CartService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.DoctorWxService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.RSAService;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.RSAUtils;
import net.shenzhou.util.SettingUtils;
import net.shenzhou.util.WeiXinUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 会员登录
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appLoginController")
@RequestMapping("/app/login")
public class LoginController extends BaseController {

	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "beforehandLoginServiceImpl")
	private BeforehandLoginService beforehandLoginService;

	

	/**
	 * 手机号和密码登录
	 * @return 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public void submit(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        Setting setting = SettingUtils.get();
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
        String mobile = json.getString("mobile");
        String password = json.getString("password");
        
        
        
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
			map.put("status", "400");
			map.put("message", "用户名或密码不能为空");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
		Member member = memberService.findByMobile(mobile);
		
		if(member!=null){
			if (!DigestUtils.md5Hex(password).equals(member.getPassword())) {
				map.put("status", "400");
				map.put("message", "用户名或密码输入有误");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}else{
				map.put("status", "200");
				map.put("message", "登陆成功");
				SafeKey safeKey = new SafeKey();
				RSAPublicKey publicKey  = rsaService.generateKey(request);
				String safeKeyValue= RSAUtils.encrypt(publicKey, file);
				System.out.println("秘钥为:"+safeKeyValue);
				String md5SafeValue = DigestUtils.md5Hex(safeKeyValue);
				System.out.println("秘钥加密后:"+md5SafeValue);
		        
				safeKey.setValue(md5SafeValue);
				safeKey.setExpire(setting.getAppSafeKeyExpiryTime() != 0 ? DateUtils.addMinutes(new Date(), setting.getAppSafeKeyExpiryTime()) : new Date());
				member.setSafeKey(safeKey);
				member.setDevice_tokens(json.has("device_tokens")?json.getString("device_tokens"):"");
				memberService.update(member);
				Map<String ,Object> data = new HashMap<String ,Object>();
				data.put("safeKeyValue", md5SafeValue);
				map.put("data", JSONObject.fromObject(data));
//				response.getWriter().print(JsonUtils.toJson(map));;
				printWriter.write(JsonUtils.toJson(map)) ;
				return;
				
			}
		}
		
			map.put("status", "400");
			map.put("message", "用户名或密码输入有误");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
			
		
		

	}

	
	/**
	 * 秘钥登录
	 * @return 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/safeKeyValuesubmit", method = RequestMethod.GET)
	public void safeKeyValuesubmit(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        Setting setting = SettingUtils.get();
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
        String safeKeyValue = json.getString("safeKeyValue");
		if (StringUtils.isEmpty(safeKeyValue)) {
			map.put("status", "400");
			map.put("message", "密钥不能为空");
			map.put("data", new Object());
			printWriter.write(JsonUtils.toJson(map).toString()) ;
			return;
		}
		Member member = memberService.findBySafeKeyValue(safeKeyValue);
		if (member!=null&&!member.getSafeKey().hasExpired()) {
			map.put("status", "200");
			map.put("message", "登陆成功");
			SafeKey safeKey = new SafeKey();
			RSAPublicKey publicKey  = rsaService.generateKey(request);
			
			String newSafeKeyValue = RSAUtils.encrypt(publicKey, file);
			System.out.println("秘钥为:"+newSafeKeyValue);
			String md5SafeValue = DigestUtils.md5Hex(newSafeKeyValue);
			System.out.println("秘钥加密后:"+md5SafeValue);
			safeKey.setValue(md5SafeValue);
			safeKey.setExpire(setting.getAppSafeKeyExpiryTime() != 0 ? DateUtils.addMinutes(new Date(), setting.getAppSafeKeyExpiryTime()) : new Date());
			member.setSafeKey(safeKey);
			member.setDevice_tokens(json.has("device_tokens")?json.getString("device_tokens"):"");
			memberService.update(member);
			Map<String ,Object> data = new HashMap<String ,Object>();
			data.put("safeKeyValue", safeKey.getValue());
			map.put("data", JSONObject.fromObject(data));
			printWriter.write(JsonUtils.toJson(map).toString()) ;
			return;
		}else{
			map.put("status", "300");
			map.put("message", "密钥失效");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}

	}
	
	
	
	
	
	
	
}