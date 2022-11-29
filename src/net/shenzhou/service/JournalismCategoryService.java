/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;

import net.shenzhou.entity.JournalismCategory;

/**
 * Service - 新闻分类
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface JournalismCategoryService extends BaseService<JournalismCategory, Long> {

	/**
	 * 查找顶级新闻分类
	 * 
	 * @return 顶级新闻分类
	 */
	List<JournalismCategory> findRoots();

	/**
	 * 查找顶级新闻分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级新闻分类
	 */
	List<JournalismCategory> findRoots(Integer count);

	/**
	 * 查找顶级新闻分类(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 顶级新闻分类(缓存)
	 */
	List<JournalismCategory> findRoots(Integer count, String cacheRegion);

	/**
	 * 查找上级新闻分类
	 * 
	 * @param JournalismCategory
	 *            新闻分类
	 * @return 上级新闻分类
	 */
	List<JournalismCategory> findParents(JournalismCategory JournalismCategory);

	/**
	 * 查找上级新闻分类
	 * 
	 * @param JournalismCategory
	 *            新闻分类
	 * @param count
	 *            数量
	 * @return 上级新闻分类
	 */
	List<JournalismCategory> findParents(JournalismCategory JournalismCategory, Integer count);

	/**
	 * 查找上级新闻分类(缓存)
	 * 
	 * @param JournalismCategory
	 *            新闻分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 上级新闻分类(缓存)
	 */
	List<JournalismCategory> findParents(JournalismCategory JournalismCategory, Integer count, String cacheRegion);

	/**
	 * 查找新闻分类树
	 * 
	 * @return 新闻分类树
	 */
	List<JournalismCategory> findTree();

	/**
	 * 查找下级新闻分类
	 * 
	 * @param JournalismCategory
	 *            新闻分类
	 * @return 下级新闻分类
	 */
	List<JournalismCategory> findChildren(JournalismCategory JournalismCategory);

	/**
	 * 查找下级新闻分类
	 * 
	 * @param JournalismCategory
	 *            新闻分类
	 * @param count
	 *            数量
	 * @return 下级新闻分类
	 */
	List<JournalismCategory> findChildren(JournalismCategory JournalismCategory, Integer count);

	/**
	 * 查找下级新闻分类(缓存)
	 * 
	 * @param JournalismCategory
	 *            新闻分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 下级新闻分类(缓存)
	 */
	List<JournalismCategory> findChildren(JournalismCategory JournalismCategory, Integer count, String cacheRegion);

}