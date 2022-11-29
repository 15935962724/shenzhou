/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.ServerProjectCategory;

/**
 * 服务项目
 * @author wsr
 *
 */
public interface ServerProjectCategoryDao extends BaseDao<ServerProjectCategory, Long> {

	/**
	 * 查找顶级项目分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级项目分类
	 */
	List<ServerProjectCategory> findRoots(Integer count);

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
	 * 查找本机构下的项目分类
	 * 
	 * @param count
	 *            数量
	 * @return 本机构下的项目分类
	 */
	List<ServerProjectCategory> getServerProjectCategory(Mechanism mechanism);
	
	/**
	 * 查找系统后台发布的项目分类
	 * 
	 * @param 
	 * @return 系统后台发布项目分类
	 */
	List<ServerProjectCategory> getServerProjectCategory();
	
	/**
	 * 查找本机构下的项目分类
	 * 
	 *            数量
	 * @return 本机构下的项目分类
	 */
	Page<ServerProjectCategory> getServerProjectCategory(Map<String,Object> query_map );
	
	/**
	 * 查询全部
	 * @return
	 */
	List<ServerProjectCategory> serverProjectCategoryList();
	

	
	
	

}