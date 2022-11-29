/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import javax.annotation.Resource;

import net.shenzhou.Message;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Relationship;
import net.shenzhou.service.RelationshipService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 关系
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("adminRelationshipController")
@RequestMapping("/admin/relationship")
public class RelationshipController extends BaseController {

	@Resource(name = "relationshipServiceImpl")
	private RelationshipService relationshipService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/relationship/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Relationship relationship, RedirectAttributes redirectAttributes) {
		
		relationshipService.save(relationship);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("relationship", relationshipService.find(id));
		return "/admin/relationship/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Relationship relationship, Long adPositionId, RedirectAttributes redirectAttributes) {
		relationshipService.update(relationship);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", relationshipService.findPage(pageable));
		return "/admin/relationship/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		relationshipService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}