/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.service.impl;


import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.dao.BalanceDao;
import net.shenzhou.dao.MemberDao;
import net.shenzhou.entity.Balance;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.service.BalanceService;

import org.springframework.stereotype.Service;

/**
 * Service - 余额
 * @author wsr
 * @date 2018-3-19 16:02:46
 */
@Service("balanceServiceImpl")
public class BalanceServiceImpl extends BaseServiceImpl<Balance, Long> implements BalanceService {
	
	@Resource(name = "balanceDaoImpl")
	public void setBaseDao(BalanceDao balanceDao) {
		super.setBaseDao(balanceDao);
	}

	@Resource(name = "balanceDaoImpl")
	private BalanceDao BalanceDao;
	@Override
	public List<Balance> getBalanceList(Member member) {
		// TODO Auto-generated method stub
		return BalanceDao.getBalanceList(member);
	}
	@Override
	public List<Balance> getByList(Member member, Mechanism mechanism) {
		// TODO Auto-generated method stub
		return null;
	}

}