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
import javax.persistence.criteria.Subquery;

import net.shenzhou.Filter;
import net.shenzhou.Order;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.JournalismDao;
import net.shenzhou.entity.Journalism;
import net.shenzhou.entity.JournalismCategory;
import net.shenzhou.entity.Tag;

import org.springframework.stereotype.Repository;

/**
 * Dao - 新闻
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("journalismDaoImpl")
public class JournalismDaoImpl extends BaseDaoImpl<Journalism, Long> implements JournalismDao {

	public List<Journalism> findList(JournalismCategory journalismCategory, List<Tag> tags, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Journalism> criteriaQuery = criteriaBuilder.createQuery(Journalism.class);
		Root<Journalism> root = criteriaQuery.from(Journalism.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), true));
		if (journalismCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("journalismCategory"), journalismCategory), criteriaBuilder.like(root.get("journalismCategory").<String> get("treePath"), "%" + journalismCategory.TREE_PATH_SEPARATOR + journalismCategory.getId() + journalismCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Journalism> subquery = criteriaQuery.subquery(Journalism.class);
			Root<Journalism> subqueryRoot = subquery.from(Journalism.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root), subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public List<Journalism> findList(JournalismCategory journalismCategory, Date beginDate, Date endDate, Integer first, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Journalism> criteriaQuery = criteriaBuilder.createQuery(Journalism.class);
		Root<Journalism> root = criteriaQuery.from(Journalism.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), true));
		if (journalismCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("journalismCategory"), journalismCategory), criteriaBuilder.like(root.get("journalismCategory").<String> get("treePath"), "%" + journalismCategory.TREE_PATH_SEPARATOR + journalismCategory.getId() + journalismCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findList(criteriaQuery, first, count, null, null);
	}

	public Page<Journalism> findPage(JournalismCategory journalismCategory, List<Tag> tags, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Journalism> criteriaQuery = criteriaBuilder.createQuery(Journalism.class);
		Root<Journalism> root = criteriaQuery.from(Journalism.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), true));
		if (journalismCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("journalismCategory"), journalismCategory), criteriaBuilder.like(root.get("journalismCategory").<String> get("treePath"), "%" + journalismCategory.TREE_PATH_SEPARATOR + journalismCategory.getId() + journalismCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Journalism> subquery = criteriaQuery.subquery(Journalism.class);
			Root<Journalism> subqueryRoot = subquery.from(Journalism.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root), subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findPage(criteriaQuery, pageable);
	}

}