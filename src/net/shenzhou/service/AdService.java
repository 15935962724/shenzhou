/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.Map;

import net.shenzhou.entity.Ad;

/**
 * Service - 广告
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface AdService extends BaseService<Ad, Long> {

	/**
	 * 用户端首页轮播图
	 * @param file
	 * @return
	 */
	Map<String ,Object> findList(String file);
	
}