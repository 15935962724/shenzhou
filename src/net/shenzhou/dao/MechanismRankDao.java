/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;

import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.MechanismRank;

/**
 * 机构级别
 * 2017-6-24 17:28:49
 * @author wsr
 *
 */
public interface MechanismRankDao extends BaseDao<MechanismRank, Long> {

	/**
	 * 查找顶级等级
	 * @param count
	 *            数量
	 * @return 顶级等级
	 */
	List<MechanismRank> findRoots(Integer count);
	
	
	
	List<MechanismRank> findChildren(MechanismRank mechanismRank, Integer count);
	
}