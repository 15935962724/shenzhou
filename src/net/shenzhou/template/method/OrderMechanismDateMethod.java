/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.template.method;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.shenzhou.entity.Bill;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.OrderService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 机构登录首页验证订单
 * @date 2017-10-23 15:16:14
 * @author wsr
 *
 */
@Component("orderMechanismDateMethod")
public class OrderMechanismDateMethod implements TemplateMethodModel {
	
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null&& arguments.get(1) != null&& arguments.get(2) != null) {
			return getOrderMechanismDate(arguments.get(0),arguments.get(1),arguments.get(2),arguments.get(3));
		}
		return null;
	}

	/**
	 * 验证
	 * @param patientId
	 * @return
	 */
	private List<Map<String,Object>> getOrderMechanismDate(Object orderId,Object mechanismId,Object date ,Object doctorId) {
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		
		if (orderId != null &&mechanismId!=null&&date!=null) {
//			Order order = orderService.find(Long.valueOf(String.valueOf(orderId)));
			Mechanism mechanism = mechanismService.find(Long.valueOf(String.valueOf(mechanismId)));
			Date date1 = DateUtil.getStringtoDate(date.toString(), "yyyy-MM-dd");
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("orderId", orderId);
			query_map.put("mechanism", mechanism);
			query_map.put("date1", date1);
			query_map.put("doctorId", doctorId);
			List<Object[]> objects = orderService.getWorkDayDate(query_map);
		    for (Object[] objects2 : objects) {
		    	Map<String,Object> data_map = new HashMap<String, Object>();
//		    	JSONArray jsonArray = JSONArray.fromObject(objects2);
//		    	data_map.put("workDayDate", jsonArray.get(0));
//		    	data_map.put("startTime", jsonArray.get(1));
//		    	data_map.put("endTime", jsonArray.get(2));
//		    	data_map.put("patientMemberName", jsonArray.get(3));
		    	
		    	data_map.put("workDayDate", objects2[0]);
		    	data_map.put("startTime", objects2[1]);
		    	data_map.put("endTime", objects2[2]);
		    	data_map.put("patientMemberName", objects2[3]);
		    	
		    	data_list.add(data_map);
			}
		} 
		return data_list;
	}
	
	
	
	
//	/**
//	 * 验证
//	 * @param patientId
//	 * @return
//	 */
//	private Boolean getOrderMechanismDate(Object orderId,Object mechanismId,Object date) {
//		if (orderId != null &&mechanismId!=null&&date!=null) {
////			Order order = orderService.find(Long.valueOf(String.valueOf(orderId)));
//			Mechanism mechanism = mechanismService.find(Long.valueOf(String.valueOf(mechanismId)));
//			Date date1 = DateUtil.getStringtoDate(date.toString(), "yyyy-MM-dd");
//			Map<String,Object> query_map = new HashMap<String, Object>();
//			query_map.put("orderId", orderId);
//			query_map.put("mechanism", mechanism);
//			query_map.put("date1", date1);
//			List<Object[]> objects = orderService.getWorkDayDate(query_map);
//			
//			if (objects.size()>0) {
////				JSONArray jsonArray = JSONArray.fromObject(objects.get(0));
//				System.out.println(String.valueOf(objects.get(0)));
////                if (DateUtil.getStringtoDate(String.valueOf(objects.get(0)), "yyyy-MM-dd").equals(date1)) {
////                	return true;
////				}else{
////					return false;
////				}		
//                
//                return true;
//                
//			}else{
//				return false;
//			}
////			if (order.getMechanism().equals(mechanism)&&order.getWorkDayItem()!=null) {
////				if (order.getWorkDayItem().getWorkDay().getWorkDayDate().equals(date1)) {
////					return true;
////				}
////			}
//		} 
//		return false;
//	}

}