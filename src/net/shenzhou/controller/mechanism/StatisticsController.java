/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.ExcelView;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Coupon;
import net.shenzhou.entity.CouponCode;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Order.AccountType;
import net.shenzhou.entity.OrderItem;
import net.shenzhou.entity.User;
import net.shenzhou.service.CouponCodeService;
import net.shenzhou.service.CouponService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.DateUtil;

import org.jsoup.helper.DataUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * 统计
 * 2017-7-27 17:27:06
 * @author wsr
 *
 */
@Controller("mechanismStatisticsController")
@RequestMapping("/mechanism/statistics")
public class StatisticsController extends BaseController {

	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "couponServiceImpl")
	private CouponService couponService;
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	
	/**
	 * 收费日报
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/charge", method = RequestMethod.GET)
	public String build(ModelMap model,Pageable pageable,String projectName,String doctorName,String memberName,String patientName,
			String phone,Date startDate,Date endDate,AccountType accountType,
			 HttpServletRequest request) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("projectName", projectName);
		
		
		if (startDate!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			startDate = calendar.getTime();
		}
		if (endDate!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			endDate = calendar.getTime();
		}
		query_map.put("startDate", startDate);
		query_map.put("endDate", endDate);
		query_map.put("doctorName", doctorName);
		query_map.put("memberName", memberName);
		query_map.put("patientName", patientName);
		query_map.put("phone", phone);
		query_map.put("accountType", accountType);
		query_map.put("pageable", pageable);
		Page<Order> page =  orderService.charges(query_map);
		System.out.println("总共"+page.getTotal()+"条记录");
		model.addAttribute("page", page);
		model.addAttribute("projectName", projectName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("doctorName", doctorName);
		model.addAttribute("memberName", memberName);
		model.addAttribute("patientName", patientName);
		model.addAttribute("phone", phone);
		model.addAttribute("accountTypes", AccountType.values());
		model.addAttribute("accountType", accountType);
		return "mechanism/statistics/charge";
	}
	
	
	/**
	 * 
	 * @param id
	 * @param count
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/downloadCharge", method = RequestMethod.GET)
	public ModelAndView downloadCharge(ModelMap model,Pageable pageable,String projectName,String doctorName,String memberName,String patientName,String phone,Date startDate,Date endDate,
			 HttpServletRequest request) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("projectName", projectName);
		Calendar calendar = Calendar.getInstance();
		
		if (startDate!=null) {
			calendar.setTime(startDate);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			startDate = calendar.getTime();
		}
		if (endDate!=null) {
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			endDate = calendar.getTime();
		}
		query_map.put("startDate", startDate);
		query_map.put("endDate", endDate);
		query_map.put("doctorName", doctorName);
		query_map.put("memberName", memberName);
		query_map.put("patientName", patientName);
		query_map.put("phone", phone);
		query_map.put("pageable", pageable);
		List<Order> orders =  orderService.downloadCharge(query_map);
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		for (Order order : orders) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("patientName",order.getPatientMember().getName());//患者
			data_map.put("memberName", order.getMember().getName());//监护人
			data_map.put("memberPhone", order.getMember().getMobile());//监护人电话
			data_map.put("projectName", order.getProject().getName());//项目名称
			data_map.put("doctorName", order.getDoctor().getName());//服务医师
			data_map.put("doctorPhone", order.getDoctor().getMobile());//医师电话
			data_map.put("serverStartTime",DateUtil.getDatetoString("yyyy-MM-dd", order.getWorkDayItem().getWorkDay().getWorkDayDate())+" "+order.getWorkDayItem().getWorkDay().getStartTime());//服务开始时间
			data_map.put("serverEndTime",DateUtil.getDatetoString("yyyy-MM-dd", order.getWorkDayItem().getWorkDay().getWorkDayDate())+" "+order.getWorkDayItem().getWorkDay().getEndTime());//服务结束时间
			data_map.put("projectPrice",order.getOrderItems().get(0).getPrice());//单节费用(元)
			data_map.put("quantity", order.getQuantity());//课节数(节)
			data_map.put("price", order.getPrice());//应收金额(元)
			data_map.put("couponDiscount", order.getCouponDiscount());//减免金额(元)
			data_map.put("amountPaid", order.getAmountPaid());//实收金额(元)
			if(order.getAccountType().equals("platform")){
				data_map.put("accountType","平台账户");//缴费账户
			}else if(order.getAccountType().equals("mechanism")){
				data_map.put("accountType","机构账户");//缴费账户
			}
			
			data_map.put("paidDate", order.getPaidDate());//缴费时间
			data.add(data_map);
		}
		
		String filename = "收费统计" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"患者","监护人","监护人电话","项目名称","服务医师","医师电话","服务开始时间","服务结束时间","单节费用(元)","课节数(节)","应收金额(元)","减免金额(元)","实收金额(元)","缴费账户","缴费时间"};//title
		String[] contents = new String []{"patientName","memberName","memberPhone","projectName","doctorName","doctorPhone","serverStartTime","serverEndTime","projectPrice","quantity","price","couponDiscount","amountPaid","accountType","paidDate"};//content
		
		String[] memos = new String [4];//memo
		memos[0] = "记录数" + ": " + data.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data, memos), model);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data, memos), model);
	}
	
	
	/**
	 * 收费月报
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model,String nameOrmoible,Date createDate, Date endDate,Pageable pageable, HttpServletRequest request) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		
		List<Date> dates = null;
		if (createDate==null||endDate==null) {
			 dates = DateUtil.getAllTheDateOftheMonth(new Date());
		}else{
			dates =DateUtil.findDates(createDate,endDate);
		}
		
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		
		for (Date date : dates) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			Double count_course = 0.0;//课节
			BigDecimal count_amount_payable = new BigDecimal(0);//应收金额
			BigDecimal count_couponDiscount = new BigDecimal(0);;//减免金额
			BigDecimal count_price = new BigDecimal(0);;//实收(实付)金额
//			int count_prtient = 0;//患者人数
//			int count_order = 0;//下单人数
			
			query_map.put("date", date);
			query_map.put("nameOrmoible", nameOrmoible);
			List<Order> orders = orderService.getMonthReport(query_map);
			List<Member> members = new ArrayList<Member>();
			List<Member> patientMembers = new ArrayList<Member>();
			for (Order order : orders) {
				for (OrderItem orderItem : order.getOrderItems()) {
					count_course = count_course+orderItem.getQuantity();
				}
			
				count_amount_payable = order.getPrice().add(count_amount_payable);
				count_couponDiscount = order.getCouponDiscount().add(count_couponDiscount);
				count_price = order.getAmountPaid().add(count_price);
				if (!members.contains(order.getMember())) {
					members.add(order.getMember());
				}
				if (!patientMembers.contains(order.getPatientMember())) {
					patientMembers.add(order.getPatientMember());
				}
			}
			data_map.put("date", date);
			data_map.put("count_course", count_course);
			data_map.put("count_amount_payable", count_amount_payable);
			data_map.put("count_couponDiscount", count_couponDiscount);
			data_map.put("count_price", count_price);
			data_map.put("count_prtient", patientMembers.size());
			data_map.put("count_order", members.size());
			data_list.add(data_map);
		}
		

		
		System.out.println("总共"+data_list.size()+"条记录");
		
		Integer last = pageable.getPageSize()*pageable.getPageNumber()>data_list.size()?data_list.size():pageable.getPageSize()*pageable.getPageNumber();
		
		Page<Map<String,Object>> page = new Page<Map<String,Object>>(data_list.subList((pageable.getPageNumber()-1)*pageable.getPageSize(), last), data_list.size(), pageable);
		model.addAttribute("page",page);
		model.addAttribute("dates", dates);
//		model.addAttribute("data_list", data_list);
		model.addAttribute("createDate", createDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("nameOrmoible", nameOrmoible);
		
		return "mechanism/statistics/list";
	}
	
	
	/**
	 * 导出收费月报
	 * @param model
	 * @param nameOrmoible
	 * @param createDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/downloadList", method = RequestMethod.GET)
	public ModelAndView downloadList(ModelMap model,String nameOrmoible,Date createDate, HttpServletRequest request) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		
		List<Date> dates = DateUtil.getAllTheDateOftheMonth(createDate==null?new Date():createDate);
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		
		for (Date date : dates) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			Double count_course = 0.0;//课节
			BigDecimal count_amount_payable = new BigDecimal(0);//应收金额
			BigDecimal count_couponDiscount = new BigDecimal(0);;//减免金额
			BigDecimal count_price = new BigDecimal(0);;//实收(实付)金额
//			int count_prtient = 0;//患者人数
//			int count_order = 0;//下单人数
			
			query_map.put("date", date);
			query_map.put("nameOrmoible", nameOrmoible);
			List<Order> orders = orderService.getMonthReport(query_map);
			List<Member> members = new ArrayList<Member>();
			List<Member> patientMembers = new ArrayList<Member>();
			for (Order order : orders) {
				for (OrderItem orderItem : order.getOrderItems()) {
					count_course = count_course+orderItem.getQuantity();
				}
			
				count_amount_payable = order.getPrice().add(count_amount_payable);
				count_couponDiscount = order.getCouponDiscount().add(count_couponDiscount);
				count_price = order.getAmountPaid().add(count_price);
				if (!members.contains(order.getMember())) {
					members.add(order.getMember());
				}
				if (!patientMembers.contains(order.getPatientMember())) {
					patientMembers.add(order.getPatientMember());
				}
			}
			data_map.put("date", DateUtil.getDatetoString("yyyy-MM-dd",date));
			data_map.put("count_course", count_course);
			data_map.put("count_amount_payable", count_amount_payable);
			data_map.put("count_couponDiscount", count_couponDiscount);
			data_map.put("count_price", count_price);
			data_map.put("count_prtient", patientMembers.size());
			data_map.put("count_order", members.size());
			data_map.put("each_price", members.size()==0?0:count_price.divide(new BigDecimal(members.size()),2,BigDecimal.ROUND_UP));
			data_list.add(data_map);
		}
		
		String filename = "收费月报" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"日期","课节数(节)","应收金额(元)","减免金额(元)","实收金额(元)","患者数量(人)","下单人数量(人)","客单价(元/人)"};//title
		String[] contents = new String []{"date","count_course","count_amount_payable","count_couponDiscount","count_price","count_prtient","count_order","each_price"};//content
		
		String[] memos = new String [4];//memo
		memos[0] = "记录数" + ": " + data_list.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
	}
	

}