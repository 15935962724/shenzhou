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

import net.shenzhou.dao.RecoveryRecordDao;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.RecoveryPlan;
import net.shenzhou.entity.RecoveryRecord;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Repository;
/**
 * Dao - 银行卡
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("recoveryRecordDaoImpl")
public class RecoveryRecordDaoImpl extends BaseDaoImpl<RecoveryRecord, Long> implements RecoveryRecordDao {

	@Override
	public List<RecoveryRecord> findRecoveryRecordList(
			Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		
		Date createDate = (Date) query_map.get("createDate");
		Date endDate = (Date) query_map.get("endDate");
		Member patientMember = (Member) query_map.get("patientMember");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RecoveryRecord> criteriaQuery = criteriaBuilder.createQuery(RecoveryRecord.class);
		Root<RecoveryRecord> root = criteriaQuery.from(RecoveryRecord.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (patientMember != null) {
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("patient"),patientMember));
		}
		if (createDate != null && endDate != null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"),createDate,endDate ));
		}
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		
		criteriaQuery.where(restrictions);
		return  super.findList(criteriaQuery, null, null, null, null);
	}

}