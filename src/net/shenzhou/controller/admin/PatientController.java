/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.CommonAttributes;
import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.Setting;
import net.shenzhou.entity.Admin;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.BaseEntity.Save;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.MemberAttribute;
import net.shenzhou.entity.MemberAttribute.Type;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Payment;
import net.shenzhou.entity.RechargeLog;
import net.shenzhou.entity.Refunds;
import net.shenzhou.entity.VisitMessage;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.DeliveryCorpService;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.MemberAttributeService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.PaymentMethodService;
import net.shenzhou.service.RechargeLogService;
import net.shenzhou.service.ShippingMethodService;
import net.shenzhou.service.VisitMessageService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 会员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("adminPatientController")
@RequestMapping("/admin/patient")
public class PatientController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "visitMessageServiceImpl")
	private VisitMessageService visitMessageService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "rechargeLogServiceImpl")
	private RechargeLogService rechargeLogService;
	
	@Resource(name = "paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;
	
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	
	@Resource(name = "deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("pageable", pageable);
		Page<Member> page = memberService.getPatients(query_map);
		model.addAttribute("page", page);
		return "/admin/patient/list";
	}

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id,Pageable pageable, ModelMap model) {
		pageable.setPageSize(1000);
		Member patient = memberService.find(id);
		Map<String , Object> query_map = new HashMap<String, Object>();
		query_map.put("patientMember", patient);
		List<Order> weekOrders = orderService.getWeekOrder(query_map);//当前用户预约的订单(未服务的)
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
		
		query_map.put("pageable", pageable);
		Page<VisitMessage> visitMessagePage = visitMessageService.getPage(query_map);//回访信息
		
		model.addAttribute("visitMessagePage", visitMessagePage);
		model.addAttribute("orders", orders);
		model.addAttribute("workDates", workDates);
		model.addAttribute("dateDays", dateDays);
		model.addAttribute("dateLists", dateLists);
		model.addAttribute("patient", patient);
		
		model.addAttribute("methods", Payment.Method.values());
		model.addAttribute("refundsMethods", Refunds.Method.values());
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		model.addAttribute("deliveryCorps", deliveryCorpService.findAll());
		model.addAttribute("order", orderService.find(187l));
		
		
		
		
		return "/admin/patient/view";
	}

	
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Member member = memberService.find(id);
				if (member != null && member.getBalance().compareTo(new BigDecimal(0)) > 0) {
					return Message.error("admin.member.deleteExistDepositNotAllowed", member.getUsername());
				}
			}
			memberService.delete(ids);
		}
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
	
	
}