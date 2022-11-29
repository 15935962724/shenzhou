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
import net.shenzhou.entity.Mechanism;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.RechargeLogService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 机构充值
 * @date 2018-5-25 10:15:19
 * @author wsr
 * @version 1.0
 */
@Controller("adminRechargeLogController")
@RequestMapping("/admin/rechargeLog")
public class RechargeLogController extends BaseController {

	@Resource(name = "rechargeLogServiceImpl")
	private RechargeLogService rechargeLogService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;

	
	/**
	 * 机构充值统计列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,Long mechanismId,String nameOrmobile, ModelMap model) {
		Mechanism mechanism = mechanismService.find(mechanismId);
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("nameOrmobile", nameOrmobile);
		query_map.put("mechanism", mechanism);
		query_map.put("pageable", pageable);
		
		model.addAttribute("mechanisms", mechanismService.findAll());
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("page", rechargeLogService.getRechargeLogs(query_map));
		return "/admin/recharge_log/list";
	}


}