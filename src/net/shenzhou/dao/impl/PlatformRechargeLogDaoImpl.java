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
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.MemberDao;
import net.shenzhou.dao.PlatformRechargeLogDao;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PlatformRechargeLog;
import net.shenzhou.entity.PlatformRechargeLog.RechargeMode;
import net.shenzhou.entity.RechargeLog;

import org.springframework.stereotype.Repository;

/**
 * 平台充值日志
 * @date 2018-5-18 10:55:36
 * @author wsr
 *
 */			 
@Repository("platformRechargeLogDaoImpl")
public class PlatformRechargeLogDaoImpl extends BaseDaoImpl<PlatformRechargeLog, Long> implements PlatformRechargeLogDao {

	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	
	@Override
	public PlatformRechargeLog getPlatformRechargeLog(String outTradeNo,
			String tradeNo) {
		if (outTradeNo == null && tradeNo == null) {
			return null;
		}
		
		try {
			String jpql = "select platformRechargeLog from PlatformRechargeLog platformRechargeLog where lower(platformRechargeLog.outTradeNo) = lower(:outTradeNo) and lower(platformRechargeLog.tradeNo) = lower(:tradeNo)";
			return entityManager.createQuery(jpql, PlatformRechargeLog.class).setFlushMode(FlushModeType.COMMIT).setParameter("outTradeNo", outTradeNo).setParameter("tradeNo", tradeNo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Page<PlatformRechargeLog> findPage(Map<String, Object> query_map) {
		
		Member member = (Member) query_map.get("member");
		Pageable pageable = (Pageable) query_map.get("pageable");
		RechargeMode rechargeMode = (RechargeMode) query_map.get("rechargeMode");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PlatformRechargeLog> criteriaQuery = criteriaBuilder.createQuery(PlatformRechargeLog.class);
		
		Root<PlatformRechargeLog> root = criteriaQuery.from(PlatformRechargeLog.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
	    if(member!=null){
	    	restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"), member));
	    }
	    if(rechargeMode!=null){
	    	restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("rechargeMode"), rechargeMode));
	    }
		criteriaQuery.where(restrictions);	
		return super.findPage(criteriaQuery, pageable);
		
		
		
	}
	
}