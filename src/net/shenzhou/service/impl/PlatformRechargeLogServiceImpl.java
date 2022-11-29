/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.dao.PlatformRechargeLogDao;
import net.shenzhou.entity.PlatformRechargeLog;
import net.shenzhou.service.PlatformRechargeLogService;

import org.springframework.stereotype.Service;

/**
 * 充值日志
 * 2017-8-1 11:10:26
 * @author wsr
 *
 */		  
@Service("platformRechargeLogServiceImpl")
public class PlatformRechargeLogServiceImpl extends BaseServiceImpl<PlatformRechargeLog, Long> implements PlatformRechargeLogService {

	
	@Resource(name = "platformRechargeLogDaoImpl")
	private PlatformRechargeLogDao platformRechargeLogDao;
	
	@Resource(name = "platformRechargeLogDaoImpl")
	public void setBaseDao(PlatformRechargeLogDao platformRechargeLogDao) {
		super.setBaseDao(platformRechargeLogDao);
	}

	@Override
	public PlatformRechargeLog getPlatformRechargeLog(String outTradeNo,
			String tradeNo) {
		// TODO Auto-generated method stub
		return platformRechargeLogDao.getPlatformRechargeLog(outTradeNo, tradeNo);
	}

	@Override
	public Page<PlatformRechargeLog> findPage(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return platformRechargeLogDao.findPage(query_map);
	}
	

}