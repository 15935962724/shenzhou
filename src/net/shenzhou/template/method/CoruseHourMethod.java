/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.template.method;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.OrderItem;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.User;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 服务课时
 * @date 2017-9-16 14:34:39
 * @author wsr
 *
 */
@Component("coruseHourMethod")
public class CoruseHourMethod implements TemplateMethodModel {
	

	
	@Resource(name = "orderServiceImpl")
    private OrderService orderService;
	@Resource(name = "memberServiceImpl")
    private MemberService memberService;
	@Resource(name = "userServiceImpl")
    private UserService userService;
	@Resource(name = "doctorServiceImpl")
    private DoctorService doctorService;
	
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null) {
			return getlastDate(arguments.get(0));
		}
		return null;
	}

	/**
	 * return 缩略字符
	 */
	private Double getlastDate(Object patientMemberId) {
		Double count_coruse_Hour = 0.0;
		if (patientMemberId != null) {
//			User user = userService.getCurrent();
//			Mechanism mechanism = user.getMechanism();
			Doctor doctorC = doctorService.getCurrent();
			Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
			
			Member patientMember = memberService.find(Long.valueOf(String.valueOf(patientMemberId)));
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("mechanism", mechanism);
			query_map.put("patientMember", patientMember);
			List<Order> orders = orderService.getLastDateCoruseHourCountMoney(query_map);
		    for (Order order : orders) {
				for (OrderItem orderItem : order.getOrderItems()) {
					count_coruse_Hour = count_coruse_Hour + orderItem.getQuantity();
				}
			}
		    return count_coruse_Hour;
		} else {
			return count_coruse_Hour;
		}
	}


	
}