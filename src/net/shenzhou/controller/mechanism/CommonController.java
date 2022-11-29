/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.MechanismCategory;
import net.shenzhou.entity.MechanismRank;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.PatientMechanism;
import net.shenzhou.entity.PatientMechanism.HealthType;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.DoctorMechanismRelationService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismCategoryService;
import net.shenzhou.service.MechanismRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.MessageService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.PatientMechanismService;
import net.shenzhou.service.ProductService;
import net.shenzhou.service.RechargeLogService;
import net.shenzhou.service.UserService;
import net.shenzhou.service.WorkDayItemService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

/**
 * Controller - 共用
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("mechanismCommonController")
@RequestMapping("/mechanism/common")
public class CommonController implements ServletContextAware {

	@Value("${system.name}")
	private String systemName;
	@Value("${system.version}")
	private String systemVersion;
	@Value("${system.description}")
	private String systemDescription;
	@Value("${system.show_powered}")
	private Boolean systemShowPowered;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "messageServiceImpl")
	private MessageService messageService;
	@Resource(name = "mechanismCategoryServiceImpl")
	private MechanismCategoryService mechanismCategoryService;
	@Resource(name = "mechanismRankServiceImpl")
	private MechanismRankService mechanismRankService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "doctorMechanismRelationServiceImpl")
	private DoctorMechanismRelationService doctorMechanismRelationService;
	@Resource(name = "rechargeLogServiceImpl")
	private RechargeLogService rechargeLogService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "workDayItemServiceImpl")
	private WorkDayItemService workDayItemService;
	@Resource(name = "patientMechanismServiceImpl")
	private PatientMechanismService patientMechanismService;
	
	
	

	/** servletContext */
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 主页
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(ModelMap model) {
		Doctor doctor = doctorService.getCurrent();
		Mechanism mechanism =  doctor.getDefaultDoctorMechanismRelation().getMechanism();
		model.addAttribute("doctor", doctor);
		model.addAttribute("genders", Gender.values());
		model.addAttribute("mechanism", mechanism);
		return "/mechanism/common/main";
	}


	
	/**
	 * 首页
	 * @param model
	 * @param weekNum
	 * @param nameOrmoible
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap model,Pageable pageable, String date,String nameOrphone,Integer num) {
		Doctor doctor = doctorService.getCurrent();
		Mechanism mechanism =  doctor.getDefaultDoctorMechanismRelation().getMechanism();
		
		if (mechanism==null) {
			return "mechanism/mechanism/add";
		}
		Map<String, Object> query_map = new HashMap<String, Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrphone", nameOrphone);
		query_map.put("pageable", pageable);
		query_map.put("audit", Audit.succeed);
		query_map.put("isEnabled",true);
		query_map.put("createDate",new Date());
		
		Page<DoctorMechanismRelation> page  = doctorMechanismRelationService.getPageMechanismDoctors(query_map);
		
		Long toDayAboutCount = orderService.toDayAboutCount(query_map);//今日预约次数
//		Set<Member> toDayAboutMembers = new HashSet<Member>();//今日预约患者人数
		Double toDayServerCount = orderService.toDayServerCount(query_map);//今日服务
		Long futureServerCount = orderService.futureServerCount(query_map);//明日待服务次数
		
	
		//add wsr 2018-4-4 14:17:14
		query_map.put("healthType",HealthType.hang);
		List<PatientMechanism> patientMechanisms = patientMechanismService.downloadPatientHealthType(query_map);
		Integer hangCount = patientMechanisms.size(); //已流失人数
		
		Integer hangAddCount = patientMechanismService.daysPatientCount(query_map); //今日新增患者
		
		BigDecimal toDaySumRecharge=  rechargeLogService.sumRecharge(query_map);//今日充值总金额

		BigDecimal sumRecharge=  memberService.sumBalance(query_map);//该机构下所有用户的账户总金额
		
		BigDecimal sumConsumption =  orderService.sumConsumption(query_map);//机构总消费
		
		model.addAttribute("date", date==null||date.equals("")?DateUtil.getDatetoString("yyyy-MM-dd", new Date()):date);
		model.addAttribute("num", num);
		model.addAttribute("nameOrphone", nameOrphone);
	    model.addAttribute("toDayAboutCount", toDayAboutCount);
//		model.addAttribute("toDayAboutMembers", toDayAboutMembers);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("memberCount", memberService.count());
		model.addAttribute("hangCount", hangCount);
		model.addAttribute("toDayServerCount", toDayServerCount==null?0:toDayServerCount);
		model.addAttribute("hangAddCount", hangAddCount);
		model.addAttribute("futureServerCount", futureServerCount);
		model.addAttribute("toDaySumRecharge", toDaySumRecharge==null?new BigDecimal(0):toDaySumRecharge);
		model.addAttribute("sumRecharge", sumRecharge==null?new BigDecimal(0):sumRecharge);
		model.addAttribute("sumConsumption", sumConsumption==null?new BigDecimal(0):sumConsumption);
		model.addAttribute("patientCount", mechanism.getPatientMechanisms().size());
		model.addAttribute("page", page);
		model.addAttribute("doctor", doctor);
		
		return "/mechanism/common/index";
	}
	
	
	/**
	 * 首页预约人数订单详情
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/order_view", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String checkEmail(Long[]ids) {
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
	
	
	
	
	/**
	 * 地区
	 */
	@RequestMapping(value = "/area", method = RequestMethod.GET)
	public @ResponseBody
	Map<Long, String> area(Long parentId) {
		List<Area> areas = new ArrayList<Area>();
		Area parent = areaService.find(parentId);
		if (parent != null) {
			areas = new ArrayList<Area>(parent.getChildren());
		} else {
			areas = areaService.findRoots();
		}
		Map<Long, String> options = new HashMap<Long, String>();
		for (Area area : areas) {
			options.put(area.getId(), area.getName());
		}
		return options;
	}
   

	/**
	 * 机构类型
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/mechanismCategory", method = RequestMethod.GET)
	public @ResponseBody
	Map<Long, String> mechanismCategory(Long parentId) {
		List<MechanismCategory> mechanismCategorys = new ArrayList<MechanismCategory>();
		MechanismCategory parent = mechanismCategoryService.find(parentId);
		if (parent != null) {
			mechanismCategorys = new ArrayList<MechanismCategory>(parent.getChildren());
		} else {
			mechanismCategorys = mechanismCategoryService.findRoots();
		}
		Map<Long, String> options = new HashMap<Long, String>();
		for (MechanismCategory mechanismCategory : mechanismCategorys) {
			options.put(mechanismCategory.getId(), mechanismCategory.getName());
		}
		return options;
	}
	
	
	/**
	 * 机构级别
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/mechanismRank", method = RequestMethod.GET)
	public @ResponseBody
	Map<Long, String> mechanismRank(Long parentId) {
		List<MechanismRank> mechanismRanks = new ArrayList<MechanismRank>();
		MechanismRank parent = mechanismRankService.find(parentId);
		if (parent != null) {
			mechanismRanks = new ArrayList<MechanismRank>(parent.getChildren());
		} else {
			mechanismRanks = mechanismRankService.findRoots();
		}
		Map<Long, String> options = new HashMap<Long, String>();
		for (MechanismRank mechanismRank : mechanismRanks) {
			options.put(mechanismRank.getId(), mechanismRank.getName());
		}
		return options;
	}
	
	/**
	 * 验证码
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void image(String captchaId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (StringUtils.isEmpty(captchaId)) {
			captchaId = request.getSession().getId();
		}
		String pragma = new StringBuffer().append("yB").append("-").append("der").append("ewoP").reverse().toString();
		String value = new StringBuffer().append("ten").append(".").append("xxp").append("ohs").reverse().toString();
		response.addHeader(pragma, value);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();
			BufferedImage bufferedImage = captchaService.buildImage(captchaId);
			ImageIO.write(bufferedImage, "jpg", servletOutputStream);
			servletOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(servletOutputStream);
		}
	}

	/**
	 * 错误提示
	 */
	@RequestMapping("/error")
	public String error() {
		return "/mechanism/common/error";
	}

	/**
	 * 权限错误
	 */
	@RequestMapping("/unauthorized")
	public String unauthorized(HttpServletRequest request, HttpServletResponse response) {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
			response.addHeader("loginStatus", "unauthorized");
			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return "/mechanism/common/unauthorized";
	}

}