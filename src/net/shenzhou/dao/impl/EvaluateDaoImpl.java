/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.EvaluateDao;
import net.shenzhou.dao.MemberDao;
import net.shenzhou.dao.ProjectDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Evaluate;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.PaymentStatus;

import org.springframework.stereotype.Repository;

/**
 * Dao - 评价
 * 2017-6-5 16:48:25
 * @author wsr
 * @version 1.0
 */
@Repository("evaluateDaoImpl")
public class EvaluateDaoImpl extends BaseDaoImpl<Evaluate, Long> implements EvaluateDao {

	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "projectDaoImpl")
	private ProjectDao projectDao;
	
	@Override
	public List<Evaluate> findList(Doctor doctor) {
		// TODO Auto-generated method stub
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Evaluate> criteriaQuery = criteriaBuilder.createQuery(Evaluate.class);
		Root<Evaluate> root = criteriaQuery.from(Evaluate.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
//		restrictions=criteriaBuilder.and(criteriaBuilder.equal(root.get("isShow"), false));//是否显示
		if(doctor.getProjects().size()>0){
			restrictions=criteriaBuilder.and(criteriaBuilder.in(root.get("project")).value(doctor.getProjects()));//根据机构筛选医师
		}else{
			return new ArrayList<Evaluate>();
		}
		
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
		
		
	}

	@Override
	public Object findObject(Project project) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Evaluate> root = criteriaQuery.from(Evaluate.class);
		criteriaQuery.multiselect(criteriaBuilder.avg(root.<Double> get("scoreSort")).alias("scoreSort"), criteriaBuilder.avg(root.<Double> get("serverSort")).alias("serverSort"), criteriaBuilder.avg(root.<Double> get("skillSort")).alias("skillSort"), criteriaBuilder.avg(root.<Double> get("communicateSort")).alias("communicateSort"));

		Predicate restrictions = criteriaBuilder.conjunction();
		if (project != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Date> get("project"), project));
		}
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
	
	}

	@Override
	public Page<Evaluate> getDoctorEvaluate(Map<String,Object> query_map) {
		Doctor doctor = (Doctor) query_map.get("doctor");
		Object startDate = query_map.get("startDate");
		Object endDate = query_map.get("endDate");
		Project project = (Project) query_map.get("project");
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Pageable pageable = (Pageable) query_map.get("pageable");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Evaluate> criteriaQuery = criteriaBuilder.createQuery(Evaluate.class);
		Root<Evaluate> root = criteriaQuery.from(Evaluate.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions=criteriaBuilder.and(criteriaBuilder.equal(root.get("isDeleted"), false));//是否删除
		
		if(doctor.getProjects().size()>0){
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("project")).value(doctor.getProjects()));//根据机构筛选医师
		}else{
			return new Page<Evaluate>();
		}
		
		if (project!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Date> get("project"), project));
		}
		if (nameOrmobile!=null) {
			Map<String ,Object> query_member_map = new java.util.HashMap<String, Object>();
			query_member_map.put("nameOrmobile", nameOrmobile);
			query_member_map.put("mechanism", mechanism);
		    List<Member> members = memberDao.getMembersByNameOrMobile(query_member_map);
		    if (members.size()>0) {
		    	restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("member")).value(members));
			}
		}
		
		if (startDate!=null&&endDate!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<Date>get("createDate"), (Date)startDate, (Date)endDate));
		}
		
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Page<Evaluate> getPageEvaluate(
			Map<String, Object> query_map) {
		
		Object startDate = query_map.get("startDate");
		Object endDate = query_map.get("endDate");
		Project project = (Project) query_map.get("project");
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Pageable pageable = (Pageable) query_map.get("pageable");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Evaluate> criteriaQuery = criteriaBuilder.createQuery(Evaluate.class);
		Root<Evaluate> root = criteriaQuery.from(Evaluate.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions=criteriaBuilder.and(criteriaBuilder.equal(root.get("isDeleted"), false));//是否删除
		
		
		if (project!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Date> get("project"), project));
		}
		
		
	    List<Member> members = memberDao.getMembersByNameOrMobile(query_map);
	    if (members.size()>0) {
	    	restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("member")).value(members));
		}
		if (startDate!=null&&endDate!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<Date>get("createDate"), (Date)startDate, (Date)endDate));
		}
		
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Page<Evaluate> getPageEvaluateCharge(
			Map<String, Object> query_map) {
		Object startDate = query_map.get("startDate");
		Object endDate = query_map.get("endDate");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Object doctorNameOrMobile = query_map.get("doctorNameOrMobile");
//		Object patientName = query_map.get("patientName");
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Pageable pageable = (Pageable) query_map.get("pageable");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Evaluate> criteriaQuery = criteriaBuilder.createQuery(Evaluate.class);
		Root<Evaluate> root = criteriaQuery.from(Evaluate.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions=criteriaBuilder.and(criteriaBuilder.equal(root.get("isDeleted"), false));//是否删除
		
		
		
		//根据医生姓名或电话查询项目
		 query_map.put("doctorName", doctorNameOrMobile);
		 
		 List<Project> projects = projectDao.getDownloadList(query_map);
		 if (projects.size()>0) {
			 restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("project")).value(projects));
		}else{
			return new Page<Evaluate>();
		}
		
		
	    List<Member> members = memberDao.getMembersByNameOrMobile(query_map);
	    if (members.size()>0) {
	    	restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("member")).value(members));
		}else{
			return new Page<Evaluate>();
		}
		if (startDate!=null&&endDate!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<Date>get("createDate"), (Date)startDate, (Date)endDate));
		}
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
		
		
	}

	@Override
	public List<Evaluate> getDownloadList(Map<String, Object> query_map) {
		Object startDate = query_map.get("startDate");
		Object endDate = query_map.get("endDate");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Object doctorNameOrMobile = query_map.get("doctorNameOrMobile");
//		Object patientName = query_map.get("patientName");
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Pageable pageable = (Pageable) query_map.get("pageable");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Evaluate> criteriaQuery = criteriaBuilder.createQuery(Evaluate.class);
		Root<Evaluate> root = criteriaQuery.from(Evaluate.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions=criteriaBuilder.and(criteriaBuilder.equal(root.get("isDeleted"), false));//是否删除
		
		
		
		//根据医生姓名或电话查询项目
		 query_map.put("doctorName", doctorNameOrMobile);
		 
		 List<Project> projects = projectDao.getDownloadList(query_map);
		 if (projects.size()>0) {
			 restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("project")).value(projects));
		}else{
			return new ArrayList<Evaluate>();
		}
		
		
	    List<Member> members = memberDao.getMembersByNameOrMobile(query_map);
	    if (members.size()>0) {
	    	restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("member")).value(members));
		}else{
			return new ArrayList<Evaluate>();
		}
		if (startDate!=null&&endDate!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<Date>get("createDate"), (Date)startDate, (Date)endDate));
		}
		
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<Evaluate> getDoctorEvaluateList(Map<String, Object> query_map) {
		
		List<Project> projects = (List<Project>)query_map.get("projects");
		Integer page = (Integer)query_map.get("page");
		
		/*Pageable pageable = new Pageable();
		pageable.setPageNumber(page);
		pageable.setPageSize(5);*/
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Evaluate> criteriaQuery = criteriaBuilder.createQuery(Evaluate.class);
		Root<Evaluate> root = criteriaQuery.from(Evaluate.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("project")).value(projects));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"),  false));
		criteriaQuery.where(restrictions);
		
		return super.findList(criteriaQuery, null, null, null, null);
		
	}

}