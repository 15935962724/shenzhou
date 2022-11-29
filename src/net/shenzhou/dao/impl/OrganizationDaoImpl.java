/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.OrganizationDao;
import net.shenzhou.entity.Organization;

import org.springframework.stereotype.Repository;
/**
 * Dao - 银行卡
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("organizationDaoImpl")
public class OrganizationDaoImpl extends BaseDaoImpl<Organization, Long> implements OrganizationDao {
}