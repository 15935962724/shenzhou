/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

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
 * Service - 新闻
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface JournalismService extends BaseService<Journalism, Long> {

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
	 * @return 仅包含已发布新闻
	 */
	List<Journalism> findList(JournalismCategory journalismCategory, List<Tag> tags, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找新闻(缓存)
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
	 * @param cacheRegion
	 *            缓存区域
	 * @return 仅包含已发布新闻
	 */
	List<Journalism> findList(JournalismCategory journalismCategory, List<Tag> tags, Integer count, List<Filter> filters, List<Order> orders, String cacheRegion);

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
	 * @return 仅包含已发布新闻
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
	 * @return 仅包含已发布新闻
	 */
	Page<Journalism> findPage(JournalismCategory journalismCategory, List<Tag> tags, Pageable pageable);

	/**
	 * 查看并更新点击数
	 * 
	 * @param id
	 *            ID
	 * @return 点击数
	 */
	long viewHits(Long id);

}