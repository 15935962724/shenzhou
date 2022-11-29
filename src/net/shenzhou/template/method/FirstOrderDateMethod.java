/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.template.method;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 获取
 * @author Administrator
 *
 */
@Component("firstOrderDateMethod")
public class FirstOrderDateMethod implements TemplateMethodModel {
	
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null) {
			return getFirsOrderDate(arguments.get(0));
		}
		return null;
	}

	/**
	 * return 缩略字符
	 */
	private String getFirsOrderDate(Object patientId) {
		if (patientId != null) {
			Member patientMember = memberService.find(Long.valueOf(String.valueOf(patientId)));
			Order order = orderService.getPatientMemberOldOrder(patientMember);
			if (order==null) {
				return "-";
			}
		   return DateUtil.getDatetoString("yyyy年MM月dd日", order.getCreateDate());
		} else {
			return "-";
		}
	}

}