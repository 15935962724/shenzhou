/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.shenzhou.Message;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DrillContent;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.RecoveryPlan;
import net.shenzhou.entity.RecoveryPlan.CheckState;
import net.shenzhou.entity.RecoveryRecord;
import net.shenzhou.entity.ServerProjectCategory;
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
import net.shenzhou.service.RecoveryRecordService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 康护计划
 * @date 2018-1-30 11:28:26
 * @author wsr
 *
 */
@Controller("mechanismRecoveryPlanController")
@RequestMapping("/mechanism/recoveryPlan")
public class RecoveryPlanController extends BaseController {

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
	@Resource(name = "recoveryRecordServiceImpl")
	private RecoveryRecordService recoveryRecordService;
	

	/**
	 * 添加康护计划
	 * @param recoveryPlan
	 * @param assessReportId
	 * @param drill
	 * @param patientMemberId
	 * @param redoctorId
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(RecoveryPlan recoveryPlan,Long assessReportId,String drill,Long patientMemberId,Long redoctorId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
        AssessReport assessReport = assessReportService.find(assessReportId);
		Doctor redoctor =  doctorService.find(redoctorId);
		
		
		Member patientMember =  memberService.find(patientMemberId);
		List <DrillContent> drillContents = recoveryPlan.getDrillContent();
		
		String[] drillContnts = drill.split("&");
		for (int i = 0; i < drillContnts.length; i++) {
			System.out.println(drillContnts[i]);
		}
		
		recoveryPlan.setIsDeleted(false);
		recoveryPlan.setCheckState(CheckState.succeed);
		recoveryPlan.setMechanism(mechanism);
		recoveryPlan.setPatient(patientMember);
		recoveryPlan.setRecoveryDoctor(redoctor);
		recoveryPlan.setAssessReport(assessReport);
		recoveryPlanService.save(recoveryPlan);
		
		for (String drillContent1 : drillContnts) {
			System.out.println(drillContent1);
			Long id = Long.valueOf(drillContent1.split(",")[0]);
			String count = drillContent1.split(",")[1];
			ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(id);
			DrillContent newdrillContent = new DrillContent();
			newdrillContent.setIsDeleted(false);
			newdrillContent.setServerProjectCategory(serverProjectCategory);
			newdrillContent.setTime(count);
			newdrillContent.setRecoveryplan(recoveryPlan);
			drillContentService.save(newdrillContent);
			drillContents.add(newdrillContent);
		}
		
		recoveryPlanService.update(recoveryPlan);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		
		return "redirect:/mechanism/assessReport/list.jhtml?patientMemberId="+patientMemberId;
	}
	
	
	/**
	 * 康户计划
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public @ResponseBody
	String view(String file ) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		try {
			file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
			 Date createDate = DateUtil.getStringtoDate(json.getString("createDate")+" 00:00:00", "yyyy-MM-dd HH:mm:ss"); ;
		     Date endDate = DateUtil.getStringtoDate(json.getString("endDate")+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
		    Long patientMemberId = json.getLong("patientMemberId");
		    
		    Long recoveryPlanId = json.getLong("recoveryPlanId");
			RecoveryPlan recoveryPlan = recoveryPlanService.find(recoveryPlanId);
			Map<String,Object> recoveryPlan_data = new HashMap<String, Object>();
			recoveryPlan_data.put("recoveryPlanId", recoveryPlan.getId());
			recoveryPlan_data.put("recoveryDoctorName", recoveryPlan.getRecoveryDoctor().getName());
			recoveryPlan_data.put("recoveryPlanStartTime", recoveryPlan.getStartTime());
			recoveryPlan_data.put("recoveryPlanEndTime", recoveryPlan.getEndTime());
			recoveryPlan_data.put("recoveryPlanShortTarget", recoveryPlan.getShortTarget());
			recoveryPlan_data.put("recoveryPlanLongTarget", recoveryPlan.getLongTarget());
			recoveryPlan_data.put("recoveryPlanSummary", recoveryPlan.getSummary());
			
			List<Map<String,Object>> recoveryPlanDrillContents = new ArrayList<Map<String,Object>>();
			for (DrillContent drillContent : recoveryPlan.getDrillContent()) {
				Map<String,Object> drillContent_map = new HashMap<String, Object>();
				drillContent_map.put("serverProjectCategoryName", drillContent.getServerProjectCategory().getName());
				drillContent_map.put("time", drillContent.getTime());
				recoveryPlanDrillContents.add(drillContent_map);
			}
			recoveryPlan_data.put("recoveryPlanDrillContents",recoveryPlanDrillContents);//训练内容
			
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("createDate", createDate);
			query_map.put("endDate", endDate);
			query_map.put("patientMember", memberService.find(patientMemberId));
			
			List<Map<String,Object>> recoveryRecords = new ArrayList<Map<String,Object>>();
			
			List<RecoveryRecord> recoveryRecordList = recoveryRecordService.findRecoveryRecordList(query_map);
			
			
			for (RecoveryRecord recoveryRecord : recoveryRecordList) {
				Map<String,Object> recoveryRecord_map = new HashMap<String, Object>();
				recoveryRecord_map.put("recoveryRecordId", recoveryRecord.getId());//id
				recoveryRecord_map.put("recoveryRecordCreateDate", recoveryRecord.getCreateDate());//创建时间
				recoveryRecord_map.put("recoveryDate", recoveryRecord.getRecoveryData());//康护时间
				recoveryRecord_map.put("recoveryContent", recoveryRecord.getRecoveryContent());//康护内容
				recoveryRecord_map.put("effect", recoveryRecord.getEffect());//效果总结
				recoveryRecords.add(recoveryRecord_map);
			}	
			
			
		
			
			
			recoveryPlan_data.put("recoveryRecords",recoveryRecords);//康护记录
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data",JsonUtils.toJson(recoveryPlan_data));
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
	 * 添加疗效总结
	 * @param assessReportId
	 * @param summary
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/summarySave", method = RequestMethod.POST)
	public @ResponseBody
	Message summarySave(Long recoveryPlanId, String summary) {
		RecoveryPlan recoveryPlan = recoveryPlanService.find(recoveryPlanId);
		recoveryPlan.setSummary(summary);
		recoveryPlanService.update(recoveryPlan);
			return Message.success("操作成功");
		
	}
	
	
	
	
	
	
	
	
}