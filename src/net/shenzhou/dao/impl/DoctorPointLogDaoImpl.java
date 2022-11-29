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

import net.shenzhou.dao.DoctorPointLogDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorPointLog;
import net.shenzhou.entity.MemberPointLog;
import net.shenzhou.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
/**
 * Dao - 银行卡
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("doctorPointLogDaoImpl")
public class DoctorPointLogDaoImpl extends BaseDaoImpl<DoctorPointLog, Long> implements DoctorPointLogDao {

	@Override
	public List<DoctorPointLog> findDoctorAcquire(Map<String,Object> query_map) {
		String account = (String)query_map.get("account");
		String startTime = (String) query_map.get("startTime");
		String endTime = (String) query_map.get("engTime");
		Doctor doctor = (Doctor) query_map.get("doctor");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoctorPointLog> criteriaQuery = criteriaBuilder.createQuery(DoctorPointLog.class);
		Root<DoctorPointLog> root = criteriaQuery.from(DoctorPointLog.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		//如果都为空,没有筛选条件
		if(StringUtils.isEmpty(account)&&StringUtils.isEmpty(startTime)&&StringUtils.isEmpty(endTime)){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"), doctor));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
			criteriaQuery.where(restrictions);
			
			List<DoctorPointLog> doctorPointLogs =  super.findList(criteriaQuery, null, null, null, null);
			return doctorPointLogs;
		}
		
		//如果有值,为筛选
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.or(criteriaBuilder.like(root.<Doctor> get("doctor").<String>get("name"), "%" + account + "%"),criteriaBuilder.like(root.<Doctor>get("doctor").<String> get("mobile"), "%" + account + "%")));
		if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
			String start_Time = startTime+" 00:00:00";
			String end_Time = endTime+" 23:59:59";
			Date startDate = DateUtil.getStringtoDate(start_Time, "yyyy-MM-dd HH:mm:ss");
			Date endDate = DateUtil.getStringtoDate(end_Time, "yyyy-MM-dd HH:mm:ss");
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDate, endDate));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"), doctor));
		criteriaQuery.where(restrictions);
		
		List<DoctorPointLog> doctorPointLogs =  super.findList(criteriaQuery, null, null, null, null);
		return doctorPointLogs;
	}

}