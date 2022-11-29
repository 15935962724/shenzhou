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
import net.shenzhou.entity.User;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.UserService;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 获取患者总消费金额
 * @date 2017-9-16 14:47:06
 * @author wsr
 */
@Component("countMoneyMethod")
public class CountMoneyMethod implements TemplateMethodModel {
	
	@Resource(name = "orderServiceImpl")
    private OrderService orderService;
	@Resource(name = "memberServiceImpl")
    private MemberService memberService;
	@Resource(name = "userServiceImpl")
    private UserService userService;
	@Resource(name = "doctorServiceImpl")
    private DoctorService doctorService;
	
	
	
	@SuppressWarnings("rawtypes")
	public BigDecimal exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null) {
			return getCountMoney(arguments.get(0));
		}
		return null;
	}

	/**
	 * return 缩略字符
	 */
	private BigDecimal getCountMoney(Object patientMemberId) {
		BigDecimal count_money = new BigDecimal(0);
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
		    	count_money = count_money.add(order.getAmountPaid());
			}
		    return count_money;
		} else {
			return count_money;
		}
	}

}