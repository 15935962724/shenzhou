/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.CouponCodeDao;
import net.shenzhou.entity.Coupon;
import net.shenzhou.entity.Coupon.CouponType;
import net.shenzhou.entity.CouponCode;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 优惠码
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("couponCodeDaoImpl")
public class CouponCodeDaoImpl extends BaseDaoImpl<CouponCode, Long> implements CouponCodeDao {

	public boolean codeExists(String code) {
		if (code == null) {
			return false;
		}
		String jpql = "select count(*) from CouponCode couponCode where lower(couponCode.code) = lower(:code)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("code", code).getSingleResult();
		return count > 0;
	}

	public CouponCode findByCode(String code) {
		if (code == null) {
			return null;
		}
		try {
			String jpql = "select couponCode from CouponCode couponCode where lower(couponCode.code) = lower(:code)";
			return entityManager.createQuery(jpql, CouponCode.class).setFlushMode(FlushModeType.COMMIT).setParameter("code", code).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public CouponCode build(Coupon coupon, Member member) {
			Assert.notNull(coupon);
			CouponCode couponCode = new CouponCode();
			String uuid = UUID.randomUUID().toString().toUpperCase();
			couponCode.setCode(coupon.getPrefix() + uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24));
			couponCode.setIsUsed(false);
			couponCode.setCoupon(coupon);
			couponCode.setMember(member);
			couponCode.setBeginDate(coupon.getBeginDate());
			couponCode.setEndDate(coupon.getEndDate());
			super.persist(couponCode);
			return couponCode;
	}

	public List<CouponCode> build(Coupon coupon, Member member, Integer count) {
		Assert.notNull(coupon);
		Assert.notNull(count);
		List<CouponCode> couponCodes = new ArrayList<CouponCode>();
		for (int i = 0; i < count; i++) {
			CouponCode couponCode = build(coupon, member);
			couponCodes.add(couponCode);
			if (i % 20 == 0) {
				super.flush();
				super.clear();
			}
		}
		return couponCodes;
	}

	public Page<CouponCode> findPage(Member member, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CouponCode> criteriaQuery = criteriaBuilder.createQuery(CouponCode.class);
		Root<CouponCode> root = criteriaQuery.from(CouponCode.class);
		criteriaQuery.select(root);
		if (member != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CouponCode> criteriaQuery = criteriaBuilder.createQuery(CouponCode.class);
		Root<CouponCode> root = criteriaQuery.from(CouponCode.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Path<Coupon> couponPath = root.get("coupon");
		if (coupon != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(couponPath, coupon));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (hasBegun != null) {
			if (hasBegun) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(couponPath.get("beginDate").isNull(), criteriaBuilder.lessThanOrEqualTo(couponPath.<Date> get("beginDate"), new Date())));
			} else {
				restrictions = criteriaBuilder.and(restrictions, couponPath.get("beginDate").isNotNull(), criteriaBuilder.greaterThan(couponPath.<Date> get("beginDate"), new Date()));
			}
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, couponPath.get("endDate").isNotNull(), criteriaBuilder.lessThan(couponPath.<Date> get("endDate"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(couponPath.get("endDate").isNull(), criteriaBuilder.greaterThanOrEqualTo(couponPath.<Date> get("endDate"), new Date())));
			}
		}
		if (isUsed != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isUsed"), isUsed));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	@Override
	public Page<CouponCode> findPage(Map<String, Object> query_map) {
		
		Pageable pageable =  (Pageable) query_map.get("pageable");
		Coupon coupon =  (Coupon) query_map.get("coupon");
		Object isUsed =   query_map.get("isUsed");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CouponCode> criteriaQuery = criteriaBuilder.createQuery(CouponCode.class);
		Root<CouponCode> root = criteriaQuery.from(CouponCode.class);
		criteriaQuery.select(root);
		Predicate predicate = criteriaBuilder.conjunction();
		
		if (coupon != null) {
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("coupon"), coupon));
		}else{
			return new Page<CouponCode>();
		}
		
		if (isUsed!=null) {
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("isUsed"), isUsed));
		}
		criteriaQuery.where(predicate);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<CouponCode> findUnusedByMember(Map<String, Object> query_map) {
		Member member = (Member)query_map.get("member");
		Boolean hasExpired =(Boolean) query_map.get("hasExpired");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CouponCode> criteriaQuery = criteriaBuilder.createQuery(CouponCode.class);
		Root<CouponCode> root = criteriaQuery.from(CouponCode.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"), member));
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isUsed"), false));
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.lessThan(root.<Date> get("endDate"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("endDate"), new Date()));
			}
		}
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
		
	}

	@Override
	public List<CouponCode> memberUsableCoupon(Map<String, Object> query_map) {
		Member member = (Member) query_map.get("member");
		Mechanism mechanism = (Mechanism)query_map.get("mechanism");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CouponCode> criteriaQuery = criteriaBuilder.createQuery(CouponCode.class);
		Root<CouponCode> root = criteriaQuery.from(CouponCode.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"), member));
//		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.<Coupon>get("coupon").<Mechanism>get("mechanism"), mechanism));
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isUsed"), false));
//		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("beginDate").isNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("beginDate"), new Date())));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("endDate"), new Date()));
//		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("expired"), false));
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<CouponCode> getMemberCouponCodes(Map<String, Object> query_map) {
		Member member = (Member) query_map.get("member");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CouponCode> criteriaQuery = criteriaBuilder.createQuery(CouponCode.class);
		Root<CouponCode> root = criteriaQuery.from(CouponCode.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		if (member!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"), member));	
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
		
	}

	

}