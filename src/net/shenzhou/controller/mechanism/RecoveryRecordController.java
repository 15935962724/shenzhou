/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.util.Collections;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.FileInfo.FileType;
import net.shenzhou.Config;
import net.shenzhou.Message;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismImage;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.RecoveryRecord;
import net.shenzhou.entity.RecoveryRecordImage;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.RecoveryRecordImageService;
import net.shenzhou.service.RecoveryRecordService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 康护记录
 * @date 2018年1月5日11:30:04
 * @author wsr
 */
@Controller("mechanismRecoveryRecordController")
@RequestMapping("/mechanism/recoveryRecord")
public class RecoveryRecordController extends BaseController {
	
	
	
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "recoveryRecordServiceImpl")
	private RecoveryRecordService recoveryRecordService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "recoveryRecordImageServiceImpl")
	private RecoveryRecordImageService recoveryRecordImageService;
	
	/**
	 * 机构端从订单详情页添加康护记录
	 * @param recoveryRecord
	 * @param orderId
	 * @param patientMemberId
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(RecoveryRecord recoveryRecord,Long orderId,Long patientMemberId, HttpServletRequest request ,RedirectAttributes redirectAttributes) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member patient = memberService.find(patientMemberId);
		Order order = orderService.find(orderId);
		for (Iterator<RecoveryRecordImage> iterator = recoveryRecord.getRecoveryRecordImages().iterator(); iterator.hasNext();) {
			RecoveryRecordImage recoveryRecordImage = iterator.next();
			if (recoveryRecordImage == null || recoveryRecordImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (recoveryRecordImage.getFile() != null && !recoveryRecordImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, recoveryRecordImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:redirect:mechanism/order/view.jhtml?id="+orderId;
				}
			}
		}
		
		for (RecoveryRecordImage recoveryRecordImage : recoveryRecord.getRecoveryRecordImages()) {
			recoveryRecordImageService.build(recoveryRecordImage);
		}
		Collections.sort(recoveryRecord.getRecoveryRecordImages());
		
		recoveryRecord.setMechanism(mechanism);
		recoveryRecord.setOrder(order);
		recoveryRecord.setPatient(patient);
		recoveryRecordService.save(recoveryRecord);
		
		System.out.println("添加成功");
		return "redirect:/mechanism/order/view.jhtml?id="+orderId;
	}

	
	/**
	 * 机构端从就诊记录页面添加康护记录
	 * @param recoveryRecord
	 * @param orderId
	 * @param patientMemberId
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/saveRecoveryRecord", method = RequestMethod.POST)
	public String saveRecoveryRecord(RecoveryRecord recoveryRecord,Long orderId,Long patientMemberId, HttpServletRequest request ,RedirectAttributes redirectAttributes) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member patient = memberService.find(patientMemberId);
		Order order = orderService.find(orderId);
		for (Iterator<RecoveryRecordImage> iterator = recoveryRecord.getRecoveryRecordImages().iterator(); iterator.hasNext();) {
			RecoveryRecordImage recoveryRecordImage = iterator.next();
			if (recoveryRecordImage == null || recoveryRecordImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (recoveryRecordImage.getFile() != null && !recoveryRecordImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, recoveryRecordImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:redirect:mechanism/order/patient_order.jhtml?patientMemberId="+patientMemberId;
				}
			}
		}
		
		for (RecoveryRecordImage recoveryRecordImage : recoveryRecord.getRecoveryRecordImages()) {
			recoveryRecordImageService.build(recoveryRecordImage);
		}
		Collections.sort(recoveryRecord.getRecoveryRecordImages());
		
		recoveryRecord.setMechanism(mechanism);
		recoveryRecord.setOrder(order);
		recoveryRecord.setPatient(patient);
		recoveryRecordService.save(recoveryRecord);
		
		System.out.println("添加成功");
		return "redirect:/mechanism/order/patient_order.jhtml?patientMemberId="+patientMemberId;
	}
	

}