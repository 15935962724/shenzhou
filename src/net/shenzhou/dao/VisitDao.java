/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.Date;
import java.util.List;

import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Visit;

/**
 * Dao - 回访
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface VisitDao extends BaseDao<Visit, Long> {
	

	/**
	 * 根据机构获取患者回访列表
	 * 
	 */
	List<Visit> getByMechanism(Mechanism mechanism,Member member,Date startDate,Date endDate);
	
}