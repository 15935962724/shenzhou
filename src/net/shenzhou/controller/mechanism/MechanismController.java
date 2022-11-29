/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shenzhou.Config;
import net.shenzhou.FileInfo;
import net.shenzhou.FileInfo.FileType;
import net.shenzhou.FileInfo.OrderType;
import net.shenzhou.Message;
import net.shenzhou.Setting;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Management;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.MechanismImage;
import net.shenzhou.entity.MechanismRank;
import net.shenzhou.entity.MechanismRole;
import net.shenzhou.entity.MechanismSetup.AchievementsType;
import net.shenzhou.entity.WorkDate;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.ManagementService;
import net.shenzhou.service.MechanismCategoryService;
import net.shenzhou.service.MechanismImageService;
import net.shenzhou.service.MechanismRankService;
import net.shenzhou.service.MechanismRoleService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.WorkDateService;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.MapUtil;
import net.shenzhou.util.SettingUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 机构用户
 * 2017-06-22 16:40:53
 * @author wsr
 *
 */
@Controller("mechanismController")
@RequestMapping("/mechanism/mechanism")
public class MechanismController extends BaseController {

	
//	@Resource(name = "userServiceImpl")
//	private UserService userService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "mechanismCategoryServiceImpl")
	private MechanismCategoryService mechanismCategoryService;
	@Resource(name = "mechanismRankServiceImpl")
	private MechanismRankService mechanismRankService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "managementServiceImpl")
	private ManagementService managementService;
	@Resource(name = "workDateServiceImpl")
	private WorkDateService workDateService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "mechanismImageServiceImpl")
	private MechanismImageService mechanismImageService;
	@Resource(name = "mechanismRoleServiceImpl")
	private MechanismRoleService mechanismRoleService;
	
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(ModelMap model, HttpServletRequest request) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		model.addAttribute("mechanismCategorys", mechanismCategoryService.findAll());
		model.addAttribute("mechanismRanks", mechanismRankService.findAll());
		model.addAttribute("serverProjectCategorys", serverProjectCategoryService.findAll());
		model.addAttribute("areas", areaService.findRoots());
		model.addAttribute("managements", managementService.findAll());
		
		if (mechanism==null) {
			return "mechanism/mechanism/add";
		}
		
		model.addAttribute("mechanism", mechanism);
		return "mechanism/mechanism/edit";
	}
	
	/**
	 * 提交保存
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Mechanism mechanism ,Long areaId, Long mechanismRankId,Long mechanismCategoryId,WorkDate workDate,Long[]managementIds, @RequestParam(value = "logo_img", required = false)MultipartFile  file, HttpServletRequest request ,RedirectAttributes redirectAttributes) {
		Setting setting = SettingUtils.get();
//		User user = userService.getCurrent();
//		Mechanism mechanism  = new Mechanism();
		Doctor doctorC = doctorService.getCurrent();
//		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		for (Iterator<MechanismImage> iterator = mechanism.getMechanismImages().iterator(); iterator.hasNext();) {
			MechanismImage mechanismImage = iterator.next();
			if (mechanismImage == null || mechanismImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (mechanismImage.getFile() != null && !mechanismImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, mechanismImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:view.jhtml";
				}
			}
		}
		System.out.println(managementIds);
		if (managementIds!=null) {//判断经营模式
			for (Long managementId : managementIds) {
				Management management = managementService.find(managementId);
				mechanism.getManagements().add(management);
			}
		}
		
		
		String path = Config.mechanismLogoUrl;
		String url = file.getSize()==0?Config.getMechanismDefaultLogo():fileService.uploadImg(FileType.image, file, path,UUID.randomUUID().toString(), false);
		System.out.println(url);
		Date currTime = new Date();
//		int month = currTime.getMonth()+1;//当前月份
//		workDate.setIsDeleted(false);
//		workDate.setMonth(String.valueOf(month));
//		workDateService.save(workDate);
		System.out.println("添加机构"+mechanism.getName());
		Area area = areaService.find(areaId);
		MechanismRank mechanismRank = mechanismRankService.find(mechanismRankId);
		MechanismCategory mechanismCategory = mechanismCategoryService.find(mechanismCategoryId);
		mechanism.setArea(area);
		mechanism.setMechanismCategory(mechanismCategory);
		mechanism.setMechanismRank(mechanismRank);
		String address = area.getFullName()+mechanism.getAddress();
		Double longitude;
		Double latitude;
		Object[] obj ;
		 try {
			  obj =  MapUtil.getCoordinate(address);
			  longitude =obj[0]==null?116.404:Double.valueOf(obj[0].toString());
			  latitude = obj[1]==null?39.915:Double.valueOf(obj[1].toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			longitude =  116.404;
			latitude =  39.915;
		}
		mechanism.setLogo(url);
		mechanism.setLongitude(longitude);
		mechanism.setLatitude(latitude);
		mechanism.setWorkDate(workDate);
		mechanism.setIsDeleted(false);
		mechanism.setSecond(0);
		mechanism.setScoreSort(10d);
		mechanism.setContacts(doctorC.getUsername());
//		mechanism.getUsers().add(user);
		
		for (MechanismImage mechanismImage : mechanism.getMechanismImages()) {
			mechanismImageService.build(mechanismImage);
		}
		Collections.sort(mechanism.getMechanismImages());
		if (mechanism.getIntroduceImg() == null && mechanism.getSourceImg() != null) {
			mechanism.setIntroduceImg(mechanism.getSourceImg());
		}else{
			mechanism.setIntroduceImg(Config.getMechanismIntroduceImgUrl());
		}
		mechanismService.save(mechanism);
		DoctorMechanismRelation doctorMechanismRelation = new DoctorMechanismRelation();
		doctorMechanismRelation.setDoctor(doctorC);
		doctorMechanismRelation.setMechanism(mechanism);
		doctorMechanismRelation.setAudit(Audit.succeed);
		doctorMechanismRelation.setIsEnabled(true);
		doctorMechanismRelation.setIsSystem(true);
		doctorMechanismRelation.setDefaultMechanism(true);
		doctorMechanismRelationService.save(doctorMechanismRelation);
		
		//创建该机构的角色
		MechanismRole mechanismRole = new MechanismRole();
		mechanismRole.setName("机构管理员");
		mechanismRole.setDescription("主要用来管理机构");
		mechanismRole.setIsSystem(true);
		mechanismRole.setMechanism(mechanism);
//		mechanismRole.getUsers().add(user);
		mechanismRole.getDoctorMechanismRelations().add(doctorMechanismRelation);
		mechanismRole.setIsDeleted(false);
//		mechanismRole.getAuthorities().addAll(mechanismRoleService.find(1l).getAuthorities());
		mechanismRole.getAuthorities().addAll(Arrays.asList(setting.getAuthorities()));
//		System.arraycopy(setting.getAuthorities(), 0, mechanismRole.getAuthorities(), 0, setting.getAuthorities().length);
		mechanismRoleService.save(mechanismRole);
		
//		user.getMechanismroles().add(mechanismRole);
//		user.setMechanism(mechanism);
//		userService.update(user);
		
		System.out.println("添加成功");
		return "redirect:view.jhtml";
	}

	/**
	 * 编辑机构信息 wsr 
	 * @param mechanism
	 * @param areaId
	 * @param mechanismRankId
	 * @param mechanismCategoryId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Mechanism mechanism ,Long areaId, Long mechanismRankId,Long mechanismCategoryId,String startTime,String endTime, @RequestParam(value = "logo_img", required = false)MultipartFile  file, HttpServletRequest request,RedirectAttributes redirectAttributes) {
//		User user = userService.getCurrent();
		Doctor doctorC = doctorService.getCurrent();
//		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		String path = Config.mechanismLogoUrl;
		String url = file.getSize()==0?mechanism.getLogo():fileService.uploadImg(FileType.image, file, path,UUID.randomUUID().toString(), false);
		System.out.println(url);
		
		System.out.println("上传："+mechanism.getMechanismImages().size()+"张图片");
		
		for (Iterator<MechanismImage> iterator = mechanism.getMechanismImages().iterator(); iterator.hasNext();) {
			MechanismImage mechanismImage = iterator.next();
			if (mechanismImage == null || mechanismImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (mechanismImage.getFile() != null && !mechanismImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, mechanismImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:view.jhtml";
				}
			}
		}
		
//		经营模式
//		for (Long managementId : managementIds) {
//			Management management = managementService.find(managementId);
//			mechanism.getManagements().add(management);
//		}

		for (MechanismImage mechanismImage : mechanism.getMechanismImages()) {
			mechanismImageService.build(mechanismImage);
		}
		Collections.sort(mechanism.getMechanismImages());
		if (mechanism.getIntroduceImg() == null && mechanism.getSourceImg()!= null) {
			mechanism.setIntroduceImg(mechanism.getSourceImg());
		}else{
			mechanism.setIntroduceImg(Config.getMechanismIntroduceImgUrl());
		}
		
		Mechanism pMechanism = mechanismService.find(mechanism.getId());
		if (pMechanism == null) {
			return ERROR_VIEW;
		}
		Date currTime = new Date();
		int month = currTime.getMonth()+1;//当前月份
		WorkDate workDate = mechanismService.find(mechanism.getId()).getWorkDate();
//		workDate.setMonth(String.valueOf(month));
//		workDate.setStartTime(startTime);
//		workDate.setEndTime(endTime);
//		workDateService.update(workDate);
		System.out.println("编辑机构"+mechanism.getName());
		Area area = areaService.find(areaId);
		MechanismRank mechanismRank = mechanismRankService.find(mechanismRankId);
		MechanismCategory mechanismCategory = mechanismCategoryService.find(mechanismCategoryId);
		mechanism.setArea(area);
		mechanism.setMechanismCategory(mechanismCategory);
		mechanism.setMechanismRank(mechanismRank);
		String address = area.getFullName()+mechanism.getAddress();
		Double longitude;
		Double latitude;
		Object[] obj ;
		 try {
			  obj =  MapUtil.getCoordinate(address);
			  longitude =obj[0]==null?116.404:Double.valueOf(obj[0].toString());
			  latitude = obj[1]==null?39.915:Double.valueOf(obj[1].toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			longitude =  116.404;
			latitude =  39.915; 
		}
		 mechanism.setLongitude(longitude);
		 mechanism.setLatitude(latitude);
		 mechanism.setLogo(url);
		 mechanism.setName(mechanism.getName());
		 mechanism.setAddress(mechanism.getAddress());
		 mechanism.setIntroduce(mechanism.getIntroduce())	;
		 mechanism.setIntroduceImg(mechanism.getIntroduceImg());
		 mechanism.setPhone(mechanism.getPhone());
		
		 mechanism.setScoreSort(pMechanism.getScoreSort());
		 mechanism.setSecond(pMechanism.getSecond());
		 
		 mechanism.setContacts(pMechanism.getContacts());
		 mechanism.setIsAuthentication(pMechanism.getIsAuthentication());
		 mechanism.setServerStatus(pMechanism.getServerStatus());
		 mechanism.setStartDate(pMechanism.getStartDate());
		 mechanism.setEndDate(pMechanism.getEndDate());
		 
//		 mechanism.getUsers().add(user);
		 mechanism.setIsDeleted(false);
		 mechanism.setWorkDate(workDate);
		 
		mechanismService.update(mechanism);
		System.out.println("编辑成功");
		return "redirect:view.jhtml";
	}
	
	
	/**
	 * 上传
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public void upload(FileType fileType, MultipartFile file,String path, HttpServletResponse response) {
		Map<String, Object> data = new HashMap<String, Object>();
//		String paths = "/upload/mechanismLog/";
		System.out.println(path);
		String name = UUID.randomUUID().toString();
		
		if (!fileService.isValid(fileType, file)) {
			data.put("message", Message.warn("admin.upload.invalid"));
		} else {
			String url = fileService.uploadImg(fileType, file, path, name, false);
			if (url == null) {
				data.put("message", Message.warn("admin.upload.error"));
			} else {
				data.put("message", SUCCESS_MESSAGE);
				data.put("url", url);
			}
		}
		try {
			response.setContentType("text/html; charset=UTF-8");
			JsonUtils.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 浏览
	 */
	@RequestMapping(value = "/browser", method = RequestMethod.GET)
	public @ResponseBody
	List<FileInfo> browser(String path, FileType fileType, OrderType orderType) {
		return fileService.browser(path, fileType, orderType);
	}
	
	
	
	

}