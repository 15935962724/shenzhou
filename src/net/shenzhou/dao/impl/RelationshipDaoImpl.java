/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.RelationshipDao;
import net.shenzhou.entity.Relationship;

import org.springframework.stereotype.Repository;

/**
 * Dao - 患者与监护人关系
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("relationshipDaoImpl")
public class RelationshipDaoImpl extends BaseDaoImpl<Relationship, Long> implements RelationshipDao {

}