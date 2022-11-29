/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.dao.RefundsDao;
import net.shenzhou.entity.Refunds;
import net.shenzhou.service.RefundsService;

import org.springframework.stereotype.Service;

/**
 * Service - 退款单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("refundsServiceImpl")
public class RefundsServiceImpl extends BaseServiceImpl<Refunds, Long> implements RefundsService {

	@Resource(name = "refundsDaoImpl")
	private RefundsDao refundsDao;
	
	@Resource(name = "refundsDaoImpl")
	public void setBaseDao(RefundsDao refundsDao) {
		super.setBaseDao(refundsDao);
	}

	@Override
	public Page<Refunds> findPage(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return refundsDao.findPage(query_map);
	}

	@Override
	public List<Refunds> downloadList(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return refundsDao.downloadList(query_map);
	}
	
	@Override
	public Map<String, Object> findMap(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return refundsDao.findMap(query_map);
	}

	

}