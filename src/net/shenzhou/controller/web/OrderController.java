/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import net.shenzhou.Config;
import net.shenzhou.Setting;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Information;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.OrderLog;
import net.shenzhou.entity.PaymentMethod;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Refunds;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.Sn;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.Information.DisposeState;
import net.shenzhou.entity.Information.InformationType;
import net.shenzhou.entity.Information.StateType;
import net.shenzhou.entity.Information.UserType;
import net.shenzhou.entity.Order.OrderMan;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.entity.Order.ServeState;
import net.shenzhou.entity.Order.ShippingStatus;
import net.shenzhou.entity.OrderLog.Type;
import net.shenzhou.entity.Refunds.Method;
import net.shenzhou.entity.Refunds.Status;
import net.shenzhou.entity.WorkDay.WorkType;
import net.shenzhou.entity.WorkDayItem;
import net.shenzhou.entity.WorkDayItem.WorkDayType;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.EvaluateService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.InformationService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderLogService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.PaymentMethodService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.RefundsService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.SnService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.service.VisitMessageService;
import net.shenzhou.service.WorkDayItemService;
import net.shenzhou.service.WorkDayService;
import net.shenzhou.util.ComparatorDate;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MobileUtil;
import net.shenzhou.util.PushUtil;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 订单
 * @date 2017-10-25 11:53:31
 * @author fl
 *
 */
