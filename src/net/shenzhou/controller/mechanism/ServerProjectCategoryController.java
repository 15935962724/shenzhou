/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import net.shenzhou.Config;
import net.shenzhou.FileInfo.FileType;
import net.shenzhou.Message;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.JsonUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 机构用户
 * 2017-06-22 16:40:53
 * @author wsr
 *
 */
@Controller("mechanismServerProjectCategoryController")
@RequestMapping("/mechanism/serverProjectCategory")
public class ServerProjectCategoryController extends BaseController {

	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("serverProjectCategorys", serverProjectCategoryService.getServerProjectCategory());
		return "/mechanism/server_project_category/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ServerProjectCategory serverProjectCategory, Long parentId, @RequestParam(value = "logo_file", required = false)MultipartFile  logo_file, @RequestParam(value = "introduceImg_file", required = false)MultipartFile  introduce_Img_file,  RedirectAttributes redirectAttributes) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		ServerProjectCategory patientServerProjectCategory = serverProjectCategoryService.find(parentId);
		serverProjectCategory.setParent(patientServerProjectCategory);
		if (!isValid(serverProjectCategory)) {
			return ERROR_VIEW;
		}
		String path = Config.projectLogoUrl;
		String logo =logo_file.getSize()==0?patientServerProjectCategory.getLogo():fileService.uploadImg(FileType.image, logo_file, path,UUID.randomUUID().toString(), false);
		System.out.println(logo);
		serverProjectCategory.setLogo(logo);
		
		
		String projectIntroduceImg =introduce_Img_file.getSize()==0?patientServerProjectCategory.getIntroduceImg():fileService.uploadImg(FileType.image, introduce_Img_file, Config.projectIntroduceImgUrl,UUID.randomUUID().toString(), false);
		System.out.println(projectIntroduceImg);
		serverProjectCategory.setIntroduceImg(projectIntroduceImg);
		serverProjectCategory.setServeType(patientServerProjectCategory.getServeType());
		serverProjectCategory.setTreePath(null);
		serverProjectCategory.setGrade(null);
		serverProjectCategory.setChildren(null);
		serverProjectCategory.setProjects(null);
		serverProjectCategory.setMechanism(mechanism);
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
		model.addAttribute("serverProjectCategorys", serverProjectCategoryService.getServerProjectCategory());
//		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("serverProjectCategory", serverProjectCategory);
		model.addAttribute("children", serverProjectCategoryService.findChildren(serverProjectCategory));
		return "/mechanism/server_project_category/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Long  id, Long parentId,String name,String seoDescription, @RequestParam(value = "logo_file", required = false)MultipartFile  logo_file, @RequestParam(value = "introduceImg_file", required = false)MultipartFile  introduce_Img_file,   RedirectAttributes redirectAttributes) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(id);
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		ServerProjectCategory parentServerProjectCategory = serverProjectCategoryService.find(parentId);
		serverProjectCategory.setParent(parentServerProjectCategory);
		serverProjectCategory.setName(name);
		serverProjectCategory.setSeoDescription(seoDescription);
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
		
		
		String path = Config.projectLogoUrl;
		
		//如果logo文件流不为空就重新赋值
		if (logo_file.getSize()!=0) {
			String logo = fileService.uploadImg(FileType.image, logo_file, path,UUID.randomUUID().toString(), false);
			System.out.println(logo);
			serverProjectCategory.setLogo(logo);
		}
		
			
		//如果introduce_Img文件流不为空就重新赋值
		if (introduce_Img_file.getSize()!=0) {
			String introduceImg = fileService.uploadImg(FileType.image, introduce_Img_file, Config.projectIntroduceImgUrl,UUID.randomUUID().toString(), false);
			System.out.println(introduceImg);
			serverProjectCategory.setIntroduceImg(introduceImg);
		}
		
	    serverProjectCategoryService.update(serverProjectCategory);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model,Pageable pageable) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("pageable", pageable);
		query_map.put("mechanism", mechanism);
		model.addAttribute("serverProjectCategory", serverProjectCategoryService.getServerProjectCategory());
		model.addAttribute("serverProjectCategoryTree", serverProjectCategoryService.findTree());
		model.addAttribute("page", serverProjectCategoryService.getServerProjectCategory(query_map));
		return "/mechanism/server_project_category/list";
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
			return Message.error("存在子级，分类无法删除");
		}
		List<Project> projects = serverProjectCategory.getProjects();
		try {
			if (projects != null && !projects.isEmpty()) {
				for (Project project : projects) {
					project.setIsDeleted(true);
					projectService.update(project);
				}
			}
			serverProjectCategory.setIsDeleted(true);
			serverProjectCategoryService.update(serverProjectCategory);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return Message.warn("操作异常");
		}
		
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