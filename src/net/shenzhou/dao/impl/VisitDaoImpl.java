/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.dao.VisitDao;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Visit;

import org.springframework.stereotype.Repository;
/**
 * Dao - 回访
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("visitDaoImpl")
public class VisitDaoImpl extends BaseDaoImpl<Visit, Long> implements VisitDao {

	@Override
	public List<Visit> getByMechanism(Mechanism mechanism, Member member,Date startDate,Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Visit> criteriaQuery = criteriaBuilder.createQuery(Visit.class);
		Root<Visit> root = criteriaQuery.from(Visit.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"), member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"), mechanism));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("visitDate"), startDate, endDate));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("visitDate")));
		
		return super.findList(criteriaQuery, null, null, null, null);
	}

}