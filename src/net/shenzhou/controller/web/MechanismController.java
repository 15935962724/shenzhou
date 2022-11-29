/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.EvaluateService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.service.VisitMessageService;
import net.shenzhou.util.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 服务
 * @date 2017-10-25 11:53:31
 * @author fl
 *
 */
@Controller("webMechanismController")
@RequestMapping("/web/mechanism")
public class MechanismController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService ;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService ;
	@Resource(name = "visitMessageServiceImpl")
	private VisitMessageService visitMessageService ;
	@Resource(name = "evaluateServiceImpl")
	private EvaluateService evaluateService ;
	@Resource(name = "verificationServiceImpl")
	private VerificationService verificationService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	
	/**
	 * 机构列表跳转
	 */
	@RequestMapping(value = "/toMechanismList", method = RequestMethod.GET)
	public String toMechanismList(ModelMap model) {
		List<ServerProjectCategory> serverProjectCategoryList = serverProjectCategoryService.findRoots();
		model.addAttribute("serverProjectCategoryList", serverProjectCategoryList);
		return "/web/serve/mechanismList";
	}
	
	/**
	 * 机构列表
	 */
	@RequestMapping(value = "/mechanismList", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String mechanismList(ModelMap model,String scoreSort,String second,String distance,String longitude,String latitude,Integer pageNumber,String flag) {
		List<Map<String,Object>> data = mechanismService.webMechanismList(scoreSort,second,distance,longitude,latitude,pageNumber,flag);
		return JsonUtils.toJson(data);
	}
	
	/**
	 * 机构详情跳转
	 */
	@RequestMapping(value = "/mechanismDetails", method = RequestMethod.GET)
	public String mechanismDetails(ModelMap model,Long mechanismId) {
		Mechanism mechanism = mechanismService.find(mechanismId);
		List<Doctor> doctor_list = new ArrayList<Doctor>();
		for (DoctorMechanismRelation doctorMechanismRelation : mechanism.getDoctorMechanismRelation()) {
			if (doctorMechanismRelation.getAudit().equals(DoctorMechanismRelation.Audit.succeed)) {
				if(doctorMechanismRelation.getDoctor()!=null&&doctorMechanismRelation.getDoctor().getIsDeleted().equals(false)){
					doctor_list.add(doctorMechanismRelation.getDoctor());
				}
			}
		}
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("doctorList", doctor_list);
		return "/web/serve/mechanismDetails";
	}
	
}