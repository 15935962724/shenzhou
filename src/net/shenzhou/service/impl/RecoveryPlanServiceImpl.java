/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.dao.RecoveryPlanDao;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.RecoveryPlan;
import net.shenzhou.service.RecoveryPlanService;

import org.springframework.stereotype.Service;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("recoveryPlanServiceImpl")
public class RecoveryPlanServiceImpl extends BaseServiceImpl<RecoveryPlan, Long> implements RecoveryPlanService {

	@Resource(name = "recoveryPlanDaoImpl")
	private RecoveryPlanDao recoveryPlanDao;

	@Resource(name = "recoveryPlanDaoImpl")
	public void setBaseDao(RecoveryPlanDao recoveryPlanDao) {
		super.setBaseDao(recoveryPlanDao);
	}

	@Override
	public RecoveryPlan findRecentlyRecoveryPlan(AssessReport assessReport) {
		// TODO Auto-generated method stub
		return recoveryPlanDao.findRecentlyRecoveryPlan(assessReport);
	}

	@Override
	public List<RecoveryPlan> findRecoveryPlanList(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return recoveryPlanDao.findRecoveryPlanList(query_map);
	}


}