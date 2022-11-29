/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PatientMechanism;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.BeforehandLoginService;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.CartService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.GroupPatientService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.PatientGroupService;
import net.shenzhou.service.RSAService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 工作报表
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appWorkStatementController")
@RequestMapping("/app/workStatement")
public class WorkStatementController extends BaseController {

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
	@Resource(name = "beforehandLoginServiceImpl")
	private BeforehandLoginService beforehandLoginService;
	@Resource(name = "patientGroupServiceImpl")
	private PatientGroupService patientGroupService;
	@Resource(name = "groupPatientServiceImpl")
	private GroupPatientService groupPatientService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	
	
	/**
	 * 资金收益(数据图)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/capitalEarnings", method = RequestMethod.GET)
	public void patientGroupList(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
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
			
			Map<String,Object> damap = orderService.capitalEarnings(doctor);
			
			printWriter.write(JsonUtils.toString(damap)) ;
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
	 * 业务统计(数据图)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/businessStatistics", method = RequestMethod.GET)
	public void businessStatistics(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
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
			
			Map<String,Object> data_map = orderService.businessStatistics(doctor);
			printWriter.write(JsonUtils.toString(data_map)) ;
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
	 * 工作量统计(数据图)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/workloadStatistics", method = RequestMethod.GET)
	public void workloadStatistics(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
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
			
			Map<String,Object> data_map = orderService.workloadStatistics(doctor);
			printWriter.write(JsonUtils.toString(data_map)) ;
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
	 * 搜索数据
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.GET)
	public void searchData(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
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
			
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Mechanism mechanism = doctorMechanismRelation.getMechanism();
			
			/*Set<Member> member_mechanism = mechanism.getPatientMembers();
			List<Member> member_doctor = doctor.getParents();
			
			List<Member> patientMembers = new ArrayList<Member>();
			List<Map<String,Object>> patientList = new ArrayList<Map<String,Object>>();
			for(Member memberMechanism: member_mechanism){
				for(Member memberDoctor:member_doctor){
					if(memberMechanism.equals(memberDoctor)){
						patientMembers.add(memberMechanism);
					}
				}
			}
			
			for(Member member : patientMembers){
				Map<String,Object> patient_map = new HashMap<String, Object>();
				patient_map.put("patientMemberId", member.getId());
				patient_map.put("patientMemberName", member.getName());
				patientList.add(patient_map);
			}*/
			
			//add wsr 2018-3-24 17:16:53 start
			List<Member> member_doctor = doctor.getParents();
			List<Map<String,Object>> patientList = new ArrayList<Map<String,Object>>();
			for(PatientMechanism patientMechanism : mechanism.getPatientMechanisms()){
				for(Member memberDoctor:member_doctor){
					if(patientMechanism.getPatient().equals(memberDoctor)){
						Map<String,Object> patient_map = new HashMap<String, Object>();
						Member member = patientMechanism.getPatient();
						patient_map.put("patientMemberId", member.getId());
						patient_map.put("patientMemberName", member.getName());
						patientList.add(patient_map);
					}
				}
			}
			//add wsr 2018-3-24 17:16:53 end
			
			
			List<Project> projects = doctor.getProjects();
			List<ServerProjectCategory> serverProjectCategorys = new ArrayList<ServerProjectCategory>();
			List<Map<String,Object>> serverProjectCategory_list = new ArrayList<Map<String,Object>>();
			
			for(Project project : projects){
				if(!serverProjectCategorys.contains(project.getServerProjectCategory())){
					serverProjectCategorys.add(project.getServerProjectCategory());
				}
			}
			
			for(ServerProjectCategory serverProjectCategory : serverProjectCategorys){
				Map<String,Object> serverProjectCategory_map = new HashMap<String, Object>();
				serverProjectCategory_map.put("serverProjectCategoryId", serverProjectCategory.getId());
				serverProjectCategory_map.put("serverProjectCategoryName", serverProjectCategory.getName());
				serverProjectCategory_list.add(serverProjectCategory_map);
			}
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("patientMember", patientList);
			data_map.put("serverProjectCategorys", serverProjectCategory_list);
			
