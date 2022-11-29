/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.dao.InformationDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Information;
import net.shenzhou.entity.Information.ClassifyType;
import net.shenzhou.entity.Member;
import net.shenzhou.service.InformationService;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Service;

/**
 * Service - 账单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("informationServiceImpl")
public class InformationServiceImpl extends BaseServiceImpl<Information, Long> implements InformationService {

	@Resource(name = "informationDaoImpl")
	private InformationDao informationDao;

	@Resource(name = "informationDaoImpl")
	public void setBaseDao(InformationDao informationDao) {
		super.setBaseDao(informationDao);
	}

	@Override
	public Map<String ,Object> getInformationByDoctor(Doctor doctor , Integer pageNumber) {
		// TODO Auto-generated method stub
		return informationDao.getInformationByDoctor(doctor,pageNumber);
	}

	@Override
	public Boolean isDoctorUnread(Doctor doctor) {
		return informationDao.isDoctorUnread(doctor);
	}

	@Override
	public Map<String, Object> getInformationByMember(Member member,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		return informationDao.getInformationByMember(member,pageNumber);
	}

	@Override
	public Boolean isMemberUnread(Member member) {
		// TODO Auto-generated method stub
		return informationDao.isMemberUnread(member);
	}

	@Override
	public void memberRead(Member member) {
		informationDao.memberRead(member);
		
	}

	@Override
	public void doctorRead(Doctor doctor) {
		informationDao.doctorRead(doctor);
		
	}

	@Override
	public Map<String, Object> getNewInformationByDoctor(Doctor doctor,
			Integer pageNumber, ClassifyType classifyType) {
		// TODO Auto-generated method stub
		return informationDao.getNewInformationByDoctor(doctor,pageNumber,classifyType);
	}

	@Override
	public Map<String, Object> getDoctorRecentlyInformation(Doctor doctor) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		Information systemInformation = informationDao.getDoctorRecentlySystemInformation(doctor);
		Information businessInformation = informationDao.getDoctorRecentlyBusinessInformation(doctor);
		Information activityInformation = informationDao.getDoctorRecentlyActivityInformation(doctor);
		
		Map<String,Object> map_system = new HashMap<String, Object>();
		Map<String,Object> map_business = new HashMap<String, Object>();
		Map<String,Object> map_activity = new HashMap<String, Object>();
		
		if(null!=systemInformation){
			map_system.put("createDate", DateUtil.getDatetoString("yyyy-MM-dd", systemInformation.getCreateDate()));
			map_system.put("message", systemInformation.getMessage());
			map_system.put("read", informationDao.getDoctorSystemInformationRead(doctor));
		}else{
			map_system.put("createDate", "");
			map_system.put("message", "");
			map_system.put("read", false);
		}
		
		if(null!=businessInformation){
			map_business.put("createDate", DateUtil.getDatetoString("yyyy-MM-dd", businessInformation.getCreateDate()));
			map_business.put("message", businessInformation.getMessage());
			map_business.put("read", informationDao.getDoctorBusinessInformationRead(doctor));
		}else{
			map_business.put("createDate", "");
			map_business.put("message", "");
			map_business.put("read", false);
		}
		
		if(null!=activityInformation){
			map_activity.put("createDate", DateUtil.getDatetoString("yyyy-MM-dd", activityInformation.getCreateDate()));
			map_activity.put("message", activityInformation.getMessage());
			map_activity.put("read", informationDao.getDoctorActivityInformationRead(doctor));
		}else{
			map_activity.put("createDate", "");
			map_activity.put("message", "");
			map_activity.put("read", false);
		}
		
		map.put("systemMap", map_system);
		map.put("businessMap", map_business);
		map.put("activityMap", map_activity);
		
		return map;
	}

	@Override
	public Boolean newIsDoctorUnread(Doctor doctor) {
		
		Boolean business = informationDao.getDoctorBusinessInformationRead(doctor);
		Boolean system = informationDao.getDoctorSystemInformationRead(doctor);
		Boolean activity = informationDao.getDoctorActivityInformationRead(doctor);
		
		if(business==true||system==true||activity==true){
			return true;
		}
		
		return false;
	}

	@Override
	public Map<String, Object> getNewInformationByMember(Member member,
			Integer pageNumber, ClassifyType classifyType) {
		
		return informationDao.getNewInformationByMember(member,pageNumber,classifyType);
	}

	@Override
	public Map<String, Object> getMemberRecentlyInformation(Member member) {
Map<String,Object> map = new HashMap<String, Object>();
		
		Information systemInformation = informationDao.getMemberRecentlySystemInformation(member);
		Information businessInformation = informationDao.getMemberRecentlyBusinessInformation(member);
		Information activityInformation = informationDao.getMemberRecentlyActivityInformation(member);
		
		Map<String,Object> map_system = new HashMap<String, Object>();
		Map<String,Object> map_business = new HashMap<String, Object>();
		Map<String,Object> map_activity = new HashMap<String, Object>();
		
		if(null!=systemInformation){
			map_system.put("createDate", DateUtil.getDatetoString("yyyy-MM-dd", systemInformation.getCreateDate()));
			map_system.put("message", systemInformation.getMessage());
			map_system.put("read", informationDao.getMemberSystemInformationRead(member));
		}else{
			map_system.put("createDate", "");
			map_system.put("message", "");
			map_system.put("read", false);
		}
		
		if(null!=businessInformation){
			map_business.put("createDate", DateUtil.getDatetoString("yyyy-MM-dd", businessInformation.getCreateDate()));
			map_business.put("message", businessInformation.getMessage());
			map_business.put("read", informationDao.getMemberBusinessInformationRead(member));
		}else{
			map_business.put("createDate", "");
			map_business.put("message", "");
			map_business.put("read", false);
		}
		
		if(null!=activityInformation){
			map_activity.put("createDate", DateUtil.getDatetoString("yyyy-MM-dd", activityInformation.getCreateDate()));
			map_activity.put("message", activityInformation.getMessage());
			map_activity.put("read", informationDao.getMemberActivityInformationRead(member));
		}else{
			map_activity.put("createDate", "");
			map_activity.put("message", "");
			map_activity.put("read", false);
		}
		
		map.put("systemMap", map_system);
		map.put("businessMap", map_business);
		map.put("activityMap", map_activity);
		
		return map;
	}

	@Override
	public Boolean newIsMemberUnread(Member member) {
		Boolean business = informationDao.getMemberSystemInformationRead(member);
		Boolean system = informationDao.getMemberBusinessInformationRead(member);
		Boolean activity = informationDao.getMemberActivityInformationRead(member);
		
		if(business==true||system==true||activity==true){
			return true;
		}
		
		return false;
	}

	@Override
	public void newDoctorRead(Doctor doctor) {
		informationDao.doctorReadSystemActivity(doctor);
		informationDao.doctorReadBusiness(doctor);
	}

	@Override
	public void doctorReadSystem(Doctor doctor) {
		informationDao.doctorReadSystemActivity(doctor);
	}

	@Override
	public void doctorReadBusiness(Doctor doctor) {
		informationDao.doctorReadBusiness(doctor);
	}

}
