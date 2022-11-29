/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.controller.app.BaseController;
import net.shenzhou.controller.app.DoctorController;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDay.WorkType;
import net.shenzhou.service.AdPositionService;
import net.shenzhou.service.AdService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.service.AdPositionService;
import net.shenzhou.service.AdService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.WorkDayService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 广告
 * @date 2017-6-9 16:50:54
 * @author wsr
 *
 */
@Controller("appAdController")
@RequestMapping("/app/ad")
public class AdController extends BaseController {

	/** logger */
	private static final Logger logger = Logger.getLogger(AdController.class.getName());
	
	@Resource(name = "adPositionServiceImpl")
	private AdPositionService adPositionService;
	@Resource(name = "adServiceImpl")
	private AdService adService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "workDayServiceImpl")
	private WorkDayService workDayService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	
	
	/**
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
	        map = adService.findList(file);
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
	 * 测试医生排班问题
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/ad/test.jhtml?file={doctorId:"34",mechanismId:"36",serverProjectCategoryId:"20"}
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);

        JSONObject json = JSONObject.fromObject(file);
        Long doctorId = json.getLong("doctorId");
        Doctor doctor = doctorService.find(doctorId);
        Long serverProjectCategoryId = json.getLong("serverProjectCategoryId");
    	ServerProjectCategory serverProjectCategory =  serverProjectCategoryService.findParent(serverProjectCategoryService.find(serverProjectCategoryId));
		System.out.println("顶级id:"+serverProjectCategory.getId());
		printWriter.write(JsonUtils.toJson(serverProjectCategory)) ;
		return;
//		try {
//			
//			response.setCharacterEncoding("utf-8");
//	        response.setContentType("text/html; charset=utf-8");
//	        file = StringEscapeUtils.unescapeHtml(file);
//
//	        JSONObject json = JSONObject.fromObject(file);
//	        Long doctorId = json.getLong("doctorId");
//	        Doctor doctor = doctorService.find(doctorId);
//	        
//	        Long mechanismId = json.getLong("mechanismId");
//	        Mechanism mechanism = mechanismService.find(mechanismId);
//	        
//			String startTime = "7:00";
//			String endTime = "19:00";
//			
//			List<WorkDay> workDays = workDayService.getWorkDays(doctor,new Date());
////			Mechanism mechanism = doctorMechanismRelation.getMechanism();
//			Integer maxday = mechanism.getMechanismSetup().getMaxday();
//			
//			//7天的WorkDay集合
//			if(workDays.size()>0){
//				WorkDay workday = workDays.get(workDays.size()-1);
//				Date workDayDate = workday.getWorkDayDate();
//				logger.info("医生id:"+doctor.getId()+",医生名字为:"+doctor.getName()+",最后一天的排班日期为"+DateUtil.getDatetoString("yyyy-MM-dd", workDayDate));
//				int key = 1;
//				for(int x = workDays.size();x<maxday;x++){
//					WorkDay workDay = workDayService.getDoctorWorkDays(doctor, DateUtil.getStringtoDate(DateUtil.dataAdd(workDayDate, key),"yyyy-MM-dd"));
//					logger.info("医生id:"+doctor.getId()+",医生名字为:"+doctor.getName()+",最后一天的排班日期加"+key+"天为："+DateUtil.dataAdd(workDayDate, key));
//					WorkDay new_workday = new WorkDay();
//					new_workday.setDoctor(doctor);
////					Calendar c = Calendar.getInstance();  
////					c.add(Calendar.DATE, + key);  
////					Date monday = c.getTime();
//					new_workday.setWorkDayDate(DateUtil.getStringtoDate(DateUtil.dataAdd(workDayDate, key),"yyyy-MM-dd"));
//					new_workday.setStartTime(startTime);
//					new_workday.setEndTime(endTime);
//					new_workday.setWorkType(WorkType.rest);
//					new_workday.setIsArrange(false);
//					if(workDay==null){
//						workDayService.save(new_workday);
//					}
//					key++;
//				}
//			}else{
//				logger.info("医生id:"+doctor.getId()+",医生名字为:"+doctor.getName()+",没有任何排班,从新开始排班");
//				for(int x=0;x<maxday;x++){
//					WorkDay workDay = workDayService.getDoctorWorkDays(doctor, DateUtil.getStringtoDate(DateUtil.dataAdd(new Date(), x),"yyyy-MM-dd"));
//					logger.info("医生id:"+doctor.getId()+",医生名字为:"+doctor.getName()+",没有任何排班,从新开始排班>>"+DateUtil.dataAdd(new Date(), x));
//					WorkDay new_workday = new WorkDay();
//					new_workday.setDoctor(doctor);
////					Calendar c = Calendar.getInstance();  
////					c.add(Calendar.DATE, + x);  
////					Date monday = c.getTime();
//					new_workday.setWorkDayDate(DateUtil.getStringtoDate(DateUtil.dataAdd(new Date(), x),"yyyy-MM-dd"));
//					new_workday.setStartTime(startTime);
//					new_workday.setEndTime(endTime);
//					new_workday.setWorkType(WorkType.rest);
//					new_workday.setIsArrange(false);
//					if(workDay==null){
//						workDayService.save(new_workday);
//					}
//				}
//			}	
//			return;
//
//	}catch(Exception e){
//		System.out.println(e.getMessage());
//	}
	
	}
		
	
}


