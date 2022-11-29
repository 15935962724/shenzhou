/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.WorkDateDao;
import net.shenzhou.entity.WorkDate;

import org.springframework.stereotype.Repository;

/**
 * 2017-7-3 13:29:16
 * 机构工作时间
 * @author wsr
 *
 */
@Repository("workDateDaoImpl")
public class WorkDateDaoImpl extends BaseDaoImpl<WorkDate, Long> implements WorkDateDao {

}