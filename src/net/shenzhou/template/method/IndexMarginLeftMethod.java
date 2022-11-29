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
 * 获取距离左侧距离
 * @date 2017-8-24 14:35:53
 * @author wsr
 *
 */
@Component("indexMarginLeftMethod")
public class IndexMarginLeftMethod implements TemplateMethodModel {
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null&& arguments.get(1) != null) {
			return getIndexMarginLeft(arguments.get(0),arguments.get(1));
		}
		return null;
	}

	/**
	 * return 百分比
	 */
	private String getIndexMarginLeft(Object workDayItemStartTime,Object workDateStartTime) {
		//margin-left:（(课程开始时间转分钟-上班开始时间转分钟）/ 5 * 22）+1
		 workDateStartTime = workDateStartTime.toString().split(":")[0]+":00";
		int marginLeft =((DateUtil.getMinute(String.valueOf(workDateStartTime), String.valueOf(workDayItemStartTime), "HH:mm")/5)*22) ;
		return marginLeft+"";
	}

}