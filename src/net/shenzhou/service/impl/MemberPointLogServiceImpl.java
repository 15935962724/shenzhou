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
import net.shenzhou.dao.MemberPointLogDao;
import net.shenzhou.entity.MemberPointLog;
import net.shenzhou.service.MemberPointLogService;

import org.springframework.stereotype.Service;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("memberPointLogServiceImpl")
public class MemberPointLogServiceImpl extends BaseServiceImpl<MemberPointLog, Long> implements MemberPointLogService {

	@Resource(name = "memberPointLogDaoImpl")
	private MemberPointLogDao memberPointLogDao;

	@Resource(name = "memberPointLogDaoImpl")
	public void setBaseDao(MemberPointLogDao memberPointLogDao) {
		super.setBaseDao(memberPointLogDao);
	}

	@Override
	public Page<MemberPointLog> findPage(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return memberPointLogDao.findPage(query_map);
	}

	@Override
	public List<MemberPointLog> findMemberAcquire(Map<String, Object> query_map) {
		
		return memberPointLogDao.findMemberAcquire(query_map);
	}


}