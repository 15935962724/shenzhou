/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.entity.Balance;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.MemberBill;
import net.shenzhou.entity.MemberBill.TimeType;
import net.shenzhou.service.BalanceService;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

/**
 * Controller - 预存款
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appDepositController")
@RequestMapping("/app/deposit")
public class DepositController extends BaseController {

	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "balanceServiceImpl")
	private BalanceService balanceService;
	
	/**
	 * 用户个人账单列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberBillList", method = RequestMethod.GET)
	public void memberBillList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			int page = Integer.parseInt(json.getString("pag")); 
			int pageSize = Config.pageSize.intValue(); 
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			List<MemberBill> memberBill_list = depositService.getMemberBill(member);
			List<MemberBill> memberBills_list = new ArrayList<MemberBill>();
			List<String> string_list = new ArrayList<String>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			//获取全部月份(从用户建档到当前月)
			Date date = member.getCreateDate();
			
			string_list.add("今天");
			string_list.add("最近7天");
			string_list.add("最近30天");
			for(int x = 0;;x++){
				System.out.println(DateUtil.dateFormat(date,x));
				System.out.println(format.format(new Date()));
				if(DateUtil.compare_date_month(DateUtil.dateFormat(new Date(),x), DateUtil.getDatetoString("yyyy-MM", date))==1){
					break;
				}
				string_list.add(DateUtil.dateFormat(new Date(),x));
			}
			
			//总充值
			BigDecimal income = new BigDecimal(0);
			//总支出
			BigDecimal expend = new BigDecimal(0);
			
			for(MemberBill memberBill : memberBill_list){
				BigDecimal incomes = new BigDecimal(memberBill.getTotalAddress());   
				BigDecimal expends = new BigDecimal(memberBill.getTotalRecharge());   
				income = income.add(incomes);
				expend = expend.add(expends);
			}
			
			//没有数据(一条没有)
			if(memberBill_list.size()<=0){
				data_map.put("balance", member.getCountBalance());
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "500");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			//总页数
			Integer pagecount = (memberBill_list.size()+pageSize-1)/pageSize;
			//页数
			Integer pagenumber = page>=pagecount?pagecount:page;
			
			if (page>pagecount) {//无更多数据
				data_map.put("balance", member.getCountBalance());
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "400");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(page==pagecount){
				data_map.put("memberBillList",memberBill_list.subList((pagenumber-1)*pageSize, memberBill_list.size()));
			}else{
				data_map.put("memberBillList", memberBill_list.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
			}
			
			
			//data_map.put("memberBillList", memberBill_list);
			data_map.put("balance", member.getCountBalance());
			data_map.put("date", string_list);
			data_map.put("income", income);
			data_map.put("expend", expend);
			data_map.put("key", "200");
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}
	
	

	/**
	 * 用户按月份查询个人账单列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberBillListByMonth", method = RequestMethod.GET)
	public void memberBillListByMonth(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			int page = Integer.parseInt(json.getString("pag")); 
			String month = json.getString("month");
			int pageSize = Config.pageSize.intValue(); 
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			List<MemberBill> memberBill_list = new ArrayList<MemberBill>();
			Map<String,Object> data_map = new HashMap<String, Object>();
			if(TimeType.TODAY.getClor().equals(month)||TimeType.WEEK.getClor().equals(month)||TimeType.MONTH.getClor().equals(month)){
				memberBill_list = depositService.getMemberBillByType(month,member);
			}else{
				memberBill_list = depositService.getMemberBillByMonth(month,member);
			}
			List<MemberBill> memberBills_list = new ArrayList<MemberBill>();
			List<String> string_list = new ArrayList<String>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			//获取全部月份(从用户建档到当前月)
			Date date = member.getCreateDate();
			string_list.add("今天");
			string_list.add("最近7天");
			string_list.add("最近30天");
			for(int x = 0;;x++){
				System.out.println(DateUtil.dateFormat(date,x));
				System.out.println(format.format(new Date()));
				if(DateUtil.compare_date_month(DateUtil.dateFormat(new Date(),x), DateUtil.getDatetoString("yyyy-MM", date))==1){
					break;
				}
				string_list.add(DateUtil.dateFormat(new Date(),x));
			}
			
			
			//总充值
			BigDecimal income = new BigDecimal(0);
			//总支出
			BigDecimal expend = new BigDecimal(0);
			
			for(MemberBill memberBill : memberBill_list){
				BigDecimal incomes = new BigDecimal(memberBill.getTotalAddress());   
				BigDecimal expends = new BigDecimal(memberBill.getTotalRecharge());   
				income = income.add(incomes);
				expend = expend.add(expends);
			}
			
			//没有数据(一条没有)
			if(memberBill_list.size()<=0){
				data_map.put("balance", member.getCountBalance());
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "500");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			//总页数
			Integer pagecount = (memberBill_list.size()+pageSize-1)/pageSize;
			//页数
			Integer pagenumber = page>=pagecount?pagecount:page;
			
			if (page>pagecount) {//无更多数据
				data_map.put("balance", member.getCountBalance());
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "400");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(page==pagecount){
				data_map.put("memberBillList",memberBill_list.subList((pagenumber-1)*pageSize, memberBill_list.size()));
			}else{
				data_map.put("memberBillList", memberBill_list.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
			}
			
			
			//data_map.put("memberBillList", memberBill_list);
			data_map.put("balance", member.getCountBalance());
			data_map.put("date", string_list);
			data_map.put("key", "200");
			data_map.put("income", income);
			data_map.put("expend", expend);
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}
	
	/**
	 * 用户按时间段查询个人账单列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberBillListByParagraph", method = RequestMethod.GET)
	public void memberBillListByParagraph(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			int page = Integer.parseInt(json.getString("pag")); 
			String startTime = json.getString("startTime");
			String endTime = json.getString("endTime");
			int pageSize = Config.pageSize.intValue(); 
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(StringUtils.isEmpty(startTime)||StringUtils.isEmpty(endTime)){
				map.put("status", "400");
				map.put("message", "请选择开始时间和结束时间");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			List<MemberBill> memberBill_list = new ArrayList<MemberBill>();
			Map<String,Object> data_map = new HashMap<String, Object>();
			memberBill_list = depositService.getMemberBillByParagraph(startTime,endTime,member);
			List<MemberBill> memberBills_list = new ArrayList<MemberBill>();
			List<String> string_list = new ArrayList<String>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			//获取全部月份(从用户建档到当前月)
			Date date = member.getCreateDate();
			string_list.add("今天");
			string_list.add("最近7天");
			string_list.add("最近30天");
			for(int x = 0;;x++){
				System.out.println(DateUtil.dateFormat(date,x));
				System.out.println(format.format(new Date()));
				if(DateUtil.compare_date_month(DateUtil.dateFormat(new Date(),x), DateUtil.getDatetoString("yyyy-MM", date))==1){
					break;
				}
				string_list.add(DateUtil.dateFormat(new Date(),x));
			}
			
			
			//总充值
			BigDecimal income = new BigDecimal(0);
			//总支出
			BigDecimal expend = new BigDecimal(0);
			
			for(MemberBill memberBill : memberBill_list){
				BigDecimal incomes = new BigDecimal(memberBill.getTotalAddress());   
				BigDecimal expends = new BigDecimal(memberBill.getTotalRecharge());   
				income = income.add(incomes);
				expend = expend.add(expends);
			}
			
			//没有数据(一条没有)
			if(memberBill_list.size()<=0){
				data_map.put("balance", member.getCountBalance());
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "500");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			//总页数
			Integer pagecount = (memberBill_list.size()+pageSize-1)/pageSize;
			//页数
			Integer pagenumber = page>=pagecount?pagecount:page;
			
			if (page>pagecount) {//无更多数据
				data_map.put("balance", member.getCountBalance());
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "400");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(page==pagecount){
				data_map.put("memberBillList",memberBill_list.subList((pagenumber-1)*pageSize, memberBill_list.size()));
			}else{
				data_map.put("memberBillList", memberBill_list.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
			}
			
			
			//data_map.put("memberBillList", memberBill_list);
			data_map.put("balance", member.getCountBalance());
			data_map.put("date", string_list);
			data_map.put("key", "200");
			data_map.put("income", income);
			data_map.put("expend", expend);
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}
	
	
	/**
	 * 医生个人账单列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doctorBillList", method = RequestMethod.GET)
	public void doctorBillList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			int page = Integer.parseInt(json.getString("pag")); 
			int pageSize = Config.pageSize.intValue(); 
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
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			//从订单获取医生当月的账单信息
			List<MemberBill> doctorBillList = orderService.getDoctorBill(doctor);
			List<MemberBill> memberBills_list = new ArrayList<MemberBill>();
			List<String> string_list = new ArrayList<String>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			//获取全部月份(从用户建档到当前月)
			Date date = doctor.getCreateDate();
			
			for(int x = 0;;x++){
				System.out.println(DateUtil.dateFormat(date,x));
				System.out.println(format.format(new Date()));
				if(DateUtil.compare_date_month(DateUtil.dateFormat(new Date(),x), DateUtil.getDatetoString("yyyy-MM", date))==1){
					break;
				}
				string_list.add(DateUtil.dateFormat(new Date(),x));
			}
			
			
			//总充值
			BigDecimal income = new BigDecimal(0);
			//总支出
			BigDecimal expend = new BigDecimal(0);
			
			for(MemberBill memberBill : doctorBillList){
				BigDecimal incomes = new BigDecimal(memberBill.getTotalAddress());   
				BigDecimal expends = new BigDecimal(memberBill.getTotalRecharge());   
				income = income.add(incomes);
				expend = expend.add(expends);
			}
			
			//没有数据(一条没有)
			if(doctorBillList.size()<=0){
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "500");
				data_map.put("income", income);
				data_map.put("expend", expend);
				data_map.put("balance", "0");
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			//总页数
			Integer pagecount = (doctorBillList.size()+pageSize-1)/pageSize;
			//页数
			Integer pagenumber = page>=pagecount?pagecount:page;
			
			if (page>pagecount) {//无更多数据
				data_map.put("date", string_list);
				data_map.put("income", income);
				data_map.put("expend", expend);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "400");
				data_map.put("balance", "0");
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(page==pagecount){
				data_map.put("memberBillList",doctorBillList.subList((pagenumber-1)*pageSize, doctorBillList.size()));
			}else{
				data_map.put("memberBillList", doctorBillList.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
			}
			
			//data_map.put("memberBillList", memberBill_list);
			data_map.put("date", string_list);
			data_map.put("income", income);
			data_map.put("expend", expend);
			data_map.put("key", "200");
			data_map.put("balance", "0");
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}
	
	/**
	 * 医生按月份查询个人账单列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doctorBillListByMonth", method = RequestMethod.GET)
	public void doctorBillListByMonth(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			int page = Integer.parseInt(json.getString("pag")); 
			String month = json.getString("month");
			int pageSize = Config.pageSize.intValue(); 
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
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			List<MemberBill> doctorBill_list = orderService.getDoctorBillByMonth(month,doctor);
			List<MemberBill> memberBills_list = new ArrayList<MemberBill>();
			List<String> string_list = new ArrayList<String>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			//获取全部月份(从用户建档到当前月)
			Date date = doctor.getCreateDate();
			
			for(int x = 0;;x++){
				System.out.println(DateUtil.dateFormat(date,x));
				System.out.println(format.format(new Date()));
				if(DateUtil.compare_date_month(DateUtil.dateFormat(new Date(),x), DateUtil.getDatetoString("yyyy-MM", date))==1){
					break;
				}
				string_list.add(DateUtil.dateFormat(new Date(),x));
			}
			

			//总充值
			BigDecimal income = new BigDecimal(0);
			//总支出
			BigDecimal expend = new BigDecimal(0);
			
			for(MemberBill memberBill : doctorBill_list){
				BigDecimal incomes = new BigDecimal(memberBill.getTotalAddress());   
				BigDecimal expends = new BigDecimal(memberBill.getTotalRecharge());   
				income = income.add(incomes);
				expend = expend.add(expends);
			}
			
			
			//没有数据(一条没有)
			if(doctorBill_list.size()<=0){
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "500");
				data_map.put("balance", "0");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			
			//总页数
			Integer pagecount = (doctorBill_list.size()+pageSize-1)/pageSize;
			//页数
			Integer pagenumber = page>=pagecount?pagecount:page;
			
			if (page>pagecount) {//无更多数据
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "400");
				data_map.put("balance", "0");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(page==pagecount){
				data_map.put("memberBillList",doctorBill_list.subList((pagenumber-1)*pageSize, doctorBill_list.size()));
			}else{
				data_map.put("memberBillList", doctorBill_list.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
			}
			
			
			//data_map.put("memberBillList", memberBill_list);
			data_map.put("date", string_list);
			data_map.put("key", "200");
			data_map.put("balance", "0");
			data_map.put("income", income);
			data_map.put("expend", expend);
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}
	
	
	
	/**
	 * 用户查询个人账单列表和筛选
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberBillByMonths", method = RequestMethod.GET)
	public void memberBillByMonths(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			int page = Integer.parseInt(json.getString("pag")); 
			String mechanismid = json.getString("mechanismid");
			String startDay = json.getString("startDay");
			
			Date startDates = null;
			Date endDates = null;
			String endDay = null;
			if(startDay != null && !startDay.equals("")){
				String month = new String(startDay.getBytes("iso-8859-1"), "utf-8");
				System.out.println(month);
				if(month.equals("本月")){//判断传进来的开始日期是否是本月,之后获取本月的第一天和最后一天
					Date date = new Date();
					SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM");
					month = format_day.format(date);
					Map<String, String> maps = new HashMap<String, String>();//获取月的第一天和最后一天
					try {
						maps = DateUtil.getDateStartEnd(month);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String startDate  = maps.get("startDate");
					String endDate =  maps.get("endDate");
					startDates = DateUtil.getStringtoDate(startDate, "yyyy-MM-dd HH:mm:ss");
					endDates = DateUtil.getStringtoDate(endDate, "yyyy-MM-dd HH:mm:ss");
				}
				endDay = json.getString("endDay");
				if(endDay != null && !endDay.equals("")){
					String startDate  = month+" 00:00:00";
					String endDate =  endDay+" 23:59:59";
					startDates = DateUtil.getStringtoDate(startDate, "yyyy-MM-dd HH:mm:ss");
					endDates = DateUtil.getStringtoDate(endDate, "yyyy-MM-dd HH:mm:ss");
				}else{
					//Date date = new Date();
					//SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM");
					//month = format_day.format(month);
					Map<String, String> maps = new HashMap<String, String>();//获取月的第一天和最后一天
					try {
						maps = DateUtil.getDateStartEnd(month);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String startDate  = maps.get("startDate");
					String endDate =  maps.get("endDate");
					startDates = DateUtil.getStringtoDate(startDate, "yyyy-MM-dd HH:mm:ss");
					endDates = DateUtil.getStringtoDate(endDate, "yyyy-MM-dd HH:mm:ss");
				}
			}
			
			String type = json.getString("type");
			int pageSize = Config.pageSize.intValue(); 
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			
			List<Mechanism> mechanismList = mechanismService.getMechanism();//机构列表
			
			
			List<MemberBill> memberBill_list = null;
			Map<String,Object> data_map = new HashMap<String, Object>();
			List<Mechanism> map_list = new ArrayList<Mechanism>();
			 for(Mechanism mechanism : mechanismList){
				
				 Mechanism m = new Mechanism();
				 m.setId(mechanism.getId());
				 m.setName(mechanism.getName());
				map_list.add(m);
			 }
			 Mechanism m = new Mechanism();
			 m.setId(0L);
			 m.setName("系统平台");
			map_list.add(m);
			try {
				memberBill_list = depositService.getMemberBillByDay(mechanismid,DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", startDates),DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", endDates),type,member);
			} catch (Exception e) {
				// TODO: handle exception
			}
			Mechanism ms = null;
			if(mechanismid != null && !mechanismid.equals("")){
				ms = mechanismService.getMechanismInfo(Long.parseLong(mechanismid));
			}
			if(memberBill_list == null){
				memberBill_list = new ArrayList<MemberBill>();
			}
			//没有数据(一条没有)
			if(memberBill_list.size()<=0){
				
				data_map.put("memberBillList", memberBill_list);
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			List<MemberBill> memberBills_list = new ArrayList<MemberBill>();
			DecimalFormat df = new DecimalFormat("######0.00"); 
			if(startDay.length()< 8){//判断开始日期长度是否大于8
				if(startDay == null || startDay.equals("")){
					
				}else{
					for(MemberBill memberBill : memberBill_list){
						SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM");
						String start = format_day.format(DateUtil.getStringtoDate(memberBill.getBillDay(), "yyyy-MM-dd"));
						String end = null;
						if(memberBill.getBillDays() != null){
							end = format_day.format(DateUtil.getStringtoDate(memberBill.getBillDays(), "yyyy-MM-dd"));
						}
						
						memberBill.setBillDay(start);
						memberBill.setBillDays(end);
						Double address = Double.valueOf(memberBill.getTotalAddress());
						memberBill.setTotalAddress(df.format(address));
						Double number = Math.abs(Double.parseDouble(memberBill.getTotalRecharge()));
						memberBill.setTotalRecharge(String.valueOf(number));
						memberBills_list.add(memberBill);
					}
				}
				
				
			}else{
				for(MemberBill memberBill : memberBill_list){
					SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM-dd");
					String start = format_day.format(DateUtil.getStringtoDate(memberBill.getBillDay(), "yyyy-MM-dd"));
					String end = null;
					if(memberBill.getBillDays() != null){
						end = format_day.format(DateUtil.getStringtoDate(memberBill.getBillDays(), "yyyy-MM-dd"));
					}
					memberBill.setBillDay(start);
					memberBill.setBillDays(end);
					Double address = Double.valueOf(memberBill.getTotalAddress());
					memberBill.setTotalAddress(df.format(address));
					Double number = Math.abs(Double.parseDouble(memberBill.getTotalRecharge()));
					memberBill.setTotalRecharge(String.valueOf(number));
					memberBills_list.add(memberBill);
				}
			}
				
			
			/*List<String> string_list = new ArrayList<String>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//获取全部月份(从用户建档到当前月)
			Date date = member.getCreateDate();
			string_list.add("今天");
			string_list.add("最近7天");
			string_list.add("最近30天");*/
			/*for(int x = 0;;x++){
				System.out.println(DateUtil.dateFormat(date,x));
				System.out.println(format.format(new Date()));
				if(DateUtil.compare_date_days(DateUtil.dateFormat(new Date(),x), DateUtil.getDatetoString("yyyy-MM", date))==1){
					break;
				}
				string_list.add(DateUtil.dateFormat(new Date(),x));
			}*/
			
			
			//总充值
			BigDecimal income = new BigDecimal(0);
			//总支出
			BigDecimal expend = new BigDecimal(0);
			
			for(MemberBill memberBill : memberBill_list){
				BigDecimal incomes = new BigDecimal(memberBill.getTotalAddress());   
				BigDecimal expends = new BigDecimal(memberBill.getTotalRecharge());   
				income = income.add(incomes);
				expend = expend.add(expends);
			}
			
			
			
			//总页数
			Integer pagecount = (memberBill_list.size()+pageSize-1)/pageSize;
			//页数
			Integer pagenumber = page>=pagecount?pagecount:page;
			
			if (page>pagecount) {//无更多数据
				data_map.put("balance", member.getCountBalance());
				data_map.put("startDay", startDay);
				data_map.put("endDay", endDay);
				data_map.put("type", type);
				data_map.put("mechanismid", mechanismid);
				data_map.put("mechanismid", map_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "400");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(page==pagecount){
				data_map.put("memberBillList",memberBill_list.subList((pagenumber-1)*pageSize, memberBill_list.size()));
			}else{
				data_map.put("memberBillList", memberBill_list.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
			}
			
			
			data_map.put("memberBillList", memberBill_list);
			data_map.put("mechanismid", map_list);
			if(ms != null){
				data_map.put("id", mechanismid);
				data_map.put("name", ms.getName());
			}
			
			/*data_map.put("balance", member.getCountBalance());
			data_map.put("mechanismid", mechanismid);
			data_map.put("startDay", startDay);
			data_map.put("endDay", endDay);
			data_map.put("type", type);
			data_map.put("key", "200");
			data_map.put("income", income);
			data_map.put("expend", expend);*/
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}
	
	
	
	
	/**
	 * 用户按月份查询个人账单列表(wo)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberBillListByDay", method = RequestMethod.GET)
	public void memberBillListBy(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			int page = Integer.parseInt(json.getString("pag")); 
			String mechanismid = json.getString("mechanismid");
			String startDay = json.getString("startDay");
			String endDay = json.getString("endDay");
			String type = json.getString("type");
			if(startDay.equals("本月")){
				Date date = new Date();
				SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
				startDay = format_month.format(date);
			}
			int pageSize = Config.pageSize.intValue(); 
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			List<MemberBill> memberBill_list = new ArrayList<MemberBill>();
			Map<String,Object> data_map = new HashMap<String, Object>();
			/*if(TimeType.TODAY.getClor().equals(month)||TimeType.WEEK.getClor().equals(month)||TimeType.MONTH.getClor().equals(month)){
				memberBill_list = depositService.getMemberBillByType(month,member);
			}else{*/
			
			Mechanism mechanism = null;
			if(mechanismid == null || mechanismid.equals("")){
				
			}else{
				mechanism = mechanismService.find(Long.parseLong(mechanismid));
			}
			memberBill_list = depositService.getMemberBillBy(mechanism,type,startDay,endDay,member);
			/*}*/
			List<MemberBill> memberBills_list = new ArrayList<MemberBill>();
			List<String> string_list = new ArrayList<String>();
			/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			//获取全部月份(从用户建档到当前月)
			Date date = member.getCreateDate();
			string_list.add("今天");
			string_list.add("最近7天");
			string_list.add("最近30天");
			for(int x = 0;;x++){
				System.out.println(DateUtil.dateFormat(date,x));
				System.out.println(format.format(new Date()));
				if(DateUtil.compare_date_month(DateUtil.dateFormat(new Date(),x), DateUtil.getDatetoString("yyyy-MM", date))==1){
					break;
				}
				string_list.add(DateUtil.dateFormat(new Date(),x));
			}*/
			
			
			//总充值
			BigDecimal income = new BigDecimal(0);
			//总支出
			BigDecimal expend = new BigDecimal(0);
			
			for(MemberBill memberBill : memberBill_list){
				BigDecimal incomes = new BigDecimal(memberBill.getTotalAddress());   
				BigDecimal expends = new BigDecimal(memberBill.getTotalRecharge());   
				income = income.add(incomes);
				expend = expend.add(expends);
			}
			
			//没有数据(一条没有)
			if(memberBill_list.size()<=0){
				data_map.put("balance", member.getCountBalance());
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "500");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			//总页数
			Integer pagecount = (memberBill_list.size()+pageSize-1)/pageSize;
			//页数
			Integer pagenumber = page>=pagecount?pagecount:page;
			
			if (page>pagecount) {//无更多数据
				data_map.put("balance", member.getCountBalance());
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "400");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(page==pagecount){
				data_map.put("memberBillList",memberBill_list.subList((pagenumber-1)*pageSize, memberBill_list.size()));
			}else{
				data_map.put("memberBillList", memberBill_list.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
			}
			
			
			//data_map.put("memberBillList", memberBill_list);
			data_map.put("balance", member.getCountBalance());
			//data_map.put("date", memberBill_list);
			data_map.put("key", "200");
			data_map.put("income", income);
			data_map.put("expend", expend);
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}
	
	
	
	/**
	 * 暂时不用
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/depositList", method = RequestMethod.GET)
	public void depositList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			int page = Integer.parseInt(json.getString("pageNumber")); 
			int pageSize = Config.pageSize.intValue(); 
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			List<Deposit> deposit_list = depositService.getDepositAllList(member);
			Map<String,Object> data_map = new HashMap<String, Object>();
			//Mechanism mechanism = mechanismService.find(Long.parseLong(mechanismid));
				//memberBill_list = depositService.getMemberBillBy(mechanism,type,startDay,endDay,member);
			List<MemberBill> memberBills_list = new ArrayList<MemberBill>();
			List<String> string_list = new ArrayList<String>();
			
			
			
			//总充值
			BigDecimal income = new BigDecimal(0);
			//总支出
			BigDecimal expend = new BigDecimal(0);
			Map<String,Object> map_list = new HashMap<String, Object>();
			Map<String,Object> map_list1 = new HashMap<String, Object>();
			List<Map<String,Object>> deposit_map = new ArrayList<Map<String,Object>>();
			int number =0;
			for(Deposit deposit : deposit_list){
				
				if(deposit.getMechanism() == null){
					map_list.put("balance", deposit.getBalance());
					map_list.put("name", "(好康护)平台系统");
					deposit_map.add(map_list);
				}else{
					if(number == 0){
						List<Balance> list = balanceService.getBalanceList(member);
						for(Balance balance : list){
							map_list1.put("balance", balance.getBalance());
							map_list1.put("name", balance.getMechanism().getName());
							deposit_map.add(map_list1);
						}
						number++;
						
					}
				}
				
				
				
			}
			
			//没有数据(一条没有)
			if(deposit_map.size()<=0){
				data_map.put("balance", member.getCountBalance());
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "500");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			//总页数
			Integer pagecount = (deposit_map.size()+pageSize-1)/pageSize;
			//页数
			Integer pagenumber = page>=pagecount?pagecount:page;
			
			if (page>pagecount) {//无更多数据
				data_map.put("balance", member.getCountBalance());
				data_map.put("date", string_list);
				data_map.put("memberBillList", memberBills_list);
				data_map.put("key", "400");
				data_map.put("income", income);
				data_map.put("expend", expend);
				
				map.put("status", "200");
				map.put("message", "数据加载成功");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(page==pagecount){
				data_map.put("balanceList",deposit_map.subList((pagenumber-1)*pageSize, deposit_map.size()));
			}else{
				data_map.put("balanceList", deposit_map.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
			}
			
			
			/*//data_map.put("memberBillList", memberBill_list);
			data_map.put("balance", member.getCountBalance());
			//data_map.put("date", memberBill_list);
			data_map.put("key", "200");
			data_map.put("income", income);
			data_map.put("expend", expend);*/
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}
}
