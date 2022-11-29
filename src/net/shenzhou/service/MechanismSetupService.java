/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;

import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismSetup;

/**
 * 
 * 机构设置
 * @author wsr
 *
 */
public interface MechanismSetupService extends BaseService<MechanismSetup, Long> {
	
	/**
	 * 通过机构获取设置收费标准
	 * @param mechanism
	 * @return
	 */
	List<MechanismSetup> getMechanismSetup(Mechanism mechanism);
	
}