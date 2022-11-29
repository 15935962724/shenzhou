/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.dao.OrderDao;
import net.shenzhou.dao.VisitMessageDao;
import net.shenzhou.entity.VisitMessage;
import net.shenzhou.service.VisitMessageService;

import org.springframework.stereotype.Service;

/**
 * Service - 回访信息
 * @date 2017-8-16 17:20:20
 * @author wsr
 */
@Service("visitMessageServiceImpl")
public class VisitMessageServiceImpl extends BaseServiceImpl<VisitMessage, Long> implements VisitMessageService {

	
	@Resource(name = "visitMessageDaoImpl")
	private VisitMessageDao visitMessageDao;
	
	@Resource(name = "visitMessageDaoImpl")
	public void setBaseDao(VisitMessageDao visitMessageDao) {
		super.setBaseDao(visitMessageDao);
	}

	
	
	
	@Override
	public Page<VisitMessage> findPage(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return visitMessageDao.findPage(query_map);
	}




	@Override
	public Page<VisitMessage> getDoctorPage(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return visitMessageDao.getDoctorPage(query_map);
	}




	@Override
	public Page<VisitMessage> getPage(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return visitMessageDao.getPage(query_map);
	}


}