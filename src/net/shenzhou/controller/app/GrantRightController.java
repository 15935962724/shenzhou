/**
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
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorAssessReport;
import net.shenzhou.entity.GrantRight;
import net.shenzhou.entity.GrantRight.WhetherAllow;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.service.DoctorAssessReportService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.GrantRightService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberService;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 授权操作
 * 2017-6-14 09:57:57
 * @author wsr
 *
 */
@Controller("appGrantRightController")
@RequestMapping("/app/grantRight")
public class GrantRightController extends BaseController {
	
	
	@Resource(name = "grantRightServiceImpl")
	private GrantRightService grantRightService;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	
	@Resource(name = "doctorAssessReportServiceImpl")
	private DoctorAssessReportService doctorAssessReportService;
	
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	
	
	
	
	
	
	
	
	/**
	 * 授权
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/grantRight/allow.jhtml?file={safeKeyValue:"f190d39a391fb923320f179c7c16063f",patientMemberId:"18",startDate:"2017-06-01",endDate:"2017-10-30",doctors:"2,3,4,5",doctorId:"1"}
	@RequestMapping(value = "/allow", method = RequestMethod.GET)
	public void allow(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
            Member member = memberService.findBySafeKeyValue(safeKeyValue); 
            if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
	        
	        Member patientMember = memberService.find(json.getLong("patientMemberId"));
			Map<String,Object> data_map = new HashMap<String, Object>();
			String startDate = json.getString("startDate");
			String endDate = json.getString("endDate");
			String doctors = json.getString("doctors");
			Doctor doctor = doctorService.find(json.getLong("doctorId"));
			data_map.put("startDate", startDate);
			data_map.put("endDate", endDate);
			data_map.put("doctors", doctors);
			data_map.put("patientMember", patientMember);
			data_map.put("doctor", doctor);
			
			Integer count = 0;
			List<DoctorAssessReport> doctorAssessReports = doctorAssessReportService.findList(data_map);
			for (DoctorAssessReport doctorAssessReport : doctorAssessReports) {
				doctorAssessReport.setWhetherAllow(WhetherAllow.allow);
				doctorAssessReportService.update(doctorAssessReport);
				count++;
			}
			if (count>0) {
				GrantRight grantRight = patientMember.getGrantRight(doctor);
				grantRight.setWhetherAllow(WhetherAllow.allow);
				grantRightService.update(grantRight);
			}
			
			
			
			map.put("status", "200");
			map.put("message", count>0?"授权成功":"条件有限,授权失败");
			map.put("data","{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
		
	}
	
	/**
	 * 取消授权或拒绝授权
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/grantRight/cancelORrefuse.jhtml?file={safeKeyValue:"f190d39a391fb923320f179c7c16063f",patientMemberId:"18",doctorId:"1",whetherAllow:""}
	@RequestMapping(value = "/cancelorrefuse", method = RequestMethod.GET)
	public void cancelORrefuse(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
            Member member = memberService.findBySafeKeyValue(safeKeyValue); 
            if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
	        
	        Member patientMember = memberService.find(json.getLong("patientMemberId"));
			Map<String,Object> data_map = new HashMap<String, Object>();
			WhetherAllow whetherAllow = WhetherAllow.valueOf(json.getString("whetherAllow"));
			Doctor doctor = doctorService.find(json.getLong("doctorId"));
			data_map.put("patientMember", patientMember);
			data_map.put("doctor", doctor);
			
			Integer count = 0;
			List<DoctorAssessReport> doctorAssessReports = doctorAssessReportService.findList(data_map);
			for (DoctorAssessReport doctorAssessReport : doctorAssessReports) {
				doctorAssessReport.setWhetherAllow(whetherAllow);
				doctorAssessReportService.update(doctorAssessReport);
				count++;
			}
			if (count>0) {
				GrantRight grantRight = patientMember.getGrantRight(doctor);
				grantRight.setWhetherAllow(whetherAllow);
				grantRightService.update(grantRight);
			}
			
			
			
			map.put("status", "200");
			map.put("message", count>0?"操作成功":"操作失败");
			map.put("data","{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
		
	}
	
	
	/**
	 * 允许授权筛选
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/grantRight/screen.jhtml?file={grantRightId:"18"}
	@RequestMapping(value = "/screen", method = RequestMethod.GET)
	public void screen(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        Map<String,Object> data_map = new HashMap<String, Object>();
			List<Mechanism> mechanisms =  mechanismService.findAll();
			List<Map<String,Object>> mechanism_list = new ArrayList<Map<String,Object>>();
			for (Mechanism mechanism : mechanisms) {
				if (mechanism.getDoctors().size()>0) {
					Map<String,Object> mechanism_map = new HashMap<String, Object>();
					mechanism_map.put("id", mechanism.getId());
					mechanism_map.put("name", mechanism.getName());
					List<Map<String,Object>> doctor_list = new ArrayList<Map<String,Object>>();
					for (Doctor doctor : mechanism.getDoctors()) {
						Map<String,Object> doctor_map = new HashMap<String, Object>();
						doctor_map.put("id", doctor.getId());
						doctor_map.put("name", doctor.getName());
						doctor_list.add(doctor_map);
					}
					mechanism_map.put("doctor_list",doctor_list );
					mechanism_list.add(mechanism_map);
				}
				
				
			}
			
			data_map.put("mechanism_list", mechanism_list);
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
			map.put("data", new Object());
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	
	
	
	/**
	 * 授权列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/grantRight/list.jhtml?file={safeKeyValue:"7da0a1c9ce7e6665da0a7a335e403090",patientMemberId:"",pageNumber:"1"}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
			Map<String,Object> data_map = new HashMap<String, Object>();
			String safeKeyValue = json.getString("safeKeyValue");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
            Member member = memberService.findBySafeKeyValue(safeKeyValue); 
            if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
            
            
//			String whetherAllow = json.getString("whetherAllow");
			String patientMemberId = json.getString("patientMemberId");
			Integer pageNumber = json.getInt("pageNumber");
			data_map.put("member", member);
//			data_map.put("whetherAllow", whetherAllow);
			data_map.put("patientMemberId", patientMemberId);
			data_map.put("pageNumber", pageNumber);
			
			map = grantRightService.findList(data_map);
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	
	
	
	
}


