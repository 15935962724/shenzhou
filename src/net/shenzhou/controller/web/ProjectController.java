/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Config;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDayItem;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Order.ServeState;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.EvaluateService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.service.VisitMessageService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 服务
 * @date 2017-10-25 11:53:31
 * @author fl
 *
 */
@Controller("webProjectController")
@RequestMapping("/web/project")
public class ProjectController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService ;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService ;
	@Resource(name = "visitMessageServiceImpl")
	private VisitMessageService visitMessageService ;
	@Resource(name = "evaluateServiceImpl")
	private EvaluateService evaluateService ;
	@Resource(name = "verificationServiceImpl")
	private VerificationService verificationService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	
	/**
	 * 服务列表跳转
	 */
	@RequestMapping(value = "/toProjectList", method = RequestMethod.GET)
	public String toProjectList(ModelMap model) {
		return "/web/serve/projectList";
	}
	
	
	/**
	 * 服务列表
	 */
	@RequestMapping(value = "/projectList", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String projectList(ModelMap model,String longitude,String latitude,Integer pageNumber,String flag,String sort) {
		Map<String,Object> data = projectService.getWebProjectList(pageNumber,longitude,latitude,flag,sort);
		List<Project> projects = (List<Project>) data.get("projectList");
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		
		
		Integer pageSize = Config.pageSize;//每页条数
		Map<String,Object> data_map = new HashMap<String, Object>();
	/*	if(projects.size()<=0){
			map.put("status", "500");
			map.put("message","暂无订单数据");
			map.put("data", "{}");
	        printWriter.write(JsonUtils.toString(map));
			return;
		}*/
		
		if(projects.size()<=0){
			return JsonUtils.toJson(data_list);
		}
		
		//总页数
		Integer pagecount = ((projects.size()+pageSize-1)/pageSize);
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		List<Project> project_list = new ArrayList<Project>();
		if (projects.size()>0){
			if(pageNumber==pagecount){
				project_list = projects.subList((pagenumber-1)*pageSize, projects.size());
			}else{
				project_list = projects.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
			}
			if (pageNumber>pagecount) {
				return JsonUtils.toJson(data_list);
			}
		}
		
		for(Project project : project_list){
			Map<String,Object> project_map = new HashMap<String, Object>();
			project_map.put("projectId", project.getId());
			project_map.put("projectName", project.getName());
			project_map.put("mechanismName", project.getMechanism().getName());
			project_map.put("doctorName", project.getDoctor().getName());
			project_map.put("sex", project.getDoctor().getGender());
			project_map.put("occupation", project.getDoctor().getDoctorCategory().getTitle());
			project_map.put("second", project.getSecond());
			project_map.put("scoreSort", project.getDoctor().getScoreSort());
			project_map.put("longitude", project.getMechanism().getLongitude());
			project_map.put("latitude", project.getMechanism().getLatitude());
			project_map.put("price", project.getPrice());
			project_map.put("time", project.getTime());
			project_map.put("projectLogo", project.getLogo());
			
			data_list.add(project_map);
		}
		
		
		return JsonUtils.toJson(data_list);
	}
	
	/**
	 * 项目详情
	 */
	@RequestMapping(value = "/projectDetails", method = RequestMethod.GET)
	public String projectDetails(ModelMap model,Long projectId) {
		Project project = projectService.find(projectId);
		Doctor doctor = project.getDoctor();
		Mechanism mechanism = project.getMechanism();
		List<Project> project_list = projectService.getProjectByDoctor(doctor);
		List<Doctor> doctor_list = new ArrayList<Doctor>();
		for (DoctorMechanismRelation doctorMechanismRelation : mechanism.getDoctorMechanismRelation()) {
			if (doctorMechanismRelation.getAudit().equals(DoctorMechanismRelation.Audit.succeed)) {
				if(doctorMechanismRelation.getDoctor()!=null&&doctorMechanismRelation.getDoctor().getIsDeleted().equals(false)){
					doctor_list.add(doctorMechanismRelation.getDoctor());
				}
			}
		}
		
		model.addAttribute("project", project);
		model.addAttribute("doctor", doctor);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("projectSize", project_list.size());
		model.addAttribute("doctorSize", doctor_list.size());
		return "/web/serve/projectDetails";
	}
	
}