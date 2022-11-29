/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.PlatformRechargeLog;

/**
 * @date 2018-5-18 10:50:50
 * 平台充值记录
 * @author wsr
 *
 */
public interface PlatformRechargeLogDao extends BaseDao<PlatformRechargeLog, Long> {

	
	/**
	 * 根据商户订单号和交易号查询该交易是否处理(充值异步通知时使用)
	 * @param outTradeNo
	 * @param tradeNo
	 * @return
	 */
	PlatformRechargeLog getPlatformRechargeLog(String outTradeNo ,String tradeNo);
	
	
	/**
	 * 平台端查询平台充值记录
	 * @param query_map
	 * @return
	 */
	Page<PlatformRechargeLog>findPage(Map<String,Object> query_map);
	
	
	
	
	
}