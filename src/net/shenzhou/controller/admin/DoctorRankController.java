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
import net.shenzhou.dao.impl.DoctorCategoryDaoImpl;
import net.shenzhou.entity.DoctorRank;
import net.shenzhou.entity.Tag;
import net.shenzhou.entity.Tag.Type;
import net.shenzhou.service.DoctorCategoryService;
import net.shenzhou.service.DoctorRankService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.DoctorRankService;
import net.shenzhou.service.TagService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 专家
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("adminDoctorRankController")
@RequestMapping("/admin/doctorRank")
public class DoctorRankController extends BaseController {

	@Resource(name = "doctorRankServiceImpl")
	private DoctorRankService doctorRankService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "doctorCategoryServiceImpl")
	private DoctorCategoryService doctorCategoryService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("doctorRanks", doctorRankService.findAll());
		model.addAttribute("doctorCategorys", doctorCategoryService.findTree());
		return "/admin/doctor_rank/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(DoctorRank doctorRank, Long parentId, RedirectAttributes redirectAttributes) {
		doctorRank.setParent(doctorRankService.find(parentId));
		doctorRank.setIsDeleted(false);
		doctorRank.setTreePath(null);
		doctorRank.setGrade(null);
		doctorRank.setChildren(null);
		doctorRank.setExperts(null);
		if (!isValid(doctorRank)) {
			return ERROR_VIEW;
		}
		
		doctorRankService.save(doctorRank);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("doctorRanks", doctorRankService.findAll());
		model.addAttribute("doctorCategorys", doctorCategoryService.findTree());
		model.addAttribute("doctorRank", doctorRankService.find(id));
		return "/admin/doctor_rank/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(DoctorRank doctorRank,Long doctorRankId, Long doctorCategoryId, RedirectAttributes redirectAttributes) {
		doctorRank.setParent(doctorRankService.find(doctorRankId));
		doctorRank.setIsDeleted(false);
		if (!isValid(doctorRank)) {
			return ERROR_VIEW;
		}
		doctorRankService.update(doctorRank);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", doctorRankService.findPage(pageable));
		return "/admin/doctor_rank/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		doctorRankService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}