/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.ProductCategory;
import net.shenzhou.entity.ServerProjectCategory;

/**
 * 服务项目
 * @author wsr
 *
 */
public interface ServerProjectCategoryService extends BaseService<ServerProjectCategory, Long> {


	/**
	 * 查找顶级项目分类
	 * 
	 * @return 顶级项目分类
	 */
	List<ServerProjectCategory> findRoots();

	/**
	 * 查找顶级项目分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级项目分类
	 */
	List<ServerProjectCategory> findRoots(Integer count);

	/**
	 * 查找顶级项目分类(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 顶级项目分类(缓存)
	 */
	List<ServerProjectCategory> findRoots(Integer count, String cacheRegion);

	/**
	 * 查找上级项目分类
	 * 
	 * @param productCategory
	 *            项目分类
	 * @return 上级项目分类
	 */
	List<ServerProjectCategory> findParents(ServerProjectCategory serverProjectCategory);

	/**
	 * 查找上级项目分类
	 * 
	 * @param productCategory
	 *            项目分类
	 * @param count
	 *            数量
	 * @return 上级项目分类
	 */
	List<ServerProjectCategory> findParents(ServerProjectCategory serverProjectCategory, Integer count);

	/**
	 * 查找上级项目分类(缓存)
	 * 
	 * @param productCategory
	 *            项目分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 上级项目分类(缓存)
	 */
	List<ServerProjectCategory> findParents(ServerProjectCategory serverProjectCategory, Integer count, String cacheRegion);

	/**
	 * 查找项目分类树
	 * 
	 * @return 项目分类树
	 */
	List<ServerProjectCategory> findTree();

	/**
	 * 查找下级项目分类
	 * 
	 * @param productCategory
	 *            项目分类
	 * @return 下级项目分类
	 */
	List<ServerProjectCategory> findChildren(ServerProjectCategory serverProjectCategory);

	/**
	 * 查找下级项目分类
	 * 
	 * @param productCategory
	 *            项目分类
	 * @param count
	 *            数量
	 * @return 下级项目分类
	 */
	List<ServerProjectCategory> findChildren(ServerProjectCategory serverProjectCategory, Integer count);

	/**
	 * 查找下级项目分类(缓存)
	 * 
	 * @param productCategory
	 *            项目分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 下级项目分类(缓存)
	 */
	List<ServerProjectCategory> findChildren(ServerProjectCategory serverProjectCategory, Integer count, String cacheRegion);

	/**
	 * 查找本机构下的项目分类
	 * @param mechanism
	 * @return
	 */
	List<ServerProjectCategory> getServerProjectCategory(Mechanism mechanism);
	
	/**
	 * 查找系统后台发布的项目分类
	 * @return
	 */
	List<ServerProjectCategory> getServerProjectCategory();
	
	/**
	 * 机构端查找项目分类
	 * @param query_map
	 * @return
	 */
	Page<ServerProjectCategory> getServerProjectCategory(
			Map<String, Object> query_map);
	
	
	/**
	 * 查询项目全部分类
	 * @return
	 */
	
	List<ServerProjectCategory> serverProjectCategoryList();
	
	/**
	 * 查找最顶级
	 * @param serverProjectCategory
	 * @return
	 */
	ServerProjectCategory findParent(ServerProjectCategory serverProjectCategory);
	
	

}