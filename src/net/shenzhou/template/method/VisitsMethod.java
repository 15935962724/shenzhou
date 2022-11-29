/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.template.method;

import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.entity.Member;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 诊次数
 * 2017-8-1 18:14:59
 * @author wsr
 */
@Component("visitsMethod")
public class VisitsMethod implements TemplateMethodModel {
	@Resource(name = "orderServiceImpl")
    private OrderService orderService;
	@Resource(name = "memberServiceImpl")
    private MemberService memberService;
	
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null) {
			return count(Long.valueOf(arguments.get(0).toString()));
		}
		return null;
	}

	/**
	 * return 诊疗次数(就诊次数)
	 */
	private Long count(Long patientMemberId) {
		if (patientMemberId != null) {
			Member patientMember = memberService.find(patientMemberId);
		   return orderService.count(patientMember);
		} else {
			return null;
		}
	}

}