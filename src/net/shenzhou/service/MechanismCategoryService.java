/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;

import net.shenzhou.entity.MechanismCategory;


/**
 * 2017-6-24 17:32:30
 * 机构类型
 * @author wsr
 *
 */
public interface MechanismCategoryService extends BaseService<MechanismCategory, Long> {
	
	
	
	/**
	 * 查找顶级分类
	 * 
	 * @return 顶级分类
	 */
	List<MechanismCategory> findRoots();
	
	
	/**
	 * 查找顶级机构分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级机构分类
	 */
	List<MechanismCategory> findRoots(Integer count);

	/**
	 * 查找顶级机构分类(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 顶级机构分类(缓存)
	 */
	List<MechanismCategory> findRoots(Integer count, String cacheRegion);

	/**
	 * 查找上级机构分类
	 * 
	 * @param productCategory
	 *            机构分类
	 * @return 上级机构分类
	 */
	List<MechanismCategory> findParents(MechanismCategory mechanismCategory);

	/**
	 * 查找上级机构分类
	 * 
	 * @param productCategory
	 *            机构分类
	 * @param count
	 *            数量
	 * @return 上级机构分类
	 */
	List<MechanismCategory> findParents(MechanismCategory mechanismCategory, Integer count);

	/**
	 * 查找上级机构分类(缓存)
	 * 
	 * @param productCategory
	 *            机构分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 上级机构分类(缓存)
	 */
	List<MechanismCategory> findParents(MechanismCategory mechanismCategory, Integer count, String cacheRegion);

	/**
	 * 查找机构分类树
	 * 
	 * @return 机构分类树
	 */
	List<MechanismCategory> findTree();

	/**
	 * 查找下级机构分类
	 * 
	 * @param productCategory
	 *            机构分类
	 * @return 下级机构分类
	 */
	List<MechanismCategory> findChildren(MechanismCategory mechanismCategory);

	/**
	 * 查找下级机构分类
	 * 
	 * @param productCategory
	 *            机构分类
	 * @param count
	 *            数量
	 * @return 下级机构分类
	 */
	List<MechanismCategory> findChildren(MechanismCategory mechanismCategory, Integer count);

	/**
	 * 查找下级机构分类(缓存)
	 * 
	 * @param productCategory
	 *            机构分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 下级机构分类(缓存)
	 */
	List<MechanismCategory> findChildren(MechanismCategory mechanismCategory, Integer count, String cacheRegion);
	
	
}