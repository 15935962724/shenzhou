/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.ExpertDao;
import net.shenzhou.entity.Expert;
import net.shenzhou.service.ExpertService;

import org.springframework.stereotype.Service;

/**
 * Service -专家
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("expertServiceImpl")
public class ExpertServiceImpl extends BaseServiceImpl<Expert, Long> implements ExpertService {

	@Resource(name = "expertDaoImpl")
	public void setBaseDao(ExpertDao expertDao) {
		super.setBaseDao(expertDao);
	}


}