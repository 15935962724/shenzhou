/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.util.HashSet;

import javax.annotation.Resource;

import net.shenzhou.Message;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Journalism;
import net.shenzhou.entity.Tag;
import net.shenzhou.entity.Tag.Type;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.JournalismCategoryService;
import net.shenzhou.service.JournalismService;
import net.shenzhou.service.TagService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 新闻
 * @date 2017-10-10 15:34:19
 * @author wsr
 *
 */
@Controller("adminJournalismController")
@RequestMapping("/admin/journalism")
public class JournalismController extends BaseController {

	@Resource(name = "journalismServiceImpl")
	private JournalismService journalismService;
	@Resource(name = "journalismCategoryServiceImpl")
	private JournalismCategoryService journalismCategoryService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("journalismCategoryTree", journalismCategoryService.findTree());
		model.addAttribute("tags", tagService.findList(Type.journalism));
		return "/admin/journalism/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Journalism journalism, Long journalismCategoryId, Long[] tagIds, RedirectAttributes redirectAttributes) {
		journalism.setJournalismCategory(journalismCategoryService.find(journalismCategoryId));
		journalism.setTags(new HashSet<Tag>(tagService.findList(tagIds)));
		if (!isValid(journalism)) {
			return ERROR_VIEW;
		}
		journalism.setHits(0L);
		journalism.setPageNumber(null);
		journalism.setIsDeleted(false);
		journalismService.save(journalism);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("journalismCategoryTree", journalismCategoryService.findTree());
		model.addAttribute("tags", tagService.findList(Type.journalism));
		model.addAttribute("journalism", journalismService.find(id));
		return "/admin/journalism/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Journalism journalism, Long journalismCategoryId, Long[] tagIds, RedirectAttributes redirectAttributes) {
		journalism.setJournalismCategory(journalismCategoryService.find(journalismCategoryId));
		journalism.setIsDeleted(false);
		journalism.setTags(new HashSet<Tag>(tagService.findList(tagIds)));
		if (!isValid(journalism)) {
			return ERROR_VIEW;
		}
		journalismService.update(journalism, "hits", "pageNumber");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", journalismService.findPage(pageable));
		return "/admin/journalism/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		journalismService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
	
}