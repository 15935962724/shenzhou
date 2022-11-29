/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.PatientMechanism;

/**
 * Service - 患者所在机构的状态
 * @date 2018-3-23 16:22:44
 * @author wsr
 */
public interface PatientMechanismService extends BaseService<PatientMechanism, Long> {


	/**
	 * 机构端患者列表
	 * @param query_map
	 * @return
	 */
	Page<PatientMechanism> getPatientMechanisms(
			Map<String, Object> query_map);
	
	/**
	 * 患者状态导出
	 * @param query_map
	 * @return
	 */
	List<PatientMechanism> downloadPatientHealthType(
			Map<String, Object> query_map);
	
	/**
	 * 今日新增患者
	 * @param query_map
	 * @return
	 */
	Integer daysPatientCount(Map<String, Object> query_map);
	
	
	
}