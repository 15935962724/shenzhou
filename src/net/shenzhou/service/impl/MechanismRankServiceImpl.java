/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.dao.MechanismRankDao;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.MechanismRank;
import net.shenzhou.service.MechanismRankService;

import org.springframework.stereotype.Service;

/**
 * Service - 管理员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("mechanismRankServiceImpl")
public class MechanismRankServiceImpl extends BaseServiceImpl<MechanismRank, Long> implements MechanismRankService {

	@Resource(name = "mechanismRankDaoImpl")
	private MechanismRankDao mechanismRankDao;

	@Resource(name = "mechanismRankDaoImpl")
	public void setBaseDao(MechanismRankDao mechanismRankDao) {
		super.setBaseDao(mechanismRankDao);
	}

	@Override
	public List<MechanismRank> findRoots() {
		// TODO Auto-generated method stub
		return mechanismRankDao.findRoots(null);
	}

	@Override
	public List<MechanismRank> findTree() {
		// TODO Auto-generated method stub
		return mechanismRankDao.findChildren(null, null);
	}

	@Override
	public List<MechanismRank> findChildren(
			MechanismRank mechanismRank) {
		// TODO Auto-generated method stub
		return mechanismRankDao.findChildren(mechanismRank, null);
	}

}