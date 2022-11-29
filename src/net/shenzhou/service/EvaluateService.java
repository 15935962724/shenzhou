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
import net.shenzhou.entity.Evaluate;
import net.shenzhou.entity.Project;

/**
 * 评价
 * 2017-06-05 17:42:33
 * @author wsr
 *
 */
public interface EvaluateService extends BaseService<Evaluate, Long> {
	
	List<Evaluate> findList(Doctor doctor);

	
	Object findObject(Project project);
	
	
	Page<Evaluate> getDoctorEvaluate(Map<String,Object> query_map);
	
	/**
	 * 机构端评价信息
	 * @param query_map
	 * @return
	 */
	Page<Evaluate> getPageEvaluate(Map<String, Object> query_map);
	
	/**
	 * 机构端评价统计
	 * @param query_map
	 * @return
	 */
	Page<Evaluate> getPageEvaluateCharge(Map<String, Object> query_map);
	
	
	/**
	 * 导出机构端评价统计
	 * @param query_map
	 * @return
	 */
	List<Evaluate> getDownloadList(Map<String, Object> query_map);


	/**
	 * 获取医生评价信息
	 * @param query_map
	 * @return
	 */
	List<Evaluate> getDoctorEvaluateList(Map<String, Object> query_map);


}