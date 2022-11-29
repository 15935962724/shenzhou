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
import net.shenzhou.entity.MemberPointLog.Type;
import net.shenzhou.service.MemberPointLogService;
import net.shenzhou.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户积分
 * @date 2018-3-22 15:46:27
 * @author wsr
 *
 */
@Controller("adminMemberPointLogController")
@RequestMapping("/admin/memberPointLog")
public class MemberPointLogController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberPointLogServiceImpl")
	private MemberPointLogService memberPointLogService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long memberId,Type type,Pageable pageable, ModelMap model) {
		
		Member member = memberService.find(memberId);
		Map <String,Object> query_map = new HashMap<String, Object>();
		query_map.put("member", member);
		query_map.put("pageable", pageable);
		if (member != null) {
			model.addAttribute("member", member);
			model.addAttribute("page", memberPointLogService.findPage(query_map));
		} else {
			model.addAttribute("page", memberPointLogService.findPage(pageable));
		}
		model.addAttribute("type", type);
		model.addAttribute("types", Type.values());
		return "/admin/member_point_log/list";
		
	}

	
	
}