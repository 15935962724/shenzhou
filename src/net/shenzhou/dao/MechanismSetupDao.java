/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;

import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismSetup;

/**
 * 2018-1-11 17:10:38
 * 机构设置
 * @author wsr
 *
 */
public interface MechanismSetupDao extends BaseDao<MechanismSetup, Long> {
	
	/**
	 * 通过机构获取收费标准
	 * @param mechanism
	 * @return
	 */
	List<MechanismSetup> getMechanismSetup(Mechanism mechanism);
	
	
}