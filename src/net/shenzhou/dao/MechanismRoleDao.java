/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.MechanismRole;

/**
 * Dao - 机构角色
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface MechanismRoleDao extends BaseDao<MechanismRole, Long> {

	
	/**
	 * 角色分页信息
	 * @param map
	 * @return
	 */
	Page<MechanismRole> findPage(Map<String ,Object> query_map);
	
}