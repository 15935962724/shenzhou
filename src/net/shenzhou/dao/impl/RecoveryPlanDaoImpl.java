/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.dao.RecoveryPlanDao;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.RecoveryPlan;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Repository;
/**
 * Dao - 康护计划
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("recoveryPlanDaoImpl")
public class RecoveryPlanDaoImpl extends BaseDaoImpl<RecoveryPlan, Long> implements RecoveryPlanDao {

	@Override
	public RecoveryPlan findRecentlyRecoveryPlan(AssessReport assessReport) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RecoveryPlan> criteriaQuery = criteriaBuilder.createQuery(RecoveryPlan.class);
		Root<RecoveryPlan> root = criteriaQuery.from(RecoveryPlan.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (assessReport != null) {
			restrictions=criteriaBuilder.and(criteriaBuilder.in(root.get("assessReport")).value(assessReport));
		}
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		criteriaQuery.select(root);
		criteriaQuery.where(restrictions);
		List<RecoveryPlan> list = super.findList(criteriaQuery, null, null, null, null);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<RecoveryPlan> findRecoveryPlanList(Map<String, Object> query_map) {
		
		Member patientMember = (Member) query_map.get("patientMember");
		Date createDate = (Date) query_map.get("createDate");
		Date endDate = (Date) query_map.get("endDate");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RecoveryPlan> criteriaQuery = criteriaBuilder.createQuery(RecoveryPlan.class);
		Root<RecoveryPlan> root = criteriaQuery.from(RecoveryPlan.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		if (patientMember != null) {
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("patient"),patientMember));
		}
		if (createDate != null && endDate != null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"),createDate,endDate));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		return  super.findList(criteriaQuery, null, null, null, null);
	}

}