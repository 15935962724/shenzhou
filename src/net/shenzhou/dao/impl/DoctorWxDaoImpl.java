/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.DoctorWxDao;
import net.shenzhou.dao.MechanismDao;
import net.shenzhou.dao.ProjectDao;
import net.shenzhou.dao.WorkDayDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Doctor.Status;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorWx;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Project.Audit;
import net.shenzhou.entity.Project.Mode;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDay.WorkType;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.DoctorUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MechanismUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author wsr
 *
 */
@Repository("doctorWxDaoImpl")
public class DoctorWxDaoImpl extends BaseDaoImpl<DoctorWx, Long> implements DoctorWxDao {

	@Override
	public DoctorWx findByOpenid(String openid) {
		if (openid == null) {
			return null;
		}
		try {
			String jpql = "select doctorWx from DoctorWx doctorWx where lower(doctorWx.openid) = lower(:openid)";
			return entityManager.createQuery(jpql, DoctorWx.class).setFlushMode(FlushModeType.COMMIT).setParameter("openid", openid).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	
	
	
}