/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Coupon;
import net.shenzhou.entity.CouponCode;
import net.shenzhou.entity.Member;

/**
 * Dao - 优惠码
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface CouponCodeDao extends BaseDao<CouponCode, Long> {

	/**
	 * 判断优惠码是否存在
	 * 
	 * @param code
	 *            号码(忽略大小写)
	 * @return 优惠码是否存在
	 */
	boolean codeExists(String code);

	/**
	 * 根据号码查找优惠码
	 * 
	 * @param code
	 *            号码(忽略大小写)
	 * @return 优惠码，若不存在则返回null
	 */
	CouponCode findByCode(String code);

	/**
	 * 生成优惠码
	 * 
	 * @param coupon
	 *            优惠券
	 * @param member
	 *            会员
	 * @return 优惠码
	 */
	CouponCode build(Coupon coupon, Member member);

	/**
	 * 生成优惠码
	 * 
	 * @param coupon
	 *            优惠券
	 * @param member
	 *            会员
	 * @param count
	 *            数量
	 * @return 优惠码
	 */
	List<CouponCode> build(Coupon coupon, Member member, Integer count);

	/**
	 * 查找优惠码分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 优惠码分页
	 */
	Page<CouponCode> findPage(Member member, Pageable pageable);

	/**
	 * 查找优惠码数量
	 * 
	 * @param coupon
	 *            优惠券
	 * @param member
	 *            会员
	 * @param hasBegun
	 *            是否已开始
	 * @param hasExpired
	 *            是否已过期
	 * @param isUsed
	 *            是否已使用
	 * @return 优惠码数量
	 */
	Long count(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed);
	
	
	/**
	 * 优惠券明细(分页)
	 * @param query_map
	 * @return
	 */
	Page<CouponCode> findPage(Map<String,Object> query_map);
	
	
	/**
	 * 获取患者未使用优惠卷列表
	 * @param query_map
	 * @return
	 */
	List<CouponCode> findUnusedByMember(Map<String, Object> query_map);
	
	
	
	
	/**
	 * 用户可用优惠券列表(下单)
	 * @param query_map
	 * @return
	 */
	List<CouponCode> memberUsableCoupon(Map<String, Object> query_map);
	
	
	/**
	 * 获取该用户所有的优惠码
	 * @param query_map
	 * @return
	 */
	List<CouponCode> getMemberCouponCodes(Map<String, Object> query_map);
	
}