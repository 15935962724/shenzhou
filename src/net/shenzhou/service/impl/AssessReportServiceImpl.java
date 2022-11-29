/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.AssessReportDao;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.Member;
import net.shenzhou.service.AssessReportService;

import org.springframework.stereotype.Service;

/**
 * Service - 评估报告
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("assessReportServiceImpl")
public class AssessReportServiceImpl extends BaseServiceImpl<AssessReport, Long> implements AssessReportService {

	@Resource(name = "assessReportDaoImpl")
	private AssessReportDao assessReportDao;

	@Resource(name = "assessReportDaoImpl")
	public void setBaseDao(AssessReportDao assessReportDao) {
		super.setBaseDao(assessReportDao);
	}

	@Override
	public Long assessReportCount(Member member) {
		
		return assessReportDao.assessReportCount(member);
	}

	@Override
	public AssessReport getRecentlyAssessReport(Member member) {
		// TODO Auto-generated method stub
		return assessReportDao.getRecentlyAssessReport(member);
	}

}