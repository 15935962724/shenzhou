/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.DoctorDao;
import net.shenzhou.dao.DoctorMechanismRelationDao;
import net.shenzhou.dao.ProjectDao;
import net.shenzhou.entity.Bill;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.entity.Mechanism;

import org.springframework.stereotype.Repository;
/**
 * Dao - 医生机构关系表
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("doctorMechanismRelationDaoImpl")
public class DoctorMechanismRelationDaoImpl extends BaseDaoImpl<DoctorMechanismRelation, Long> implements DoctorMechanismRelationDao {

	@Resource(name = "doctorDaoImpl")
	private DoctorDao doctorDao;
	@Resource(name = "projectDaoImpl")
	private ProjectDao projectDao;
	
	
	@Override
	public Page<DoctorMechanismRelation> getPageMechanismDoctors(
			Map<String, Object> query_map) {
		Pageable pageable = (Pageable) query_map.get("pageable");
		Mechanism mechanism =  (Mechanism) query_map.get("mechanism");
		Object nameOrphone = query_map.get("nameOrphone");
		Object doctorCategory = query_map.get("doctorCategory");
		Object gender = query_map.get("gender");
		ServerProjectCategory serverProjectCategory =(ServerProjectCategory)query_map.get("serverProjectCategory");
		Object audit = query_map.get("audit");
		Boolean isEnabled = (Boolean) query_map.get("isEnabled");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoctorMechanismRelation> criteriaQuery = criteriaBuilder.createQuery(DoctorMechanismRelation.class);
		Root<DoctorMechanismRelation> root = criteriaQuery.from(DoctorMechanismRelation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isSystem"), false));
		List<Doctor> doctors = doctorDao.getDoctors(query_map);
		//根据医生姓名或手机号搜索
		if (doctors.size()>0) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("doctor")).value(doctors));
		}
		//审核通过
		if (audit!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("audit"),audit));
		}
		//是否停用
		if (isEnabled!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"),isEnabled));
		}
		
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}


	@Override
	public List<DoctorMechanismRelation> downloadList(
			Map<String, Object> query_map) {
		Pageable pageable = (Pageable) query_map.get("pageable");
		Mechanism mechanism =  (Mechanism) query_map.get("mechanism");
		Object nameOrphone = query_map.get("nameOrphone");
		Object doctorCategory = query_map.get("doctorCategory");
		Object gender = query_map.get("gender");
		Object serverProjectCategory = query_map.get("serverProjectCategory");
		Object audit = query_map.get("audit");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoctorMechanismRelation> criteriaQuery = criteriaBuilder.createQuery(DoctorMechanismRelation.class);
		Root<DoctorMechanismRelation> root = criteriaQuery.from(DoctorMechanismRelation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isSystem"), false));
		List<Doctor> doctors = doctorDao.getDoctors(query_map);
		//根据医生姓名或手机号搜索
		if (doctors.size()>0) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("doctor")).value(doctors));
		}
		if (audit!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("audit"),audit));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}


	@Override
	public List<DoctorMechanismRelation> getDoctorMechanism(Doctor doctor) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoctorMechanismRelation> criteriaQuery = criteriaBuilder.createQuery(DoctorMechanismRelation.class);
		Root<DoctorMechanismRelation> root = criteriaQuery.from(DoctorMechanismRelation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"), doctor));
		criteriaQuery.where(restrictions);
		List<DoctorMechanismRelation> list = super.findList(criteriaQuery, null, null, null, null);
		
		if(list.size()>0){
			return list;
		}
		return null;
	}


	@Override
	public DoctorMechanismRelation getByDoctorMechanism(Doctor doctor, Mechanism mechanism) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoctorMechanismRelation> criteriaQuery = criteriaBuilder.createQuery(DoctorMechanismRelation.class);
		Root<DoctorMechanismRelation> root = criteriaQuery.from(DoctorMechanismRelation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"), doctor));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		criteriaQuery.where(restrictions);
		List<DoctorMechanismRelation> list = super.findList(criteriaQuery, null, null, null, null);
		
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
}