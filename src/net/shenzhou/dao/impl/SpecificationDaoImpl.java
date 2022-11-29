/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.SpecificationDao;
import net.shenzhou.entity.Specification;

import org.springframework.stereotype.Repository;

/**
 * Dao - 规格
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("specificationDaoImpl")
public class SpecificationDaoImpl extends BaseDaoImpl<Specification, Long> implements SpecificationDao {

}