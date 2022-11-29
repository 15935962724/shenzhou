/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.entity.RecoveryRecord;

/**
 * Service - 康护记录
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface RecoveryRecordService extends BaseService<RecoveryRecord, Long> {
	
	
	/**
	 * 根据时间段查询康护记录
	 * @param query_map
	 * @return
	 */
	List<RecoveryRecord> findRecoveryRecordList(Map<String,Object> query_map);
	
}