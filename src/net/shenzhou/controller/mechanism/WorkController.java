/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDayItem;
import net.shenzhou.entity.WorkDayItem.WorkDayType;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.RechargeLogService;
import net.shenzhou.service.WorkDayService;
import net.shenzhou.util.SortUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 工作统计
 * @date 2018-5-21 10:36:02
 * @author wsr
 *
 */
@Controller("mechanismWorkController")
@RequestMapping("/mechanism/work")
public class WorkController extends BaseController {

	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "workDayServiceImpl")
	private WorkDayService workDayService;
	@Resource(name = "rechargeLogServiceImpl")
	private RechargeLogService rechargeLogService;
	
	
	
	/**
	 * 
	 * @param pageable
	 * @param name  排序字段
	 * @param fals  升序or降序   true降序   false 升序
	 * @param date   日期
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, @RequestParam (defaultValue = "price")String name  , @RequestParam (defaultValue = "false")Boolean fals , Date date, ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String, Object> query_map = new HashMap<String, Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrphone", null);
		query_map.put("pageable", pageable);
		query_map.put("audit", DoctorMechanismRelation.Audit.succeed); 
		query_map.put("isEnabled",true);
		query_map.put("createDate",new Date());
		Date date1 = date == null ? new Date():date;
		List<DoctorMechanismRelation> doctorMechanismRelations  = doctorMechanismRelationService.downloadList(query_map);
		 
		List<Map<String,Object>> doctor_list = new ArrayList<Map<String,Object>>();
		for (DoctorMechanismRelation doctorMechanismRelation : doctorMechanismRelations) {
			Map<String,Object> doctor_map = new HashMap<String, Object>();
			doctor_map.put("doctorId", doctorMechanismRelation.getDoctor().getId());
			System.out.println(doctorMechanismRelation.getDoctor().getId());
			doctor_map.put("doctorName", doctorMechanismRelation.getDoctor().getName());
			WorkDay workDay = workDayService.getDoctorWorkDays(doctorMechanismRelation.getDoctor(), date1);
			Double quantity = 0.0;//总共课节数
			BigDecimal price = new BigDecimal(0);//收费
			
			Double cancelledQuantity = 0.0;//已退课节
			BigDecimal cancelledPrice = new BigDecimal(0);//已退费用
			List<Map<String,Object>> workDayItem_list = new ArrayList<Map<String,Object>>();
			if (workDay!=null) {
				for (WorkDayItem workDayItem : workDay.getWorkDayItems()) {
					
					if (workDayItem.getWorkDayType().equals(WorkDayType.reserve)) {
						Order order = workDayItem.getOrder();
						Map<String,Object> workDayItem_map = new HashMap<String, Object>();
						if (order != null) {
							
//							if (order.getPaymentStatus().equals(PaymentStatus.partialPayment)
//									||order.getPaymentStatus().equals(PaymentStatus.paid)
//									||order.getPaymentStatus().equals(PaymentStatus.partialRefunds)) {
//								quantity = quantity + order.getQuantity();
////								price = price.add(order.getAmountPaid());
//								price = price.add(order.getAmount());
//								
//								
//							}
//							if (order.getPaymentStatus().equals(PaymentStatus.refunded)) {
//								cancelledQuantity = cancelledQuantity + order.getQuantity();
//								cancelledPrice =cancelledPrice.add(order.getCountAmount());
//							}
							
							if (order.getOrderStatus().equals(OrderStatus.completed)
								||order.getOrderStatus().equals(OrderStatus.record)) {
								quantity = quantity + order.getQuantity();
//								price = price.add(order.getAmountPaid());
								price = price.add(order.getAmount());
							}
							if ((order.getOrderStatus().equals(OrderStatus.completed)
								||order.getOrderStatus().equals(OrderStatus.record))
								&&
								(order.getPaymentStatus().equals(PaymentStatus.partialPayment)
								||order.getPaymentStatus().equals(PaymentStatus.refunded))) {
								cancelledQuantity = cancelledQuantity + order.getQuantity();
								cancelledPrice =cancelledPrice.add(order.getCountAmount());
							}
						}
						workDayItem_map.put("startTime",workDayItem.getStartTime());
						workDayItem_map.put("endTime",workDayItem.getEndTime());
						workDayItem_map.put("time",order.getOrderItems().get(0).getTime());
						workDayItem_map.put("price",order.getOrderItems().get(0).getPrice());
						workDayItem_map.put("projectName",order.getOrderItems().get(0).getName());
						workDayItem_map.put("patientName",order.getPatientMember().getName());
						workDayItem_map.put("refundsPrice",order.getCountAmount());
						SimpleDateFormat format =  new SimpleDateFormat("HH:mm");  
					    Date dates = null;
						try {
							dates = format.parse(workDayItem.getStartTime());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
						workDayItem_map.put("times",dates.getTime());
						workDayItem_list.add(workDayItem_map);
					}
				}
			}
			
			List<Map<String,Object>> sort_workDayItem_list = new ArrayList<Map<String,Object>>();
			
			sort_workDayItem_list = SortUtil.getOrderByDistance(workDayItem_list, "times", false);
			
			doctor_map.put("workDayItem_list", sort_workDayItem_list);//预约时段
			doctor_map.put("quantity", quantity);//课节
			doctor_map.put("price", price);//收费
			doctor_map.put("cancelledQuantity", cancelledQuantity);//已退课节
			doctor_map.put("cancelledPrice", cancelledPrice);//已退费用
			doctor_list.add(doctor_map);
			
		}
		
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		
		//排序
		data_list = SortUtil.getOrderByDistance(doctor_list, name, fals);
		
		List<Map<String,Object>> doctor_data_list = new ArrayList<Map<String,Object>>();
		
		//以下为分页信息
		Integer pagecount = (data_list.size()+pageable.getPageSize()-1)/pageable.getPageSize();//总页数
		if(pageable.getPageNumber()>=pagecount){
			doctor_data_list = data_list.subList((pageable.getPageNumber()-1)*pageable.getPageNumber(), data_list.size());
		}else{
			doctor_data_list = data_list.subList((pageable.getPageNumber()-1)*pageable.getPageNumber(), pageable.getPageSize()*pageable.getPageNumber());
		}
		
		model.addAttribute("doctor_list", doctor_data_list);
		model.addAttribute("mechanism", mechanism);
		Page page = new Page(doctor_list,doctor_list.size(),pageable);
		
		model.addAttribute("page", page);
		model.addAttribute("pageable", pageable);
		model.addAttribute("date", date);
		model.addAttribute("fals", fals);
		model.addAttribute("name", name);
		return "/mechanism/work/list";
	}

	/**
	 * 机构内工作数据统计
	 * @param startTime
	 * @param endTime
	 * @param nameOrMobile
	 * @return
	 */
	@RequestMapping(value = "/workStatistics", method = RequestMethod.POST)
	public @ResponseBody
	Map<String,Object> update(String startTime,String endTime,String nameOrMobile) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> return_map = new HashMap<String, Object>();
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("startTime", startTime);
		query_map.put("endTime", endTime);
		query_map.put("nameOrMobile", nameOrMobile);
		query_map.put("mechanism", mechanism);
		Long aboutCount = orderService.getAboutCount(query_map);//预约次数
		Double serverCount = orderService.getServerCount(query_map);//服务课节
		Double refundCount = orderService.getRefundedCount(query_map);//退款课节
		BigDecimal refundPrice = orderService.getRefundedPrice(query_map);//退款金额
		BigDecimal sumRecharge = rechargeLogService.getSumRecharge(query_map);//充值金额
		BigDecimal consumptionPrice = orderService.getConsumptionPrice(query_map);//消费金额
		
		return_map.put("serverCount", serverCount);
		return_map.put("aboutCount", aboutCount);
		return_map.put("refundCount", refundCount);
		return_map.put("refundPrice", refundPrice);
		return_map.put("sumRecharge", sumRecharge);
		return_map.put("consumptionPrice", consumptionPrice);
		
		return_map.put("status", "200");
		return_map.put("data", "老王");
		return return_map;
		
	}
	
	
	
	
	
}

