/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.shenzhou.Message;
import net.shenzhou.controller.mechanism.BaseController;
import net.shenzhou.entity.Brand;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.MechanismRank;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.MechanismCategoryService;
import net.shenzhou.service.MechanismRankService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 项目类型
 * @date 2017-10-16 16:29:26
 * @author wsr
 *
 */
@Controller("serverProjectCategoryController")
@RequestMapping("/admin/server_project_category")
public class ServerProjectCategoryController extends BaseController {

	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "mechanismCategoryServiceImpl")
	private MechanismCategoryService mechanismCategoryService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "mechanismRankServiceImpl")
	private MechanismRankService mechanismRankService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("serverProjectCategoryTree", serverProjectCategoryService.findTree());
		model.addAttribute("serveTypes", ServerProjectCategory.ServeType.values());
		return "/admin/server_project_category/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ServerProjectCategory serverProjectCategory, RedirectAttributes redirectAttributes) {
		serverProjectCategory.setParent(null);
		if (!isValid(serverProjectCategory)) {
			return ERROR_VIEW;
		}
		serverProjectCategory.setTreePath(null);
		serverProjectCategory.setGrade(null);
		serverProjectCategory.setChildren(null);
		serverProjectCategory.setProjects(null);
		serverProjectCategory.setIsDeleted(false);
		serverProjectCategoryService.save(serverProjectCategory);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(id);
		model.addAttribute("serverProjectCategoryTree", serverProjectCategoryService.findTree());
		model.addAttribute("serverProjectCategory", serverProjectCategory);
		model.addAttribute("children", serverProjectCategoryService.findChildren(serverProjectCategory));
		model.addAttribute("serveTypes", ServerProjectCategory.ServeType.values());
		return "/admin/server_project_category/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ServerProjectCategory serverProjectCategory, RedirectAttributes redirectAttributes) {
		serverProjectCategory.setParent(null);
		if (!isValid(serverProjectCategory)) {
			return ERROR_VIEW;
		}
		if (serverProjectCategory.getParent() != null) {
			ServerProjectCategory parent = serverProjectCategory.getParent();
			if (parent.equals(serverProjectCategory)) {
				return ERROR_VIEW;
			}
			List<ServerProjectCategory> children = serverProjectCategoryService.findChildren(parent);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		serverProjectCategoryService.update(serverProjectCategory, "treePath", "grade", "children", "projects", "parameterGroups", "attributes", "promotions");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("serverProjectCategoryTree", serverProjectCategoryService.findTree());
		return "/admin/server_project_category/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(id);
		if (serverProjectCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<ServerProjectCategory> children = serverProjectCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("存在子集分类");
		}
		List<Project> projects = serverProjectCategory.getProjects();
		if (projects != null && !projects.isEmpty()) {
			return Message.error("该项目被机构端引用,不可删除");
		}
		serverProjectCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}
	
	/**
	 * 子级引用父级信息
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public @ResponseBody
	Map<String ,Object> query(Long parentId) {
		ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(parentId);
		Map<String ,Object> data = new HashMap<String, Object>();
		if (serverProjectCategory == null) {
			data.put("status", "400");
			data.put("message", "错误消息");
			data.put("data", "");
			return data;
		}
		data.put("status", "200");
		data.put("message", "成功");
		data.put("data", JsonUtils.toJson(serverProjectCategory));
		return data;
		
	}

}