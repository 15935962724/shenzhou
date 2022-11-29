/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

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
import net.shenzhou.entity.Balance;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Information;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Order.AccountType;
import net.shenzhou.entity.OrderLog;
import net.shenzhou.entity.PaymentMethod;
import net.shenzhou.entity.Refunds;
import net.shenzhou.entity.Sn;
import net.shenzhou.entity.Information.ClassifyType;
import net.shenzhou.entity.Information.DisposeState;
import net.shenzhou.entity.Information.InformationType;
import net.shenzhou.entity.Information.StateType;
import net.shenzhou.entity.Information.UserType;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.entity.Order.ServeState;
import net.shenzhou.entity.Order.ShippingStatus;
import net.shenzhou.entity.OrderLog.Type;
import net.shenzhou.entity.Refunds.Method;
import net.shenzhou.entity.Refunds.Status;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDayItem;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.BalanceService;
import net.shenzhou.service.BankCardService;
import net.shenzhou.service.BeforehandLoginService;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.CartService;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.DoctorCategoryService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorPointLogService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.EvaluateService;
import net.shenzhou.service.InformationService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderLogService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.OrganizationService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.RSAService;
import net.shenzhou.service.RefundsService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.SnService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.service.WithdrawDepositService;
import net.shenzhou.service.WorkDayItemService;
import net.shenzhou.service.WorkDayService;
import net.shenzhou.util.ComparatorDate;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 确认订单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appConfirmOrderController")
@RequestMapping("/app/confirmOrder")
public class ConfirmOrderController extends BaseController {
	

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
	@Resource(name = "refundsServiceImpl")
	private RefundsService refundsService;
	@Resource(name = "snServiceImpl")
	private SnService snService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "informationServiceImpl")
	private InformationService informationService;
	@Resource(name = "orderLogServiceImpl")
	private OrderLogService orderLogService;
	@Resource(name = "balanceServiceImpl")
	private BalanceService balanceService;
	
	
	
	/**
	 * 确认订单列表页
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/orderList", method = RequestMethod.GET)
	public void orderList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			WorkDay workDay = workDayService.find(json.getLong("workDayId"));
			Integer pageNumber = json.getInt("pageNumber");//页码
		    Integer pageSize = Config.pageSize;
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
			
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Mechanism mechanism = doctorMechanismRelation.getMechanism();
			List<Order> orders = orderService.getConfirmOrder(doctor, mechanism, workDay);
			
			String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			
			if(orders.size()==0){
				map.put("status", "400");
				map.put("message", "暂无订单数据");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			
			//总页数
			Integer pagecount = (orders.size()+pageSize-1)/pageSize;
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			
			if (pageNumber>pagecount) {
				message = "无更多数据";
				status = "500";
			}
			
			List<Order> pag_order = new ArrayList<Order>();
			if (orders.size()>0){
				if(pageNumber>=pagecount){
					pag_order = orders.subList((pagenumber-1)*pageSize, orders.size());
				}else{
					pag_order = orders.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			
			List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
			for(Order order : orders){
				Map<String,Object> data_map = new HashMap<String, Object>();
				data_map.put("orderId", order.getId());
				data_map.put("projectName", order.getProject().getName());
				data_map.put("time", order.getWorkDayItem().getStartTime()+"-"+order.getWorkDayItem().getEndTime());
				data_map.put("patientLogo", order.getPatientMember().getLogo());
				data_map.put("patientName", order.getPatientMember().getName());
				data_map.put("patientSex", order.getPatientMember().getGender());
				data_map.put("patientAge", DateUtil.getAge(order.getPatientMember().getBirth()));
				data_map.put("memberName", order.getMember().getName());
				data_map.put("memberPhone", order.getMember().getMobile());
				data_list.add(data_map);
			}
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("data", data_list);
			
			map.put("status", status);
			map.put("message", message);
			map.put("data", JsonUtils.toJson(data_map));
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
	 * 确认订单页面
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/confirmOrder", method = RequestMethod.GET)
	public void confirmOrder(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			Order order = orderService.find(json.getLong("orderId"));
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
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("orderId", order.getId());
			data_map.put("projectName", order.getProject().getName());
			data_map.put("time", order.getWorkDayItem().getStartTime()+"-"+order.getWorkDayItem().getEndTime());
			data_map.put("patientLogo", order.getPatientMember().getLogo());
			data_map.put("patientName", order.getPatientMember().getName());
			data_map.put("patientSex", order.getPatientMember().getGender());
			data_map.put("patientAge", DateUtil.getAge(order.getPatientMember().getBirth()));
			data_map.put("memberName", order.getMember().getName());
			data_map.put("memberPhone", order.getMember().getMobile());
			data_map.put("createDate", order.getCreateDate());
			data_map.put("difference", DateUtil.getMinute(order.getWorkDayItem().getStartTime(), order.getWorkDayItem().getEndTime(),"HH:mm"));
			
			map.put("status", "200");
			map.put("message", "加载成功");
			map.put("data", JsonUtils.toJson(data_map));
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
	 * 预约变更
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/timeReserveChange", method = RequestMethod.GET)
	public void timeReserveChange(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			Order order = orderService.find(json.getLong("orderId"));
			WorkDay workDay = workDayService.find(json.getLong("workDayId"));
			String startTime = json.getString("startTime");
			String endTime = json.getString("endTime");
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
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Mechanism mechanism = doctorMechanismRelation.getMechanism();
			String start_time = order.getWorkDayItem().getStartTime();
			String end_time = order.getWorkDayItem().getEndTime();
			
			/**判断选择的开始时间是否在当前时间之前(只有workDay等于今天才判断这种情况)**/
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String now_time = format.format(new Date());
			String now_date = df.format(new Date());
			
			if(workDay.getWorkDayDate().equals(now_date)){
				if(DateUtil.compare_date(startTime, now_time)==1){
					map.put("status", "400");
					map.put("message", "选择的开始时间在当前时间之前,请重新选择开始时间");
					map.put("data", "{}");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}
			}
			
			//先获取当前机构时间,判断所选择时间段是否在机构时间内
			List<WorkDayItem> mechanism_workDayItem = workDayItemService.getMechanismWorkDayItemByMechanism(doctor, workDay, mechanism);
			Boolean key = false;
			for(WorkDayItem workDayItem : mechanism_workDayItem){
				if(DateUtil.compare_date(startTime, workDayItem.getStartTime())==-1&&DateUtil.compare_date(endTime, workDayItem.getEndTime())==1){
					key=true;
				}
				if(DateUtil.compare_date(startTime, workDayItem.getStartTime())==0&&DateUtil.compare_date(endTime, workDayItem.getEndTime())==1){
					key=true;
				}
				if(DateUtil.compare_date(startTime, workDayItem.getStartTime())==-1&&DateUtil.compare_date(endTime, workDayItem.getEndTime())==0){
					key=true;
				}
				if(DateUtil.compare_date(startTime, workDayItem.getStartTime())==0&&DateUtil.compare_date(endTime, workDayItem.getEndTime())==0){
					key=true;
				}
			}
			
			if(key==false){
				map.put("status", "400");
				map.put("message", "所选时间没有排班,请重新选择");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			
			List<WorkDayItem> order_workDayItems = workDayItemService.getWorkDayItemByMechanism(doctor, workDay, mechanism);
			List<WorkDayItem> order_workDayItem = new ArrayList<WorkDayItem>();
			for(WorkDayItem workDayItem : order_workDayItems){
				if(!workDayItem.getStartTime().equals(start_time)&&!workDayItem.getEndTime().equals(end_time)){
					order_workDayItem.add(workDayItem);
				}
			}
			
			/**先判断开始时间是否合法**/
			for(WorkDayItem workDayItem : order_workDayItem){
				if(startTime.equals(workDayItem.getStartTime())){
					map.put("status", "400");
					map.put("message", "该开始时间已有订单,请重新选择时间");
					map.put("data", "{}");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}
				if(DateUtil.compare_date(startTime, workDayItem.getStartTime())==-1&&DateUtil.compare_date(startTime, workDayItem.getStartTime())==1){
					map.put("status", "400");
					map.put("message", "该开始时间已有订单,请重新选择时间");
					map.put("data", "{}");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}
				if(DateUtil.compare_date(startTime, workDayItem.getStartTime())==-1&&DateUtil.compare_date(startTime, workDayItem.getStartTime())==0){
					map.put("status", "400");
					map.put("message", "该开始时间已有订单,请重新选择时间");
					map.put("data", "{}");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}
			}
			
			if(order_workDayItem.size()>0){
				String next_endTime = getLastData(startTime,order_workDayItem);
				if(!next_endTime.equals(startTime)){
					if(DateUtil.compare_date(endTime, next_endTime)==-1){
						map.put("status", "400");
						map.put("message","结束时间选择不合法,请重新选择结束时间");
						map.put("data", "{}");
				        printWriter.write(JsonUtils.toString(map));
						return;
					}
				}
			}
			
			WorkDayItem workDayItem = order.getWorkDayItem();
			workDayItem.setStartTime(startTime);
			workDayItem.setEndTime(endTime);
			workDayItem.setWorkDay(workDay);
			workDayItemService.update(workDayItem);
			
			order.setOrderStatus(OrderStatus.confirmed);
			order.setServeState(ServeState.await);
			
			orderService.update(order);
			
			Information information_member = new Information();
		    information_member.setMessage("医生"+order.getDoctor().getName()+"已确认接受您对"+order.getProject().getName()+"服务的预约，并将预约时间修改为"+order.getWorkDayItem().getWorkDay().getWorkDayDate()+" "+order.getWorkDayItem().getStartTime()+"~"+order.getWorkDayItem().getEndTime()+"，请提前做好康护准备。");
		    information_member.setInformationId(order.getId());
		    information_member.setHeadline("预约通知");
		    information_member.setInformationType(InformationType.order);
		    information_member.setClassifyType(ClassifyType.business);
		    information_member.setState(StateType.unread);
		    information_member.setDoctor(order.getProject().getDoctor());
		    information_member.setMember(order.getMember()); 
		    information_member.setIsDeleted(false);
		    information_member.setDisposeState(DisposeState.unDispose);
		    information_member.setUserType(UserType.member);
		    information_member.setMechanism(order.getMechanism());
			informationService.save(information_member);
			
			map.put("status", "200");
			map.put("message", "加载成功");
			map.put("data", "{}");
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
	 * 确认预约(不修改时间的接口)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/reserveChange", method = RequestMethod.GET)
	public void reserveChange(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			Order order = orderService.find(json.getLong("orderId"));
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
			
			order.setOrderStatus(OrderStatus.confirmed);
			order.setServeState(ServeState.await);
			
			orderService.update(order);
			
			Information information_member = new Information();
		    information_member.setMessage("医生"+order.getDoctor().getName()+"已确认接受您对"+order.getProject().getName()+"服务的预约，请提前做好康护准备。");
		    information_member.setInformationId(order.getId());
		    information_member.setHeadline("预约通知");
		    information_member.setInformationType(InformationType.order);
		    information_member.setClassifyType(ClassifyType.business);
		    information_member.setState(StateType.unread);
		    information_member.setDoctor(order.getProject().getDoctor());
		    information_member.setMember(order.getMember()); 
		    information_member.setIsDeleted(false);
		    information_member.setDisposeState(DisposeState.unDispose);
		    information_member.setUserType(UserType.member);
		    information_member.setMechanism(order.getMechanism());
			informationService.save(information_member);
			
			map.put("status", "200");
			map.put("message", "加载成功");
			map.put("data", "{}");
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
	 * 取消订单
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.GET)
	public void cancelOrder(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
	        String cause = json.getString("cause");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
            Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue); 
            if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			
			Order order = orderService.find(json.getLong("orderId"));
			Mechanism mechanism = order.getProject().getMechanism();
			Member member = order.getMember();
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
						
						refunds.setAmount(order.getAmountPaid());
						refunds.setOrder(order);
						PaymentMethod paymentMethod = order.getPaymentMethod();
						refunds.setPaymentMethod(paymentMethod != null ? paymentMethod.getName() : null);
						refunds.setMethod(Method.deposit);//此处设置方式  是为了设置机构的退款方式
						refunds.setSn(snService.generate(Sn.Type.refunds));
						refunds.setBank("");
						refunds.setAccount(doctor.getMobile());
						refunds.setPayee(doctor.getName());
						refunds.setOperator(doctor.getName());
						refunds.setStatus(Status.complete);
						refunds.setMethod(Method.deposit);
						refunds.setIsDeleted(false);
						refunds.setMechanism(mechanism);
						Long workDayItemId = order.getWorkDayItem().getId();
						refunds.setMemo("医生取消订单，自动退款");
						refundsService.save(refunds);
						
						if (refunds.getMethod() == Refunds.Method.deposit) {
							Deposit deposit = new Deposit();
							if (order.getAccountType().equals(AccountType.mechanism)) {
								Balance balance = member.getBalance(mechanism);
								balance.setBalance(balance.getBalance().add(refunds.getAmount()));
								balanceService.update(balance);
								deposit.setBalance(balance.getBalance());
								deposit.setMechanism(mechanism);
							}
							if (order.getAccountType().equals(AccountType.platform)){
								member.setBalance(member.getBalance().add(refunds.getAmount()));
								memberService.update(member);
								deposit.setBalance(member.getBalance());
							}
							
							deposit.setType(Deposit.Type.adminRefunds);
							deposit.setCredit(refunds.getAmount());
							deposit.setDebit(new BigDecimal(0));
							deposit.setOperator(doctor.getName());
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
							deposit.setMechanism(mechanism);
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
						String text = "医生"+order.getDoctor().getName()+"已取消"+order.getProject().getName();
						information.setMessage(text);
						information.setInformationId(order.getId());
						information.setHeadline("订单取消通知");
						information.setInformationType(InformationType.order);
						information.setClassifyType(ClassifyType.business);
						information.setState(StateType.unread);
						information.setDoctor(order.getProject().getDoctor());
						information.setMember(order.getMember()); 
						information.setIsDeleted(false);
						information.setDisposeState(DisposeState.unDispose);
						information.setUserType(UserType.doctor);
						information.setMechanism(order.getMechanism());
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
						orderLog.setOperator(doctor.getName());
						orderLog.setOrder(order);
						orderLog.setContent("医生取消订单");
						orderLog.setIsDeleted(false);
						orderLogService.save(orderLog);
						
						System.out.println("取消订单(已支付,未服务),并把预定时间空出来");
						workDayItemService.delete(workDayItemId);
						
						map.put("status", "200");
						map.put("message", "订单已经取消");
						map.put("data", new Object());
						printWriter.write(JSONObject.fromObject(map).toString()) ;
						return;
						
					}
					
				}else if(order.getPaymentStatus().equals(PaymentStatus.unpaid)){
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
					
				}else if(order.getPaymentStatus().equals(PaymentStatus.refunded)||order.getPaymentStatus().equals(PaymentStatus.partialRefunds)){
					map.put("status", "200");
					map.put("message", "订单已退款或部分退款,不可重复取消");
					map.put("data", new Object());
					printWriter.write(JSONObject.fromObject(map).toString()) ;
					return;
				}
			}else{
				map.put("status", "200");
				map.put("message", "订单已完成,不可取消");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			

			
			map.put("status", "200");
			map.put("message", "取消成功");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
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
	 * 返回下一个时间段的开始时间
	 * @param start
	 * @param workDay
	 * @return
	 */
	public static String getLastData(String startTime,List<WorkDayItem> workDayItems){
		List<Date> lists = new ArrayList<Date>();
		for (WorkDayItem workDayItem : workDayItems){//将所有已占用的时间加到lists里面
			lists.add(DateUtil.getStringtoDate(workDayItem.getStartTime(), "HH:mm"));
			lists.add(DateUtil.getStringtoDate(workDayItem.getEndTime(), "HH:mm"));
		}
		String workDayItemStartTime = "";
		for(WorkDayItem workDayItem : workDayItems){
			if(workDayItem.getStartTime().equals(startTime)||workDayItem.getEndTime().equals(startTime)){
				DateUtil.getMinute(DateUtil.getStringtoDate(startTime, "HH:mm"), 1,"HH:mm");
				lists.add(DateUtil.getStringtoDate(DateUtil.getMinute(DateUtil.getStringtoDate(startTime, "HH:mm"), 1,"HH:mm"), "HH:mm"));//将开始时间插入lists集合并转成Date类型
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
		
		
		
		lists.add(DateUtil.getStringtoDate(startTime, "HH:mm"));//将开始时间插入lists集合并转成Date类型
		ComparatorDate c = new ComparatorDate();
		Collections.sort(lists, c);  
		
		System.out.print("长度为"+lists.size());
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