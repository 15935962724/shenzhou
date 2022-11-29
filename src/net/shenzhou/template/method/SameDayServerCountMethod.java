/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.template.method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.OrderService;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 当日服务人次
 * @date 2018-1-16 17:08:58
 * @author wsr
 *
 */
@Component("sameDayServerCountMethod")
public class SameDayServerCountMethod implements TemplateMethodModel {
	
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null&& arguments.get(1) != null) {
			return getSameDayServerCount(arguments.get(0),arguments.get(1));
		}
		return 0;
	}

	/**
	 * 当日服务人次
	 * @param doctorId
	 * @param date
	 * @return
	 */
	private Integer getSameDayServerCount(Object doctorId,Object date) {
		Integer count = 0;
		if (doctorId != null &&date!=null) {
			Doctor doctorC = doctorService.getCurrent();
			Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("doctor", doctorService.find(Long.valueOf(doctorId.toString())));
			query_map.put("mechanism", mechanism);
			query_map.put("create_date", date);
			count = orderService.getSameDayServerCount(query_map);
			return count;
		} 
		return count;
	}

}