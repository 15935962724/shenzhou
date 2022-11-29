/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.OrderLog;
import net.shenzhou.entity.Sn;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.entity.Order.ShippingStatus;
import net.shenzhou.entity.OrderLog.Type;
import net.shenzhou.entity.Refunds;
import net.shenzhou.entity.Refunds.Method;
import net.shenzhou.entity.Refunds.Status;
import net.shenzhou.entity.User;
import net.shenzhou.service.AssessReportService;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderLogService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.PaymentMethodService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.RefundsService;
import net.shenzhou.service.SnService;
import net.shenzhou.service.UserService;
import net.shenzhou.service.WorkDayItemService;
import net.shenzhou.service.WorkDayService;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 退款单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appRefundsController")
@RequestMapping("/app/refunds")
public class RefundsController extends BaseController {

	@Resource(name = "refundsServiceImpl")
	private RefundsService refundsService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "orderLogServiceImpl")
	private OrderLogService orderLogService;
	
	
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "snServiceImpl")
	private SnService snService;
	
	


	/**
	 * 医生查看订单的退款情况
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
            Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue); 
            if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
		
			Order order = orderService.find(json.getLong("orderId"));
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("order", order);
			printWriter.write(JsonUtils.toString(refundsService.findMap(query_map))) ;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}


	/**
	 * 医生申请退款
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public void save(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
            Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue); 
            if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			Order order = orderService.find(json.getLong("orderId"));
			System.out.println(order.getId()+"号订单申请退款，申请人"+doctor.getName()+"，订单编号为:"+order.getSn());
			BigDecimal amount = new BigDecimal(String.valueOf(json.get("amount")));
			if (amount.compareTo(order.getAmountPaid())==1) {//判断如果退款金额大于订单 已付金额 就返回申请失败
				map.put("status", "400");
				map.put("message", "申请退款金额大于订单已付金额，系统不予受理");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			String memo = json.getString("memo"); //备注
			
			Refunds refunds = new Refunds();
			refunds.setSn(snService.generate(Sn.Type.refunds));
			refunds.setMethod(Method.deposit);
			refunds.setStatus(Status.applying);
			refunds.setPaymentMethod(order.getPaymentMethod().getName());
			refunds.setBank(null);
			refunds.setAccount(order.getMember().getMobile());
			refunds.setAmount(amount);
			refunds.setPayee(order.getMember().getName());
			refunds.setOperator(order.getDoctor().getName());
			refunds.setMemo(memo);
			refunds.setOrder(order);
			refunds.setIsDeleted(false);
			refunds.setMechanism(order.getMechanism());
			refundsService.save(refunds);
			order.setIsAbnormal(true);
			orderService.update(order);
			map.put("status", "200");
			map.put("message", "申请成功，请耐心等待");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}

	
	

	

}