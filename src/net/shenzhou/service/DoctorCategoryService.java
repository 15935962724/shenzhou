/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import net.shenzhou.Page;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.ProductCategory;

/**
 * Service - 医生分类(级别)
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface DoctorCategoryService extends BaseService<DoctorCategory, Long> {
	
	/**
	 * 查找医生分类树
	 * 
	 * @return 医生分类树
	 */
	List<DoctorCategory> findTree();
	
	
	/**
	 * 查找下级分类
	 * 
	 * @param droductCategory
	 *            医生级别分类
	 * @return 下级分类
	 */
	List<DoctorCategory> findChildren(DoctorCategory droductCategory);
	

	/**
	 * 查找机构下的所欲职称分类
	 * @param query_map
	 * @return
	 */
	Page<DoctorCategory> findPage(Map<String, Object> query_map);
	
	/**
	 * 查找平台发布的医生职级(职称分类)
	 * @param query_map
	 * @return
	 */
	List<DoctorCategory> findList(Map<String, Object> query_map);
	
	
	/**
	 * 查找全部分类
	 * @return
	 */
	List<DoctorCategory> find();
	
}