/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.OrganizationDao;
import net.shenzhou.entity.Organization;
import net.shenzhou.service.OrganizationService;

import org.springframework.stereotype.Service;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("organizationServiceImpl")
public class OrganizationServiceImpl extends BaseServiceImpl<Organization, Long> implements OrganizationService {

	@Resource(name = "organizationDaoImpl")
	private OrganizationDao organizationDao;

	@Resource(name = "organizationDaoImpl")
	public void setBaseDao(OrganizationDao organizationDao) {
		super.setBaseDao(organizationDao);
	}


}