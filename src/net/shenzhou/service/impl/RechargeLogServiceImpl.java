/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.dao.RechargeLogDao;
import net.shenzhou.entity.RechargeLog;
import net.shenzhou.service.RechargeLogService;

import org.springframework.stereotype.Service;

/**
 * 充值日志
 * 2017-8-1 11:10:26
 * @author wsr
 *
 */
@Service("rechargeLogServiceImpl")
public class RechargeLogServiceImpl extends BaseServiceImpl<RechargeLog, Long> implements RechargeLogService {

	@Resource(name = "rechargeLogDaoImpl")
	public void setBaseDao(RechargeLogDao rechargelogDao) {
		super.setBaseDao(rechargelogDao);
	}

	@Resource(name = "rechargeLogDaoImpl")
	private RechargeLogDao rechargelogDao;
	
	
	@Override
	public Page<RechargeLog> getMechanismRechargeLogList(
			Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return rechargelogDao.getMechanismRechargeLogList(query_map);
	}


	@Override
	public BigDecimal sumRecharge(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return rechargelogDao.sumRecharge(query_map);
	}


	@Override
	public List<RechargeLog> downloadList(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return rechargelogDao.downloadList(query_map);
	}


	@Override
	public Page<RechargeLog> getRechargeLogs(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return rechargelogDao.getRechargeLogs(query_map);
	}


	@Override
	public BigDecimal getSumRecharge(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return rechargelogDao.getSumRecharge(query_map);
	}

}