@Controller("webOrderController")
@RequestMapping("/web/order")
public class OrderController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService ;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService ;
	@Resource(name = "visitMessageServiceImpl")
	private VisitMessageService visitMessageService ;
	@Resource(name = "evaluateServiceImpl")
	private EvaluateService evaluateService ;
	@Resource(name = "verificationServiceImpl")
	private VerificationService verificationService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	@Resource(name = "workDayServiceImpl")
	private WorkDayService workDayService;
	@Resource(name = "workDayItemServiceImpl")
	private WorkDayItemService workDayItemService;
	@Resource(name = "informationServiceImpl")
	private InformationService informationService;
	@Resource(name = "paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "refundsServiceImpl")
	private RefundsService refundsService;
	@Resource(name = "snServiceImpl")
	private SnService snService;
	@Resource(name = "orderLogServiceImpl")
	private OrderLogService orderLogService;
	
	/**
	 * 确认订单
	 */
	@RequestMapping(value = "/toConfirmOrder", method = RequestMethod.GET)
	public String toConfirmOrder(ModelMap model,Long projectId) {
		Member member = memberService.getCurrent();
		if(member==null){
			return "redirect:/web/login/toLogin.jhtml";
		}
		
		Project project = projectService.find(projectId);
		Doctor doctor = project.getDoctor();
		Mechanism mechanism = project.getMechanism();
		List<Member> patientMember_List = new ArrayList<Member>();
		for (Member member2 : member.getChildren()) {
			if (!member2.getIsDeleted()) {
				patientMember_List.add(member2);
			}
		}
		
		model.addAttribute("member", member);
		model.addAttribute("project", project);
		model.addAttribute("doctor", doctor);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("patientMemberList", patientMember_List);
		model.addAttribute("doctorPhone", MobileUtil.getStarString2(doctor.getMobile(),3,4));
		model.addAttribute("introduce", project.getIntroduce().substring(0, 20)+"……");
		model.addAttribute("price", project.getPrice());
		model.addAttribute("num", "1");
		model.addAttribute("startTime", "");
		model.addAttribute("endTime", "");
		
		return "/web/serve/confirmOrder";
	}
	
	/**
	 * 确认时间
	 */
	@RequestMapping(value = "/toConfirmTime", method = RequestMethod.GET)
	public String toConfirmTime(ModelMap model,String num,Long projectId) {
		Project project = projectService.find(projectId);
		Doctor doctor = project.getDoctor();
		
		List<WorkDay> doctorWorkDays = new ArrayList<WorkDay>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = DateUtil.getStringtoDate(df.format(new Date()), "yyyy-MM-dd");
		doctorWorkDays = workDayService.getWorkDays(doctor, date);
		
		model.addAttribute("projectId",project.getId());
		model.addAttribute("count",num);
		model.addAttribute("doctorId",doctor.getId());
		model.addAttribute("doctorWorkDays",doctorWorkDays);
		model.addAttribute("workDaySize", doctorWorkDays.size());
		
		return "/web/serve/confirmTime";
	}
	
	/**
	 * 选择时间(医生工作日详情)
	 */
	@RequestMapping(value = "/doctorWorkDayDetails", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String doctorWorkDayDetails(ModelMap model,Long workDayId) {
		WorkDay workDay = workDayService.find(workDayId);
		Map<String,Object> data_map = new HashMap<String, Object>();
		
		List<WorkDayItem> mechanism_workDayItemList = new ArrayList<WorkDayItem>();
		List<WorkDayItem> order_workDayItemList = new ArrayList<WorkDayItem>();
		for(WorkDayItem workDayItem : workDay.getWorkDayItems()){
			if(workDayItem.getWorkDayType().equals(WorkDayType.rest)||workDayItem.getWorkDayType().equals(WorkDayType.locking)||workDayItem.getWorkDayType().equals(WorkDayType.reserve)){
				order_workDayItemList.add(workDayItem);
			}else if(workDayItem.getWorkDayType().equals(WorkDayType.mechanism)){
				mechanism_workDayItemList.add(workDayItem);
			}
		}
		
		List<Map<String,Object>> mechanism_itemList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> order_itemList = new ArrayList<Map<String,Object>>();
		
		for(WorkDayItem workDayItem:mechanism_workDayItemList){
			Map<String,Object> workDayItem_map = new HashMap<String, Object>();
			workDayItem_map.put("id", workDayItem.getId());
			workDayItem_map.put("startTime", workDayItem.getStartTime());
			workDayItem_map.put("endTime", workDayItem.getEndTime());
			workDayItem_map.put("workDayType", workDayItem.getWorkDayType());
			if(workDayItem.getOrder()!=null){
				workDayItem_map.put("patientName", workDayItem.getOrder().getPatientMember().getName());
			}else{
				workDayItem_map.put("patientName", "");
			}
			if(workDayItem.getWorkDayType().equals(WorkDayType.mechanism)){
				workDayItem_map.put("meschanismName",workDayItem.getMechanism().getName());
				workDayItem_map.put("meschanismId",workDayItem.getMechanism().getId());
			}else{
				workDayItem_map.put("meschanismName","");
				workDayItem_map.put("meschanismId","");
			}
			//计算距离和宽度
			String begin = "00:00";
			//距离
			long distance =  DateUtil.getMinute(begin, workDayItem.getStartTime(), "HH:mm");
			//宽度
			long width =  DateUtil.getMinute(workDayItem.getStartTime(), workDayItem.getEndTime(), "HH:mm");
			workDayItem_map.put("distance",(distance/5)*(6.67/12));
			workDayItem_map.put("width",(width/5)*(6.67/12));
			mechanism_itemList.add(workDayItem_map);
		}
		
		for(WorkDayItem workDayItem:order_workDayItemList){
			Map<String,Object> workDayItem_map = new HashMap<String, Object>();
			workDayItem_map.put("id", workDayItem.getId());
			workDayItem_map.put("startTime", workDayItem.getStartTime());
			workDayItem_map.put("endTime", workDayItem.getEndTime());
			workDayItem_map.put("workDayType", workDayItem.getWorkDayType());
			if(workDayItem.getOrder()!=null){
				workDayItem_map.put("patientName", workDayItem.getOrder().getPatientMember().getName());
			}else{
				workDayItem_map.put("patientName", "");
			}
			if(workDayItem.getWorkDayType().equals(WorkDayType.mechanism)){
				workDayItem_map.put("meschanismName",workDayItem.getMechanism().getName());
				workDayItem_map.put("meschanismId",workDayItem.getMechanism().getId());
			}else{
				workDayItem_map.put("meschanismName","");
				workDayItem_map.put("meschanismId","");
			}
			//计算距离和宽度
			String begin = "00:00";
			//距离
			long distance =  DateUtil.getMinute(begin, workDayItem.getStartTime(), "HH:mm");
			//宽度
			long width = DateUtil.getMinute(workDayItem.getStartTime(), workDayItem.getEndTime(), "HH:mm");
			workDayItem_map.put("distance",(distance/5)*(6.67/12));
			workDayItem_map.put("width",(width/5)*(6.67/12));
			
			order_itemList.add(workDayItem_map);
		}
		
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("mechanismItemList", mechanism_itemList);
		data.put("orderItemList", order_itemList);
		
		return JsonUtils.toJson(data);
	}
	
	
	/**
	 * 验证时间
	 */
	@RequestMapping(value = "/verifyTime", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String verifyTime(ModelMap model,Long workDayId,String startTime,Integer count,Long projectId) {
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		WorkDay workDay = workDayService.find(workDayId);//预约日期
		Project project = projectService.find(projectId);//项目实体
		Doctor doctor = project.getDoctor();//该项目所属医师
		
		
		if (workDay.getWorkType().equals(WorkType.rest)) {
			map.put("status", "400");
			map.put("message", workDay.getWorkDayDate()+"为"+project.getDoctor().getName()+"休息时间");
			map.put("data","{}");
			return JsonUtils.toJson(map);
		}
		
		Date now = new Date();
		Date now_10 = new Date(now.getTime() - 600000);
		
		Date today = workDay.getWorkDayDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(today); 
		String todays = sdf.format(now); 
		
		if(todays.equals(dateNowStr)){
			if(DateUtil.compare_date(startTime,DateUtil.getDatetoString("HH:mm", now_10))==1){
				map.put("status", "400");
				map.put("message", startTime+"在当前时间之前.不能预约");
				map.put("data", "{}");
				return JsonUtils.toJson(map);
			}
		}
		
		Date startDate  = DateUtil.getStringtoDate(startTime, "HH:mm");
		Integer Minute = project.getTime()*count;//项目的分钟乘以次数得到预约开始时间往后延 多少分钟
		String endTime = DateUtil.getMinute(startDate, Minute, "HH:mm");//预约结束时间
		
		
		Mechanism mechanism = null;
		WorkDayItem mechanism_workDayItem = null;
		List<WorkDayItem> workDayItems = workDayItemService.getDoctorMechanismTime(doctor, workDay);
		for(WorkDayItem workDayItem : workDayItems){
			if(startTime.equals(workDayItem.getStartTime())||startTime.equals(workDayItem.getEndTime())||DateUtil.compare_date(startTime, workDayItem.getStartTime())==-1&&DateUtil.compare_date(startTime, workDayItem.getEndTime())==1){
				mechanism = workDayItem.getMechanism();
				mechanism_workDayItem = workDayItem;
				break;
			}
		}
		
		if(mechanism==null||mechanism_workDayItem==null){
			map.put("status", "400");
			map.put("message","当前选择的时间没有机构排班,请重新选择时间");
			map.put("data", "{}");
			return JsonUtils.toJson(map);
		}
		
		if(!mechanism.equals(project.getMechanism())){
			map.put("status", "400");
			map.put("message","抱歉,当前选择的项目只能在"+project.getMechanism().getName()+"机构预约,请正确选择时间");
			map.put("data", "{}");
			return JsonUtils.toJson(map);
		}
		
		if(DateUtil.compare_date(startTime, mechanism_workDayItem.getStartTime())==1||DateUtil.compare_date(startTime, mechanism_workDayItem.getEndTime())==-1){
			map.put("status", "400");
			map.put("message","开始时间在机构设置时间外,请重新选择开始时间");
			map.put("data", "{}");
			return JsonUtils.toJson(map);
		}
		
		List<WorkDayItem> all_list = workDayItemService.getWorkDayItem(doctor, workDay);
		List<WorkDayItem> mechanism_item_list = new ArrayList<WorkDayItem>();
		for(WorkDayItem WorkDayItem : all_list){
			if(DateUtil.compare_date(WorkDayItem.getStartTime(), mechanism_workDayItem.getStartTime())==-1&&DateUtil.compare_date(WorkDayItem.getEndTime(), mechanism_workDayItem.getEndTime())==1){
				mechanism_item_list.add(WorkDayItem);
			}
			if(DateUtil.compare_date(WorkDayItem.getStartTime(), mechanism_workDayItem.getStartTime())==0&&DateUtil.compare_date(WorkDayItem.getEndTime(), mechanism_workDayItem.getEndTime())==1){
				mechanism_item_list.add(WorkDayItem);
			}
			if(DateUtil.compare_date(WorkDayItem.getStartTime(), mechanism_workDayItem.getStartTime())==-1&&DateUtil.compare_date(WorkDayItem.getEndTime(), mechanism_workDayItem.getEndTime())==0){
				mechanism_item_list.add(WorkDayItem);
			}
		}
		
		//判断输入的开始时间时候和机构已有的时间段冲突
		for(WorkDayItem workDayItem : mechanism_item_list){
			if(startTime.equals(workDayItem.getStartTime())){
				map.put("status", "400");
				map.put("message","该开始时间已被占用,请重新选择开始时间");
				map.put("data", "{}");
				return JsonUtils.toJson(map);
			}
			if(DateUtil.compare_date(startTime, workDayItem.getStartTime())==-1&&DateUtil.compare_date(startTime, workDayItem.getEndTime())==1){
				map.put("status", "400");
				map.put("message","该时间段已被占用,请重新选择开始时间");
				map.put("data", "{}");
				return JsonUtils.toJson(map);			
			}
		}
			
					
		Map<String,Object> map1 = new HashMap<String, Object>();			
		map1.put("workDay", workDay);
		map1.put("startTime", startTime);
		map1.put("endTime", endTime);
		map1.put("count", count);
		map1.put("project", project);
		map1.put("mechanism_item_list", mechanism_item_list);
		map1.put("mechanism_workDayItem", mechanism_workDayItem);
		
		data_map = getData(map1);
		
		map.put("status", "200");
		map.put("message", "该时间段可以预约");
		System.out.println("你所预约的时间在:"+workDay.getWorkDayDate()+"最后面面");
		map.put("data", JsonUtils.toJson(data_map));
		return JsonUtils.toJson(map);
		
	}
	
	/**
	 * 返回预约次数,结束时间,总价
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
		List<WorkDayItem> mechanism_item_list = (List<WorkDayItem>)map.get("mechanism_item_list");
		WorkDayItem mechanism_workDayItem = (WorkDayItem)map.get("mechanism_workDayItem");
		boolean isOwnOrder = false;
		BigDecimal countPrice = project.getPrice().multiply(new BigDecimal(count));//先计算出总价
		BigDecimal discountPrice  = project.getPrice().multiply(new BigDecimal(count)).subtract(countPrice);//先计算出优惠价格
        Doctor doctor = project.getDoctor();
        Mechanism mechanism = project.getMechanism();
        Integer  endMinute = DateUtil.getMinute(startTime, endTime, "HH:mm");//实际做了多少分钟
        
        /**先判断当前机构时间段里是否有其他时间项**/
        if(mechanism_item_list.size()<=0){
        	/**判断结束时间是否超过机构时间的结束时间**/
        	if(DateUtil.compare_date(endTime, mechanism_workDayItem.getEndTime())==-1){
        		endMinute = DateUtil.getMinute(startTime, mechanism_workDayItem.getEndTime(), "HH:mm");//计算出本次预约开始时间到预约结束时间多少分钟
				endTime = DateUtil.getMinute(DateUtil.getStringtoDate(startTime, "HH:mm"), endMinute, "HH:mm");//预约结束时间
				count = (int) Math.ceil((double) endMinute / (double) project.getTime());//先去算出预约总次数(预约总次数为预约的分钟数与本次项目的分钟数向上取整)
				countPrice = project.getPrice().multiply(new BigDecimal(count));//总价为项目的单价乘以预约次数
				if (endMinute%project.getTime()!=0) {
					if (endMinute%project.getTime()<project.getTime()/2) {
						countPrice = project.getPrice().multiply(new BigDecimal(count-1)).add(project.getPrice().divide(new BigDecimal(2)));
					}
				}
				discountPrice = project.getPrice().multiply(new BigDecimal(count)).subtract(countPrice);
				
				//计算距离和宽度
				String begin = "00:00";
				//距离
				long distance =  DateUtil.getMinute(begin, startTime, "HH:mm");
				//宽度
				long width = DateUtil.getMinute(startTime, endTime, "HH:mm");
				
				data_map.put("distance",(distance/5)*(6.67/12));
				data_map.put("width",(width/5)*(6.67/12));
				data_map.put("discountPrice", discountPrice);
				data_map.put("isOwnOrder", isOwnOrder);
				data_map.put("startTime", startTime);
				data_map.put("endTime", endTime);
				data_map.put("countPrice", countPrice);
				data_map.put("count", count);
				data_map.put("key", true);
				System.out.println("预约"+endMinute+"分钟");
				System.out.println("你所预约的时间在:"+workDay.getWorkDayDate()+"最前面2,预约的为医师的"+ isOwnOrder+"时间");
				
				return data_map;
        	}else{
        		countPrice = project.getPrice().multiply(new BigDecimal(count));
    			discountPrice = project.getPrice().multiply(new BigDecimal(count)).subtract(countPrice);
    			
    			//计算距离和宽度
				String begin = "00:00";
				//距离
				long distance =  DateUtil.getMinute(begin, startTime, "HH:mm");
				//宽度
				long width = DateUtil.getMinute(startTime, endTime, "HH:mm");
				
				data_map.put("distance",(distance/5)*(6.67/12));
				data_map.put("width",(width/5)*(6.67/12));
    			data_map.put("discountPrice", discountPrice);
    			data_map.put("isOwnOrder", isOwnOrder);
    			data_map.put("startTime", startTime);
    			data_map.put("endTime", endTime);
    			data_map.put("countPrice", countPrice);
    			data_map.put("count", count);
    			data_map.put("key", true);
    			return data_map;
        	}
        }else{
        	/**先判断开始时间是不是当前机构时间的最后一个时间点**/
        	if(isEndData(startTime,mechanism_item_list).equals("true")){
        		/**判断结束时间是否超过机构时间的结束时间**/
            	if(DateUtil.compare_date(endTime, mechanism_workDayItem.getEndTime())==-1){
            		endMinute = DateUtil.getMinute(startTime, mechanism_workDayItem.getEndTime(), "HH:mm");//计算出本次预约开始时间到预约结束时间多少分钟
    				endTime = DateUtil.getMinute(DateUtil.getStringtoDate(startTime, "HH:mm"), endMinute, "HH:mm");//预约结束时间
    				count = (int) Math.ceil((double) endMinute / (double) project.getTime());//先去算出预约总次数(预约总次数为预约的分钟数与本次项目的分钟数向上取整)
    				countPrice = project.getPrice().multiply(new BigDecimal(count));//总价为项目的单价乘以预约次数
    				if (endMinute%project.getTime()!=0) {
    					if (endMinute%project.getTime()<project.getTime()/2) {
    						countPrice = project.getPrice().multiply(new BigDecimal(count-1)).add(project.getPrice().divide(new BigDecimal(2)));
    					}
    				}
    				discountPrice = project.getPrice().multiply(new BigDecimal(count)).subtract(countPrice);
    				
    				//计算距离和宽度
    				String begin = "00:00";
    				//距离
    				long distance =  DateUtil.getMinute(begin, startTime, "HH:mm");
    				//宽度
    				long width = DateUtil.getMinute(startTime, endTime, "HH:mm");
    				
    				data_map.put("distance",(distance/5)*(6.67/12));
    				data_map.put("width",(width/5)*(6.67/12));
    				data_map.put("discountPrice", discountPrice);
    				data_map.put("isOwnOrder", isOwnOrder);
    				data_map.put("startTime", startTime);
    				data_map.put("endTime", endTime);
    				data_map.put("countPrice", countPrice);
    				data_map.put("count", count);
    				data_map.put("key", true);
    				System.out.println("预约"+endMinute+"分钟");
    				System.out.println("你所预约的时间在:"+workDay.getWorkDayDate()+"最前面2,预约的为医师的"+ isOwnOrder+"时间");
    				
    				return data_map;
            	}else{
            		countPrice = project.getPrice().multiply(new BigDecimal(count));
        			discountPrice = project.getPrice().multiply(new BigDecimal(count)).subtract(countPrice);
        			
        			//计算距离和宽度
    				String begin = "00:00";
    				//距离
    				long distance =  DateUtil.getMinute(begin, startTime, "HH:mm");
    				//宽度
    				long width = DateUtil.getMinute(startTime, endTime, "HH:mm");
    				
    				data_map.put("distance",(distance/5)*(6.67/12));
    				data_map.put("width",(width/5)*(6.67/12));
        			data_map.put("discountPrice", discountPrice);
        			data_map.put("isOwnOrder", isOwnOrder);
        			data_map.put("startTime", startTime);
        			data_map.put("endTime", endTime);
        			data_map.put("countPrice", countPrice);
        			data_map.put("count", count);
        			data_map.put("key", true);
        			return data_map;
            	}
        	}
        	/**不是最后一个时间点,判断是否超过下一个时间段的开始时间**/
        	else{
        		if(DateUtil.compare_date(endTime, getEndData(startTime,mechanism_item_list))==-1){
					Date startDate  = DateUtil.getStringtoDate(startTime, "HH:mm");
					endMinute = DateUtil.getMinute(startTime, getEndData(startTime,workDay.getWorkDayItems()), "HH:mm");//计算出本次预约开始时间到预约结束时间多少分钟
					endTime = DateUtil.getMinute(startDate, endMinute, "HH:mm");//预约结束时间
					count = (int) Math.ceil((double) endMinute / (double) project.getTime());//先去算出预约总次数(预约总次数为预约的分钟数与本次项目的分钟数向上取整)
					countPrice = project.getPrice().multiply(new BigDecimal(count));//总价为项目的单价乘以预约次数
					if (endMinute%project.getTime()!=0) {
						if (endMinute%project.getTime()<project.getTime()/2) {
							countPrice = project.getPrice().multiply(new BigDecimal(count-1)).add(project.getPrice().divide(new BigDecimal(2)));
						}
					}
					discountPrice = project.getPrice().multiply(new BigDecimal(count)).subtract(countPrice);
					
					//计算距离和宽度
					String begin = "00:00";
					//距离
					long distance =  DateUtil.getMinute(begin, startTime, "HH:mm");
					//宽度
					long width = DateUtil.getMinute(startTime, endTime, "HH:mm");
					
					data_map.put("distance",(distance/5)*(6.67/12));
					data_map.put("width",(width/5)*(6.67/12));
					data_map.put("discountPrice", discountPrice);
					data_map.put("isOwnOrder", isOwnOrder);
					data_map.put("startTime", startTime);
					data_map.put("endTime", endTime);
					data_map.put("countPrice", countPrice);
					data_map.put("count", count);
					data_map.put("key", true);
					System.out.println("预约"+endMinute+"分钟");
					System.out.println("你所预约的时间在:"+workDay.getWorkDayDate()+"最前面2,预约的为医师的"+ isOwnOrder+"时间");
					
					return data_map;
				}
				//
				else{
					Date startDate  = DateUtil.getStringtoDate(startTime, "HH:mm");
					Integer minute = project.getTime()*count;//项目的分钟乘以次数得到预约开始时间往后延 多少分钟
					endTime = DateUtil.getMinute(startDate, minute, "HH:mm");//预约结束时间
					countPrice = project.getPrice().multiply(new BigDecimal(count));
					discountPrice = project.getPrice().multiply(new BigDecimal(count)).subtract(countPrice);
					
					//计算距离和宽度
					String begin = "00:00";
					//距离
					long distance =  DateUtil.getMinute(begin, startTime, "HH:mm");
					//宽度
					long width = DateUtil.getMinute(startTime, endTime, "HH:mm");
					
					data_map.put("distance",(distance/5)*(6.67/12));
					data_map.put("width",(width/5)*(6.67/12));
					data_map.put("discountPrice", discountPrice);
					data_map.put("isOwnOrder", isOwnOrder);
					data_map.put("startTime", startTime);
					data_map.put("endTime", endTime);
					data_map.put("countPrice", countPrice);
					data_map.put("count", count);
					data_map.put("key", true);
					System.out.println("你所预约的时间在:"+workDay.getWorkDayDate()+"最后面面");
					return data_map;
				}
        	}
        }
				
	}
	
	
	/**
	 * 确认订单(选择时间之后的数据)
	 */
	@RequestMapping(value = "/confirmOrder", method = RequestMethod.GET)
	public String confirmOrder(ModelMap model,Long projectId,String startTime,String endTime,String countPrice,String num,Long workDayId) {
		Member member = memberService.getCurrent();
		if(member==null){
			return "redirect:/web/login/toLogin.jhtml";
		}
		
		Project project = projectService.find(projectId);
		Doctor doctor = project.getDoctor();
		Mechanism mechanism = project.getMechanism();
		List<Member> patientMember_List = new ArrayList<Member>();
		for (Member member2 : member.getChildren()) {
			if (!member2.getIsDeleted()) {
				patientMember_List.add(member2);
			}
		}
		
		model.addAttribute("member", member);
		model.addAttribute("project", project);
		model.addAttribute("doctor", doctor);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("patientMemberList", patientMember_List);
		model.addAttribute("doctorPhone", MobileUtil.getStarString2(doctor.getMobile(),3,4));
		model.addAttribute("introduce", project.getIntroduce().substring(0, 20)+"……");
		model.addAttribute("price", countPrice==null?project.getPrice():countPrice);
		model.addAttribute("num", num==null?"1":num);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("workDayId", workDayId);
		return "/web/serve/confirmOrder";
	}
	
	
	/**
	 * 提交订单
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/submitOrder", method = RequestMethod.GET)
	public String submitOrder(ModelMap model,Long projectId,String startTime,Integer num,Long patientId,Long workDayId,String memo) throws IOException {
		Project project = projectService.find(projectId);//项目id
		
		Doctor doctor = project.getDoctor();
//			Mechanism mechanism = doctor.getMechanism();//次处稍有更改   医生属于多机构
		Mechanism mechanism = project.getMechanism();//机构 只取医生的项目所在的机构
		
		Member patientMember = memberService.find(patientId);//患者id
		Integer count = num;//预约次数
		WorkDay workDay = workDayService.find(workDayId);//预约日期
		Date startDate  = DateUtil.getStringtoDate(startTime, "HH:mm");
		Integer Minute = project.getTime()*count;//项目的分钟乘以次数得到预约开始时间往后延 多少分钟
		
		String endTime = DateUtil.getMinute(startDate, Minute, "HH:mm");//预约结束时间
		
		
		Member member = memberService.getCurrent();
		if(member == null){
			return "redirect:/web/order/toConfirmOrder.jhtml?porjectId="+projectId;
		}
		/*if(member.getPaymentPassword()==null||member.getPaymentPassword().equals("")){
			return "redirect:/web/order/toConfirmOrder.jhtml?porjectId="+projectId;
		}*/
		System.out.println("预约的是"+DateUtil.getDatetoString("yyyy-MM-dd", workDay.getWorkDayDate())+"---"+ startTime+"~~~~"+endTime);
		
		
		WorkDayItem mechanism_workDayItem = null;
		List<WorkDayItem> workDayItems = workDayItemService.getDoctorMechanismTime(doctor, workDay);
		for(WorkDayItem workDayItem : workDayItems){
			if(startTime.equals(workDayItem.getStartTime())||startTime.equals(workDayItem.getEndTime())||DateUtil.compare_date(startTime, workDayItem.getStartTime())==-1&&DateUtil.compare_date(startTime, workDayItem.getEndTime())==1){
				mechanism_workDayItem = workDayItem;
				break;
			}
		}
		if(mechanism==null||mechanism_workDayItem==null){
			return "redirect:/web/order/toConfirmOrder.jhtml?porjectId="+projectId;
		}
		
		List<WorkDayItem> all_list = workDayItemService.getWorkDayItem(doctor, workDay);
		List<WorkDayItem> mechanism_item_list = new ArrayList<WorkDayItem>();
		for(WorkDayItem WorkDayItem : all_list){
			if(DateUtil.compare_date(WorkDayItem.getStartTime(), mechanism_workDayItem.getStartTime())==-1){
				mechanism_item_list.add(WorkDayItem);
			}
		}
		
		
		
		for (WorkDayItem workDayItem : mechanism_item_list){
		String start = workDayItem.getStartTime();//数据库已经被预定或者已锁定时间
		String end = workDayItem.getEndTime();//数据库已经被预定或者已锁定时间
		System.out.println(start+"===="+end);
			if (DateUtil.compare_date(startTime, start)==-1  && DateUtil.compare_date(startTime, end) == 1) {
				//map.put("message", "时间不合法请重新选择时间,不合法原因为:"+startTime+"，在："+start+"和"+end+"之间");
				return "redirect:/web/order/toConfirmOrder.jhtml?porjectId="+projectId;
			}
			if (DateUtil.compare_date(startTime, start)==0) {
				//map.put("message", "时间不合法请重新选择时间,不合法原因为:"+startTime+"和"+start+"不能一致");
				return "redirect:/web/order/toConfirmOrder.jhtml?porjectId="+projectId;
			}
			
		}

		Map<String,Object> map1 = new HashMap<String, Object>();			
		map1.put("workDay", workDay);
		map1.put("startTime", startTime);
		map1.put("endTime", endTime);
		map1.put("count", count);
		map1.put("project", project);
		map1.put("mechanism_item_list", mechanism_item_list);
		map1.put("mechanism_workDayItem", mechanism_workDayItem);
		Map<String ,Object> return_data_map = getData(map1);
//			return_data_map.get("discountPrice");
//			return_data_map.get("isOwnOrder");
//			return_data_map.get("startTime");
//			return_data_map.get("endTime");
//			return_data_map.get("countPrice");
//			return_data_map.get("count");
		
		//目前在这里判断用户自身时间是否重复(后期会提到验证时间方法里)
		String startTime_patient = return_data_map.get("startTime").toString();
		String endTime_patient = return_data_map.get("endTime").toString();
		
		Boolean isConflict = orderService.memberTimeRepetition(startTime_patient, endTime_patient, workDay, patientMember);
		if(isConflict){
			//map.put("message", "该用户预约时间已有安排");
			return "redirect:/web/order/toConfirmOrder.jhtml?porjectId="+projectId;
		}
		
		return_data_map.put("workDay", workDay);
		return_data_map.put("project", project);
		return_data_map.put("patientMember", patientMember);
		return_data_map.put("member", member);
		return_data_map.put("mechanism", mechanism);
//			return_data_map.put("discountPrice", discountPrice);
//			return_data_map.put("countPrice",return_data_map.get("countPrice"));
		return_data_map.put("memo", memo);
		return_data_map.put("orderMan", OrderMan.member);
		Order order = orderService.persist(return_data_map);
		
		//生成消息
		//创建消息通知
		Information information = new Information();
		information.setMessage("您有来自"+order.getPatientMember().getName()+"的订单。服务项目："+order.getProject().getName()+"，康复时间："+order.getWorkDayItem().getStartTime()+"-"+ order.getWorkDayItem().getEndTime()+"，用户："+order.getMember().getName()+"。");
		information.setInformationId(order.getId());
		information.setHeadline("用户下单通知");
		information.setInformationType(InformationType.order);
		information.setState(StateType.unread);
		information.setDoctor(doctor);
		information.setMember(member); 
		information.setIsDeleted(false);
		information.setDisposeState(DisposeState.unDispose);
		information.setUserType(UserType.doctor);
		informationService.save(information);
		
		
		Map<String,Object> data_map = new HashMap<String, Object>();
		data_map.put("orderId", order.getId());
		data_map.put("sn", order.getSn());
		data_map.put("projectName", project.getName());
		data_map.put("doctorName", doctor.getName());
		data_map.put("patientName", patientMember.getName());
		data_map.put("amountPayable", order.getAmountPayable());
		data_map.put("reserveWork", workDay.getWorkDayDate());
		data_map.put("reserveWorktTime",startTime);
		data_map.put("area",mechanism.getArea());
		data_map.put("serverAddress",mechanism.getAddress());
		data_map.put("balance",order.getMember().getBalance());
		data_map.put("workDayId",workDay.getId());
		data_map.put("paymentMethods",paymentMethodService.findAll());
		
		if(StringUtils.isEmpty(member.getPaymentPassword())){
			return "redirect:/web/order/toConfirmOrder.jhtml?porjectId="+projectId;
		}
		
		model.addAttribute("order", order);
		model.addAttribute("balance", order.getMember().getBalance());
		model.addAttribute("amount", order.getAmount());
		model.addAttribute("workDayDate", workDay.getWorkDayDate());
		model.addAttribute("startTime", startTime);
		model.addAttribute("address", order.getMechanism().getAddress());
		model.addAttribute("sn", order.getSn());
		
		return "/web/serve/paymentDetails";
	}
	
	
	/**
	 * 患者订单列表跳转
	 */
	@RequestMapping(value = "/toMemberOrderList", method = RequestMethod.GET)
	public String list(ModelMap model) {
		Member member = memberService.getCurrent();
		if(member==null){
			return "redirect:/web/login/toLogin.jhtml";
		}
		model.addAttribute("member", member);
		return "/web/order/memberOrderList";
	}
	
	/**
	 * 患者订单数据
	 */
	@RequestMapping(value = "/memberOrderList", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String memberOrderList(ModelMap model,String flag,Integer pageNumber) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		
		Member member = memberService.getCurrent();
		Map<String,Object> data_map = new HashMap<String, Object>();
		
		String paymentStatus = "";//待付款
		String serveState = "";//待康复
		String orderStatus = "";//已完成
		String evaluate = "";//待评价
		//flag="1：全部；2：待付款；3、待康复；4、待评价；5、已完成"
		if(flag.equals("2")){
			paymentStatus = "unpaid";
		}else if(flag.equals("3")){
			serveState="await";
		}else if(flag.equals("4")){
			evaluate="not";
		}else if(flag.equals("5")){
			orderStatus="completed";
		}
		
		
		data_map.put("paymentStatus", paymentStatus);//待付款
		data_map.put("serveState", serveState);//待康复
		data_map.put("orderStatus", orderStatus);//已完成
		data_map.put("evaluate", evaluate);//待评价
		data_map.put("member",member);//用户
		data_map.put("pageNumber",pageNumber);//页码
		
		map = orderService.findList(data_map);
		return JsonUtils.toJson(map);
	}
	
	
	/**
	 * 判断当前时间点后有无医生机构时间
	 * @param start
	 * @param workDay
	 * @return
	 */
	public static String isEndData(String startTime,List<WorkDayItem> workDayItems){
		List<Date> lists = new ArrayList<Date>();
		for (WorkDayItem workDayItem : workDayItems){//将所有已占用的时间加到lists里面
			lists.add(DateUtil.getStringtoDate(workDayItem.getStartTime(), "HH:mm"));
			lists.add(DateUtil.getStringtoDate(workDayItem.getEndTime(), "HH:mm"));
		}
		
		for(Date date : lists){
			if(DateUtil.compare_date(DateUtil.getDatetoString("HH:mm", date), startTime)==0){
				startTime = DateUtil.getMinute(DateUtil.getStringtoDate(startTime, "HH:mm"), 1, "HH:mm");
			}
		}
		
		lists.add(DateUtil.getStringtoDate(startTime, "HH:mm"));//将开始时间插入lists集合并转成Date类型
		ComparatorDate c = new ComparatorDate();
		Collections.sort(lists, c);  
		
		String workDayItemStartTime = "";
		for (int i = 0; i < lists.size(); i++) {
			if (DateUtil.compare_date(startTime, DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)))==0) {
				if(i+2<=lists.size()){
					return "false";
				}else{
					return "true";
				}
			}
			System.out.println(DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)));
		}
		
		
		return workDayItemStartTime;
	}
	
	/**
	 * 返回下一个预约时间开始时间
	 * @param start
	 * @param workDay
	 * @return
	 */
	public static String getEndData(String startTime,List<WorkDayItem> workDayItems){
		List<Date> lists = new ArrayList<Date>();
		for (WorkDayItem workDayItem : workDayItems){//将所有已占用的时间加到lists里面
			lists.add(DateUtil.getStringtoDate(workDayItem.getStartTime(), "HH:mm"));
			lists.add(DateUtil.getStringtoDate(workDayItem.getEndTime(), "HH:mm"));
		}
		
		for(Date date : lists){
			if(DateUtil.compare_date(DateUtil.getDatetoString("HH:mm", date), startTime)==0){
				startTime = DateUtil.getMinute(DateUtil.getStringtoDate(startTime, "HH:mm"), 1, "HH:mm");
			}
		}
		
		lists.add(DateUtil.getStringtoDate(startTime, "HH:mm"));//将开始时间插入lists集合并转成Date类型
		ComparatorDate c = new ComparatorDate();
		Collections.sort(lists, c);  
		
		String workDayItemStartTime = "";
		for (int i = 0; i < lists.size(); i++) {
			if (DateUtil.compare_date(startTime, DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)))==0) {
				workDayItemStartTime = DateUtil.getDatetoString("HH:mm", (Date)lists.get(i+1));
				break;
			}
			System.out.println(DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)));
		}
		
		
		return workDayItemStartTime;
	}
	
	/**
	 * 取消订单
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/cancelled", method = RequestMethod.GET)
	public String cancelled(ModelMap model,String paymentPassword,Long orderId) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
			
			Member member = memberService.getCurrent();
			if(member==null){
				return "redirect:/web/login/toLogin.jhtml";
			}
			
			if (member.getPaymentPassword()==null||member.getPaymentPassword().equals("")) {
				map.put("status", "400");
				map.put("message", "订单取消失败,请设置支付密码");
				map.put("data", new Object());
				return JsonUtils.toJson(map);
			}
			
			if (!member.getPaymentPassword().equals(DigestUtils.md5Hex(paymentPassword))) {
				map.put("status", "400");
				map.put("message", "密码输入有误,订单取消失败");
				map.put("data", new Object());
				return JsonUtils.toJson(map);
			}
			
			Order order = orderService.find(orderId);
			
			System.out.println(order.getId()+"号订单即将取消，订单编号为:"+order.getSn());
			if (!order.getOrderStatus().equals(OrderStatus.completed)) {
				if (order.getPaymentStatus().equals(PaymentStatus.paid)||order.getPaymentStatus().equals(PaymentStatus.partialPayment)) {
					if (order.getServeState().equals(ServeState.await)) {
						String orderStartTime = DateUtil.getDatetoString("yyyy-MM-dd", order.getWorkDayItem().getWorkDay().getWorkDayDate())+" "+order.getWorkDayItem().getStartTime();;
						
						/*Date orderStart = DateUtil.getStringtoDate(orderStartTime, "yyyy-MM-dd HH:mm");
						Date date = new Date();
						long difference = (orderStart.getTime() -  date.getTime()) ;
						Long min = difference/(1000*60);
						Setting setting = SettingUtils.get();
						BigDecimal free  =  new BigDecimal(0);
						if (min<setting.getDifference()) {
							 free = order.getAmountPaid().multiply(new BigDecimal(setting.getDeductionRate()));
						}
						BigDecimal amount = order.getAmountPaid().subtract(free);*/
						
						
						
						Refunds refunds = new Refunds();//退款单
						
						refunds.setAmount(order.getAmountPaid());;
						refunds.setOrder(order);
						PaymentMethod paymentMethod = order.getPaymentMethod();
						refunds.setPaymentMethod(paymentMethod != null ? paymentMethod.getName() : null);
