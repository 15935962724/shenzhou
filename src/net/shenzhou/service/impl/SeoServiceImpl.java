/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.SeoDao;
import net.shenzhou.entity.Seo;
import net.shenzhou.entity.Seo.Type;
import net.shenzhou.service.SeoService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - SEO设置
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("seoServiceImpl")
public class SeoServiceImpl extends BaseServiceImpl<Seo, Long> implements SeoService {

	@Resource(name = "seoDaoImpl")
	private SeoDao seoDao;

	@Resource(name = "seoDaoImpl")
	public void setBaseDao(SeoDao seoDao) {
		super.setBaseDao(seoDao);
	}

	@Transactional(readOnly = true)
	public Seo find(Type type) {
		return seoDao.find(type);
	}

	@Transactional(readOnly = true)
	@Cacheable("seo")
	public Seo find(Type type, String cacheRegion) {
		return seoDao.find(type);
	}

	@Override
	@Transactional
	@CacheEvict(value = "seo", allEntries = true)
	public void save(Seo seo) {
		super.save(seo);
	}

	@Override
	@Transactional
	@CacheEvict(value = "seo", allEntries = true)
	public Seo update(Seo seo) {
		return super.update(seo);
	}

	@Override
	@Transactional
	@CacheEvict(value = "seo", allEntries = true)
	public Seo update(Seo seo, String... ignoreProperties) {
		return super.update(seo, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "seo", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "seo", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "seo", allEntries = true)
	public void delete(Seo seo) {
		super.delete(seo);
	}

}