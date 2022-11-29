/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.Map;

import net.shenzhou.entity.Ad;

/**
 * Dao - 广告
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface AdDao extends BaseDao<Ad, Long> {

	/**
	 * 用户端首页广告(轮播图)
	 * @param file
	 * @return
	 */
	Map<String ,Object> findList(String file);
	
}