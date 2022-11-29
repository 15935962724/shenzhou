/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.shenzhou.dao.MechanismDao;
import net.shenzhou.dao.MechanismSetupDao;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismSetup;
import net.shenzhou.service.MechanismSetupService;

/**
 * 2018-1-11 17:19:09
 * 机构设置
 * @author wsr
 *
 */
@Service("mechanismSetupServiceImpl")
public class MechanismSetupServiceImpl extends BaseServiceImpl<MechanismSetup, Long> implements MechanismSetupService {

	@Resource(name = "mechanismSetupDaoImpl")
	private MechanismSetupDao mechanismSetupDao;
	
	@Resource(name = "mechanismSetupDaoImpl")
	public void setBaseDao(MechanismSetupDao mechanismSetupDao) {
		super.setBaseDao(mechanismSetupDao);
	}


	@Override
	public List<MechanismSetup> getMechanismSetup(Mechanism mechanism) {
		return mechanismSetupDao.getMechanismSetup(mechanism);
	}

	
}