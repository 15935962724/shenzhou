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
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.WorkTarget;
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
import net.shenzhou.service.WithdrawDepositService;
import net.shenzhou.service.WorkDayItemService;
import net.shenzhou.service.WorkDayService;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - ??????????????????
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appDoctorMechanismController")
@RequestMapping("/app/doctorMechanism")
public class DoctorMechanismController extends BaseController {
	

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
	
	
	/**
	 * ?????????????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doctorMechanismList", method = RequestMethod.GET)
	public void doctorMechanismList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			List<DoctorMechanismRelation> list = doctor.getDoctorMechanismRelations();
			List<Map<String,Object>> succeed_list = new ArrayList<Map<String,Object>>();
			for(DoctorMechanismRelation doctorMechanismRelation : list){
				if(doctorMechanismRelation.getIsDeleted()!=true&&doctorMechanismRelation.getAudit().equals(Audit.succeed)){
					Map<String,Object> mechanism_map = new HashMap<String, Object>();
					Mechanism mechanism = doctorMechanismRelation.getMechanism();
					mechanism_map.put("doctorMechanismRelationId", doctorMechanismRelation.getId());
					mechanism_map.put("mechanismId", mechanism.getId());
					mechanism_map.put("mechanismLogo", mechanism.getLogo());
					mechanism_map.put("mechanismName", mechanism.getName());
					mechanism_map.put("address", mechanism.getAddress());
					mechanism_map.put("phone", mechanism.getPhone());
					mechanism_map.put("audit", doctorMechanismRelation.getAudit());
					mechanism_map.put("isDefault", doctorMechanismRelation.getDefaultMechanism());
					mechanism_map.put("statusExplain", doctorMechanismRelation.getStatusExplain());
					succeed_list.add(mechanism_map);
				}
			}
			
			List<Map<String,Object>> pend_list = new ArrayList<Map<String,Object>>();
			for(DoctorMechanismRelation doctorMechanismRelation : list){
				if(doctorMechanismRelation.getIsDeleted()!=true&&!doctorMechanismRelation.getAudit().equals(Audit.succeed)){
					Map<String,Object> mechanism_map = new HashMap<String, Object>();
					Mechanism mechanism = doctorMechanismRelation.getMechanism();
					mechanism_map.put("doctorMechanismRelationId", doctorMechanismRelation.getId());
					mechanism_map.put("mechanismId", mechanism.getId());
					mechanism_map.put("mechanismLogo", mechanism.getLogo());
					mechanism_map.put("mechanismName", mechanism.getName());
					mechanism_map.put("address", mechanism.getAddress());
					mechanism_map.put("phone", mechanism.getPhone());
					mechanism_map.put("audit", doctorMechanismRelation.getAudit());
					mechanism_map.put("isDefault", doctorMechanismRelation.getDefaultMechanism());
					mechanism_map.put("statusExplain", doctorMechanismRelation.getStatusExplain());
					pend_list.add(mechanism_map);
				}
			}
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("succeedList", succeed_list);
			data_map.put("pendList", pend_list);
			
			map.put("status", "200");
			map.put("message", "??????????????????");
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
	 * ????????????????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/mechanismList", method = RequestMethod.GET)
	public void mechanismList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			String safeKeyValue = json.getString("safeKeyValue");
			Integer pageNumber = json.getInt("pageNumber");//??????
		    Integer pageSize = Config.pageSize;
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			List<Mechanism> mechanisms = mechanismService.findAll();
			
			String status = "200";
			String message = "???"+pageNumber+"?????????????????????";
			
			if(mechanisms.size()==0){
				map.put("status", "400");
				map.put("message", "??????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			
			//?????????
			Integer pagecount = (mechanisms.size()+pageSize-1)/pageSize;
					
			//??????
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			
			if (pageNumber>pagecount) {
				message = "???????????????";
				status = "500";
			}
			
			List<Mechanism> pag_order = new ArrayList<Mechanism>();
			if (mechanisms.size()>0){
				if(pageNumber>=pagecount){
					pag_order = mechanisms.subList((pagenumber-1)*pageSize, mechanisms.size());
				}else{
					pag_order = mechanisms.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "???????????????";
				}
			}
			
			List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
			for(Mechanism mechanism : pag_order){
				Map<String,Object> mechanism_map = new HashMap<String, Object>();
				mechanism_map.put("mechanismId", mechanism.getId());
				mechanism_map.put("mechanismLogo", mechanism.getLogo());
				mechanism_map.put("mechanismName", mechanism.getName());
				mechanism_map.put("address", mechanism.getAddress());
				mechanism_map.put("phone", mechanism.getPhone());
				data_list.add(mechanism_map);
			}
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("mechanismList", data_list);
			
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
	 * ??????????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/toApplyMechanism", method = RequestMethod.GET)
	public void toApplyMechanism(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			Mechanism mechanism = mechanismService.find(json.getLong("mechanismId"));
			DoctorMechanismRelation doctorMechanismRelation = doctorMechanismRelationService.getByDoctorMechanism(doctor, mechanism);
			
			/**?????????????????????,?????????????????????????????????**/
			if(null==doctorMechanismRelation){
				DoctorMechanismRelation relation = new DoctorMechanismRelation();
				relation.setAudit(Audit.pending);
				relation.setDoctor(doctor);
				relation.setMechanism(mechanism);
				relation.setIsEnabled(true);
				relation.setIsSystem(false);
				relation.setDefaultMechanism(false);
				relation.setIsDeleted(false);
				relation.setStatusExplain("");
				relation.setIsAbout(false);
				relation.setIsEnabledExplain("");
				BigDecimal BigDecimal = new BigDecimal(0);
				WorkTarget workTarget = new WorkTarget();
				workTarget.setDayWorkTarget(BigDecimal);
				workTarget.setPercentage(BigDecimal);
				relation.setWorkTarget(workTarget);
				doctorMechanismRelationService.save(relation);
				
				map.put("status", "200");
				map.put("message", "????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			if(doctorMechanismRelation.getAudit().equals(Audit.succeed)){
				map.put("status", "400");
				map.put("message", "?????????????????????????????????,???????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			if(doctorMechanismRelation.getAudit().equals(Audit.fail)){
				doctorMechanismRelation.setAudit(Audit.pending);;
				doctorMechanismRelationService.update(doctorMechanismRelation);
			}
			
			if(doctorMechanismRelation.getAudit().equals(Audit.cancel)){
				doctorMechanismRelation.setAudit(Audit.pending);;
				doctorMechanismRelationService.update(doctorMechanismRelation);
			}
			
			
			map.put("status", "200");
			map.put("message", "????????????");
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
	
	/**
	 * ????????????????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/searchMechanism", method = RequestMethod.GET)
	public void searchMechanism(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			String mechanismName = json.getString("mechanismName");
			Map<String,Object> search_map = new HashMap<String, Object>();
			search_map.put("name", mechanismName);
			List<Mechanism> mechanismList = mechanismService.findList(search_map);

			List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
			for(Mechanism mechanism : mechanismList){
				Map<String,Object> mechanism_map = new HashMap<String, Object>();
				mechanism_map.put("mechanismId", mechanism.getId());
				mechanism_map.put("mechanismLogo", mechanism.getLogo());
				mechanism_map.put("mechanismName", mechanism.getName());
				mechanism_map.put("address", mechanism.getAddress());
				mechanism_map.put("phone", mechanism.getPhone());
				data_list.add(mechanism_map);
			}
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("mechanismList", data_list);
			
			map.put("status", "200");
			map.put("message", "????????????");
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
	 * ????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/cancelApplyMechanism", method = RequestMethod.GET)
	public void cancelApplyMechanism(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			DoctorMechanismRelation doctorMechanismRelation = doctorMechanismRelationService.find(json.getLong("doctorMechanismRelationId"));
			if(doctorMechanismRelation.getAudit().equals(Audit.succeed)){
				map.put("status", "400");
				map.put("message", "?????????????????????????????????,????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			 doctorMechanismRelationService.delete(doctorMechanismRelation);
			
			map.put("status", "200");
			map.put("message", "??????????????????");
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
	
	
	/**
	 * ??????????????????
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/setDefaultMechanism", method = RequestMethod.GET)
	public void setDefaultMechanism(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			DoctorMechanismRelation doctorMechanismRelation = doctorMechanismRelationService.find(json.getLong("doctorMechanismRelationId"));
			DoctorMechanismRelation defaultMechanism = doctor.getDefaultDoctorMechanismRelation();
			if(defaultMechanism!=null){
				defaultMechanism.setDefaultMechanism(false);
				doctorMechanismRelationService.update(defaultMechanism);
			}
			doctorMechanismRelation.setDefaultMechanism(true);
			doctorMechanismRelationService.update(doctorMechanismRelation);
			
			map.put("status", "200");
			map.put("message", "????????????????????????");
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
 