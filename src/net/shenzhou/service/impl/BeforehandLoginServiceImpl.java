/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.dao.BeforehandLoginDao;
import net.shenzhou.entity.BeforehandLogin;
import net.shenzhou.entity.BeforehandLogin.UserType;
import net.shenzhou.service.BeforehandLoginService;

import org.springframework.stereotype.Service;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("beforehandLoginServiceImpl")
public class BeforehandLoginServiceImpl extends BaseServiceImpl<BeforehandLogin, Long> implements BeforehandLoginService {

	@Resource(name = "beforehandLoginDaoImpl")
	private BeforehandLoginDao beforehandLoginDao;

	@Resource(name = "beforehandLoginDaoImpl")
	public void setBaseDao(BeforehandLoginDao beforehandLoginDao) {
		super.setBaseDao(beforehandLoginDao);
	}

	@Override
	public BeforehandLogin findByMobile(String mobile) {
		// TODO Auto-generated method stub
		return beforehandLoginDao.findByMobile(mobile);
	}

	@Override
	public BeforehandLogin findByMobile(String mobile, UserType userTypes,UserType usersTypes) {
		// TODO Auto-generated method stub
		return beforehandLoginDao.findByMobile(mobile,userTypes,usersTypes);
	}

	@Override
	public List<BeforehandLogin> findMemberUnAcquire(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return beforehandLoginDao.findMemberUnAcquire(query_map);
	}

	@Override
	public List<BeforehandLogin> findDoctorUnAcquire(
			Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return beforehandLoginDao.findDoctorUnAcquire(query_map);
	}


}