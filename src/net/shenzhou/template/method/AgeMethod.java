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
 * 获取年龄
 * 2017-8-2 11:29:42
 * @author wsr
 *
 */
@Component("ageMethod")
public class AgeMethod implements TemplateMethodModel {
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null) {
			return getAge(arguments.get(0));
		}
		return null;
	}

	/**
	 * return 缩略字符
	 */
	public static Integer getAge(Object birth) {
		if (birth != null) {
			 
			Integer age = 0;
			try {
				age = DateUtil.getAge(DateUtil.getStringtoDate(birth.toString(), "yyyy-MM-dd"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("年龄转换出错:"+e.getMessage());
				return age;
			}
		   return age;
		} else {
			return 0;
		}
	}

}