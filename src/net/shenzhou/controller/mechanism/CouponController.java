/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Message;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Coupon;
import net.shenzhou.entity.Coupon.CouponType;
import net.shenzhou.entity.Coupon.GrantType;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.CouponCodeService;
import net.shenzhou.service.CouponService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.ServerProjectCategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 优惠券
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("mechanismCouponController")
@RequestMapping("/mechanism/coupon")
public class CouponController extends BaseController {

	@Resource(name = "couponServiceImpl")
	private CouponService couponService;
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	
	


	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,CouponType couponType, @RequestParam(defaultValue="false") Boolean isConduct,@RequestParam(defaultValue="false") Boolean isEnd, @RequestParam(defaultValue="true")Boolean isEnabled,ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("pageable", pageable);
		query_map.put("couponType", couponType);
		query_map.put("isConduct", isConduct);//进行中
		query_map.put("isEnd", isEnd);//已结束
		query_map.put("isEnabled", isEnabled);//已停止
		query_map.put("grantType", GrantType.mechanism);//机构发放的优惠券
		
		model.addAttribute("page", couponService.findPage(query_map));
		model.addAttribute("couponTypes", CouponType.values());
		model.addAttribute("couponType",couponType);
		return "/mechanism/coupon/list";
	}

	
	/**
	 * 新增优惠券
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Pageable pageable,ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		model.addAttribute("couponTypes", CouponType.values());
//		model.addAttribute("serverProjectCategorys",  serverProjectCategoryService.getServerProjectCategory(mechanism));
		model.addAttribute("serverProjectCategorys",  serverProjectCategoryService.findRoots());//所有的顶级分类
		return "/mechanism/coupon/add";
	}
	
	
	/**
	 * 添加优惠券
	 * @param coupon
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Coupon coupon, Long []serverProjectCategoryIds, RedirectAttributes redirectAttributes) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		coupon.setPoint(null);
		coupon.setCouponCodes(null);
		coupon.setPromotions(null);
		coupon.setOrders(null);
		coupon.setIsEnabled(true);
		coupon.setIsExchange(false);
		coupon.setIsDeleted(false);
		coupon.setPrefix(coupon.getCouponType().toString());
		coupon.setMechanism(mechanism);
		coupon.setGrantType(GrantType.mechanism);
		coupon.getMechanisms().add(mechanism);
		couponService.save(coupon);
		for (Long serverProjectCategoryId : serverProjectCategoryIds) {
			ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(serverProjectCategoryId);
			serverProjectCategory.getCoupons().add(coupon);
			serverProjectCategoryService.update(serverProjectCategory);
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	
	/**
	 * 领用明细
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Pageable pageable,Long couponId,Boolean isUsed, ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Coupon coupon = couponService.find(couponId);
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("pageable", pageable);
		query_map.put("isUsed", isUsed);
		query_map.put("coupon", coupon);
		Long usedCount = couponCodeService.count(coupon, null, null, null, true);
		model.addAttribute("page", couponCodeService.findPage(query_map));
		model.addAttribute("isUsed",  isUsed);
		model.addAttribute("coupon",  coupon);
		model.addAttribute("usedCount",  usedCount);
		return "/mechanism/coupon/view";
	}
	
	
	/**
	 * 编辑优惠券页面
	 * @param couponId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long couponId, ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Coupon coupon = couponService.find(couponId);
		model.addAttribute("coupon",  coupon);
		return "/mechanism/coupon/edit";
	}
	
	
	/**
	 * 优惠券停用或启用
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	Message updateStatus(Long id) {
		Coupon coupon = couponService.find(id);
		coupon.setIsEnabled(!coupon.getIsEnabled());
		couponService.update(coupon);
		return SUCCESS_MESSAGE;
	}
	
	
}