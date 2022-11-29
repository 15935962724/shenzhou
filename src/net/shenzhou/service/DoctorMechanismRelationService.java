/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;

/**
 * Service - 医生机构关系表
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface DoctorMechanismRelationService extends BaseService<DoctorMechanismRelation, Long> {

	
	/**
	 * 根据ID查找权限
	 * 
	 * @param id
	 *            ID
	 * @return 权限,若不存在则返回null
	 */
	List<String> findAuthorities(Long id);
	
	
	/**
	 * 机构端根据姓名 或电话  级别  性别 服务项目 查询医生
	 * @param query_map
	 * @return
	 */
	Page<DoctorMechanismRelation> getPageMechanismDoctors(
			Map<String, Object> query_map);
	
	
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
	 * 根据医生和机构获取数据
	 * @param query_map
	 * @return
	 */
	List<DoctorMechanismRelation> getDoctorMechanism(Doctor doctor);
	
}