/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.dao.MechanismCategoryDao;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.service.MechanismCategoryService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 管理员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("mechanismCategoryServiceImpl")
public class MechanismCategoryServiceImpl extends BaseServiceImpl<MechanismCategory, Long> implements MechanismCategoryService {

	@Resource(name = "mechanismCategoryDaoImpl")
	private MechanismCategoryDao mechanismCategoryDao;

	@Resource(name = "mechanismCategoryDaoImpl")
	public void setBaseDao(MechanismCategoryDao mechanismCategoryDao) {
		super.setBaseDao(mechanismCategoryDao);
	}

	@Override
	public List<MechanismCategory> findRoots() {
		return mechanismCategoryDao.findRoots(null);
	}

	

	@Transactional(readOnly = true)
	public List<MechanismCategory> findRoots(Integer count) {
		return mechanismCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable("mechanismCategory")
	public List<MechanismCategory> findRoots(Integer count, String cacheRegion) {
		return mechanismCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<MechanismCategory> findParents(MechanismCategory mechanismCategory) {
		return mechanismCategoryDao.findParents(mechanismCategory, null);
	}

	@Transactional(readOnly = true)
	public List<MechanismCategory> findParents(MechanismCategory mechanismCategory, Integer count) {
		return mechanismCategoryDao.findParents(mechanismCategory, count);
	}

	@Transactional(readOnly = true)
	@Cacheable("mechanismCategory")
	public List<MechanismCategory> findParents(MechanismCategory mechanismCategory, Integer count, String cacheRegion) {
		return mechanismCategoryDao.findParents(mechanismCategory, count);
	}

	@Transactional(readOnly = true)
	public List<MechanismCategory> findTree() {
		return mechanismCategoryDao.findChildren(null, null);
	}

	@Transactional(readOnly = true)
	public List<MechanismCategory> findChildren(MechanismCategory mechanismCategory) {
		return mechanismCategoryDao.findChildren(mechanismCategory, null);
	}

	@Transactional(readOnly = true)
	public List<MechanismCategory> findChildren(MechanismCategory mechanismCategory, Integer count) {
		return mechanismCategoryDao.findChildren(mechanismCategory, count);
	}

	@Transactional(readOnly = true)
	@Cacheable("mechanismCategory")
	public List<MechanismCategory> findChildren(MechanismCategory mechanismCategory, Integer count, String cacheRegion) {
		return mechanismCategoryDao.findChildren(mechanismCategory, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "mechanism", "mechanismCategory", "review", "consultation" }, allEntries = true)
	public MechanismCategory update(MechanismCategory mechanismCategory) {
		return super.update(mechanismCategory);
	}

	
}