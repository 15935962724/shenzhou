/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.dao.AdDao;
import net.shenzhou.dao.ProjectDao;
import net.shenzhou.entity.Ad;
import net.shenzhou.service.AdService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 广告
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("adServiceImpl")
public class AdServiceImpl extends BaseServiceImpl<Ad, Long> implements AdService {

	
	@Resource(name = "adDaoImpl")
	private AdDao adDao;
	
	@Resource(name = "adDaoImpl")
	public void setBaseDao(AdDao adDao) {
		super.setBaseDao(adDao);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void save(Ad ad) {
		super.save(ad);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public Ad update(Ad ad) {
		return super.update(ad);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public Ad update(Ad ad, String... ignoreProperties) {
		return super.update(ad, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(Ad ad) {
		super.delete(ad);
	}

	@Override
	public Map<String, Object> findList(String file) {
		// TODO Auto-generated method stub
		return adDao.findList(file);
	}

}