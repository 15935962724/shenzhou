/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.dao.DoctorPointLogDao;
import net.shenzhou.entity.DoctorPointLog;
import net.shenzhou.service.DoctorPointLogService;

import org.springframework.stereotype.Service;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("doctorPointLogServiceImpl")
public class DoctorPointLogServiceImpl extends BaseServiceImpl<DoctorPointLog, Long> implements DoctorPointLogService {

	@Resource(name = "doctorPointLogDaoImpl")
	private DoctorPointLogDao doctorPointLogDao;

	@Resource(name = "doctorPointLogDaoImpl")
	public void setBaseDao(DoctorPointLogDao doctorPointLogDao) {
		super.setBaseDao(doctorPointLogDao);
	}

	@Override
	public List<DoctorPointLog> findDoctorAcquire(Map<String,Object> query_map) {
		return doctorPointLogDao.findDoctorAcquire(query_map);
	}


}