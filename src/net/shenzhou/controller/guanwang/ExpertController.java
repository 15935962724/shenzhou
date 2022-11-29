/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.guanwang;

import javax.annotation.Resource;

import net.shenzhou.Pageable;
import net.shenzhou.entity.Expert;
import net.shenzhou.service.ExpertService;
import net.shenzhou.service.SearchService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 专家
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("guanwangExpertController")
@RequestMapping("/expert")
public class ExpertController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 20;

	@Resource(name = "expertServiceImpl")
	private ExpertService expertService;
	@Resource(name = "searchServiceImpl")
	private SearchService searchService;

	/**
	 * 列表
	 */
//	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
//	public String list(@PathVariable Long id, Integer pageNumber, ModelMap model) {
//		ExpertCategory expertCategory = expertCategoryService.find(id);
//		if (expertCategory == null) {
//			throw new ResourceNotFoundException();
//		}
//		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
//		model.addAttribute("expertCategory", expertCategory);
//		model.addAttribute("page", expertService.findPage(expertCategory, null, pageable));
//		return "/shop/expert/list";
//	}

	/**
	 * 搜索
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(String keyword, Integer pageNumber, ModelMap model) {
		if (StringUtils.isEmpty(keyword)) {
			return ERROR_VIEW;
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("expertKeyword", keyword);
		model.addAttribute("page", searchService.search(keyword, pageable));
		return "shop/expert/search";
	}

	

	/**
	 * 新闻详情页
	 * @param id
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/content", method = RequestMethod.GET)
	public String content(Long id,Pageable pageable, ModelMap model) {
		Expert expert = expertService.find(id);
		model.addAttribute("expert", expert);
		return "/guanwang/shenzhou/expert/content";
	}
	
}