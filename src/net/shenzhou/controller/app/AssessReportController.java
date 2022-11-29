/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.Message;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.DrillContent;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PatientMechanism.HealthType;
import net.shenzhou.entity.RecoveryPlan;
import net.shenzhou.entity.RecoveryPlan.CheckState;
import net.shenzhou.entity.RecoveryRecord;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.AssessReportService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.DrillContentService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.RecoveryPlanService;
import net.shenzhou.service.RecoveryRecordService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.util.ComparatorDate;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 评估报告
 * @date 2017-6-9 16:50:54
 * @author wsr
 *
 */
@Controller("appAssessReportController")
@RequestMapping("/app/assessReport")
public class AssessReportController extends BaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "assessReportServiceImpl")
	private AssessReportService assessReportService;
	@Resource(name = "recoveryPlanServiceImpl")
	private RecoveryPlanService recoveryPlanService;
	@Resource(name = "recoveryRecordServiceImpl")
	private RecoveryRecordService recoveryRecordService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "drillContentServiceImpl")
	private DrillContentService drillContentService;
	
	
	
	
	
	/**
	 *  
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/assessReport/list.jhtml?file={patientMemberId:"18"}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
			Member patientMember = memberService.find(json.getLong("patientMemberId"));
			Map<String,Object> patientMember_data = new HashMap<String, Object>();
			patientMember_data.put("name", patientMember.getName());
			patientMember_data.put("logo", patientMember.getLogo());
			patientMember_data.put("gender", patientMember.getGender());
			patientMember_data.put("birth", patientMember.getBirth());
			patientMember_data.put("createDate", patientMember.getCreateDate());
			
			List<Map<String,Object>> assessReport_list = new ArrayList<Map<String,Object>>();
			for (AssessReport assessReport : patientMember.getAssessReports()) {
				Map<String,Object> assessReport_data = new HashMap<String, Object>();
				//System.out.println("orderId:"+order.getId());
				if (assessReport!=null) {
					assessReport_data.put("id", assessReport.getId());
					assessReport_data.put("diseaseName", assessReport.getDiseaseName());
					assessReport_data.put("nowExplain", assessReport.getNowExplain());
					assessReport_data.put("assessResult", assessReport.getAssessResult());
					assessReport_data.put("assessResultImages",assessReport.getAssessReportImages());
					assessReport_data.put("doctorName", assessReport.getDoctor().getName());
					assessReport_data.put("mechanismName",assessReport.getDoctor().getMechanism().getName());
					assessReport_data.put("createDate", assessReport.getCreateDate());
					List<Map<String ,Object>> assessReport_lsit = new ArrayList<Map<String ,Object>>();
					System.out.println(assessReport.getRecoveryPlans().size());
					for (RecoveryPlan recoveryPlan : assessReport.getRecoveryPlans()) {
						System.out.println("recoveryPlanId:"+recoveryPlan.getId());
						Map<String ,Object> recoveryPlan_map = new HashMap<String, Object>();
						recoveryPlan_map.put("id", recoveryPlan.getId());//id
						recoveryPlan_map.put("startTime", recoveryPlan.getStartTime());//开始时间
						recoveryPlan_map.put("endTime", recoveryPlan.getEndTime());//结束时间
						recoveryPlan_map.put("shortTarget", recoveryPlan.getShortTarget());//短期目标
						recoveryPlan_map.put("longTarget", recoveryPlan.getLongTarget());//长期目标
						recoveryPlan_map.put("recoveryProject", recoveryPlan.getRecoveryProject());//康复项目
						recoveryPlan_map.put("summary", recoveryPlan.getSummary());//疗效总结
						if(recoveryPlan.getRecoveryDoctor()!=null){
							recoveryPlan_map.put("recoveryDoctoyDoctorName", recoveryPlan.getRecoveryDoctor().getName());//康护师
						}else{
							recoveryPlan_map.put("recoveryDoctoyDoctorName", "");//康护师
						}
						List<Map<String ,Object>> drillContent_lsit = new ArrayList<Map<String ,Object>>();
						for (DrillContent drillContent : recoveryPlan.getDrillContent()) {
							Map<String ,Object> drillContent_map = new HashMap<String, Object>();
							drillContent_map.put("time", drillContent.getTime());
							drillContent_map.put("serverProjectCategory", drillContent.getServerProjectCategory().getName());
							drillContent_lsit.add(drillContent_map);
						}
						recoveryPlan_map.put("drillContents", drillContent_lsit);//康护记录
						assessReport_lsit.add(recoveryPlan_map);
					}
					assessReport_data.put("recoveryPlans", assessReport_lsit);
					assessReport_list.add(assessReport_data);
				}
			}
			Map<String,Object> data_map = new HashMap<String, Object>();
			
			data_map.put("patientMember", patientMember_data);
			data_map.put("assessReport_list",assessReport_list);
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data",JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	

	/**
	 * 诊评报告详情
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/assessReport/view.jhtml?file={assessReportId:"4"}
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void view(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        
	        AssessReport assessReport = assessReportService.find(json.getLong("assessReportId"));//诊评报告
			Member patientMember = assessReport.getOrder().getPatientMember();//患者
			
			Map<String,Object> patientMember_data = new HashMap<String, Object>();
			patientMember_data.put("name", patientMember.getName());
			patientMember_data.put("logo", patientMember.getLogo());
			patientMember_data.put("gender", patientMember.getGender());
			patientMember_data.put("birth", patientMember.getBirth());
			patientMember_data.put("createDate", patientMember.getCreateDate());
			Map<String,Object> assessReport_data = new HashMap<String, Object>();
			assessReport_data.put("id", assessReport.getId());
			assessReport_data.put("diseaseName", assessReport.getDiseaseName());
			assessReport_data.put("nowExplain", assessReport.getNowExplain());
			assessReport_data.put("assessResult", assessReport.getAssessResult());
			assessReport_data.put("assessResultImages",assessReport.getAssessReportImages());
			assessReport_data.put("mechanismName", assessReport.getOrder().getProject().getDoctor().getMechanism().getName());
			assessReport_data.put("doctorName", assessReport.getOrder().getProject().getDoctor().getName());
			assessReport_data.put("createDate", assessReport.getCreateDate());
			
			List<Map<String,Object>> recoveryPlans = new ArrayList<Map<String,Object>>();
			for (RecoveryPlan recoveryPlan : assessReport.getRecoveryPlans()) {
				Map<String,Object> recoveryPlan_map = new HashMap<String, Object>();
				System.out.println("recoveryPlanId:"+recoveryPlan.getId());
				recoveryPlan_map.put("id", recoveryPlan.getId());//id
				recoveryPlan_map.put("startTime", recoveryPlan.getStartTime());//开始时间
				recoveryPlan_map.put("endTime", recoveryPlan.getEndTime());//结束时间
				recoveryPlan_map.put("shortTarget", recoveryPlan.getShortTarget());//短期目标
				recoveryPlan_map.put("longTarget", recoveryPlan.getLongTarget());//长期目标
				recoveryPlan_map.put("recoveryProject", recoveryPlan.getRecoveryProject());//康复项目
				recoveryPlan_map.put("summary", recoveryPlan.getSummary());//疗效总结
				recoveryPlan_map.put("recoveryDoctoyDoctorName", recoveryPlan.getRecoveryDoctor().getName());//康护师
				List<Map<String ,Object>> drillContent_lsit = new ArrayList<Map<String ,Object>>();
				for (DrillContent drillContent : recoveryPlan.getDrillContent()) {
					Map<String ,Object> drillContent_map = new HashMap<String, Object>();
					drillContent_map.put("time", drillContent.getTime());
					drillContent_map.put("serverProjectCategory", drillContent.getServerProjectCategory().getName());
					drillContent_lsit.add(drillContent_map);
				}
				recoveryPlan_map.put("drillContents", drillContent_lsit);//康护记录
				List<Map<String ,Object>> recoveryRecord_lsit = new ArrayList<Map<String ,Object>>();
				for (RecoveryRecord recoveryRecord : recoveryPlan.getRecoveryRecordList()) {
					Map<String ,Object> recoveryRecord_map = new HashMap<String, Object>();
					recoveryRecord_map.put("id", recoveryRecord.getId());
					recoveryRecord_map.put("recoveryData", recoveryRecord.getRecoveryData());
					recoveryRecord_map.put("recoveryContent", recoveryRecord.getRecoveryContent());
					recoveryRecord_lsit.add(recoveryRecord_map);
				}
				recoveryPlan_map.put("recoveryRecords", recoveryRecord_lsit);//康护记录
				
				recoveryPlans.add(recoveryPlan_map);
			}
			Map<String,Object> data_map = new HashMap<String, Object>();
			
			data_map.put("patientMember", patientMember_data);
			data_map.put("assessReport", assessReport_data);
			data_map.put("recoveryPlans",recoveryPlans);
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data",JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	
	
	
	/**
	 * 评估报告
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/assessReportList", method = RequestMethod.GET)
	public void assessReportList(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
			Member patientMember = memberService.find(json.getLong("patientMemberId"));
			Map<String,Object> patientMember_data = new HashMap<String, Object>();
			patientMember_data.put("patientMemberId", patientMember.getId());
			patientMember_data.put("name", patientMember.getName());
			patientMember_data.put("logo", patientMember.getLogo());
			patientMember_data.put("gender", patientMember.getGender());
			patientMember_data.put("birth", patientMember.getBirth());
			patientMember_data.put("createDate", patientMember.getCreateDate());
			
			List<Map<String,Object>> assessReport_list = new ArrayList<Map<String,Object>>();
			Map<String,Object> assessReport_data_null = new HashMap<String, Object>();
			
			List lists = new ArrayList();
			for (AssessReport assessReport : patientMember.getAssessReports()) {
				lists.add(assessReport.getCreateDate());
			}
			ComparatorDate c = new ComparatorDate();
			Collections.sort(lists, c);  
			for (Object object : lists) {
				System.out.println(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", (Date) object));
			}
			if (lists.size()>0) {
				Date endDate = (Date) lists.get(lists.size()-1);
				System.out.println(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", endDate));
				Map<String,Object> query_map = new HashMap<String, Object>();
				query_map.put("createDate", new Date());
				query_map.put("endDate", endDate);
				query_map.put("patientMember", patientMember);
				List<RecoveryPlan> recoveryPlanList = recoveryPlanService.findRecoveryPlanList(query_map);
				if (recoveryPlanList.size()>0) {
					assessReport_data_null.put("id", null);
					assessReport_data_null.put("diseaseName", "");
					assessReport_data_null.put("nowExplain", "");
					assessReport_data_null.put("assessResult", "");
					assessReport_data_null.put("assessResultImages",new ArrayList<Object>());
					assessReport_data_null.put("doctorName","");
					assessReport_data_null.put("mechanismName","");
					assessReport_data_null.put("createDate", new Date());
					assessReport_list.add(assessReport_data_null);
				}
			}
			
			for (AssessReport assessReport : patientMember.getAssessReports()) {
				Map<String,Object> assessReport_data = new HashMap<String, Object>();
				if (assessReport!=null) {
					assessReport_data.put("id", assessReport.getId());
					assessReport_data.put("diseaseName", assessReport.getDiseaseName());
					assessReport_data.put("nowExplain", assessReport.getNowExplain());
					assessReport_data.put("assessResult", assessReport.getAssessResult());
					assessReport_data.put("assessResultImages",assessReport.getAssessReportImages());
					assessReport_data.put("doctorName", assessReport.getDoctor().getName());
					assessReport_data.put("mechanismName",assessReport.getMechanism()==null?"":assessReport.getMechanism().getName());
					assessReport_data.put("createDate", assessReport.getCreateDate());
					assessReport_list.add(assessReport_data);
				}
			}
			Map<String,Object> data_map = new HashMap<String, Object>();
			
			data_map.put("patientMember", patientMember_data);
			data_map.put("assessReport_list",assessReport_list);
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data",JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	
	/**
	 * 康护计划
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/recoveryPlanList", method = RequestMethod.GET)
	public void recoveryPlanList(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        Date createDate = DateUtil.getStringtoDate(json.getString("createDate"), "yyyy-MM-dd HH:mm:ss");
	        Date endDate = DateUtil.getStringtoDate(json.getString("endDate"), "yyyy-MM-dd HH:mm:ss");
	        Long patientMemberId = json.getLong("patientMemberId");
			Member patientMember = memberService.find(patientMemberId);
			Map<String,Object> patientMember_data = new HashMap<String, Object>();
			patientMember_data.put("patientMemberId", patientMember.getId());
			patientMember_data.put("name", patientMember.getName());
			patientMember_data.put("logo", patientMember.getLogo());
			patientMember_data.put("gender", patientMember.getGender());
			patientMember_data.put("birth", patientMember.getBirth());
			patientMember_data.put("createDate", patientMember.getCreateDate());
			
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("createDate", createDate);
			query_map.put("endDate", endDate);
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
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	
	
	/**
	 * 康护记录
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/recoveryRecordList", method = RequestMethod.GET)
	public void recoveryRecordList(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        Date createDate = DateUtil.getStringtoDate(json.getString("createDate")+" 00:00:00", "yyyy-MM-dd HH:mm:ss"); ;
	        Date endDate = DateUtil.getStringtoDate(json.getString("endDate")+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
	        Long patientMemberId = json.getLong("patientMemberId");
			Member patientMember = memberService.find(patientMemberId);
			Map<String,Object> patientMember_data = new HashMap<String, Object>();
			patientMember_data.put("patientMemberId", patientMember.getId());
			patientMember_data.put("name", patientMember.getName());
			patientMember_data.put("logo", patientMember.getLogo());
			patientMember_data.put("gender", patientMember.getGender());
			patientMember_data.put("birth", patientMember.getBirth());
			patientMember_data.put("createDate", patientMember.getCreateDate());
			
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("createDate", createDate);
			query_map.put("endDate", endDate);
			query_map.put("patientMember", patientMember);
			
			List<RecoveryRecord> recoveryRecordList = recoveryRecordService.findRecoveryRecordList(query_map);
			
			List<Map<String,Object>> recoveryRecord_list = new ArrayList<Map<String,Object>>();
			
			for (RecoveryRecord recoveryRecord : recoveryRecordList) {
				Map<String,Object> data_map = new HashMap<String, Object>();
				data_map.put("recoveryRecordId", recoveryRecord.getId());//id
				data_map.put("recoveryRecordCreateDate", recoveryRecord.getCreateDate());//创建时间
				data_map.put("recoveryRecordDate", recoveryRecord.getRecoveryData());//康护时间
				data_map.put("recoveryRecordContent", recoveryRecord.getRecoveryContent());//康护内容
				data_map.put("recoveryRecordEffect", recoveryRecord.getEffect());//效果总结
				data_map.put("recoveryRecordPatientName", recoveryRecord.getPatient()==null?"":recoveryRecord.getPatient().getName());//患者姓名
				data_map.put("recoveryRecordMechanismName",recoveryRecord.getMechanism()==null?"":recoveryRecord.getMechanism().getName());//机构名称
				recoveryRecord_list.add(data_map);
			}
			
			Map<String ,Object> recoveryRecord_map = new HashMap<String, Object>();
			recoveryRecord_map.put("recoveryRecordlist", recoveryRecord_list);
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data",JsonUtils.toJson(recoveryRecord_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	
	
	/**
	 * 评估报告
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/assessReport", method = RequestMethod.GET)
	public void assessReport(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
			Member patientMember = memberService.find(json.getLong("patientMemberId"));
			Map<String,Object> patientMember_data = new HashMap<String, Object>();
			patientMember_data.put("patientMemberId", patientMember.getId());
			patientMember_data.put("name", patientMember.getName());
			patientMember_data.put("logo", patientMember.getLogo());
			patientMember_data.put("gender", patientMember.getGender());
			patientMember_data.put("birth", patientMember.getBirth());
			patientMember_data.put("createDate", patientMember.getCreateDate());
			
			List<Map<String,Object>> assessReport_list = new ArrayList<Map<String,Object>>();
			for (AssessReport assessReport : patientMember.getAssessReports()) {
				Map<String,Object> assessReport_data = new HashMap<String, Object>();
				if (assessReport!=null) {
					assessReport_data.put("id", assessReport.getId());
					assessReport_data.put("diseaseName", assessReport.getDiseaseName());
					assessReport_data.put("nowExplain", assessReport.getNowExplain());
					assessReport_data.put("assessResult", assessReport.getAssessResult());
					assessReport_data.put("assessResultImages",assessReport.getAssessReportImages());
					assessReport_data.put("doctorName", assessReport.getDoctor().getName());
					assessReport_data.put("mechanismName",assessReport.getDoctor().getMechanism().getName());
					assessReport_data.put("createDate", assessReport.getCreateDate());
					assessReport_list.add(assessReport_data);
				}
			}
			Map<String,Object> data_map = new HashMap<String, Object>();
			
			data_map.put("patientMember", patientMember_data);
			data_map.put("assessReport_list",assessReport_list);
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data",JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	
	/**
	 * 添加总结
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/addSummary", method = RequestMethod.GET)
	public void addSummary(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
			RecoveryPlan recoveryPlan = recoveryPlanService.find(json.getLong("recoveryPlanId"));
			String summary = json.getString("summary");
			recoveryPlan.setSummary(summary);
			recoveryPlanService.update(recoveryPlan);
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data","{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	
	
	/**
	 * 医生APP端查看患者健康档案
	 * @param file
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/patientAssessReport", method = RequestMethod.GET)
	public String patientAssessReport( String file, HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
        Long patientMemberId = json.getLong("patientMemberId");
        String safeKeyValue = json.getString("safeKeyValue");
		Doctor doctorC = doctorService.findBySafeKeyValue(safeKeyValue);
//		if (doctorC==null) {
//			JsonUtils.writeValue(response.getWriter(), data);
//			return safeKeyValue;
//		}
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		List <DoctorMechanismRelation> doctorMechanismRelations = mechanism.getDoctorMechanismRelations(Audit.succeed);
		Member patientMember = memberService.find(patientMemberId);
		model.addAttribute("patientMember", patientMember);
		model.addAttribute("doctorMechanismRelations", doctorMechanismRelations);
		model.addAttribute("serverProjectCategorys", mechanism.getServerProjectCategorys());
		model.addAttribute("assessReports", patientMember.getAssessReports());
		model.addAttribute("healthTypes", HealthType.values());
		model.addAttribute("doctor", doctorC);
		model.addAttribute("patientMember", patientMember);
		return "/app/assess_report/doctor_patient_assess_report";
	}

	
	/**
	 * 用户查看患者的健康档案
	 * @param file
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/memberPatientAssessReport", method = RequestMethod.GET)
	public String memberPatientAssessReport( String file, HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
        Long patientMemberId = json.getLong("patientMemberId");
        String safeKeyValue = json.getString("safeKeyValue");
		Member member = memberService.findBySafeKeyValue(safeKeyValue);
		Member patientMember = memberService.find(patientMemberId);
		model.addAttribute("patientMember", patientMember);
		model.addAttribute("assessReports", patientMember.getAssessReports());
		model.addAttribute("healthTypes", HealthType.values());
		model.addAttribute("member", member);
		model.addAttribute("patientMember", patientMember);
		return "/app/assess_report/member_patient_assess_report";
	}
	
	
	/**
	 * 添加康复计划
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
		
        AssessReport assessReport = assessReportService.find(assessReportId);
		Doctor redoctor =  doctorService.find(redoctorId);
		Mechanism mechanism =  redoctor.getDefaultDoctorMechanismRelation().getMechanism();
		
		Member patientMember =  memberService.find(patientMemberId);
		List <DrillContent> drillContents = recoveryPlan.getDrillContent();
		
		String[] drillContnts = drill.split("#");
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
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("patientMemberId", patientMemberId);
		data.put("safeKeyValue", redoctor.getSafeKey().getValue());
		String file = JsonUtils.toJson(data).replaceAll("\\{","%7B")
                .replaceAll("\\}","%7D")
                .replaceAll("\"","%22")
                .replaceAll("\\:","%3A")
                .replaceAll("\\,","%2C");
		System.out.println(file);
		return "redirect:patientAssessReport.jhtml?file="+file;
	}
	
	/**
	 * 保存疗效总结
	 * @param recoveryPlanId
	 * @param summary
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


