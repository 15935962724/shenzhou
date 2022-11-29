/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Coupon;

/**
 * Service - 优惠券
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface CouponService extends BaseService<Coupon, Long> {

	/**
	 * 查找优惠券分页
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @param isExchange
	 *            是否允许积分兑换
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 优惠券分页
	 */
	Page<Coupon> findPage(Boolean isEnabled, Boolean isExchange, Boolean hasExpired, Pageable pageable);
	
	/**
	 * 机构端查询优惠券分页
	 * @param query_map
	 * @return
	 */
	Page<Coupon> findPage(Map<String,Object> query_map);
	
	
	/**
	 * 获取机构未过期优惠卷列表
	 * @param query_map
	 * @return
	 */
	List<Coupon> findByMechanism(Map<String,Object> query_map);
	
	
	/**
	 * 获取全部未过期优惠券
	 * @param query_map
	 * @return
	 */
	List<Coupon> findNotOvertimeCoupon();
	
	/**
	 * 获取全部优惠券
	 * @param query_map
	 * @return
	 */
	List<Coupon> getMechanismCoupon(Map<String, Object> query_map);
	
	/**
	 * 获取平台端发布的优惠券(可打包销售的优惠券)
	 */
	List<Coupon> getCoupons(Map<String,Object> query_map);
	
	
	
	

}