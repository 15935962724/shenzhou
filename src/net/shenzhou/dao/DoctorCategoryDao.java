/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.DoctorCategory;

/**
 * Dao - 医生职称
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface DoctorCategoryDao extends BaseDao<DoctorCategory, Long> {

	/**
	 * 查找下级医生分类
	 * 
	 * @param doctorCategory
	 *            医生分类
	 * @param count
	 *            数量
	 * @return 下级医生分类
	 */
	List<DoctorCategory> findChildren(DoctorCategory doctorCategory, Integer count);
	
	/**
	 * 查找机构下的所欲职称分类
	 * @param query_map
	 * @return
	 */
	Page<DoctorCategory> findPage(Map<String,Object> query_map);
	
	/**
	 * 查找平台的添加的医生职级(医生分类)
	 * @param query_map
	 * @return
	 */
	List<DoctorCategory> findList(Map<String,Object> query_map);
	/**
	 * 获取全部分类
	 * @return
	 */
	List<DoctorCategory> find();
	
}