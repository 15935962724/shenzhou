/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.FileInfo.FileType;
import net.shenzhou.Message;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.AssessReportImage;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.DrillContent;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.PatientMechanism.HealthType;
import net.shenzhou.entity.RecoveryPlan;
import net.shenzhou.entity.RecoveryPlan.CheckState;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.AssessReportImageService;
import net.shenzhou.service.AssessReportService;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.DrillContentService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.RechargeLogService;
import net.shenzhou.service.RecoveryPlanService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 会员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("mechanismAssessReportController")
@RequestMapping("/mechanism/assessReport")
public class AssessReportController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "assessReportServiceImpl")
	private AssessReportService assessReportService;
	
	@Resource(name = "recoveryPlanServiceImpl")
	private RecoveryPlanService recoveryPlanService;
	@Resource(name = "drillContentServiceImpl")
	private DrillContentService drillContentService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "rechargeLogServiceImpl")
	private RechargeLogService rechargeLogService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "assessReportImageServiceImpl")
	private AssessReportImageService assessReportImageService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	

	/**
	 * 添加诊评报告
	 * @param assessReport
	 * @param patientMemberId
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public String save(AssessReport assessReport,RecoveryPlan recoveryPlan,Long orderId,String drill,Long patientMemberId,Long auditDoctorId,Long doctorId,Long redoctorId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
//		
////		User user = userService.getCurrent();
////		Mechanism mechanism = user.getMechanism();
//		
//		Doctor doctorC = doctorService.getCurrent();
//		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
//		
//		Order order = orderService.find(orderId);
//		Doctor doctor = doctorService.find(doctorId);
//		Doctor auditDoctor =  doctorService.find(auditDoctorId);
//		Doctor redoctor =  doctorService.find(redoctorId);
//		
//		for (Iterator<AssessReportImage> iterator = assessReport.getAssessReportImages().iterator(); iterator.hasNext();) {
//			AssessReportImage assessReportImage = iterator.next();
//			if (assessReportImage == null || assessReportImage.isEmpty()) {
//				iterator.remove();
//				continue;
//			}
//			if (assessReportImage.getFile() != null && !assessReportImage.getFile().isEmpty()) {
//				if (!fileService.isValid(FileType.image, assessReportImage.getFile())) {
//					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
//					return "redirect:/mechanism/order/view.jhtml?id="+orderId;
//				}
//			}
//		}
//		
//		Member patientMember =  memberService.find(patientMemberId);
//		List <DrillContent> drillContents = recoveryPlan.getDrillContent();
//		
//		String[] drillContnts = drill.split("&");
//		for (int i = 0; i < drillContnts.length; i++) {
//			System.out.println(drillContnts[i]);
//		}
//		
//		
//		recoveryPlan.setIsDeleted(false);
//		recoveryPlan.setCheckState(CheckState.succeed);
//		recoveryPlan.setMechanism(mechanism);
//		recoveryPlan.setPatient(order==null?patientMember:order.getPatientMember());
//		recoveryPlan.setRecoveryDoctor(redoctor);
//		recoveryPlanService.save(recoveryPlan);
//		
//		for (String drillContent1 : drillContnts) {
//			System.out.println(drillContent1);
//			Long id = Long.valueOf(drillContent1.split(",")[0]);
//			String count = drillContent1.split(",")[1];
//			ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(id);
//			DrillContent newdrillContent = new DrillContent();
//			newdrillContent.setIsDeleted(false);
//			newdrillContent.setServerProjectCategory(serverProjectCategory);
//			newdrillContent.setTime(count);
//			newdrillContent.setRecoveryplan(recoveryPlan);
//			drillContentService.save(newdrillContent);
//			drillContents.add(newdrillContent);
//		}
//		
//		
//		assessReport.setMember(patientMember);
//		assessReport.setIsDeleted(false);
//		assessReport.setCheckState(CheckState.succeed);
//		assessReport.getRecoveryPlans().add(recoveryPlan);
//		
//		assessReport.setOrder(order);
//		assessReport.setDoctor(doctor);
//		assessReport.setAuditDoctor(auditDoctor);
//		assessReport.setRedoctor(redoctor);
//		assessReport.setMechanism(mechanism);
//		for (AssessReportImage assessReportImage : assessReport.getAssessReportImages()) {
//			assessReportImageService.build(assessReportImage);
//		}
//		Collections.sort(assessReport.getAssessReportImages());
//		
//		assessReportService.save(assessReport);
//		recoveryPlan.setAssessReport(assessReport);
//		recoveryPlanService.update(recoveryPlan);
//		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
//		if(orderId==null){
//			return "redirect:/mechanism/member/patient_list.jhtml";
//		}
//		return "redirect:/mechanism/order/view.jhtml?id="+orderId;
//	}
	
	
	/**
	 * 订单详情页添加评估报告
	 * @param assessReport
	 * @param orderId
	 * @param patientMemberId
	 * @param doctorId
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(AssessReport assessReport,Long orderId,Long patientMemberId,Long doctorId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		Order order = orderService.find(orderId);
		Doctor doctor = doctorService.find(doctorId);
		
		for (Iterator<AssessReportImage> iterator = assessReport.getAssessReportImages().iterator(); iterator.hasNext();) {
			AssessReportImage assessReportImage = iterator.next();
			if (assessReportImage == null || assessReportImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (assessReportImage.getFile() != null && !assessReportImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, assessReportImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:/mechanism/order/view.jhtml?id="+orderId;
				}
			}
		}
		
		Member patientMember =  memberService.find(patientMemberId);
		
		assessReport.setMember(patientMember);
		assessReport.setIsDeleted(false);
		assessReport.setCheckState(CheckState.succeed);
		
		assessReport.setOrder(order);
		assessReport.setDoctor(doctor);
//		assessReport.setAuditDoctor(auditDoctor);
//		assessReport.setRedoctor(redoctor);
		assessReport.setMechanism(mechanism);
		for (AssessReportImage assessReportImage : assessReport.getAssessReportImages()) {
			assessReportImageService.build(assessReportImage);
		}
		Collections.sort(assessReport.getAssessReportImages());
		
		assessReportService.save(assessReport);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		if(orderId==null){
			return "redirect:/mechanism/member/patient_list.jhtml";
		}
		return "redirect:/mechanism/order/view.jhtml?id="+orderId;
	}
	
	
	/**
	 * 康护记录页添加评估报告
	 * @param assessReport
	 * @param orderId
	 * @param patientMemberId
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/assessReportSave", method = RequestMethod.POST)
	public String assessReportSave(AssessReport assessReport,Long orderId,Long patientMemberId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		Order order = orderService.find(orderId);
		
		for (Iterator<AssessReportImage> iterator = assessReport.getAssessReportImages().iterator(); iterator.hasNext();) {
			AssessReportImage assessReportImage = iterator.next();
			if (assessReportImage == null || assessReportImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (assessReportImage.getFile() != null && !assessReportImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, assessReportImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:/mechanism/order/patient_order.jhtml?patientMemberId="+patientMemberId;
				}
			}
		}
		
		Member patientMember =  memberService.find(patientMemberId);
		
		assessReport.setMember(patientMember);
		assessReport.setIsDeleted(false);
		assessReport.setCheckState(CheckState.succeed);
		
		assessReport.setOrder(order);
		assessReport.setDoctor(order.getDoctor());
//		assessReport.setAuditDoctor(auditDoctor);
//		assessReport.setRedoctor(redoctor);
		assessReport.setMechanism(mechanism);
		for (AssessReportImage assessReportImage : assessReport.getAssessReportImages()) {
			assessReportImageService.build(assessReportImage);
		}
		Collections.sort(assessReport.getAssessReportImages());
		
		assessReportService.save(assessReport);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:/mechanism/order/patient_order.jhtml?patientMemberId="+patientMemberId;
	}
	
	
	
	/**
	 * 健康档案
	 * @param patientMemberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/archives", method = RequestMethod.GET)
	public String archives(Long patientMemberId, ModelMap model) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		Member patientMember = memberService.find(patientMemberId);
		model.addAttribute("patientMember", patientMember);
		model.addAttribute("assessReports", patientMember.getAssessReports());
		return "/mechanism/member/archives";
	}
	
	/**
	 * 健康档案
	 * @param patientMemberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long patientMemberId, ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		List <DoctorMechanismRelation> doctorMechanismRelations = mechanism.getDoctorMechanismRelations(Audit.succeed);
		Member patientMember = memberService.find(patientMemberId);
		model.addAttribute("patientMember", patientMember);
		model.addAttribute("doctorMechanismRelations", doctorMechanismRelations);
		model.addAttribute("serverProjectCategorys", mechanism.getServerProjectCategorys());
		model.addAttribute("assessReports", patientMember.getAssessReports());
		model.addAttribute("healthTypes", HealthType.values());
		model.addAttribute("patientMechanism", patientMember.getPatientMechanism(mechanism));
		model.addAttribute("colour", "3");
		return "/mechanism/assess_report/list";
	}
	
	
	/**
	 * 康护计划
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/recoveryPlanList", method = RequestMethod.POST)
	public @ResponseBody
	String recoveryPlanList(Long patientMemberId ,String createDate,String endDate ) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		try {
			Member patientMember = memberService.find(patientMemberId);
			Map<String,Object> patientMember_data = new HashMap<String, Object>();
			patientMember_data.put("patientMemberId", patientMember.getId());
			patientMember_data.put("name", patientMember.getName());
			patientMember_data.put("logo", patientMember.getLogo());
			patientMember_data.put("gender", patientMember.getGender());
			patientMember_data.put("birth", patientMember.getBirth());
			patientMember_data.put("createDate", patientMember.getCreateDate());
			
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("createDate", DateUtil.getStringtoDate(createDate,"yyyy-MM-dd HH:mm:ss"));
			query_map.put("endDate", DateUtil.getStringtoDate(endDate,"yyyy-MM-dd HH:mm:ss"));
			query_map.put("patientMember", patientMember);
			
			List<RecoveryPlan> recoveryPlanList = recoveryPlanService.findRecoveryPlanList(query_map);
			
			List<Map<String,Object>> assessReport_list = new ArrayList<Map<String,Object>>();
			
			for (RecoveryPlan recoveryPlan : recoveryPlanList) {
				Map<String,Object> data_map = new HashMap<String, Object>();
				data_map.put("recoveryPlanId", recoveryPlan.getId());//id
				data_map.put("recoveryPlanCreateDate", recoveryPlan.getCreateDate());//创建时间
				data_map.put("recoveryPlanStartTime", recoveryPlan.getStartTime());//工作开始时间
				data_map.put("recoveryPlanEndTime", recoveryPlan.getEndTime());	//工作结束时间
				data_map.put("recoveryPlanShortTarget", recoveryPlan.getShortTarget());//短期目标
				data_map.put("recoveryPlanLongTarget", recoveryPlan.getLongTarget());//长期目标
				data_map.put("recoveryPlanRecoveryProject", recoveryPlan.getRecoveryProject());//康护项目
				List<Map<String,Object>> drillContent_List = new ArrayList<Map<String,Object>>();
				for (DrillContent drillContent : recoveryPlan.getDrillContent()) {
					Map<String,Object> drillContent_map = new HashMap<String, Object>();
					drillContent_map.put("drillContentServerProjectCategoryName", drillContent.getServerProjectCategory().getName());
					drillContent_map.put("drillContentTime", drillContent.getTime());
					drillContent_List.add(drillContent_map);
				}
				data_map.put("recoveryPlanDrillContent_List", drillContent_List);//训练内容
				data_map.put("recoveryPlanSummary", recoveryPlan.getSummary());//疗效总结
				data_map.put("recoveryPlanCheckState", recoveryPlan.getCheckState());//审核状态
				data_map.put("recoveryPlanRecoveryDoctorName",recoveryPlan.getRecoveryDoctor()==null?"":recoveryPlan.getRecoveryDoctor().getName());//康护技师姓名
				data_map.put("recoveryPlanPatientName", recoveryPlan.getPatient()==null?"":recoveryPlan.getPatient().getName());//患者姓名
				data_map.put("recoveryPlanMechanismName", recoveryPlan.getMechanism()==null?"":recoveryPlan.getMechanism().getName());//机构名称
				assessReport_list.add(data_map);
			}
			Map<String ,Object> assessReportlist = new HashMap<String, Object>();
			assessReportlist.put("assessReportlist", assessReport_list);
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data",JsonUtils.toJson(assessReportlist));
			return JsonUtils.toString(map) ;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			return JsonUtils.toString(map);
		}
		
	}
	
	
	
	/**
	 * 查阅其他计划
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String view(Long id) {
		AssessReport assessReport = assessReportService.find(id);
		List<RecoveryPlan> recoveryPlans = new ArrayList<RecoveryPlan>();
		for (RecoveryPlan recoveryPlan : assessReport.getRecoveryPlans()) {
			if (recoveryPlan.getIsDeleted()) {
				recoveryPlans.add(recoveryPlan);
			}
		}
		
		System.out.println(recoveryPlans.size()+"条数据");
			return JsonUtils.toJson(recoveryPlans);
		
	}
	
	
	
	
	
}