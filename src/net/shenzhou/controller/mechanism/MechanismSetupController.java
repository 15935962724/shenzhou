/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.Message;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismSetup;
import net.shenzhou.entity.MechanismSetup.AchievementsType;
import net.shenzhou.entity.MechanismSetup.ChargeType;
import net.shenzhou.entity.MechanismSetup.OrderType;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismSetupService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 机构设置
 * @date 2018-1-11 17:51:50
 * @author wsr
 *
 */
@Controller("mechanismSetupController")
@RequestMapping("/mechanism/mechanismSetup")
public class MechanismSetupController extends BaseController {

	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	@Resource(name = "mechanismSetupServiceImpl")
	private MechanismSetupService mechanismSetupService;
	
	
	
	/**
	 * 绩效设置
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "achievements", method = RequestMethod.GET)
	public String achievements(ModelMap model, HttpServletRequest request) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("achievementsTypes", AchievementsType.values());
		return "mechanism/mechanism/achievements";
	}
	
	
	/**
	 * 机构修改最大预约天数
	 */
	@RequestMapping(value = "/updateMaxDay", method = RequestMethod.POST)
	public @ResponseBody
	Message updateMaxDay(Integer maxDay) {
		Doctor doctorC = doctorService.getCurrent();
		MechanismSetup mechanismSetup = doctorC.getDefaultDoctorMechanismRelation().getMechanism().getMechanismSetup();
		mechanismSetup.setMaxday(maxDay);
		mechanismSetupService.update(mechanismSetup);
		return SUCCESS_MESSAGE;
	}
	
	
	/**
	 * 机构修改接单类型
	 * @param orderType
	 * @return
	 */
	@RequestMapping(value = "/updateOrderType", method = RequestMethod.POST)
	public @ResponseBody
	Message updateOrderType(OrderType orderType) {
		Doctor doctorC = doctorService.getCurrent();
		MechanismSetup mechanismSetup = doctorC.getDefaultDoctorMechanismRelation().getMechanism().getMechanismSetup();
		mechanismSetup.setOrderType(orderType);
		mechanismSetupService.update(mechanismSetup);
		return SUCCESS_MESSAGE;
	}
	
	
	/**
	 * 机构修改接单类型
	 * @param orderType
	 * @return
	 */
	@RequestMapping(value = "/updateChargeType", method = RequestMethod.POST)
	public @ResponseBody
	Message updateChargeType(ChargeType chargeType,String chargeTypeRemark,String minute) {
		Doctor doctorC = doctorService.getCurrent();
		MechanismSetup mechanismSetup = doctorC.getDefaultDoctorMechanismRelation().getMechanism().getMechanismSetup();
		mechanismSetup.setChargeType(chargeType);
		mechanismSetup.setChargeTypeRemark(chargeTypeRemark);
		if(!chargeType.equals(ChargeType.course)){
			mechanismSetup.setIsMinute(1.0);
		}else{
			mechanismSetup.setIsMinute(Double.valueOf(minute));
		}
		mechanismSetupService.update(mechanismSetup);
		return SUCCESS_MESSAGE;
	}
	
	
	
	
	/**
	 * 机构、医师劳务分配占比设置
	 * @param achievementsType
	 * @param mechanismProportion
	 * @param doctorProportion
	 * @return
	 */
	@RequestMapping(value = "/updateProportion", method = RequestMethod.POST)
	public @ResponseBody
	Message updateProportion(AchievementsType achievementsType,BigDecimal mechanismProportion,BigDecimal doctorProportion) {
		Doctor doctorC = doctorService.getCurrent();
		MechanismSetup mechanismSetup = doctorC.getDefaultDoctorMechanismRelation().getMechanism().getMechanismSetup();
		mechanismSetup.setAchievementsType(achievementsType);
		mechanismSetup.setMechanismProportion(mechanismProportion);
		mechanismSetup.setDoctorProportion(doctorProportion);
		mechanismSetupService.update(mechanismSetup);
		return SUCCESS_MESSAGE;
	}
	
	
	/**
	 * 阶梯考核设置
	 * @param reduceMoney
	 * @param reduceProportion
	 * @return
	 */
	@RequestMapping(value = "/updateMoneyAndProportion", method = RequestMethod.POST)
	public @ResponseBody
	Message updateMoneyAndProportion(BigDecimal reduceMoney,BigDecimal reduceProportion) {
		Doctor doctorC = doctorService.getCurrent();
		MechanismSetup mechanismSetup = doctorC.getDefaultDoctorMechanismRelation().getMechanism().getMechanismSetup();
		mechanismSetup.setReduceMoney(reduceMoney);
		mechanismSetup.setReduceProportion(reduceProportion);
		mechanismSetupService.update(mechanismSetup);
		return SUCCESS_MESSAGE;
	}
	
	
	
}