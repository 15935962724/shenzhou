/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.Date;
import java.util.List;

import net.shenzhou.Filter;
import net.shenzhou.Order;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Journalism;
import net.shenzhou.entity.JournalismCategory;
import net.shenzhou.entity.Tag;

/**
 * Dao - 新闻
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface JournalismDao extends BaseDao<Journalism, Long> {

	/**
	 * 查找新闻
	 * 
	 * @param journalismCategory
	 *            新闻分类
	 * @param tags
	 *            标签
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 新闻
	 */
	List<Journalism> findList(JournalismCategory journalismCategory, List<Tag> tags, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找新闻
	 * 
	 * @param journalismCategory
	 *            新闻分类
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @return 新闻
	 */
	List<Journalism> findList(JournalismCategory journalismCategory, Date beginDate, Date endDate, Integer first, Integer count);

	/**
	 * 查找新闻分页
	 * 
	 * @param journalismCategory
	 *            新闻分类
	 * @param tags
	 *            标签
	 * @param pageable
	 *            分页信息
	 * @return 新闻分页
	 */
	Page<Journalism> findPage(JournalismCategory journalismCategory, List<Tag> tags, Pageable pageable);

}