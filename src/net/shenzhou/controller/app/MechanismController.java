/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.entity.Order;
import net.shenzhou.Config;
import net.shenzhou.Setting;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.Coupon;
import net.shenzhou.entity.Coupon.CouponType;
import net.shenzhou.entity.CouponCode;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismRank;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.PatientMechanism;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDayItem;
import net.shenzhou.service.CouponCodeService;
import net.shenzhou.service.CouponService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismRankService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.WorkDayItemService;
import net.shenzhou.service.WorkDayService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MobileUtil;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * 机构
 * @author wsr
 *
 */
@Controller("appMechanismController")
@RequestMapping("/app/mechanism")
public class MechanismController extends BaseController {

	@Value("${url_escaping_charset}")
	private String urlEscapingCharset;
	
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
	@Resource(name = "mechanismRankServiceImpl")
	private MechanismRankService mechanismRankService;
	@Resource(name = "couponServiceImpl")
	private CouponService couponService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;
	
	
	

	/**
	 * 用户端广告轮播图
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/ad_list", method = RequestMethod.GET)
	public void ad_list(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        
		try {
			Setting setting = SettingUtils.get();
			List<String> ad_list = new ArrayList<String>();
			ad_list.add(setting.getSiteUrl()+"/upload/ad/img_ad_one.png");
			ad_list.add(setting.getSiteUrl()+"/upload/ad/img_ad_two.png");
			ad_list.add(setting.getSiteUrl()+"/upload/ad/img_ad_three.png");
			Map<String,Object> data_map = new HashMap<String,Object>();
			data_map.put("ad_list", ad_list);
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data", JsonUtils.toJson(data_map));
	        
			printWriter.print(JsonUtils.toString(map)) ;
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
	 * 机构列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	
	//http://localhost:8080/shenzhou/app/mechanism/list.jhtml?file={scoreSort:"desc",second:"desc",longitude:"148.432423",latitude:"39.43432",pageNumber:"1"}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
	        map = mechanismService.findList(file);
	        System.out.println(JSONObject.fromObject(map).toString());
			printWriter.print(JsonUtils.toString(map)) ;
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
	 * 机构列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	
	//http://localhost:8080/shenzhou/app/mechanism/list.jhtml?file={scoreSort:"desc",second:"desc",longitude:"148.432423",latitude:"39.43432",pageNumber:"1"}
	@RequestMapping(value = "/mechanismList", method = RequestMethod.GET)
	public void mechanismList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
	        map = mechanismService.findMechanismList(file);
//	        System.out.println(JSONObject.fromObject(map).toString());
			printWriter.print(JsonUtils.toString(map)) ;
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
	 * 机构详情
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//http://localhost:8080/shenzhou/app/project/mechanism.jhtml?file={mechanismId:"1"}
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void view(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			JSONObject json = JSONObject.fromObject(file);
			Map<String ,Object> mechanism_map = new HashMap<String, Object>();
			System.out.println("mechanismId："+json.getLong("mechanismId"));
			Mechanism mechanism = mechanismService.find(json.getLong("mechanismId"));
			String safeKeyValue = json.getString("safeKeyValue");
			Map<String,Object> query_map = new HashMap<String, Object>();
			if(StringUtils.isEmpty(safeKeyValue)){
				List<Doctor> doctor_list = new ArrayList<Doctor>();
				for (DoctorMechanismRelation doctorMechanismRelation : mechanism.getDoctorMechanismRelation()) {
					if (doctorMechanismRelation.getAudit().equals(DoctorMechanismRelation.Audit.succeed)) {
						if(doctorMechanismRelation.getDoctor()!=null&&doctorMechanismRelation.getDoctor().getIsDeleted().equals(false)
								&&doctorMechanismRelation.getIsAbout()==true&&doctorMechanismRelation.getIsEnabled()==true ){
							doctor_list.add(doctorMechanismRelation.getDoctor());
						}
					}
				}

				List<Map<String,Object>> couponList = new ArrayList<Map<String,Object>>();
				for(Coupon coupon : mechanism.getCoupons()){
					if(coupon.getIsEnabled()&&!coupon.hasExpired()){
						Map<String,Object> couponMap = new HashMap<String, Object>();
						couponMap.put("isGet", false);
						couponMap.put("couponName", coupon.getName());
						couponMap.put("couponType", coupon.getCouponType());
						if(coupon.getCouponType().equals(CouponType.firstorder)){
							couponMap.put("minConsumptionPrice", new BigDecimal(0));
						}else{
							couponMap.put("minConsumptionPrice", coupon.getMinConsumptionPrice());
						}
						couponMap.put("reductionPrice", coupon.getReductionPrice());
						couponMap.put("couponId", coupon.getId());
						couponList.add(couponMap);
					}
				}
				
				//用户是否关注机构
				mechanism_map.put("collect", false);
				mechanism_map.put("mechanism_map", couponList);
				mechanism_map.put("mechanism",mechanism);
				mechanism_map.put("doctors",doctor_list);
				mechanism_map.put("serverProjectCategorys",serverProjectCategoryService.getServerProjectCategory(mechanism));
				
		        map.put("status", "200");
		        map.put("message", "数据加载成功");
		        map.put("data", JsonUtils.toJson(mechanism_map));
				printWriter.write(JsonUtils.toString(map));
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
	        
			
//			List<Doctor> doctor_list = doctorService.findByMechanism(mechanism);
			List<Doctor> doctor_list = new ArrayList<Doctor>();
			for (DoctorMechanismRelation doctorMechanismRelation : mechanism.getDoctorMechanismRelation()) {
				if (doctorMechanismRelation.getAudit().equals(DoctorMechanismRelation.Audit.succeed)) {
					if(doctorMechanismRelation.getDoctor()!=null&&doctorMechanismRelation.getDoctor().getIsDeleted().equals(false)&&doctorMechanismRelation.getIsAbout()==true){
						doctor_list.add(doctorMechanismRelation.getDoctor());
					}
				}
			}
			
			List<Map<String,Object>> couponList = new ArrayList<Map<String,Object>>();
			
			Set<Coupon> member_coupons = new HashSet<Coupon>();//获取用户领过的优惠券
			for (CouponCode	couponCode : member.getCouponCodes()) {
				member_coupons.add(couponCode.getCoupon());
			}
			
			Set<Coupon> mechanism_coupons = new HashSet<Coupon>();//获取该机构可领用
			for (Coupon coupon : mechanism.getCoupons()) {
				if (coupon.getIsEnabled()&&!coupon.hasExpired()) {
					mechanism_coupons.add(coupon);
				}
			}
			
			for(Coupon coupon : mechanism_coupons){
				Map<String,Object> couponMap = new HashMap<String, Object>();
				couponMap.put("isGet", member_coupons.contains(coupon));
				couponMap.put("couponName", coupon.getName());
				couponMap.put("couponType", coupon.getCouponType());
				if(coupon.getCouponType().equals(CouponType.firstorder)){
					couponMap.put("minConsumptionPrice", new BigDecimal(0));
				}else{
					couponMap.put("minConsumptionPrice", coupon.getMinConsumptionPrice());
				}
				couponMap.put("reductionPrice", coupon.getReductionPrice());
				couponMap.put("couponId", coupon.getId());
				couponList.add(couponMap);
			}
			
		
			//用户是否关注机构
			Set<Mechanism> member_mechanisms =  member.getMemberMechanisms();
			if(member_mechanisms==null||member_mechanisms.size()<=0){
				mechanism_map.put("collect", false);
			}else{
				mechanism_map.put("collect", member_mechanisms.contains(mechanism)?true:false);
			}
			mechanism_map.put("mechanism_map", couponList);
			mechanism_map.put("mechanism",mechanism);
			mechanism_map.put("doctors",doctor_list);
			mechanism_map.put("serverProjectCategorys",serverProjectCategoryService.getServerProjectCategory(mechanism));
			
	        map.put("status", "200");
	        map.put("message", "数据加载成功");
	        map.put("data", JsonUtils.toJson(mechanism_map));
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
	 * 机构下所有患者列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//http://localhost:8080/shenzhou/app/mechanism/patientList.jhtml?file={mechanismId:"1"}
	@RequestMapping(value = "/patientList", method = RequestMethod.GET)
	public void patientList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			List<Map<String,Object>> patientMember_list = new ArrayList<Map<String,Object>>();
			
			if(json.containsKey("mechanismId")){
				Mechanism mechanism = mechanismService.find(json.getLong("mechanismId"));
			/*	Set<Member> set = mechanism.getPatientMembers();
				for(Member member : set){
					Map<String,Object> patient_map = new HashMap<String,Object>();
					patient_map.put("patientMemberId", member.getId());
					if(member.getParent()!=null){
						patient_map.put("memberId", member.getParent().getId());
						patient_map.put("memberMobile", MobileUtil.getStarString2(member.getParent().getMobile(),3,4));
						patient_map.put("memberName", member.getParent().getName());
					}else{
						patient_map.put("memberId", "");
						patient_map.put("memberMobile","");
						patient_map.put("memberName", "");
					}
					patient_map.put("patientMemberName", member.getName());
					patient_map.put("year", DateUtil.getAge(member.getBirth()));
					patient_map.put("logo", member.getLogo());
					patient_map.put("gender", member.getGender());
					patientMember_list.add(patient_map);
				}*/
				
