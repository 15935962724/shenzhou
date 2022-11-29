/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;

import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.MechanismRank;

/**
 * Service - 级别
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface MechanismRankService extends BaseService<MechanismRank, Long> {

	
	
	/**
	 * 查找顶级级别
	 * 
	 * @return 顶级级别
	 */
	List<MechanismRank> findRoots();
	
	
	public List<MechanismRank> findTree();
	
	
	List<MechanismRank> findChildren(MechanismRank mechanismCategory);
	
}