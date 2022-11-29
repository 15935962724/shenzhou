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
import net.shenzhou.dao.DoctorRankDao;
import net.shenzhou.dao.ProductCategoryDao;
import net.shenzhou.dao.DoctorRankDao;
import net.shenzhou.entity.DoctorRank;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.DoctorRank;
import net.shenzhou.service.DoctorRankService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 专家级别
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("doctorRankServiceImpl")
public class DoctorRankServiceImpl extends BaseServiceImpl<DoctorRank, Long> implements DoctorRankService {
	

	@Resource(name = "doctorRankDaoImpl")
	private DoctorRankDao doctorRankDao;

	@Resource(name = "doctorRankDaoImpl")
	public void setBaseDao(DoctorRankDao doctorRankDao) {
		super.setBaseDao(doctorRankDao);
	}


	@Transactional(readOnly = true)
	public List<DoctorRank> findRoots() {
		return doctorRankDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public List<DoctorRank> findRoots(Integer count) {
		return doctorRankDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable("productCategory")
	public List<DoctorRank> findRoots(Integer count, String cacheRegion) {
		return doctorRankDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<DoctorRank> findParents(DoctorRank doctorRank) {
		return doctorRankDao.findParents(doctorRank, null);
	}

	@Transactional(readOnly = true)
	public List<DoctorRank> findParents(DoctorRank doctorRank, Integer count) {
		return doctorRankDao.findParents(doctorRank, count);
	}

	@Transactional(readOnly = true)
	@Cacheable("doctorRank")
	public List<DoctorRank> findParents(DoctorRank doctorRank, Integer count, String cacheRegion) {
		return doctorRankDao.findParents(doctorRank, count);
	}

	@Transactional(readOnly = true)
	public List<DoctorRank> findTree() {
		return doctorRankDao.findChildren(null, null);
	}

	@Transactional(readOnly = true)
	public List<DoctorRank> findChildren(DoctorRank doctorRank) {
		return doctorRankDao.findChildren(doctorRank, null);
	}

	@Transactional(readOnly = true)
	public List<DoctorRank> findChildren(DoctorRank doctorRank, Integer count) {
		return doctorRankDao.findChildren(doctorRank, count);
	}

	@Transactional(readOnly = true)
	@Cacheable("doctorRank")
	public List<DoctorRank> findChildren(DoctorRank doctorRank, Integer count, String cacheRegion) {
		return doctorRankDao.findChildren(doctorRank, count);
	}

	
}