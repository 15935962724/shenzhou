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
import net.shenzhou.controller.mechanism.BaseController;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.MechanismRank;
import net.shenzhou.service.MechanismCategoryService;
import net.shenzhou.service.MechanismRankService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 机构等级
 * 2017-06-22 16:40:53
 * @author fl
 *
 */
@Controller("mechanismRankController")
@RequestMapping("/admin/mechanism_rank")
public class MechanismRankController extends BaseController {

	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "mechanismCategoryServiceImpl")
	private MechanismCategoryService mechanismCategoryService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "mechanismRankServiceImpl")
	private MechanismRankService mechanismRankService;
	
	
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("mechanismRankTree", mechanismRankService.findTree());
//		model.addAttribute("brands", brandService.findAll());
		return "/admin/mechanism_rank/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(MechanismRank mechanismRank, Long parentId, Long[] brandIds, RedirectAttributes redirectAttributes) {
		mechanismRank.setParent(mechanismRankService.find(parentId));
		if (!isValid(mechanismRank)) {
			return ERROR_VIEW;
		}
		
		Integer key = 0;
		String ss = ",";
		for(;mechanismRankService.find(parentId)!=null;){
			if(parentId==null){
				break;
			}
			MechanismRank mechanismRanks = mechanismRankService.find(parentId);
			key = key+1;
			ss=ss+mechanismRanks.getId()+",";
			MechanismRank parentDoctorCategorys = mechanismRanks.getParent();
			if(parentDoctorCategorys!=null){
				parentId = parentDoctorCategorys.getId();
			}else{
				break;
			}
		}
		
		mechanismRank.setChildren(null);
		mechanismRank.setTreePath(ss);
		mechanismRank.setGrade(key);
		mechanismRank.setIsDeleted(false);
		mechanismRank.setSeoTitle(" ");
		mechanismRank.setSeoKeywords(" ");
		try {
			mechanismRankService.save(mechanismRank);	
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
		MechanismRank mechanismRank = mechanismRankService.find(id);
		model.addAttribute("mechanismRankTree", mechanismRankService.findTree());
		model.addAttribute("mechanismRank", mechanismRank);
		model.addAttribute("children", mechanismRankService.findChildren(mechanismRank));
		return "/admin/mechanism_rank/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(MechanismRank mechanismRank, Long parentId, Long[] brandIds, RedirectAttributes redirectAttributes) {
		mechanismRank.setParent(mechanismRankService.find(parentId));
//		mechanismCategory.setBrands(new HashSet<Brand>(brandService.findList(brandIds)));
		if (!isValid(mechanismRank)) {
			return ERROR_VIEW;
		}
		if (mechanismRank.getParent() != null) {
			MechanismRank parent = mechanismRank.getParent();
			if (parent.equals(mechanismRank)) {
				return ERROR_VIEW;
			}
			List<MechanismRank> children = mechanismRankService.findChildren(parent);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		
		Integer key = 0;
		String ss = ",";
		for(;mechanismRankService.find(parentId)!=null;){
			if(parentId==null){
				break;
			}
			MechanismRank mechanismRanks = mechanismRankService.find(parentId);
			key = key+1;
			ss=ss+mechanismRanks.getId()+",";
			MechanismRank parentDoctorCategorys = mechanismRanks.getParent();
			if(parentDoctorCategorys!=null){
				parentId = parentDoctorCategorys.getId();
			}else{
				break;
			}
		}
		mechanismRank.setTreePath(ss);
		mechanismRank.setGrade(key);
		mechanismRankService.update(mechanismRank,"children", "products", "parameterGroups", "attributes", "promotions");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("mechanismRankTree", mechanismRankService.findTree());
		return "/admin/mechanism_rank/list";
	}
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		MechanismRank mechanismRank = mechanismRankService.find(id);
		if (mechanismRank == null) {
			return ERROR_MESSAGE;
		}
		Set<MechanismRank> children = mechanismRank.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("存在下级分类，无法删除");
		}
		Set<Mechanism> mechanisms = mechanismRank.getMechanisms();
		if (mechanisms != null && !mechanisms.isEmpty()) {
			return Message.error("该分类被引用,无法删除");
		}
		try {
			mechanismRankService.delete(mechanismRank);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return SUCCESS_MESSAGE;
	}
	
	
	

}