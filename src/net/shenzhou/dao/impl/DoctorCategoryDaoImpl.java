/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
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
import net.shenzhou.dao.DoctorCategoryDao;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.DoctorCategory;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
/**
 * Dao - 银行卡
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("doctorCategoryDaoImpl")
public class DoctorCategoryDaoImpl extends BaseDaoImpl<DoctorCategory, Long> implements DoctorCategoryDao {

	@Override
	public List<DoctorCategory> findChildren(DoctorCategory doctorCategory,
			Integer count) {
		TypedQuery<DoctorCategory> query;
		if (doctorCategory != null) {
			String jpql = "select doctorCategory from DoctorCategory doctorCategory where doctorCategory.treePath like :treePath order by doctorCategory.order asc";
			query = entityManager.createQuery(jpql, DoctorCategory.class).setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%" + DoctorCategory.TREE_PATH_SEPARATOR + doctorCategory.getId() + DoctorCategory.TREE_PATH_SEPARATOR + "%");
		} else {
			String jpql = "select doctorCategory from DoctorCategory doctorCategory order by doctorCategory.order asc";
			query = entityManager.createQuery(jpql, DoctorCategory.class).setFlushMode(FlushModeType.COMMIT);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return sort(query.getResultList(), doctorCategory);
	}

	/**
	 * 排序医生分类
	 * 
	 * @param productCategories
	 *            医生分类
	 * @param parent
	 *            上级医生分类
	 * @return 医生分类
	 */
	private List<DoctorCategory> sort(List<DoctorCategory> doctorCategorys, DoctorCategory parent) {
		List<DoctorCategory> result = new ArrayList<DoctorCategory>();
		if (doctorCategorys != null) {
			for (DoctorCategory doctorCategory : doctorCategorys) {
				if ((doctorCategory.getParent() != null && doctorCategory.getParent().equals(parent)) || (doctorCategory.getParent() == null && parent == null)) {
					result.add(doctorCategory);
					result.addAll(sort(doctorCategorys, doctorCategory));
				}
			}
		}
		return result;
	}

	@Override
	public Page<DoctorCategory> findPage(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Pageable pageable = (Pageable) query_map.get("pageable");
		if (mechanism == null) {
			return new Page<DoctorCategory>(Collections.<DoctorCategory> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoctorCategory> criteriaQuery = criteriaBuilder.createQuery(DoctorCategory.class);
		Root<DoctorCategory> root = criteriaQuery.from(DoctorCategory.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("mechanism"), mechanism));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<DoctorCategory> findList(Map<String, Object> query_map) {
//		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
//		Pageable pageable = (Pageable) query_map.get("pageable");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoctorCategory> criteriaQuery = criteriaBuilder.createQuery(DoctorCategory.class);
		Root<DoctorCategory> root = criteriaQuery.from(DoctorCategory.class);
		criteriaQuery.select(root);
		Predicate predicate = criteriaBuilder.conjunction();
		predicate = criteriaBuilder.equal(root.get("isDeleted"), false);
		predicate = criteriaBuilder.and(criteriaBuilder.isNull(root.get("mechanism")));
		criteriaQuery.where(predicate);
		return super.findList(criteriaQuery, null, null, null, null);
	}
	
	
	
	/**
	 * 设置treePath、grade并保存
	 * 
	 * @param doctorCategory
	 *            商品分类
	 */
	@Override
	public void persist(DoctorCategory doctorCategory) {
		Assert.notNull(doctorCategory);
		setValue(doctorCategory);
		super.persist(doctorCategory);
	}

	/**
	 * 设置treePath、grade并更新
	 * 
	 * @param doctorCategory
	 *            商品分类
	 * @return 商品分类
	 */
	@Override
	public DoctorCategory merge(DoctorCategory doctorCategory) {
		Assert.notNull(doctorCategory);
		setValue(doctorCategory);
		for (DoctorCategory category : findChildren(doctorCategory, null)) {
			setValue(category);
		}
		return super.merge(doctorCategory);
	}

//	/**
//	 * 清除商品属性值并删除
//	 * 
//	 * @param doctorCategory
//	 *            商品分类
//	 */
//	@Override
//	public void remove(DoctorCategory doctorCategory) {
//		if (doctorCategory != null) {
//			StringBuffer jpql = new StringBuffer("update Product product set ");
//			for (int i = 0; i < Product.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
//				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
//				if (i == 0) {
//					jpql.append("product." + propertyName + " = null");
//				} else {
//					jpql.append(", product." + propertyName + " = null");
//				}
//			}
//			jpql.append(" where product.doctorCategory = :doctorCategory");
//			entityManager.createQuery(jpql.toString()).setFlushMode(FlushModeType.COMMIT).setParameter("doctorCategory", doctorCategory).executeUpdate();
//			super.remove(doctorCategory);
//		}
//	}
	
	/**
	 * 设置值
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	private void setValue(DoctorCategory doctorCategory) {
		if (doctorCategory == null) {
			return;
		}
		DoctorCategory parent = doctorCategory.getParent();
		if (parent != null) {
			doctorCategory.setTreePath(parent.getTreePath() + parent.getId() + DoctorCategory.TREE_PATH_SEPARATOR);
		} else {
			doctorCategory.setTreePath(DoctorCategory.TREE_PATH_SEPARATOR);
		}
		doctorCategory.setGrade(doctorCategory.getTreePaths().size());
	}

	@Override
	public List<DoctorCategory> find() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoctorCategory> criteriaQuery = criteriaBuilder.createQuery(DoctorCategory.class);
		Root<DoctorCategory> root = criteriaQuery.from(DoctorCategory.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"),  false));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	
	
	
}