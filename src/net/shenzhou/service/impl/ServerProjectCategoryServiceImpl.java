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
import net.shenzhou.dao.ServerProjectCategoryDao;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.ServerProjectCategoryService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 2017-05-24 14:45:02
 * @author wsr
 *
 */
@Service("serverProjectCategoryServiceImpl")
public class ServerProjectCategoryServiceImpl extends BaseServiceImpl<ServerProjectCategory, Long> implements ServerProjectCategoryService {

	@Resource(name = "serverProjectCategoryDaoImpl")
	private ServerProjectCategoryDao serverProjectCategoryDao;

	@Resource(name = "serverProjectCategoryDaoImpl")
	public void setBaseDao(ServerProjectCategoryDao serverProjectCategoryDao) {
		super.setBaseDao(serverProjectCategoryDao);
	}


	@Transactional(readOnly = true)
	public List<ServerProjectCategory> findRoots() {
		return serverProjectCategoryDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public List<ServerProjectCategory> findRoots(Integer count) {
		return serverProjectCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable("productCategory")
	public List<ServerProjectCategory> findRoots(Integer count, String cacheRegion) {
		return serverProjectCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<ServerProjectCategory> findParents(ServerProjectCategory serverProjectCategory) {
		return serverProjectCategoryDao.findParents(serverProjectCategory, null);
	}

	@Transactional(readOnly = true)
	public List<ServerProjectCategory> findParents(ServerProjectCategory serverProjectCategory, Integer count) {
		return serverProjectCategoryDao.findParents(serverProjectCategory, count);
	}

	@Transactional(readOnly = true)
	@Cacheable("serverProjectCategory")
	public List<ServerProjectCategory> findParents(ServerProjectCategory serverProjectCategory, Integer count, String cacheRegion) {
		return serverProjectCategoryDao.findParents(serverProjectCategory, count);
	}

	@Transactional(readOnly = true)
	public List<ServerProjectCategory> findTree() {
		return serverProjectCategoryDao.findChildren(null, null);
	}

	@Transactional(readOnly = true)
	public List<ServerProjectCategory> findChildren(ServerProjectCategory serverProjectCategory) {
		return serverProjectCategoryDao.findChildren(serverProjectCategory, null);
	}

	@Transactional(readOnly = true)
	public List<ServerProjectCategory> findChildren(ServerProjectCategory serverProjectCategory, Integer count) {
		return serverProjectCategoryDao.findChildren(serverProjectCategory, count);
	}

	@Transactional(readOnly = true)
	@Cacheable("serverProjectCategory")
	public List<ServerProjectCategory> findChildren(ServerProjectCategory serverProjectCategory, Integer count, String cacheRegion) {
		return serverProjectCategoryDao.findChildren(serverProjectCategory, count);
	}


	@Override
	public List<ServerProjectCategory> getServerProjectCategory(
			Mechanism mechanism) {
		// TODO Auto-generated method stub
		return serverProjectCategoryDao.getServerProjectCategory(mechanism);
	}


	@Override
	public List<ServerProjectCategory> getServerProjectCategory() {
		// TODO Auto-generated method stub
		return serverProjectCategoryDao.getServerProjectCategory();
	}


	@Override
	public Page<ServerProjectCategory> getServerProjectCategory(
			Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return serverProjectCategoryDao.getServerProjectCategory(query_map);
	}


	@Override
	public List<ServerProjectCategory> serverProjectCategoryList() {
		// TODO Auto-generated method stub
		return serverProjectCategoryDao.serverProjectCategoryList();
	}


	@Override
	public ServerProjectCategory findParent(
			ServerProjectCategory serverProjectCategory) {
		System.out.println("-----"+serverProjectCategory.getId());
		ServerProjectCategory pServerProjectCategory = serverProjectCategoryDao.find(serverProjectCategory.getId());
		if (serverProjectCategory.getParent()!=null) {
			return findParent(pServerProjectCategory.getParent());
		}else{
			System.out.println(">>>>>"+serverProjectCategory.getId());
			return serverProjectCategory;
		}
	}
}