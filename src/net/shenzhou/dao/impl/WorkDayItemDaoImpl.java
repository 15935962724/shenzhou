/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.dao.WorkDayItemDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDayItem;
import net.shenzhou.entity.WorkDayItem.WorkDayType;

import org.springframework.stereotype.Repository;

/**
 * 
 * 2017-5-25 09:25:23
 * @author wsr
 * 
 *
 */
@Repository("workDayItemDaoImpl")
public class WorkDayItemDaoImpl extends BaseDaoImpl<WorkDayItem, Long> implements WorkDayItemDao {

	@Override
	public List<WorkDayItem> getLockTime(WorkDay workday) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDayItem> criteriaQuery = criteriaBuilder.createQuery(WorkDayItem.class);
		Root<WorkDayItem> root = criteriaQuery.from(WorkDayItem.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDay"),  workday));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDayType"),WorkDayType.locking));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("startTime")));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	
	}

	@Override
	public WorkDayItem getByStartTime(String startTime , WorkDay workday) {
		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDayItem> criteriaQuery = criteriaBuilder.createQuery(WorkDayItem.class);
		Root<WorkDayItem> root = criteriaQuery.from(WorkDayItem.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDay"),  workday));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("startTime"),  startTime));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("startTime")));
		criteriaQuery.where(restrictions);
		List<WorkDayItem> list = super.findList(criteriaQuery, null, null, null, null);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<WorkDayItem> getByMember(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDayItem> criteriaQuery = criteriaBuilder.createQuery(WorkDayItem.class);
		Root<WorkDayItem> root = criteriaQuery.from(WorkDayItem.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),  member));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDayType"),WorkDayType.reserve));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("startTime")));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<WorkDayItem> getDoctorMechanismTime(Doctor doctor,  WorkDay workDay) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDayItem> criteriaQuery = criteriaBuilder.createQuery(WorkDayItem.class);
		Root<WorkDayItem> root = criteriaQuery.from(WorkDayItem.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDay"),  workDay));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDayType"),WorkDayType.mechanism));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("startTime")));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<WorkDayItem> getDoctorOrderTime(Doctor doctor, WorkDay workDay) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDayItem> criteriaQuery = criteriaBuilder.createQuery(WorkDayItem.class);
		Root<WorkDayItem> root = criteriaQuery.from(WorkDayItem.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDay"),  workDay));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDayType"),WorkDayType.reserve));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("startTime")));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<WorkDayItem> getDoctorLockTime(Doctor doctor, WorkDay workDay) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDayItem> criteriaQuery = criteriaBuilder.createQuery(WorkDayItem.class);
		Root<WorkDayItem> root = criteriaQuery.from(WorkDayItem.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDay"),  workDay));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDayType"),WorkDayType.locking));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("startTime")));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<WorkDayItem> getWorkDayItem(Doctor doctor,
			WorkDay workDay) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDayItem> criteriaQuery = criteriaBuilder.createQuery(WorkDayItem.class);
		Root<WorkDayItem> root = criteriaQuery.from(WorkDayItem.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDay"),  workDay));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get("workDayType"), WorkDayType.mechanism));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("startTime")));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<WorkDayItem> getMechanismWorkDayItemByMechanism(Doctor doctor,
			WorkDay workDay,Mechanism mechanism) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDayItem> criteriaQuery = criteriaBuilder.createQuery(WorkDayItem.class);
		Root<WorkDayItem> root = criteriaQuery.from(WorkDayItem.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDay"),  workDay));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDayType"), WorkDayType.mechanism));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("startTime")));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<WorkDayItem> getWorkDayItemByMechanism(Doctor doctor,
			WorkDay workDay, Mechanism mechanism) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDayItem> criteriaQuery = criteriaBuilder.createQuery(WorkDayItem.class);
		Root<WorkDayItem> root = criteriaQuery.from(WorkDayItem.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDay"),  workDay));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get("workDayType"), WorkDayType.mechanism));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("startTime")));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<WorkDayItem> getWorkDayItems(Map<String, Object> query_map) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WorkDayItem> criteriaQuery = criteriaBuilder.createQuery(WorkDayItem.class);
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Root<WorkDayItem> root = criteriaQuery.from(WorkDayItem.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.select(root);
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("workDayType"), WorkDayType.reserve));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

}