/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismServerTime;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Order.OrderMan;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDay.WorkType;
import net.shenzhou.entity.WorkDayItem;
import net.shenzhou.entity.WorkDayItem.WorkDayType;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.UserService;
import net.shenzhou.service.WorkDayItemService;
import net.shenzhou.service.WorkDayService;
import net.shenzhou.util.ComparatorDate;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.time.DateUtils;
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
@Controller("mechanismWorkDayController")
@RequestMapping("/mechanism/workDay")
public class WorkDayController extends BaseController {

	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "workDayItemServiceImpl")
	private WorkDayItemService workDayItemService;
	@Resource(name = "workDayServiceImpl")
	private WorkDayService workDayService;
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	
	
	/**
	 * 医生排班列表
	 * //2017-12-27 14:09:19之前的
	 */
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public String list(Pageable pageable,String nameOrphone,String name ,ModelMap model) {
////		User user = userService.getCurrent();
////		Mechanism mechanism = user.getMechanism();
//		Doctor doctorC = doctorService.getCurrent();
//		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
//		List<Map<String,Object>> dateDays = DateUtil.getDateDays();
//		Map<String,Object> query_map = new HashMap<String,Object>();
//		query_map.put("mechanism", mechanism);
//		query_map.put("nameOrphone", nameOrphone);
//		query_map.put("pageable", pageable);
//		model.addAttribute("nameOrphone", nameOrphone);
//		Page<DoctorMechanismRelation> page = doctorMechanismRelationService.getPageMechanismDoctors(query_map);
////		model.addAttribute("page", doctorService.findPage(query_map));
//		model.addAttribute("page", page);
//		model.addAttribute("dateDays", dateDays);
//		model.addAttribute("name", name);
//		model.addAttribute("today", new Date());//今天
//		model.addAttribute("yesterday", DateUtils.addDays(new Date(), -1));//昨天
//		model.addAttribute("tomorrow", DateUtils.addDays(new Date(), 1));//明天
//		return "/mechanism/workDay/list";
//	}
	
	/**
	 * 医生排班列表
	 * @param pageable
	 * @param date
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,String date,ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		List<Map<String,Object>> dateDays = new ArrayList<Map<String,Object>>();
		Date pdate = date==null?DateUtil.getStringtoDate(DateUtil.getDatetoString("yyyy-MM-dd", new Date()), "yyyy-MM-dd"):DateUtil.getStringtoDate(date, "yyyy-MM-dd");
		for (int i = -1; i < mechanism.getMechanismSetup().getMaxday(); i++) {
			Map<String,Object> map = new HashMap<String, Object>();
			if (i==-1) {
				map.put("week","昨天");
			}else if (i==0) {
				map.put("week","今天");
			}else if (i==1) {
				map.put("week","明天");
			}else{
				map.put("week",DateUtil.getDatetoString("yy-MM-dd", DateUtils.addDays(new Date(), i)));
			}
			 map.put("dateDay", DateUtils.addDays(new Date(), i)); 
			 dateDays.add(map);
		}
		
		 
//		 Map<String,Object> map1 = new HashMap<String, Object>();
//		 map1.put("week","今天");
//		 map1.put("dateDay", new Date());
//		 dateDays.add(map1);
//		 Map<String,Object> map2 = new HashMap<String, Object>();
//		 map2.put("week","明天");
//		 map2.put("dateDay", DateUtils.addDays(new Date(), 1));
//		 dateDays.add(map2);
		 model.addAttribute("dateDays", dateDays);
		 model.addAttribute("date", pdate);
		
		 List<Map<String,Object>> server_time_doctor_list = new ArrayList<Map<String,Object>>();
		 for (MechanismServerTime mechanismServerTime : mechanism.getMechanismServerTimes()) {
			 Map<String,Object> mechanismServerTimeMap = new HashMap<String, Object>();
			 mechanismServerTimeMap.put("name", mechanismServerTime.getName());
			 mechanismServerTimeMap.put("startTime", mechanismServerTime.getStartTime());
			 mechanismServerTimeMap.put("endTime", mechanismServerTime.getEndTime());
			 Set<Doctor> doctors = new HashSet<Doctor>();
			 for (DoctorMechanismRelation doctorMechanismRelation : mechanism.getDoctorMechanismRelations(Audit.succeed)) {
				Doctor doctor = doctorMechanismRelation.getDoctor();
				WorkDay workDay = workDayService.getWorkDayByDoctorAndData(doctor, pdate);
				if (workDay!=null) {
					for (WorkDayItem workDayItem : workDay.getWorkDayItems()) {
						String sourceTime = mechanismServerTime.getStartTime()+"-"+mechanismServerTime.getEndTime();
						if (DateUtil.isInTime(sourceTime, workDayItem.getStartTime())) {
							doctors.add(doctor);
						}
					}
				}
			} 
			
			 mechanismServerTimeMap.put("doctors",doctors);//此处放的是医生集合，不知道在页面是否可以遍历该医生对象
			 server_time_doctor_list.add(mechanismServerTimeMap);
			}
		 
		 model.addAttribute("server_time_doctor_list", server_time_doctor_list);
		 model.addAttribute("mechanism", mechanism);
		
		return "/mechanism/workDay/list";
	}
	
	/**
	 * 
	 * @param pageable
	 * @param date
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/past", method = RequestMethod.GET)
	public String past(Pageable pageable,Date startDate,Date endDate ,String nameOrphone, ModelMap model) {
		 Doctor doctorC = doctorService.getCurrent();
		 Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		 Map<String,Object> query_map = new HashMap<String,Object>();
		 query_map.put("mechanism", mechanism); 
		 query_map.put("startDate", startDate); 
		 query_map.put("endDate", endDate); 
		 query_map.put("nameOrphone", nameOrphone==null?"王双瑞":nameOrphone); //此处三目运算是做测试使用
		 query_map.put("pageable", pageable); 
		 Page<WorkDay> page = workDayService.getPageWorkDays(query_map);
		 model.addAttribute("page", page);
		 model.addAttribute("mechanism", mechanism);
		 model.addAttribute("startDate", startDate);
		 model.addAttribute("endDate", endDate);
		 model.addAttribute("nameOrphone", nameOrphone);
		 return "/mechanism/workDay/past";
	}
	
	
	
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long doctorId,Long workDayId,ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		WorkDay workDay = workDayService.find(workDayId);
//		String s = mechanism.getWorkDate().getStartTime().split(":")[0];//这是什么东西 好像不是我写的吧  wsr
		
		List<Map<String,Object>> dateDays = DateUtil.getDateDays();
		Doctor doctor = doctorService.find(doctorId);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("workDay", workDay);
		model.addAttribute("doctor", doctor);
		model.addAttribute("dateDays", dateDays);
		model.addAttribute("workDayTypes", WorkDayType.values());
		return "/mechanism/workDay/edit";
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(String start_time,String end_time,Long projectId,Long patientId,Integer num, WorkDayType workDayType,Long workDayId,Long doctorId,HttpServletRequest request, RedirectAttributes redirectAttributes) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		WorkDay workDay = workDayService.find(workDayId);
		WorkDayItem workDayItem = new WorkDayItem();
		
		if (workDayType.equals(WorkDayType.reserve)) {
			Project project = projectService.find(projectId);
			Member patientMember = memberService.find(patientId);
			Member member = patientMember.getParent();
			Map<String,Object> map1 = new HashMap<String, Object>();			
			map1.put("workDay", workDay);
			map1.put("startTime", start_time);
			map1.put("endTime", end_time);
			map1.put("count", num);
			map1.put("project", project);
			
			Map<String ,Object> return_data_map = getData(map1);
			
			return_data_map.put("workDay", workDay);
			return_data_map.put("project", project);
			return_data_map.put("patientMember", patientMember);
			return_data_map.put("member", member);
			return_data_map.put("mechanism", mechanism);
			return_data_map.put("memo", "平台端下单");
			return_data_map.put("orderMan", OrderMan.mechanism);
			Order order = orderService.persist(return_data_map);

		}else{
			workDayItem.setContent(null);
			workDayItem.setStartTime(start_time);
			workDayItem.setEndTime(end_time);
			workDayItem.setWorkDayType(workDayType);
			workDayItem.setWorkDay(workDay);
			workDayItem.setIsDeleted(false);
			workDayItem.setMechanism(mechanism);
			workDayItemService.save(workDayItem);
		}
		addFlashMessage(redirectAttributes, Message.success("操作成功"));
		return "redirect:edit.jhtml?doctorId="+doctorId+"&workDayId="+workDayId;
	}
	
	/**
	 * 
	 * @param start_time
	 * @param end_time
	 * @param workDayType
	 * @param num
	 * @param workDayId
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/verifyTime", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String verifyTime(String start_time ,String end_time ,WorkDayType workDayType ,Integer num ,Long workDayId ,Long projectId ) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		Map<String ,Object> map = new HashMap<String ,Object>();
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			WorkDay workDay = workDayService.find(workDayId);//预约日期
			String startTime = start_time;//预约开始时间
			String endTime = end_time;//预约结束时间
			if (DateUtil.getDatetoString("yyyyMMdd", workDay.getWorkDayDate()).equals(DateUtil.getDatetoString("yyyyMMdd", new Date()))) {
				if(DateUtil.compare_date(startTime, DateUtil.getDatetoString("HH:mm", new Date())) == 1){
					map.put("status", "400");
					map.put("message", "不能早于当前时间");
					map.put("data", new Object());
					System.out.println("时间不合法请重新选择时间,不合法原因为:不能早于当前时间");
					return JSONObject.fromObject(map).toString();
				}
			}
			List<WorkDayItem> workDayItems = new ArrayList<WorkDayItem>();//取出当天日期下的所有占用时间(不包括该医生在机构的上班时间)
			List<WorkDayItem> doctorMechanismWorkDayItems = new ArrayList<WorkDayItem>();//取出医生在当天的上班时间
			
			for (WorkDayItem workDayItem : workDay.getWorkDayItems()){
				if (workDayItem.getMechanism().equals(mechanism)&&!workDayItem.getWorkDayType().equals(WorkDayType.mechanism)) {
					workDayItems.add(workDayItem);
				}else{
					doctorMechanismWorkDayItems.add(workDayItem);
				}
			}
			
			Integer sumWorkDayItemStartAndEdn = 0;
			
			for (WorkDayItem workDayItem : doctorMechanismWorkDayItems) {
				String start = workDayItem.getStartTime();//数据库已经存的医生的上班时间(开始)
				String end = workDayItem.getEndTime();//数据库已经存的医生的上班时间(结束)
				String sourceTime = start+"-"+end;
				if (DateUtil.isInTime(sourceTime, startTime)) {//判断预约开始时间是否在医生的上班时间内
					sumWorkDayItemStartAndEdn++;
				}
			}
			
			if(sumWorkDayItemStartAndEdn==0){
				map.put("status", "400");
				map.put("message", "不在该医生的上班时间内");
				map.put("data", new Object());
				System.out.println("时间不合法请重新选择时间,不合法原因为:不在该医生的上班时间内");
				return JSONObject.fromObject(map).toString();
			}
			
			for (WorkDayItem workDayItem : workDayItems){
				String start = workDayItem.getStartTime();//数据库已经被预定或者已锁定时间
				String end = workDayItem.getEndTime();//数据库已经被预定或者已锁定时间
				System.out.println(start+"===="+end);
				
					if (DateUtil.compare_date(startTime, start)==-1  && DateUtil.compare_date(startTime, end) == 1) {
						map.put("status", "400");
						map.put("message", start+"至"+end+"之间已被占用");
						map.put("data", new Object());
						System.out.println("时间不合法请重新选择时间,不合法原因为:"+startTime+"，在："+start+"和"+end+"之间");
						return JSONObject.fromObject(map).toString();
					}
//					if (DateUtil.compare_date(endTime, start)==-1  && DateUtil.compare_date(endTime, end) == 1) {
//						map.put("status", "400");
//						map.put("message", start+"至"+end+"之间已被占用");
//						map.put("data", new Object());
//						System.out.println("时间不合法请重新选择时间,不合法原因为:"+startTime+"，在："+start+"和"+end+"之间");
//						return JSONObject.fromObject(map).toString();
//					}
					if (DateUtil.compare_date(startTime, start)==0) {
						map.put("status", "400");
						map.put("message", "时间不合法请重新选择时间,不合法原因为:"+startTime+"和"+start+"不能一致");
						map.put("data", new Object());
						System.out.println("时间不合法请重新选择时间,不合法原因为:"+startTime+"和"+start+"不能一致");
						return JSONObject.fromObject(map).toString();
					}
					
				}
			
			
			
			
			if (!workDayType.equals(WorkDayType.reserve)) {
				String workDayItemStartTime = getEndTime(startTime, workDay).equals("")?endTime:getEndTime(startTime, workDay);//返回下一个预约开始时间，
				if(DateUtil.compare_date(endTime, workDayItemStartTime) == -1){//判断如果预约结束时间是否在下一个时间段开始之后
					//如果占用结束时间在下一个结束时间之后,那么就不合法
					map.put("status", "400");
					map.put("message", "时间不合法请重新选择时间,不合法原因为:跨时间段");
					map.put("data", new Object());
					System.out.println("时间不合法请重新选择时间,不合法原因为:跨时间段");
					return JSONObject.fromObject(map).toString();
				}
				
				map.put("status", "200");
				map.put("message", "Ok该时间段可以使用");
				map.put("data", new Object());
				System.out.println("时间待验证");
				return JSONObject.fromObject(map).toString();
			}else{
				Integer count = num==null?0:num;//预约次数
				Project project = projectService.find(projectId);//项目实体
				Doctor doctor = project.getDoctor();//该项目所属医师
				
				Date startDate  = DateUtil.getStringtoDate(startTime, "HH:mm");
				Integer Minute = project.getTime()*count;//项目的分钟乘以次数得到预约开始时间往后延 多少分钟 
				
				endTime = DateUtil.getMinute(startDate, Minute, "HH:mm");//预约结束时间
				System.out.println("预约的是"+DateUtil.getDatetoString("yyyy-MM-dd", workDay.getWorkDayDate())+"---"+ startTime+"~~~~"+endTime);
				
				
				Map<String,Object> map1 = new HashMap<String, Object>();			
				map1.put("workDay", workDay);
				map1.put("startTime", startTime);
				map1.put("endTime", endTime);
				map1.put("count", count);
				map1.put("project", project);
				
				data_map = getData(map1);
				
				map.put("status", "200");
				map.put("message", "该时间段可以预约");
				System.out.println("你所预约的时间在:"+workDay.getWorkDayDate()+"最后面面");
				map.put("data", JsonUtils.toJson(data_map));
				return JsonUtils.toString(map);
				
			}
	}
	
	
	/**
	 * 验证该医生这一天有没有排班
	 * @param doctorId
	 * @param workDayDate
	 * @return
	 */
	@RequestMapping(value = "/verificationScheduling", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String verificationScheduling(Long doctorId ,
			String  workDayDate  ) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		try {
//			User user = userService.getCurrent();
//			Mechanism mechanism = user.getMechanism();
			Doctor doctorC = doctorService.getCurrent();
			Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
			Doctor doctor = doctorService.find(doctorId);
			WorkDay workDay = workDayService.getWorkDayByDoctorAndData(doctor, DateUtil.getStringtoDate(workDayDate, "yyyyMMdd"));
			if (workDay==null) {
				map.put("status", "400");
				map.put("message", "该医生未排班");
				map.put("data","{}");
				System.out.println(JsonUtils.toJson(map));
				return JsonUtils.toJson(map);
				
			}else{
				map.put("status", "200");
				map.put("message", "该医生已排班");
				map.put("data", workDay.getId());
				System.out.println(JsonUtils.toJson(map));
				return JsonUtils.toJson(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			map.put("status", "400");
			map.put("message", "排班失败");
			map.put("data", "{}");
			System.out.println(JsonUtils.toJson(map));
			return JsonUtils.toJson(map);
		}

	}
	
	
	
	
	/**
	 * 排班
	 * @param doctorId
	 * @param workDayDate
	 * @return
	 */
	@RequestMapping(value = "/scheduling", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String scheduling(Long doctorId ,
			String  workDayDate  ) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		try {
//			User user = userService.getCurrent();
//			Mechanism mechanism = user.getMechanism();
			Doctor doctorC = doctorService.getCurrent();
			Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
			Doctor doctor = doctorService.find(doctorId);
			WorkDay workDay = new WorkDay();
			workDay.setStartTime(mechanism.getWorkDate().getStartTime());
			workDay.setEndTime(mechanism.getWorkDate().getEndTime());
			workDay.setWorkDayDate(DateUtil.getStringtoDate(workDayDate, "yyyyMMdd"));
			workDay.setDoctor(doctor);
			workDay.setWorkType(WorkType.rest);
			workDay.setIsArrange(false);
			workDayService.save(workDay);
			map.put("status", "200");
			map.put("message", "排班成功");
			map.put("data", workDay.getId());
		} catch (Exception e) {
			// TODO: handle exception
			map.put("status", "400");
			map.put("message", "排班失败");
			map.put("data", "{}");
		}
	    return JsonUtils.toJson(map);

	}
	
	
	
	
	
	
	/**
	 * 解锁休息时间或锁定时间
	 * @param workDayItemId
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long workDayItemId) {
		if (workDayItemId != null) {
			workDayItemService.delete(workDayItemId);
		}
		return SUCCESS_MESSAGE;
	}
	
	/**
	 * 预约验证时间重新计算方法
	 * @param map
	 * @return
	 */
	public static Map<String,Object> getData(Map<String,Object> map){
		Map<String,Object> data_map = new HashMap<String, Object>();
		WorkDay workDay = (WorkDay) map.get("workDay");
		String startTime =  map.get("startTime").toString();
		String endTime =  map.get("endTime").toString();
		Integer count = Integer.valueOf(map.get("count").toString());
		Project project = (Project) map.get("project");
		boolean isOwnOrder = false;
		BigDecimal countPrice = project.getPrice().multiply(new BigDecimal(count));//先计算出总价
		BigDecimal discountPrice  = project.getPrice().multiply(new BigDecimal(count)).subtract(countPrice);//先计算出优惠价格
        Doctor doctor = project.getDoctor();
        Mechanism mechanism = project.getMechanism();
        Integer  endMinute = DateUtil.getMinute(startTime, endTime, "HH:mm");//实际做了多少分钟
        
        
        
        
		if (workDay.getWorkDayItems().size()>0) {
			String lastWorkDayEndTime = workDay.getWorkDayItems().get(workDay.getWorkDayItems().size()-1).getEndTime();
			if (DateUtil.compare_date(startTime, lastWorkDayEndTime)==-1) {//如果  预约时间在最后一个时间段 之后  就开始计算价
				Date startDate  = DateUtil.getStringtoDate(startTime, "HH:mm");//预约开始时间
//				Date endWorkDayendTime =  DateUtil.getStringtoDate(lastWorkDayEndTime, "HH:mm");//最后一个
				int differenceMinute = DateUtil.getMinute(startTime, lastWorkDayEndTime, "HH:mm");
				if (differenceMinute>5&&differenceMinute<50) {//患者预约开始时间距离预约医师上一预约结束时间大于5分钟，小于50分钟的订单，如患者预约此时间段，系统自动调节预约开始时间为上一预约结束后5分钟为本次起始时间
					startTime = DateUtil.getDatetoString("HH:mm", DateUtils.addMinutes(DateUtil.getStringtoDate(lastWorkDayEndTime, "HH:mm"),5)); 
				}
//				startTime =  DateUtil.getMinute(startDate, 5, "HH:mm");
				Integer minute = project.getTime()*count;//项目的分钟乘以次数得到预约开始时间往后延 多少分钟
				endTime = DateUtil.getMinute(startDate, minute, "HH:mm");//预约结束时间
				if (DateUtil.compare_date(endTime, mechanism.getWorkDate().getEndTime())==-1) {
					endTime = mechanism.getWorkDate().getEndTime();
				}
			}
			
			String workDayItemEndTime = getStartTime(startTime, workDay);//返回上一个预约结束时间
			
			String workDayItemStartTime = getEndTime(startTime, workDay).equals("")?endTime:getEndTime(startTime, workDay);//返回下一个预约开始时间，
			
			
//			//在判断结束时间  是否在第一条记录的开始时间之后 如果是  
			
			int differenceMinute = DateUtil.getMinute(workDayItemEndTime, startTime, "HH:mm");
			if (differenceMinute>5&&differenceMinute<50) {//患者预约开始时间距离预约医师上一预约结束时间大于5分钟，小于50分钟的订单，如患者预约此时间段，系统自动调节预约开始时间为上一预约结束后5分钟为本次起始时间
				startTime = DateUtil.getDatetoString("HH:mm", DateUtils.addMinutes(DateUtil.getStringtoDate(workDayItemEndTime, "HH:mm"),5)); 
				Date startDate  = DateUtil.getStringtoDate(startTime, "HH:mm");
				Integer minute = project.getTime()*count;//项目的分钟乘以次数得到预约开始时间往后延 多少分钟
				endTime = DateUtil.getMinute(startDate, minute, "HH:mm");//预约结束时间
			}
			
			
			//判断预约结束时间是否在下一个时间段开始之后  
			if(DateUtil.compare_date(endTime, workDayItemStartTime) == -1){
				//判断如果预约结束时间是否在下一个时间段开始之后
				endTime = workDayItemStartTime;//如果预约结束时间在下一个结束时间之后，那么预约下一个预约开始时间就是本次预约结束时间
				endMinute = DateUtil.getMinute(startTime, endTime, "HH:mm");
				count = (int) Math.ceil((double) endMinute / (double) project.getTime());//先去算出预约总次数(预约总次数为预约的分钟数与本次项目的分钟数向上取整)
			}
			
		}
		
		if (endMinute%project.getTime()!=0) {
			if (endMinute%project.getTime()<project.getTime()/2) {
				countPrice = project.getPrice().multiply(new BigDecimal(count-1)).add(project.getPrice().divide(new BigDecimal(2)));
			}
		}
		
//		countPrice = project.getPrice().multiply(new BigDecimal(count));
		discountPrice = project.getPrice().multiply(new BigDecimal(count)).subtract(countPrice);
		
		System.out.println("实际预约："+count+"次；实际预约时间为："+workDay.getWorkDayDate()+"，"+startTime+"至"+endTime+",总共"+endMinute+"分钟，实际总价钱为："+countPrice+"，实际优惠价为："+discountPrice);
		
		data_map.put("endMinute", endMinute);
		data_map.put("discountPrice", discountPrice);
		data_map.put("isOwnOrder", isOwnOrder);
		data_map.put("startTime", startTime);
		data_map.put("endTime", endTime);
		data_map.put("countPrice", countPrice);
		data_map.put("count", count);
		System.out.println("你所预约的时间在:"+workDay.getWorkDayDate()+"最后面面");
		return data_map;
		
	}
	
	
	/**
	 * 返回下一个预约开始时间，也就是本次预约结束时间
	 * @param start
	 * @param workDay
	 * @return
	 */
	public static String getEndTime(String startTime,WorkDay workDay){
		List lists = new ArrayList();
		String workDayItemStartTime = "";
		System.out.println(startTime);
		List<WorkDayItem> workDayItems = workDay.getWorkDayItems();
		System.out.println(workDayItems.size());
		for (int i = 0; i < workDayItems.size(); i++) {//将所有已占用的时间加到lists里面
			
			System.out.println(workDayItems.get(i).getStartTime()+"---"+workDayItems.get(i).getEndTime());
			lists.add(DateUtil.getStringtoDate(workDayItems.get(i).getStartTime(), "HH:mm"));
			lists.add(DateUtil.getStringtoDate(workDayItems.get(i).getEndTime(), "HH:mm"));
			
			
			
			
		}
		
//		for (WorkDayItem workDayItem : workDay.getWorkDayItems()){//将所有已占用的时间加到lists里面
//			lists.add(DateUtil.getStringtoDate(workDayItem.getStartTime(), "HH:mm"));
//			lists.add(DateUtil.getStringtoDate(workDayItem.getEndTime(), "HH:mm"));
//		}
		ComparatorDate c = new ComparatorDate();
		Collections.sort(lists, c);  
		for (int i = 0; i < lists.size(); i++) {
			if (DateUtil.compare_date(startTime, DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)))==0) {//判断开始时间如果在最后一个就返回空
				if (i==(lists.size()-1)) {
					
					return workDayItemStartTime;
				}else{
					workDayItemStartTime = DateUtil.getDatetoString("HH:mm", (Date)lists.get(i+1));
					return workDayItemStartTime;
				}
			}
		}
		
		lists.add(DateUtil.getStringtoDate(startTime, "HH:mm"));//将开始时间插入lists集合并转成Date类型
//		ComparatorDate c = new ComparatorDate();
		Collections.sort(lists, c);  
		
		for (int i = 0; i < lists.size(); i++) {
			if (DateUtil.compare_date(startTime, DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)))==0) {
				if (i==(lists.size()-1)) {
					return workDayItemStartTime;
				}else{
					workDayItemStartTime = DateUtil.getDatetoString("HH:mm", (Date)lists.get(i+1));
					return workDayItemStartTime;
				}
				
				
					
			}
			System.out.println(DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)));
		}
		
		
		return workDayItemStartTime;
	}
	
	/**
	 * 返回上一个预约结束，也就是本次预约开始时间 
	 * @param start
	 * @param workDay
	 * @return
	 */
	public static String getStartTime(String startTime,WorkDay workDay){
		List lists = new ArrayList();
		boolean fals = false;
		
		List<WorkDayItem> workDayItems = workDay.getWorkDayItems();
		for (int i = 0; i < workDayItems.size(); i++) {//将所有已占用的时间加到lists里面
			lists.add(DateUtil.getStringtoDate(workDayItems.get(i).getStartTime(), "HH:mm"));
			lists.add(DateUtil.getStringtoDate(workDayItems.get(i).getEndTime(), "HH:mm"));
			if (DateUtil.compare_date(startTime,workDayItems.get(i).getEndTime()) == 0) {
				return startTime;
			}
			
			
		}
//		for (WorkDayItem workDayItem : workDay.getWorkDayItems()){//将所有已占用的时间加到lists里面
//			lists.add(DateUtil.getStringtoDate(workDayItem.getStartTime(), "HH:mm"));
//			lists.add(DateUtil.getStringtoDate(workDayItem.getEndTime(), "HH:mm"));
//			if (DateUtil.compare_date(startTime,workDayItem.getEndTime()) == 0) {
//				return startTime;
//			}
//			
//		}
		lists.add(DateUtil.getStringtoDate(startTime, "HH:mm"));//将开始时间插入lists集合并转成Date类型
		ComparatorDate c = new ComparatorDate();
		Collections.sort(lists, c);  
		
		String workDayItemEndTime = "";
		
		if (DateUtil.compare_date(startTime, DateUtil.getDatetoString("HH:mm", (Date)lists.get(0)))==0) {//如果预约开始时间在第一个就返回他自己的时间
			return startTime;
		}
		if (DateUtil.compare_date(startTime, DateUtil.getDatetoString("HH:mm", (Date)lists.get(lists.size()-1)))==0) {//如果预约开始时间在最后一个就返回他自己的时间
			workDayItemEndTime =  DateUtil.getDatetoString("HH:mm", (Date)lists.get(lists.size()-2));
			return workDayItemEndTime;
		}
		
		for (int i = 0; i < lists.size(); i++) {
			System.out.println(DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)));
		}
		
		for (int i = 0; i < lists.size(); i++) {
			if (DateUtil.compare_date(startTime, DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)))==0) {
				workDayItemEndTime = DateUtil.getDatetoString("HH:mm", (Date)lists.get(i-1));
				break;
			}
			System.out.println(DateUtil.getDatetoString("HH:mm",(Date)lists.get(i)));
		}
		return workDayItemEndTime;
	}
	
	
	
}