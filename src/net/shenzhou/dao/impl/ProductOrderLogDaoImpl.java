package net.shenzhou.dao.impl;

import org.springframework.stereotype.Repository;

import net.shenzhou.dao.ProductOrderLogDao;
import net.shenzhou.entity.ProductOrderLog;

@Repository("productOrderLogDaoImpl")
public class ProductOrderLogDaoImpl extends BaseDaoImpl<ProductOrderLog, Long> implements ProductOrderLogDao{

}
