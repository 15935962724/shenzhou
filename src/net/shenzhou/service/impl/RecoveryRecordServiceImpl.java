/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.dao.RecoveryRecordDao;
import net.shenzhou.entity.RecoveryRecord;
import net.shenzhou.service.RecoveryRecordService;

import org.springframework.stereotype.Service;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("recoveryRecordServiceImpl")
public class RecoveryRecordServiceImpl extends BaseServiceImpl<RecoveryRecord, Long> implements RecoveryRecordService {

	@Resource(name = "recoveryRecordDaoImpl")
	private RecoveryRecordDao recoveryRecordDao;

	@Resource(name = "recoveryRecordDaoImpl")
	public void setBaseDao(RecoveryRecordDao recoveryRecordDao) {
		super.setBaseDao(recoveryRecordDao);
	}

	@Override
	public List<RecoveryRecord> findRecoveryRecordList(
			Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return recoveryRecordDao.findRecoveryRecordList(query_map);
	}


}