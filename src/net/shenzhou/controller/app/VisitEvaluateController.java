/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Visit;
import net.shenzhou.entity.Visit.VisitType;
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
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.OrganizationService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.RSAService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.service.VisitService;
import net.shenzhou.service.WithdrawDepositService;
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
 * Controller - 回访,评价
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appVisitEvaluateController")
@RequestMapping("/app/visitEvaluate")
public class VisitEvaluateController extends BaseController {
	

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
	@Resource(name = "visitServiceImpl")
	private VisitService visitService;
	
	/**
	 * 患者回访列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/memberVisitList", method = RequestMethod.GET)
	public void memberVisitList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
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
			
			
			Member patientMember = memberService.find(json.getLong("patientMemberId"));
			Integer pageSize = Config.pageSize;
			Integer pageNumber = json.getInt("pageNumber");//页码
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Mechanism mechanism = doctorMechanismRelation.getMechanism();
			String startDate = json.getString("startDate");
			String endDate = json.getString("endDate");
			//如果为空,是首次进入,获取当前月份第一天到当前月份的时间段搜索数据
			if(StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)){
				Map<String,Object> data_map = DateUtil.getStartDateAndEndDate(new Date());
				Date startTime = (Date) data_map.get("startDate");
				startDate = DateUtil.getDatetoString("yyyy-MM-dd", startTime);
				endDate = DateUtil.getDatetoString("yyyy-MM-dd", new Date());
			}
			
			Date start_data = DateUtil.getStringtoDate(startDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
			Date end_data = DateUtil.getStringtoDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
			Long id = mechanism.getId();
			List<Visit> visits = visitService.getByMechanism(mechanism, patientMember,start_data,end_data);

			String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			
			if(visits.size()==0){
				map.put("status", "400");
				map.put("message", "暂无回访数据");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			//总页数
			Integer pagecount = (visits.size()+pageSize-1)/pageSize;
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			
			if (pageNumber>pagecount) {
				message = "无更多数据";
				status = "500";
			}
			
			List<Visit> visit_order = new ArrayList<Visit>();
			if (visits.size()>0){
				if(pageNumber>=pagecount){
					visit_order = visits.subList((pagenumber-1)*pageSize, visits.size());
				}else{
					visit_order = visits.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
			for(Visit visit : visit_order){
				Map<String,Object> visit_map = new HashMap<String, Object>();
				visit_map.put("visitId", visit.getId());
				visit_map.put("visitDate", visit.getVisitDate());
				visit_map.put("doctorName", visit.getDoctor().getName());
				visit_map.put("visitType", visit.getVisitType());
				visit_map.put("content", visit.getContent());
				visit_map.put("result", visit.getResult());
				data_list.add(visit_map);
			}
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("visits", data_list);
			data_map.put("startTime", start_data);
			data_map.put("endTime", end_data);
			
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
	 * 添加回访记录
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/addVisit", method = RequestMethod.GET)
	public void addVisit(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
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
			
			Member patientMember = memberService.find(json.getLong("patientMemberId"));
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Mechanism mechanism = doctorMechanismRelation.getMechanism();
			Date date = DateUtil.getStringtoDate(json.getString("date"), "yyyy-MM-dd");
			VisitType visitType = VisitType.valueOf(json.getString("visitType"));
			String content = json.getString("content");
			String result = json.getString("result");
			
			Visit visit = new Visit();
			visit.setMember(patientMember);
			visit.setDoctor(doctor);
			visit.setMechanism(mechanism);
			visit.setVisitDate(date);
			visit.setVisitType(visitType);
			visit.setContent(content);
			visit.setResult(result);
			visit.setIsDeleted(false);
			
			visitService.save(visit);
			
			map.put("status", "200");
			map.put("message", "添加回访记录成功");
			map.put("data", "{}");
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
 