/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import net.shenzhou.dao.MechanismRankDao;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.MechanismRank;

import org.springframework.stereotype.Repository;

/**
 * 机构级别
 * @author wsr
 * 2017-6-24 17:29:57
 *
 */
@Repository("mechanismRankDaoImpl")
public class MechanismRankDaoImpl extends BaseDaoImpl<MechanismRank, Long> implements MechanismRankDao {

	@Override
	public List<MechanismRank> findRoots(Integer count) {
		String jpql = "select mechanismRank from MechanismRank mechanismRank where mechanismRank.parent is null order by mechanismRank.order asc";
		TypedQuery<MechanismRank> query = entityManager.createQuery(jpql, MechanismRank.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public List<MechanismRank> findChildren(MechanismRank mechanismRank,
			Integer count) {
		TypedQuery<MechanismRank> query;
		if (mechanismRank != null) {
			String jpql = "select mechanismRank from MechanismRank mechanismRank where mechanismRank.treePath like :treePath order by mechanismRank.order asc";
			query = entityManager.createQuery(jpql, MechanismRank.class).setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%" + mechanismRank.TREE_PATH_SEPARATOR + mechanismRank.getId() + mechanismRank.TREE_PATH_SEPARATOR + "%");
		} else {
			String jpql = "select mechanismRank from MechanismRank mechanismRank order by mechanismRank.order asc";
			query = entityManager.createQuery(jpql, MechanismRank.class).setFlushMode(FlushModeType.COMMIT);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return sort(query.getResultList(), mechanismRank);
	}

	/**
	 * 排序机构级别
	 * 
	 * @return 机构级别
	 */
	private List<MechanismRank> sort(List<MechanismRank> doctorCategorys, MechanismRank parent) {
		List<MechanismRank> result = new ArrayList<MechanismRank>();
		if (doctorCategorys != null) {
			for (MechanismRank doctorCategory : doctorCategorys) {
				if ((doctorCategory.getParent() != null && doctorCategory.getParent().equals(parent)) || (doctorCategory.getParent() == null && parent == null)) {
					result.add(doctorCategory);
					result.addAll(sort(doctorCategorys, doctorCategory));
				}
			}
		}
		return result;
	}
	
	
}