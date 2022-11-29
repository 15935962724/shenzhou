/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.entity.DoctorPointLog;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface DoctorPointLogService extends BaseService<DoctorPointLog, Long> {

	
	/**
	 * 医生以获得积分列表
	 * @param query_map
	 * @return
	 */
	List<DoctorPointLog> findDoctorAcquire(Map<String,Object> query_map);
	
}