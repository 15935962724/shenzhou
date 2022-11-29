/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Evaluate;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.BankCardService;
import net.shenzhou.service.BeforehandLoginService;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.CartService;
import net.shenzhou.service.DoctorCategoryService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorPointLogService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.EvaluateService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.OrganizationService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.RSAService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.service.WithdrawDepositService;
import net.shenzhou.service.WorkDayItemService;
import net.shenzhou.service.WorkDayService;
import net.shenzhou.util.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 医师
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("webDoctorController")
@RequestMapping("/web/doctor")
public class DoctorController extends BaseController {
	

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
	@Resource(name = "verificationServiceImpl")
	private VerificationService verificationService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "bankCardServiceImpl")
	private BankCardService bankCardService;
	@Resource(name = "withdrawDepositServiceImpl")
	private WithdrawDepositService withdrawDepositService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	@Resource(name = "evaluateServiceImpl")
	private EvaluateService evaluateService;
	@Resource(name = "doctorCategoryServiceImpl")
	private DoctorCategoryService doctorCategoryService;
	@Resource(name = "workDayServiceImpl")
	private WorkDayService workDayService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "workDayItemServiceImpl")
	private WorkDayItemService workDayItemService;
	@Resource(name = "organizationServiceImpl")
	private OrganizationService organizationService;
	@Resource(name = "beforehandLoginServiceImpl")
	private BeforehandLoginService beforehandLoginService;
	@Resource(name = "doctorPointLogServiceImpl")
	private DoctorPointLogService doctorPointLogService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	
	
	/**
	 * 医生列表跳转
	 */
	@RequestMapping(value = "/toDoctorList", method = RequestMethod.GET)
	public String toPerfect(ModelMap model) {
		List<ServerProjectCategory> serverProjectCategoryList = serverProjectCategoryService.findRoots();
		model.addAttribute("serverProjectCategoryList", serverProjectCategoryList);
		return "/web/serve/doctorList";
	}
	
	
	/**
	 * 医师列表
	 */
	@RequestMapping(value = "/doctorList", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String toDoctorList(ModelMap model,String scoreSort,String second,String distance,String longitude,String latitude,String pageNumber,String flag) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		map = doctorService.webDoctorList(scoreSort,second,distance,longitude,latitude,pageNumber,flag);
		List<Map<String,Object>> data = (List<Map<String, Object>>) map.get("doctors");
		return JsonUtils.toJson(data);
	}
	
	/**
	 * 筛选医生
	 */
	@RequestMapping(value = "/filtrateDoctor", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String filtrateDoctor(ModelMap model,String price_min,String price_max,String serverProjectCategoryId,String doctorName,String reserveDate,String startDate,String endDate,String service,String sex,Integer pageNumber,String longitude,String latitude) {
		List<Map<String ,Object>> data_list = doctorService.filtrateDoctor(price_min,price_max,serverProjectCategoryId,doctorName,reserveDate,startDate,endDate,service,sex,pageNumber,longitude,latitude);
		return JsonUtils.toJson(data_list);
	}
	
	/**
	 * 医师详情
	 */
	@RequestMapping(value = "/doctorDetails", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
	public String toDoctorList(ModelMap model,Long doctorId) {
		Doctor doctor = doctorService.find(doctorId);
		List<Mechanism> mechanisms = new ArrayList<Mechanism>();
		for(DoctorMechanismRelation dmr : doctor.getDoctorMechanismRelations(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed)){
			mechanisms.add(dmr.getMechanism());
		}
		
		List<Project> projects = projectService.getProjectByDoctor(doctor);
		List<Project> doctor_projects = new ArrayList<Project>();
		for(Project project : projects){
			for(Mechanism mechanism : mechanisms){
				if(project.getMechanism().equals(mechanism)){
					doctor_projects.add(project);
				}
			}
		}
		List<DoctorMechanismRelation> doctorMechanismRelations = doctor.getDoctorMechanismRelations(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed);
		List<String> mechanismName_list = new ArrayList<String>();
		for(DoctorMechanismRelation doctorMechanismRelation : doctorMechanismRelations){
			mechanismName_list.add(doctorMechanismRelation.getMechanism().getName());
		}
		List<Evaluate> evaluateList = evaluateService.findList(doctor);
		
		model.addAttribute("doctor", doctor);
		model.addAttribute("doctor_projects", doctor_projects);
		model.addAttribute("mechanismName_list", mechanismName_list);
		model.addAttribute("evaluateList", evaluateList);
		model.addAttribute("occupationName", doctor.getDoctorCategory().getName());
		model.addAttribute("evaluateSize", evaluateList.size());
		return "/web/serve/doctorDetails";
	}
	
}
 
