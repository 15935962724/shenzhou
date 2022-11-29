/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.dao.VisitDao;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Visit;
import net.shenzhou.service.VisitService;

import org.springframework.stereotype.Service;

/**
 * Service - 回访
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("visitServiceImpl")
public class VisitServiceImpl extends BaseServiceImpl<Visit, Long> implements VisitService {

	@Resource(name = "visitDaoImpl")
	private VisitDao visitDao;

	@Resource(name = "visitDaoImpl")
	public void setBaseDao(VisitDao visitDao) {
		super.setBaseDao(visitDao);
	}

	@Override
	public List<Visit> getByMechanism(Mechanism mechanism, Member member,Date startDate,Date endDate) {
		
		return visitDao.getByMechanism(mechanism, member,startDate,endDate);
	}


}