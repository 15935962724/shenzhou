/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.BalanceDao;
import net.shenzhou.entity.Balance;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Member;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

/**
 * Dao -余额
 * @author wsr
 * @date 2018-3-19 16:02:46
 */
@Repository("balanceDaoImpl")
public class BalanceDaoImpl extends BaseDaoImpl<Balance, Long> implements BalanceDao {

	@Override
	public List<Balance> getBalanceList(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Balance> criteriaQuery = criteriaBuilder.createQuery(Balance.class);
		Root<Balance> root = criteriaQuery.from(Balance.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),  member));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null,null,null,null);
	}

}