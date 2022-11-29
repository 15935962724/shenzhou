/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.WithdrawDepositDao;
import net.shenzhou.entity.WithdrawDeposit;
import net.shenzhou.service.WithdrawDepositService;

import org.springframework.stereotype.Service;

/**
 * Service - 提现明细单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("withdrawDepositServiceImpl")
public class WithdrawDepositServiceImpl extends BaseServiceImpl<WithdrawDeposit, Long> implements WithdrawDepositService {

	@Resource(name = "withdrawDepositDaoImpl")
	private WithdrawDepositDao withdrawDepositDao;

	@Resource(name = "withdrawDepositDaoImpl")
	public void setBaseDao(WithdrawDepositDao withdrawDepositDao) {
		super.setBaseDao(withdrawDepositDao);
	}


}