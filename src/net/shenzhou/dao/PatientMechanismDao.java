/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PatientMechanism;

/**
 * Dao - 患者在机构的状态
 * @date 2018-3-23 16:08:47
 * @author wsr
 */
public interface PatientMechanismDao extends BaseDao<PatientMechanism, Long> {
	
	
	/**
	 * 患者信息 (机构下的患者列表)
	 * @param query_map
	 * @return
	 */
	Page<PatientMechanism> getPatientMechanisms(Map<String, Object> query_map);
	
	
	/**
	 * 导出患者状态
	 * @param query_map
	 * @return
	 */
	List<PatientMechanism> downloadPatientHealthType(Map<String, Object> query_map);
	
	/**
	 * 今日新增患者
	 * @param query_map
	 * @return
	 */
	Integer daysPatientCount(Map<String, Object> query_map);
	
	
	
	
}