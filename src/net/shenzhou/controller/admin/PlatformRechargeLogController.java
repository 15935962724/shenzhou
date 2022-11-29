/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Pageable;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PlatformRechargeLog.RechargeMode;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.PlatformRechargeLogService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 广告
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("adminPlatformRechargeLogController")
@RequestMapping("/admin/platformRechargeLog")
public class PlatformRechargeLogController extends BaseController {

	@Resource(name = "platformRechargeLogServiceImpl")
	private PlatformRechargeLogService platformRechargeLogService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,Long memberId,RechargeMode rechargeMode, ModelMap model) {
		Member member = memberService.find(memberId);
		Map<String ,Object> query_map = new HashMap<String, Object>();
		query_map.put("member", member);
		query_map.put("rechargeMode", rechargeMode);
		query_map.put("pageable", pageable);
		model.addAttribute("page", platformRechargeLogService.findPage(query_map));
		return "/admin/platform_recharge_log/list";
	}

	

}