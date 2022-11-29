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
import net.shenzhou.dao.DoctorCategoryDao;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.service.DoctorCategoryService;

import org.springframework.stereotype.Service;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("doctorCategoryServiceImpl")
public class DoctorCategoryServiceImpl extends BaseServiceImpl<DoctorCategory, Long> implements DoctorCategoryService {

	@Resource(name = "doctorCategoryDaoImpl")
	private DoctorCategoryDao doctorCategoryDao;

	@Resource(name = "doctorCategoryDaoImpl")
	public void setBaseDao(DoctorCategoryDao doctorCategoryDao) {
		super.setBaseDao(doctorCategoryDao);
	}

	@Override
	public List<DoctorCategory> findTree() {
		return doctorCategoryDao.findChildren(null, null);
	}

	@Override
	public List<DoctorCategory> findChildren(DoctorCategory droductCategory) {
		// TODO Auto-generated method stub
		return doctorCategoryDao.findChildren(droductCategory, null);
	}

	@Override
	public Page<DoctorCategory> findPage(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return doctorCategoryDao.findPage(query_map);
	}

	@Override
	public List<DoctorCategory> findList(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return doctorCategoryDao.findList(query_map);
	}

	@Override
	public List<DoctorCategory> find() {
		// TODO Auto-generated method stub
		return doctorCategoryDao.find();
	}

}