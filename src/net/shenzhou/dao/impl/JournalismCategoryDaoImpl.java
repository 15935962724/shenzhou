/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import net.shenzhou.dao.JournalismCategoryDao;
import net.shenzhou.entity.JournalismCategory;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 新闻分类
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("journalismCategoryDaoImpl")
public class JournalismCategoryDaoImpl extends BaseDaoImpl<JournalismCategory, Long> implements JournalismCategoryDao {

	public List<JournalismCategory> findRoots(Integer count) {
		String jpql = "select journalismCategory from JournalismCategory journalismCategory where journalismCategory.parent is null order by journalismCategory.order asc";
		TypedQuery<JournalismCategory> query = entityManager.createQuery(jpql, JournalismCategory.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<JournalismCategory> findParents(JournalismCategory journalismCategory, Integer count) {
		if (journalismCategory == null || journalismCategory.getParent() == null) {
			return Collections.<JournalismCategory> emptyList();
		}
		String jpql = "select journalismCategory from JournalismCategory journalismCategory where journalismCategory.id in (:ids) order by journalismCategory.grade asc";
		TypedQuery<JournalismCategory> query = entityManager.createQuery(jpql, JournalismCategory.class).setFlushMode(FlushModeType.COMMIT).setParameter("ids", journalismCategory.getTreePaths());
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<JournalismCategory> findChildren(JournalismCategory journalismCategory, Integer count) {
		TypedQuery<JournalismCategory> query;
		if (journalismCategory != null) {
			String jpql = "select journalismCategory from JournalismCategory journalismCategory where journalismCategory.treePath like :treePath order by journalismCategory.order asc";
			query = entityManager.createQuery(jpql, JournalismCategory.class).setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%" + JournalismCategory.TREE_PATH_SEPARATOR + journalismCategory.getId() + JournalismCategory.TREE_PATH_SEPARATOR + "%");
		} else {
			String jpql = "select journalismCategory from JournalismCategory journalismCategory order by journalismCategory.order asc";
			query = entityManager.createQuery(jpql, JournalismCategory.class).setFlushMode(FlushModeType.COMMIT);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return sort(query.getResultList(), journalismCategory);
	}

	/**
	 * 设置treePath、grade并保存
	 * 
	 * @param journalismCategory
	 *            新闻分类
	 */
	@Override
	public void persist(JournalismCategory journalismCategory) {
		Assert.notNull(journalismCategory);
		setValue(journalismCategory);
		super.persist(journalismCategory);
	}

	/**
	 * 设置treePath、grade并更新
	 * 
	 * @param journalismCategory
	 *            新闻分类
	 * @return 新闻分类
	 */
	@Override
	public JournalismCategory merge(JournalismCategory journalismCategory) {
		Assert.notNull(journalismCategory);
		setValue(journalismCategory);
		for (JournalismCategory category : findChildren(journalismCategory, null)) {
			setValue(category);
		}
		return super.merge(journalismCategory);
	}

	/**
	 * 排序新闻分类
	 * 
	 * @param articleCategories
	 *            新闻分类
	 * @param parent
	 *            上级新闻分类
	 * @return 新闻分类
	 */
	private List<JournalismCategory> sort(List<JournalismCategory> articleCategories, JournalismCategory parent) {
		List<JournalismCategory> result = new ArrayList<JournalismCategory>();
		if (articleCategories != null) {
			for (JournalismCategory journalismCategory : articleCategories) {
				if ((journalismCategory.getParent() != null && journalismCategory.getParent().equals(parent)) || (journalismCategory.getParent() == null && parent == null)) {
					result.add(journalismCategory);
					result.addAll(sort(articleCategories, journalismCategory));
				}
			}
		}
		return result;
	}

	/**
	 * 设置值
	 * 
	 * @param journalismCategory
	 *            新闻分类
	 */
	private void setValue(JournalismCategory journalismCategory) {
		if (journalismCategory == null) {
			return;
		}
		JournalismCategory parent = journalismCategory.getParent();
		if (parent != null) {
			journalismCategory.setTreePath(parent.getTreePath() + parent.getId() + journalismCategory.TREE_PATH_SEPARATOR);
		} else {
			journalismCategory.setTreePath(journalismCategory.TREE_PATH_SEPARATOR);
		}
		journalismCategory.setGrade(journalismCategory.getTreePaths().size());
	}

}