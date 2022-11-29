/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.ServerProjectCategoryDao;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.ProductCategory;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.ServerProjectCategory;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 服务项目
 * @author wsr
 *
 */
@Repository("serverProjectCategoryDaoImpl")
public class ServerProjectCategoryDaoImpl extends BaseDaoImpl<ServerProjectCategory, Long> implements ServerProjectCategoryDao {


	public List<ServerProjectCategory> findRoots(Integer count) {
		String jpql = "select serverProjectCategory from ServerProjectCategory serverProjectCategory where serverProjectCategory.parent is null order by serverProjectCategory.order asc";
		TypedQuery<ServerProjectCategory> query = entityManager.createQuery(jpql, ServerProjectCategory.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ServerProjectCategory> findParents(ServerProjectCategory serverProjectCategory, Integer count) {
		if (serverProjectCategory == null || serverProjectCategory.getParent() == null) {
			return Collections.<ServerProjectCategory> emptyList();
		}
		String jpql = "select serverProjectCategory from ServerProjectCategory serverProjectCategory where serverProjectCategory.id in (:ids) order by serverProjectCategory.grade asc";
		TypedQuery<ServerProjectCategory> query = entityManager.createQuery(jpql, ServerProjectCategory.class).setFlushMode(FlushModeType.COMMIT).setParameter("ids", serverProjectCategory.getTreePaths());
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ServerProjectCategory> findChildren(ServerProjectCategory serverProjectCategory, Integer count) {
		TypedQuery<ServerProjectCategory> query;
		if (serverProjectCategory != null) {
			String jpql = "select serverProjectCategory from ServerProjectCategory serverProjectCategory where serverProjectCategory.mechanism is Null and serverProjectCategory.treePath like :treePath order by serverProjectCategory.order asc";
			query = entityManager.createQuery(jpql, ServerProjectCategory.class).setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%" + serverProjectCategory.TREE_PATH_SEPARATOR + serverProjectCategory.getId() + serverProjectCategory.TREE_PATH_SEPARATOR + "%");
		} else {
			String jpql = "select serverProjectCategory from ServerProjectCategory serverProjectCategory where serverProjectCategory.mechanism is Null order by serverProjectCategory.order asc";
			query = entityManager.createQuery(jpql, ServerProjectCategory.class).setFlushMode(FlushModeType.COMMIT);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return sort(query.getResultList(), serverProjectCategory);
	}

	/**
	 * 设置treePath、grade并保存
	 * 
	 * @param serverProjectCategory
	 *            项目分类
	 */
	@Override
	public void persist(ServerProjectCategory serverProjectCategory) {
		Assert.notNull(serverProjectCategory);
		setValue(serverProjectCategory);
		super.persist(serverProjectCategory);
	}

	/**
	 * 设置treePath、grade并更新
	 * 
	 * @param serverProjectCategory
	 *            项目分类
	 * @return 项目分类
	 */
	@Override
	public ServerProjectCategory merge(ServerProjectCategory serverProjectCategory) {
		Assert.notNull(serverProjectCategory);
		setValue(serverProjectCategory);
		for (ServerProjectCategory category : findChildren(serverProjectCategory, null)) {
			setValue(category);
		}
		return super.merge(serverProjectCategory);
	}

//	/**
//	 * 清除项目属性值并删除
//	 * 
//	 * @param serverProjectCategory
//	 *            项目分类
//	 */
//	@Override
//	public void remove(ServerProjectCategory serverProjectCategory) {
//		if (serverProjectCategory != null) {
//			StringBuffer jpql = new StringBuffer("update Project project set ");
//			for (int i = 0; i < Project.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
//				String propertyName = Project.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
//				if (i == 0) {
//					jpql.append("project." + propertyName + " = null");
//				} else {
//					jpql.append(", project." + propertyName + " = null");
//				}
//			}
//			jpql.append(" where project.serverProjectCategory = :serverProjectCategory");
//			entityManager.createQuery(jpql.toString()).setFlushMode(FlushModeType.COMMIT).setParameter("serverProjectCategory", serverProjectCategory).executeUpdate();
//			super.remove(serverProjectCategory);
//		}
//	}

	/**
	 * 排序项目分类
	 * 
	 * @param serverProjectCategories
	 *            项目分类
	 * @param parent
	 *            上级项目分类
	 * @return 项目分类
	 */
	private List<ServerProjectCategory> sort(List<ServerProjectCategory> serverProjectCategories, ServerProjectCategory parent) {
		List<ServerProjectCategory> result = new ArrayList<ServerProjectCategory>();
		if (serverProjectCategories != null) {
			for (ServerProjectCategory serverProjectCategory : serverProjectCategories) {
				if ((serverProjectCategory.getParent() != null && serverProjectCategory.getParent().equals(parent)) || (serverProjectCategory.getParent() == null && parent == null)) {
					result.add(serverProjectCategory);
					result.addAll(sort(serverProjectCategories, serverProjectCategory));
				}
			}
		}
		return result;
	}

	/**
	 * 设置值
	 * 
	 * @param serverProjectCategory
	 *            项目分类
	 */
	private void setValue(ServerProjectCategory serverProjectCategory) {
		if (serverProjectCategory == null) {
			return;
		}
		ServerProjectCategory parent = serverProjectCategory.getParent();
		if (parent != null) {
			serverProjectCategory.setTreePath(parent.getTreePath() + parent.getId() + serverProjectCategory.TREE_PATH_SEPARATOR);
		} else {
			serverProjectCategory.setTreePath(serverProjectCategory.TREE_PATH_SEPARATOR);
		}
		serverProjectCategory.setGrade(serverProjectCategory.getTreePaths().size());
	}

	@Override
	public List<ServerProjectCategory> getServerProjectCategory(
			Mechanism mechanism) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ServerProjectCategory> criteriaQuery = criteriaBuilder.createQuery(ServerProjectCategory.class);
		Root<ServerProjectCategory> root = criteriaQuery.from(ServerProjectCategory.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		if (mechanism != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("order")));
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<ServerProjectCategory> getServerProjectCategory() {
		String jpql = "select serverProjectCategory from ServerProjectCategory serverProjectCategory where serverProjectCategory.mechanism is null order by serverProjectCategory.order asc";
		TypedQuery<ServerProjectCategory> query = entityManager.createQuery(jpql, ServerProjectCategory.class).setFlushMode(FlushModeType.COMMIT);
		return query.getResultList();
	}

	@Override
	public Page<ServerProjectCategory> getServerProjectCategory(
			Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism"); 
		Pageable pageable = (Pageable) query_map.get("pageable");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ServerProjectCategory> criteriaQuery = criteriaBuilder.createQuery(ServerProjectCategory.class);
		Root<ServerProjectCategory> root = criteriaQuery.from(ServerProjectCategory.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		if (mechanism != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("order")));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<ServerProjectCategory> serverProjectCategoryList() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ServerProjectCategory> criteriaQuery = criteriaBuilder.createQuery(ServerProjectCategory.class);
		Root<ServerProjectCategory> root = criteriaQuery.from(ServerProjectCategory.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("order")));
		return super.findList(criteriaQuery, null, null, null, null);
	}

}