/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.RecoveryPlan;

/**
 * Dao - 康护计划
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface RecoveryPlanDao extends BaseDao<RecoveryPlan, Long> {

	/**
	 * 根据评估报告获取最近的康复计划
	 * 
	 * @return 康护计划
	 */
	RecoveryPlan findRecentlyRecoveryPlan(AssessReport assessReport);
	
	/**
	 * 查询康护计划
	 * @param query_map
	 * @return
	 */
	List<RecoveryPlan> findRecoveryPlanList(Map<String,Object> query_map);
	
	
	
}