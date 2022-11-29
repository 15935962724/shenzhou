/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Setting;
import net.shenzhou.Setting.PointGrantType;
import net.shenzhou.entity.BeforehandLogin;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorPointLog;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.MemberPointLog;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.BankCardService;
import net.shenzhou.service.BeforehandLoginService;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.CartService;
import net.shenzhou.service.DoctorCategoryService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorPointLogService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.EvaluateService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberPointLogService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.OrganizationService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.RSAService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.service.WithdrawDepositService;
import net.shenzhou.service.WorkDayItemService;
import net.shenzhou.service.WorkDayService;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MobileUtil;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 积分
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appPointController")
@RequestMapping("/app/point")
public class PointController extends BaseController {
	

	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "verificationServiceImpl")
	private VerificationService verificationService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "bankCardServiceImpl")
	private BankCardService bankCardService;
	@Resource(name = "withdrawDepositServiceImpl")
	private WithdrawDepositService withdrawDepositService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	@Resource(name = "evaluateServiceImpl")
	private EvaluateService evaluateService;
	@Resource(name = "doctorCategoryServiceImpl")
	private DoctorCategoryService doctorCategoryService;
	@Resource(name = "workDayServiceImpl")
	private WorkDayService workDayService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "workDayItemServiceImpl")
	private WorkDayItemService workDayItemService;
	@Resource(name = "organizationServiceImpl")
	private OrganizationService organizationService;
	@Resource(name = "beforehandLoginServiceImpl")
	private BeforehandLoginService beforehandLoginService;
	@Resource(name = "doctorPointLogServiceImpl")
	private DoctorPointLogService doctorPointLogService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	@Resource(name = "memberPointLogServiceImpl")
	private MemberPointLogService memberPointLogService;
	
	/**
	 * 用户以获得积分列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberAcquire", method = RequestMethod.GET)
	public void memberAcquire(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        List<Map<String,Object>> pointList = new ArrayList<Map<String,Object>>();
        Map<String,Object> data_map = new HashMap<String, Object>();
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
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
			
			String account = json.getString("account");
			String startTime = json.getString("startTime");
			String engTime = json.getString("endTime");
			Integer pageNumber = json.getInt("pageNumber");
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("account", account);
			query_map.put("startTime", startTime);
			query_map.put("engTime", engTime);
			query_map.put("member", member);
			query_map.put("pageNumber", pageNumber);
			List<MemberPointLog> memberPointLogs = memberPointLogService.findMemberAcquire(query_map);
			
			Integer pageSize = Config.pageSize;//每页条数
			String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			
			
			if(memberPointLogs.size()<=0){
				
				data_map.put("pointList",pointList);
//				data_map.put("point", member.getPoint());
				//用户的有效积分
				data_map.put("point", member.getCountPoint());
				
				map.put("status", "500");
				map.put("message", "暂无健康金数据");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map));
				return;
			}
			
			//总页数
			Integer pagecount = ((memberPointLogs.size()+pageSize-1)/pageSize);
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			List<MemberPointLog> point_list = new ArrayList<MemberPointLog>();
			if (memberPointLogs.size()>0){
				if(pageNumber>=pagecount){
					point_list = memberPointLogs.subList((pagenumber-1)*pageSize, memberPointLogs.size());
				}else{
					point_list = memberPointLogs.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			
			
			for(MemberPointLog memberPointLog : point_list){
				Map<String,Object> pointMap = new HashMap<String, Object>();
				pointMap.put("createDate", memberPointLog.getCreateDate());
				pointMap.put("type", memberPointLog.getType());
				pointMap.put("credit", memberPointLog.getCredit().toString().equals("0.0")?"-"+memberPointLog.getDebit().toString():"+"+memberPointLog.getCredit().toString());
				pointMap.put("memo", memberPointLog.getMemo());
				pointMap.put("isExpired", memberPointLog.isExpired());
				pointList.add(pointMap);
			}
			
			data_map.put("pointList",pointList);
//			data_map.put("point", member.getPoint());
			//用户的有效积分
			data_map.put("point", member.getCountPoint());
			
			map.put("status", status);
			map.put("message", message);
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
	 * 用户未获得积分列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberUnAcquire", method = RequestMethod.GET)
	public void memberUnAcquire(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        List<Map<String,Object>> point_list = new ArrayList<Map<String,Object>>();
        Map<String,Object> data_map = new HashMap<String, Object>();
        Setting setting = SettingUtils.get();
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
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
			
			String account = json.getString("account");
			String startTime = json.getString("startTime");
			String engTime = json.getString("endTime");
			Integer pageNumber = json.getInt("pageNumber");
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("account", account);
			query_map.put("startTime", startTime);
			query_map.put("engTime", engTime);
			query_map.put("mobile", member.getMobile());
			query_map.put("pageNumber", pageNumber);
			List<BeforehandLogin> beforehandLogins = beforehandLoginService.findMemberUnAcquire(query_map);
			
			List<Map<String,Object>> beforehandLoginList = new ArrayList<Map<String,Object>>();
			
			for(BeforehandLogin beforehandLogin : beforehandLogins){
				//判断是一次发放积分,还是二次发放积分.生成几条记录
				/**一次发放**/
				if(setting.getPointGrantType().equals(PointGrantType.once)){
					if(!beforehandLogin.isNotarizeLogin()){
						Map<String,Object> pointMap = new HashMap<String, Object>();
						pointMap.put("createDate", beforehandLogin.getCreateDate());
						pointMap.put("message", "邀请"+MobileUtil.getStarString2(beforehandLogin.getMobile(),3,4)+",待完成注册");
						pointMap.put("point", setting.getFirstMemberInvitationMemberPoint());
						beforehandLoginList.add(pointMap);
 					}
				}
				if(setting.getPointGrantType().equals(PointGrantType.secondary)){
					if(!beforehandLogin.isNotarizeLogin()){
						Map<String,Object> pointMap = new HashMap<String, Object>();
						pointMap.put("createDate", beforehandLogin.getCreateDate());
						pointMap.put("message", "邀请"+MobileUtil.getStarString2(beforehandLogin.getMobile(),3,4)+",待完成注册");
						pointMap.put("point", setting.getFirstMemberInvitationMemberPoint());
						beforehandLoginList.add(pointMap);
 					}
					if(!beforehandLogin.isPurchase()){
						Map<String,Object> pointMap = new HashMap<String, Object>();
						pointMap.put("createDate", beforehandLogin.getCreateDate());
						pointMap.put("message", "邀请"+MobileUtil.getStarString2(beforehandLogin.getMobile(),3,4)+",待完成5次下单");
						pointMap.put("point", setting.getSecondaryMemberInvitationMemberPoint());
						beforehandLoginList.add(pointMap);
					}
				}
				
			}
			
			Integer pageSize = Config.pageSize;//每页条数
			String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			
			if(beforehandLoginList.size()<=0){
				
				data_map.put("pointList",point_list);
//				data_map.put("point", member.getPoint());
				//用户的有效积分
				data_map.put("point", member.getCountPoint());
				
				map.put("status", "500");
				map.put("message", "暂无健康金数据");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map));
				return;
			}
			
			//总页数
			Integer pagecount = ((beforehandLoginList.size()+pageSize-1)/pageSize);
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			if (beforehandLoginList.size()>0){
				if(pageNumber>=pagecount){
					point_list = beforehandLoginList.subList((pagenumber-1)*pageSize, beforehandLoginList.size());
				}else{
					point_list = beforehandLoginList.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			
			
			data_map.put("pointList",point_list);
//			data_map.put("point", member.getPoint());
			//用户的有效积分
			data_map.put("point", member.getCountPoint());
			
			map.put("status", status);
			map.put("message", message);
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
	 * 医生以获得积分列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doctorAcquire", method = RequestMethod.GET)
	public void doctorAcquire(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        List<Map<String,Object>> pointList = new ArrayList<Map<String,Object>>();
        Map<String,Object> data_map = new HashMap<String, Object>();
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			String account = json.getString("account");
			String startTime = json.getString("startTime");
			String engTime = json.getString("endTime");
			Integer pageNumber = json.getInt("pageNumber");
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("account", account);
			query_map.put("startTime", startTime);
			query_map.put("engTime", engTime);
			query_map.put("doctor", doctor);
			List<DoctorPointLog> doctorPointLogs = doctorPointLogService.findDoctorAcquire(query_map);
			
			
			Integer pageSize = Config.pageSize;//每页条数
			String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			
			if(doctorPointLogs.size()<=0){
				
				data_map.put("pointList",pointList);
				data_map.put("point",doctor.getPoint());
				
				map.put("status", "500");
				map.put("message", "暂无健康金数据");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map));
				return;
			}
			
			//总页数
			Integer pagecount = ((doctorPointLogs.size()+pageSize-1)/pageSize);
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			List<DoctorPointLog> point_list = new ArrayList<DoctorPointLog>();
			if (doctorPointLogs.size()>0){
				if(pageNumber>=pagecount){
					point_list = doctorPointLogs.subList((pagenumber-1)*pageSize, doctorPointLogs.size());
				}else{
					point_list = doctorPointLogs.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			
			
			for(DoctorPointLog doctorPointLog : point_list){
				Map<String,Object> pointMap = new HashMap<String, Object>();
				pointMap.put("createDate", doctorPointLog.getCreateDate());
				pointMap.put("type", doctorPointLog.getGenre());
				pointMap.put("credit", "+"+doctorPointLog.getPointChange());
				pointMap.put("memo", doctorPointLog.getComment());
				
				pointList.add(pointMap);
			}
			
			data_map.put("pointList",pointList);
			data_map.put("point",doctor.getPoint());
			
			map.put("status", status);
			map.put("message", message);
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
	 * 医生未获得积分列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doctorUnAcquire", method = RequestMethod.GET)
	public void doctorUnAcquire(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        List<Map<String,Object>> point_list = new ArrayList<Map<String,Object>>();
        Map<String,Object> data_map = new HashMap<String, Object>();
        Setting setting = SettingUtils.get();
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			String account = json.getString("account");
			String startTime = json.getString("startTime");
			String engTime = json.getString("endTime");
			Integer pageNumber = json.getInt("pageNumber");
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("account", account);
			query_map.put("startTime", startTime);
			query_map.put("engTime", engTime);
			query_map.put("mobile", doctor.getMobile());
			query_map.put("pageNumber", pageNumber);
			List<BeforehandLogin> beforehandLogins = beforehandLoginService.findDoctorUnAcquire(query_map);
			
			List<Map<String,Object>> beforehandLoginList = new ArrayList<Map<String,Object>>();
			
			for(BeforehandLogin beforehandLogin : beforehandLogins){
				//判断是一次发放积分,还是二次发放积分.生成几条记录
				/**一次发放**/
				if(setting.getPointGrantType().equals(PointGrantType.once)){
					if(!beforehandLogin.isNotarizeLogin()){
						Map<String,Object> pointMap = new HashMap<String, Object>();
						pointMap.put("createDate", beforehandLogin.getCreateDate());
						pointMap.put("message", "邀请"+MobileUtil.getStarString2(beforehandLogin.getMobile(),3,4)+",待完成注册");
						pointMap.put("point", setting.getFirstDoctorInvitationMemberPoint());
						beforehandLoginList.add(pointMap);
 					}
				}
				if(setting.getPointGrantType().equals(PointGrantType.secondary)){
					if(!beforehandLogin.isNotarizeLogin()){
						Map<String,Object> pointMap = new HashMap<String, Object>();
						pointMap.put("createDate", beforehandLogin.getCreateDate());
						pointMap.put("message", "邀请"+MobileUtil.getStarString2(beforehandLogin.getMobile(),3,4)+",待完成注册");
						pointMap.put("point", setting.getFirstDoctorInvitationMemberPoint());
						beforehandLoginList.add(pointMap);
 					}
					if(!beforehandLogin.isPurchase()){
						Map<String,Object> pointMap = new HashMap<String, Object>();
						pointMap.put("createDate", beforehandLogin.getCreateDate());
						pointMap.put("message", "邀请"+MobileUtil.getStarString2(beforehandLogin.getMobile(),3,4)+",待完成5次下单");
						pointMap.put("point", setting.getSecondaryDoctorInvitationMemberPoint());
						beforehandLoginList.add(pointMap);
					}
				}
				
			}
			
			Integer pageSize = Config.pageSize;//每页条数
			String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			
			if(beforehandLoginList.size()<=0){
				
				data_map.put("pointList",point_list);
				data_map.put("point", doctor.getPoint());
				
				map.put("status", "500");
				map.put("message", "暂无健康金数据");
				map.put("data", JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map));
				return;
			}
			
			//总页数
			Integer pagecount = ((beforehandLoginList.size()+pageSize-1)/pageSize);
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			if (beforehandLoginList.size()>0){
				if(pageNumber>=pagecount){
					point_list = beforehandLoginList.subList((pagenumber-1)*pageSize, beforehandLoginList.size());
				}else{
					point_list = beforehandLoginList.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			
			
			data_map.put("pointList",point_list);
			data_map.put("point", doctor.getPoint());
			
			map.put("status", status);
			map.put("message", message);
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
 