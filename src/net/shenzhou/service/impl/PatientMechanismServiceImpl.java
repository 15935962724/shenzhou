/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.dao.AdDao;
import net.shenzhou.dao.PatientMechanismDao;
import net.shenzhou.entity.PatientMechanism;
import net.shenzhou.service.PatientMechanismService;

import org.springframework.stereotype.Service;

/**
 * Service - 患者所在机构的状态
 * @date 2018-3-23 16:58:14
 * @author wsr
 */
@Service("patientMechanismServiceImpl")
public class PatientMechanismServiceImpl extends BaseServiceImpl<PatientMechanism, Long> implements PatientMechanismService {

	
	@Resource(name = "patientMechanismDaoImpl")
	private PatientMechanismDao patientMechanismDao;
	
	@Resource(name = "patientMechanismDaoImpl")
	public void setBaseDao(PatientMechanismDao patientMechanismDao) {
		super.setBaseDao(patientMechanismDao);
	}

	@Override
	public Page<PatientMechanism> getPatientMechanisms(
			Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return patientMechanismDao.getPatientMechanisms(query_map);
	}

	@Override
	public List<PatientMechanism> downloadPatientHealthType(
			Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return patientMechanismDao.downloadPatientHealthType(query_map);
	}

	@Override
	public Integer daysPatientCount(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return patientMechanismDao.daysPatientCount(query_map);
	}

	

}