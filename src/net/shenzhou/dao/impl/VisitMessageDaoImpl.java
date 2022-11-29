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

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.VisitMessageDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.VisitMessage;
import net.shenzhou.entity.Order.Evaluate;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.entity.Order.ServeState;
import net.shenzhou.entity.Order.ShippingStatus;

import org.springframework.stereotype.Repository;

/**
 * 回访信息
 * @date 2017-8-16 17:16:44
 * @author wsr
 *
 */
@Repository("visitMessageDaoImpl")
public class VisitMessageDaoImpl extends BaseDaoImpl<VisitMessage, Long> implements VisitMessageDao {

	@Override
	public Page<VisitMessage> findPage(Map<String, Object> query_map) {
		// TODO Auto-generated method stub
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Member member = (Member) query_map.get("member");
		Member patientMember = (Member) query_map.get("patientMember");
//		Object nameOrMobile= query_map.get("nameOrMobile");
		Object startCreateDate =  query_map.get("startCreateDate");
		Object endCreateDate =  query_map.get("endCreateDate");
		Object startVisitDate = query_map.get("startVisitDate");
		Object endVisitDate = query_map.get("endVisitDate");
		Object type = query_map.get("type");
		Pageable pageable = (Pageable) query_map.get("pageable");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<VisitMessage> criteriaQuery = criteriaBuilder.createQuery(VisitMessage.class);
		Root<VisitMessage> root = criteriaQuery.from(VisitMessage.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (mechanism!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		}
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		//类型
		if (type.equals("member")) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}else{
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("patient"), patientMember));
		}
		
	
		//创建时间
		if (startCreateDate != null&&endCreateDate!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), (Date)startCreateDate, (Date)endCreateDate));
		}
		
		//回访时间
		if (startVisitDate != null&&endVisitDate!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("visitDate"), (Date)startVisitDate, (Date)endVisitDate));
		}
		
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
		
	}

	@Override
	public Page<VisitMessage> getDoctorPage(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Doctor doctor = (Doctor) query_map.get("doctor");
		Object startCreateDate =  query_map.get("startCreateDate");
		Object endCreateDate =  query_map.get("endCreateDate");
		Object startVisitDate = query_map.get("startVisitDate");
		Object endVisitDate = query_map.get("endVisitDate");
		Pageable pageable = (Pageable) query_map.get("pageable");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<VisitMessage> criteriaQuery = criteriaBuilder.createQuery(VisitMessage.class);
		Root<VisitMessage> root = criteriaQuery.from(VisitMessage.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		//机构
		if (mechanism!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechanism));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		//医生
		if (doctor!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"), doctor));
		}
		
		//创建时间
		if (startCreateDate != null&&endCreateDate!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), (Date)startCreateDate, (Date)endCreateDate));
		}
		
		//回访时间
		if (startVisitDate != null&&endVisitDate!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("visitDate"), (Date)startVisitDate, (Date)endVisitDate));
		}
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
		
	}

	@Override
	public Page<VisitMessage> getPage(Map<String, Object> query_map) {
		
		Member member = (Member) query_map.get("member");
		Member patientMember = (Member) query_map.get("patientMember");
		Pageable pageable = (Pageable) query_map.get("pageable");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<VisitMessage> criteriaQuery = criteriaBuilder.createQuery(VisitMessage.class);
		Root<VisitMessage> root = criteriaQuery.from(VisitMessage.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		if (member!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		
		if (patientMember!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("patient"), patientMember));
		}
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}