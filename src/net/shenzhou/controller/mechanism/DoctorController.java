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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import net.shenzhou.CommonAttributes;
import net.shenzhou.Config;
import net.shenzhou.ExcelView;
import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.Setting;
import net.shenzhou.FileInfo.FileType;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.BaseEntity.Save;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Doctor.Status;
import net.shenzhou.entity.DoctorCategory;
import net.shenzhou.entity.DoctorCategoryRelation;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Evaluate;
import net.shenzhou.entity.Information;
import net.shenzhou.entity.MechanismRole;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.WorkTarget;
import net.shenzhou.entity.Information.DisposeState;
import net.shenzhou.entity.Information.InformationType;
import net.shenzhou.entity.Information.StateType;
import net.shenzhou.entity.Information.UserType;
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
import net.shenzhou.service.DoctorCategoryRelationService;
import net.shenzhou.service.DoctorCategoryService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.EvaluateService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.InformationService;
import net.shenzhou.service.MechanismRoleService;
import net.shenzhou.service.MechanismService;
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
import net.shenzhou.util.PinyinUtil;
import net.shenzhou.util.PushUtil;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 医生
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("mechanismDoctorController")
@RequestMapping("/mechanism/doctor")
public class DoctorController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "doctorCategoryServiceImpl")
	private DoctorCategoryService doctorCategoryService;
	@Resource(name = "informationServiceImpl")
	private InformationService informationService;
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	@Resource(name = "visitMessageServiceImpl")
	private VisitMessageService visitMessageService;
	@Resource(name = "evaluateServiceImpl")
	private EvaluateService evaluateService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "mechanismRoleServiceImpl")
	private MechanismRoleService mechanismRoleService;
	@Resource(name = "doctorCategoryRelationServiceImpl")
	private DoctorCategoryRelationService doctorCategoryRelationService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	
	
	
	
	/**
	 * 检查用户名是否被禁用或已存在
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (doctorService.usernameDisabled(username) || doctorService.usernameExists(username)) {
			return false;
		} else {
			return true;
		}
	}

	
	/**
	 * 新增员工判断该员工是否 在平台存在
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/username", method = RequestMethod.GET)
	public @ResponseBody
	Map<String,Object> getIsDoctorMechanism(String username) {
		Map<String,Object> map = new HashMap<String, Object>();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		if (StringUtils.isEmpty(username)) {
			map.put("status", "400");
			map.put("message", "手机号不能为空");
			map.put("data", "{}");
			return map;
		}
		Doctor doctor = doctorService.findByUsername(username);
		if (doctor==null) {
			map.put("status", "200");
			map.put("message", "该用户不存在，可新增该员工");
			map.put("data", "{}");
		}else{
			DoctorMechanismRelation doctorMechanismRelation = doctor.getDoctorMechanismRelation(mechanism);
			if (doctorMechanismRelation==null) {
				map.put("status", "300");
				map.put("message", "该用户已在平台端，可直接加入本机构");
				Map<String,Object> doctor_map = new HashMap<String, Object>();
				doctor_map.put("username", doctor.getUsername());
				doctor_map.put("password", doctor.getPassword());
				doctor_map.put("gender", doctor.getGender());
				doctor_map.put("name", doctor.getName());
				doctor_map.put("id", doctor.getId());
				map.put("data", doctor_map);
			}else{
				map.put("status", "500");
				map.put("message", "该员工已在本机构，不可重复添加");
				map.put("data", "{}");
			}
		}
		return map;
	}
	

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		DoctorMechanismRelation doctorMechanismRelation = doctorMechanismRelationService.find(id);
		List<DoctorCategory> doctorCategorys = doctorCategoryService.findAll();
		model.addAttribute("doctorMechanismRelation", doctorMechanismRelation);
		model.addAttribute("doctorMechanismRelationAudits", DoctorMechanismRelation.Audit.values());
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("doctorCategorys", doctorCategorys);
		return "/mechanism/doctor/view";
	}

	/**
	 * 新增员工
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		model.addAttribute("genders", Gender.values());
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("doctorCategorys", doctorCategoryService.findAll());
		return "/mechanism/doctor/add";
	}

	/**
	 * 新增保存员工
	 * @param doctorId
	 * @param patientMemberId
	 * @param visitDate
	 * @param message
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(String username,String password,String name,Gender gender,Long doctorCategoryId, Long mechanismRoleId, @RequestParam(defaultValue = "false") Boolean isAbout, BigDecimal dayWorkTarget,BigDecimal percentage, ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
	    Doctor doctor = doctorService.findByUsername(username);
	    DoctorCategory doctorCategory = doctorCategoryService.find(doctorCategoryId);
	    if (doctor==null) {
    	 	doctor = new Doctor();
    	 	doctor.setUsername(username);
	 	    doctor.setMobile(username);
	 	    doctor.setPassword(DigestUtils.md5Hex(password));
	 	    doctor.setGender(gender);
	 	    doctor.setIsDeleted(false);
	 	    doctor.setIsLocked(false);
	 	    doctor.setIsReal(false);
	 	    doctor.setName(name);
	 	    doctor.setPinyin(PinyinUtil.getPingYin(name));
	 	    doctor.setSkillSort(0.0);
	 	    doctor.setScoreSort(0.0);
	 	    doctor.setServerSort(0.0);
	 	    doctor.setCommunicateSort(0.0);
	 	    doctor.setSecond(0);
	 	    doctor.setMechanism(mechanism);
	 	    doctor.setPoint(0l);
	 	    doctor.setDoctorCategory(doctorCategory);
	 	    doctor.setPersonageAccount(new BigDecimal(0));
	 	    doctor.setOrganizationAccount(new BigDecimal(0));
	 	    doctor.setStatus(Status.allow);
	 	    doctorService.save(doctor);
		}
	   
	    
	    DoctorCategoryRelation doctorCategoryRelation = new DoctorCategoryRelation();
	    doctorCategoryRelation.setAudit(net.shenzhou.entity.DoctorMechanismRelation.Audit.pending);
	    doctorCategoryRelation.setDefaultDoctorCategory(true);
	    doctorCategoryRelation.setDoctor(doctor);
	    doctorCategoryRelation.setDoctorCategory(doctorCategory);
	    doctorCategoryRelation.setIsDeleted(false);
	    doctorCategoryRelationService.save(doctorCategoryRelation);
	    
	    MechanismRole mechanismRole = mechanismRoleService.find(mechanismRoleId);
	    DoctorMechanismRelation doctorMechanismRelation = new DoctorMechanismRelation();
	    doctorMechanismRelation.setDoctor(doctor);
	    doctorMechanismRelation.setMechanism(mechanism);
	    doctorMechanismRelation.setAudit(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed);
	    doctorMechanismRelation.setIsEnabled(true);
	    doctorMechanismRelation.setIsSystem(false);
	    doctorMechanismRelation.setDefaultMechanism(true);
	    doctorMechanismRelation.setIsAbout(isAbout);
	    doctorMechanismRelation.setDoctorCategory(doctorCategory);
	    WorkTarget workTarget = new WorkTarget();
	    if (isAbout) {
	    	workTarget.setDayWorkTarget(dayWorkTarget);
	    	workTarget.setPercentage(percentage);
		}
	    doctorMechanismRelation.setWorkTarget(workTarget);
	    doctorMechanismRelation.getMechanismroles().add(mechanismRole);
	    doctorMechanismRelationService.save(doctorMechanismRelation);
		return "redirect:list.jhtml";
		
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Integer pageNumber,Integer pageSize,String nameOrphone,Long doctorCategorieId ,Gender gender, DoctorMechanismRelation.Audit audit,Long serverProjectCategorieId, ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		List<ServerProjectCategory> serverProjectCategories = serverProjectCategoryService.getServerProjectCategory(mechanism);
		List<DoctorCategory> doctorCategorys = doctorCategoryService.findAll();
		DoctorCategory doctorCategory = doctorCategoryService.find(doctorCategorieId);
		ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(serverProjectCategorieId);
		Pageable pageable = new Pageable();
		if (pageNumber!=null&&pageSize!=null) {
			pageable.setPageNumber(pageNumber);
			pageable.setPageSize(pageSize);
		}
		Map<String,Object> query_map = new HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrphone", nameOrphone);
		query_map.put("doctorCategory", doctorCategory);
		query_map.put("gender", gender);
		query_map.put("audit", audit);
		query_map.put("serverProjectCategory", serverProjectCategory);
		query_map.put("pageable", pageable);
		
		model.addAttribute("nameOrphone", nameOrphone);
		model.addAttribute("doctorCategory", doctorCategory);
		model.addAttribute("gender", gender);
		model.addAttribute("serverProjectCategorie", serverProjectCategory);
		
		model.addAttribute("doctorCategorys", doctorCategorys);
		model.addAttribute("serverProjectCategories", serverProjectCategories);
		model.addAttribute("genders", Gender.values());
		model.addAttribute("doctorMechanismRelationAudits", DoctorMechanismRelation.Audit.values());
		Page<DoctorMechanismRelation> page = doctorMechanismRelationService.getPageMechanismDoctors(query_map);
		model.addAttribute("page", page);
		model.addAttribute("mechanism", mechanism);
		return "/mechanism/doctor/list";
	}

	
	/**
	 * 导出医生技师
	 * @param pageable
	 * @param nameOrphone
	 * @param doctorCategorieId
	 * @param gender
	 * @param serverProjectCategorieId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/downloadList", method = RequestMethod.GET)
	public ModelAndView downloadList(Pageable pageable,String nameOrphone,Long doctorCategorieId ,Gender gender,Long serverProjectCategorieId, ModelMap model) {

//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
//		List<ServerProjectCategory> serverProjectCategories = serverProjectCategoryService.getServerProjectCategory(mechanism);
//		List<DoctorCategory> doctorCategorys = doctorCategoryService.findAll();
		DoctorCategory doctorCategory = doctorCategoryService.find(doctorCategorieId);
		ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(serverProjectCategorieId);
		Map<String,Object> query_map = new HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrphone", nameOrphone);
		query_map.put("doctorCategory", doctorCategory);
		query_map.put("gender", gender);
		query_map.put("serverProjectCategory", serverProjectCategory);
		query_map.put("pageable", pageable);
		List<DoctorMechanismRelation> doctorMechanismRelations = doctorMechanismRelationService.downloadList(query_map);
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for (DoctorMechanismRelation doctorMechanismRelation : doctorMechanismRelations) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("doctorName", doctorMechanismRelation.getDoctor().getName());
			data_map.put("doctorGender", message("Member.Gender."+doctorMechanismRelation.getDoctor().getGender()));
			data_map.put("doctorCatrgoryName",doctorMechanismRelation.getDoctorCategory()==null?"暂未设置":doctorMechanismRelation.getDoctorCategory().getTitle());
			data_map.put("doctorMechanismRoleName", doctorMechanismRelation.getMechanismRoleName());
			data_map.put("doctorMobile", doctorMechanismRelation.getDoctor().getMobile());
//			data_map.put("doctorSecond", doctorMechanismRelation.getDoctor().getSecond());
			data_map.put("doctorAudit", message("DoctorMechanismRelation.Audit."+doctorMechanismRelation.getAudit()));
//			data_map.put("doctorParentSize", doctorMechanismRelation.getDoctor().getParents().size());
			data_list.add(data_map);
		}
		String filename = "医生技师" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"姓名","性别","员工职级","员工角色","联系电话","状态"};//title
		String[] contents = new String []{"doctorName","doctorGender","doctorCatrgoryName","doctorMechanismRoleName","doctorMobile","doctorAudit"};//content
		
		String[] memos = new String [3];//memo
		memos[0] = "记录数" + ": " + data_list.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
	}
	
	
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				DoctorMechanismRelation doctorMechanismRelation = doctorMechanismRelationService.find(id);
				Doctor doctor = doctorMechanismRelation.getDoctor();
				doctorMechanismRelationService.delete(doctorMechanismRelation);
				List<DoctorMechanismRelation> list = doctor.getDoctorMechanismRelations(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed);
				if(list.size()<=0){
					doctor.setStatus(Status.noOperation);
					doctorService.update(doctor);
				}
//				doctorMechanismRelation.setIsDeleted(true);
//				doctorMechanismRelationService.update(doctorMechanismRelation);
			}
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 医生审核
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public @ResponseBody
	Message status( Long id,Long doctorCategoryId,String status,String statusExplain) {
		
//		Mechanism mechanism = mechanismService.getCurrent();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		DoctorCategory doctorCategory = doctorCategoryService.find(doctorCategoryId);
		DoctorMechanismRelation doctorMechanismRelation = doctorMechanismRelationService.find(id);
		doctorMechanismRelation.setAudit((net.shenzhou.entity.DoctorMechanismRelation.Audit.valueOf(status)));
		doctorMechanismRelation.setDoctorCategory(doctorCategory);
		doctorMechanismRelation.setStatusExplain(statusExplain);
		
		doctorMechanismRelationService.update(doctorMechanismRelation);
	    
	    Information information = new Information();
	    Doctor doctor = doctorMechanismRelation.getDoctor();
	    if(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed.equals(net.shenzhou.entity.DoctorMechanismRelation.Audit.valueOf(status))){
	    	information.setMessage(mechanism.getName()+"中心，将你身份审核通过!备注:"+statusExplain);
	    	doctor.setStatus(Status.allow);
	    	doctorService.update(doctor);
	    }else if(net.shenzhou.entity.DoctorMechanismRelation.Audit.fail.equals(net.shenzhou.entity.DoctorMechanismRelation.Audit.valueOf(status))){
	    	information.setMessage("你申请加入"+mechanism.getName()+"中心，未审核通过，原因:"+statusExplain);
	    }
	    if (doctor.getDevice_tokens()!=null) {
	    	Boolean fals = PushUtil.getFals(doctor.getDevice_tokens());
		    String appkey = fals?PushUtil.android_Ys_AppKey:PushUtil.ios_Ys_AppKey;//appkey
			String secret = fals?PushUtil.android_Ys_App_Master_Secret:PushUtil.ios_Ys_App_Master_Secret;//secret
			String device_tokens = doctor.getDevice_tokens(); //device_tokens 设备唯一识别号
			String ticker = "审核结果";// 通知栏提示文字
			String title = "个人身份审核结果";// 必填 通知标题
			String text = "";// 必填 通知文字描述 
			String after_open = "go_activity";//必填 值为"go_app：打开应用", "go_url: 跳转到URL", "go_activity: 打开特定的activity", "go_custom: 用户自定义内容。"
			String url = "";    // 可选 当"after_open"为"go_url"时，必填。 通知栏点击后跳转的URL，要求以http或者https开头  
			String activity = "NotificationActivity";     // 可选 当"after_open"为"go_activity"时，必填。 通知栏点击后打开的Activity 
			String custom = "{}";// 可选 display_type=message, 或者display_type=notification且"after_open"为"go_custom"时，该字段必填。用户自定义内容, 可以为字符串或者JSON格式
			String extra =  "{}";//用户自定义 extra
			statusExplain = statusExplain==null?"无":statusExplain;
		    if(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed.equals(net.shenzhou.entity.DoctorMechanismRelation.Audit.valueOf(status))){
		    	text = mechanism.getName()+"中心，将你身份审核通过!备注:"+statusExplain;
		    }else if(net.shenzhou.entity.DoctorMechanismRelation.Audit.fail.equals(net.shenzhou.entity.DoctorMechanismRelation.Audit.valueOf(status))){
		    	text = "你申请加入"+mechanism.getName()+"中心，未审核通过，原因:"+statusExplain;
		    }
		    Map<String ,Object> map = new HashMap<String, Object>();
		    //android 所需参数
		    map.put("appkey", appkey);
		    map.put("secret", secret);
		    map.put("device_tokens", device_tokens);
		    map.put("ticker", ticker);
		    map.put("title", title);
		    map.put("text", text);
		    map.put("after_open", after_open);
		    map.put("url", url);
		    map.put("activity", activity);
		    map.put("custom", custom);
		    map.put("extra", extra);
		    
		    //ios 所需参数
		    map.put("alias_type","");//可选当type=customizedcast时，必填，alias的类型, alias_type可由开发者自定义,开发者在SDK中 调用setAlias(alias, alias_type)时所设置的alias_type
			map.put("alias", "");// 可选 当type=customizedcast时, 开发者填写自己的alias。  要求不超过50个alias,多个alias以英文逗号间隔。 在SDK中调用setAlias(alias, alias_type)时所设置的alias
			map.put("file_id", ""); // 可选 当type=filecast时，file内容为多条device_token,  device_token以回车符分隔当type=customizedcast时，file内容为多条alias， alias以回车符分隔，注意同一个文件内的alias所对应的alias_type必须和接口参数alias_type一致。注意，使用文件播前需要先调用文件上传接口获取file_id,  具体请参照"2.4文件上传接口"
			map.put("alert", text);  // 必填 iOS10 新增带title，subtile的alert格式如下"alert":{//  "title":"title","subtitle":"subtitle", "body":"body",}                   
			map.put("badge", "1"); // 可选        
			map.put("sound", "default"); // 可选        
			map.put("content_available", ""); // 可选        
			map.put("category", ""); // 可选        
			map.put("max_send_num", "");// 可选 发送限速，每秒发送的最大条数。开发者发送的消息如果有请求自己服务器的资源，可以考虑此参数。
			map.put("apns_collapse_id", "");//可选，iOS10开始生效。
			map.put("description", ""); // 可选 发送消息描述，建议填写。
			map.put("iOSType", "doctor"); //1.project 2.docotr
			map.put("iOSValue", doctor.getId());//iOSType 填写时  可填写
		    
			try {
				Map<String, Object> map_data = fals?PushUtil.androidSend(map):PushUtil.iosSend(map);//这一步 判断android 或者 ios 推送
				System.out.println("status:"+map_data.get("status")+",message:"+map_data.get("message"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
        
	    
		information.setInformationId(null);
		information.setHeadline("医生审核通知");
		information.setInformationType(InformationType.doctor);
		information.setState(StateType.unread);
		information.setDoctor(doctorMechanismRelation.getDoctor());
		information.setMember(null);
		information.setIsDeleted(false);
		information.setDisposeState(DisposeState.unDispose);
		information.setInformationType(InformationType.system);
		information.setUserType(UserType.doctor);
		informationService.save(information);
		
		return SUCCESS_MESSAGE;
	}
	
	
	/**
	 * 医生的项目
	 * @param pageable
	 * @param nameOrphone
	 * @param doctorCategorieId
	 * @param gender
	 * @param serverProjectCategorieId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/project", method = RequestMethod.GET)
	public String project(Pageable pageable,Long doctorId, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Doctor doctor = doctorService.find(doctorId);
		Map<String,Object> query_map = new HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("doctor", doctor);
		query_map.put("pageable", pageable);
		Page<Project> page = projectService.getDoctorProjects(query_map);
		model.addAttribute("audits", Audit.values());
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("doctor", doctor);
		model.addAttribute("page",page);
		return "/mechanism/doctor/project";
	}

	
	
	/**
	 * 回访信息
	 * @param pageable
	 * @param doctorId
	 * @param memberId
	 * @param nameOrMobile
	 * @param startCreateDate
	 * @param endCreateDate
	 * @param startVisitDate
	 * @param endVisitDate
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/doctor_visit_message_list", method = RequestMethod.GET)
	public String doctor_visit_message_list(Pageable pageable,Long doctorId,Date startCreateDate,Date endCreateDate,Date startVisitDate,Date endVisitDate , ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		pageable.setPageSize(5);
		Doctor doctor = doctorService.find(doctorId);
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("doctor", doctor);
		query_map.put("mechanism", mechanism);
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
		if (startVisitDate!=null) {
			calendar.setTime(startVisitDate);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			startVisitDate = calendar.getTime();
		}
		if (endVisitDate!=null) {
			calendar.setTime(endVisitDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			endVisitDate = calendar.getTime();
		}
		query_map.put("startVisitDate", startVisitDate);
		query_map.put("endVisitDate", endVisitDate);
		Page<VisitMessage> page = visitMessageService.getDoctorPage(query_map);
		model.addAttribute("page", page);
		model.addAttribute("doctor", doctor);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("startCreateDate", startCreateDate);
		model.addAttribute("endCreateDate", endCreateDate);
		model.addAttribute("startVisitDate", startVisitDate);
		model.addAttribute("endVisitDate", endVisitDate);
		return "/mechanism/doctor/doctor_visit_message_list";
	}
	
	
	/**
	 * 保存回访信息
	 * @param doctorId
	 * @param patientMemberId
	 * @param visitDate
	 * @param message
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save_visit_message", method = RequestMethod.POST)
	public String save_visit_message(Long doctorId,Long patientMemberId,Date visitDate,String message,String type, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member patientMember = memberService.find(patientMemberId);
		Doctor doctor = doctorService.find(doctorId);
		VisitMessage visitMessage = new VisitMessage();
		visitMessage.setVisitDate(visitDate);
		visitMessage.setDoctor(doctor);
		visitMessage.setPatient(patientMember);
		visitMessage.setMember(patientMember.getParent());
		visitMessage.setMechanism(mechanism);
		visitMessage.setMessage(message);
		visitMessage.setResultMessage(null);
		visitMessage.setIsDeleted(false);
		visitMessageService.save(visitMessage);
		
		return "redirect:doctor_visit_message_list.jhtml?doctorId="+doctorId;
		
	}
	
	
	/**
	 * 编辑回访信息页面
	 * @param visitMessageId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/doctor_visit_message_edit", method = RequestMethod.GET)
	public String member_visitMessage_edit(Long visitMessageId ,ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		VisitMessage visitMessage = visitMessageService.find(visitMessageId);
		model.addAttribute("doctor", visitMessage.getDoctor());
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("visitMessage", visitMessage);
		return "mechanism/doctor/doctor_visit_message_edit";
		
	}
	
	/**
	 * 编辑保存回访信息
	 * @param doctorId
	 * @param patientMemberId
	 * @param visitDate
	 * @param message
	 * @param type
	 * @param visitMessageId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update_visit_message", method = RequestMethod.POST)
	public String update_visit_message(Long doctorId,Long patientMemberId,Date visitDate,String message,String type,Long visitMessageId,ModelMap model) {
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
		
		return "redirect:doctor_visit_message_list.jhtml?doctorId="+doctorId;
		
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
	
	
	
	/**
	 * 评价信息
	 * @param pageable
	 * @param doctorId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/doctor_evaluate_list", method = RequestMethod.GET)
	public String doctor_evaluate_list(Pageable pageable,Long doctorId,Date startDate,Date endDate,Long projectId,String nameOrmobile, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		pageable.setPageSize(5);
		Doctor doctor = doctorService.find(doctorId);
		Project project = projectService.find(projectId);
		
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("doctor", doctor);
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
		query_map.put("project", project);
		query_map.put("nameOrmobile", nameOrmobile);
		query_map.put("mechanism", mechanism);
		query_map.put("pageable", pageable);
	
		Page<Evaluate> page = evaluateService.getDoctorEvaluate(query_map);
		model.addAttribute("page", page);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("nameOrmobile", nameOrmobile);
		model.addAttribute("doctor", doctor);
		model.addAttribute("projectId", projectId);
		model.addAttribute("mechanism", mechanism);

		return "/mechanism/doctor/doctor_evaluate_list";
	}
	
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete_doctor_evalueate", method = RequestMethod.POST)
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
	
	/**
	 * 预约管理
	 * @param pageable
	 * @param doctorId
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @param nameOrmobile
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/doctor_reserve", method = RequestMethod.GET)
	public String doctor_preabout(Pageable pageable,Long doctorId,Integer weekNum, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		pageable.setPageSize(5);
		Doctor doctor = doctorService.find(doctorId);
		
		List<Map<String,Object>> dateLists = new ArrayList<Map<String,Object>>();//获取本周的所有日期
		dateLists = DateUtil.getDates(weekNum==null?DateUtil.getWeekNum():weekNum);
		int startTime = Integer.valueOf(mechanism.getWorkDate().getStartTime().split(":")[0]);//取出机构的上班时间转成整型(时)
		int endTiemt = Integer.valueOf(mechanism.getWorkDate().getEndTime().split(":")[0]);//取出机构的下班时间转成整型(时)
		List<Map<String,Object>> workDates = new ArrayList<Map<String,Object>>();
		int count = 0;
		for (int i = startTime; i <= endTiemt; i++) {//////此处问题比较严重后期需要修改(待定)
			Map<String,Object> wordDate_map = new HashMap<String, Object>();
			wordDate_map.put("workDateTime", (startTime+count));
			count++;
			workDates.add(wordDate_map);
		}
		Map<String , Object> query_map = new HashMap<String, Object>();
		
		//如果点击今天weekNum就传空
		query_map.put("weekNum", weekNum==null?DateUtil.getWeekNum():weekNum);
		query_map.put("doctor", doctor);
		query_map.put("mechanism", mechanism);
		List<Order> weekOrders = orderService.getWeekOrder(query_map);//当前医师预约的订单(未服务的)
		List<Map<String,Object>> orders = new ArrayList<Map<String,Object>>();
		
		for (Order order : weekOrders) {
			Map<String,Object> map = new HashMap<String, Object>();
			System.out.println(order.getId());
			map.put("orderId", order.getId());
			map.put("orderSn", order.getSn());
			map.put("orderWorkDatItemStartTime", order.getWorkDayItem().getStartTime());
			map.put("orderWorkDay", order.getWorkDayItem().getWorkDay().getWorkDayDate());
			map.put("orderStartTime", Integer.valueOf(order.getWorkDayItem().getStartTime().split(":")[0]));
			orders.add(map);
		}
		
		model.addAttribute("dateLists", dateLists);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("doctor", doctor);
		model.addAttribute("weekNum", weekNum==null?DateUtil.getWeekNum():weekNum);
		model.addAttribute("workDates", workDates);
		model.addAttribute("orders", orders);
		return "/mechanism/doctor/doctor_reserve";
	}
	
	
	
	/**
	 * 机构端顶部修改资料
	 * @param name
	 * @param gender
	 * @param nation
	 * @param birth
	 * @param birthplaceId
	 * @param birthplaceAddress
	 * @param areaId
	 * @param address
	 * @param introduce
	 * @param file
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	Map<String ,Object> update(String name,Gender gender,String nation ,Date birth ,Long birthplaceId,
		String birthplaceAddress, Long areaId,String address,String introduce,@RequestParam(value = "logo_img", required = false)MultipartFile  file,ModelMap model) {
		Doctor doctorC = doctorService.getCurrent();
		Area birthplace = areaService.find(birthplaceId); 
		Area area = areaService.find(areaId);
		String logo = file.getSize()==0?doctorC.getLogo():fileService.uploadImg(FileType.image, file, Config.doctorLogoUrl,UUID.randomUUID().toString(), false);
		System.out.println(logo);
		doctorC.setName(name);
		doctorC.setGender(gender);
		doctorC.setNation(nation);
		doctorC.setBirth(birth);
		doctorC.setBirthplace(birthplace);
		doctorC.setBirthplaceAddress(birthplaceAddress);
		doctorC.setArea(area);
		doctorC.setAddress(address);
		doctorC.setIntroduce(introduce);
		doctorC.setLogo(logo);
		doctorService.update(doctorC);
		Map<String ,Object> map = new HashMap<String, Object>();
		map.put("status", "200");
		map.put("mesasge", "资料修改成功");
		map.put("data", doctorC);
		return map;
		
	}
	
	
	/**
	 * 医师预约管理 (预约)
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/order_view", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String order_view(Long[]ids) {
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for (Long id : ids) {
			if (id!=null) {
				Map<String,Object> data_map = new HashMap<String, Object>();
				Order order = orderService.find(id);
				data_map.put("patienMemberLogo", order.getPatientMember().getLogo());
				data_map.put("patienMemberName", order.getPatientMember().getName());
				data_map.put("patienMemberGender", order.getPatientMember().getGender());
				data_map.put("patienMemberBirth", order.getPatientMember().getBirth());
				data_map.put("patienMemberParentName", order.getPatientMember().getParent().getName());
				data_map.put("patienMemberParentMoble", order.getPatientMember().getParent().getMobile());
				data_map.put("doctorName", order.getDoctor().getName());
				data_map.put("doctorMobile", order.getDoctor().getMobile());
				data_map.put("porjectName", order.getProject().getName());
				data_map.put("startTime", order.getWorkDayItem().getStartTime());
				data_map.put("endTime", order.getWorkDayItem().getEndTime());
				data_map.put("projectMode", order.getMode());
				data_map.put("memo", order.getMemo());
				data_list.add(data_map);
			}
		}
		
		System.out.println(data_list.size()+"条数据");
			return JsonUtils.toJson(data_list);
		
	}
	
	
	
}

