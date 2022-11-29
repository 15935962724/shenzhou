/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Page;
import net.shenzhou.dao.DoctorWxDao;
import net.shenzhou.dao.ProjectDao;
import net.shenzhou.dao.ServerProjectCategoryDao;
import net.shenzhou.dao.WorkDayDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Doctor.Status;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorWx;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDay.WorkType;
import net.shenzhou.service.DoctorWxService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MechanismUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Service - 医生
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("doctorWxServiceImpl")
public class DoctorWxServiceImpl extends BaseServiceImpl<DoctorWx, Long> implements DoctorWxService {

	@Resource(name = "doctorWxDaoImpl")
	private DoctorWxDao doctorWxDao;

	@Resource(name = "doctorWxDaoImpl")
	public void setBaseDao(DoctorWxDao doctorWxDao) {
		super.setBaseDao(doctorWxDao);
	}

	@Override
	public DoctorWx findByOpenid(String openid) {
		// TODO Auto-generated method stub
		return doctorWxDao.findByOpenid(openid);
	}
	
	


	
	
}
