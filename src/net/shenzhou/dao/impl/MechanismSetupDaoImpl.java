/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.MechanismSetupDao;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismSetup;
import net.shenzhou.entity.Member;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author wsr
 *
 */
@Repository("mechanismSetupDaoImpl")
public class MechanismSetupDaoImpl extends BaseDaoImpl<MechanismSetup, Long> implements MechanismSetupDao {

	@Override
	public List<MechanismSetup> getMechanismSetup(Mechanism mechanism) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MechanismSetup> criteriaQuery = criteriaBuilder.createQuery(MechanismSetup.class);
		Root<MechanismSetup> root = criteriaQuery.from(MechanismSetup.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"), mechanism));	
		criteriaQuery.select(root);
		criteriaQuery.where(restrictions);
		
		return super.findList(criteriaQuery, null, null, null, null);
	}

	
	
}