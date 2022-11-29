/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DrillContent;
import net.shenzhou.entity.Information;
import net.shenzhou.entity.Information.ClassifyType;
import net.shenzhou.entity.Information.InformationType;
import net.shenzhou.entity.Information.StateType;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.RecoveryPlan;
import net.shenzhou.entity.RecoveryPlan.CheckState;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.AssessReportService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.InformationService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 消息
 * @date 2017-6-13 17:06:12
 * @author fl
 *
 */
@Controller("appInformationController")
@RequestMapping("/app/information")
public class InformationController extends BaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "assessReportServiceImpl")
	private AssessReportService assessReportService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "informationServiceImpl")
	private InformationService informationService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	
	/**
	 * 医生端消息列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/informationList", method = RequestMethod.GET)
	public void informationList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			Integer pageNumber = json.getInt("pageNumber");//页码
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			Map<String ,Object> map_date =informationService.getInformationByDoctor(doctor,pageNumber);
			printWriter.write(JsonUtils.toString(map_date)) ;
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	

	/**
	 * 消息详情
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/informationDetails", method = RequestMethod.GET)
	public void informationDetails(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
		try {
			Long informationId = json.getLong("informationId");
			Information information = informationService.find(informationId);
			/**判断消息类型**/
			//系统通知
			if(information.getInformationType().equals(InformationType.system)){
				information.setState(StateType.read);
				informationService.update(information);
				
				Map<String,Object> information_map = new HashMap<String,Object>();	
				information_map.put("message",information.getMessage());
				information_map.put("informationType",information.getInformationType());
				information_map.put("headline",information.getHeadline());
				
				Map<String,Object> data_map = new HashMap<String,Object>();	
				data_map.put("information",information_map );
				
				map.put("status", "200");
		        map.put("data",JsonUtils.toJson(data_map) );
		        map.put("message", "读取消息成功");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}else if(information.getInformationType().equals(InformationType.audit)){
				//审核通知处理(要返回三级联动数据)
				information.setState(StateType.read);
				informationService.update(information);
				
				AssessReport assessReport = assessReportService.find(information.getInformationId());
				Map<String,Object> assessReport_data = new HashMap<String, Object>();
				assessReport_data.put("id", assessReport.getId());
				assessReport_data.put("diseaseName", assessReport.getDiseaseName());
				assessReport_data.put("nowExplain", assessReport.getNowExplain());
				assessReport_data.put("assessResult", assessReport.getAssessResult());
				assessReport_data.put("assessResultImages",assessReport.getAssessReportImages());
				assessReport_data.put("doctorName", assessReport.getOrder().getProject().getDoctor().getName());
				assessReport_data.put("mechanismName",assessReport.getOrder().getProject().getDoctor().getMechanism().getName());
				assessReport_data.put("createDate", assessReport.getCreateDate());
				List<Map<String ,Object>> assessReport_lsit = new ArrayList<Map<String ,Object>>();
				
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
					recoveryPlan_map.put("recoveryDoctoyDoctorName", recoveryPlan.getRecoveryDoctor().getName());//康护师
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
				Map<String,Object> data_map = new HashMap<String, Object>();
				data_map.put("assessReport", assessReport_data);
				
				map.put("status", "200");
		        map.put("data",JsonUtils.toJson(data_map) );
		        map.put("message", "成功获取数据");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}else if(information.getInformationType().equals(InformationType.recovery)){
				Map<String,Object> data_map = new HashMap<String,Object>();
				
				information.setState(StateType.read);
				informationService.update(information);
				AssessReport assessReport =assessReportService.find(information.getInformationId()); 
				if(assessReport.getCheckState().equals(CheckState.succeed)){
					data_map.put("key", false);
				}else{
					data_map.put("key", true);
				}
				Order order = assessReport.getOrder();
				Member member = order.getPatientMember();
				
				data_map.put("patientMemberName", member.getName());
				data_map.put("age",DateUtil.getAge(member.getBirth()));
				data_map.put("gender", member.getGender());
				data_map.put("logo", member.getLogo());
				data_map.put("doctorName", assessReport.getDoctor().getName());
				data_map.put("createTime", assessReport.getCreateDate());
				data_map.put("nowExplain", assessReport.getNowExplain());
				data_map.put("assessResult", assessReport.getAssessResult());
				data_map.put("proposal", assessReport.getProposal());
				data_map.put("assessReportId", assessReport.getId());
				data_map.put("time",assessReport.getCreateDate() );
				data_map.put("informationId",json.getLong("informationId") );
				
				List<Map<String,Object>> serverProjectCategoryList = new ArrayList<Map<String,Object>>();
				for(ServerProjectCategory serverProjectCategory :assessReport.getDoctor().getMechanism().getServerProjectCategorys()){
					Map<String,Object> serverProjectCategory_map = new HashMap<String, Object>();
					serverProjectCategory_map.put("serverProjectCategoryName", serverProjectCategory.getName());
					serverProjectCategory_map.put("id", serverProjectCategory.getId());
					serverProjectCategoryList.add(serverProjectCategory_map);
				}
				
				data_map.put("serverProjectCategoryList",serverProjectCategoryList );
				
				map.put("status", "200");
		        map.put("data",JsonUtils.toJson(data_map) );
		        map.put("message", "成功获取数据");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}else if(information.getInformationType().equals(InformationType.order)){
				information.setState(StateType.read);
				informationService.update(information);
				
				Map<String,Object> information_map = new HashMap<String,Object>();	
				information_map.put("message",information.getMessage());
				information_map.put("informationType",information.getInformationType());
				information_map.put("headline",information.getHeadline());
				
				Map<String,Object> data_map = new HashMap<String,Object>();	
				data_map.put("information",information_map );
				
				map.put("status", "200");
		        map.put("data",JsonUtils.toJson(data_map) );
		        map.put("message", "读取消息成功");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}
	
	
	/**
	 * 医生端未读消息提示
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doctorInformationRead", method = RequestMethod.GET)
	public void doctorInformationRead(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			
			String safeKeyValue = json.getString("SafeKey");
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Boolean key = informationService.isDoctorUnread(doctor);
			
			Map<String,Object> data_map = new HashMap<String,Object>();
			data_map.put("stateType",key);
			
			map.put("status", "200");
	        map.put("data",JsonUtils.toJson(data_map) );
	        map.put("message", "成功");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message","失败");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	
	/**
	 * 用户端未读消息提示
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberInformationRead", method = RequestMethod.GET)
	public void memberInformationRead(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			
			String safeKeyValue = json.getString("safeKeyValue");
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			
			Boolean key = informationService.isMemberUnread(member);
			
			Map<String,Object> data_map = new HashMap<String,Object>();
			data_map.put("stateType",key);
			
			map.put("status", "200");
	        map.put("data",JsonUtils.toJson(data_map) );
	        map.put("message", "获取消息成功");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 医生端删除消息
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteDoctorInformation", method = RequestMethod.GET)
	public void deleteDoctorInformation(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			Long informationId = json.getLong("informationId");
			
			Information information = informationService.find(informationId);
			
			if(information.getState().equals(StateType.unread)&&information.getInformationType().equals(InformationType.audit)){
				map.put("status", "400");
				map.put("message","该未读消息不能删除");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(information.getState().equals(StateType.unread)&&information.getInformationType().equals(InformationType.recovery)){
				map.put("status", "400");
				map.put("message","该未读消息不能删除");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			information.setIsDeleted(true);
			informationService.update(information);
			
			
			map.put("status", "200");
	        map.put("data","{}");
	        map.put("message", "删除成功");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message","失败");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	
	/**
	 * 患者端消息列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberInformationList", method = RequestMethod.GET)
	public void memberInformationList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			Integer pageNumber = json.getInt("pageNumber");//页码
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			Map<String ,Object> map_date =informationService.getInformationByMember(member,pageNumber);
			printWriter.write(JsonUtils.toString(map_date)) ;
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 用户端删除消息
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteMemberInformation", method = RequestMethod.GET)
	public void deleteMemberInformation(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			Long informationId = json.getLong("informationId");
			
			Information information = informationService.find(informationId);
			information.setIsDeleted(true);
			informationService.update(information);
			
			
			map.put("status", "200");
	        map.put("data","{}");
	        map.put("message", "删除成功");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message","失败");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 用户端一键已读
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberRead", method = RequestMethod.GET)
	public void memberRead(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			informationService.memberRead(member);
			
			map.put("status", "200");
			map.put("message", "设置成功");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 医生端一键已读
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doctorRead", method = RequestMethod.GET)
	public void doctorRead(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			informationService.doctorRead(doctor);
			
			map.put("status", "200");
			map.put("message", "设置成功");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 医生端父级消息列表(新)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/newParentInformationList", method = RequestMethod.GET)
	public void newParentInformationList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			/**获取医生三个分类最近的消息**/
			Map<String ,Object> data_map =informationService.getDoctorRecentlyInformation(doctor);
			
			map.put("status", "200");
			map.put("message", "成功");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 医生端消息列表(新)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/newInformationList", method = RequestMethod.GET)
	public void newInformationList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			Integer pageNumber = json.getInt("pageNumber");//页码
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			ClassifyType classifyType = ClassifyType.valueOf(json.getString("classifyType"));
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			Map<String ,Object> map_date =informationService.getNewInformationByDoctor(doctor,pageNumber,classifyType);
			printWriter.write(JsonUtils.toString(map_date)) ;
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 消息详情
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/newInformationDetails", method = RequestMethod.GET)
	public void newInformationDetails(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
		try {
			Information information = informationService.find(json.getLong("informationId"));
			/**判断消息类型**/
			//系统通知
			if(information.getInformationType().equals(InformationType.system)){
				information.setState(StateType.read);
				informationService.update(information);
				
				Map<String,Object> information_map = new HashMap<String,Object>();	
				information_map.put("message",information.getMessage());
				information_map.put("informationType",information.getInformationType());
				information_map.put("headline",information.getHeadline());
				information_map.put("informationId",information.getInformationId());
				
				Map<String,Object> data_map = new HashMap<String,Object>();	
				data_map.put("information",information_map );
				
				map.put("status", "200");
		        map.put("data",JsonUtils.toJson(data_map) );
		        map.put("message", "读取消息成功");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}else if(information.getInformationType().equals(InformationType.order)){
				information.setState(StateType.read);
				informationService.update(information);
				
				Map<String,Object> information_map = new HashMap<String,Object>();	
				information_map.put("message",information.getMessage());
				information_map.put("informationType",information.getInformationType());
				information_map.put("headline",information.getHeadline());
				information_map.put("informationId",information.getInformationId());
				
				Map<String,Object> data_map = new HashMap<String,Object>();	
				data_map.put("information",information_map );
				
				map.put("status", "200");
		        map.put("data",JsonUtils.toJson(data_map) );
		        map.put("message", "读取消息成功");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}else if(information.getInformationType().equals(InformationType.project)){
				information.setState(StateType.read);
				informationService.update(information);
				
				Map<String,Object> information_map = new HashMap<String,Object>();	
				information_map.put("message",information.getMessage());
				information_map.put("informationType",information.getInformationType());
				information_map.put("headline",information.getHeadline());
				information_map.put("informationId",information.getInformationId());
				
				Map<String,Object> data_map = new HashMap<String,Object>();	
				data_map.put("information",information_map );
				
				map.put("status", "200");
		        map.put("data",JsonUtils.toJson(data_map) );
		        map.put("message", "读取消息成功");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}else if(information.getInformationType().equals(InformationType.doctor)){
				information.setState(StateType.read);
				informationService.update(information);
				
				Map<String,Object> information_map = new HashMap<String,Object>();	
				information_map.put("message",information.getMessage());
				information_map.put("informationType",information.getInformationType());
				information_map.put("headline",information.getHeadline());
				information_map.put("informationId",information.getInformationId());
				
				Map<String,Object> data_map = new HashMap<String,Object>();	
				data_map.put("information",information_map );
				
				map.put("status", "200");
		        map.put("data",JsonUtils.toJson(data_map) );
		        map.put("message", "读取消息成功");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}
	
	/**
	 * 医生端未读消息提示(新)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/newDoctorInformationRead", method = RequestMethod.GET)
	public void newDoctorInformationRead(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			
			String safeKeyValue = json.getString("safeKeyValue");
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Boolean key = informationService.newIsDoctorUnread(doctor);
			
			Map<String,Object> data_map = new HashMap<String,Object>();
			data_map.put("stateType",key);
			
			map.put("status", "200");
	        map.put("data",JsonUtils.toJson(data_map) );
	        map.put("message", "成功");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message","失败");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 患者端父级消息列表(新)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/newMemberParentInformationList", method = RequestMethod.GET)
	public void newMemberParentInformationList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			/**获取医生三个分类最近的消息**/
			Map<String ,Object> data_map =informationService.getMemberRecentlyInformation(member);
			
			map.put("status", "200");
			map.put("message", "成功");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 患者端消息列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/newMemberInformationList", method = RequestMethod.GET)
	public void newMemberInformationList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			Integer pageNumber = json.getInt("pageNumber");//页码
			ClassifyType classifyType = ClassifyType.valueOf(json.getString("classifyType"));
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			Map<String ,Object> map_date =informationService.getNewInformationByMember(member,pageNumber,classifyType);
			printWriter.write(JsonUtils.toString(map_date)) ;
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 患者端未读消息提示(新)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/newMemberInformationRead", method = RequestMethod.GET)
	public void newMemberInformationRead(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			
			String safeKeyValue = json.getString("safeKeyValue");
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Boolean key = informationService.newIsMemberUnread(member);
			
			Map<String,Object> data_map = new HashMap<String,Object>();
			data_map.put("stateType",key);
			
			map.put("status", "200");
	        map.put("data",JsonUtils.toJson(data_map) );
	        map.put("message", "成功");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message","失败");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 医生端一键已读(新)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/newDoctorRead", method = RequestMethod.GET)
	public void newDoctorRead(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			informationService.newDoctorRead(doctor);
			
			map.put("status", "200");
			map.put("message", "设置成功");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 医生端一键已读(系统消息)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doctorReadSystem", method = RequestMethod.GET)
	public void doctorReadSystem(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			informationService.doctorReadSystem(doctor);
			
			map.put("status", "200");
			map.put("message", "设置成功");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 医生端一键已读(业务消息)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doctorReadBusiness", method = RequestMethod.GET)
	public void doctorReadBusiness(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			informationService.doctorReadBusiness(doctor);
			
			map.put("status", "200");
			map.put("message", "设置成功");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 消息已读接口
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/informationRead", method = RequestMethod.GET)
	public void informationRead(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			Information information = informationService.find(json.getLong("informationId"));
			information.setState(StateType.read);
			informationService.update(information);
			
			map.put("status", "200");
			map.put("message", "设置成功");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
}


