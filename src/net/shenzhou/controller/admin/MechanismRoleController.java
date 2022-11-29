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

import javax.annotation.Resource;

import net.shenzhou.Message;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismRole;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismRoleService;
import net.shenzhou.service.MechanismService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 角色
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("mechanismRoleController")
@RequestMapping("/mechanism/mechanismrole")
public class MechanismRoleController extends BaseController {

	@Resource(name = "mechanismRoleServiceImpl")
	private MechanismRoleService mechanismRoleService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,String name, ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String, Object>();
		pageable.setSearchProperty("name");
		pageable.setSearchValue(name);
		query_map.put("pageable", pageable);
		query_map.put("mechanism", mechanism);
		model.addAttribute("page", mechanismRoleService.findPage(query_map));
		return "/mechanism/mechanism_role/list";
	}

	/**
	 * 添加角色
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		return "/mechanism/mechanism_role/add";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("mechanismRole", mechanismRoleService.find(id));
		return "/mechanism/mechanism_role/edit";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(MechanismRole mechanismRole, RedirectAttributes redirectAttributes) {
		if (!isValid(mechanismRole)) {
			return ERROR_VIEW;
		}
		MechanismRole pMechanismRole = mechanismRoleService.find(mechanismRole.getId());
		if (pMechanismRole == null || pMechanismRole.getIsSystem()) {
			return ERROR_VIEW;
		}
		mechanismRoleService.update(mechanismRole, "isSystem", "users","isDeleted","mechanism");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(MechanismRole mechanismRole, RedirectAttributes redirectAttributes) {
		Doctor doctorC = doctorService.getCurrent();
		DoctorMechanismRelation doctorMechanismRelation = doctorC.getDefaultDoctorMechanismRelation();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		if (!isValid(mechanismRole)) {
			return ERROR_VIEW;
		}
		mechanismRole.getDoctorMechanismRelations().add(doctorMechanismRelation);
		mechanismRole.setMechanism(doctorMechanismRelation.getMechanism());
		mechanismRole.setIsSystem(false);
		mechanismRole.setUsers(null);
		mechanismRole.setIsDeleted(false);
		mechanismRoleService.save(mechanismRole);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 用户设置
	 * @param mechanismRole
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/doctorSetup", method = RequestMethod.GET)
	public String doctorSetup(Long mechanismRoleId,Model model, RedirectAttributes redirectAttributes) {
		Doctor doctorC = doctorService.getCurrent();
		DoctorMechanismRelation doctorMechanismRelation = doctorC.getDefaultDoctorMechanismRelation();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		MechanismRole mechanismRole = mechanismRoleService.find(mechanismRoleId);
		List<DoctorMechanismRelation> doctorMechanismRelations = mechanism.getDoctorMechanismRelations(Audit.succeed);
		
		model.addAttribute("doctorMechanismRelations",doctorMechanismRelations);
		model.addAttribute("mechanismRole",mechanismRole);
		return "/mechanism/mechanism_role/doctorSetup";
	}
	

	/**
	 * 
	 * @param mechanismRoleId
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/updateDoctorSetup", method = RequestMethod.POST)
	public String updateDoctorSetup(Long mechanismRoleId,Long []doctorIds, Model model, RedirectAttributes redirectAttributes) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		MechanismRole mechanismRole = mechanismRoleService.find(mechanismRoleId);
		
		for (DoctorMechanismRelation doctorMechanismRelation1 : mechanism.getDoctorMechanismRelation()) {
//			DoctorMechanismRelation doctorMechanismRelation1 = doctor.getDoctorMechanismRelation(mechanism);
			if (!doctorMechanismRelation1.getIsSystem()) {
					doctorMechanismRelation1.getMechanismroles().remove(mechanismRole);
					doctorMechanismRelationService.update(doctorMechanismRelation1);
			}
		}
		
		for (Long doctorId : doctorIds) {
		    Doctor doctor =	doctorService.find(doctorId);
			DoctorMechanismRelation doctorMechanismRelation2 = doctor.getDoctorMechanismRelation(mechanism);
			doctorMechanismRelation2.setMechanismroles(new HashSet<MechanismRole>());
			doctorMechanismRelationService.update(doctorMechanismRelation2);
			try {
				mechanismRole.getDoctorMechanismRelations().add(doctorMechanismRelation2);
				mechanismRoleService.update(mechanismRole);
				System.out.println(doctorMechanismRelation2.getMechanismroles());
				System.out.println(doctorMechanismRelation2.getMechanismroles().size());
				doctorMechanismRelation2.getMechanismroles().add(mechanismRole);
				doctorMechanismRelationService.update(doctorMechanismRelation2);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
			
			
		}
		
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		
		MechanismRole role = mechanismRoleService.find(id);
		if (role != null && (role.getIsSystem() || (role.getDoctorMechanismRelations()!= null && !role.getDoctorMechanismRelations().isEmpty()))) {
			return Message.error("admin.role.deleteExistNotAllowed", role.getName());
		}
		mechanismRoleService.delete(id);
		return SUCCESS_MESSAGE;
	}
	

}