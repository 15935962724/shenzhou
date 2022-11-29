/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.VersionsDao;
import net.shenzhou.entity.Versions;
import net.shenzhou.service.VersionsService;

import org.springframework.stereotype.Service;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("versionsServiceImpl")
public class VersionsServiceImpl extends BaseServiceImpl<Versions, Long> implements VersionsService {

	@Resource(name = "versionsDaoImpl")
	private VersionsDao versionsDao;

	@Resource(name = "versionsDaoImpl")
	public void setBaseDao(VersionsDao versionsDao) {
		super.setBaseDao(versionsDao);
	}


}