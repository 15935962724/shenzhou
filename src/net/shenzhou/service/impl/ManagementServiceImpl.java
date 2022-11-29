/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.ManagementDao;
import net.shenzhou.entity.Management;
import net.shenzhou.service.ManagementService;

import org.springframework.stereotype.Service;

/**
 * 经营模式
 * 2017-07-19 15:16:07
 * @author wsr
 */
@Service("managementServiceImpl")
public class ManagementServiceImpl extends BaseServiceImpl<Management, Long> implements ManagementService {

	@Resource(name = "managementDaoImpl")
	private ManagementDao managementDao;

	@Resource(name = "managementDaoImpl")
	public void setBaseDao(ManagementDao managementDao) {
		super.setBaseDao(managementDao);
	}



}