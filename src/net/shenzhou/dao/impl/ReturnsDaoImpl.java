/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.ReturnsDao;
import net.shenzhou.entity.Returns;

import org.springframework.stereotype.Repository;

/**
 * Dao - 退货单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("returnsDaoImpl")
public class ReturnsDaoImpl extends BaseDaoImpl<Returns, Long> implements ReturnsDao {

}