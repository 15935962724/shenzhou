/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.dao;

import java.util.List;

import net.shenzhou.entity.Balance;
import net.shenzhou.entity.Member;

/**
 * Dao - 余额
 * @author wsr
 * @date 2018-3-19 16:02:46
 */
public interface BalanceDao extends BaseDao<Balance, Long> {

	List<Balance> getBalanceList(Member member);
}