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
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 计算色块宽度
 * @date 2017-8-24 14:48:52
 * @author wsr
 *
 */
@Component("widthMethod")
public class WidthMethod implements TemplateMethodModel {
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null&& arguments.get(1) != null&& arguments.get(2) != null&& arguments.get(3) != null) {
			return getWidth(arguments.get(0),arguments.get(1),arguments.get(2),arguments.get(3));
		}
		return null;
	}

	/**
	 * return 百分比
	 */
	private String getWidth(Object workDateStartTime,Object workDateEndTime,Object workDayItemStartTime,Object workDayItemStartEnd) {
		
		Double j = DateUtil.getJ(String.valueOf(workDateStartTime), String.valueOf(workDateEndTime));
		
		int m = DateUtil.getMinute(String.valueOf(workDayItemStartTime), String.valueOf(workDayItemStartEnd), "HH:mm");
		
		return (m/5*j)+"%";
	}

}