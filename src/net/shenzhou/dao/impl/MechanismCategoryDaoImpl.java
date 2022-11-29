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

import net.shenzhou.dao.MechanismCategoryDao;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.ProductCategory;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 机构类型
 * @author wsr
 * 2017-6-24 17:30:56
 *
 */
@Repository("mechanismCategoryDaoImpl")
public class MechanismCategoryDaoImpl extends BaseDaoImpl<MechanismCategory, Long> implements MechanismCategoryDao {

	@Override
	public List<MechanismCategory> findRoots(Integer count) {
		String jpql = "select mechanismCategory from MechanismCategory mechanismCategory where mechanismCategory.parent is null order by mechanismCategory.order asc";
		TypedQuery<MechanismCategory> query = entityManager.createQuery(jpql, MechanismCategory.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}


	public List<MechanismCategory> findParents(MechanismCategory mechanismCategory, Integer count) {
		if (mechanismCategory == null || mechanismCategory.getParent() == null) {
			return Collections.<MechanismCategory> emptyList();
		}
		String jpql = "select mechanismCategory from MechanismCategory mechanismCategory where mechanismCategory.id in (:ids) order by mechanismCategory.grade asc";
		TypedQuery<MechanismCategory> query = entityManager.createQuery(jpql, MechanismCategory.class).setFlushMode(FlushModeType.COMMIT).setParameter("ids", mechanismCategory.getTreePaths());
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<MechanismCategory> findChildren(MechanismCategory mechanismCategory, Integer count) {
		TypedQuery<MechanismCategory> query;
		if (mechanismCategory != null) {
			String jpql = "select mechanismCategory from MechanismCategory mechanismCategory where mechanismCategory.treePath like :treePath order by mechanismCategory.order asc";
			query = entityManager.createQuery(jpql, MechanismCategory.class).setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%" + MechanismCategory.TREE_PATH_SEPARATOR + mechanismCategory.getId() + MechanismCategory.TREE_PATH_SEPARATOR + "%");
		} else {
			String jpql = "select mechanismCategory from MechanismCategory mechanismCategory order by mechanismCategory.order asc";
			query = entityManager.createQuery(jpql, MechanismCategory.class).setFlushMode(FlushModeType.COMMIT);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return sort(query.getResultList(), mechanismCategory);
	}

	/**
	 * 设置treePath、grade并保存
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	@Override
	public void persist(MechanismCategory mechanismCategory) {
		Assert.notNull(mechanismCategory);
		setValue(mechanismCategory);
		super.persist(mechanismCategory);
	}

	/**
	 * 设置treePath、grade并更新
	 * 
	 * @param productCategory
	 *            商品分类
	 * @return 商品分类
	 */
	@Override
	public MechanismCategory merge(MechanismCategory mechanismCategory) {
		Assert.notNull(mechanismCategory);
		setValue(mechanismCategory);
		for (MechanismCategory category : findChildren(mechanismCategory, null)) {
			setValue(category);
		}
		return super.merge(mechanismCategory);
	}

	/**
	 * 清除商品属性值并删除
	 * 
	 * @param productCategory
	 *            商品分类
	 */
//	@Override
//	public void remove(MechanismCategory mechanismCategory) {
//		if (mechanismCategory != null) {
//			StringBuffer jpql = new StringBuffer("update Product product set ");
//			for (int i = 0; i < Mechanism.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
//				String propertyName = Mechanism.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
//				if (i == 0) {
//					jpql.append("mechanism." + propertyName + " = null");
//				} else {
//					jpql.append(", mechanism." + propertyName + " = null");
//				}
//			}
//			jpql.append(" where mechanism.mechanismCategory = :mechanismCategory");
//			entityManager.createQuery(jpql.toString()).setFlushMode(FlushModeType.COMMIT).setParameter("mechanismCategory", mechanismCategory).executeUpdate();
//			super.remove(mechanismCategory);
//		}
//	}

	/**
	 * 排序商品分类
	 * 
	 * @param productCategories
	 *            商品分类
	 * @param parent
	 *            上级商品分类
	 * @return 商品分类
	 */
	private List<MechanismCategory> sort(List<MechanismCategory> mechanismCategorys, MechanismCategory parent) {
		List<MechanismCategory> result = new ArrayList<MechanismCategory>();
		if (mechanismCategorys != null) {
			for (MechanismCategory mechanismCategory : mechanismCategorys) {
				if ((mechanismCategory.getParent() != null && mechanismCategory.getParent().equals(parent)) || (mechanismCategory.getParent() == null && parent == null)) {
					result.add(mechanismCategory);
					result.addAll(sort(mechanismCategorys, mechanismCategory));
				}
			}
		}
		return result;
	}

	/**
	 * 设置值
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	private void setValue(MechanismCategory mechanismCategory) {
		if (mechanismCategory == null) {
			return;
		}
		MechanismCategory parent = mechanismCategory.getParent();
		if (parent != null) {
			mechanismCategory.setTreePath(parent.getTreePath() + parent.getId() + MechanismCategory.TREE_PATH_SEPARATOR);
		} else {
			mechanismCategory.setTreePath(ProductCategory.TREE_PATH_SEPARATOR);
		}
		mechanismCategory.setGrade(mechanismCategory.getTreePaths().size());
	}


}