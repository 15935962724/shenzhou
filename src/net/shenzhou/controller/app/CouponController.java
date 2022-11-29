/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.entity.Coupon;
import net.shenzhou.entity.Coupon.CouponType;
import net.shenzhou.entity.Coupon.GrantType;
import net.shenzhou.entity.CouponCode;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.CouponCodeService;
import net.shenzhou.service.CouponService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.ProjectItemService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.WorkDayItemService;
import net.shenzhou.service.WorkDayService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 优惠券
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appCouponController")
@RequestMapping("/app/coupon")
public class CouponController extends BaseController {

	@Resource(name = "couponServiceImpl")
	private CouponService couponService;
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	@Resource(name = "workDayServiceImpl")
	private WorkDayService workDayService;
	@Resource(name = "workDayItemServiceImpl")
	private WorkDayItemService workDayItemService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "projectItemServiceImpl")
	private ProjectItemService projectItemService;
	 /*
	 * 用户未使用(已过期)优惠券列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberUnusedCouponCodeList", method = RequestMethod.GET)
	public void memberUnusedCouponCodeList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		List<Map<String,Object>> couponCodes = new ArrayList<Map<String,Object>>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			Boolean hasExpired = json.getBoolean("hasExpired");
			Integer pageNumber = json.getInt("pageNumber");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("member", member);
			query_map.put("hasExpired", hasExpired);
			List<CouponCode> couponCodeList = couponCodeService.findUnusedByMember(query_map);
			
			Integer pageSize = 10;//每页条数
			String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			
			
			if(couponCodeList.size()<=0){
				data_map.put("couponCodes",couponCodes);
				map.put("status", "500");
				map.put("message", "暂无优惠券数据");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map));
				return;
			}
			
			//总页数
			Integer pagecount = ((couponCodeList.size()+pageSize-1)/pageSize);
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			List<CouponCode> couponCode_list = new ArrayList<CouponCode>();
			if (couponCodeList.size()>0){
				if(pageNumber>=pagecount){
					couponCode_list = couponCodeList.subList((pagenumber-1)*pageSize, couponCodeList.size());
				}else{
					couponCode_list = couponCodeList.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			 
			for(CouponCode couponCode : couponCode_list){
				Map<String,Object> couponCodeMap = new HashMap<String, Object>();
				couponCodeMap.put("couponName", couponCode.getCoupon().getName());
				couponCodeMap.put("validity",DateUtil.getDatetoString("yyyy-MM-dd", couponCode.getBeginDate())+"至"+DateUtil.getDatetoString("yyyy-MM-dd", couponCode.getEndDate()));
				couponCodeMap.put("restrict",couponCode.getCoupon().getGrantType().equals(GrantType.mechanism)?"限:"+couponCode.getCoupon().getMechanism().getName()+"可用":"全机构可用" );
				couponCodeMap.put("grantType", couponCode.getCoupon().getGrantType());
				couponCodeMap.put("minConsumptionPrice", couponCode.getCoupon().getMinConsumptionPrice());
				couponCodeMap.put("reductionPrice", couponCode.getCoupon().getReductionPrice());
				couponCodes.add(couponCodeMap);
			}
			
			data_map.put("couponCodes", couponCodes);
			
			map.put("status", status);
			map.put("message",message);
			map.put("data", JsonUtils.toJson(data_map));
	        printWriter.write(JsonUtils.toString(map));
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
	 * 用户领取优惠券
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberGetCouponCode", method = RequestMethod.GET)
	public void memberGetCouponCode(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			Coupon coupon = couponService.find(json.getLong("couponId"));
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			//coupon
			Set<Coupon> member_coupons = new HashSet<Coupon>();//获取用户领过的优惠券
			for (CouponCode	couponCode : member.getCouponCodes()) {
				member_coupons.add(couponCode.getCoupon());
			}
		
			if(member_coupons.contains(coupon)){
				map.put("status", "400");
				map.put("message","已领取过该优惠");
				map.put("data", JsonUtils.toJson(data_map));
		        printWriter.write(JsonUtils.toString(map));
				return;
			}
			
			couponCodeService.build(coupon, member);
			
			map.put("status", "200");
			map.put("message","领取成功");
			map.put("data", JsonUtils.toJson(data_map));
	        printWriter.write(JsonUtils.toString(map));
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
	 * 用户领券中心
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/couponList", method = RequestMethod.GET)
	public void couponList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		List<Map<String,Object>> couponCodes = new ArrayList<Map<String,Object>>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			Integer pageNumber = json.getInt("pageNumber");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			List<Coupon> coupons = couponService.findNotOvertimeCoupon();
			
			Integer pageSize = 10;//每页条数
			String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			
			
			if(coupons.size()<=0){
				data_map.put("couponCodes",couponCodes);
				map.put("status", "500");
				map.put("message", "暂无优惠券数据");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map));
				return;
			}
			
			//总页数
			Integer pagecount = ((coupons.size()+pageSize-1)/pageSize);
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			List<Coupon> couponCode_list = new ArrayList<Coupon>();
			if (coupons.size()>0){
				if(pageNumber>=pagecount){
					couponCode_list = coupons.subList((pagenumber-1)*pageSize, coupons.size());
				}else{
					couponCode_list = coupons.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			 
			for(Coupon coupon : couponCode_list){
				Map<String,Object> couponMap = new HashMap<String, Object>();
				
				Set<CouponCode> couponCodeList = member.getCouponCodes();
				Boolean isGet = false;
				for(CouponCode couponCode : couponCodeList){
					if(couponCode.getCoupon().equals(coupon)){
						isGet=true;
					}
				}
				couponMap.put("isGet", isGet);
				couponMap.put("couponName", coupon.getName());
				System.out.println(JsonUtils.toJson(coupon));
				couponMap.put("validity",DateUtil.getDatetoString("yyyy-MM-dd", coupon.getBeginDate())+"至"+DateUtil.getDatetoString("yyyy-MM-dd", coupon.getEndDate()));
				
				if(coupon.getGrantType()!=null&&coupon.getGrantType().equals(GrantType.mechanism)){
					couponMap.put("restrict","限:"+coupon.getMechanism().getName()+"可用");
				}else{
					couponMap.put("restrict","全机构可用");
				}
				couponMap.put("grantType", coupon.getGrantType());
				couponMap.put("minConsumptionPrice", coupon.getMinConsumptionPrice());
				couponMap.put("reductionPrice", coupon.getReductionPrice());//couponId
				couponMap.put("couponId", coupon.getId());
				couponCodes.add(couponMap);
			}
			
			data_map.put("couponCodes", couponCodes);
			
			map.put("status", status);
			map.put("message",message);
			map.put("data", JsonUtils.toJson(data_map));
	        printWriter.write(JsonUtils.toString(map));
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
	 * 用户可用优惠券列表(下单)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberUsableCoupon", method = RequestMethod.GET)
	public void memberUsableCoupon(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			Project project = projectService.find(json.getLong("projectId"));
			BigDecimal price = new BigDecimal(json.getString("price"));
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Member member = memberService.findBySafeKeyValue(safeKeyValue);
			if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			ServerProjectCategory serverProjectCategory = project.getParentServerProjectCategory();
			System.out.println("项目分类serverProjectCategoryParentId:"+serverProjectCategory.getId());
			System.out.println("项目最顶级serverProjectCategoryParentId:"+serverProjectCategoryService.findParent(serverProjectCategory).getId());
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("member", member);
			query_map.put("serverProjectCategory", serverProjectCategory);
			query_map.put("price", price);
			query_map.put("mechanism", project.getMechanism());
			
			Set<CouponCode> member_coupon_codes = new HashSet<CouponCode>();//获取用户领用的本机构的优惠券和平台的优惠券
			for (CouponCode	couponCode : member.getCouponCodes()) {
				if (couponCode.getCoupon().getMechanism().equals(project.getMechanism())||couponCode.getCoupon().getMechanism() == null) {
					member_coupon_codes.add(couponCode);
				}
			}
			
			List<Map<String,Object>> couponList = new ArrayList<Map<String,Object>>();
			
			if (member.getOrders().size()<=0) {
				for (CouponCode couponCode : member_coupon_codes) {
					Coupon conpon = couponCode.getCoupon();
					if (conpon.getCouponType().equals(CouponType.firstorder)) {
						Map<String,Object> couponMap = new HashMap<String, Object>();
						couponMap.put("isGet", true);
						couponMap.put("couponName", couponCode.getCoupon().getName());
						couponMap.put("validity",DateUtil.getDatetoString("yyyy-MM-dd", couponCode.getCoupon().getBeginDate())+"-"+DateUtil.getDatetoString("yyyy-MM-dd", couponCode.getCoupon().getEndDate()));
						if(couponCode.getCoupon().getGrantType()!=null&&couponCode.getCoupon().getGrantType().equals(GrantType.mechanism)){
							couponMap.put("restrict","限:"+couponCode.getCoupon().getMechanism().getName()+"可用");
						}else{
							couponMap.put("restrict","全机构可用");
						}
						couponMap.put("grantType", couponCode.getCoupon().getGrantType());
						couponMap.put("minConsumptionPrice", couponCode.getCoupon().getMinConsumptionPrice());
						couponMap.put("reductionPrice", couponCode.getCoupon().getReductionPrice());
						couponMap.put("couponCodeId", couponCode.getId());
						couponList.add(couponMap);
					}
					if (conpon.getCouponType().equals(CouponType.fullcut)) {
						if(conpon.getIsEnabled()//判断该优惠券是否启用
								&&conpon.hasBegun()//判断该优惠券是否开始
								&&!conpon.hasExpired()//判断该优惠券是否过期
								&&conpon.getServerProjectCategorys().contains(serverProjectCategory)//判断该优惠券是否可使用该项目
								&&conpon.getMinConsumptionPrice().compareTo(price)<0//判断该优惠券最低消费金额是否小于订单价格
								){
							Map<String,Object> couponMap = new HashMap<String, Object>();
							couponMap.put("isGet", true);
							couponMap.put("couponName", couponCode.getCoupon().getName());
							couponMap.put("validity",DateUtil.getDatetoString("yyyy-MM-dd", couponCode.getCoupon().getBeginDate())+"-"+DateUtil.getDatetoString("yyyy-MM-dd", couponCode.getCoupon().getEndDate()));
							if(couponCode.getCoupon().getGrantType()!=null&&couponCode.getCoupon().getGrantType().equals(GrantType.mechanism)){
								couponMap.put("restrict","限:"+couponCode.getCoupon().getMechanism().getName()+"可用");
							}else{
								couponMap.put("restrict","全机构可用");
							}
							couponMap.put("grantType", couponCode.getCoupon().getGrantType());
							couponMap.put("minConsumptionPrice", couponCode.getCoupon().getMinConsumptionPrice());
							couponMap.put("reductionPrice", couponCode.getCoupon().getReductionPrice());
							couponMap.put("couponCodeId", couponCode.getId());
							couponList.add(couponMap);
						}
					}
				}
			}else{
				for (CouponCode couponCode : member_coupon_codes) {
					Coupon conpon = couponCode.getCoupon();
					System.out.println("conponId:"+conpon.getId());
					if (conpon.getCouponType().equals(CouponType.fullcut)) {
						if(conpon.getIsEnabled()//判断该优惠券是否启用
								&&conpon.hasBegun()//判断该优惠券是否开始
								&&!conpon.hasExpired()//判断该优惠券是否过期
								&&conpon.getServerProjectCategorys().contains(serverProjectCategory)//判断该优惠券是否可使用该项目
								&&conpon.getMinConsumptionPrice().compareTo(price)<0//判断该优惠券最低消费金额是否小于订单价格
								){
							Map<String,Object> couponMap = new HashMap<String, Object>();
							couponMap.put("isGet", true);
							couponMap.put("couponName", couponCode.getCoupon().getName());
							couponMap.put("validity",DateUtil.getDatetoString("yyyy-MM-dd", couponCode.getCoupon().getBeginDate())+"-"+DateUtil.getDatetoString("yyyy-MM-dd", couponCode.getCoupon().getEndDate()));
							if(couponCode.getCoupon().getGrantType()!=null&&couponCode.getCoupon().getGrantType().equals(GrantType.mechanism)){
								couponMap.put("restrict","限:"+couponCode.getCoupon().getMechanism().getName()+"可用");
							}else{
								couponMap.put("restrict","全机构可用");
							}
							couponMap.put("grantType", couponCode.getCoupon().getGrantType());
							couponMap.put("minConsumptionPrice", couponCode.getCoupon().getMinConsumptionPrice());
							couponMap.put("reductionPrice", couponCode.getCoupon().getReductionPrice());
							couponMap.put("couponCodeId", couponCode.getId());
							couponList.add(couponMap);
						}
					}
				}
			}
			
			
		
			
			/*List<CouponCode> couponCodes = couponCodeService.memberUsableCoupon(query_map);
			List<Map<String,Object>> couponList = new ArrayList<Map<String,Object>>();
			
			for(CouponCode couponCode : couponCodes){
				if (couponCode.getCoupon().getMechanism().equals(project.getMechanism())||couponCode.getCoupon().getMechanism() == null) {
					Map<String,Object> couponMap = new HashMap<String, Object>();
					couponMap.put("isGet", true);
					couponMap.put("couponName", couponCode.getCoupon().getName());
					couponMap.put("validity",DateUtil.getDatetoString("yyyy-MM-dd", couponCode.getCoupon().getBeginDate())+"-"+DateUtil.getDatetoString("yyyy-MM-dd", couponCode.getCoupon().getEndDate()));
					if(couponCode.getCoupon().getGrantType()!=null&&couponCode.getCoupon().getGrantType().equals(GrantType.mechanism)){
						couponMap.put("restrict","限:"+couponCode.getCoupon().getMechanism().getName()+"可用");
					}else{
						couponMap.put("restrict","全机构可用");
					}
					couponMap.put("grantType", couponCode.getCoupon().getGrantType());
					couponMap.put("minConsumptionPrice", couponCode.getCoupon().getMinConsumptionPrice());
					couponMap.put("reductionPrice", couponCode.getCoupon().getReductionPrice());
					couponMap.put("couponCodeId", couponCode.getId());
					couponList.add(couponMap);
				}
			}*/
			
			data_map.put("couponList", couponList);
			
			map.put("status", "200");
			map.put("message","领取成功");
			map.put("data", JsonUtils.toJson(data_map));
	        printWriter.write(JsonUtils.toString(map));
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
	
	
	
}