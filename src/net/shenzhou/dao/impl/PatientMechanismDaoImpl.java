/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.MemberDao;
import net.shenzhou.dao.PatientMechanismDao;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PatientMechanism;
import net.shenzhou.entity.PatientMechanism.HealthType;
import net.shenzhou.util.DateUtil;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 患者所在机构的状态
 * @date 2018-3-23 16:11:49
 * @author wsr
 */
@Repository("patientMechanismDaoImpl")
public class PatientMechanismDaoImpl extends BaseDaoImpl<PatientMechanism, Long> implements PatientMechanismDao {

	
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	
	@Override
	public Page<PatientMechanism> getPatientMechanisms(
			Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Pageable pageable = (Pageable) query_map.get("pageable");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Object healthType = query_map.get("healthType");
		HealthType[] healthTypes =  (HealthType[]) query_map.get("healthTypes");
		Object isDeleted = query_map.get("isDeleted");
		
		if (mechanism == null) {
			return new Page<PatientMechanism>(Collections.<PatientMechanism> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PatientMechanism> criteriaQuery = criteriaBuilder.createQuery(PatientMechanism.class);
		Root<PatientMechanism> root = criteriaQuery.from(PatientMechanism.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"),mechanism));	
		if (isDeleted!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"),Boolean.valueOf(isDeleted.toString())));	
		}
		if (nameOrmobile!=null) {
			List<Member> patientMembers = memberDao.downloadPatientHealthType(query_map);
			if (patientMembers.size()>0) {
				restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("patient")).value(patientMembers));	
			}else{
				return new Page<PatientMechanism>(Collections.<PatientMechanism> emptyList(), 0, pageable);
			}
		}
		if (healthType!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("healthType"), HealthType.valueOf(String.valueOf(healthType))));	
		}
		
		if (healthTypes!=null) {//这个条件主要针对患者状态 统计 那里使用
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("healthType")).value( Arrays.asList(healthTypes)));	
		}
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
		
	}

	@Override
	public List<PatientMechanism> downloadPatientHealthType(
			Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Pageable pageable = (Pageable) query_map.get("pageable");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Object healthType = query_map.get("healthType");
		HealthType[] healthTypes =  (HealthType[]) query_map.get("healthTypes");
		Object isDeleted = query_map.get("isDeleted");
		if (mechanism == null) {
			return new ArrayList<PatientMechanism>();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PatientMechanism> criteriaQuery = criteriaBuilder.createQuery(PatientMechanism.class);
		Root<PatientMechanism> root = criteriaQuery.from(PatientMechanism.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"),mechanism));
		if (isDeleted!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"),Boolean.valueOf(isDeleted.toString())));	
		}
		if (nameOrmobile!=null) {
			List<Member> patientMembers = memberDao.downloadPatientHealthType(query_map);
			if (patientMembers.size()>0) {
				restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("patient")).value(patientMembers));	
			}else{
				return new ArrayList<PatientMechanism>();
			}
		}
		if (healthType!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("healthType"), HealthType.valueOf(String.valueOf(healthType))));	
		}
		if (healthTypes!=null) {//这个条件主要针对患者状态 统计 那里使用
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("healthType")).value( Arrays.asList(healthTypes)));	
		}
		
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
		
	}

	@Override
	public Integer daysPatientCount(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		if (mechanism == null) {
			return 0;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PatientMechanism> criteriaQuery = criteriaBuilder.createQuery(PatientMechanism.class);
		Root<PatientMechanism> root = criteriaQuery.from(PatientMechanism.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"),mechanism));
		Date startDate = new Date();
		Date endDate = new Date();
		Calendar calendar = Calendar.getInstance();
		if (startDate!=null) {
			calendar.setTime(startDate);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			startDate = calendar.getTime();
		}
		if (endDate!=null) {
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			endDate = calendar.getTime();
		}
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDate,endDate));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null).size();
	}
	
	

}