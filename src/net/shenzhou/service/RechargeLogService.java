/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.RechargeLog;

/**
 * 充值日志
 * 2017-8-1 11:09:30
 * @author wsr
 *
 */
public interface RechargeLogService extends BaseService<RechargeLog, Long> {

	/**
	 * 机构端查看充值日志
	 * @param query_map
	 * @return
	 */
	Page<RechargeLog> getMechanismRechargeLogList(
				Map<String, Object> query_map);
	
	
	
	/**
	 * 查询充值总额
	 * @param query_map
	 * @return
	 */
	BigDecimal sumRecharge(Map<String, Object> query_map);
	
	
	/**
	 * 导出充值记录
	 * @param query_map
	 * @return
	 */
	List<RechargeLog> downloadList(Map<String, Object> query_map);
	
	
	/**
	 * 平台端查询机构充值记录
	 * @param query_map
	 * @return
	 */
	Page<RechargeLog> getRechargeLogs(Map<String,Object> query_map);
	
	
	/**
	 * 工作统计(充值金额)
	 * @param query_map
	 * @return
	 */
	BigDecimal getSumRecharge(Map<String, Object> query_map);
	
}