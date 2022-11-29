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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.shenzhou.Message;
import net.shenzhou.Setting;
import net.shenzhou.Setting.CaptchaType;
import net.shenzhou.entity.BaseEntity.Save;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.SafeKey;
import net.shenzhou.entity.Verification;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.CartService;
import net.shenzhou.service.MailService;
import net.shenzhou.service.MemberAttributeService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.RSAService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.RSAUtils;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 密码
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appPasswordController")
@RequestMapping("/app/password")
public class PasswordController extends BaseController {


	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	@Resource(name = "verificationServiceImpl")
	private VerificationService verificationService;

	

	
	/**
	 * 重置密码提交
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
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
		
		Verification verification = verificationService.findByMobile(mobile);
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
		
		Setting setting = SettingUtils.get();
		Member member = memberService.findByMobile(mobile);
		if(member == null){
			map.put("status", "400");
			map.put("message", "该手机号未注册或输入有误");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
		
		member.setPassword(DigestUtils.md5Hex(password));
		SafeKey safeKey = new SafeKey();
		RSAPublicKey publicKey  = rsaService.generateKey(request);
		System.out.println(RSAUtils.encrypt(publicKey, file));
		safeKey.setValue(RSAUtils.encrypt(publicKey, file));
		safeKey.setExpire(setting.getAppSafeKeyExpiryTime() != 0 ? DateUtils.addMinutes(new Date(),setting.getAppSafeKeyExpiryTime()) : new Date());
		member.setSafeKey(safeKey);
		
		memberService.update(member);
		Map<String ,Object> data = new HashMap<String ,Object>();
		map.put("status", "200");
		map.put("message", "设置成功");
		data.put("safeKeyValue", safeKey.getValue());
		map.put("data", JSONObject.fromObject(data));
		printWriter.write(JSONObject.fromObject(map).toString()) ;
		return;
	}
}