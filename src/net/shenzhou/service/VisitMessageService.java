/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.VisitMessage;

/**
 * Service 回访信息
 * @date 2017-8-16 17:18:46
 * @author wsr
 */
public interface VisitMessageService extends BaseService<VisitMessage, Long> {

	
	
	 Page<VisitMessage> findPage(Map<String, Object> query_map);
	
	 
	 Page<VisitMessage> getDoctorPage(Map<String,Object> query_map);
	
	 
	 Page<VisitMessage> getPage(Map<String, Object> query_map);
	 
}