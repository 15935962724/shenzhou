/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.Refunds;

/**
 * Service - 退款单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface RefundsService extends BaseService<Refunds, Long> {

	/**
	 * 机构端查询退款单(查询医师发起的退款)
	 * @param query_map
	 * @return
	 */
	 Page<Refunds> findPage(Map<String, Object> query_map);
	 
	 /**
	  * 退款管理(导出)
	  * @param query_map
	  * @return
	  */
	 List<Refunds> downloadList(Map<String, Object> query_map);
	/**
	 * app端查询订单的退款情况
	 * @date 2017-9-19 11:28:05
	 * @author wsr
	 * @param query_map
	 * @return
	 */
	 Map<String, Object> findMap(Map<String, Object> query_map);
	 
}