//						refunds.setMethod(Method.online);//此处设置方式  是为了设置机构的退款方式
						refunds.setSn(snService.generate(Sn.Type.refunds));
						refunds.setBank("");
						refunds.setAccount(member.getMobile());
						refunds.setPayee(member.getName());
						refunds.setOperator(member.getName());
						refunds.setStatus(Status.complete);
						refunds.setMethod(Method.deposit);
						refunds.setIsDeleted(false);
						Long workDayItemId = order.getWorkDayItem().getId();
						refunds.setMemo("用户取消订单，自动退款");
						refundsService.save(refunds);
						
						if (refunds.getMethod() == Refunds.Method.deposit) {
							
							member.setBalance(member.getBalance().add(refunds.getAmount()));
							memberService.update(member);
							
							Deposit deposit = new Deposit();
							deposit.setType(Deposit.Type.adminRefunds);
							deposit.setCredit(refunds.getAmount());
							deposit.setDebit(new BigDecimal(0));
							
							deposit.setBalance(member.getBalance());
							deposit.setOperator(member.getName());
							deposit.setMember(member);
							deposit.setOrder(order);
					/*		String memo = "";
							if (free.compareTo(new BigDecimal(0))>0) {
								NumberFormat num = NumberFormat.getPercentInstance(); 
								num.setMaximumIntegerDigits(3); 
								num.setMaximumFractionDigits(2); 
								double csdn = setting.getDeductionRate();
								System.out.println(num.format(csdn));
								memo = "，订单取消小于"+setting.getDifference()+"分钟，扣除"+num.format(csdn)+",大约扣除"+free+"元";
							} 
							
							deposit.setMemo("订单取消，退款"+memo);*/
							deposit.setMemo("订单取消，退款"+refunds.getAmount());
							depositService.save(deposit);
							
						}
						order.setAmountPaid(order.getAmountPaid().subtract(refunds.getAmount()));
						if (order.getAmountPaid().compareTo(new BigDecimal(0)) == 0) {
							order.setPaymentStatus(PaymentStatus.refunded);
						} else if (order.getAmountPaid().compareTo(new BigDecimal(0)) > 0) {
							order.setPaymentStatus(PaymentStatus.partialRefunds);
						}
						
						//取消订单消息通知
						Information information = new Information();
						String text = "您来自患者"+order.getPatientMember().getName()+"的订单已取消。服务项目："+order.getProject().getName()+"，康复时间："+order.getWorkDayItem().getStartTime()+"-"+order.getWorkDayItem().getEndTime()+"，用户："+order.getMember().getName()+"，订单编号:"+order.getSn()+"。";
						information.setMessage(text);
						information.setInformationId(order.getId());
						information.setHeadline("用户付款通知");
						information.setInformationType(InformationType.order);
						information.setState(StateType.unread);
						information.setDoctor(order.getProject().getDoctor());
						information.setMember(order.getMember()); 
						information.setIsDeleted(false);
						information.setDisposeState(DisposeState.unDispose);
						information.setUserType(UserType.doctor);
						informationService.save(information);
						
						order.setRefundedDate(new Date());
						order.setExpire(null);
						
						order.setOrderStatus(OrderStatus.cancelled);
						order.setShippingStatus(ShippingStatus.returned);
						order.setWorkDayItem(null);
						order.setIsDeleted(true);
						
						orderService.update(order);
						
						OrderLog orderLog = new OrderLog();
						orderLog.setType(Type.refunds);
						orderLog.setOperator(member.getName());
						orderLog.setOrder(order);
						orderLog.setContent("用户取消订单");
						orderLog.setIsDeleted(false);
						orderLogService.save(orderLog);
						
						System.out.println("取消订单(已支付,未服务),并把预定时间空出来");
						workDayItemService.delete(workDayItemId);
						
						map.put("status", "200");
						map.put("message", "订单已经取消");
						map.put("data", new Object());
						return JsonUtils.toJson(map);
						
					}
					
				}else{
					//取消订单消息通知
					Information information = new Information();
					String text = "您来自患者"+order.getPatientMember().getName()+"的订单已取消。服务项目："+order.getProject().getName()+"，康复时间："+order.getWorkDayItem().getStartTime()+"-"+order.getWorkDayItem().getEndTime()+"，用户："+order.getMember().getName()+"，订单编号:"+order.getSn()+"。";
					information.setMessage(text);
					information.setInformationId(order.getId());
					information.setHeadline("用户付款通知");
					information.setInformationType(InformationType.order);
					information.setState(StateType.unread);
					information.setDoctor(order.getProject().getDoctor());
					information.setMember(order.getMember()); 
					information.setIsDeleted(false);
					information.setDisposeState(DisposeState.unDispose);
					information.setUserType(UserType.doctor);
					informationService.save(information);
					
					order.setOrderStatus(OrderStatus.cancelled);
					order.setShippingStatus(ShippingStatus.returned);
					order.setExpire(null);
					Long workDayItemId = order.getWorkDayItem().getId();
					order.setWorkDayItem(null);
					order.setIsDeleted(true);
					orderService.update(order);
					
					System.out.println("取消订单(未支付),并把预定时间空出来");
					workDayItemService.delete(workDayItemId);
					
					OrderLog orderLog = new OrderLog();
					orderLog.setType(Type.refunds);
					orderLog.setOperator(member.getName());
					orderLog.setOrder(order);
					orderLog.setContent("用户取消订单");
					orderLog.setIsDeleted(false);
					orderLogService.save(orderLog);
					
				}
				
			}else{
				map.put("status", "400");
				map.put("message", "订单已完成,不可取消");
				map.put("data", new Object());
				return JsonUtils.toJson(map);
			}
			

			
			map.put("status", "200");
			map.put("message", "取消成功");
			map.put("data", new Object());
			return JsonUtils.toJson(map);
		
	}
	
	/**
	 * 支付
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String payment(String paymentPassword,Long paymentMethodId,String sn) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
			
			Member member = memberService.getCurrent();
			
//		    String startTime = json.getString("startTime");//预约开始时间
//		    Integer count = json.getInt("count");//预约次数
//		    WorkDay workDay = workDayService.find(json.getLong("workDayId"));//工作日对象
//		    Project project = projectService.find(json.getLong("projectId"));//项目
		    

			
			if (member.getPaymentPassword()==null) {
				map.put("status", "400");
				map.put("message", "支付密码输入有误,请重新输入");
				map.put("data", "{}");
				return JsonUtils.toJson(map);
			}
			if((!member.getPaymentPassword().equals(DigestUtils.md5Hex(paymentPassword)))){
				map.put("status", "400");
				map.put("message", "支付密码输入有误,请重新输入");
				map.put("data", "{}");
				return JsonUtils.toJson(map);
			}
			
			
			PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
			Order order = orderService.findBySn(sn);
			String payType = "platform";
			map = orderService.payment(order ,paymentMethod,payType );
			String statue = map.get("status").toString();
			if(statue.equals("200")){
				Information information = new Information();
				String text = "您有来自患者"+order.getPatientMember().getName()+"的订单。服务项目："+order.getProject().getName()+"，康复时间："+order.getWorkDayItem().getStartTime()+"-"+order.getWorkDayItem().getEndTime()+"，用户："+order.getMember().getName()+"。";
				information.setMessage(text);
				information.setInformationId(order.getId());
				information.setHeadline("用户付款通知");
				information.setInformationType(InformationType.order);
				information.setState(StateType.unread);
				information.setDoctor(order.getProject().getDoctor());
				information.setMember(order.getMember()); 
				information.setIsDeleted(false);
				information.setDisposeState(DisposeState.unDispose);
				information.setUserType(UserType.doctor);
				informationService.save(information);
			    Doctor doctor = order.getDoctor();
			    if (doctor.getDevice_tokens()!=null) {
			    	Boolean fals = PushUtil.getFals(doctor.getDevice_tokens());
				    String appkey = fals?PushUtil.android_Ys_AppKey:PushUtil.ios_Ys_AppKey;//appkey
					String secret = fals?PushUtil.android_Ys_App_Master_Secret:PushUtil.ios_Ys_App_Master_Secret;//secret
					String device_tokens = doctor.getDevice_tokens(); //device_tokens 设备唯一识别号
					String ticker = "订单通知";// 通知栏提示文字
					String title = "订单支付通知";// 必填 通知标题
					
					String after_open = "go_activity";//必填 值为"go_app：打开应用", "go_url: 跳转到URL", "go_activity: 打开特定的activity", "go_custom: 用户自定义内容。"
					String url = "";    // 可选 当"after_open"为"go_url"时，必填。 通知栏点击后跳转的URL，要求以http或者https开头  
					String activity = "OrderDetailsActivity";     // 可选 当"after_open"为"go_activity"时，必填。 通知栏点击后打开的Activity 
					String custom = "{}";// 可选 display_type=message, 或者display_type=notification且"after_open"为"go_custom"时，该字段必填。用户自定义内容, 可以为字符串或者JSON格式
					String extra =  "{\"orderId\":"+order.getId()+"}";//用户自定义 extra
				   
				    Map<String ,Object> send_map = new HashMap<String, Object>();
				    //android 所需参数
				    send_map.put("appkey", appkey);
				    send_map.put("secret", secret);
				    send_map.put("device_tokens", device_tokens);
				    send_map.put("ticker", ticker);
				    send_map.put("title", title);
				    send_map.put("text", text);
				    send_map.put("after_open", after_open);
				    send_map.put("url", url);
				    send_map.put("activity", activity);
				    send_map.put("custom", custom);
				    send_map.put("extra", extra);
				    
				    //ios 所需参数
				    send_map.put("alias_type","");//可选当type=customizedcast时，必填，alias的类型, alias_type可由开发者自定义,开发者在SDK中 调用setAlias(alias, alias_type)时所设置的alias_type
				    send_map.put("alias", "");// 可选 当type=customizedcast时, 开发者填写自己的alias。  要求不超过50个alias,多个alias以英文逗号间隔。 在SDK中调用setAlias(alias, alias_type)时所设置的alias
				    send_map.put("file_id", ""); // 可选 当type=filecast时，file内容为多条device_token,  device_token以回车符分隔当type=customizedcast时，file内容为多条alias， alias以回车符分隔，注意同一个文件内的alias所对应的alias_type必须和接口参数alias_type一致。注意，使用文件播前需要先调用文件上传接口获取file_id,  具体请参照"2.4文件上传接口"
				    send_map.put("alert", text);  // 必填 iOS10 新增带title，subtile的alert格式如下"alert":{//  "title":"title","subtitle":"subtitle", "body":"body",}                   
				    send_map.put("badge", "1"); // 可选        
				    send_map.put("sound", "default"); // 可选        
				    send_map.put("content_available", ""); // 可选        
				    send_map.put("category", ""); // 可选        
				    send_map.put("max_send_num", "");// 可选 发送限速，每秒发送的最大条数。开发者发送的消息如果有请求自己服务器的资源，可以考虑此参数。
				    send_map.put("apns_collapse_id", "");//可选，iOS10开始生效。
				    send_map.put("description", ""); // 可选 发送消息描述，建议填写。
				    send_map.put("iOSType", "order"); //1.project 2.docotr 3.order
				    send_map.put("iOSValue", order.getId());//iOSType 填写时  可填写
				    
				    try {
				    	Map<String, Object> map_data = fals?PushUtil.androidSend(send_map):PushUtil.iosSend(send_map);//这一步 判断android 或者 ios 推送
						System.out.println("status:"+map_data.get("status")+",message:"+map_data.get("message"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				}
				
			}
			return JsonUtils.toJson(map);
	}
	
	/**
	 * 重定向到订单列表
	 */
	@RequestMapping(value = "/toOrderList", method = RequestMethod.GET)
	public String toOrderList(ModelMap model,Long projectId) {
		return "redirect:/web/order/toMemberOrderList.jhtml";
	}
	
	
}





