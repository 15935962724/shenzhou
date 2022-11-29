/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.ProductNotifyDao;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.ProductNotify;
import net.shenzhou.service.MailService;
import net.shenzhou.service.ProductNotifyService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 到货通知
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("productNotifyServiceImpl")
public class ProductNotifyServiceImpl extends BaseServiceImpl<ProductNotify, Long> implements ProductNotifyService {

	@Resource(name = "productNotifyDaoImpl")
	ProductNotifyDao productNotifyDao;
	@Resource(name = "mailServiceImpl")
	MailService mailService;

	@Resource(name = "productNotifyDaoImpl")
	public void setBaseDao(ProductNotifyDao ProductNotifyDao) {
		super.setBaseDao(ProductNotifyDao);
	}

	@Transactional(readOnly = true)
	public boolean exists(Product product, String email) {
		return productNotifyDao.exists(product, email);
	}

	@Transactional(readOnly = true)
	public Page<ProductNotify> findPage(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent, Pageable pageable) {
		return productNotifyDao.findPage(member, isMarketable, isOutOfStock, hasSent, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent) {
		return productNotifyDao.count(member, isMarketable, isOutOfStock, hasSent);
	}

	public int send(Long[] ids) {
		List<ProductNotify> productNotifys = findList(ids);
		for (ProductNotify productNotify : productNotifys) {
			mailService.sendProductNotifyMail(productNotify);
			productNotify.setHasSent(true);
			productNotifyDao.merge(productNotify);
		}
		return productNotifys.size();
	}

}