/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.ProductNotifyDao;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.ProductNotify;

import org.springframework.stereotype.Repository;

/**
 * Dao - 到货通知
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("productNotifyDaoImpl")
public class ProductNotifyDaoImpl extends BaseDaoImpl<ProductNotify, Long> implements ProductNotifyDao {

	public boolean exists(Product product, String email) {
		String jpql = "select count(*) from ProductNotify productNotify where productNotify.product = :product and lower(productNotify.email) = lower(:email) and productNotify.hasSent = false";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("product", product).setParameter("email", email).getSingleResult();
		return count > 0;
	}

	public Page<ProductNotify> findPage(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductNotify> criteriaQuery = criteriaBuilder.createQuery(ProductNotify.class);
		Root<ProductNotify> root = criteriaQuery.from(ProductNotify.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product").get("isMarketable"), isMarketable));
		}
		if (isOutOfStock != null) {
			Path<Integer> stock = root.get("product").get("stock");
			Path<Integer> allocatedStock = root.get("product").get("allocatedStock");
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, allocatedStock)));
			}
		}
		if (hasSent != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("hasSent"), hasSent));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductNotify> criteriaQuery = criteriaBuilder.createQuery(ProductNotify.class);
		Root<ProductNotify> root = criteriaQuery.from(ProductNotify.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product").get("isMarketable"), isMarketable));
		}
		if (isOutOfStock != null) {
			Path<Integer> stock = root.get("product").get("stock");
			Path<Integer> allocatedStock = root.get("product").get("allocatedStock");
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, allocatedStock)));
			}
		}
		if (hasSent != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("hasSent"), hasSent));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

}