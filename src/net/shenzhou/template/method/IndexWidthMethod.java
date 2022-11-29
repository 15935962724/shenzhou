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
@Component("indexWidthMethod")
public class IndexWidthMethod implements TemplateMethodModel {
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null&& arguments.get(1) != null) {
			return getIndexWidth(arguments.get(0),arguments.get(1));
		}
		return null;
	}

	/**
	 * retur 宽度
	 */
	private String getIndexWidth(Object workDayItemStartTime,Object workDayItemStartEnd) {
		int width = DateUtil.getMinute(String.valueOf(workDayItemStartTime), String.valueOf(workDayItemStartEnd), "HH:mm")/5*22;
		return width+"";
	}

}