/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.BankCardDao;
import net.shenzhou.entity.BankCard;

import org.springframework.stereotype.Repository;

/**
 * Dao - 银行卡
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("bankCardDaoImpl")
public class BankCardDaoImpl extends BaseDaoImpl<BankCard, Long> implements BankCardDao {


}