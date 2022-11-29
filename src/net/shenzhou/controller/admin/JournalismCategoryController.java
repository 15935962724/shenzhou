/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.shenzhou.Message;
import net.shenzhou.entity.Journalism;
import net.shenzhou.entity.JournalismCategory;
import net.shenzhou.service.JournalismCategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 新闻分类
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("adminjournalismCategoryController")
@RequestMapping("/admin/journalism_category")
public class JournalismCategoryController extends BaseController {

	@Resource(name = "journalismCategoryServiceImpl")
	private JournalismCategoryService journalismCategoryService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("journalismCategoryTree", journalismCategoryService.findTree());
		return "/admin/journalism_category/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(JournalismCategory journalismCategory, Long parentId, RedirectAttributes redirectAttributes) {
		journalismCategory.setParent(journalismCategoryService.find(parentId));
		if (!isValid(journalismCategory)) {
			return ERROR_VIEW;
		}
		journalismCategory.setTreePath(null);
		journalismCategory.setGrade(null);
		journalismCategory.setChildren(null);
		journalismCategory.setJournalisms(null);
		journalismCategory.setIsDeleted(false);
		try {
			journalismCategoryService.save(journalismCategory);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		JournalismCategory journalismCategory = journalismCategoryService.find(id);
		model.addAttribute("journalismCategoryTree", journalismCategoryService.findTree());
		model.addAttribute("journalismCategory", journalismCategory);
		model.addAttribute("children", journalismCategoryService.findChildren(journalismCategory));
		return "/admin/journalism_category/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(JournalismCategory journalismCategory, Long parentId, RedirectAttributes redirectAttributes) {
		journalismCategory.setParent(journalismCategoryService.find(parentId));
		journalismCategory.setIsDeleted(false);
		if (!isValid(journalismCategory)) {
			return ERROR_VIEW;
		}
		if (journalismCategory.getParent() != null) {
			JournalismCategory parent = journalismCategory.getParent();
			if (parent.equals(journalismCategory)) {
				return ERROR_VIEW;
			}
			List<JournalismCategory> children = journalismCategoryService.findChildren(parent);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		journalismCategoryService.update(journalismCategory, "treePath", "grade", "children", "journalisms");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("journalismCategoryTree", journalismCategoryService.findTree());
		return "/admin/journalism_category/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		JournalismCategory journalismCategory = journalismCategoryService.find(id);
		if (journalismCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<JournalismCategory> children = journalismCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("admin.journalismCategory.deleteExistChildrenNotAllowed");
		}
		Set<Journalism> journalisms = journalismCategory.getJournalisms();
		if (journalisms != null && !journalisms.isEmpty()) {
			return Message.error("admin.journalismCategory.deleteExistjournalismNotAllowed");
		}
		journalismCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}

}