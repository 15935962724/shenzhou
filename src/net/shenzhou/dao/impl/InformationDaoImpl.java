/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Config;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.InformationDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Information;
import net.shenzhou.entity.Information.ClassifyType;
import net.shenzhou.entity.Information.StateType;
import net.shenzhou.entity.Information.UserType;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
/**
 * Dao - 银行卡
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("informationDaoImpl")
public class InformationDaoImpl extends BaseDaoImpl<Information, Long> implements InformationDao {

	@Override
	public Map<String ,Object> getInformationByDoctor(Doctor doctor,Integer pageNumber) {
		Map<String ,Object> map = new HashMap<String, Object>();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Integer pageSize = Config.pageSize;
		Pageable pageable = new Pageable();
		pageable.setPageNumber(pageNumber);
		pageable.setPageSize(pageSize);
		if(doctor.getInformations().size()<=0){
			map.put("status", "500");
			map.put("message", "无消息数据数据");
			map.put("data", "{}");
			return map;
		}
		

		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"),doctor));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userType"),UserType.doctor));
		
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		
		List<Information> informations = new ArrayList<Information>();
		informations = super.findPage(criteriaQuery, pageable).getContent();
		
		
		if(informations.size()<=0){
			map.put("status", "500");
			map.put("message", "暂无消息数据");
			map.put("data", "{}");
			return map;
		}
		
		Page<Information> page =  super.findPage(criteriaQuery, pageable);
		Integer count = page.getTotalPages();
		if (pageNumber>(count)) {
			map.put("status", "500");
			map.put("message", "无更多数据");
			map.put("data", "{}");
			return map;
		}
		
		List<Map<String,Object>> informationList = new ArrayList<Map<String,Object>>();
		for (Information information : informations) {
			Map<String,Object> information_map = new HashMap<String,Object>();	
			information_map.put("id",information.getId());
			System.out.println(information.getId());
			information_map.put("disposeState",information.getDisposeState());
			information_map.put("message",information.getMessage());
			information_map.put("informationType",information.getInformationType());
			information_map.put("state",information.getState());
			information_map.put("createDate",DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", information.getCreateDate()));
			information_map.put("headline",information.getHeadline());
			informationList.add(information_map);
		}
		Map<String,Object> information_map = new HashMap<String, Object>();
		information_map.put("informationList", informationList);
		map.put("status", "200");
		map.put("message", "查询成功");
		map.put("data", JsonUtils.toJson(information_map));
		return map;
	}

	@Override
	public Boolean isDoctorUnread(Doctor doctor) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("doctor"), doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("state"), StateType.unread));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		criteriaQuery.where(restrictions);
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return true;
	    }
	    return false;
	}

	@Override
	public Map<String, Object> getInformationByMember(Member member,
			Integer pageNumber) {
		Map<String ,Object> map = new HashMap<String, Object>();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Integer pageSize = Config.pageSize;
		Pageable pageable = new Pageable();
		pageable.setPageNumber(pageNumber);
		pageable.setPageSize(pageSize);
		if(member.getInformations().size()<=0){
			map.put("status", "500");
			map.put("message", "无消息数据");
			map.put("data", "{}");
			return map;
		}
		

		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),member));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userType"),UserType.member));
		
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		
		List<Information> informations = new ArrayList<Information>();
		informations = super.findPage(criteriaQuery, pageable).getContent();
		
		
		if(informations.size()<=0){
			map.put("status", "500");
			map.put("message", "暂无消息数据");
			map.put("data", "{}");
			return map;
		}
		
		Page<Information> page =  super.findPage(criteriaQuery, pageable);
		Integer count = page.getTotalPages();
		if (pageNumber>(count)) {
			map.put("status", "500");
			map.put("message", "无更多数据");
			map.put("data", "{}");
			return map;
		}
		
		List<Map<String,Object>> informationList = new ArrayList<Map<String,Object>>();
		for (Information information : informations) {
			Map<String,Object> information_map = new HashMap<String,Object>();	
			information_map.put("id",information.getId());
			System.out.println(information.getId());
			information_map.put("disposeState",information.getDisposeState());
			information_map.put("message",information.getMessage());
			information_map.put("informationType",information.getInformationType());
			information_map.put("state",information.getState());
			information_map.put("createDate",DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", information.getCreateDate()));
			information_map.put("headline",information.getHeadline());
			informationList.add(information_map);
		}
		Map<String,Object> information_map = new HashMap<String, Object>();
		information_map.put("informationList", informationList);
		map.put("status", "200");
		map.put("message", "查询成功");
		map.put("data", JsonUtils.toJson(information_map));
		return map;
	}

	@Override
	public Boolean isMemberUnread(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"), member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("state"), StateType.unread));
		criteriaQuery.where(restrictions);
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return true;
	    }
	    return false;
	}

	@Override
	public void memberRead(Member member) {
		StringBuffer sbf=new StringBuffer();
		sbf.append("update  xx_information_table informations set state = '0' where ");
		sbf.append("member = "+member.getId()+" ");
		sbf.append("and informations.user_type = 1 ");
		sbf.append(" and informations.is_deleted = 0 ");
		Session session =sessionFactory.openSession();
		String sql=sbf.toString();
		System.out.println(sql);
		session.createSQLQuery(sql).executeUpdate();
		session.close();
	}

	@Override
	public void doctorRead(Doctor doctor) {
		StringBuffer sbf=new StringBuffer();
		sbf.append("update  xx_information_table informations set state = '0' where ");
		sbf.append("doctor = "+doctor.getId()+" ");
		sbf.append("and informations.user_type = 0 ");
		sbf.append(" and informations.is_deleted = 0 ");
		Session session =sessionFactory.openSession();
		String sql=sbf.toString();
		System.out.println(sql);
		session.createSQLQuery(sql).executeUpdate();
		session.close();

	}

	@Override
	public Map<String, Object> getNewInformationByDoctor(Doctor doctor,
			Integer pageNumber, ClassifyType classifyType) {
		Map<String ,Object> map = new HashMap<String, Object>();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Integer pageSize = Config.pageSize;
		Pageable pageable = new Pageable();
		pageable.setPageNumber(pageNumber);
		pageable.setPageSize(pageSize);
		if(doctor.getInformations().size()<=0){
			map.put("status", "500");
			map.put("message", "无消息数据数据");
			map.put("data", "{}");
			return map;
		}
		

		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"),doctor));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userType"),UserType.doctor));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("classifyType"),classifyType));
		
		//如果是业务消息,需要区分机构
		if(ClassifyType.business.equals(classifyType)){
			if(null==doctor.getDefaultDoctorMechanismRelation()){
				map.put("status", "500");
				map.put("message", "该医生无审核通过的机构,暂无业务消息");
				map.put("data", "{}");
				return map;
			}
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("mechanism"),doctor.getDefaultDoctorMechanismRelation().getMechanism()));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		
		List<Information> informations = new ArrayList<Information>();
		informations = super.findPage(criteriaQuery, pageable).getContent();
		
		
		if(informations.size()<=0){
			map.put("status", "500");
			map.put("message", "暂无消息数据");
			map.put("data", "{}");
			return map;
		}
		
		Page<Information> page =  super.findPage(criteriaQuery, pageable);
		Integer count = page.getTotalPages();
		if (pageNumber>(count)) {
			map.put("status", "500");
			map.put("message", "无更多数据");
			map.put("data", "{}");
			return map;
		}
		
		List<Map<String,Object>> informationList = new ArrayList<Map<String,Object>>();
		for (Information information : informations) {
			Map<String,Object> information_map = new HashMap<String,Object>();	
			information_map.put("id",information.getId());
			System.out.println(information.getId());
			information_map.put("disposeState",information.getDisposeState());
			information_map.put("message",information.getMessage());
			information_map.put("informationType",information.getInformationType());
			information_map.put("state",information.getState());
			information_map.put("createDate",DateUtil.getDatetoString("yyyy-MM-dd", information.getCreateDate()));
			information_map.put("headline",information.getHeadline());
			information_map.put("orderId",information.getInformationId());
			informationList.add(information_map);
		}
		Map<String,Object> information_map = new HashMap<String, Object>();
		information_map.put("informationList", informationList);
		map.put("status", "200");
		map.put("message", "查询成功");
		map.put("data", JsonUtils.toJson(information_map));
		return map;
	}

	@Override
	public Information getDoctorRecentlySystemInformation(Doctor doctor) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("doctor"), doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("classifyType"), ClassifyType.system));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return iinformation_list.get(0);
	    }
	    return null;
	}

	@Override
	public Information getDoctorRecentlyBusinessInformation(Doctor doctor) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("doctor"), doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("classifyType"), ClassifyType.business));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return iinformation_list.get(0);
	    }
	    return null;
	}

	@Override
	public Information getDoctorRecentlyActivityInformation(Doctor doctor) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("doctor"), doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("classifyType"), ClassifyType.activity));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return iinformation_list.get(0);
	    }
	    return null;
	}

	@Override
	public Boolean getDoctorSystemInformationRead(Doctor doctor) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("doctor"), doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("state"), StateType.unread));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("classifyType"), ClassifyType.system));
		criteriaQuery.where(restrictions);
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return true;
	    }
	    return false;
	}

	@Override
	public Boolean getDoctorBusinessInformationRead(Doctor doctor) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("doctor"), doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("state"), StateType.unread));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("classifyType"), ClassifyType.business));
		criteriaQuery.where(restrictions);
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return true;
	    }
	    return false;
	}

	@Override
	public Boolean getDoctorActivityInformationRead(Doctor doctor) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("doctor"), doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.doctor));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("state"), StateType.unread));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("classifyType"), ClassifyType.activity));
		criteriaQuery.where(restrictions);
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return true;
	    }
	    return false;
	}

	@Override
	public Map<String, Object> getNewInformationByMember(Member member,
			Integer pageNumber, ClassifyType classifyType) {
		
		Map<String ,Object> map = new HashMap<String, Object>();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Integer pageSize = Config.pageSize;
		Pageable pageable = new Pageable();
		pageable.setPageNumber(pageNumber);
		pageable.setPageSize(pageSize);
		if(member.getInformations().size()<=0){
			map.put("status", "500");
			map.put("message", "无消息数据");
			map.put("data", "{}");
			return map;
		}
		

		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"),member));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userType"),UserType.member));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("classifyType"),classifyType));
		
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		
		List<Information> informations = new ArrayList<Information>();
		informations = super.findPage(criteriaQuery, pageable).getContent();
		
		
		if(informations.size()<=0){
			map.put("status", "500");
			map.put("message", "暂无消息数据");
			map.put("data", "{}");
			return map;
		}
		
		Page<Information> page =  super.findPage(criteriaQuery, pageable);
		Integer count = page.getTotalPages();
		if (pageNumber>(count)) {
			map.put("status", "500");
			map.put("message", "无更多数据");
			map.put("data", "{}");
			return map;
		}
		
		List<Map<String,Object>> informationList = new ArrayList<Map<String,Object>>();
		for (Information information : informations) {
			Map<String,Object> information_map = new HashMap<String,Object>();	
			information_map.put("id",information.getId());
			System.out.println(information.getId());
			information_map.put("disposeState",information.getDisposeState());
			information_map.put("message",information.getMessage());
			information_map.put("informationType",information.getInformationType());
			information_map.put("state",information.getState());
			information_map.put("createDate",DateUtil.getDatetoString("yyyy-MM-dd", information.getCreateDate()));
			information_map.put("headline",information.getHeadline());
			information_map.put("orderId",information.getInformationId());
			informationList.add(information_map);
		}
		Map<String,Object> information_map = new HashMap<String, Object>();
		information_map.put("informationList", informationList);
		map.put("status", "200");
		map.put("message", "查询成功");
		map.put("data", JsonUtils.toJson(information_map));
		return map;
	}

	@Override
	public Information getMemberRecentlySystemInformation(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"), member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("classifyType"), ClassifyType.system));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return iinformation_list.get(0);
	    }
	    return null;
	}

	@Override
	public Information getMemberRecentlyBusinessInformation(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"), member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("classifyType"), ClassifyType.business));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return iinformation_list.get(0);
	    }
	    return null;
	}

	@Override
	public Information getMemberRecentlyActivityInformation(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"), member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("classifyType"), ClassifyType.activity));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return iinformation_list.get(0);
	    }
	    return null;
	}

	@Override
	public Boolean getMemberSystemInformationRead(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"), member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("state"), StateType.unread));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("classifyType"), ClassifyType.system));
		criteriaQuery.where(restrictions);
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return true;
	    }
	    return false;
	}

	@Override
	public Boolean getMemberBusinessInformationRead(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"), member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("state"), StateType.unread));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("classifyType"), ClassifyType.business));
		criteriaQuery.where(restrictions);
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return true;
	    }
	    return false;
	}

	@Override
	public Boolean getMemberActivityInformationRead(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Information> criteriaQuery = criteriaBuilder.createQuery(Information.class);
		Root<Information> root = criteriaQuery.from(Information.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"), member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("userType"), UserType.member));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("state"), StateType.unread));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("classifyType"), ClassifyType.activity));
		criteriaQuery.where(restrictions);
		
	    List<Information> iinformation_list =  super.findList(criteriaQuery, null, null, null, null);
	    if(iinformation_list.size()>0){
	    	return true;
	    }
	    return false;
	}

	@Override
	public void doctorReadSystemActivity(Doctor doctor) {
		StringBuffer sbf=new StringBuffer();
		sbf.append("update  xx_information_table informations set state = '0' where ");
		sbf.append("doctor = "+doctor.getId()+" ");
		sbf.append("and informations.user_type = 0 ");
		sbf.append(" and informations.is_deleted = 0 ");
		sbf.append(" and informations.classify_type <> 1 ");
		Session session =sessionFactory.openSession();
		String sql=sbf.toString();
		System.out.println(sql);
		session.createSQLQuery(sql).executeUpdate();
		session.close();
		
	}

	@Override
	public void doctorReadBusiness(Doctor doctor) {
		if(null!=doctor.getDefaultDoctorMechanismRelation()){
			Mechanism mechanism = doctor.getDefaultDoctorMechanismRelation().getMechanism();
			StringBuffer sbf=new StringBuffer();
			sbf.append("update  xx_information_table informations set state = '0' where ");
			sbf.append("doctor = "+doctor.getId()+" ");
			sbf.append("and informations.user_type = 0 ");
			sbf.append(" and informations.is_deleted = 0 ");
			sbf.append(" and informations.classify_type = 1 ");
			sbf.append(" and informations.mechanism = "+mechanism.getId()+"");
			Session session =sessionFactory.openSession();
			String sql=sbf.toString();
			System.out.println(sql);
			session.createSQLQuery(sql).executeUpdate();
			session.close();
		}
	}

}