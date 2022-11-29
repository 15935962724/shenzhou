/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.Member;

/**
 * Service - 评估报告
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface AssessReportService extends BaseService<AssessReport, Long> {

	/**
	 * 获取患者最新报告数量
	 * 
	 * @param member
	 *          
	 * @return 
	 */
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