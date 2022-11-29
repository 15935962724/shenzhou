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
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.GroupPatient;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PatientGroup;
import net.shenzhou.entity.PatientMechanism;
import net.shenzhou.service.BeforehandLoginService;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.CartService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.GroupPatientService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.PatientGroupService;
import net.shenzhou.service.RSAService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MobileUtil;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 医生患者
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appPatientMemberController")
@RequestMapping("/app/patientMember")
public class PatientMemberController extends BaseController {

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
	@Resource(name = "beforehandLoginServiceImpl")
	private BeforehandLoginService beforehandLoginService;
	@Resource(name = "patientGroupServiceImpl")
	private PatientGroupService patientGroupService;
	@Resource(name = "groupPatientServiceImpl")
	private GroupPatientService groupPatientService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;

	/**
	 * 医生患者分类列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/patientGroupList", method = RequestMethod.GET)
	public void patientGroupList(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
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
			
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			List<PatientGroup> patientGroups = doctorMechanismRelation.getPatientGroups();
			Mechanism mechanism = doctorMechanismRelation.getMechanism();
			
//			Set<Member> member_mechanism = mechanism.getPatientMembers();
//			List<Member> member_doctor = doctor.getParents();
//			
//			List<Member> patientMembers = new ArrayList<Member>();
//			
//			for(Member memberMechanism: member_mechanism){
//				for(Member memberDoctor:member_doctor){
//					if(memberMechanism.equals(memberDoctor)){
//						patientMembers.add(memberMechanism);
//					}
//				}
//			}
			
			//add wsr 2018-3-24 17:05:52 start
			List<Member> member_doctor = doctor.getParents();
			
			List<Member> patientMembers = new ArrayList<Member>();
			
			for(PatientMechanism patientMechanism: mechanism.getPatientMechanisms()){
				for(Member memberDoctor:member_doctor){
					if(patientMechanism.getPatient().equals(memberDoctor)&&!memberDoctor.getIsDeleted()){
						patientMembers.add(patientMechanism.getPatient());
					}
				}
			}
			//add wsr 2018-3-24 17:05:52 end
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("patientGroups", patientGroups);
			data_map.put("patientSize", patientMembers.size());
			
			map.put("status", "200");
			map.put("message", "查询成功");
			map.put("data",JsonUtils.toJson(data_map) );
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
	 * 医生患者分类数据(分组下的患者)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/groupDetails", method = RequestMethod.GET)
	public void groupDetails(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			Long patientGroupId = json.getLong("patientGroupId");
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
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			List<Map<String,Object>> patientList = new ArrayList<Map<String,Object>>();
			/**全部患者**/
			if(patientGroupId==0){
				Mechanism mechanism = doctorMechanismRelation.getMechanism();
				
//				Set<Member> member_mechanism = mechanism.getPatientMembers();
//				List<Member> member_doctor = doctor.getParents();
//				
//				List<Member> patientMembers = new ArrayList<Member>();
//				
//				for(Member memberMechanism: member_mechanism){
//					for(Member memberDoctor:member_doctor){
//						if(memberMechanism.equals(memberDoctor)){
//							patientMembers.add(memberMechanism);
//						}
//					}
//				}
//				
//				for(Member member : patientMembers){
//					Map<String,Object> patient_map = new HashMap<String, Object>();
//					patient_map.put("patientMemberId", member.getId());
//					patient_map.put("patientMemberLogo", member.getLogo());
//					patient_map.put("patientMemberName", member.getName());
//					patient_map.put("patientMemberSex", member.getGender());
//					patient_map.put("patientMemberAge", DateUtil.getAge(member.getBirth()));
//					patient_map.put("parentName", member.getParent().getName());
//					patient_map.put("parentPhone",MobileUtil.getStarString2(member.getParent().getMobile(), 3, 4));
//					patientList.add(patient_map);
//				}
				
				//add wsr 2018-3-24 17:13:47 start
				List<Member> member_doctor = doctor.getParents();
				for(PatientMechanism patientMechanism: mechanism.getPatientMechanisms()){
					for(Member memberDoctor:member_doctor){
						if(patientMechanism.getPatient().equals(memberDoctor)){
							Member member = patientMechanism.getPatient();
							Map<String,Object> patient_map = new HashMap<String, Object>();
							patient_map.put("patientMemberId", member.getId());
							patient_map.put("patientMemberLogo", member.getLogo());
							patient_map.put("patientMemberName", member.getName());
							patient_map.put("patientMemberSex", member.getGender());
							patient_map.put("patientMemberAge", DateUtil.getAge(member.getBirth()));
							patient_map.put("parentName", member.getParent().getName());
							patient_map.put("parentPhone",MobileUtil.getStarString2(member.getParent().getMobile(), 3, 4));
							patientList.add(patient_map);
							
						}
					}
				}
				//add wsr 2018-3-24 17:13:47 end
				
				
				
				data_map.put("patientMembers", patientList);
				map.put("status", "200");
				map.put("message", "查询成功");
				map.put("data",JsonUtils.toJson(data_map) );
				printWriter.write(JsonUtils.toString(map)) ;
				return;
				
			}
			
