/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.shenzhou.ExcelView;
import net.shenzhou.Message;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Coupon;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Coupon.CouponType;
import net.shenzhou.entity.CouponCode;
import net.shenzhou.entity.Coupon.GrantType;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.CouponCodeService;
import net.shenzhou.service.CouponService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.util.FreemarkerUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 优惠券
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("adminCouponController")
@RequestMapping("/admin/coupon")
public class CouponController extends BaseController {

	@Resource(name = "couponServiceImpl")
	private CouponService couponService;
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	
	
	

	/**
	 * 检查价格运算表达式是否正确
	 */
	@RequestMapping(value = "/check_price_expression", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkPriceExpression(String priceExpression) {
		if (StringUtils.isEmpty(priceExpression)) {
			return false;
		}
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("quantity", 111);
			model.put("price", new BigDecimal(9.99));
			new BigDecimal(FreemarkerUtils.process("#{(" + priceExpression + ");M50}", model));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("couponTypes",CouponType.values());
		List<ServerProjectCategory> serverProjectCategories = serverProjectCategoryService.getServerProjectCategory();
		
		model.addAttribute("areas",areaService.findRoots());
		model.addAttribute("couponTypes",CouponType.values());
		model.addAttribute("serverProjectCategories",serverProjectCategories);
		return "/admin/coupon/add";
	}

	/**
	 * 保存
	 */
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public String save(Coupon coupon, RedirectAttributes redirectAttributes) {
//		if (!isValid(coupon)) {
//			return ERROR_VIEW;
//		}
//		if (coupon.getBeginDate() != null && coupon.getEndDate() != null && coupon.getBeginDate().after(coupon.getEndDate())) {
//			return ERROR_VIEW;
//		}
//		if (coupon.getMinimumQuantity() != null && coupon.getMaximumQuantity() != null && coupon.getMinimumQuantity() > coupon.getMaximumQuantity()) {
//			return ERROR_VIEW;
//		}
//		if (coupon.getMinimumPrice() != null && coupon.getMaximumPrice() != null && coupon.getMinimumPrice().compareTo(coupon.getMaximumPrice()) > 0) {
//			return ERROR_VIEW;
//		}
//		if (StringUtils.isNotEmpty(coupon.getPriceExpression())) {
//			try {
//				Map<String, Object> model = new HashMap<String, Object>();
//				model.put("quantity", 111);
//				model.put("price", new BigDecimal(9.99));
//				new BigDecimal(FreemarkerUtils.process("#{(" + coupon.getPriceExpression() + ");M50}", model));
//			} catch (Exception e) {
//				return ERROR_VIEW;
//			}
//		}
//		if (coupon.getIsExchange() && coupon.getPoint() == null) {
//			return ERROR_VIEW;
//		}
//		if (!coupon.getIsExchange()) {
//			coupon.setPoint(null);
//		}
//		coupon.setCouponCodes(null);
//		coupon.setPromotions(null);
//		coupon.setOrders(null);
//		coupon.setGrantType(GrantType.platform);
//		couponService.save(coupon);
//		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
//		return "redirect:list.jhtml";
//	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Coupon coupon, Long []serverProjectCategoryIds, RedirectAttributes redirectAttributes) {
//		Doctor doctorC = doctorService.getCurrent();
//		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		coupon.setPoint(null);
		coupon.setCouponCodes(null);
		coupon.setPromotions(null);
		coupon.setOrders(null);
		coupon.setIsEnabled(true);
		coupon.setIsExchange(false);
		coupon.setIsDeleted(false);
		coupon.setPrefix(coupon.getCouponType().toString());
		coupon.setMechanism(null);
		coupon.setGrantType(GrantType.platform);
		couponService.save(coupon);
		for (Long serverProjectCategoryId : serverProjectCategoryIds) {
			ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(serverProjectCategoryId);
			serverProjectCategory.getCoupons().add(coupon);
			serverProjectCategoryService.update(serverProjectCategory);
		}
		for (Mechanism mechanism : mechanismService.findAll()) {
			mechanism.getMechanismCoupons().add(coupon);
			mechanismService.update(mechanism);
		}
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("coupon", couponService.find(id));
		return "/admin/coupon/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Coupon coupon, RedirectAttributes redirectAttributes) {
		if (!isValid(coupon)) {
			return ERROR_VIEW;
		}
		if (coupon.getBeginDate() != null && coupon.getEndDate() != null && coupon.getBeginDate().after(coupon.getEndDate())) {
			return ERROR_VIEW;
		}
		if (coupon.getMinimumQuantity() != null && coupon.getMaximumQuantity() != null && coupon.getMinimumQuantity() > coupon.getMaximumQuantity()) {
			return ERROR_VIEW;
		}
		if (coupon.getMinimumPrice() != null && coupon.getMaximumPrice() != null && coupon.getMinimumPrice().compareTo(coupon.getMaximumPrice()) > 0) {
			return ERROR_VIEW;
		}
		if (StringUtils.isNotEmpty(coupon.getPriceExpression())) {
			try {
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("quantity", 111);
				model.put("price", new BigDecimal(9.99));
				new BigDecimal(FreemarkerUtils.process("#{(" + coupon.getPriceExpression() + ");M50}", model));
			} catch (Exception e) {
				return ERROR_VIEW;
			}
		}
		if (coupon.getIsExchange() && coupon.getPoint() == null) {
			return ERROR_VIEW;
		}
		if (!coupon.getIsExchange()) {
			coupon.setPoint(null);
		}
		couponService.update(coupon, "couponCodes", "promotions", "orders");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,Boolean isConduct ,Boolean isEnd ,Boolean isEnabled , ModelMap model) {
		model.addAttribute("page", couponService.findPage(pageable));
		return "/admin/coupon/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		couponService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 生成优惠码
	 */
	@RequestMapping(value = "/build", method = RequestMethod.GET)
	public String build(Long id, ModelMap model) {
		Coupon coupon = couponService.find(id);
		model.addAttribute("coupon", coupon);
		model.addAttribute("totalCount", couponCodeService.count(coupon, null, null, null, null));
		model.addAttribute("usedCount", couponCodeService.count(coupon, null, null, null, true));
		return "/admin/coupon/build";
	}

	/**
	 * 下载优惠码
	 */
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public ModelAndView download(Long id, Integer count, ModelMap model) {
		if (count == null || count <= 0) {
			count = 50;
		}
		Coupon coupon = couponService.find(id);
		List<CouponCode> data = couponCodeService.build(coupon, null, count);
		String filename = "coupon_code_" + new SimpleDateFormat("yyyyMM").format(new Date()) + ".xls";
		String[] contents = new String[4];
		contents[0] = message("admin.coupon.type") + ": " + coupon.getName();
		contents[1] = message("admin.coupon.count") + ": " + count;
		contents[2] = message("admin.coupon.operator") + ": " + adminService.getCurrentUsername();
		contents[3] = message("admin.coupon.date") + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return new ModelAndView(new ExcelView(filename, null, new String[] { "code" }, new String[] { message("admin.coupon.title") }, null, null, data, contents), model);
	}

	
	/**
	 * 优惠券停用或启用
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/updateIsEnabled", method = RequestMethod.POST)
	public @ResponseBody
	Message updateIsEnabled(Long id) {
		Coupon coupon = couponService.find(id);
		coupon.setIsEnabled(!coupon.getIsEnabled());
		couponService.update(coupon);
		return SUCCESS_MESSAGE;
	}
	
	/***
	 * 领用明细
	 * @param pageable
	 * @param couponId
	 * @param isUsed
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Pageable pageable,Long couponId,Boolean isUsed, ModelMap model) {
		
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
		return "/admin/coupon/view";
	}
	
	
	
}