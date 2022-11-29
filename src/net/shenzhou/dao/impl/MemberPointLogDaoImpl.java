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

import net.shenzhou.Config;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.MemberPointLogDao;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.MemberPointLog;
import net.shenzhou.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
/**
 * 用户积分
 * @date 2018-3-22 15:56:45
 * @author wsr
 *
 */
@Repository("memberPointLogDaoImpl")
public class MemberPointLogDaoImpl extends BaseDaoImpl<MemberPointLog, Long> implements MemberPointLogDao {

	@Override
	public Page<MemberPointLog> findPage(Map<String, Object> query_map) {
		Member member = (Member) query_map.get("member");
		Pageable pageable = (Pageable) query_map.get("pageable"); 
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MemberPointLog> criteriaQuery = criteriaBuilder.createQuery(MemberPointLog.class);
		Root<MemberPointLog> root = criteriaQuery.from(MemberPointLog.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<MemberPointLog> findMemberAcquire(Map<String, Object> query_map) {
		String account = (String)query_map.get("account");
		String startTime = (String) query_map.get("startTime");
		String endTime = (String) query_map.get("engTime");
		Member member = (Member) query_map.get("member");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MemberPointLog> criteriaQuery = criteriaBuilder.createQuery(MemberPointLog.class);
		Root<MemberPointLog> root = criteriaQuery.from(MemberPointLog.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		//如果都为空,没有筛选条件
		if(StringUtils.isEmpty(account)&&StringUtils.isEmpty(startTime)&&StringUtils.isEmpty(endTime)){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
			criteriaQuery.where(restrictions);
			
			List<MemberPointLog> memberPointLogs =  super.findList(criteriaQuery, null, null, null, null);
			return memberPointLogs;
		}
		
		//如果有值,为筛选
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.or(criteriaBuilder.like(root.<Member> get("member").<String>get("name"), "%" + account + "%"),criteriaBuilder.like(root.<Member> get("member").<String>get("mobile"), "%" + account + "%")));
		if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
			String start_Time = startTime+" 00:00:00";
			String end_Time = endTime+" 23:59:59";
			Date startDate = DateUtil.getStringtoDate(start_Time, "yyyy-MM-dd HH:mm:ss");
			Date endDate = DateUtil.getStringtoDate(end_Time, "yyyy-MM-dd HH:mm:ss");
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDate, endDate));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		criteriaQuery.where(restrictions);
		
		List<MemberPointLog> memberPointLogs =  super.findList(criteriaQuery, null, null, null, null);
		return memberPointLogs;
		
	}

}