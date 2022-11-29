/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.CouponCodeDao;
import net.shenzhou.dao.MemberDao;
import net.shenzhou.dao.OrderDao;
import net.shenzhou.entity.Coupon;
import net.shenzhou.entity.Coupon.CouponType;
import net.shenzhou.entity.Coupon.GrantType;
import net.shenzhou.entity.CouponCode;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.service.CouponCodeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 优惠码
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("couponCodeServiceImpl")
public class CouponCodeServiceImpl extends BaseServiceImpl<CouponCode, Long> implements CouponCodeService {

	@Resource(name = "couponCodeDaoImpl")
	private CouponCodeDao couponCodeDao;
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;
	@Resource(name = "couponCodeDaoImpl")
	public void setBaseDao(CouponCodeDao couponCodeDao) {
		super.setBaseDao(couponCodeDao);
	}

	@Transactional(readOnly = true)
	public boolean codeExists(String code) {
		return couponCodeDao.codeExists(code);
	}

	@Transactional(readOnly = true)
	public CouponCode findByCode(String code) {
		return couponCodeDao.findByCode(code);
	}

	public CouponCode build(Coupon coupon, Member member) {
		return couponCodeDao.build(coupon, member);
	}

	public List<CouponCode> build(Coupon coupon, Member member, Integer count) {
		return couponCodeDao.build(coupon, member, count);
	}

	public CouponCode exchange(Coupon coupon, Member member) {
		Assert.notNull(coupon);
		Assert.notNull(member);

		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
		member.setPoint(member.getPoint() - coupon.getPoint());
		memberDao.merge(member);

		return couponCodeDao.build(coupon, member);
	}

	@Transactional(readOnly = true)
	public Page<CouponCode> findPage(Member member, Pageable pageable) {
		return couponCodeDao.findPage(member, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed) {
		return couponCodeDao.count(coupon, member, hasBegun, hasExpired, isUsed);
	}

	@Override
	public Page<CouponCode> findPage(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return couponCodeDao.findPage(query_map);
	}

	@Override
	public List<CouponCode> findUnusedByMember(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return couponCodeDao.findUnusedByMember(query_map);
	}

	@Override
	public List<CouponCode> memberUsableCoupon(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism)query_map.get("mechanism");
		BigDecimal price = (BigDecimal)query_map.get("price");
		Member member = (Member)query_map.get("member");
		List<Order> orders = orderDao.getMemberOrder(member);
		List<Order> mechanism_orders = orderDao.getMemberOrder(member,mechanism);
		
		List<CouponCode> couponCodes = couponCodeDao.memberUsableCoupon(query_map);
		
		
		List<CouponCode> memberCouponCode = new ArrayList<CouponCode>();
		
		for(CouponCode couponCode : couponCodes){
			//平台端发放的优惠券,判断包不包括该机构,首单等
			if(couponCode.getCoupon().getGrantType().equals(GrantType.platform)){
				if(couponCode.getCoupon().getCouponType().equals(CouponType.firstorder)){
					if(orders.size()<=0){
						if(couponCode.getCoupon().getMinConsumptionPrice().compareTo(price)==-1||couponCode.getCoupon().getMinConsumptionPrice().compareTo(price)==0){
							memberCouponCode.add(couponCode);
						}
					}
				}
				if(couponCode.getCoupon().getCouponType().equals(CouponType.fullcut)){
					if(couponCode.getCoupon().getMinConsumptionPrice().compareTo(price)==-1||couponCode.getCoupon().getMinConsumptionPrice().compareTo(price)==0){
						memberCouponCode.add(couponCode);
					}
				}
			}
			//机构端法防的优惠券
			if(couponCode.getCoupon().getGrantType().equals(GrantType.mechanism)){
					if(couponCode.getCoupon().getCouponType().equals(CouponType.firstorder)){
						if(mechanism_orders.size()<=0){
							if(couponCode.getCoupon().getMinConsumptionPrice().compareTo(price)==-1||couponCode.getCoupon().getMinConsumptionPrice().compareTo(price)==0){
								memberCouponCode.add(couponCode);
							}
						}
					}
					if(couponCode.getCoupon().getCouponType().equals(CouponType.fullcut)){
						if(couponCode.getCoupon().getMinConsumptionPrice().compareTo(price)==-1||couponCode.getCoupon().getMinConsumptionPrice().compareTo(price)==0){
							memberCouponCode.add(couponCode);
						}
					}
			}
		}
		
		return couponCodes;
	}

	@Override
	public List<CouponCode> getMemberCouponCodes(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		return couponCodeDao.getMemberCouponCodes(query_map);
	}

}