/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;

import net.shenzhou.entity.Area;

/**
 * Dao - 地区
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface AreaDao extends BaseDao<Area, Long> {

	/**
	 * 查找顶级地区
	 * 
	 * @param count
	 *            数量
	 * @return 顶级地区
	 */
	List<Area> findRoots(Integer count);
	
	
	
		/**
	 * 查找子集地区
	 * 
	 * @param 
	 *            数量
	 * @return 顶级地区
	 */
	List<Area> findList(Area parent);
	
	
	
	

}