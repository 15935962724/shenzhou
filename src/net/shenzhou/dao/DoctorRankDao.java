/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.DoctorRank;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.DoctorRank;

/**
 * Dao - 专家级别
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface DoctorRankDao extends BaseDao<DoctorRank, Long> {



	/**
	 * 查找顶级专家级别
	 * 
	 * @param count
	 *            数量
	 * @return 顶级专家级别
	 */
	List<DoctorRank> findRoots(Integer count);

	/**
	 * 查找上级专家级别
	 * 
	 * @param productCategory
	 *            专家级别
	 * @param count
	 *            数量
	 * @return 上级专家级别
	 */
	List<DoctorRank> findParents(DoctorRank doctorRank, Integer count);

	/**
	 * 查找下级专家级别
	 * 
	 * @param productCategory
	 *            专家级别
	 * @param count
	 *            数量
	 * @return 下级专家级别
	 */
	List<DoctorRank> findChildren(DoctorRank doctorRank, Integer count);



	
}