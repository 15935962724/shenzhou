/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.shenzhou.Message;
import net.shenzhou.controller.mechanism.BaseController;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.service.MechanismCategoryService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 机构分类
 * 2017-06-22 16:40:53
 * @author fl
 *
 */
@Controller("adminCategoryController")
@RequestMapping("/admin/mechanism_category")
public class MechanismCategoryController extends BaseController {

	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "mechanismCategoryServiceImpl")
	private MechanismCategoryService mechanismCategoryService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	
	
	
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("mechanismCategoryTree", mechanismCategoryService.findTree());
//		model.addAttribute("brands", brandService.findAll());
		return "/admin/mechanism_category/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(MechanismCategory mechanismCategory, Long parentId, Long[] brandIds, RedirectAttributes redirectAttributes) {
		mechanismCategory.setParent(mechanismCategoryService.find(parentId));
		if (!isValid(mechanismCategory)) {
			return ERROR_VIEW;
		}
		mechanismCategory.setChildren(null);
		mechanismCategory.setTreePath(null);
		mechanismCategory.setGrade(null);
		mechanismCategory.setIsDeleted(false);
		mechanismCategory.setSeoTitle(" ");
		mechanismCategory.setSeoKeywords(" ");
		try {
			mechanismCategoryService.save(mechanismCategory);	
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
		MechanismCategory mechanismCategory = mechanismCategoryService.find(id);
		model.addAttribute("mechanismCategoryTree", mechanismCategoryService.findTree());
		model.addAttribute("mechanismCategory", mechanismCategory);
		model.addAttribute("children", mechanismCategoryService.findChildren(mechanismCategory));
		return "/admin/mechanism_category/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(MechanismCategory mechanismCategory, Long parentId, Long[] brandIds, RedirectAttributes redirectAttributes) {
		mechanismCategory.setParent(mechanismCategoryService.find(parentId));
//		mechanismCategory.setBrands(new HashSet<Brand>(brandService.findList(brandIds)));
		if (!isValid(mechanismCategory)) {
			return ERROR_VIEW;
		}
		if (mechanismCategory.getParent() != null) {
			MechanismCategory parent = mechanismCategory.getParent();
			if (parent.equals(mechanismCategory)) {
				return ERROR_VIEW;
			}
			List<MechanismCategory> children = mechanismCategoryService.findChildren(parent);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		mechanismCategoryService.update(mechanismCategory, "treePath", "grade", "children", "products", "parameterGroups", "attributes", "promotions");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("mechanismCategoryTree", mechanismCategoryService.findTree());
		return "/admin/mechanism_category/list";
	}
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		MechanismCategory mechanismCategory = mechanismCategoryService.find(id);
		if (mechanismCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<MechanismCategory> children = mechanismCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("存在下级分类，无法删除");
		}
		Set<Mechanism> mechanisms = mechanismCategory.getMechanisms();
		if (mechanisms != null && !mechanisms.isEmpty()) {
			return Message.error("该分类被引用,无法删除");
		}
		try {
			mechanismCategoryService.delete(mechanismCategory);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return SUCCESS_MESSAGE;
	}
	
	
	

}