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
import net.shenzhou.entity.Expert;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.Tag;
import net.shenzhou.entity.Tag.Type;
import net.shenzhou.service.DoctorCategoryService;
import net.shenzhou.service.DoctorRankService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.ExpertService;
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
@Controller("adminExpertController")
@RequestMapping("/admin/expert")
public class ExpertController extends BaseController {

	@Resource(name = "expertServiceImpl")
	private ExpertService expertService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "doctorCategoryServiceImpl")
	private DoctorCategoryService doctorCategoryService;
	@Resource(name = "doctorRankServiceImpl")
	private DoctorRankService doctorRankService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("doctorRanks", doctorRankService.findAll());
		model.addAttribute("doctorCategorys", doctorCategoryService.findTree());
		model.addAttribute("genders", Gender.values());
		return "/admin/expert/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Expert expert, Long doctorRankId, Long doctorCategoryId, RedirectAttributes redirectAttributes) {
		expert.setDoctorRank(doctorRankService.find(doctorRankId));
		expert.setDoctorCategory(doctorCategoryService.find(doctorCategoryId));
		expert.setServerSort(0d);
		expert.setSkillSort(0d);
		expert.setCommunicateSort(0d);
		expert.setScoreSort(0d);
		expert.setIsDeleted(false);
		if (!isValid(expert)) {
			return ERROR_VIEW;
		}
		
		expertService.save(expert);
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
		model.addAttribute("expert", expertService.find(id));
		model.addAttribute("genders", Gender.values());
		return "/admin/expert/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Expert expert,Long doctorRankId, Long doctorCategoryId, RedirectAttributes redirectAttributes) {
		expert.setDoctorRank(doctorRankService.find(doctorRankId));
		expert.setDoctorCategory(doctorCategoryService.find(doctorCategoryId));
		if (!isValid(expert)) {
			return ERROR_VIEW;
		}
		expertService.update(expert);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", expertService.findPage(pageable));
		return "/admin/expert/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		expertService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}