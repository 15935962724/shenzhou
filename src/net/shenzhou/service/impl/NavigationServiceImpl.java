/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.Filter;
import net.shenzhou.Order;
import net.shenzhou.dao.NavigationDao;
import net.shenzhou.entity.Navigation;
import net.shenzhou.entity.Navigation.Position;
import net.shenzhou.service.NavigationService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 导航
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("navigationServiceImpl")
public class NavigationServiceImpl extends BaseServiceImpl<Navigation, Long> implements NavigationService {

	@Resource(name = "navigationDaoImpl")
	private NavigationDao navigationDao;

	@Resource(name = "navigationDaoImpl")
	public void setBaseDao(NavigationDao navigationDao) {
		super.setBaseDao(navigationDao);
	}

	@Transactional(readOnly = true)
	public List<Navigation> findList(Position position) {
		return navigationDao.findList(position);
	}

	@Transactional(readOnly = true)
	@Cacheable("navigation")
	public List<Navigation> findList(Integer count, List<Filter> filters, List<Order> orders, String cacheRegion) {
		return navigationDao.findList(null, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public void save(Navigation navigation) {
		super.save(navigation);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public Navigation update(Navigation navigation) {
		return super.update(navigation);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public Navigation update(Navigation navigation, String... ignoreProperties) {
		return super.update(navigation, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "navigation", allEntries = true)
	public void delete(Navigation navigation) {
		super.delete(navigation);
	}

}