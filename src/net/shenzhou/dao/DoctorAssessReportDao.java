/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorAssessReport;
import net.shenzhou.entity.Member;

/**
 * 授权详情
 * 2017-6-16 09:49:17
 * @author wsr
 *
 */
public interface DoctorAssessReportDao extends BaseDao<DoctorAssessReport, Long> {

	List<DoctorAssessReport> findList(Map<String,Object> map);
	
	
	/**
	 * 根据医生和患者 获取所有病历
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	List<DoctorAssessReport> findByDoctorAndMember(Doctor doctor,Member member);
}