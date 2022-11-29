/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PatientMechanism;
import net.shenzhou.entity.PatientMechanism.HealthType;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.PatientMechanismService;
import net.shenzhou.service.RechargeLogService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 综合统计
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("comprehensiveController")
@RequestMapping("/admin/comprehensive")
public class ComprehensiveController extends BaseController {


	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService ;
	@Resource(name = "rechargeLogServiceImpl")
	private RechargeLogService rechargeLogService ;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService ;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService ;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService ;
	@Resource(name = "patientMechanismServiceImpl")
	private PatientMechanismService patientMechanismService ;
	
	
	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(ModelMap model,Long mechanismId) {
		Mechanism mechanism = mechanismService.find(mechanismId);
		Map<String,Object> query_map = new HashMap<String, Object>(); 
		query_map.put("mechanism", mechanism);
		BigDecimal sumRecharge = rechargeLogService.sumRecharge(query_map);//充值总额
		Page<Member> members = memberService.getMembers(query_map);
		Page<Doctor> doctors = doctorService.findPage(new Pageable()); 
//		Set<Member> patients = new HashSet<Member>();
//		if (mechanism!=null) {
//			patients = mechanism.getPatientMembers();
//		}else{
//			for (Member patient : memberService.findAll()) {
//				if (patient.getParent()!=null) {
//					patients.add(patient);
//				}
//			}
//		}
		
		/**在康护**/
    	Integer countHealth = 0;
    	/**挂起**/
    	Integer countHang = 0;
    	/**休疗程**/
    	Integer countHughCourse = 0;
    	/**已康复**/
    	Integer countAlreadyRecovery = 0;
        if (mechanism==null) {
        	List<PatientMechanism> patientMechanisms = patientMechanismService.findAll();
    		for (PatientMechanism patientMechanism : patientMechanisms) {
    			if (patientMechanism.getHealthType().equals(HealthType.health)) {
    				countHealth++;
    			}
    			if (patientMechanism.getHealthType().equals(HealthType.hang)) {
    				countHang++;
    			}
    			if (patientMechanism.getHealthType().equals(HealthType.hughCourse)) {
    				countHughCourse++;
    			}
    			if (patientMechanism.getHealthType().equals(HealthType.alreadyRecovery)) {
    				countAlreadyRecovery++;
    			}
    		}
		}else{
				countHealth = mechanism.getPatientMechanisms(HealthType.health).size();
				countHang = mechanism.getPatientMechanisms(HealthType.hang).size();
				countHughCourse = mechanism.getPatientMechanisms(HealthType.hughCourse).size();
				countAlreadyRecovery = mechanism.getPatientMechanisms(HealthType.alreadyRecovery).size();
		}
		
		Integer sumQuantity = orderService.getSumQuantity(query_map);//康复总课时
		model.addAttribute("sumQuantity", sumQuantity);
		model.addAttribute("countHealth", countHealth);
		model.addAttribute("countHang", countHang);
		model.addAttribute("countHughCourse", countHughCourse);
		model.addAttribute("countAlreadyRecovery", countAlreadyRecovery);
		
		model.addAttribute("sumMember", mechanism!=null?mechanism.getMembers().size():members.getTotal());
		model.addAttribute("sumDoctor", mechanism!=null?mechanism.getDoctorMechanismRelation().size():doctors.getTotal());
		
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("sumRecharge", sumRecharge);
		model.addAttribute("mechanisms", mechanismService.findAll());
		return "/admin/comprehensive/view";
	}
	
}