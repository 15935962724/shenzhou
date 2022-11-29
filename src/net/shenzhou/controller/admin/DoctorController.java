/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.FileInfo.FileType;
import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.Setting;
import net.shenzhou.entity.Admin;
import net.shenzhou.entity.Attribute;
import net.shenzhou.entity.Brand;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Evaluate;
import net.shenzhou.entity.Goods;
import net.shenzhou.entity.MemberRank;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Parameter;
import net.shenzhou.entity.ParameterGroup;
import net.shenzhou.entity.PaymentMethod;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Refunds;
import net.shenzhou.entity.Sn;
import net.shenzhou.entity.VisitMessage;
import net.shenzhou.entity.Doctor.Status;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.entity.Product.OrderType;
import net.shenzhou.entity.ProductCategory;
import net.shenzhou.entity.ProductImage;
import net.shenzhou.entity.Promotion;
import net.shenzhou.entity.Specification;
import net.shenzhou.entity.SpecificationValue;
import net.shenzhou.entity.Tag;
import net.shenzhou.entity.Tag.Type;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.BrandService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.EvaluateService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.GoodsService;
import net.shenzhou.service.MemberAttributeService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.ProductCategoryService;
import net.shenzhou.service.ProductImageService;
import net.shenzhou.service.ProductService;
import net.shenzhou.service.PromotionService;
import net.shenzhou.service.SpecificationService;
import net.shenzhou.service.SpecificationValueService;
import net.shenzhou.service.TagService;
import net.shenzhou.service.VisitMessageService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 医生
 * @date 2017-10-25 11:53:31
 * @author wsr
 *
 */
@Controller("adminDoctorController")
@RequestMapping("/admin/doctor")
public class DoctorController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService ;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService ;
	@Resource(name = "visitMessageServiceImpl")
	private VisitMessageService visitMessageService ;
	@Resource(name = "evaluateServiceImpl")
	private EvaluateService evaluateService ;
	
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", doctorService.findPage(pageable));
		return "/admin/doctor/list";
	}

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		Doctor doctor =  doctorService.find(id);
		List<Map<String,Object>> dateDays = DateUtil.getDateDays();
		
		List<Map<String,Object>> dateLists = new ArrayList<Map<String,Object>>();//获取本周的所有日期
		dateLists = DateUtil.getDates(DateUtil.getWeekNum());
		int startTime = 0;
		int endTiemt = 23;
		List<Map<String,Object>> workDates = new ArrayList<Map<String,Object>>();
		int count = 0;
		for (int i = startTime; i <= endTiemt; i++) {//////此处问题比较严重后期需要修改(待定)
			Map<String,Object> wordDate_map = new HashMap<String, Object>();
			wordDate_map.put("workDateTime", (startTime+count));
			count++;
			workDates.add(wordDate_map);
		}
		Map<String , Object> query_map = new HashMap<String, Object>();
		
		//如果点击今天weekNum就传空
		query_map.put("weekNum", DateUtil.getWeekNum());
		query_map.put("doctor", doctor);
		List<Order> weekOrders = orderService.getWeekOrder(query_map);//当前医师预约的订单(未服务的)
		List<Map<String,Object>> orders = new ArrayList<Map<String,Object>>();
		
		for (Order order : weekOrders) {
			Map<String,Object> map = new HashMap<String, Object>();
			System.out.println(order.getId());
			map.put("orderId", order.getId());
			map.put("orderSn", order.getSn());
			map.put("orderWorkDatItemStartTime", order.getWorkDayItem().getStartTime());
			map.put("orderWorkDay", order.getWorkDayItem().getWorkDay().getWorkDayDate());
			map.put("orderStartTime", Integer.valueOf(order.getWorkDayItem().getStartTime().split(":")[0]));
			orders.add(map);
		}
		Pageable pageable = new Pageable();
		pageable.setPageSize(1000);
		Map<String,Object> query_map1 = new HashMap<String, Object>();
		query_map1.put("doctor", doctor);
		query_map1.put("pageable", pageable);
		Page<VisitMessage> page = visitMessageService.getDoctorPage(query_map1);//回访信息
		
		Page<Evaluate> evaluatePage = evaluateService.getDoctorEvaluate(query_map1);//评价信息
		
		model.addAttribute("evaluatePage", evaluatePage);
		model.addAttribute("dateDays", dateDays);
		model.addAttribute("dateLists", dateLists);
		model.addAttribute("doctor", doctor);
		model.addAttribute("workDates", workDates);
		model.addAttribute("orders", orders);
		model.addAttribute("page", page);
		model.addAttribute("status", Status.values());
		return "/admin/doctor/view";
	}
	
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		doctorService.delete(ids);
		return SUCCESS_MESSAGE;
	}


	/**
	 * 删除回访消息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete_visit_message", method = RequestMethod.POST)
	public @ResponseBody
	Message delete_visit_message( Long id) {
		visitMessageService.delete(id);
		return SUCCESS_MESSAGE;
	}
	
	
	/**
	 * 删除评论信息
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete_doctor_evalueate", method = RequestMethod.POST)
	public @ResponseBody
	Message delete_doctor_evalueate(Long[] ids) {
		evaluateService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	

	/**
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public @ResponseBody
	Message updateStatus(Long id,Status status,String statusExplain) {
		Doctor doctor = doctorService.find(id);
		doctor.setStatus(status);
		if (status.equals(Status.allow)) {
			doctor.setIsReal(true);
		}
		doctor.setStatusExplain(statusExplain);
		doctorService.update(doctor);
		return SUCCESS_MESSAGE;
	}
	
	
	/**
	 * 审核医生资质证书
	 * @param orderId
	 * @param paymentMethodId
	 * @param refunds
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/updateDoctorImage", method = RequestMethod.POST)
	public String updateDoctorImage(Long doctorId, Integer index, Status status,String statusExplain, RedirectAttributes redirectAttributes) {
	    Doctor doctor = doctorService.find(doctorId);
	    doctor.getDoctorImages().get(index).setStatus(status);
	    doctor.getDoctorImages().get(index).setStatusExplain(statusExplain);
	    doctorService.update(doctor);		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:view.jhtml?id=" + doctorId;
	}

	
	
}