/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.MechanismRole;

/**
 * Service - 角色
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface MechanismRoleService extends BaseService<MechanismRole, Long> {

	
	Page<MechanismRole> findPage(Map<String, Object> query_map);
	
	
	
}