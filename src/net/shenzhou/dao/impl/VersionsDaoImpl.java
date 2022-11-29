/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.VersionsDao;
import net.shenzhou.entity.Versions;

import org.springframework.stereotype.Repository;
/**
 * Dao - 银行卡
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("versionsDaoImpl")
public class VersionsDaoImpl extends BaseDaoImpl<Versions, Long> implements VersionsDao {
	
}