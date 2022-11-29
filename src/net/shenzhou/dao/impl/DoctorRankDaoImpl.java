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
import net.shenzhou.dao.DoctorRankDao;
import net.shenzhou.entity.DoctorRank;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.DoctorRank;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 专家级别
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("doctorRankDaoImpl")
public class DoctorRankDaoImpl extends BaseDaoImpl<DoctorRank, Long> implements DoctorRankDao {

	



	public List<DoctorRank> findRoots(Integer count) {
		String jpql = "select doctorRank from DoctorRank doctorRank where doctorRank.parent is null order by doctorRank.order asc";
		TypedQuery<DoctorRank> query = entityManager.createQuery(jpql, DoctorRank.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<DoctorRank> findParents(DoctorRank doctorRank, Integer count) {
		if (doctorRank == null || doctorRank.getParent() == null) {
			return Collections.<DoctorRank> emptyList();
		}
		String jpql = "select doctorRank from DoctorRank doctorRank where doctorRank.id in (:ids) order by doctorRank.grade asc";
		TypedQuery<DoctorRank> query = entityManager.createQuery(jpql, DoctorRank.class).setFlushMode(FlushModeType.COMMIT).setParameter("ids", doctorRank.getTreePaths());
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<DoctorRank> findChildren(DoctorRank doctorRank, Integer count) {
		TypedQuery<DoctorRank> query;
		if (doctorRank != null) {
			String jpql = "select doctorRank from DoctorRank doctorRank where doctorRank.treePath like :treePath order by doctorRank.order asc";
			query = entityManager.createQuery(jpql, DoctorRank.class).setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%" + doctorRank.TREE_PATH_SEPARATOR + doctorRank.getId() + doctorRank.TREE_PATH_SEPARATOR + "%");
		} else {
			String jpql = "select doctorRank from DoctorRank doctorRank order by doctorRank.order asc";
			query = entityManager.createQuery(jpql, DoctorRank.class).setFlushMode(FlushModeType.COMMIT);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return sort(query.getResultList(), doctorRank);
	}

	/**
	 * 设置treePath、grade并保存
	 * 
	 * @param doctorRank
	 *            专家级别
	 */
	@Override
	public void persist(DoctorRank doctorRank) {
		Assert.notNull(doctorRank);
		setValue(doctorRank);
		super.persist(doctorRank);
	}

	/**
	 * 设置treePath、grade并更新
	 * 
	 * @param doctorRank
	 *            专家级别
	 * @return 专家级别
	 */
	@Override
	public DoctorRank merge(DoctorRank doctorRank) {
		Assert.notNull(doctorRank);
		setValue(doctorRank);
		for (DoctorRank category : findChildren(doctorRank, null)) {
			setValue(category);
		}
		return super.merge(doctorRank);
	}

//	/**
//	 * 清除项目属性值并删除
//	 * 
//	 * @param doctorRank
//	 *            专家级别
//	 */
//	@Override
//	public void remove(DoctorRank doctorRank) {
//		if (doctorRank != null) {
//			StringBuffer jpql = new StringBuffer("update Project project set ");
//			for (int i = 0; i < Project.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
//				String propertyName = Project.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
//				if (i == 0) {
//					jpql.append("project." + propertyName + " = null");
//				} else {
//					jpql.append(", project." + propertyName + " = null");
//				}
//			}
//			jpql.append(" where project.doctorRank = :doctorRank");
//			entityManager.createQuery(jpql.toString()).setFlushMode(FlushModeType.COMMIT).setParameter("doctorRank", doctorRank).executeUpdate();
//			super.remove(doctorRank);
//		}
//	}

	/**
	 * 排序专家级别
	 * 
	 * @param serverProjectCategories
	 *            专家级别
	 * @param parent
	 *            上级专家级别
	 * @return 专家级别
	 */
	private List<DoctorRank> sort(List<DoctorRank> serverProjectCategories, DoctorRank parent) {
		List<DoctorRank> result = new ArrayList<DoctorRank>();
		if (serverProjectCategories != null) {
			for (DoctorRank doctorRank : serverProjectCategories) {
				if ((doctorRank.getParent() != null && doctorRank.getParent().equals(parent)) || (doctorRank.getParent() == null && parent == null)) {
					result.add(doctorRank);
					result.addAll(sort(serverProjectCategories, doctorRank));
				}
			}
		}
		return result;
	}

	/**
	 * 设置值
	 * 
	 * @param doctorRank
	 *            专家级别
	 */
	private void setValue(DoctorRank doctorRank) {
		if (doctorRank == null) {
			return;
		}
		DoctorRank parent = doctorRank.getParent();
		if (parent != null) {
			doctorRank.setTreePath(parent.getTreePath() + parent.getId() + doctorRank.TREE_PATH_SEPARATOR);
		} else {
			doctorRank.setTreePath(doctorRank.TREE_PATH_SEPARATOR);
		}
		doctorRank.setGrade(doctorRank.getTreePaths().size());
	}


	
	
}