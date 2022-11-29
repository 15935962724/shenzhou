/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.shenzhou.Filter;
import net.shenzhou.Order;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.JournalismDao;
import net.shenzhou.entity.Journalism;
import net.shenzhou.entity.JournalismCategory;
import net.shenzhou.entity.Tag;
import net.shenzhou.service.JournalismService;
import net.shenzhou.service.StaticService;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
/**
 * Service - 新闻
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("journalismServiceImpl")
public class JournalismServiceImpl extends BaseServiceImpl<Journalism, Long> implements JournalismService, DisposableBean {

	/** 查看点击数时间 */
	private long viewHitsTime = System.currentTimeMillis();

	@Resource(name = "ehCacheManager")
	private CacheManager cacheManager;
	@Resource(name = "journalismDaoImpl")
	private JournalismDao journalismDao;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	@Resource(name = "journalismDaoImpl")
	public void setBaseDao(JournalismDao journalismDao) {
		super.setBaseDao(journalismDao);
	}

	@Transactional(readOnly = true)
	public List<Journalism> findList(JournalismCategory journalismCategory, List<Tag> tags, Integer count, List<Filter> filters, List<Order> orders) {
		return journalismDao.findList(journalismCategory, tags, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable("journalism")
	public List<Journalism> findList(JournalismCategory journalismCategory, List<Tag> tags, Integer count, List<Filter> filters, List<Order> orders, String cacheRegion) {
		return journalismDao.findList(journalismCategory, tags, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public List<Journalism> findList(JournalismCategory journalismCategory, Date beginDate, Date endDate, Integer first, Integer count) {
		return journalismDao.findList(journalismCategory, beginDate, endDate, first, count);
	}

	@Transactional(readOnly = true)
	public Page<Journalism> findPage(JournalismCategory journalismCategory, List<Tag> tags, Pageable pageable) {
		return journalismDao.findPage(journalismCategory, tags, pageable);
	}

	public long viewHits(Long id) {
		Ehcache cache = cacheManager.getEhcache(Journalism.HITS_CACHE_NAME);
		Element element = cache.get(id);
		Long hits;
		if (element != null) {
			hits = (Long) element.getObjectValue();
		} else {
			Journalism journalism = journalismDao.find(id);
			if (journalism == null) {
				return 0L;
			}
			hits = journalism.getHits();
		}
		hits++;
		cache.put(new Element(id, hits));
		long time = System.currentTimeMillis();
		if (time > viewHitsTime + Journalism.HITS_CACHE_INTERVAL) {
			viewHitsTime = time;
			updateHits();
			cache.removeAll();
		}
		return hits;
	}

	public void destroy() throws Exception {
		updateHits();
	}

	/**
	 * 更新点击数
	 */
	@SuppressWarnings("unchecked")
	private void updateHits() {
		Ehcache cache = cacheManager.getEhcache(Journalism.HITS_CACHE_NAME);
		List<Long> ids = cache.getKeys();
		for (Long id : ids) {
			Journalism journalism = journalismDao.find(id);
			if (journalism != null) {
				Element element = cache.get(id);
				long hits = (Long) element.getObjectValue();
				journalism.setHits(hits);
				journalismDao.merge(journalism);
			}
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "journalism", "journalismCategory" }, allEntries = true)
	public void save(Journalism journalism) {
		Assert.notNull(journalism);

		super.save(journalism);
		journalismDao.flush();
		staticService.build(journalism);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "journalism", "journalismCategory" }, allEntries = true)
	public Journalism update(Journalism journalism) {
		Assert.notNull(journalism);

		Journalism pjournalism = super.update(journalism);
		journalismDao.flush();
		staticService.build(pjournalism);
		return pjournalism;
	}

	@Override
	@Transactional
	@CacheEvict(value = { "journalism", "journalismCategory" }, allEntries = true)
	public Journalism update(Journalism journalism, String... ignoreProperties) {
		return super.update(journalism, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "journalism", "journalismCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "journalism", "journalismCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "journalism", "journalismCategory" }, allEntries = true)
	public void delete(Journalism journalism) {
		if (journalism != null) {
			staticService.delete(journalism);
		}
		super.delete(journalism);
	}

}