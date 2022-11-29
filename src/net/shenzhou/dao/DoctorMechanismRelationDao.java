/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;

/**
 * Dao - 医生机构关系表
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface DoctorMechanismRelationDao extends BaseDao<DoctorMechanismRelation, Long> {

	/**
	 * 机构端查找医生列表
	 * @param query_map
	 * @return
	 */
	Page<DoctorMechanismRelation> getPageMechanismDoctors(Map<String,Object>query_map);
	
	/**
	 * 导出医生技师
	 * @param query_map
	 * @return
	 */
	List<DoctorMechanismRelation> downloadList(Map<String,Object>query_map);
	
	
	
	
	/**
	 * 根据医生和机构获取关系实体
	 * @param query_map
	 * @return
	 */
	DoctorMechanismRelation getByDoctorMechanism(Doctor doctor,Mechanism mechanism);
	

	/**
	 * 根据医生和机构获取集合
	 * @param query_map
	 * @return
	 */
	List<DoctorMechanismRelation> getDoctorMechanism(Doctor doctor);
}