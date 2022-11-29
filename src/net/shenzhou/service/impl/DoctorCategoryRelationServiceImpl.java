/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.DoctorCategoryRelationDao;
import net.shenzhou.entity.DoctorCategoryRelation;
import net.shenzhou.service.DoctorCategoryRelationService;

import org.springframework.stereotype.Service;

/**
 * Service - 医生机构关系表
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("doctorCategoryRelationServiceImpl")
public class DoctorCategoryRelationServiceImpl extends BaseServiceImpl<DoctorCategoryRelation, Long> implements DoctorCategoryRelationService {

	@Resource(name = "doctorCategoryRelationDaoImpl")
	private DoctorCategoryRelationDao doctorCategoryRelationDao;

	@Resource(name = "doctorCategoryRelationDaoImpl")
	public void setBaseDao(DoctorCategoryRelationDao doctorCategoryRelationDaoImpl) {
		super.setBaseDao(doctorCategoryRelationDaoImpl);
	}


}