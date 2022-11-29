/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.ManagementDao;
import net.shenzhou.entity.Management;

import org.springframework.stereotype.Repository;

/**
 * 经营模式
 * 2017-07-19 15:11:26
 * @author 2017-07-19 15:11:34
 *
 */
@Repository("managementDaoImpl")
public class ManagementDaoImpl extends BaseDaoImpl<Management, Long> implements ManagementDao {

}