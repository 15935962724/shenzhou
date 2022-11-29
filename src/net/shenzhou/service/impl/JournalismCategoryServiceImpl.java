/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.dao.JournalismCategoryDao;
import net.shenzhou.entity.JournalismCategory;
import net.shenzhou.service.JournalismCategoryService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 新闻分类
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("journalismCategoryServiceImpl")
public class JournalismCategoryServiceImpl extends BaseServiceImpl<JournalismCategory, Long> implements JournalismCategoryService {

	@Resource(name = "journalismCategoryDaoImpl")
	private JournalismCategoryDao journalismCategoryDao;

	@Resource(name = "journalismCategoryDaoImpl")
	public void setBaseDao(JournalismCategoryDao journalismCategoryDao) {
		super.setBaseDao(journalismCategoryDao);
	}

	@Transactional(readOnly = true)
	public List<JournalismCategory> findRoots() {
		return journalismCategoryDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public List<JournalismCategory> findRoots(Integer count) {
		return journalismCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable("journalismCategory")
	public List<JournalismCategory> findRoots(Integer count, String cacheRegion) {
		return journalismCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<JournalismCategory> findParents(JournalismCategory journalismCategory) {
		return journalismCategoryDao.findParents(journalismCategory, null);
	}

	@Transactional(readOnly = true)
	public List<JournalismCategory> findParents(JournalismCategory journalismCategory, Integer count) {
		return journalismCategoryDao.findParents(journalismCategory, count);
	}

	@Transactional(readOnly = true)
	@Cacheable("journalismCategory")
	public List<JournalismCategory> findParents(JournalismCategory journalismCategory, Integer count, String cacheRegion) {
		return journalismCategoryDao.findParents(journalismCategory, count);
	}

	@Transactional(readOnly = true)
	public List<JournalismCategory> findTree() {
		return journalismCategoryDao.findChildren(null, null);
	}

	@Transactional(readOnly = true)
	public List<JournalismCategory> findChildren(JournalismCategory journalismCategory) {
		return journalismCategoryDao.findChildren(journalismCategory, null);
	}

	@Transactional(readOnly = true)
	public List<JournalismCategory> findChildren(JournalismCategory journalismCategory, Integer count) {
		return journalismCategoryDao.findChildren(journalismCategory, count);
	}

	@Transactional(readOnly = true)
	@Cacheable("journalismCategory")
	public List<JournalismCategory> findChildren(JournalismCategory journalismCategory, Integer count, String cacheRegion) {
		return journalismCategoryDao.findChildren(journalismCategory, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "journalism", "journalismCategory" }, allEntries = true)
	public void save(JournalismCategory journalismCategory) {
		super.save(journalismCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "journalism", "journalismCategory" }, allEntries = true)
	public JournalismCategory update(JournalismCategory journalismCategory) {
		return super.update(journalismCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "journalism", "journalismCategory" }, allEntries = true)
	public JournalismCategory update(JournalismCategory journalismCategory, String... ignoreProperties) {
		return super.update(journalismCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "journalism", "journalismCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "journalism", "journalismCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "journalism", "journalismCategory" }, allEntries = true)
	public void delete(JournalismCategory journalismCategory) {
		super.delete(journalismCategory);
	}

}