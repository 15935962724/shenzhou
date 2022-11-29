/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.controller.mechanism;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PatientMechanism.HealthType;
import net.shenzhou.entity.VisitMessage;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.UserService;
import net.shenzhou.service.VisitMessageService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* @ClassName: VisitMessageController 
* @Description: TODO(回访信息) 
* @author wsr  
* @date 2017-8-16 15:50:58
 */
@Controller("mechanismVisitMessageController")
@RequestMapping("/mechanism/visitMessage")
public class VisitMessageController extends BaseController {

	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "visitMessageServiceImpl")
	private VisitMessageService visitMessageService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	
	
	
	/**
	 * 用户回访信息
	 * @param memberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member_visitMessage_list", method = RequestMethod.GET)
	public String member_visitMessage_list(Long memberId,String nameOrMobile ,Pageable pageable,Date startCreateDate,Date endCreateDate,Date startVisitDate,Date endVisitDate,String type,ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		pageable.setPageSize(5);
		Member member = memberService.find(memberId);
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("member", member);
		query_map.put("mechanism", mechanism);
//		query_map.put("nameOrMobile", nameOrMobile);
		query_map.put("pageable", pageable);
		Calendar calendar = Calendar.getInstance();
		if (startCreateDate!=null) {
			calendar.setTime(startCreateDate);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			startCreateDate = calendar.getTime();
		}
		if (endCreateDate!=null) {
			calendar.setTime(endCreateDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			endCreateDate = calendar.getTime();
		}
		query_map.put("startCreateDate", startCreateDate);
		query_map.put("endCreateDate", endCreateDate);
		query_map.put("startVisitDate", startVisitDate);
		query_map.put("endVisitDate", endVisitDate);
		query_map.put("type", type);
		Page<VisitMessage> page = visitMessageService.findPage(query_map);
		model.addAttribute("startCreateDate", startCreateDate);
		model.addAttribute("endCreateDate", endCreateDate);
		model.addAttribute("startVisitDate", startVisitDate);
		model.addAttribute("endVisitDate", endVisitDate);
//		model.addAttribute("nameOrMobile", nameOrMobile);
		model.addAttribute("page", page);
		model.addAttribute("member", member);
		model.addAttribute("mechanism", mechanism);
		return "mechanism/visitMessage/member_visitMessage_list";
	}
	
	/**
	 * 患者回访信息
	 * @param memberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/patient_visitMessage_list", method = RequestMethod.GET)
	public String patient_visitMessage_list(Long patientMemberId,Pageable pageable,Date startCreateDate,Date endCreateDate,Date startVisitDate,Date endVisitDate,String type, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member patientMember = memberService.find(patientMemberId);
		pageable.setPageSize(5);
		Member member = patientMember.getParent();
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("member", member);
		query_map.put("patientMember", patientMember);
		query_map.put("mechanism", mechanism);
//		query_map.put("nameOrMobile", nameOrMobile);
		query_map.put("pageable", pageable);
		Calendar calendar = Calendar.getInstance();
		if (startCreateDate!=null) {
			calendar.setTime(startCreateDate);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			startCreateDate = calendar.getTime();
		}
		if (endCreateDate!=null) {
			calendar.setTime(endCreateDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			endCreateDate = calendar.getTime();
		}
		query_map.put("startCreateDate", startCreateDate);
		query_map.put("endCreateDate", endCreateDate);
		query_map.put("startVisitDate", startVisitDate);
		query_map.put("endVisitDate", endVisitDate);
		query_map.put("type", type);
		Page<VisitMessage> page = visitMessageService.findPage(query_map);
		model.addAttribute("startCreateDate", startCreateDate);
		model.addAttribute("endCreateDate", endCreateDate);
		model.addAttribute("startVisitDate", startVisitDate);
		model.addAttribute("endVisitDate", endVisitDate);
//		model.addAttribute("nameOrMobile", nameOrMobile);
		model.addAttribute("page", page);
		model.addAttribute("member", member);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("patientMember", patientMember);
		model.addAttribute("colour", "4");
		model.addAttribute("healthTypes", HealthType.values());
		model.addAttribute("patientMechanism", patientMember.getPatientMechanism(mechanism));
		return "mechanism/visitMessage/patient_visitMessage_list";
	}
	
	
	/**
	 * 
	 * @param patientMemberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(VisitMessage visitMessage ,Long visitDoctorId,Long patientMemberId,String type, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member patientMember = memberService.find(patientMemberId);
		Doctor doctor = doctorService.find(visitDoctorId);
		visitMessage.setDoctor(doctor);
		visitMessage.setPatient(patientMember);
		visitMessage.setMember(patientMember.getParent());
		visitMessage.setMechanism(mechanism);
		visitMessage.setIsDeleted(false);
		visitMessageService.save(visitMessage);
		
		if (type.equals("member")) {//如果是用户端提交的回访信息就跳转用户回访信息列表
			return "redirect:member_visitMessage_list.jhtml?memberId="+patientMember.getParent().getId()+"&type="+type;
		}
		return "redirect:patient_visitMessage_list.jhtml?patientMemberId="+patientMemberId+"&type="+type;
		
	}
	
	/**
	 * 编辑回访信息页面(用户端)
	 * @param visitMessageId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member_visitMessage_edit", method = RequestMethod.GET)
	public String member_visitMessage_edit(Long visitMessageId, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		VisitMessage visitMessage = visitMessageService.find(visitMessageId);
		model.addAttribute("member", visitMessage.getMember());
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("visitMessage", visitMessage);
		return "mechanism/visitMessage/member_visitMessage_edit";
		
	}
	
	/**
	 * 编辑回访信息(患者端)
	 * @param visitMessageId
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/patient_visitMessage_edit", method = RequestMethod.GET)
	public String patient_visitMessage_edit(Long visitMessageId,String type, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		VisitMessage visitMessage = visitMessageService.find(visitMessageId);
		model.addAttribute("patientMember", visitMessage.getPatient());
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("visitMessage", visitMessage);
		model.addAttribute("type", type);
		return "mechanism/visitMessage/patient_visitMessage_edit";
		
	}
	

	/**
	 * 编辑回访信息
	 * @param doctorId
	 * @param patientMemberId
	 * @param visitDate
	 * @param message
	 * @param type
	 * @param visitMessageId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Long doctorId,Long patientMemberId,Date visitDate,String message,String type,Long visitMessageId,ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member patientMember = memberService.find(patientMemberId);
		Doctor doctor = doctorService.find(doctorId);
		VisitMessage visitMessage = visitMessageService.find(visitMessageId);
		visitMessage.setVisitDate(visitDate);
		visitMessage.setDoctor(doctor);
		visitMessage.setPatient(patientMember);
		visitMessage.setMember(patientMember.getParent());
		visitMessage.setMessage(message);
		visitMessageService.update(visitMessage);
		
		if (type.equals("member")) {//如果是用户端提交的回访信息就跳转用户回访信息列表
			return "redirect:member_visitMessage_list.jhtml?memberId="+patientMember.getParent().getId()+"&type="+type;
		}
		return "redirect:patient_visitMessage_list.jhtml?patientMemberId="+patientMemberId+"&type="+type;
		
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