/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.PatientGroupDao;
import net.shenzhou.entity.PatientGroup;
import net.shenzhou.service.PatientGroupService;

import org.springframework.stereotype.Service;

/**
 * Service - 患者分类
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("patientGroupServiceImpl")
public class PatientGroupServiceImpl extends BaseServiceImpl<PatientGroup, Long> implements PatientGroupService {

	@Resource(name = "patientGroupDaoImpl")
	private PatientGroupDao patientGroupDao;

	@Resource(name = "patientGroupDaoImpl")
	public void setBaseDao(PatientGroupDao patientGroupDao) {
		super.setBaseDao(patientGroupDao);
	}


}