			map.put("status", "200");
			map.put("message", "加载成功");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", "加载成功");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}
	

	/**
	 * 资金收益明细
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/capitalEarningsDetail", method = RequestMethod.GET)
	public void capitalEarningsDetail(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
	        Integer pageNumber = json.getInt("pageNumber");//页码
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
			
			Map<String, Object> query_map = new HashMap<String, Object>();
			
			if(json.containsKey("serverProjectCategoryId")&&!StringUtils.isEmpty(json.getString("serverProjectCategoryId"))){
				ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(json.getLong("serverProjectCategoryId"));
				query_map.put("serverProjectCategory",serverProjectCategory );
			}else{
				query_map.put("serverProjectCategory",null );
			}
			if(json.containsKey("patientMemberId")&&!StringUtils.isEmpty(json.getString("patientMemberId"))){
				Member patientMember = memberService.find(json.getLong("patientMemberId"));
				query_map.put("patientMember",patientMember );
			}else{
				query_map.put("patientMember",null );
			}
			
			String start_time = json.getString("startTime");
			String end_time = json.getString("endTime");
			
			if(StringUtils.isEmpty(start_time)||StringUtils.isEmpty(end_time)){
				start_time="2017-08-20";
				end_time=DateUtil.getDatetoString("yyyy-MM-dd", new Date());
			}
			query_map.put("startTime",start_time );
			query_map.put("endTime",end_time );
			query_map.put("doctor",doctor );
			query_map.put("pageNumber",pageNumber );
			
			Map<String,Object> damap = orderService.capitalEarningsDetail(query_map);
			
			printWriter.write(JsonUtils.toString(damap)) ;
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
	 * 业务统计明细
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/businessStatisticsDetails", method = RequestMethod.GET)
	public void businessStatisticsDetails(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
	        Integer pageNumber = json.getInt("pageNumber");//页码
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
			
			Map<String, Object> query_map = new HashMap<String, Object>();
			
			if(json.containsKey("serverProjectCategoryId")&&!StringUtils.isEmpty(json.getString("serverProjectCategoryId"))){
				ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(json.getLong("serverProjectCategoryId"));
				query_map.put("serverProjectCategory",serverProjectCategory );
			}else{
				query_map.put("serverProjectCategory",null );
			}
			if(json.containsKey("patientMemberId")&&!StringUtils.isEmpty(json.getString("patientMemberId"))){
				Member patientMember = memberService.find(json.getLong("patientMemberId"));
				query_map.put("patientMember",patientMember );
			}else{
				query_map.put("patientMember",null );
			}
			
			String start_time = json.getString("startTime");
			String end_time = json.getString("endTime");
			
			if(StringUtils.isEmpty(start_time)||StringUtils.isEmpty(end_time)){
				start_time="2017-08-20";
				end_time=DateUtil.getDatetoString("yyyy-MM-dd", new Date());
			}
			query_map.put("startTime",start_time );
			query_map.put("endTime",end_time );
			query_map.put("doctor",doctor );
			query_map.put("pageNumber",pageNumber );
			
			Map<String,Object> damap = orderService.businessStatisticsDetails(query_map);
			
			printWriter.write(JsonUtils.toString(damap)) ;
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
	 * 工作量统计明细
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/workloadStatisticsDetails", method = RequestMethod.GET)
	public void workloadStatisticsDetails(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
	        Integer pageNumber = json.getInt("pageNumber");//页码
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
			
			Map<String, Object> query_map = new HashMap<String, Object>();
			
			if(json.containsKey("serverProjectCategoryId")&&!StringUtils.isEmpty(json.getString("serverProjectCategoryId"))){
				ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(json.getLong("serverProjectCategoryId"));
				query_map.put("serverProjectCategory",serverProjectCategory );
			}else{
				query_map.put("serverProjectCategory",null );
			}
			if(json.containsKey("patientMemberId")&&!StringUtils.isEmpty(json.getString("patientMemberId"))){
				Member patientMember = memberService.find(json.getLong("patientMemberId"));
				query_map.put("patientMember",patientMember );
			}else{
				query_map.put("patientMember",null );
			}
			
			String start_time = json.getString("startTime");
			String end_time = json.getString("endTime");
			
			if(StringUtils.isEmpty(start_time)||StringUtils.isEmpty(end_time)){
				start_time="2017-08-20";
				end_time=DateUtil.getDatetoString("yyyy-MM-dd", new Date());
			}
			query_map.put("startTime",start_time );
			query_map.put("endTime",end_time );
			query_map.put("doctor",doctor );
			query_map.put("pageNumber",pageNumber );
			
			Map<String,Object> damap = orderService.workloadStatisticsDetails(query_map);
			
			printWriter.write(JsonUtils.toString(damap)) ;
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
	 * 工作报表列表页数据
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/workStatementList", method = RequestMethod.GET)
	public void workStatementList(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
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
			
			Map<String, Object> query_map = new HashMap<String, Object>();
			
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			if(json.containsKey("serverProjectCategoryId")&&!StringUtils.isEmpty(json.getString("serverProjectCategoryId"))){
				ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(json.getLong("serverProjectCategoryId"));
				query_map.put("serverProjectCategory",serverProjectCategory );
			}else{
				query_map.put("serverProjectCategory",null );
			}
			if(json.containsKey("patientMemberId")&&!StringUtils.isEmpty(json.getString("patientMemberId"))){
				Member patientMember = memberService.find(json.getLong("patientMemberId"));
				query_map.put("patientMember",patientMember );
			}else{
				query_map.put("patientMember",null );
			}
			
			query_map.put("doctor",doctor );
			Map<String,Object> damap = orderService.workStatementList(query_map);
			
			printWriter.write(JsonUtils.toString(damap)) ;
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
	 * 我的绩效
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doctorPerformance", method = RequestMethod.GET)
	public void doctorPerformance(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
	        Integer pageNumber = json.getInt("pageNumber");//页码
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
			
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			String startTime = json.getString("startTime");
			String endTime = json.getString("endTime");
			Map<String, Object> query_map = new HashMap<String, Object>();
			
			if(json.containsKey("serverProjectCategoryId")&&!StringUtils.isEmpty(json.getString("serverProjectCategoryId"))){
				ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(json.getLong("serverProjectCategoryId"));
				query_map.put("serverProjectCategory",serverProjectCategory );
			}else{
				query_map.put("serverProjectCategory",null );
			}
			
			if(json.containsKey("patientMemberId")&&!StringUtils.isEmpty(json.getString("patientMemberId"))){
				Member patientMember = memberService.find(json.getLong("patientMemberId"));
				query_map.put("patientMember",patientMember );
			}else{
				query_map.put("patientMember",null );
			}
			query_map.put("startTime", startTime);
			query_map.put("endTime", endTime);
			query_map.put("doctor", doctor);
			query_map.put("pageNumber", pageNumber);
			
			Map<String,Object> damap = orderService.doctorPerformance(query_map);
			printWriter.write(JsonUtils.toString(damap)) ;
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
	 * 我的绩效(最新)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doctorPerformances", method = RequestMethod.GET)
	public void doctorPerformances(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
	        Integer pageNumber = json.getInt("pageNumber");//页码
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
			
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			String startTime = json.getString("startTime");
			String endTime = json.getString("endTime");
			Map<String, Object> query_map = new HashMap<String, Object>();
			
			if(json.containsKey("serverProjectCategoryId")&&!StringUtils.isEmpty(json.getString("serverProjectCategoryId"))){
				ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(json.getLong("serverProjectCategoryId"));
				query_map.put("serverProjectCategory",serverProjectCategory );
			}else{
				query_map.put("serverProjectCategory",null );
			}
			
			if(json.containsKey("patientMemberId")&&!StringUtils.isEmpty(json.getString("patientMemberId"))){
				Member patientMember = memberService.find(json.getLong("patientMemberId"));
				query_map.put("patientMember",patientMember );
			}else{
				query_map.put("patientMember",null );
			}
			
			query_map.put("startTime", startTime);
			query_map.put("endTime", endTime);
			query_map.put("doctor", doctor);
			query_map.put("pageNumber", pageNumber);
			Map<String,Object> damap = orderService.doctorPerformances(query_map);
			printWriter.write(JsonUtils.toString(damap)) ;
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
	
}