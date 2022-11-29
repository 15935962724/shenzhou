/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shenzhou.Config;
import net.shenzhou.FileInfo;
import net.shenzhou.FileInfo.FileType;
import net.shenzhou.FileInfo.OrderType;
import net.shenzhou.Message;
import net.shenzhou.Setting;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Doctor.Status;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Evaluate;
import net.shenzhou.entity.Management;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.MechanismImage;
import net.shenzhou.entity.MechanismRank;
import net.shenzhou.entity.MechanismRole;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.VisitMessage;
import net.shenzhou.entity.MechanismSetup.AchievementsType;
import net.shenzhou.entity.WorkDate;
import net.shenzhou.entity.WorkTarget;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.DoctorCategoryService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.ManagementService;
import net.shenzhou.service.MechanismCategoryService;
import net.shenzhou.service.MechanismImageService;
import net.shenzhou.service.MechanismRankService;
import net.shenzhou.service.MechanismRoleService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.WorkDateService;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MapUtil;
import net.shenzhou.util.SettingUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 员工设置
 * @date 2018-1-18 10:01:51
 * @author wsr
 *
 */
@Controller("doctorMechanismRelationController")
@RequestMapping("/mechanism/doctorMechanismRelation")
public class DoctorMechanismRelationController extends BaseController {

	
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	@Resource(name = "mechanismRoleServiceImpl")
	private MechanismRoleService mechanismRoleService;
	@Resource(name = "doctorCategoryServiceImpl")
	private DoctorCategoryService doctorCategoryService;
	
	
	/**
	 * 审核
	 * @param id
	 * @param audit
	 * @param isEnabledExplain
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update_audit", method = RequestMethod.POST)
	public String update_audit(Long doctorMechanismRelationId,Audit audit, Long mechanismRoleId,Long doctorCategoryId, @RequestParam(defaultValue = "false")Boolean isAbout,
			@RequestParam(defaultValue = "0.0")BigDecimal dayWorkTarget,@RequestParam(defaultValue = "0.0")BigDecimal percentage, String statusExplain,
			Integer pageNumber,Integer pageSize,ModelMap model) {
		DoctorMechanismRelation doctorMechanismRelation = doctorMechanismRelationService.find(doctorMechanismRelationId);
		doctorMechanismRelation.setMechanismroles(new HashSet<MechanismRole>());
		doctorMechanismRelationService.update(doctorMechanismRelation);
		DoctorCategory doctorCategory = doctorCategoryService.find(doctorCategoryId);
		MechanismRole mechanismRole =  mechanismRoleService.find(mechanismRoleId);
		if (audit.equals(Audit.succeed)) {
			WorkTarget workTarget = doctorMechanismRelation.getWorkTarget();
			workTarget.setDayWorkTarget(dayWorkTarget);
			workTarget.setPercentage(percentage);
			doctorMechanismRelation.setWorkTarget(workTarget);
			doctorMechanismRelation.setIsAbout(isAbout);
			doctorMechanismRelation.getMechanismroles().add(mechanismRole);
			Doctor doctor = doctorMechanismRelation.getDoctor();
			doctor.setStatus(Status.allow);
			doctorService.update(doctor);
		}else{
			doctorMechanismRelation.setStatusExplain(statusExplain);
			
		}
		doctorMechanismRelation.setAudit(audit);
		doctorMechanismRelation.setDoctorCategory(doctorCategory);
		doctorMechanismRelationService.update(doctorMechanismRelation);
		if (pageNumber==null||pageSize==null) {
			return "redirect:/mechanism/doctor/list.jhtml";
		}
		return "redirect:/mechanism/doctor/list.jhtml?pageNumber="+pageNumber+"&pageSize="+pageSize;
		
	}
	
	
	
	/**
	 * 停用启用
	 * @param id
	 * @param isEnabled
	 * @param isEnabledExplain
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update_isEnabled", method = RequestMethod.POST)
	public String update_isEnabled(Long id,Boolean isEnabled,String isEnabledExplain,Integer pageNumber,Integer pageSize,ModelMap model) {
		DoctorMechanismRelation doctorMechanismRelation = doctorMechanismRelationService.find(id);
		doctorMechanismRelation.setIsEnabled(isEnabled);
//		if (!isEnabled) {
//			Doctor doctor = doctorMechanismRelation.getDoctor();
//			doctor.setStatus(Status.refuse);
//			doctorService.update(doctor);
//		}
		
		doctorMechanismRelation.setIsEnabledExplain(isEnabledExplain);
		doctorMechanismRelationService.update(doctorMechanismRelation);
		return "redirect:/mechanism/doctor/list.jhtml?pageNumber="+pageNumber+"&pageSize="+pageSize;
		
	}
	

}