			PatientGroup patientGroup = patientGroupService.find(patientGroupId);
			List<Member> members = groupPatientService.getGroupPatientByGroupAndDoctor(doctorMechanismRelation, patientGroup);
			for(Member member : members){
				Map<String,Object> patient_map = new HashMap<String, Object>();
				patient_map.put("patientMemberId", member.getId());
				patient_map.put("patientMemberLogo", member.getLogo());
				patient_map.put("patientMemberName", member.getName());
				patient_map.put("patientMemberSex", member.getGender());
				patient_map.put("patientMemberAge", DateUtil.getAge(member.getBirth()));
				patient_map.put("parentName", member.getParent().getName());
				patient_map.put("parentPhone",MobileUtil.getStarString2(member.getParent().getMobile(), 3, 4));
				patientList.add(patient_map);
			}
			
			data_map.put("patientMembers", patientList);
			
			map.put("status", "200");
			map.put("message", "查询成功");
			map.put("data",JsonUtils.toJson(data_map) );
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
	 * 患者详情
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/patientDetails", method = RequestMethod.GET)
	public void patientDetails(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			Member patientMember  = memberService.find(json.getLong("patientMemberId"));
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
			
			Map<String,Object> patient_map = new HashMap<String, Object>();
			patient_map.put("patientMemberId", patientMember.getId());
			patient_map.put("patientMemberLogo", patientMember.getLogo());
			patient_map.put("patientMemberName", patientMember.getName());
			patient_map.put("patientMemberSex", patientMember.getGender());
			patient_map.put("patientMemberAge", DateUtil.getAge(patientMember.getBirth()));
			patient_map.put("parentName", patientMember.getParent().getName());
			patient_map.put("parentPhone",patientMember.getParent().getMobile());
			
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			List<PatientGroup> patientGroups = doctorMechanismRelation.getPatientGroups();
			
			GroupPatient groupPatient = groupPatientService.getGroupPatientByPatientAndDoctor(doctorMechanismRelation, patientMember);
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("patient", patient_map);
			data_map.put("patientGroups", patientGroups);
			if(null!=groupPatient){
				data_map.put("groupPatientName", groupPatient.getPatientGroup().getGroupName());
			}else{
				data_map.put("groupPatientName", "暂无分组");
			}
			
			map.put("status", "200");
			map.put("message", "查询成功");
			map.put("data",JsonUtils.toJson(data_map) );
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
	 * 患者切换分组
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/patientGroupExchange", method = RequestMethod.GET)
	public void patientGroupExchange(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			Member patientMember  = memberService.find(json.getLong("patientMemberId"));
			PatientGroup patientGroup = patientGroupService.find(json.getLong("patientGroupId"));
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
			
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			GroupPatient groupPatient = groupPatientService.getGroupPatientByPatientAndDoctor(doctorMechanismRelation, patientMember);
			/**如果为空,说明该患者没分组,直接添加到分组**/
			if(null==groupPatient){
				GroupPatient groupPatients = new GroupPatient();
				groupPatients.setDoctorMechanismRelation(doctorMechanismRelation);
				groupPatients.setPatientGroup(patientGroup);
				groupPatients.setPatientMember(patientMember);
				groupPatientService.save(groupPatients);
				
				//改变分组数量
				Integer sum = patientGroup.getSum()+1;
				patientGroup.setSum(sum);
				patientGroupService.update(patientGroup);
				
				map.put("status", "200");
				map.put("message", "分组成功");
				map.put("data","{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			/**如果存在,则先删除原分组信息,再创建新分组**/
			else{
				
				PatientGroup old_patientGroup = groupPatient.getPatientGroup();
				Integer sum = old_patientGroup.getSum()-1;
				old_patientGroup.setSum(sum);
				patientGroupService.update(old_patientGroup);
				
				
				groupPatientService.delete(groupPatient);
				
				GroupPatient groupPatients = new GroupPatient();
				groupPatients.setDoctorMechanismRelation(doctorMechanismRelation);
				groupPatients.setPatientGroup(patientGroup);
				groupPatients.setPatientMember(patientMember);
				groupPatientService.save(groupPatients);
				
				Integer sums = patientGroup.getSum()+1;
				patientGroup.setSum(sums);
				patientGroupService.update(patientGroup);
				
				map.put("status", "200");
				map.put("message", "分组成功");
				map.put("data","{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
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
	 * 分组删除患者
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/deletePatient", method = RequestMethod.GET)
	public void deletePatient(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
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
			
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Member patientMember  = memberService.find(json.getLong("patientMemberId"));
			PatientGroup patientGroup = patientGroupService.find(json.getLong("patientGroupId"));
			GroupPatient groupPatient = groupPatientService.getGroupPatient(doctorMechanismRelation, patientMember, patientGroup);
			
			groupPatientService.delete(groupPatient);
			
			Integer sum = patientGroup.getSum()-1;
			patientGroup.setSum(sum);
			patientGroupService.update(patientGroup);
			
			map.put("status", "200");
			map.put("message", "删除成功");
			map.put("data","{}");
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
	 * 分组批量添加患者数据
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/addPatientData", method = RequestMethod.GET)
	public void addPatientData(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
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
			
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			List<Member> group_member = groupPatientService.getAddGroupPatientData(doctorMechanismRelation);
			
//			Set<Member> member_mechanism = doctorMechanismRelation.getMechanism().getPatientMembers();
//			List<Member> member_doctor = doctor.getParents();
//			
//			List<Member> patientMembers = new ArrayList<Member>();
//			
//			for(Member memberMechanism: member_mechanism){
//				for(Member memberDoctor:member_doctor){
//					if(memberMechanism.equals(memberDoctor)){
//						patientMembers.add(memberMechanism);
//					}
//				}
//			}
			
			//add wsr 2018-3-24 17:09:03 start
			List<Member> member_doctor = doctor.getParents();
			List<Member> patientMembers = new ArrayList<Member>();
			for(PatientMechanism patientMechanism: doctorMechanismRelation.getMechanism().getPatientMechanisms()){
				for(Member memberDoctor:member_doctor){
					if(patientMechanism.getPatient().equals(memberDoctor)){
						patientMembers.add(patientMechanism.getPatient());
					}
				}
			}
			//add wsr 2018-3-24 17:09:03 end
			
			patientMembers.removeAll(group_member);
			
			List<Map<String,Object>> patientList = new ArrayList<Map<String,Object>>();
			for(Member member : patientMembers){
				Map<String,Object> patient_map = new HashMap<String, Object>();
				patient_map.put("patientMemberId", member.getId());
				patient_map.put("patientMemberLogo", member.getLogo());
				patient_map.put("patientMemberName", member.getName());
				patient_map.put("patientMemberSex", member.getGender());
				patient_map.put("patientMemberAge", DateUtil.getAge(member.getBirth()));
				patient_map.put("parentName", member.getParent().getName());
				patient_map.put("parentPhone",MobileUtil.getStarString2(member.getParent().getMobile(), 3, 4));
				patientList.add(patient_map);
			}
			
			data_map.put("patientList", patientList);
			
			map.put("status", "200");
			map.put("message", "查询成功");
			map.put("data",JsonUtils.toJson(data_map) );
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
	 * 分组批量添加患者(2018)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/addPatient", method = RequestMethod.GET)
	public void addPatient(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			String members = json.getString("members");
			
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
			
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			PatientGroup patientGroup = patientGroupService.find(json.getLong("patientGroupId"));
			String[] memberIds = null;   
			memberIds = members.split(",");
			
			for(int x = 0;x<memberIds.length;x++){
				long l = Long.parseLong(memberIds[x]);
				Member member = memberService.find(l);
				
				GroupPatient groupPatient = new GroupPatient();
				groupPatient.setDoctorMechanismRelation(doctorMechanismRelation);
				groupPatient.setPatientGroup(patientGroup);
				groupPatient.setPatientMember(member);
				groupPatientService.save(groupPatient);
			}	
			
			Integer sum = patientGroup.getSum()+memberIds.length;
			patientGroup.setSum(sum);
			patientGroupService.update(patientGroup);
			
			map.put("status", "200");
			map.put("message", "查询成功");
			map.put("data",JsonUtils.toJson(data_map) );
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
	 * 新建患者分组
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/addGroup", method = RequestMethod.GET)
	public void addGroup(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			String groupName = json.getString("groupName");
			String members = json.getString("members");
			
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
			
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			if(StringUtils.isEmpty(members)){
				PatientGroup patientGroup = new PatientGroup();
				patientGroup.setGroupName(groupName);
				patientGroup.setDoctorMechanismRelation(doctorMechanismRelation);
				Integer sum = 0;
				patientGroup.setSum(sum);
				patientGroupService.save(patientGroup);
				
				map.put("status", "200");
				map.put("message", "添加成功");
				map.put("data",JsonUtils.toJson(data_map) );
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
			String[] memberIds = null;   
			memberIds = members.split(",");
			System.out.println(memberIds.length);
			PatientGroup patientGroup = new PatientGroup();
			patientGroup.setGroupName(groupName);
			patientGroup.setDoctorMechanismRelation(doctorMechanismRelation);
			Integer sum = patientGroup.getSum()==null?0+memberIds.length:patientGroup.getSum()+memberIds.length;
			patientGroup.setSum(sum);
			patientGroupService.save(patientGroup);
			
			for(int x = 0;x<memberIds.length;x++){
				long l = Long.parseLong(memberIds[x]);
				Member member = memberService.find(l);
				
				GroupPatient groupPatient = new GroupPatient();
				groupPatient.setDoctorMechanismRelation(doctorMechanismRelation);
				groupPatient.setPatientGroup(patientGroup);
				groupPatient.setPatientMember(member);
				groupPatientService.save(groupPatient);
			}	
			
			
			map.put("status", "200");
			map.put("message", "添加成功");
			map.put("data",JsonUtils.toJson(data_map) );
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
	 * 删除患者分组
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteGroup", method = RequestMethod.GET)
	public void deleteGroup(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			PatientGroup patientGroup = patientGroupService.find(json.getLong("patientGroupId"));
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
			
		/*	DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			List<Member> members = groupPatientService.getGroupPatientByGroupAndDoctor(doctorMechanismRelation, patientGroup);
			if(members.size()>0){
				map.put("status", "400");
				map.put("message", "该分组下有患者,不能删除");
				map.put("data",JsonUtils.toJson(data_map) );
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}*/
			
			patientGroupService.delete(patientGroup);
			
			map.put("status", "200");
			map.put("message", "删除成功");
			map.put("data",JsonUtils.toJson(data_map) );
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
	 * 修改患者分组名称
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/editPatientGroup", method = RequestMethod.GET)
	public void editPatientGroup(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			PatientGroup patientGroup = patientGroupService.find(json.getLong("patientGroupId"));
			String patientGroupName = json.getString("patientGroupName");
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
			
			patientGroup.setGroupName(patientGroupName);
			
			patientGroupService.update(patientGroup);
			
			map.put("status", "200");
			map.put("message", "删除成功");
			map.put("data",JsonUtils.toJson(data_map) );
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
	 * 患者康护记录
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/patientHealthRecord", method = RequestMethod.GET)
	public void patientHealthRecord(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			Member patientMember = memberService.find(json.getLong("patientMemberId"));
			//获取页码
			Integer pageNumber = json.getInt("pageNumber");//页码
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
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			Mechanism mechanism = doctorMechanismRelation.getMechanism();
			
			Map<String ,Object> map_date = orderService.patientMemberRecord(patientMember,pageNumber,mechanism);
			printWriter.write(JsonUtils.toString(map_date)) ;
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
	 * 根据姓名搜索机构患者
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/searchPatient", method = RequestMethod.GET)
	public void searchPatient(String file,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
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
			
			String patientName = json.getString("patientName");
			List<Member> members = new ArrayList<Member>();
			List<Map<String,Object>> member_data = new ArrayList<Map<String,Object>>();
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDefaultDoctorMechanismRelation();
			if(doctorMechanismRelation==null){
				map.put("status", "400");
				map.put("message", "请选择默认机构");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Mechanism mechanism = doctorMechanismRelation.getMechanism();
			
//			for(Member member : mechanism.getPatientMembers()){
//				if(member.getName().contains(patientName)){
//					members.add(member);
//				}
//			}
			
			//add wsr 2018-3-24 17:10:51 start
			for(PatientMechanism patientMechanism : mechanism.getPatientMechanisms()){
				if(patientMechanism.getPatient().getName().contains(patientName)){
					members.add(patientMechanism.getPatient());
				}
			}
			//add wsr 2018-3-24 17:10:51 end
			
			for(Member member : members){
				Map<String,Object> patient_map = new HashMap<String, Object>();
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
				member_data.add(patient_map);
			}
			
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("patients", member_data);
			
			map.put("status", "200");
	        map.put("data", JsonUtils.toJson(member_data));
	        map.put("message", "数据加载成功");
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
