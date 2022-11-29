/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.CouponDao;
import net.shenzhou.entity.Coupon;
import net.shenzhou.service.CouponService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 优惠券
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("couponServiceImpl")
public class CouponServiceImpl extends BaseServiceImpl<Coupon, Long> implements CouponService {

	@Resource(name = "couponDaoImpl")
	private CouponDao couponDao;

	@Resource(name = "couponDaoImpl")
	public void setBaseDao(CouponDao couponDao) {
		super.setBaseDao(couponDao);
	}

	@Transactional(readOnly = true)
	public Page<Coupon> findPage(Boolean isEnabled, Boolean isExchange, Boolean hasExpired, Pageable pageable) {
		return couponDao.findPage(isEnabled, isExchange, hasExpired, pageable);
	}

	@Override
	public Page<Coupon> findPage(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return couponDao.findPage(query_map);
	}

	@Override
	public List<Coupon> findByMechanism(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return couponDao.findByMechanism(query_map);
	}

	@Override
	public List<Coupon> findNotOvertimeCoupon() {
		// TODO Auto-generated method stub
		return couponDao.findNotOvertimeCoupon();
	}

	@Override
	public List<Coupon> getMechanismCoupon(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return couponDao.getMechanismCoupon(query_map);
	}

	@Override
	public List<Coupon> getCoupons(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return couponDao.getCoupons(query_map);
	}

}