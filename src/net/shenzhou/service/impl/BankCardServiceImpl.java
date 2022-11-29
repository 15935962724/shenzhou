/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.BankCardDao;
import net.shenzhou.entity.BankCard;
import net.shenzhou.service.BankCardService;

import org.springframework.stereotype.Service;

/**
 * Service - 银行卡
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("bankCardServiceImpl")
public class BankCardServiceImpl extends BaseServiceImpl<BankCard, Long> implements BankCardService {

	@Resource(name = "bankCardDaoImpl")
	private BankCardDao bankCardDao;

	@Resource(name = "bankCardDaoImpl")
	public void setBaseDao(BankCardDao bankCardDao) {
		super.setBaseDao(bankCardDao);
	}


}