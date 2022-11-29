/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.WorkDateDao;
import net.shenzhou.entity.WorkDate;
import net.shenzhou.service.WorkDateService;

import org.springframework.stereotype.Service;

/**
 * 机构工作时间
 * 2017-7-3 13:34:15
 * @author wsr
 *
 */
@Service("workDateServiceImpl")
public class WorkDateServiceImpl extends BaseServiceImpl<WorkDate, Long> implements WorkDateService {

	@Resource(name = "workDateDaoImpl")
	public void setBaseDao(WorkDateDao workDateDao) {
		super.setBaseDao(workDateDao);
	}

	

}