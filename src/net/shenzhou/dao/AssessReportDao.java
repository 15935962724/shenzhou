/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;

/**
 * Dao - 评估报告
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface AssessReportDao extends BaseDao<AssessReport, Long> {

	/**
	 * 根据时间和治疗医师筛选评估报告
	 * @param map
	 * @return
	 */
	 List findList(Map<String, Object> map);
	
	
	 
	 Long assessReportCount(Member member); 
	
	 
	/**
	 * 获取患者最新报告
	 * 
	 * @param member
	 *          
	 * @return 
	 */
	AssessReport getRecentlyAssessReport(Member member);
	 
}