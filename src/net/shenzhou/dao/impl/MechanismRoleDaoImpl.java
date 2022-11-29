/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.Collections;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.MechanismRoleDao;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismRole;

import org.springframework.stereotype.Repository;

/**
 * Dao - 角色
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("mechanismRoleDaoImpl")
public class MechanismRoleDaoImpl extends BaseDaoImpl<MechanismRole, Long> implements MechanismRoleDao {

	
	
	@Override
	public Page<MechanismRole> findPage(Map<String, Object> query_map) {
		
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Pageable pageable = (Pageable) query_map.get("pageable");
		if (mechanism == null) {
			return new Page<MechanismRole>(Collections.<MechanismRole> emptyList(), 0,pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MechanismRole> criteriaQuery = criteriaBuilder.createQuery(MechanismRole.class);
		Root<MechanismRole> root = criteriaQuery.from(MechanismRole.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("mechanism"), mechanism));
		return super.findPage(criteriaQuery, pageable);
	}

}