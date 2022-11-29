/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.CacheManager;
import net.shenzhou.dao.GrantRightDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.GrantRight;
import net.shenzhou.entity.Member;
import net.shenzhou.service.GrantRightService;

import org.springframework.stereotype.Service;

/**
 * 授权
 * 2017-6-14 09:56:01
 * @author wsr
 *
 */
@Service("grantRightServiceImpl")
public class GrantRightServiceImpl extends BaseServiceImpl<GrantRight, Long> implements GrantRightService {

	@Resource(name = "grantRightDaoImpl")
	private GrantRightDao grantRightDao;
	
	@Resource(name = "grantRightDaoImpl")
	public void setBaseDao(GrantRightDao grantRightDao) {
		super.setBaseDao(grantRightDao);
	}

	@Override
	public Map<String, Object> findList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return grantRightDao.findList(map);
	}

	@Override
	public boolean findByDoctor(Doctor doctor, Member member) {
		return grantRightDao.findByDoctor(doctor,member);
	}

	@Override
	public GrantRight findByDoctorAndMember(Doctor doctor, Member member) {
		// TODO Auto-generated method stub
		return grantRightDao.findByDoctorAndMember(doctor,member);
	}
	
}