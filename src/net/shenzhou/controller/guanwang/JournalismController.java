/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.guanwang;

import javax.annotation.Resource;

import net.shenzhou.Pageable;
import net.shenzhou.ResourceNotFoundException;
import net.shenzhou.entity.Journalism;
import net.shenzhou.entity.JournalismCategory;
import net.shenzhou.service.JournalismCategoryService;
import net.shenzhou.service.JournalismService;
import net.shenzhou.service.SearchService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 新闻
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("guanwangJournalismController")
@RequestMapping("/journalism")
public class JournalismController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 20;

	@Resource(name = "journalismServiceImpl")
	private JournalismService journalismService;
	@Resource(name = "journalismCategoryServiceImpl")
	private JournalismCategoryService journalismCategoryService;
	@Resource(name = "searchServiceImpl")
	private SearchService searchService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public String list(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		JournalismCategory journalismCategory = journalismCategoryService.find(id);
		if (journalismCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("journalismCategory", journalismCategory);
		model.addAttribute("page", journalismService.findPage(journalismCategory, null, pageable));
		return "/shop/journalism/list";
	}

	/**
	 * 搜索
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(String keyword, Integer pageNumber, ModelMap model) {
		if (StringUtils.isEmpty(keyword)) {
			return ERROR_VIEW;
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("journalismKeyword", keyword);
		model.addAttribute("page", searchService.search(keyword, pageable));
		return "shop/journalism/search";
	}

	/**
	 * 点击数
	 */
	@RequestMapping(value = "/hits/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Long hits(@PathVariable Long id) {
		return journalismService.viewHits(id);
	}

	/**
	 * 新闻详情页
	 * @param id
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/journalismContent", method = RequestMethod.GET)
	public String content(Long id,Pageable pageable, ModelMap model) {
		Journalism journalism = journalismService.find(id);
		model.addAttribute("journalism", journalism);
		return "/guanwang/shenzhou/journalismContent";
	}
	
}