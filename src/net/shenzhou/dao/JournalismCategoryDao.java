/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;

import net.shenzhou.entity.JournalismCategory;

/**
 * Dao - 新闻分类
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface JournalismCategoryDao extends BaseDao<JournalismCategory, Long> {

	/**
	 * 查找顶级新闻分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级新闻分类
	 */
	List<JournalismCategory> findRoots(Integer count);

	/**
	 * 查找上级新闻分类
	 * 
	 * @param JournalismCategory
	 *            新闻分类
	 * @param count
	 *            数量
	 * @return 上级新闻分类
	 */
	List<JournalismCategory> findParents(JournalismCategory journalismCategory, Integer count);

	/**
	 * 查找下级新闻分类
	 * 
	 * @param JournalismCategory
	 *            新闻分类
	 * @param count
	 *            数量
	 * @return 下级新闻分类
	 */
	List<JournalismCategory> findChildren(JournalismCategory journalismCategory, Integer count);

}