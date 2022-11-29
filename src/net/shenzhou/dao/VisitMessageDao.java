/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.VisitMessage;

/**
 * 回访信息dao
 * @date 2017-8-16 17:15:21
 * @author wsr
 *
 */
public interface VisitMessageDao extends BaseDao<VisitMessage, Long> {

	
	/**
	 * 机构端查找回访信息(用户/患者)分页
	 * @param query_map
	 * @return
	 */
	Page<VisitMessage> findPage(Map<String,Object> query_map);
	
	
	/**
	 * 机构端查找
	 * @param query_map
	 * @return
	 */
	Page<VisitMessage> getDoctorPage(Map<String,Object> query_map);
	
	
	/**
	 * 平台端查找
	 * @param query_map
	 * @return
	 */
	Page<VisitMessage> getPage(Map<String,Object> query_map);
	
	
}