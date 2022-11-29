/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.MemberPointLog;

/**
 * Service - 用户积分日志
 * @date 2018-3-22 16:08:57
 * @author wsr
 */
public interface MemberPointLogService extends BaseService<MemberPointLog, Long> {

	
	/**
	 * 用户积分列表
	 * @param query_map
	 * @return
	 */
	Page<MemberPointLog> findPage(Map<String,Object> query_map);
	
	
	
	/**
	 * 用户已获取积分列表
	 * @param query_map
	 * @return
	 */
	List<MemberPointLog> findMemberAcquire(Map<String,Object> query_map);
	
	
}