/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;

import net.shenzhou.entity.DoctorRank;

/**
 * Service - 专家级别
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface DoctorRankService extends BaseService<DoctorRank, Long> {




	/**
	 * 查找顶级专家级别
	 * 
	 * @return 顶级专家级别
	 */
	List<DoctorRank> findRoots();

	/**
	 * 查找顶级专家级别
	 * 
	 * @param count
	 *            数量
	 * @return 顶级专家级别
	 */
	List<DoctorRank> findRoots(Integer count);

	/**
	 * 查找顶级专家级别(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 顶级专家级别(缓存)
	 */
	List<DoctorRank> findRoots(Integer count, String cacheRegion);

	/**
	 * 查找上级专家级别
	 * 
	 * @param productCategory
	 *            专家级别
	 * @return 上级专家级别
	 */
	List<DoctorRank> findParents(DoctorRank doctorRank);

	/**
	 * 查找上级专家级别
	 * 
	 * @param productCategory
	 *            专家级别
	 * @param count
	 *            数量
	 * @return 上级专家级别
	 */
	List<DoctorRank> findParents(DoctorRank doctorRank, Integer count);

	/**
	 * 查找上级专家级别(缓存)
	 * 
	 * @param productCategory
	 *            专家级别
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 上级专家级别(缓存)
	 */
	List<DoctorRank> findParents(DoctorRank doctorRank, Integer count, String cacheRegion);

	/**
	 * 查找专家级别树
	 * 
	 * @return 专家级别树
	 */
	List<DoctorRank> findTree();

	/**
	 * 查找下级专家级别
	 * 
	 * @param productCategory
	 *            专家级别
	 * @return 下级专家级别
	 */
	List<DoctorRank> findChildren(DoctorRank doctorRank);

	/**
	 * 查找下级专家级别
	 * 
	 * @param productCategory
	 *            专家级别
	 * @param count
	 *            数量
	 * @return 下级专家级别
	 */
	List<DoctorRank> findChildren(DoctorRank doctorRank, Integer count);

	/**
	 * 查找下级专家级别(缓存)
	 * 
	 * @param productCategory
	 *            专家级别
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 下级专家级别(缓存)
	 */
	List<DoctorRank> findChildren(DoctorRank doctorRank, Integer count, String cacheRegion);



	
}