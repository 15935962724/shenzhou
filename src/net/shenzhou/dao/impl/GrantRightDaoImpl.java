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

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.Config;
import net.shenzhou.Pageable;
import net.shenzhou.dao.AssessReportDao;
import net.shenzhou.dao.GrantRightDao;
import net.shenzhou.dao.MemberDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.GrantRight;
import net.shenzhou.entity.Member;
import net.shenzhou.util.JsonUtils;

import org.springframework.stereotype.Repository;

/**
 * 授权
 * 2017-6-14 09:52:58
 * @author wsr
 *
 */
@Repository("grantRightDaoImpl")
public class GrantRightDaoImpl extends BaseDaoImpl<GrantRight, Long> implements GrantRightDao {

	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	
	@Resource(name = "assessReportDaoImpl")
	private AssessReportDao assessReportDao;
	
	
	@Override
	public Map<String, Object> findList(Map<String, Object> map) {
		Map<String, Object> data_map = new HashMap<String, Object>();
//		String whetherAllow = map.get("whetherAllow").toString();
		String patientMemberId =  map.get("patientMemberId").toString();
		Integer pageNumber = (Integer) map.get("pageNumber");
		Member member = (Member) map.get("member");
		Pageable pageable = new Pageable();
		pageable.setPageNumber(pageNumber);
		pageable.setPageSize(Config.pageSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrantRight> criteriaQuery = criteriaBuilder.createQuery(GrantRight.class);
		Root<GrantRight> root = criteriaQuery.from(GrantRight.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("patientMember")).value(member.getChildren()));
//		if (whetherAllow!=null&&!whetherAllow.equals("")) {
//			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("whetherAllow"), WhetherAllow.valueOf(whetherAllow)));
//		} 
		if (patientMemberId != null&&!patientMemberId.equals("")) {
			Member patientMember = memberDao.find(Long.valueOf(patientMemberId));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("patientMember"), patientMember));
		}
		criteriaQuery.where(restrictions);
		List<GrantRight> grantRights =  super.findPage(criteriaQuery, pageable).getContent();
		List<Map<String,Object> > list_grantRights = new ArrayList<Map<String,Object>>();
		for (GrantRight grantRight : grantRights) {
			Map<String,Object> data_grantRight = new HashMap<String, Object>();
			data_grantRight.put("doctorId", grantRight.getDoctor().getId());//医生id
			data_grantRight.put("doctorCategoryTile", grantRight.getDoctor().getDoctorCategory().getTitle());//医生职位
			data_grantRight.put("mechanismName", grantRight.getDoctor().getMechanism().getName());//机构名称
			data_grantRight.put("doctorLogo", grantRight.getDoctor().getLogo());//医生logo
			data_grantRight.put("doctorName", grantRight.getDoctor().getName());//医生姓名
			data_grantRight.put("doctorGender", grantRight.getDoctor().getGender());//医生性别
			data_grantRight.put("doctorIntroduce", grantRight.getDoctor().getIntroduce());//医生介绍
			data_grantRight.put("patientMemberName", grantRight.getPatientMember().getName());//医生患者名字
			data_grantRight.put("whetherAllow", grantRight.getWhetherAllow());//状态
			data_grantRight.put("doctorCategoryName", grantRight.getDoctor().getDoctorCategory().getTitle());//医生职称
			list_grantRights.add(data_grantRight);
		}
		
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("grantRights", list_grantRights);
		data_map.put("status", "200");
		data_map.put("message","数据加载成功");
		data_map.put("data",JsonUtils.toJson(data));
		return data_map;
	}


	@Override
	public boolean findByDoctor(Doctor doctor, Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrantRight> criteriaQuery = criteriaBuilder.createQuery(GrantRight.class);
		Root<GrantRight> root = criteriaQuery.from(GrantRight.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (doctor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"),  doctor));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("patientMember"),  member));
		}
		criteriaQuery.select(root);
		criteriaQuery.where(restrictions);
		
		List<GrantRight> grantRightList = super.findList(criteriaQuery, null, null, null, null);
		if(grantRightList.size()>0){
			return true;
		}
		return false;
	}


	@Override
	public GrantRight findByDoctorAndMember(Doctor doctor, Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrantRight> criteriaQuery = criteriaBuilder.createQuery(GrantRight.class);
		Root<GrantRight> root = criteriaQuery.from(GrantRight.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (doctor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("doctor"),  doctor));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("patientMember"),  member));
		}
		criteriaQuery.select(root);
		criteriaQuery.where(restrictions);
		
		List<GrantRight> grantRightList = super.findList(criteriaQuery, null, null, null, null);
		
		if(grantRightList.size()>0){
			return grantRightList.get(0);
		}
		return null;
	}


	


	

}