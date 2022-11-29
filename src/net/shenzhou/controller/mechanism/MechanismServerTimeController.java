/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.Message;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismServerTime;
import net.shenzhou.entity.MechanismSetup.ChargeType;
import net.shenzhou.entity.MechanismSetup.OrderType;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismServerTimeService;
import net.shenzhou.util.JsonUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 机构的服务时间
 * @date 2018-1-11 10:14:09
 * @author wsr
 *
 */
@Controller("mechanismServerTimeController")
@RequestMapping("/mechanism/mechanismServerTime")
public class MechanismServerTimeController extends BaseController {

	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	@Resource(name = "mechanismServerTimeServiceImpl")
	private MechanismServerTimeService mechanismServerTimeService;
	
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String build(ModelMap model, HttpServletRequest request) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("mechanismSetup", mechanism.getMechanismSetup());
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("chargeTypes", ChargeType.values());
		model.addAttribute("mechanismServerTimes", new ArrayList<MechanismServerTime>(mechanism.getMechanismServerTimes()));
		return "mechanism/mechanism_server_time/list";
	}

	
	
	/**
	 * 新增机构服务时间
	 * @param mechanismServerTime
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(MechanismServerTime mechanismServerTime, RedirectAttributes redirectAttributes) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		if (!isValid(mechanismServerTime)) {
			return ERROR_VIEW;
		}
		mechanismServerTime.setIsDeleted(false);
		mechanismServerTime.setMechanism(mechanism);
		mechanismServerTimeService.save(mechanismServerTime);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 修改服务时间
	 * @param mechanismServerTime
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(MechanismServerTime mechanismServerTime, RedirectAttributes redirectAttributes) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		MechanismServerTime pMechanismServerTime = mechanismServerTimeService.find(mechanismServerTime.getId());
				
		if (!isValid(mechanismServerTime)) {
			return ERROR_VIEW;
		}
		BeanUtils.copyProperties(mechanismServerTime, pMechanismServerTime, new String[] { "id", "createDate", "modifyDate", "isDeleted", "mechanism"});
		mechanismServerTimeService.update(pMechanismServerTime);
//		mechanismServerTime.setIsDeleted(false);
//		mechanismServerTime.setMechanism(mechanism);
//		mechanismServerTimeService.save(mechanismServerTime);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		if (id != null) {
			mechanismServerTimeService.delete(id);
		}
		return SUCCESS_MESSAGE;
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.POST)
	public @ResponseBody
	String show(Long id) {
			MechanismServerTime mechanismServerTime = mechanismServerTimeService.find(id);
		return JsonUtils.toJson(mechanismServerTime);
	}

}