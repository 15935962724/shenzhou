/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.DrillContentDao;
import net.shenzhou.entity.DrillContent;
import net.shenzhou.service.DrillContentService;

import org.springframework.stereotype.Service;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("drillContentServiceImpl")
public class DrillContentServiceImpl extends BaseServiceImpl<DrillContent, Long> implements DrillContentService {

	@Resource(name = "drillContentDaoImpl")
	private DrillContentDao drillContentDao;

	@Resource(name = "drillContentDaoImpl")
	public void setBaseDao(DrillContentDao drillContentDao) {
		super.setBaseDao(drillContentDao);
	}


}