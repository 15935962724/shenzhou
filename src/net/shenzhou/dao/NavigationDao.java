/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;

import net.shenzhou.entity.Navigation;
import net.shenzhou.entity.Navigation.Position;

/**
 * Dao - 导航
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface NavigationDao extends BaseDao<Navigation, Long> {

	/**
	 * 查找导航
	 * 
	 * @param position
	 *            位置
	 * @return 导航
	 */
	List<Navigation> findList(Position position);

}