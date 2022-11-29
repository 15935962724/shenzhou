/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.controller.mechanism;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.Config;
import net.shenzhou.ExcelView;
import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.Setting;
import net.shenzhou.FileInfo.FileType;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.DoctorCategoryRelation;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoorCost;
import net.shenzhou.entity.Management;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.MechanismImage;
import net.shenzhou.entity.MechanismRank;
import net.shenzhou.entity.MechanismRole;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.ProductCategory;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.WorkDate;
import net.shenzhou.entity.DoctorCategory.ChargeType;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.DoctorCategoryService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.RechargeLogService;
import net.shenzhou.util.MapUtil;
import net.shenzhou.util.SettingUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 医生职级操作
 * @date 2018-1-20 14:35:27
 * @author wsr
 *
 */
@Controller("mechanismDoctorCategoryController")
@RequestMapping("/mechanism/doctorCategory")
public class DoctorCategoryController extends BaseController {

	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "rechargeLogServiceImpl")
	private RechargeLogService rechargeLogService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "doctorCategoryServiceImpl")
	private DoctorCategoryService doctorCategoryService;
	
	/**
	 * 职级设置
	 * @param memberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("pageable", pageable);
		Page<DoctorCategory> page = doctorCategoryService.findPage(query_map);
		model.addAttribute("page", page);
		model.addAttribute("mechanism", mechanism);
		return "mechanism/doctor_category/list";
	}
	
	/**
	 * 导出医生职级
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/downloadList", method = RequestMethod.GET)
	public ModelAndView downloadList(ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		List<DoctorCategory> doctorCategorys = doctorCategoryService.findAll();
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for (DoctorCategory doctorCategory : doctorCategorys) {
			if (doctorCategory.getMechanism().equals(mechanism)) {
				Map<String,Object> data_map = new HashMap<String, Object>();
				data_map.put("doctorCategoryName", doctorCategory.getName());
				data_map.put("doctorCategoryChargeType", message("DoctorCategory.ChargeType."+doctorCategory.getChargeType()));
				data_list.add(data_map);
			}
		}
		String filename = "医生技师" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"职级名称","上门费用类型"};//title
		String[] contents = new String []{"doctorCategoryName","doctorCategoryChargeType"};//content
		
		String[] memos = new String [3];//memo
		memos[0] = "记录数" + ": " + data_list.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
	}
	
	/**
	 * 添加职级页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		List<DoctorCategory> doctorCategorys = doctorCategoryService.findList(new HashMap<String, Object>());
		model.addAttribute("chargeTypes", ChargeType.values());
		model.addAttribute("doctorCategorys", doctorCategorys);
		return "mechanism/doctor_category/add";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id,ModelMap model) {
		DoctorCategory doctorCategory = doctorCategoryService.find(id);
		List<DoctorCategory> doctorCategorys = doctorCategoryService.findList(new HashMap<String, Object>());
		model.addAttribute("chargeTypes", ChargeType.values());
		model.addAttribute("doctorCategorys", doctorCategorys);
		model.addAttribute("doctorCategory", doctorCategory);
		return "mechanism/doctor_category/edit";
	}
	
	/**
	 * 保存职级
	 * @param name
	 * @param chargeType
	 * @param kilometre
	 * @param price
	 * @param increaseKilometre
	 * @param increasePrice
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(String name, ChargeType chargeType, @RequestParam(defaultValue = "0") Integer kilometre,@RequestParam(defaultValue = "0.0") BigDecimal price,@RequestParam(defaultValue = "0")  Integer increaseKilometre,@RequestParam(defaultValue = "0.0") BigDecimal increasePrice,HttpServletRequest request ,RedirectAttributes redirectAttributes) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		DoctorCategory doctorCategory = new DoctorCategory();
		doctorCategory.setName(name);
		doctorCategory.setTitle(name);
		doctorCategory.setChargeType(chargeType);
		doctorCategory.setSeoKeywords(null);
		doctorCategory.setSeoDescription(null);
		doctorCategory.setTreePath(null);
		doctorCategory.setParent(null);
		doctorCategory.setChildren(null);
		doctorCategory.setGrade(null);
		doctorCategory.setIsDeleted(false);
		doctorCategory.setOrder(1);
		DoorCost doorCost = new DoorCost();
		doorCost.setKilometre(kilometre);
	    doorCost.setPrice(price);
	    doorCost.setIncreaseKilometre(increaseKilometre);
	    doorCost.setIncreasePrice(increasePrice);
	    doctorCategory.setDoorCost(doorCost);
	    doctorCategory.setMechanism(mechanism);
		doctorCategoryService.save(doctorCategory);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * @param id
	 * @param name
	 * @param chargeType
	 * @param kilometre
	 * @param price
	 * @param increaseKilometre
	 * @param increasePrice
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Long id ,String name, ChargeType chargeType, @RequestParam(defaultValue = "0")Integer kilometre,@RequestParam(defaultValue = "0.0")BigDecimal price, @RequestParam(defaultValue = "0")Integer increaseKilometre,@RequestParam(defaultValue = "0.0")BigDecimal increasePrice,HttpServletRequest request ,RedirectAttributes redirectAttributes) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		DoctorCategory doctorCategory = doctorCategoryService.find(id);
		doctorCategory.setName(name);
		doctorCategory.setTitle(name);
		doctorCategory.setChargeType(chargeType);
		DoorCost doorCost = new DoorCost();
		doorCost.setKilometre(kilometre);
	    doorCost.setPrice(price);
	    doorCost.setIncreaseKilometre(increaseKilometre);
	    doorCost.setIncreasePrice(increasePrice);
	    doctorCategory.setDoorCost(doorCost);
		doctorCategoryService.update(doctorCategory);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
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
			return Message.error("存在下级分类，无法删除");
		}
		Set<Doctor> doctors = doctorCategory.getDoctors();
		List<DoctorCategoryRelation> doctorCategoryRelations = doctorCategory.getDoctorCategoryRelations();
		if ((doctors != null && !doctors.isEmpty())||(doctorCategoryRelations != null && !doctorCategoryRelations.isEmpty())) {
			return Message.error("存在下级员工，无法删除");
		}
		doctorCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}
	
	

	
}