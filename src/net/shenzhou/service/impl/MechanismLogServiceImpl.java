/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.MechanismLogDao;
import net.shenzhou.entity.MechanismLog;
import net.shenzhou.service.MechanismLogService;

import org.springframework.stereotype.Service;

/**
 * ServiceImpl - 机构操作记录 
 * @date 2018-3-5 17:37:07
 * @author wsr
 */
@Service("mechanismLogServiceImpl")
public class MechanismLogServiceImpl extends BaseServiceImpl<MechanismLog, Long> implements MechanismLogService {

	@Resource(name = "mechanismLogDaoImpl")
	private MechanismLogDao mechanismLogDao;
	
	@Resource(name = "mechanismLogDaoImpl")
	public void setBaseDao(MechanismLogDao mechanismLogDao) {
		super.setBaseDao(mechanismLogDao);
	}



}