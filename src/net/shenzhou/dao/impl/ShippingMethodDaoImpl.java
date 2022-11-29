/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.ShippingMethodDao;
import net.shenzhou.entity.ShippingMethod;

import org.springframework.stereotype.Repository;

/**
 * Dao - 配送方式
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("shippingMethodDaoImpl")
public class ShippingMethodDaoImpl extends BaseDaoImpl<ShippingMethod, Long> implements ShippingMethodDao {

}