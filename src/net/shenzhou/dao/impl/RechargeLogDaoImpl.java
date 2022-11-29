/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import net.shenzhou.dao.MemberDao;
import net.shenzhou.dao.RechargeLogDao;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.RechargeLog;
import net.shenzhou.entity.Refunds;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Repository;

/**
 * 充值日志
 * @author wsr
 *
 */
@Repository("rechargeLogDaoImpl")
public class RechargeLogDaoImpl extends BaseDaoImpl<RechargeLog, Long> implements RechargeLogDao {

	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	
	@Override
	public Page<RechargeLog> getMechanismRechargeLogList(
			Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Object startDate = query_map.get("startDate");
		Object endDate = query_map.get("endDate");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Pageable pageable = (Pageable) query_map.get("pageable");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RechargeLog> criteriaQuery = criteriaBuilder.createQuery(RechargeLog.class);
		
		Root<RechargeLog> root = criteriaQuery.from(RechargeLog.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
	    if (mechanism!=null) {
	    	Map<String, Object> query_member_list_map = new HashMap<String, Object>();
	    	query_member_list_map.put("mechanism", mechanism);
	    	query_member_list_map.put("nameOrmobile", nameOrmobile);
	    	List<Member> members = memberDao.getMembersByNameOrMobile(query_member_list_map);
//	    	List<Member> members = memberDao.getMembersByNameOrMobile(String.valueOf(nameOrmobile));
	    	if (members.size()>0) {
	    		restrictions = criteriaBuilder.in(root.get("member")).value(members);
			}
		}
	    //add wsr 2018-3-19 17:05:15
	    restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"), mechanism));
	    
	    if (startDate != null&&endDate != null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"),(Date)startDate , (Date)endDate));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public BigDecimal sumRecharge(Map<String, Object> query_map) {
			Mechanism mechansim = (Mechanism) query_map.get("mechanism");
			Date createDate =  (Date) query_map.get("createDate");
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
			Root<RechargeLog> root = criteriaQuery.from(RechargeLog.class);
			criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal> get("money")));
			Predicate restrictions = criteriaBuilder.conjunction();
			if (mechansim != null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechansim));
			}
			if (createDate != null) {
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
			
			}
			
			criteriaQuery.where(restrictions);
			return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
		}

	@Override
	public List<RechargeLog> downloadList(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Object startDate = query_map.get("startDate");
		Object endDate = query_map.get("endDate");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Pageable pageable = (Pageable) query_map.get("pageable");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RechargeLog> criteriaQuery = criteriaBuilder.createQuery(RechargeLog.class);
		
		Root<RechargeLog> root = criteriaQuery.from(RechargeLog.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
	    if (mechanism!=null) {
	    	Map<String, Object> query_member_list_map = new HashMap<String, Object>();
	    	query_member_list_map.put("mechanism", mechanism);
	    	query_member_list_map.put("nameOrmobile", nameOrmobile);
//	    	List<Member> members = memberDao.getMembersByNameOrMobile(query_member_list_map);
	    	List<Member> members = memberDao.getMembersByNameOrMobile(String.valueOf(nameOrmobile));
	    	if (members.size()>0) {
	    		restrictions = criteriaBuilder.in(root.get("member")).value(members);
			}
		}
	    
	    
	    if (startDate != null&&endDate != null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"),(Date)startDate , (Date)endDate));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public Page<RechargeLog> getRechargeLogs(Map<String, Object> query_map) {
		Mechanism mechanism = (Mechanism) query_map.get("mechanism");
		Object nameOrmobile = query_map.get("nameOrmobile");
		Pageable pageable = (Pageable) query_map.get("pageable");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RechargeLog> criteriaQuery = criteriaBuilder.createQuery(RechargeLog.class);
		
		Root<RechargeLog> root = criteriaQuery.from(RechargeLog.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
	    if (mechanism!=null&&nameOrmobile!=null) {
	    	Map<String, Object> query_member_list_map = new HashMap<String, Object>();
	    	query_member_list_map.put("mechanism", mechanism);
	    	query_member_list_map.put("nameOrmobile", nameOrmobile);
	    	List<Member> members = memberDao.getMembersByNameOrMobile(query_member_list_map);
//	    	List<Member> members = memberDao.getMembersByNameOrMobile(String.valueOf(nameOrmobile));
	    	if (members.size()>0) {
	    		restrictions = criteriaBuilder.in(root.get("member")).value(members);
			}
	    	 restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"), mechanism));
		}
	    
	   if(mechanism!=null&&nameOrmobile==null){
		   restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mechanism"), mechanism));
	   }
	   
	   if(mechanism==null&&nameOrmobile!=null){
		   List<Member> members = memberDao.getMembersByNameOrMobile(String.valueOf(nameOrmobile));
		   if (members.size()>0) {
	    		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.in(root.get("member")).value(members));
			}else{
				return new Page<RechargeLog>(new ArrayList<RechargeLog>(),0,pageable);
			}
	   }
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public BigDecimal getSumRecharge(Map<String, Object> query_map) {
		Mechanism mechansim = (Mechanism) query_map.get("mechanism");
		String startTime  = (String) query_map.get("startTime");
		String endTime =  (String) query_map.get("endTime");
		
		Date startDate = DateUtil.getStringtoDate(startTime, "yyyy-MM-dd HH:mm:ss");
		Date endDate = DateUtil.getStringtoDate(endTime, "yyyy-MM-dd HH:mm:ss");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<RechargeLog> root = criteriaQuery.from(RechargeLog.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal> get("money")));
		Predicate restrictions = criteriaBuilder.conjunction();
		if (mechansim != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"), mechansim));
		}
		if (startDate != null && endDate != null ) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<Date>get("createDate"), startDate,endDate));
		}
		
		criteriaQuery.where(restrictions);
		BigDecimal sumRecharge =  entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
		return sumRecharge==null?new BigDecimal(0):sumRecharge;
	}


}