/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.ExcelView;
import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Information;
import net.shenzhou.entity.Information.ClassifyType;
import net.shenzhou.entity.Information.DisposeState;
import net.shenzhou.entity.Information.InformationType;
import net.shenzhou.entity.Information.StateType;
import net.shenzhou.entity.Information.UserType;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Project.Audit;
import net.shenzhou.entity.ProjectItem;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.InformationService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.PushUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller - 机构项目
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("mechanismProjectController")
@RequestMapping("/mechanism/project")
public class ProjectController extends BaseController {

	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "informationServiceImpl")
	private InformationService informationService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	

	/**
	 * 项目列表
	 * @param pageable
	 * @param audit
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Integer pageSize,Integer pageNumber, Audit audit,String doctorName, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Pageable pageable = new Pageable();
		if (pageSize!=null&&pageNumber!=null) {
			pageable.setPageNumber(pageNumber);
			pageable.setPageSize(pageSize);
		}
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map <String,Object> map = new HashMap<String, Object>();
		map.put("mechanism", mechanism);
		map.put("pageable", pageable);
		map.put("audit", audit);
		map.put("doctorName", doctorName);
		model.addAttribute("doctorName",doctorName);
		model.addAttribute("audit",audit);
		model.addAttribute("audits", Audit.values());
		model.addAttribute("page", projectService.getPageProjects(map));
		return "/mechanism/project/list";
	} 
	
	/**
	 * 导出项目列表
	 * @param audit
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/downloadList", method = RequestMethod.GET)
	public ModelAndView downloadList(Audit audit,String doctorName,ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map <String,Object> query_map = new HashMap<String, Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("audit", audit);
		query_map.put("doctorName", doctorName);
		List<Project> projects = projectService.getDownloadList(query_map);
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for (Project project : projects) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("projectName", project.getName());
			data_map.put("serverProjectCategory", project.getServerProjectCategory().getName());
			StringBuffer projectItems = new StringBuffer();
			for (ProjectItem projectItem : project.getProjectItems()) {
				projectItems.append(message("Project.ServiceGroup."+projectItem.getServiceGroup())+"  "+message("Project.Mode."+projectItem.getMode())+ "  " +project.getPrice()+"元/"+project.getTime()+"分钟\r\n");
			}
			data_map.put("projectPrice", projectItems.toString());
			data_map.put("projectDoctorName", project.getDoctor().getName());
			data_map.put("projectAudit", message("Project.Audit."+project.getAudit()) );
			data_map.put("projectCreateDate", DateUtil.getDatetoString("yyyy-MM-dd",project.getCreateDate()));
			data_list.add(data_map);
		}
		
		String filename = "服务项目" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"项目名称","所属项目","价格(元/分钟)","服务医师","审核状态","创建时间"};//title
		String[] contents = new String []{"projectName","serverProjectCategory","projectPrice","projectDoctorName","projectAudit","projectCreateDate"};//content
		
		
		String[] memos = new String [3];//memo
		memos[0] = "记录数" + ": " + data_list.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
	} 
	

	/**
	 * 审核项目
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateAudit", method = RequestMethod.POST)
	public String status( Long id,String audit,String remarks,Integer pageSize,Integer pageNumber) {
		Mechanism mechanism = mechanismService.getCurrent();
	    Project project = projectService.find(id);
	    project.setAudit(Audit.valueOf(audit));
	    project.setRemarks(remarks);
	    projectService.update(project);
	    
	    //服务审核后给医生发送通知
	    Doctor doctor = project.getDoctor();
	    Information information = new Information();
	    if(Audit.succeed.equals(Audit.valueOf(audit))){
	    	remarks = remarks==null?"无":remarks;
	    	information.setMessage("您的服务<"+project.getName()+">已通过审核,备注："+remarks+"。");
	    }else if(Audit.fail.equals(Audit.valueOf(audit))){
	    	remarks = remarks==null?"无":remarks;
	    	information.setMessage("对不起,您的服务<"+project.getName()+">审核失败,请修改后重新申请。备注："+remarks+"。");
	    }
	    if (doctor.getDevice_tokens()!=null) {
	    	Boolean fals = PushUtil.getFals(doctor.getDevice_tokens());
		    String appkey = fals?PushUtil.android_Ys_AppKey:PushUtil.ios_Ys_AppKey;//appkey
			String secret = fals?PushUtil.android_Ys_App_Master_Secret:PushUtil.ios_Ys_App_Master_Secret;//secret
			String device_tokens = doctor.getDevice_tokens(); //device_tokens 设备唯一识别号
			String ticker = "审核结果";// 通知栏提示文字
			String title = "个人项目审核结果";// 必填 通知标题
			String text = "";// 必填 通知文字描述 
			String after_open = "go_activity";//必填 值为"go_app：打开应用", "go_url: 跳转到URL", "go_activity: 打开特定的activity", "go_custom: 用户自定义内容。"
			String url = "";    // 可选 当"after_open"为"go_url"时，必填。 通知栏点击后跳转的URL，要求以http或者https开头  
			String activity = "MyServiceDetailActivity";     // 可选 当"after_open"为"go_activity"时，必填。 通知栏点击后打开的Activity 
			String custom = "{}";// 可选 display_type=message, 或者display_type=notification且"after_open"为"go_custom"时，该字段必填。用户自定义内容, 可以为字符串或者JSON格式
			String extra =  "{\"projectId\":"+project.getId()+"}";//用户自定义 extra
			
		    if(Audit.succeed.equals(Audit.valueOf(audit))){
		    	remarks = remarks==null?"无":remarks;
		    	text = "你在"+mechanism.getName()+"中心发布的 "+project.getName()+"服务项目，审核通过。";
		    }else if(Audit.fail.equals(Audit.valueOf(audit))){
		    	remarks = remarks==null?"无":remarks;
		    	text = "你在"+mechanism.getName()+"中心发布的 "+project.getName()+"服务项目，审核未通过，原因:"+remarks;
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
			map.put("iOSType", "project"); //1.project 2.docotr
			map.put("iOSValue", project.getId());//iOSType 填写时  可填写
		    
		    
		    try {
		    	Map<String, Object> map_data = fals?PushUtil.androidSend(map):PushUtil.iosSend(map);//这一步 判断android 或者 ios 推送
				System.out.println("status:"+map_data.get("status")+",message:"+map_data.get("message"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		    
		}
		
		information.setInformationId(project.getId());
		information.setHeadline("服务审核通知");
		information.setInformationType(InformationType.system);
		information.setState(StateType.unread);
		information.setDoctor(doctor);
		information.setMember(null);
		information.setIsDeleted(false);
		information.setDisposeState(DisposeState.unDispose);
		information.setUserType(UserType.doctor);
		information.setClassifyType(ClassifyType.system);
		information.setMechanism(mechanism);
		informationService.save(information);
		
		return  "redirect:list.jhtml?pageNumber="+pageNumber+"&pageSize="+pageSize;
	}
	
	
	
	/**
	 * 项目统计
	 * @param pageable
	 * @param nameOrmoible
	 * @param createDate
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/charge", method = RequestMethod.GET)
	public String charge(String nameOrmoible,Date createDate, Date endDate, Long[] serverProjectCategoryIds,Pageable pageable, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		List<ServerProjectCategory> serverProjectCategories = new ArrayList<ServerProjectCategory>();
		if (serverProjectCategoryIds!=null) {
			for (Long serverProjectCategoryId : serverProjectCategoryIds) {
			    ServerProjectCategory serverProjectCategory =	serverProjectCategoryService.find(serverProjectCategoryId);
			    serverProjectCategories.add(serverProjectCategory);
			}
		}
		
		Map <String,Object> query_map = new HashMap<String, Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrmoible", nameOrmoible);
		
		List<Date> dates = null;
		if (createDate==null||endDate==null) {
			 dates = DateUtil.getAllTheDateOftheMonth(new Date());
		}else{
			dates =DateUtil.findDates(createDate,endDate);
		}
		
		List<ServerProjectCategory> serverCategories = serverProjectCategoryService.getServerProjectCategory(mechanism);
		
		Integer last = pageable.getPageSize()*pageable.getPageNumber()>dates.size()?dates.size():pageable.getPageSize()*pageable.getPageNumber();
		
		Page<Date> page = new Page<Date>(dates.subList((pageable.getPageNumber()-1)*pageable.getPageSize(), last), dates.size(), pageable);
		
//		model.addAttribute("dates",dates);
		model.addAttribute("page",page);
		
		model.addAttribute("serverCategories",serverCategories);
		model.addAttribute("nameOrmoible",nameOrmoible);
		model.addAttribute("createDate",createDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("serverProjectCategories",serverProjectCategories);
		return "/mechanism/project/charge";
	} 
	
	
	/**
	 * 项目统计导出
	 * @param nameOrmoible
	 * @param createDate
	 * @param serverProjectCategoryIds
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/downloadCharge", method = RequestMethod.GET)
	public ModelAndView downloadCharge(String nameOrmoible,Date createDate,  Long[] serverProjectCategoryIds ,ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		List<ServerProjectCategory> serverProjectCategories = new ArrayList<ServerProjectCategory>();
		if (serverProjectCategoryIds!=null) {
			for (Long serverProjectCategoryId : serverProjectCategoryIds) {
			    ServerProjectCategory serverProjectCategory =	serverProjectCategoryService.find(serverProjectCategoryId);
			    serverProjectCategories.add(serverProjectCategory);
			}
		}
		
		Map <String,Object> query_map = new HashMap<String, Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrmoible", nameOrmoible);
		
		List<Date> dates = DateUtil.getAllTheDateOftheMonth(createDate==null?new Date():createDate);
		List<ServerProjectCategory> serverCategories = serverProjectCategoryIds!=null?serverProjectCategories:serverProjectCategoryService.getServerProjectCategory(mechanism);
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for (Date date : dates) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("date", DateUtil.getDatetoString("yyyy-MM-dd", date));//日期
			BigDecimal countDataPrice = new BigDecimal(0);
			for (ServerProjectCategory serverProjectCategory : serverCategories) {
				query_map.put("date", date);
				List<Order> orders = orderService.getMonthAbout(query_map);
				BigDecimal count_price = new BigDecimal(0);//项目统计价格
				try {
					for (Order order : orders) {
						if (serverProjectCategory.equals(order.getProject().getServerProjectCategory())) {
							count_price = order.getPrice().add(count_price);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("项目统计出错:"+e.getMessage());
					
				}
				data_map.put("serverProjectCategory"+serverProjectCategory.getId(), count_price);
				countDataPrice = countDataPrice.add(count_price);
			}
			data_map.put("total", countDataPrice);//合计
			data_list.add(data_map);
		}
		
		String filename = "项目统计" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		StringBuffer title = new StringBuffer();
		title.append("日期").append(",");
		for (ServerProjectCategory serverProjectCategory : serverCategories) {
			title.append(serverProjectCategory.getName()+"(元)").append(",");
		}
		title.append("合计");
		String[] titles = title.toString().split(",");
		
		StringBuffer content = new StringBuffer();
		content.append("date").append(",");
		for (ServerProjectCategory serverProjectCategory : serverCategories) {
			content.append("serverProjectCategory"+serverProjectCategory.getId()).append(",");
		}
		content.append("total");
		String[] contents = content.toString().split(",") ;//content
		String[] memos = new String [4];//memo
		memos[0] = "记录数" + ": " + data_list.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
	} 
	
	
	

	
	



	
	
}










