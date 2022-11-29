/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.PlatformRechargeLog;

/**
 * 平台充值日志
 * 2018-5-18 10:59:55
 * @author wsr
 *
 */
public interface PlatformRechargeLogService extends BaseService<PlatformRechargeLog, Long> {

	
	 PlatformRechargeLog getPlatformRechargeLog(String outTradeNo,
			String tradeNo) ;
	
	 
	 Page<PlatformRechargeLog> findPage(Map<String, Object> query_map);
	 
	
}