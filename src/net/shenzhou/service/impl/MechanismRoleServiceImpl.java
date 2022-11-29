/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.dao.MechanismRoleDao;
import net.shenzhou.dao.OrderDao;
import net.shenzhou.entity.MechanismRole;
import net.shenzhou.service.MechanismRoleService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 角色
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("mechanismRoleServiceImpl")
public class MechanismRoleServiceImpl extends BaseServiceImpl<MechanismRole, Long> implements MechanismRoleService {

	@Resource(name = "mechanismRoleDaoImpl")
	private MechanismRoleDao mechanismRoleDao;
	
	@Resource(name = "mechanismRoleDaoImpl")
	public void setBaseDao(MechanismRoleDao mechanismRoleDao) {
		super.setBaseDao(mechanismRoleDao);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void save(MechanismRole mechanismRole) {
		super.save(mechanismRole);
	}

//	@Override
//	@Transactional
//	@CacheEvict(value = "authorization", allEntries = true)
//	public MechanismRole update(MechanismRole mechanismRole) {
//		return super.update(mechanismRole);
//	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public MechanismRole update(MechanismRole mechanismRole, String... ignoreProperties) {
		return super.update(mechanismRole, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(MechanismRole mechanismRole) {
		super.delete(mechanismRole);
	}

	@Override
	public Page<MechanismRole> findPage(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return mechanismRoleDao.findPage(query_map);
	}

}