/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.shenzhou.Message;
import net.shenzhou.entity.Brand;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.ProductCategory;
import net.shenzhou.entity.DoctorCategory.ChargeType;
import net.shenzhou.service.BrandService;
import net.shenzhou.service.DoctorCategoryService;
import net.shenzhou.service.ProductCategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 医生级别分类
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("doctorProductCategoryController")
@RequestMapping("/admin/doctor_category")
public class DoctorCategoryController extends BaseController {

	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "doctorCategoryServiceImpl")
	private DoctorCategoryService doctorCategoryService;

	
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("doctorCategoryTree", doctorCategoryService.findTree());
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		return "/admin/doctor_category/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(DoctorCategory doctorCategory, Long parentId, RedirectAttributes redirectAttributes) {
		doctorCategory.setParent(doctorCategoryService.find(parentId));
		if (!isValid(doctorCategory)) {
			return ERROR_VIEW;
		}
		doctorCategory.setChildren(null);
		Integer key = 0;
		String ss = ",";
		for(;doctorCategoryService.find(parentId)!=null;){
			if(parentId==null){
				break;
			}
			DoctorCategory doctorCategorys = doctorCategoryService.find(parentId);
			key = key+1;
			ss=ss+doctorCategorys.getId()+",";
			DoctorCategory parentDoctorCategorys = doctorCategorys.getParent();
			if(parentDoctorCategorys!=null){
				parentId = parentDoctorCategorys.getId();
			}else{
				break;
			}
		}
		doctorCategory.setTreePath(ss);
		doctorCategory.setGrade(key);
		doctorCategory.setSeoKeywords("1");
		doctorCategory.setIsDeleted(false);
		doctorCategoryService.save(doctorCategory);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		DoctorCategory doctorCategory = doctorCategoryService.find(id);
		model.addAttribute("doctorCategoryTree", doctorCategoryService.findTree());
		model.addAttribute("doctorCategory", doctorCategory);
		model.addAttribute("children", doctorCategoryService.findChildren(doctorCategory));
		return "/admin/doctor_category/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(DoctorCategory doctorCategory, Long parentId, RedirectAttributes redirectAttributes) {
		doctorCategory.setParent(doctorCategoryService.find(parentId));
		if (!isValid(doctorCategory)) {
			return ERROR_VIEW;
		}
		if (doctorCategory.getParent() != null) {
			DoctorCategory parent = doctorCategory.getParent();
			if (parent.equals(doctorCategory)) {
				return ERROR_VIEW;
			}
			List<DoctorCategory> children = doctorCategoryService.findChildren(parent);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		
		doctorCategory.setChargeType(ChargeType.free);
		doctorCategoryService.update(doctorCategory, "seoKeywords", "children", "doctors","doorCost");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		//model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("doctorCategoryTree", doctorCategoryService.findTree());
		return "/admin/doctor_category/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		DoctorCategory doctorCategory = doctorCategoryService.find(id);
		if (doctorCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<DoctorCategory> children = doctorCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("admin.productCategory.deleteExistChildrenNotAllowed");
		}
		Set<Doctor> doctors = doctorCategory.getDoctors();
		if (doctors != null && !doctors.isEmpty()) {
			return Message.error("admin.productCategory.deleteExistProductNotAllowed");
		}
		doctorCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}

}