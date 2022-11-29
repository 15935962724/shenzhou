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
import net.shenzhou.entity.Evaluate;
import net.shenzhou.entity.Project;

/**
 * 
 * @author wsr
 * 2017-6-5 16:44:22
 * Dao 评论
 *
 */
public interface EvaluateDao extends BaseDao<Evaluate, Long> {

	List<Evaluate> findList(Doctor doctor);
	
	Object findObject(Project project);
	
	
	/**
	 * 机构端查询医师的评价信息
	 * @param query_map
	 * @return
	 */
	Page<Evaluate> getDoctorEvaluate(Map<String,Object> query_map);
	
	/**
	 * 机构端评价信息(2018-1-24 14:36:29废弃 wsr)
	 * @param query_map
	 * @return
	 */
	Page<Evaluate> getPageEvaluate(Map<String,Object> query_map);
	
	/**
	 * 评价统计
	 * @param query_map
	 * @return
	 */
	Page<Evaluate> getPageEvaluateCharge(Map<String,Object> query_map);
	
	/**
	 * 导出评价统计
	 * @param query_map
	 * @return
	 */
	List<Evaluate> getDownloadList(Map<String,Object> query_map);
	
	
	/**
	 * 获取医生评价信息
	 * @param query_map
	 * @return
	 */
	List<Evaluate> getDoctorEvaluateList(Map<String, Object> query_map);

	
}