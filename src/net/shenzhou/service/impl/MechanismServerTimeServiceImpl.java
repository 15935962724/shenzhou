/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.MechanismServerTimeDao;
import net.shenzhou.entity.MechanismServerTime;
import net.shenzhou.service.MechanismServerTimeService;

import org.springframework.stereotype.Service;

/**
 * 
 * 机构
 * @author wsr
 *
 */
@Service("mechanismServerTimeServiceImpl")
public class MechanismServerTimeServiceImpl extends BaseServiceImpl<MechanismServerTime, Long> implements MechanismServerTimeService {

	@Resource(name = "mechanismServerTimeDaoImpl")
	private MechanismServerTimeDao mechanismServerTimeDao;
	
	
	@Resource(name = "mechanismServerTimeDaoImpl")
	public void setBaseDao(MechanismServerTimeDao mechanismServerTimeDao) {
		super.setBaseDao(mechanismServerTimeDao);
	}



}