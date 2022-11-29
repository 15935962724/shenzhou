/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.entity.RecoveryPlan;
import net.shenzhou.entity.RecoveryRecord;

/**
 * Dao - 康护记录
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface RecoveryRecordDao extends BaseDao<RecoveryRecord, Long> {

	

	
	/**
	 * 根据时间段查询康护记录
	 * @param query_map
	 * @return
	 */
	List<RecoveryRecord> findRecoveryRecordList(Map<String,Object> query_map);
	
}