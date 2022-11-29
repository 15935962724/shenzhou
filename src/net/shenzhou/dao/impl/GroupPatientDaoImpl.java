/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.dao.GroupPatientDao;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.GroupPatient;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PatientGroup;

import org.springframework.stereotype.Repository;
/**
 * Dao - 分组患者
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("groupPatientDaoImpl")
public class GroupPatientDaoImpl extends BaseDaoImpl<GroupPatient, Long> implements GroupPatientDao {

	@Override
	public List<GroupPatient> getGroupPatientByGroupAndDoctor(
			DoctorMechanismRelation doctorMechanismRelation,
			PatientGroup patientGroup) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GroupPatient> criteriaQuery = criteriaBuilder.createQuery(GroupPatient.class);
		Root<GroupPatient> root = criteriaQuery.from(GroupPatient.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"),  false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctorMechanismRelation"),  doctorMechanismRelation));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("patientGroup"),  patientGroup));
		
		criteriaQuery.select(root);
		criteriaQuery.where(restrictions);
		
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public GroupPatient getGroupPatientByPatientAndDoctor(
			DoctorMechanismRelation doctorMechanismRelation, Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GroupPatient> criteriaQuery = criteriaBuilder.createQuery(GroupPatient.class);
		Root<GroupPatient> root = criteriaQuery.from(GroupPatient.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"),  false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctorMechanismRelation"),  doctorMechanismRelation));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("patientMember"),  member));
		
		criteriaQuery.select(root);
		criteriaQuery.where(restrictions);
		
		List<GroupPatient> groupPatients = super.findList(criteriaQuery, null, null, null, null);
		
		if(groupPatients.size()>0){
			return groupPatients.get(0);
		}
		return null;
	}

	@Override
	public GroupPatient getGroupPatient(
			DoctorMechanismRelation doctorMechanismRelation, Member member,
			PatientGroup patientGroup) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GroupPatient> criteriaQuery = criteriaBuilder.createQuery(GroupPatient.class);
		Root<GroupPatient> root = criteriaQuery.from(GroupPatient.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"),  false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctorMechanismRelation"),  doctorMechanismRelation));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("patientMember"),  member));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("patientGroup"),  patientGroup));
		
		criteriaQuery.select(root);
		criteriaQuery.where(restrictions);
		
		List<GroupPatient> groupPatients = super.findList(criteriaQuery, null, null, null, null);
		
		if(groupPatients.size()>0){
			return groupPatients.get(0);
		}
		return null;
	}

	@Override
	public List<Member> getAddGroupPatientData(
			DoctorMechanismRelation doctorMechanismRelation) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GroupPatient> criteriaQuery = criteriaBuilder.createQuery(GroupPatient.class);
		Root<GroupPatient> root = criteriaQuery.from(GroupPatient.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"),  false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctorMechanismRelation"),  doctorMechanismRelation));
		
		criteriaQuery.select(root);
		criteriaQuery.where(restrictions);
		
		List<GroupPatient> groupPatients = super.findList(criteriaQuery, null, null, null, null);
		List<Member> members = new ArrayList<Member>(); 
		
		for(GroupPatient GroupPatient : groupPatients){
			members.add(GroupPatient.getPatientMember());
		}
		
		return members;
		
	}
	
}