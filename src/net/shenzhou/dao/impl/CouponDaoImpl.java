/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.CouponDao;
import net.shenzhou.entity.Coupon;
import net.shenzhou.entity.Coupon.CouponType;
import net.shenzhou.entity.Coupon.GrantType;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Repository;

/**
 * Dao - 优惠券
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("couponDaoImpl")
public class CouponDaoImpl extends BaseDaoImpl<Coupon, Long> implements CouponDao {

	public Page<Coupon> findPage(Boolean isEnabled, Boolean isExchange, Boolean hasExpired, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder.createQuery(Coupon.class);
		Root<Coupon> root = criteriaQuery.from(Coupon.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (isEnabled != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), isEnabled));
		}
		if (isExchange != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isExchange"), isExchange));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("endDate").isNotNull(), criteriaBuilder.lessThan(root.<Date> get("endDate"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("endDate").isNull(), criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("endDate"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	
	
	public Page<Coupon> findPage(Map<String, Object> query_map) {
		
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Pageable pageable = (Pageable) query_map.get("pageable");
		GrantType grantType =  GrantType.valueOf(String.valueOf(query_map.get("grantType")));	
		
		Boolean isConduct =  (Boolean) query_map.get("isConduct");//进行中
		Boolean isEnd = (Boolean) query_map.get("isEnd");//已结束
		Boolean isEnabled = (Boolean) query_map.get("isEnabled");//已停止(是否启用)
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder.createQuery(Coupon.class);
		Root<Coupon> root = criteriaQuery.from(Coupon.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		if (mechanism != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		}
		
		if (grantType != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("grantType"),grantType));
		}
		
		//已结束
		if (isEnd) {
			restrictions = criteriaBuilder.and(restrictions, root.get("endDate").isNotNull(), criteriaBuilder.lessThan(root.<Date> get("endDate"), new Date()));
		}
		
		//已停止
		if (!isEnabled) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), isEnabled));
		}
		
		//进行中
		if (isConduct && isEnabled) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("endDate").isNull(), criteriaBuilder.greaterThan(root.<Date> get("endDate"), new Date())));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), isEnabled));
		}
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}


	@Override
	public List<Coupon> findByMechanism(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism)query_map.get("mechanism");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder.createQuery(Coupon.class);
		Root<Coupon> root = criteriaQuery.from(Coupon.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"), mechanism));
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("grantType"), GrantType.mechanism));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("endDate").isNull(), criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("endDate"), new Date())));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}


	@Override
	public List<Coupon> findNotOvertimeCoupon() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder.createQuery(Coupon.class);
		Root<Coupon> root = criteriaQuery.from(Coupon.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("endDate").isNull(), criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("endDate"), new Date())));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}


	@Override
	public List<Coupon> getMechanismCoupon(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism)query_map.get("mechanism");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder.createQuery(Coupon.class);
		Root<Coupon> root = criteriaQuery.from(Coupon.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		if (mechanism!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"), mechanism));
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("grantType"), GrantType.mechanism));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}


	@Override
	public List<Coupon> getCoupons(Map<String, Object> query_map) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder.createQuery(Coupon.class);
		Root<Coupon> root = criteriaQuery.from(Coupon.class);
		
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), true));
//		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isExchange"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("grantType"), GrantType.platform));
//		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThan(root.<Date> get("beginDate"), new Date()));

		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThan(root.<Date> get("beginDate"), new Date()));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThan(root.<Date> get("endDate"), new Date()));
		criteriaQuery.select(root);
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}
	
}