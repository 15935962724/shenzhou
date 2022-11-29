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
 * 项目统计
 * @date 2017-9-14 18:17:48
 * @author wsr
 *
 */
@Component("projectAboutMethod")
public class ProjectAboutMethod implements TemplateMethodModel {
	
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null && arguments.get(1) != null) {
			return getProjectAbout(arguments.get(0),arguments.get(1),arguments.get(2));
		}
		return 0;
	}

	/**
	 * return 缩略字符
	 */
	private BigDecimal getProjectAbout(Object date,Object serverProjectCategoryId ,Object nameOrmoible) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		if (date != null || serverProjectCategoryId==null) {
			ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(Long.valueOf(String.valueOf(serverProjectCategoryId)));
			Map <String,Object> query_map = new HashMap<String, Object>();
			query_map.put("mechanism", mechanism);
			query_map.put("nameOrmoible", nameOrmoible);
			query_map.put("date", DateUtil.getStringtoDate(date.toString(), "yyyy-MM-dd"));
			List<Order> orders = orderService.getMonthAbout(query_map);
			BigDecimal count_price = new BigDecimal(0);
			try {
				for (Order order : orders) {
					if (serverProjectCategory.equals(order.getProject().getServerProjectCategory())) {
						count_price = order.getPrice().add(count_price);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("项目统计出错:"+e.getMessage());
				return count_price;
			}
		   return count_price;
		} else {
			return new BigDecimal(0);
		}
	}

}