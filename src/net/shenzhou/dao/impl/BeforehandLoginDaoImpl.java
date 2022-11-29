/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.dao.BeforehandLoginDao;
import net.shenzhou.entity.BeforehandLogin;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.MemberPointLog;
import net.shenzhou.entity.BeforehandLogin.UserType;
import net.shenzhou.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
/**
 * Dao - 银行卡
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("beforehandLoginDaoImpl")
public class BeforehandLoginDaoImpl extends BaseDaoImpl<BeforehandLogin, Long> implements BeforehandLoginDao {

	@Override
	public BeforehandLogin findByMobile(String mobile) {
		if (mobile == null) {
			return null;
		}
		try {
			String jpql = "select beforehandLogins from BeforehandLogin beforehandLogins where lower(beforehandLogins.mobile) = lower(:mobile)";
			return entityManager.createQuery(jpql, BeforehandLogin.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile", mobile).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public BeforehandLogin findByMobile(String mobile, UserType userType,UserType usersType) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BeforehandLogin> criteriaQuery = criteriaBuilder.createQuery(BeforehandLogin.class);
		Root<BeforehandLogin> bill = criteriaQuery.from(BeforehandLogin.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (mobile != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("mobile"),  mobile));
		}
		if(userType!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("userType"),  userType));
		}
		if (usersType != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(bill.get("usersType"),  usersType));
		}
		criteriaQuery.select(bill);
		criteriaQuery.where(restrictions);
		
		List<BeforehandLogin> beforehandLoginList = super.findList(criteriaQuery, null, null, null, null);
		
		if(beforehandLoginList.size()>0){
			return beforehandLoginList.get(0);
		}
		
		return null;
	}

	@Override
	public List<BeforehandLogin> findMemberUnAcquire(
			Map<String, Object> query_map) {
		String account = (String)query_map.get("account");
		String startTime = (String) query_map.get("startTime");
		String endTime = (String) query_map.get("engTime");
		String mobile = (String) query_map.get("mobile");
		
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BeforehandLogin> criteriaQuery = criteriaBuilder.createQuery(BeforehandLogin.class);
		Root<BeforehandLogin> root = criteriaQuery.from(BeforehandLogin.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		//如果都为空,没有筛选条件
		if(StringUtils.isEmpty(account)&&StringUtils.isEmpty(startTime)&&StringUtils.isEmpty(endTime)){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userType"), UserType.member));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("phone"), mobile));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
			criteriaQuery.where(restrictions);
			
			List<BeforehandLogin> memberPointLogs =  super.findList(criteriaQuery, null, null, null, null);
			return memberPointLogs;
		}
		
		//如果有值,为筛选
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("phone"), mobile));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userType"), UserType.member));
		if(!StringUtils.isEmpty(account)){
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.and(criteriaBuilder.like(root.<String> get("mobile"), "%" + account + "%")));
		}
		if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
			String start_Time = startTime+" 00:00:00";
			String end_Time = endTime+" 23:59:59";
			Date startDate = DateUtil.getStringtoDate(start_Time, "yyyy-MM-dd HH:mm:ss");
			Date endDate = DateUtil.getStringtoDate(end_Time, "yyyy-MM-dd HH:mm:ss");
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDate, endDate));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		criteriaQuery.where(restrictions);
		
		List<BeforehandLogin> memberPointLogs =  super.findList(criteriaQuery, null, null, null, null);
		return memberPointLogs;
		
	}

	@Override
	public List<BeforehandLogin> findDoctorUnAcquire(
			Map<String, Object> query_map) {
		String account = (String)query_map.get("account");
		String startTime = (String) query_map.get("startTime");
		String endTime = (String) query_map.get("engTime");
		String mobile = (String) query_map.get("mobile");
		
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BeforehandLogin> criteriaQuery = criteriaBuilder.createQuery(BeforehandLogin.class);
		Root<BeforehandLogin> root = criteriaQuery.from(BeforehandLogin.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		//如果都为空,没有筛选条件
		if(StringUtils.isEmpty(account)&&StringUtils.isEmpty(startTime)&&StringUtils.isEmpty(endTime)){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userType"), UserType.doctor));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("phone"), mobile));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
			criteriaQuery.where(restrictions);
			
			List<BeforehandLogin> memberPointLogs =  super.findList(criteriaQuery, null, null, null, null);
			return memberPointLogs;
		}
		
		//如果有值,为筛选
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("phone"), mobile));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userType"), UserType.doctor));
		if(!StringUtils.isEmpty(account)){
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.and(criteriaBuilder.like(root.<String> get("mobile"), "%" + account + "%")));
		}
		if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
			String start_Time = startTime+" 00:00:00";
			String end_Time = endTime+" 23:59:59";
			Date startDate = DateUtil.getStringtoDate(start_Time, "yyyy-MM-dd HH:mm:ss");
			Date endDate = DateUtil.getStringtoDate(end_Time, "yyyy-MM-dd HH:mm:ss");
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDate, endDate));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		criteriaQuery.where(restrictions);
		
		List<BeforehandLogin> memberPointLogs =  super.findList(criteriaQuery, null, null, null, null);
		return memberPointLogs;
	}

}