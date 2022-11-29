/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;

import net.shenzhou.entity.Ad;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.ProductCategory;

/**
 * 机构类型
 * @author wsr
 *
 */
public interface MechanismCategoryDao extends BaseDao<MechanismCategory, Long> {

	
	/**
	 * 查找顶级分类
	 * @param count
	 *            数量
	 * @return 顶级分类
	 */
	List<MechanismCategory> findRoots(Integer count);
	

	/**
	 * 查找上级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @return 上级商品分类
	 */
	List<MechanismCategory> findParents(MechanismCategory mechanismCategory, Integer count);

	/**
	 * 查找下级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @return 下级商品分类
	 */
	List<MechanismCategory> findChildren(MechanismCategory mechanismCategory, Integer count);

	
	
	
	
	
}