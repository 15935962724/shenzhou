/*

 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.Pageable;
import net.shenzhou.entity.Admin;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Mechanism.ServerStatus;
import net.shenzhou.entity.MechanismLog;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.MechanismLogService;
import net.shenzhou.service.MechanismService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 机构管理
 * @date 2018-3-2 11:56:29
 * @author wsr
 *
 */
@Controller("adminMechanismController")
@RequestMapping("/admin/mechanism")
public class MechanismController extends BaseController {

	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "mechanismLogServiceImpl")
	private MechanismLogService mechanismLogService;
	
	
	
	
	/**
	 * 机构列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model,Pageable pageable, HttpServletRequest request) {
		Admin admin = adminService.getCurrent();
		model.addAttribute("page", mechanismService.findPage(pageable));
		return "admin/mechanism/list";
	}
	
	
	/**
	 * 企业明细
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(ModelMap model,Long id ,HttpServletRequest request) {
		Admin admin = adminService.getCurrent();
		Mechanism mechanism = mechanismService.find(id);
		
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("doctor", mechanism.getSystemDoctor());
		model.addAttribute("certificates", mechanism.getCertificates());
		model.addAttribute("mechanismLogs", new ArrayList<MechanismLog>(mechanism.getMechanismLogs()));
		model.addAttribute("serverStatus", ServerStatus.values());
		return "admin/mechanism/view";
	}
	
	
	/**
	 * 更新认证状态
	 */
	@RequestMapping(value = "/updateIsAuthentication", method = RequestMethod.POST)
	public String update(Long id, Boolean isAuthentication, String remark,RedirectAttributes redirectAttributes) {
		Admin admin = adminService.getCurrent();
		Mechanism mechanism = mechanismService.find(id);
		mechanism.setIsAuthentication(isAuthentication);
		mechanismService.update(mechanism);
		MechanismLog mechanismLog = new MechanismLog();
		mechanismLog.setAction(isAuthentication?"认证通过":"认证失败");
		mechanismLog.setRemark(remark);
		mechanismLog.setOperator(admin);
		mechanismLog.setMechanism(mechanism);
		mechanismLogService.save(mechanismLog);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	
	/**
	 * 更新服务状态
	 */
	@RequestMapping(value = "/updateServerStatus", method = RequestMethod.POST)
	public String update(Long id, ServerStatus serverStatus,Date endDate, String remark,RedirectAttributes redirectAttributes) {
		Admin admin = adminService.getCurrent();
		Mechanism mechanism = mechanismService.find(id);
		mechanism.setServerStatus(serverStatus);
		mechanism.setEndDate(endDate);
		mechanismService.update(mechanism);
		MechanismLog mechanismLog = new MechanismLog();
		mechanismLog.setAction(message("Mechanism.ServerStatus."+serverStatus));
		mechanismLog.setRemark(remark);
		mechanismLog.setOperator(admin);
		mechanismLog.setMechanism(mechanism);
		mechanismLogService.save(mechanismLog);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

}