/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.RelationshipDao;
import net.shenzhou.entity.Relationship;
import net.shenzhou.service.RelationshipService;

import org.springframework.stereotype.Service;

/**
 * Service - 患者与监护人关系
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("relationshipServiceImpl")
public class RelationshipServiceImpl extends BaseServiceImpl<Relationship, Long> implements RelationshipService {

	@Resource(name = "relationshipDaoImpl")
	private RelationshipDao relationshipDao;

	@Resource(name = "relationshipDaoImpl")
	public void setBaseDao(RelationshipDao relationshipDao) {
		super.setBaseDao(relationshipDao);
	}


}