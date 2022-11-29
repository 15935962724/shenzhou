/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.CommonAttributes;
import net.shenzhou.ExcelView;
import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.Setting;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.BaseEntity.Save;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Doctor.Status;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.Evaluate;
import net.shenzhou.entity.Information;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.ProjectItem;
import net.shenzhou.entity.Information.DisposeState;
import net.shenzhou.entity.Information.InformationType;
import net.shenzhou.entity.Information.StateType;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.MemberAttribute;
import net.shenzhou.entity.MemberAttribute.Type;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Project.Audit;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.User;
import net.shenzhou.entity.VisitMessage;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.DoctorCategoryService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.EvaluateService;
import net.shenzhou.service.InformationService;
import net.shenzhou.service.MemberAttributeService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.UserService;
import net.shenzhou.service.VisitMessageService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 评价信息
 * @date 2017-9-16 15:36:00
 * @author wsr
 *
 */
@Controller("mechanismEvaluateController")
@RequestMapping("/mechanism/evaluate")
public class EvaluateController extends BaseController {

	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "evaluateServiceImpl")
	private EvaluateService evaluateService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;

	/**
	 * 2018-1-24 13:44:54之前的评价信息
	 * 列表
	 */
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public String list(Pageable pageable,Date startDate,Date endDate,Long projectId,String nameOrmobile, ModelMap model) {
////		User user = userService.getCurrent();
////		Mechanism mechanism = user.getMechanism();
//		Doctor doctorC = doctorService.getCurrent();
//		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
//		pageable.setPageSize(2);
//		Project project = projectService.find(projectId);
//		List<Project> projects = new ArrayList<Project>();//机构端所有的项目
//		for (Doctor doctor : mechanism.getDoctors()) {
//			for (Project project2 : doctor.getProjects()) {
//				projects.add(project2);
//			}
//		}
//		Map<String,Object> query_map = new HashMap<String, Object>();
//		Calendar calendar = Calendar.getInstance();
//		if (startDate!=null) {
//			calendar.setTime(startDate);
//			calendar.set(Calendar.HOUR_OF_DAY,00);
//			calendar.set(Calendar.MINUTE,00);
//			calendar.set(Calendar.SECOND,00);
//			startDate = calendar.getTime();
//		}
//		if (endDate!=null) {
//			calendar.setTime(endDate);
//			calendar.set(Calendar.HOUR_OF_DAY,23);
//			calendar.set(Calendar.MINUTE,59);
//			calendar.set(Calendar.SECOND,59);
//			endDate = calendar.getTime();
//		}
//		query_map.put("startDate", startDate);
//		query_map.put("endDate", endDate);
//		query_map.put("project", project);
//		query_map.put("nameOrmobile", nameOrmobile);
//		query_map.put("mechanism", mechanism);
//		query_map.put("pageable", pageable);
//	
//		Page<Evaluate> page = evaluateService.getPageEvaluate(query_map);
//		model.addAttribute("projects", projects);
//		model.addAttribute("page", page);
//		model.addAttribute("startDate", startDate);
//		model.addAttribute("endDate", endDate);
//		model.addAttribute("nameOrmobile", nameOrmobile);
//		model.addAttribute("projectId", projectId);
//		model.addAttribute("mechanism", mechanism);
//		
//		return "/mechanism/evaluate/list";
//	}

	
	
	/**
	 * 评分统计 
	 * @param pageable
	 * @param startDate
	 * @param endDate
	 * @param doctorNameOrMobile
	 * @param memberNameOrMobile
	 * @param patientName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,Date startDate,Date endDate,String doctorNameOrMobile,String nameOrmobile,String patientName,ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		Map<String,Object> query_map = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		if (startDate!=null) {
			calendar.setTime(startDate);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			startDate = calendar.getTime();
		}
		if (endDate!=null) {
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			endDate = calendar.getTime();
		}
		query_map.put("startDate", startDate);
		query_map.put("endDate", endDate);
		query_map.put("doctorNameOrMobile", doctorNameOrMobile);
		query_map.put("nameOrmobile", nameOrmobile);
		query_map.put("patientName", patientName);
		query_map.put("mechanism", mechanism);
		query_map.put("pageable", pageable);
	
		Page<Evaluate> page = evaluateService.getPageEvaluateCharge(query_map);
		model.addAttribute("page", page);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("doctorNameOrMobile", doctorNameOrMobile);
		model.addAttribute("nameOrmobile", nameOrmobile);
		return "/mechanism/evaluate/list";
	}
	
	@RequestMapping(value = "/downloadList", method = RequestMethod.GET)
	public ModelAndView downloadList(Date startDate,Date endDate,String doctorNameOrMobile,String nameOrmobile,String patientName,ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		Map<String,Object> query_map = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		if (startDate!=null) {
			calendar.setTime(startDate);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			startDate = calendar.getTime();
		}
		if (endDate!=null) {
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			endDate = calendar.getTime();
		}
		query_map.put("startDate", startDate);
		query_map.put("endDate", endDate);
		query_map.put("doctorNameOrMobile", doctorNameOrMobile);
		query_map.put("nameOrmobile", nameOrmobile);
		query_map.put("patientName", patientName);
		query_map.put("mechanism", mechanism);
	
		List<Evaluate> evaluates = evaluateService.getDownloadList(query_map);
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for (Evaluate evaluate : evaluates) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("memberName", evaluate.getMember().getName());
			data_map.put("memberMobile", evaluate.getMember().getMobile());
			data_map.put("scoreSort", evaluate.getScoreSort());
			data_map.put("skillSort", evaluate.getSkillSort());
			data_map.put("serverSort", evaluate.getServerSort());
			data_map.put("communicateSort", evaluate.getCommunicateSort());
			data_map.put("doctorName", evaluate.getProject().getDoctor().getName());
			data_map.put("doctorMobile", evaluate.getProject().getDoctor().getMobile());
			data_map.put("projectName", evaluate.getProject().getName());
			data_map.put("createDate", DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss",evaluate.getCreateDate()));
			data_map.put("content", evaluate.getContent());
			data_list.add(data_map);
		}
		
		String filename = "评价统计" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"联系人","电话","综合评分","技能评分","服务评分","沟通评分","诊疗医师","医师电话","服务项目","评价时间","评论内容"};//title
		String[] contents = new String []{"memberName","memberMobile","scoreSort","skillSort","serverSort","communicateSort","doctorName","doctorMobile","projectName","createDate","content"};//content
		
		
		String[] memos = new String [3];//memo
		memos[0] = "记录数" + ": " + data_list.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
	} 
	
	/**
	 * 删除评价信息
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete_doctor_evalueate(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Evaluate evaluate = evaluateService.find(id);
				evaluate.setIsDeleted(true);
				evaluateService.update(evaluate);
			}
		}
		return SUCCESS_MESSAGE;
	}
	
}

