package net.shenzhou.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.shenzhou.dao.ProductOrderLogDao;
import net.shenzhou.entity.ProductOrderLog;
import net.shenzhou.service.ProductOrderLogService;

/**
 * 商品订单日志
 * @author wenlf
 *
 */
@Service("productOrderLogServiceImpl")
public class ProductOrderLogServiceImpl extends BaseServiceImpl<ProductOrderLog, Long> implements ProductOrderLogService{

	
	@Resource(name = "productOrderLogDaoImpl")
	public void setBaseDao(ProductOrderLogDao productOrderLogDao) {
		super.setBaseDao(productOrderLogDao);
	}
}