				// add wsr 2018-3-24 16:56:28 start
				for(PatientMechanism patientMechanism : mechanism.getPatientMechanisms()){
					Map<String,Object> patient_map = new HashMap<String,Object>();
					Member member = patientMechanism.getPatient();
					patient_map.put("patientMemberId", member.getId());
					patient_map.put("memberId", member.getParent().getId());
					patient_map.put("memberMobile", MobileUtil.getStarString2(member.getParent().getMobile(),3,4));
					patient_map.put("memberName", member.getParent().getName());
					patient_map.put("patientMemberName", member.getName());
					patient_map.put("year", DateUtil.getAge(member.getBirth()));
					patient_map.put("logo", member.getLogo());
					patient_map.put("gender", member.getGender());
					patientMember_list.add(patient_map);
				}
				// add wsr 2018-3-24 16:56:28 end
				
				map.put("status", "200");
		        map.put("data", JsonUtils.toJson(patientMember_list));
		        map.put("message", "数据加载成功");
				printWriter.write(JsonUtils.toString(map));
				return;
				
			}
			
			
//			List<Mechanism> mechanisms = new ArrayList<Mechanism>();
//			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
//			if(doctorMechanismRelation==null){
//				map.put("status", "400");
//				map.put("message", "请选择默认机构");
//				map.put("data", "{}");
//				printWriter.write(JsonUtils.toString(map)) ;
//				return;
//			}
//			mechanisms.add(doctorMechanismRelation.getMechanism());
//			for(Mechanism mechanism : mechanisms){
//				Set<Member> set = mechanism.getPatientMembers();
//				for(Member member : set){
//					Map<String,Object> patient_map = new HashMap<String,Object>();
//					patient_map.put("patientMemberId", member.getId());
//					if(member.getParent()!=null){
//						patient_map.put("memberId", member.getParent().getId());
//						patient_map.put("memberMobile", MobileUtil.getStarString2(member.getParent().getMobile(),3,4));
//						patient_map.put("memberName", member.getParent().getName());
//					}else{
//						patient_map.put("memberId", "");
//						patient_map.put("memberMobile","");
//						patient_map.put("memberName", "");
//					}
//					patient_map.put("patientMemberName", member.getName());
//					patient_map.put("year", DateUtil.getAge(member.getBirth()));
//					patient_map.put("logo", member.getLogo());
//					patient_map.put("gender", member.getGender());
//					patientMember_list.add(patient_map);
//				}
//			}
			
			//add wsr 2018-3-24 17:02:58 start
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Mechanism mechanism = doctorMechanismRelation.getMechanism();
			for(PatientMechanism patientMechanism : mechanism.getPatientMechanisms()){
				Member member = patientMechanism.getPatient();
				Map<String,Object> patient_map = new HashMap<String,Object>();
				patient_map.put("patientMemberId", member.getId());
				patient_map.put("memberId", member.getParent().getId());
				patient_map.put("memberMobile", MobileUtil.getStarString2(member.getParent().getMobile(),3,4));
				patient_map.put("memberName", member.getParent().getName());
				patient_map.put("patientMemberName", member.getName());
				patient_map.put("year", DateUtil.getAge(member.getBirth()));
				patient_map.put("logo", member.getLogo());
				patient_map.put("gender", member.getGender());
				patientMember_list.add(patient_map);
			}
			//add wsr 2018-3-24 17:02:58 end
			
			
			
	        map.put("status", "200");
	        map.put("data", JsonUtils.toJson(patientMember_list));
	        map.put("message", "数据加载成功");
			printWriter.write(JsonUtils.toString(map));
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", "患者资料缺少必要数据");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}

	
	/**
	 * 根据名称搜索机构
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/searchByName", method = RequestMethod.GET)
	public void searchByName(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			String mechanismName = json.getString("mechanismName");
			String longitude = json.getString("longitude");// 经度    	
	    	String latitude = json.getString("latitude");//纬度 
	    	String distanceSort = json.getString("distanceSort");//距离排序
			List<Mechanism> mechanisms = mechanismService.searchByName(mechanismName);
			
			if(mechanisms.size()<=0){
				map.put("status", "500");
				map.put("message","暂无机构数据 ");
				map.put("data", "{}");
		        printWriter.write(JsonUtils.toString(map));
				return;
			}
			/*if( distanceSort!=null&&!distanceSort.equals("")) {//距离排序
			    if((longitude!=null&&!longitude.equals(""))&&(latitude!=null&&!latitude.equals(""))) {// 经度    	//纬度 
			    	Map <String,Object> doctor_map = new HashMap<String, Object>();
			    	doctor_map.put("longitude", longitude);
			    	doctor_map.put("latitude", latitude);
			    	doctor_map.put("mechanisms", mechanisms);
			    	doctor_map.put("isDesc", distanceSort.equals("desc"));//false 升序  true 降序
			    	mechanisms = MechanismUtil.getOrderByDistance(doctor_map);
				   }
			   }
			*/
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("mechanisms", mechanisms);
			map.put("status", "200");
			map.put("message","搜索成功 ");
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
	 * 筛选机构
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/mechanismFiltrate", method = RequestMethod.GET)
	public void mechanismFiltrate(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			
			map = mechanismService.mechanismFiltrate(file);
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
	 * 机构排班情况
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/mechanismWorkMenu", method = RequestMethod.GET)
	public void mechanismWorkMenu(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			Mechanism mechanism = mechanismService.find(json.getLong("mechanismId"));
			
			List<DoctorMechanismRelation> doctorMechanismRelation_list = mechanism.getDoctorMechanismRelations(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed);
			List<Doctor> doctor_list = new ArrayList<Doctor>();
			
			for(DoctorMechanismRelation doctorMechanismRelation : doctorMechanismRelation_list){
				doctor_list.add(doctorMechanismRelation.getDoctor());
			}
			
			List<Map<String,Object>> doctor_data = new ArrayList<Map<String,Object>>();
			for(Doctor doctor : doctor_list){
				Map<String,Object> doctor_map = new HashMap<String, Object>();
				doctor_map.put("doctorName", doctor.getName());
				doctor_map.put("doctorId", doctor.getId());
				doctor_data.add(doctor_map);
			}
			
			List<String> string_list = new ArrayList<String>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//获取当天往后七天的时间
			for(int x = 0;x<7;x++){
				string_list.add(DateUtil.dataAdd(new Date(),x));
			}
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("doctorList", doctor_data);
			data_map.put("dateList", string_list);
			
			map.put("status", "200");
			map.put("message", "加载成功");
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
	 * 获取医生每天排班情况
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/mechanismWorkDetail", method = RequestMethod.GET)
	public void mechanismWorkDetail(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		file = StringEscapeUtils.unescapeHtml(file);
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			JSONObject json = JSONObject.fromObject(file);
			Mechanism mechanism = mechanismService.find(json.getLong("mechanismId"));
			String date = json.getString("date");
			Doctor doctor = doctorService.find(json.getLong("doctorId"));
			WorkDay workDay = workDayService.getWorkDayByDoctorAndData(doctor, DateUtil.getStringtoDate(date, "yyyy-MM-dd"));
			
			if(null==workDay){
				map.put("status", "400");
				map.put("message", "该医生今天没排班信息");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			
			List<WorkDayItem> workDayItem_list = workDayItemService.getDoctorOrderTime(doctor, workDay);
			List<WorkDayItem> workDayItem_lists = new ArrayList<WorkDayItem>();
			for(WorkDayItem workDayItem : workDayItem_list){
				if(workDayItem.getMechanism()==mechanism){
					workDayItem_lists.add(workDayItem);
				}
			}
			
			if(workDayItem_lists.size()<=0){
				map.put("status", "400");
				map.put("message", "该医生今天没排班信息");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			
			List<Map<String,Object>> am_workDayItem_list = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> pm_workDayItem_list = new ArrayList<Map<String,Object>>();
			
			for(WorkDayItem workDayItem : workDayItem_lists){
				/**上午**/
				if(DateUtil.compare_date(workDayItem.getStartTime(), "12:00")==1){
					Map<String,Object> data_map = new HashMap<String, Object>();
					data_map.put("startTime", workDayItem.getStartTime());
					data_map.put("endTime", workDayItem.getEndTime());
					data_map.put("patientName", workDayItem.getOrder().getPatientMember().getName());
					data_map.put("projectName", workDayItem.getOrder().getProject().getName());
					am_workDayItem_list.add(data_map);
				}else{
					Map<String,Object> data_map = new HashMap<String, Object>();
					data_map.put("startTime", workDayItem.getStartTime());
					data_map.put("endTime", workDayItem.getEndTime());
					data_map.put("patientName", workDayItem.getOrder().getPatientMember().getName());
					data_map.put("projectName", workDayItem.getOrder().getProject().getName());
					pm_workDayItem_list.add(data_map);
				}
			}
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("amWorkDayItemList", am_workDayItem_list);
			data_map.put("pmWorkDayItemList", pm_workDayItem_list);
			
			map.put("status", "200");
			map.put("message", "加载成功");
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
	 * 机构下所有患者列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/mechanismPatientList", method = RequestMethod.GET)
	public void mechanismPatientList(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			
			Mechanism mechanism = mechanismService.find(json.getLong("mechanismId"));
			List<Order> orderList = new ArrayList<Order>();
			orderList = orderService.doctorMechanismOrder(doctor, mechanism);
			List<Member> memberList = new ArrayList<Member>();
			for(Order order : orderList){
				if(!memberList.contains(order.getPatientMember())){
					memberList.add(order.getPatientMember());
				}
			}
			
			List<Map<String,Object>> patientMember_list = new ArrayList<Map<String,Object>>();
				/*Set<Member> set = mechanism.getPatientMembers();
				for(Member member : set){*/
				for(Member member : memberList){
					System.out.println(member.getId());
					Map<String,Object> patient_map = new HashMap<String,Object>();
					patient_map.put("patientMemberId", member.getId());
					if(member.getParent()!=null){
						patient_map.put("memberId", member.getParent().getId());
						patient_map.put("memberMobile", MobileUtil.getStarString2(member.getParent().getMobile(),3,4));
						patient_map.put("memberName", member.getParent().getName());
					}else{
						patient_map.put("memberId", "");
						patient_map.put("memberMobile","");
						patient_map.put("memberName", "");
					}
					Order order = orderService.getPatientMemberOldOrder(member);
					Order projectOrder = orderService.getPatientMemberRecentlyRecoveryOrder(member);
					Order assOrder = orderService.getPatientMemberRecentlyOrder(member);
					
					patient_map.put("firstTime", order==null?0:order.getCreateDate());//首次就诊时间
					patient_map.put("projectName",projectOrder==null?"": projectOrder.getProject().getServerProjectCategory().getName());//康复项目
					if(assOrder!=null){
						AssessReport assessReport = assOrder.getAssessReport();
						patient_map.put("assessResult",assessReport==null?"":assessReport.getAssessResult());//病情描述
					}else{
						patient_map.put("assessResult","");//病情描述
					}
					patient_map.put("patientMemberName", member.getName());
					patient_map.put("year", DateUtil.getAge(member.getBirth()));
					patient_map.put("logo", member.getLogo());
					patient_map.put("gender", member.getGender());
					patientMember_list.add(patient_map);
				}
			
			
	        map.put("status", "200");
	        map.put("data", JsonUtils.toJson(patientMember_list));
	        map.put("message", "数据加载成功");
			printWriter.write(JsonUtils.toString(map));
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", "患者资料缺少必要数据");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
	}

	/**
	 * 用户关注(取消关注)机构
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/attentionMechanism", method = RequestMethod.GET)
	public void attentionMechanism(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
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
			Mechanism mechanism = mechanismService.find(json.getLong("mechanismId"));
			
			Set<Mechanism> mechanism_list = member.getMemberMechanisms();
			
			if(mechanism_list.contains(mechanism)){
				//doctor.getMembers().remove(member);
				member.getMemberMechanisms().remove(mechanism);
				//doctorService.update(doctor);
				memberService.update(member);
				
				map.put("status", "200");
				map.put("message", "取消成功");
				map.put("data",JsonUtils.toJson(data_map));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			/*doctor.getMembers().add(member);
			doctorService.update(doctor);*/
			mechanism_list.add(mechanism);
			memberService.update(member);
			
			map.put("status", "200");
			map.put("message", "收藏成功");
			map.put("data",JsonUtils.toJson(data_map));
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
	 * 机构列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/lists", method = RequestMethod.GET)
	public void lists(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		List<Mechanism> list = new ArrayList<Mechanism>();
		PrintWriter printWriter = response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
		try {
			list = mechanismService.find(file);
	        System.out.println(JSONObject.fromObject(map).toString());
	        map.put("list", list);
	        map.put("data", JsonUtils.toJson(map));
			printWriter.print(JsonUtils.toString(map)) ;
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
	 * 机构列表(最新的)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	
	//http://localhost:8080/shenzhou/app/mechanism/list.jhtml?file={scoreSort:"desc",second:"desc",longitude:"148.432423",latitude:"39.43432",pageNumber:"1"}
	@RequestMapping(value = "/mechanismLists", method = RequestMethod.GET)
	public void mechanismLists(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String ,Object> mechanism_lists = new HashMap<String, Object>();
		List<Mechanism>  mechanismList= null;
		List<Map<String ,Object>> list_map =new ArrayList<Map<String,Object>>();
		Map<String ,Object> map1 = new HashMap<String, Object>();
		Map<String ,Object> maps = new HashMap<String, Object>();
		Map<String ,Object> mapList = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        List<MechanismRank> mechanismRank = new ArrayList<MechanismRank>();
		try {
			mechanism_lists = mechanismService.findMechanismLists(file);
			mechanismList = (List<Mechanism>) mechanism_lists.get("mechanisms");
			Integer pageSize = Config.pageSize.intValue();//每页条数
	        JSONObject json = JSONObject.fromObject(file);
	        Integer pageNumber = json.getInt("pageNumber");
	        String status = "200";
			String message = "第"+pageNumber+"页数据加载成功";
			
			if(mechanismList == null){
				mechanismList = new ArrayList<Mechanism>();
			}
			if(mechanismList.size()<=0){
				maps.put("status", "500");
				maps.put("message","暂无机构数据");
				maps.put("data", "{}");
		        printWriter.write(JsonUtils.toString(maps));
				return;
			}
			//总页数
			Integer pagecount = ((mechanismList.size()+pageSize-1)/pageSize);
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			List<Mechanism> mechanism_list = new ArrayList<Mechanism>();
			if (mechanismList.size()>0){
				if(pageNumber>=pagecount){
					mechanism_list = mechanismList.subList((pagenumber-1)*pageSize, mechanismList.size());
				}else{
					mechanism_list = mechanismList.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			mechanismRank = mechanismRankService.findChildren(null);
	        for(Mechanism ms : mechanism_list){
	        	Map<String ,Object> map = new HashMap<String, Object>();
	        	map.put("id", ms.getId());
	        	map.put("name", ms.getName());
	        	map.put("mechanismRankName", ms.getMechanismRank().getName().trim());
	        	map.put("introduce", ms.getIntroduce());
	        	map.put("phone", ms.getPhone());
	        	map.put("logo", ms.getLogo());
	        	map.put("mobile", ms.getMobile());
	        	map.put("scoreSort", ms.getScoreSort());
	        	map.put("second", ms.getSecond());
	        	map.put("address", ms.getAddress());
	        	map.put("longitude", ms.getLongitude());
	        	map.put("latitude", ms.getLatitude());
	        	map.put("mechanismCategoryName", ms.getMechanismCategory().getName());
	        	list_map.add(map);
	        }
	        List<Map<String ,Object>> rank_map = new ArrayList<Map<String,Object>>();
	        for(MechanismRank mr : mechanismRank){
	        	Map<String ,Object> map = new HashMap<String, Object>();
	        	map.put("rankid", mr.getId());
	        	map.put("rankname", mr.getName());
	        	rank_map.add(map);
	        }
	        map1.put("rankList", rank_map);
	        map1.put("mechanismList", list_map);
	        mapList.put("status", status);
	        mapList.put("message", message);
	        mapList.put("data", JsonUtils.toJson(map1));
	        printWriter.write(JsonUtils.toString(mapList));
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			mapList.put("status", "400");
			mapList.put("message", e.getMessage());
			mapList.put("data", new Object());
			printWriter.write(JSONObject.fromObject(mapList).toString()) ;
			return;
		}
	}
	
	/**
	 * 机构筛选
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/mechanismScreen", method = RequestMethod.GET)
	public void mechanismScreen(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Mechanism>  mechanismList= new ArrayList<Mechanism>();
		List<Map<String ,Object>> list_map =new ArrayList<Map<String,Object>>();
		Map<String ,Object> map1 = new HashMap<String, Object>();
		Map<String ,Object> mapList = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        List<MechanismRank> mechanismRank = new ArrayList<MechanismRank>();
		try {
			mechanismList = mechanismService.screenMechanismLists(file);
			JSONObject json = JSONObject.fromObject(file);
			List<Mechanism>  mechanism_List= new ArrayList<Mechanism>();
			String rankid = json.getString("rankid");
			if(rankid != null && !rankid.equals("")){
				String rank[] = rankid.split(",");
				for(String rid : rank){
					Long id = Long.parseLong(rid);
					for(Mechanism msm : mechanismList){
						if(id == msm.getMechanismRank().getId()){
							mechanism_List.add(msm);
						}
					}
				}
			}else{
				mechanism_List = mechanismList;
			}
			
			  Integer pageSize = Config.pageSize.intValue();//每页条数
			 //Integer pageSize = 20;//每页条数
		     Integer pageNumber = json.getInt("pageNumber");
		     String status = "200";
		     String message = "第"+pageNumber+"页数据加载成功";
			
				if(mechanism_List.size() <= 0){
					
					mapList.put("status", "500");
					mapList.put("message","暂无机构数据");
					mapList.put("data", "{}");
			        printWriter.write(JsonUtils.toString(mapList));
					return;
				}
		     
			//总页数
			Integer pagecount = ((mechanism_List.size()+pageSize-1)/pageSize);
					
			//页数
			Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
			List<Mechanism> Mechanism_list = new ArrayList<Mechanism>();
			if (mechanism_List.size()>0){
				if(pageNumber>=pagecount){
					Mechanism_list = mechanism_List.subList((pagenumber-1)*pageSize, mechanism_List.size());
				}else{
					Mechanism_list = mechanism_List.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (pageNumber>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
	        for(Mechanism ms : Mechanism_list){
	        	Map<String ,Object> map = new HashMap<String, Object>();
	        	map.put("name", ms.getName());
	        	System.out.println(ms.getMechanismRank().getName());
	        	map.put("mechanismRankName", ms.getMechanismRank().getName());
	        	map.put("introduce", ms.getIntroduce());
	        	map.put("phone", ms.getPhone());
	        	map.put("logo", ms.getLogo());
	        	map.put("mobile", ms.getMobile());
	        	map.put("scoreSort", ms.getScoreSort());
	        	map.put("second", ms.getSecond());
	        	map.put("address", ms.getAddress());
	        	map.put("longitude", ms.getLongitude());
	        	map.put("latitude", ms.getLatitude());
	        	map.put("mechanismCategoryName", ms.getMechanismCategory().getName());
	        	list_map.add(map);
	        }
	        List<Map<String ,Object>> rank_map = new ArrayList<Map<String,Object>>();
	        for(MechanismRank mr : mechanismRank){
	        	Map<String ,Object> map = new HashMap<String, Object>();
	        	map.put("rankid", mr.getId());
	        	map.put("rankname", mr.getName());
	        	rank_map.add(map);
	        }
	        
	     
	        
	        map1.put("mechanismList", list_map);
	        mapList.put("status", status);
	        mapList.put("message", message);
	        mapList.put("data", JsonUtils.toJson(map1));
	        printWriter.write(JsonUtils.toString(mapList));
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			mapList.put("status", "400");
			mapList.put("message", e.getMessage());
			mapList.put("data", new Object());
			printWriter.write(JSONObject.fromObject(mapList).toString()) ;
			return;
		}
	}
	
	
	
}