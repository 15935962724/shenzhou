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
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDayItem;
import net.shenzhou.entity.WorkDayItem.WorkDayType;
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
import net.shenzhou.util.ComparatorDate;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - ???????????????
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appWorkDayController")
@RequestMapping("/app/workDay")
public class WorkDayController extends BaseController {
	

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
	 * ?????????????????????????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//http://192.168.1.85:8080/shenzhou/app/workDay/setDoctorWorkTime.jhtml?file={safeKeyValue:"",startTime:"",endTime:""}
	@RequestMapping(value = "/setDoctorWorkTime", method = RequestMethod.GET)
	public void setDoctorWorkTime(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			String startTime = json.getString("startTime");
			String endTime = json.getString("endTime");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			doctor.setStartTime(startTime);
			doctor.setEndTime(endTime);
			doctorService.update(doctor);
			
			map.put("status", "200");
			map.put("message","???????????? ");
			map.put("data", "{}");
	        printWriter.write(JsonUtils.toString(map));
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
	 * ???????????????????????????(?????????)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//http://192.168.1.85:8080/shenzhou/app/workDay/doctorMechanismWorkTimeEcho.jhtml?file={safeKeyValue:"",workDayId:""}
	@RequestMapping(value = "/doctorMechanismWorkTimeEcho", method = RequestMethod.GET)
	public void doctorMechanismWorkTimeEcho(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			WorkDay workDay = workDayService.find(json.getLong("workDayId"));
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			List<WorkDayItem> workDayItems = workDayItemService.getDoctorMechanismTime(doctor, workDay);
			String startTime = doctor.getStartTime();
			String endTime = doctor.getEndTime();
			List<DoctorMechanismRelation> doctorMechanisms = doctor.getDoctorMechanismRelations(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed);
			
			List<Map<String,Object>> mechanism_list = new ArrayList<Map<String,Object>>();
			for(DoctorMechanismRelation doctorMechanismRelation : doctorMechanisms){
				Map<String,Object> mechanism_map = new HashMap<String, Object>();
				mechanism_map.put("mechanismName", doctorMechanismRelation.getMechanism().getName());
				mechanism_map.put("mechanismId", doctorMechanismRelation.getMechanism().getId());
				mechanism_list.add(mechanism_map);
			}
			
			List<Map<String,Object>> workDayItem_list = new ArrayList<Map<String,Object>>();
			for(WorkDayItem workDayItem : workDayItems){
				Map<String,Object> mechanism_map = new HashMap<String, Object>();
				mechanism_map.put("workDayItemStartTime", workDayItem.getStartTime());
				mechanism_map.put("workDayItemEndTime", workDayItem.getEndTime());
				mechanism_map.put("workDayItemId", workDayItem.getId());
				mechanism_map.put("mechanismName", workDayItem.getMechanism().getName());
				mechanism_map.put("mechanismId", workDayItem.getMechanism().getId());
				workDayItem_list.add(mechanism_map);
			}
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("startTime", startTime);
			data_map.put("endTime", endTime);
			data_map.put("mechanism_list", mechanism_list);
			data_map.put("workDayItem_list", workDayItem_list);
			data_map.put("workDayId", json.getLong("workDayId"));
			
			map.put("status", "200");
			map.put("message","???????????? ");
			map.put("data", JsonUtils.toJson(data_map));
	        printWriter.write(JsonUtils.toString(map));
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
	 * ????????????(???????????????)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//http://192.168.1.85:8080/shenzhou/app/workDay/doctorMechanismWorkTime.jhtml?file={safeKeyValue:"",workDayId:"",startTime:"",endTime:"",mechanismId:""}
	@RequestMapping(value = "/doctorMechanismWorkTime", method = RequestMethod.GET)
	public void setMechanismWorkTime(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			WorkDay workDay = workDayService.find(json.getLong("workDayId"));
			Mechanism mechanism = mechanismService.find(json.getLong("mechanismId"));
			String startTime = json.getString("startTime");
			String endTime = json.getString("endTime");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(!doctor.getIsReal()){
				map.put("status", "666");
				map.put("message", "?????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			/*String doctor_startTime = doctor.getStartTime();
			String doctor_endTime = doctor.getEndTime();*/
			
			/*if(StringUtils.isEmpty(doctor_startTime)||StringUtils.isEmpty(doctor_endTime)){
				map.put("status", "400");
				map.put("message","????????????????????????????????????");
				map.put("data", "{}");
		        printWriter.write(JsonUtils.toString(map));
				return;
			}*/
			
			/**??????????????????????????????**/
			if(startTime.equals(endTime)||DateUtil.compare_date(startTime, endTime)==-1){
				map.put("status", "400");
				map.put("message","????????????????????????,???????????????");
				map.put("data", "{}");
		        printWriter.write(JsonUtils.toString(map));
				return;
			}
			
		/*	*//**???????????????????????????????????????????????????**//*
			if(DateUtil.compare_date(startTime, doctor_startTime)==1||DateUtil.compare_date(endTime, doctor_endTime)==-1){
				map.put("status", "400");
				map.put("message","????????????????????????????????????,??????????????? ");
				map.put("data", "{}");
		        printWriter.write(JsonUtils.toString(map));
				return;
			}*/
			
			/**????????????????????????,?????????????????????????????????????????????????????????mechanism**/
			String mechanism_startTime = mechanism.getWorkDate().getStartTime();
			String mechanism_endTime = mechanism.getWorkDate().getEndTime();
			if(DateUtil.compare_date(startTime, mechanism_startTime)==1||DateUtil.compare_date(endTime, mechanism_endTime)==-1){
				map.put("status", "400");
				map.put("message","???????????????????????????"+mechanism_startTime+"---"+mechanism_endTime+",??????????????????????????????????????????,??????????????? ");
				map.put("data", "{}");
		        printWriter.write(JsonUtils.toString(map));
				return;
			}
			
			/**?????????????????????????????????,???????????????????????????**/
			//???????????????????????????
			List<WorkDayItem> workDayItems = workDayItemService.getDoctorMechanismTime(doctor, workDay);
			for(WorkDayItem workDayItem : workDayItems){
				if(DateUtil.compare_date(startTime, workDayItem.getStartTime())==-1&&DateUtil.compare_date(startTime, workDayItem.getEndTime())==1){
					map.put("status", "400");
					map.put("message","???????????????????????????,?????????????????????");
					map.put("data", "{}");
			        printWriter.write(JsonUtils.toString(map));
					return;
				}
				if((DateUtil.compare_date(startTime, workDayItem.getStartTime())==0)){
					map.put("status", "400");
					map.put("message","???????????????????????????,?????????????????????");
					map.put("data", "{}");
			        printWriter.write(JsonUtils.toString(map));
					return;
				}
			}
			
			//????????????????????????????????????????????????????????????,?????????????????????????????????????????????????????????,????????????????????????
			if(workDayItems.size()>0){
				String next_endTime = getLastData(startTime,workDayItems);
				if(!next_endTime.equals(startTime)){
					if(DateUtil.compare_date(endTime, next_endTime)==-1){
						map.put("status", "400");
						map.put("message","???????????????????????????,???????????????????????????");
						map.put("data", "{}");
				        printWriter.write(JsonUtils.toString(map));
						return;
					}
				}
			}
			
			WorkDayItem workDayItem = new WorkDayItem();
			workDayItem.setStartTime(startTime);
			workDayItem.setEndTime(endTime);
			workDayItem.setContent("???????????????????????????");
			workDayItem.setWorkDay(workDay);
			workDayItem.setWorkDayType(WorkDayType.mechanism);
			workDayItem.setMechanism(mechanism);
			/*workDayItem.setCountPrice(null);
			workDayItem.setPrice(null);*/
//			workDayItem.setCountPrice(null);
//			workDayItem.setPrice(null);
			workDayItem.setIsDeleted(false);
			workDayItemService.save(workDayItem);
			
			workDay.setIsArrange(true);
			workDayService.update(workDay);
			
			map.put("status", "200");
			map.put("message","????????????");
			map.put("data", "{}");
	        printWriter.write(JsonUtils.toString(map));
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
	 * ???????????????????????????id(???????????????????????????,????????????HH:mm,?????????????????????200,??????400)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//http://192.168.1.85:8080/shenzhou/app/workDay/getMechanismByTime.jhtml?file={safeKeyValue:"",time:"",workDayId:""}
	@RequestMapping(value = "/getMechanismByTime", method = RequestMethod.GET)
	public void getMechanismByTime(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			String time = json.getString("time");
			WorkDay workDay = workDayService.find(json.getLong("workDayId"));
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			List<WorkDayItem> workDayItems = workDayItemService.getDoctorMechanismTime(doctor, workDay);
			for(WorkDayItem workDayItem : workDayItems){
				if(time.equals(workDayItem.getStartTime())||time.equals(workDayItem.getEndTime())){
					Mechanism mechanism = workDayItem.getMechanism();
					Map<String,Object> data_map = new HashMap<String, Object>();
					data_map.put("mechanismName", mechanism.getName());
					data_map.put("mechanismId", mechanism.getId());
					data_map.put("workDayItemId", workDayItem.getId());
					map.put("status", "200");
					map.put("message", "????????????");
					map.put("data", JsonUtils.toJson(data_map));
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}
				
				if(DateUtil.compare_date(time, workDayItem.getStartTime())==-1&&DateUtil.compare_date(time, workDayItem.getEndTime())==1){
					Mechanism mechanism = workDayItem.getMechanism();
					Map<String,Object> data_map = new HashMap<String, Object>();
					data_map.put("mechanismName", mechanism.getName());
					data_map.put("mechanismId", mechanism.getId());
					data_map.put("workDayItemId", workDayItem.getId());
					map.put("status", "200");
					map.put("message", "????????????");
					map.put("data", JsonUtils.toJson(data_map));
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}
				
			}
			
			map.put("status", "400");
			map.put("message","???????????????????????????????????????");
			map.put("data", "{}");
	        printWriter.write(JsonUtils.toString(map));
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
	 * ????????????????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//http://192.168.1.85:8080/shenzhou/app/workDay/cancelMechanismTime.jhtml?file={safeKeyValue:"",workDayId:"",workDayItemId:""}
	@RequestMapping(value = "/cancelMechanismTime", method = RequestMethod.GET)
	public void cancelMechanismTime(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			WorkDay workDay = workDayService.find(json.getLong("workDayId"));
			WorkDayItem workDayItem = workDayItemService.find(json.getLong("workDayItemId"));
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			//????????????????????????????????????????????????
			
			List<WorkDayItem> workDayItems = workDayItemService.getDoctorOrderTime(doctor, workDay);
			List<WorkDayItem> list = getOrderTime(workDayItem.getStartTime(), workDayItem.getEndTime(), workDayItems);
			
			if(list.size()>0){
				map.put("status", "400");
				map.put("message","????????????????????????????????????,??????????????????");
				map.put("data", "{}");
		        printWriter.write(JsonUtils.toString(map));
				return;
			}
			
			workDayItemService.delete(workDayItem);
			
			List<WorkDayItem> workDayItem_today = workDayItemService.getDoctorMechanismTime(doctor, workDay);
			if(workDayItem_today.size()<=0){
				workDay.setIsArrange(false);
				workDayService.update(workDay);
			}
			
			map.put("status", "200");
			map.put("message","??????????????????");
			map.put("data", "{}");
	        printWriter.write(JsonUtils.toString(map));
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
	 * ????????????????????????????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//http://192.168.1.85:8080/shenzhou/app/workDay/cancelMechanismDay.jhtml?file={safeKeyValue:"",workDayId:""}
	@RequestMapping(value = "/cancelMechanismDay", method = RequestMethod.GET)
	public void cancelMechanismDay(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			WorkDay workDay = workDayService.find(json.getLong("workDayId"));
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			//????????????????????????????????????????????????
			List<WorkDayItem> workDayItems_order = workDayItemService.getDoctorOrderTime(doctor, workDay);
			List<WorkDayItem> workDayItems_lock = workDayItemService.getDoctorLockTime(doctor, workDay);
			if(workDayItems_order.size()>0){
				map.put("status", "400");
				map.put("message","????????????????????????,??????????????????");
				map.put("data", "{}");
		        printWriter.write(JsonUtils.toString(map));
				return;
			}
			
			if(workDayItems_lock.size()>0){
				map.put("status", "400");
				map.put("message","????????????????????????,??????????????????");
				map.put("data", "{}");
		        printWriter.write(JsonUtils.toString(map));
				return;
			}
			List<WorkDayItem> workDayItems_mechanism = workDayItemService.getDoctorMechanismTime(doctor, workDay);
			for(WorkDayItem workDayItem : workDayItems_mechanism){
				workDayItemService.delete(workDayItem);
			}
			
			workDay.setIsArrange(false);
			workDayService.update(workDay);
			
			map.put("status", "200");
			map.put("message","??????????????????");
			map.put("data", "{}");
	        printWriter.write(JsonUtils.toString(map));
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
	 * ????????????????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//http://192.168.1.85:8080/shenzhou/app/workDay/copyYesterday.jhtml?file={safeKeyValue:"",workDayId:""}
	@RequestMapping(value = "/copyYesterday", method = RequestMethod.GET)
	public void copyYesterday(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			WorkDay today_workDay = workDayService.find(json.getLong("workDayId"));
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			String ss = safeKeyValue.replace(" ","+");
			Doctor doctor = doctorService.findBySafeKeyValue(ss);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(!doctor.getIsReal()){
				map.put("status", "666");
				map.put("message", "?????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			/**???????????????????????????????????????????????????workDay??????**/
			Date today_date = today_workDay.getWorkDayDate();
			String str_yesterday_date = DateUtil.getLastDate(today_date, 1);
			Date yesterday_date = DateUtil.getStringtoDate(str_yesterday_date, "yyyy-MM-dd");
			WorkDay yesterday_workDay = workDayService.getWorkDayByDoctorAndData(doctor,yesterday_date);
			
			List<WorkDayItem> today_date_mechanism = workDayItemService.getDoctorMechanismTime(doctor, today_workDay);
			List<WorkDayItem> yesterday_date_mechanism = workDayItemService.getDoctorMechanismTime(doctor, yesterday_workDay);
			
			if(today_date_mechanism.size()>0){
				map.put("status", "400");
				map.put("message","????????????????????????????????????,????????????");
				map.put("data", "{}");
		        printWriter.write(JsonUtils.toString(map));
				return;
			}
			if(yesterday_date_mechanism.size()<=0){
				map.put("status", "400");
				map.put("message","?????????????????????????????????,????????????");
				map.put("data", "{}");
		        printWriter.write(JsonUtils.toString(map));
				return;
			}
			
			for(WorkDayItem workDayItem : yesterday_date_mechanism){
				WorkDayItem workDayItem_copy = new WorkDayItem();
				String startTime = workDayItem.getStartTime();
				String endTime = workDayItem.getEndTime();
				workDayItem_copy.setStartTime(startTime);
				workDayItem_copy.setEndTime(endTime);
				Mechanism mechanism = workDayItem.getMechanism();
				workDayItem_copy.setContent("???????????????????????????");
				workDayItem_copy.setWorkDay(today_workDay);
				workDayItem_copy.setWorkDayType(WorkDayType.mechanism);
				workDayItem_copy.setMechanism(mechanism);
				/*workDayItem_copy.setCountPrice(null);
				workDayItem_copy.setPrice(null);*/
//				workDayItem_copy.setCountPrice(null);
//				workDayItem_copy.setPrice(null);
				workDayItem_copy.setIsDeleted(false);
				workDayItemService.save(workDayItem_copy);
			}
			today_workDay.setIsArrange(true);
			workDayService.update(today_workDay);
			
			map.put("status", "200");
			map.put("message","??????????????????");
			map.put("data", "{}");
	        printWriter.write(JsonUtils.toString(map));
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
	 * (?????????)???????????????????????????id(???????????????????????????,????????????HH:mm,?????????????????????200,??????400)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//http://192.168.1.31:8080/shenzhou/app/workDay/getMechanism.jhtml?file={time:"",workDayId:""}
	@RequestMapping(value = "/getMechanism", method = RequestMethod.GET)
	public void getMechanism(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String time = json.getString("time");
			WorkDay workDay = workDayService.find(json.getLong("workDayId"));
			Doctor doctor = new Doctor();
			List<WorkDayItem> workDayItems = workDayItemService.getDoctorMechanismTime(doctor, workDay);
			for(WorkDayItem workDayItem : workDayItems){
				if(time.equals(workDayItem.getStartTime())||time.equals(workDayItem.getEndTime())){
					Mechanism mechanism = workDayItem.getMechanism();
					Map<String,Object> data_map = new HashMap<String, Object>();
					data_map.put("mechanismName", mechanism.getName());
					data_map.put("mechanismId", mechanism.getId());
					data_map.put("workDayItemId", workDayItem.getId());
					map.put("status", "200");
					map.put("message", "????????????");
					map.put("data", JsonUtils.toJson(data_map));
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}
				
				if(DateUtil.compare_date(time, workDayItem.getStartTime())==-1&&DateUtil.compare_date(time, workDayItem.getEndTime())==1){
					Mechanism mechanism = workDayItem.getMechanism();
					Map<String,Object> data_map = new HashMap<String, Object>();
					data_map.put("mechanismName", mechanism.getName());
					data_map.put("mechanismId", mechanism.getId());
					data_map.put("workDayItemId", workDayItem.getId());
					map.put("status", "200");
					map.put("message", "????????????");
					map.put("data", JsonUtils.toJson(data_map));
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}
				
			}
			
			map.put("status", "400");
			map.put("message","???????????????????????????????????????");
			map.put("data", "{}");
	        printWriter.write(JsonUtils.toString(map));
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
	 * ??????????????????????????????????????????????????????
	 * @param start
	 * @param workDay
	 * @return
	 */
	public static List<WorkDayItem> getOrderTime(String startTime,String endTime,List<WorkDayItem> workDayItems){
		List<WorkDayItem> order_list = new ArrayList<WorkDayItem>();
		for(WorkDayItem workDayItem : workDayItems){
			if(DateUtil.compare_date(startTime, workDayItem.getStartTime())==1&&DateUtil.compare_date(endTime, workDayItem.getEndTime())==-1){
				order_list.add(workDayItem);
				continue;
			}
			if(startTime.equals(workDayItem.getStartTime())&&DateUtil.compare_date(endTime, workDayItem.getEndTime())==-1){
				order_list.add(workDayItem);
				continue;
			}
			if(endTime.equals(workDayItem.getEndTime())&&DateUtil.compare_date(startTime, workDayItem.getStartTime())==1){
				order_list.add(workDayItem);
				continue;
			}
		}
		
		return order_list;
	}
	
	
	
	
	/**
	 * ???????????????????????????????????????
	 * @param start
	 * @param workDay
	 * @return
	 */
	public static String getLastData(String startTime,List<WorkDayItem> workDayItems){
		List<Date> lists = new ArrayList<Date>();
		for (WorkDayItem workDayItem : workDayItems){//?????????????????????????????????lists??????
			lists.add(DateUtil.getStringtoDate(workDayItem.getStartTime(), "HH:mm"));
			lists.add(DateUtil.getStringtoDate(workDayItem.getEndTime(), "HH:mm"));
		}
		String workDayItemStartTime = "";
		for(WorkDayItem workDayItem : workDayItems){
			if(workDayItem.getStartTime().equals(startTime)||workDayItem.getEndTime().equals(startTime)){
				DateUtil.getMinute(DateUtil.getStringtoDate(startTime, "HH:mm"), 1,"HH:mm");
				lists.add(DateUtil.getStringtoDate(DateUtil.getMinute(DateUtil.getStringtoDate(startTime, "HH:mm"), 1,"HH:mm"), "HH:mm"));//?????????????????????lists???????????????Date??????
				ComparatorDate c = new ComparatorDate();
				Collections.sort(lists, c);  
				for (int i = 0; i < lists.size(); i++) {
					if (DateUtil.compare_date(DateUtil.getMinute(DateUtil.getStringtoDate(startTime, "HH:mm"), 1,"HH:mm"), DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)))==0) {
						if(i+1==lists.size()){
							workDayItemStartTime =startTime;// DateUtil.getDatetoString("HH:mm", (Date)lists.get(i));
							return workDayItemStartTime;
						}
						if(i+1<lists.size()){
							workDayItemStartTime = DateUtil.getDatetoString("HH:mm", (Date)lists.get(i+1));
							return workDayItemStartTime;
						}
					}
				}
			}
		}
		
		
		
		lists.add(DateUtil.getStringtoDate(startTime, "HH:mm"));//?????????????????????lists???????????????Date??????
		ComparatorDate c = new ComparatorDate();
		Collections.sort(lists, c);  
		
		System.out.print("?????????"+lists.size());
		for (int i = 0; i < lists.size(); i++) {
			if (DateUtil.compare_date(startTime, DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)))==0) {
				if(i==lists.size()-1){
					workDayItemStartTime = DateUtil.getDatetoString("HH:mm", (Date)lists.get(i));
					break;
				}
				if(i+1<lists.size()){
					workDayItemStartTime = DateUtil.getDatetoString("HH:mm", (Date)lists.get(i+1));
					break;
				}
			}
			System.out.println(DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)));
		}
		
		
		return workDayItemStartTime;
	}
	
	
}
 
