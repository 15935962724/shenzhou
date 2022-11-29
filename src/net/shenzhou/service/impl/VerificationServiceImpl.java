/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.VerificationDao;
import net.shenzhou.entity.Admin;
import net.shenzhou.entity.Verification;
import net.shenzhou.service.VerificationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 管理员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("verificationServiceImpl")
public class VerificationServiceImpl extends BaseServiceImpl<Verification, Long> implements VerificationService {

	@Resource(name = "verificationDaoImpl")
	private VerificationDao verificationDao;

	@Resource(name = "verificationDaoImpl")
	public void setBaseDao(VerificationDao verificationDao) {
		super.setBaseDao(verificationDao);
	}
	
	@Override
	public boolean mobileExists(String mobile) {
		// TODO Auto-generated method stub
		 return verificationDao.mobileExists(mobile);
	}

	@Override
	public Verification findByMobile(String mobile) {
		// TODO Auto-generated method stub
		return verificationDao.findByMobile(mobile);
	}


}