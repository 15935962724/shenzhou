/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.CommonAttributes;
import net.shenzhou.ExcelView;
import net.shenzhou.FileInfo.FileType;
import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.Setting;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.AssessReportImage;
import net.shenzhou.entity.Balance;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DrillContent;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.MemberAttribute;
import net.shenzhou.entity.MemberAttribute.Type;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.OrderItem;
import net.shenzhou.entity.PatientMechanism;
import net.shenzhou.entity.PatientMechanism.HealthType;
import net.shenzhou.entity.RechargeLog;
import net.shenzhou.entity.RecoveryPlan;
import net.shenzhou.entity.RecoveryPlan.CheckState;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.AssessReportService;
import net.shenzhou.service.BalanceService;
import net.shenzhou.service.BrandService;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.DrillContentService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberAttributeService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.PatientMechanismService;
import net.shenzhou.service.ProductCategoryService;
import net.shenzhou.service.RechargeLogService;
import net.shenzhou.service.RecoveryPlanService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.SpecificationService;
import net.shenzhou.service.TagService;
import net.shenzhou.service.UserService;
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
 * Controller - 会员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("mechanismMemberController")
@RequestMapping("/mechanism/member")
public class MemberController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	@Resource(name = "specificationServiceImpl")
	private SpecificationService specificationService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "assessReportServiceImpl")
	private AssessReportService assessReportService;
	
	@Resource(name = "recoveryPlanServiceImpl")
	private RecoveryPlanService recoveryPlanService;
	@Resource(name = "drillContentServiceImpl")
	private DrillContentService drillContentService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "rechargeLogServiceImpl")
	private RechargeLogService rechargeLogService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "balanceServiceImpl")
	private BalanceService balanceService;
	@Resource(name = "patientMechanismServiceImpl")
	private PatientMechanismService patientMechanismService ;
	
	
	/**
	 * 检查E-mail是否唯一
	 */
	@RequestMapping(value = "/check_email", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkEmail(String previousEmail, String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		if (memberService.emailUnique(previousEmail, email)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("genders", Gender.values());
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("memberAttributes", memberAttributeService.findList());
		model.addAttribute("member", memberService.find(id));
		return "/mechanism/member/edit";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Member member, Long memberRankId, Integer modifyPoint, BigDecimal modifyBalance, String depositMemo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		member.setMemberRank(memberRankService.find(memberRankId));
//		if (!isValid(member)) {
//			return ERROR_VIEW;
//		}
		Setting setting = SettingUtils.get();
		if (member.getPassword() != null && (member.getPassword().length() < setting.getPasswordMinLength() || member.getPassword().length() > setting.getPasswordMaxLength())) {
			return ERROR_VIEW;
		}
		Member pMember = memberService.find(member.getId());
		if (pMember == null) {
			return ERROR_VIEW;
		}
		if (!setting.getIsDuplicateEmail() && !memberService.emailUnique(pMember.getEmail(), member.getEmail())) {
			return ERROR_VIEW;
		}
		member.removeAttributeValue();
		for (MemberAttribute memberAttribute : memberAttributeService.findList()) {
			String parameter = request.getParameter("memberAttribute_" + memberAttribute.getId());
			if (memberAttribute.getType() == Type.name || memberAttribute.getType() == Type.address || memberAttribute.getType() == Type.zipCode || memberAttribute.getType() == Type.phone || memberAttribute.getType() == Type.mobile || memberAttribute.getType() == Type.text || memberAttribute.getType() == Type.select) {
				if (memberAttribute.getIsRequired() && StringUtils.isEmpty(parameter)) {
					return ERROR_VIEW;
				}
				member.setAttributeValue(memberAttribute, parameter);
			} else if (memberAttribute.getType() == Type.gender) {
				Gender gender = StringUtils.isNotEmpty(parameter) ? Gender.valueOf(parameter) : null;
				if (memberAttribute.getIsRequired() && gender == null) {
					return ERROR_VIEW;
				}
				member.setGender(gender);
			} else if (memberAttribute.getType() == Type.birth) {
				try {
					Date birth = StringUtils.isNotEmpty(parameter) ? DateUtils.parseDate(parameter, CommonAttributes.DATE_PATTERNS) : null;
					if (memberAttribute.getIsRequired() && birth == null) {
						return ERROR_VIEW;
					}
					member.setBirth(birth);
				} catch (ParseException e) {
					return ERROR_VIEW;
				}
			} else if (memberAttribute.getType() == Type.area) {
				Area area = StringUtils.isNotEmpty(parameter) ? areaService.find(Long.valueOf(parameter)) : null;
				if (area != null) {
					member.setArea(area);
				} else if (memberAttribute.getIsRequired()) {
					return ERROR_VIEW;
				}
			} else if (memberAttribute.getType() == Type.checkbox) {
				String[] parameterValues = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
				List<String> options = parameterValues != null ? Arrays.asList(parameterValues) : null;
				if (memberAttribute.getIsRequired() && (options == null || options.isEmpty())) {
					return ERROR_VIEW;
				}
				member.setAttributeValue(memberAttribute, options);
			}
		}
		if (StringUtils.isEmpty(member.getPassword())) {
			member.setPassword(pMember.getPassword());
		} else {
			member.setPassword(DigestUtils.md5Hex(member.getPassword()));
		}
		if (pMember.getIsLocked() && !member.getIsLocked()) {
			member.setLoginFailureCount(0);
			member.setLockedDate(null);
		} else {
			member.setIsLocked(pMember.getIsLocked());
			member.setLoginFailureCount(pMember.getLoginFailureCount());
			member.setLockedDate(pMember.getLockedDate());
		}
		member.setIsDefault(false);
		BeanUtils.copyProperties(member, pMember, new String[] { "username", "point", "amount", "balance", "registerIp", "loginIp", "loginDate", "safeKey", "cart", "orders", "deposits", "payments", "couponCodes", "receivers", "reviews", "consultations", "favoriteProducts", "productNotifies", "inMessages", "outMessages" ,"isDeleted","parent" });
		memberService.update(pMember, modifyPoint, modifyBalance, depositMemo, null);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	
	/**
	 * 充值
	 * @param member
	 * @param memberRankId
	 * @param modifyPoint
	 * @param modifyBalance
	 * @param depositMemo
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/balanceSave", method = RequestMethod.POST)
	public String balanceSave(String mobile, String name, BigDecimal modifyBalance, String memo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member member = memberService.findByUsername(mobile);
	    if (member==null) {
	    	addFlashMessage(redirectAttributes,Message.error("用户不存在"));
	    	return "redirect:recharge.jhtml";
		}
		if (!member.getName().equals(name)) {
			addFlashMessage(redirectAttributes, Message.error("手机号和名字不匹配"));
	    	return "redirect:recharge.jhtml";
		}
		if (modifyBalance != null && modifyBalance.compareTo(new BigDecimal(0)) != 0) {
//			member.setBalance(member.getBalance().add(modifyBalance));
			
			//wsr 2018-3-19 15:57:56 start
			Balance balance = member.getBalance(mechanism);
			if (balance==null) {
				if (modifyBalance.compareTo(new BigDecimal(0)) < 0) {
					addFlashMessage(redirectAttributes,Message.success("扣款失败，扣款金额不能大于用户余额！"));
					return "redirect:recharge.jhtml";
				}
				balance = new Balance();
				balance.setBalance(modifyBalance);
				balance.setMechanism(mechanism);
				balance.setMember(member);
				balanceService.save(balance);
			}else{
				if (balance.getBalance().add(modifyBalance).compareTo(new BigDecimal(0)) < 0) {
					addFlashMessage(redirectAttributes,Message.success("扣款失败，扣款金额不能大于用户余额！"));
					return "redirect:recharge.jhtml";
				}
				balance.setBalance(balance.getBalance().add(modifyBalance));
				balanceService.update(balance);
			}
			//wsr 2018-3-19 15:57:56 end
			
			if (!member.getMemberMechanisms().contains(mechanism)) {
				member.getMemberMechanisms().add(mechanism);
				memberService.update(member);
			}
			
			Deposit deposit = new Deposit();
			if (modifyBalance.compareTo(new BigDecimal(0)) > 0) {//判断是否充值还是扣钱
				deposit.setType( Deposit.Type.mechanismRecharge );
				deposit.setCredit(modifyBalance);
				deposit.setDebit(new BigDecimal(0));
			} else {
				deposit.setType(Deposit.Type.mechanismChargeback );
				deposit.setCredit(new BigDecimal(0));
				deposit.setDebit(modifyBalance);
			}
			deposit.setBalance(balance.getBalance());
			deposit.setOperator(doctorC.getUsername());
			deposit.setMemo(memo);
			deposit.setMember(member);
			deposit.setMechanism(mechanism);//后续添加  wsr 2018-3-19 15:56:27
			depositService.save(deposit);
			
			if (modifyBalance.compareTo(new BigDecimal(0)) > 0){//判断如果是充值金额大于0,就保存充值日志
				RechargeLog  rechargeLog = new RechargeLog();
				rechargeLog.setIsDeleted(false);
				rechargeLog.setOperator(doctorC.getName());
				rechargeLog.setMoney(modifyBalance);
				rechargeLog.setRemarks(memo);
				rechargeLog.setType(net.shenzhou.entity.Deposit.Type.mechanismRecharge);
				rechargeLog.setMember(member);
				rechargeLog.setMechanism(mechanism);
				rechargeLog.setMobile(doctorC.getUsername());
				rechargeLogService.save(rechargeLog);
			}
		}
		addFlashMessage(redirectAttributes,modifyBalance.compareTo(new BigDecimal(0)) > 0?Message.success("充值成功"):Message.success("扣款成功"));
		if (!mechanism.getMembers().contains(member)) {
			mechanism.getMembers().add(member);
			mechanismService.update(mechanism);
		}
		
		return "redirect:recharge.jhtml";
	}
	
	
	/**
	 * 充值页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.GET)
	public String recharge( ModelMap model) {
        System.out.println("进入充值页面");	
		return "/mechanism/member/recharge";
	}

	
	
	
	/**
	 * 查看
	 */
	@RequestMapping(value = "/member_view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		model.addAttribute("member", memberService.find(id));
		return "/mechanism/member/member_view";
	}


	
	/**
	 * 添加诊评报告 和  康复计划
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addassessReport", method = RequestMethod.GET)
	public String addassessReport(ModelMap model ,Long patientMemberId) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member patientMember = memberService.find(patientMemberId);
		
		model.addAttribute("patientMember", patientMember);
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("tags", tagService.findList(net.shenzhou.entity.Tag.Type.product));
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("specifications", specificationService.findAll());
		
		model.addAttribute("serverProjectCategorys", serverProjectCategoryService.getServerProjectCategory(mechanism));
		model.addAttribute("memberAttributes", memberAttributeService.findList());
		return "/mechanism/member/addassessReport";
	}
	

	
	
	/**
	 * 患者列表
	 * @param pageable
	 * @param nameOrmobile
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/patient_list", method = RequestMethod.GET)
	public String patient_list(Pageable pageable,HealthType healthType, String nameOrmobile,Boolean isDeleted, ModelMap model) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String ,Object> query_map = new  HashMap<String,Object>();
		query_map.put("pageable", pageable);
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrmobile", nameOrmobile);
		query_map.put("healthType", healthType);
		query_map.put("isDeleted", isDeleted);
//		model.addAttribute("page", memberService.getPatientLists(query_map));//原先的患者列表
		
		model.addAttribute("page", patientMechanismService.getPatientMechanisms(query_map));//add wsr 2018-3-24 14:15:47
		model.addAttribute("nameOrmobile", nameOrmobile);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("healthTypes",HealthType.values());
		model.addAttribute("healthtype",healthType);
		model.addAttribute("isDeleted",isDeleted);
		
		model.addAttribute("serverProjectCategorys", serverProjectCategoryService.getServerProjectCategory(mechanism));
		return "/mechanism/member/patient_list";
	}
	
	
	/**
	 * 导出患者信息(患者列表)
	 * @param pageable
	 * @param healthType
	 * @param nameOrmobile
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/downloadPatientList", method = RequestMethod.GET)
	public ModelAndView downloadPatientList(Pageable pageable,HealthType healthType, Boolean isDeleted,String nameOrmobile, ModelMap model) throws Exception {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String ,Object> query_map = new  HashMap<String,Object>();
		query_map.put("pageable", pageable);
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrmobile", nameOrmobile);
		query_map.put("healthType", healthType);
		query_map.put("isDeleted", isDeleted);
//		List<Member> patients = memberService.downloadPatientList(query_map);
		//add wsr 2018-3-26 17:31:38
		List<PatientMechanism> patientMechanisms = patientMechanismService.downloadPatientHealthType(query_map);
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for (PatientMechanism patientMechanism : patientMechanisms) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			Member patient = patientMechanism.getPatient();
			data_map.put("name", patient.getName());
			data_map.put("gender",message("Member.Gender."+patient.getGender()));
			data_map.put("age",DateUtil.getAge(patient.getBirth())+"周岁");
			data_map.put("nation",patient.getNation());
			data_map.put("birth",DateUtil.getDatetoString("yyyy年MM月dd日", patient.getBirth()));
			data_map.put("healthType", message("PatientMechanism.HealthType."+patient.getPatientMechanism(mechanism).getHealthType()));
			Order order = orderService.getPatientMemberOldOrder(patient);
		   	data_map.put("orderCreateDate", order==null?"-":DateUtil.getDatetoString("yyyy年MM月dd日", order.getCreateDate()));
			data_map.put("mobile", patient.getMobile());
			data_map.put("memberName", patient.getParent().getName());
			data_map.put("memberGender",message("Member.Gender."+patient.getParent().getGender()));
			data_map.put("memberMobile", patient.getParent().getMobile());
			String areaAddress = patient.getAreaAddress()==null?"":patient.getAreaAddress();
			data_map.put("areaFullName", patient.getArea()!=null?patient.getArea().getFullName()+areaAddress:areaAddress);
			data_list.add(data_map);
		}
		
		String filename = "患者信息" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"姓名","性别","年龄","民族","出生日期","状态","入院时间","联系电话","联系人","联系人性别","联系人电话","户籍地址"};//title
		String[] contents = new String []{"name","gender","age","nation","birth","healthType","orderCreateDate","mobile","memberName","memberGender","memberMobile","areaFullName"};//content
		
		String[] memos = new String [3];//memo
		memos[0] = "记录数" + ": " + data_list.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
	}
	

	/**
	 * 用户列表
	 */
	@RequestMapping(value = "/member_list", method = RequestMethod.GET)
	public String list(Pageable pageable,String nameOrmobile,String type, ModelMap model,Integer pageNumber,Integer pageSize) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		if(pageNumber != null && pageSize != null){
			pageable.setPageNumber(pageNumber);
			pageable.setPageSize(pageSize);
		}
		
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String ,Object> query_map = new  HashMap<String,Object>();
		query_map.put("pageable", pageable);
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrmobile", nameOrmobile);
		query_map.put("type", type);
		
		model.addAttribute("page", memberService.getMemberLists(query_map));
		model.addAttribute("nameOrmobile", nameOrmobile);
		model.addAttribute("pageable", pageable);
		model.addAttribute("type", type);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("patients", mechanism.getPatients());
		return "/mechanism/member/member_list";
	}

	
	/**
	 * 导出用户信息(用户列表)
	 * @param pageable
	 * @param nameOrmobile
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/downloadMemberList", method = RequestMethod.GET)
	public ModelAndView downloadMemberList(Pageable pageable,String nameOrmobile,String type, ModelMap model) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String ,Object> query_map = new  HashMap<String,Object>();
		query_map.put("pageable", pageable);
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrmobile", nameOrmobile);
		query_map.put("type", type);
		List<Member> members = memberService.downloadMemberList(query_map);
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for (Member member : members) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("name", member.getName());
			data_map.put("gender",message("Member.Gender."+member.getGender()));
			data_map.put("birth", DateUtil.getDatetoString("yyyy年MM月",member.getBirth()));
			data_map.put("mobile", member.getMobile());
			StringBuffer patients = new StringBuffer();
			for (Member patient : member.getChildren()) {
				if (!patient.getIsDeleted()) {
					patients.append(patient.getName()).append(",");
				}
			}
			data_map.put("patients", patients);
			data_map.put("balance", member.getBalance().compareTo(new BigDecimal(0))==1?member.getBalance():0.00);
			data_map.put("loginDate", member.getLoginDate());
			data_list.add(data_map);
		}
		
		String filename = "用户信息" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"姓名","性别","出生日期","联系电话","名下患者","余额","最后登录时间"};//title
		String[] contents = new String []{"name","gender","birth","mobile","patients","balance","loginDate"};//content
		
		String[] memos = new String [3];//memo
		memos[0] = "记录数" + ": " + data_list.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
	
	}
	
	
	/**
	 * 我的患者列表
	 * @param pageable
	 * @param nameOrmobile
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member_patient", method = RequestMethod.GET)
	public String member_patient(Long memberId, ModelMap model) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member member = memberService.find(memberId);
		model.addAttribute("member", member);
		model.addAttribute("healthTypes", HealthType.values());
		return "/mechanism/member/member_patient";
	}
	
	/**
	 * 患者明细
	 * @param memberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/patient_view", method = RequestMethod.GET)
	public String patient_view(Long patientMemberId, ModelMap model) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member patientMember = memberService.find(patientMemberId);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("patientMember", patientMember);
		model.addAttribute("patientMechanism", patientMember.getPatientMechanism(mechanism));
		model.addAttribute("healthTypes", HealthType.values());
		model.addAttribute("colour", "1");
		return "/mechanism/member/patient_view";
	}
	
	
	
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Member member = memberService.find(id);
				if (member != null && member.getBalance().compareTo(new BigDecimal(0)) > 0) {
					return Message.error("admin.member.deleteExistDepositNotAllowed", member.getUsername());
				}
			}
			memberService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 添加诊评报告
	 * @param assessReport
	 * @param patientMemberId
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/saveassessreport", method = RequestMethod.POST)
	public String save(AssessReport assessReport,RecoveryPlan recoveryPlan,String drill,Long patientMemberId,Long auditDoctorId,Long doctorId,Long redoctorId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Doctor doctor = doctorService.find(doctorId);
		Doctor auditDoctor =  doctorService.find(auditDoctorId);
		Doctor redoctor =  doctorService.find(redoctorId);
		for (Iterator<AssessReportImage> iterator = assessReport.getAssessReportImages().iterator(); iterator.hasNext();) {
			AssessReportImage assessReportImage = iterator.next();
			if (assessReportImage == null || assessReportImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (assessReportImage.getFile() != null && !assessReportImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, assessReportImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:add.jhtml";
				}
			}
		}
		
		Member patientMember =  memberService.find(patientMemberId);
		List <DrillContent> drillContents = recoveryPlan.getDrillContent();
		
		String[] drillContnts = drill.split("&");
		for (int i = 0; i < drillContnts.length; i++) {
			System.out.println(drillContnts[i]);
		}
		
		
		recoveryPlan.setIsDeleted(false);
		recoveryPlan.setCheckState(CheckState.succeed);
		recoveryPlanService.save(recoveryPlan);
		
		for (String drillContent1 : drillContnts) {
			System.out.println(drillContent1);
			Long id = Long.valueOf(drillContent1.split(",")[0]);
			String count = drillContent1.split(",")[1];
			ServerProjectCategory serverProjectCategory = serverProjectCategoryService.find(id);
			DrillContent newdrillContent = new DrillContent();
			newdrillContent.setIsDeleted(false);
			newdrillContent.setServerProjectCategory(serverProjectCategory);
			newdrillContent.setTime(count);
			newdrillContent.setRecoveryplan(recoveryPlan);
			drillContentService.save(newdrillContent);
			drillContents.add(newdrillContent);
		}
		
		
		assessReport.setMember(patientMember);
		assessReport.setIsDeleted(false);
		assessReport.setCheckState(CheckState.succeed);
		assessReport.getRecoveryPlans().add(recoveryPlan);
		
		assessReport.setOrder(null);
		assessReport.setDoctor(doctor);
		assessReport.setAuditDoctor(auditDoctor);
		assessReport.setRedoctor(redoctor);
		
		
		assessReportService.save(assessReport);
		recoveryPlan.setAssessReport(assessReport);
		recoveryPlanService.update(recoveryPlan);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	
	
	/**
	 * 预约信息
	 * @param memberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member_reserve", method = RequestMethod.GET)
	public String reserve(Long memberId,Long patientMemberId,Integer weekNum, ModelMap model) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member member = memberService.find(memberId);
		Member patientMember = memberService.find(patientMemberId);
		Map<String , Object> query_map = new HashMap<String, Object>();
		//如果点击今天weekNum就传空
		query_map.put("weekNum", weekNum==null?DateUtil.getWeekNum():weekNum);
		query_map.put("mechanism", mechanism);
		query_map.put("member", member);
		query_map.put("patientMember", patientMember);
		List<Order> weekOrders = orderService.getWeekOrder(query_map);//当前用户预约的订单(未服务的)
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
		
		
		List<Map<String,Object>> dateLists = new ArrayList<Map<String,Object>>();//获取本周的所有日期
		dateLists = DateUtil.getDates(weekNum==null?DateUtil.getWeekNum():weekNum);
		int startTime = Integer.valueOf(mechanism.getWorkDate().getStartTime().split(":")[0]);//取出机构的上班时间转成整型(时)
		int endTiemt = Integer.valueOf(mechanism.getWorkDate().getEndTime().split(":")[0]);//取出机构的下班时间转成整型(时)
		List<Map<String,Object>> workDates = new ArrayList<Map<String,Object>>();
		int count = 0;
		for (int i = startTime; i <= endTiemt; i++) {
			Map<String,Object> wordDate_map = new HashMap<String, Object>();
			wordDate_map.put("workDateTime", (startTime+count));
			count++;
			workDates.add(wordDate_map);
		}
		
//		for (Map<String, Object> map : workDates) {
//			System.out.println("==="+map.get("workDateTime").toString());
//		}
		
		model.addAttribute("weekNum", weekNum==null?DateUtil.getWeekNum():weekNum);
		model.addAttribute("member", member==null?patientMember.getParent():member);
		model.addAttribute("patientMember", patientMember);
		model.addAttribute("memberId", memberId);
		model.addAttribute("patientMemberId", patientMemberId);
		model.addAttribute("weekOrders", weekOrders);
		model.addAttribute("dateLists", dateLists);
		model.addAttribute("orders", orders);
		model.addAttribute("workDates", workDates);
		
		return "/mechanism/member/member_reserve";
	}
	
	
	/**
	 * 患者端预约信息
	 * @param memberId
	 * @param patientMemberId
	 * @param weekNum
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/patient_reserve", method = RequestMethod.GET)
	public String patient_reserve(Long memberId,Long patientMemberId,Integer weekNum, ModelMap model) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member member = memberService.find(memberId);
		Member patientMember = memberService.find(patientMemberId);
		Map<String , Object> query_map = new HashMap<String, Object>();
		//如果点击今天weekNum就传空
		query_map.put("weekNum", weekNum==null?DateUtil.getWeekNum():weekNum);
		query_map.put("mechanism", mechanism);
		query_map.put("member", member);
		query_map.put("patientMember", patientMember);
		List<Order> weekOrders = orderService.getWeekOrder(query_map);//当前用户预约的订单(未服务的)
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
		
		
		List<Map<String,Object>> dateLists = new ArrayList<Map<String,Object>>();//获取本周的所有日期
		dateLists = DateUtil.getDates(weekNum==null?DateUtil.getWeekNum():weekNum);
		int startTime = Integer.valueOf(mechanism.getWorkDate().getStartTime().split(":")[0]);//取出机构的上班时间转成整型(时)
		int endTiemt = Integer.valueOf(mechanism.getWorkDate().getEndTime().split(":")[0]);//取出机构的下班时间转成整型(时)
		List<Map<String,Object>> workDates = new ArrayList<Map<String,Object>>();
		int count = 0;
		for (int i = startTime; i <= endTiemt; i++) {
			Map<String,Object> wordDate_map = new HashMap<String, Object>();
			wordDate_map.put("workDateTime", (startTime+count));
			count++;
			workDates.add(wordDate_map);
		}
		
		model.addAttribute("weekNum", weekNum==null?DateUtil.getWeekNum():weekNum);
		model.addAttribute("member", member==null?patientMember.getParent():member);
		model.addAttribute("patientMember", patientMember);
		model.addAttribute("memberId", memberId);
		model.addAttribute("patientMemberId", patientMemberId);
		model.addAttribute("weekOrders", weekOrders);
		model.addAttribute("dateLists", dateLists);
		model.addAttribute("orders", orders);
		model.addAttribute("workDates", workDates);
		
		return "/mechanism/member/patient_reserve";
	}
	
	/**
	 * 患者我的医师
	 * @param patientMemberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/patient_doctor", method = RequestMethod.GET)
	public String patient_doctor(Long patientMemberId,ModelMap model) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member patientMember = memberService.find(patientMemberId);
		Map<String , Object> query_map = new HashMap<String, Object>();
		model.addAttribute("patientMember", patientMember);
		return "/mechanism/member/patient_doctor";
	}
	
	/**
	 * 用户我的医师
	 * @param memberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member_doctor", method = RequestMethod.GET)
	public String member_doctor(Long memberId,ModelMap model) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member member = memberService.find(memberId);
		model.addAttribute("member", member);
		return "/mechanism/member/member_doctor";
	}
	
	
	/**
	 * 预约详情
	 * @param id
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
				data_map.put("porjectName", order.getOrderItems().get(0).getName());
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
	 * 获取充值用户
	 * @param nameOrMobile
	 * @return
	 */
	@RequestMapping(value = "/members", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String members(String nameOrMobile) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		Map<String ,Object> query_map = new  HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrmobile", nameOrMobile);
	    List<Member> memers =  memberService.getMembersByNameOrMobile(query_map);
	    for (Member member : memers) {
	    	Map<String,Object> data_map = new HashMap<String, Object>();
	    	data_map.put("id", member.getId());
	    	data_map.put("name", member.getName());
	    	data_map.put("mobile", member.getMobile());
	    	data_map.put("username", member.getUsername());
			data_list.add(data_map);
		}
		System.out.println(data_list.size()+"条数据");
		return JsonUtils.toJson(data_list);
		
	}
	
	
	/**
	 * 患者修改状态
	 * @param healthType
	 * @param patientId
	 * @return
	 */
	@RequestMapping(value = "/updateHealthType", method = RequestMethod.POST)
	public @ResponseBody
	Message updateHealthType( HealthType healthType, Long patientId,RedirectAttributes redirectAttributes) {
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		Member patientMember = memberService.find(patientId);
		
//		patientMember.setHealthType(healthType);
//		memberService.update(patientMember);
		
		PatientMechanism patientMechanism = patientMember.getPatientMechanism(mechanism);
		patientMechanism.setHealthType(healthType);
		patientMechanismService.update(patientMechanism);
//		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return SUCCESS_MESSAGE;
	}
	
	/**
	 * 患者状态
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/patient_healthType", method = RequestMethod.GET)
	public String patient_healthType(Pageable pageable,HealthType[] healthTypes, String nameOrmobile,Date startDate,Date endDate, ModelMap model) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String ,Object> query_map = new  HashMap<String,Object>();
		query_map.put("pageable", pageable);
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrmobile", nameOrmobile);
		query_map.put("healthTypes", healthTypes!=null?healthTypes:HealthType.values());
		//add wsr 2018-3-24 15:06:05
//		Page<Member> page = memberService.getPatientLists(query_map);
		Page<PatientMechanism> page = patientMechanismService.getPatientMechanisms(query_map);
		List<String> bfb_list = new ArrayList<String>();
		for (HealthType healthType : healthTypes!=null?healthTypes:HealthType.values()) {
			Map<String ,Object> query_healthType_map = new  HashMap<String,Object>();
			query_healthType_map.put("healthType", healthType);
			query_healthType_map.put("mechanism", mechanism);
			query_healthType_map.put("nameOrmobile", nameOrmobile);
			//add wsr 2018-3-24 15:06:05
//			Page<Member> healthType_page = memberService.getPatientLists(query_healthType_map);
			
			Page<PatientMechanism> healthType_page = patientMechanismService.getPatientMechanisms(query_healthType_map);
			NumberFormat numberFormat = NumberFormat.getInstance();
			// 设置精确到小数点后2位
			numberFormat.setMaximumFractionDigits(2);
			String result = numberFormat.format((float) healthType_page.getTotal() / (float) page.getTotal() * 100);
			bfb_list.add(result);
		}
		
		model.addAttribute("page", page);
		model.addAttribute("bfb_list", bfb_list);
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("healthTypes",healthTypes!=null?healthTypes:HealthType.values());
		model.addAttribute("types", HealthType.values());
		model.addAttribute("nameOrmobile", nameOrmobile);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "/mechanism/member/patient_healthType";
	}
	
	
	
	/**
	 * 导出患者状态
	 * @param pageable
	 * @param healthTypes
	 * @param nameOrmobile
	 * @param startDate
	 * @param endDate
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/downloadPatientHealthType", method = RequestMethod.GET)
	public ModelAndView downloadPatientHealthType(Pageable pageable,HealthType[] healthTypes, String nameOrmobile,Date startDate,Date endDate, ModelMap model) {
//		User user  = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String ,Object> query_map = new  HashMap<String,Object>();
		query_map.put("pageable", pageable);
		query_map.put("mechanism", mechanism);
		query_map.put("nameOrmobile", nameOrmobile);
		query_map.put("healthTypes", healthTypes!=null?healthTypes:HealthType.values());
		
//		List<Member> patientMembers = memberService.downloadPatientHealthType(query_map);
		List<PatientMechanism> patientMechanisms = patientMechanismService.downloadPatientHealthType(query_map);
		System.out.println(">>>>>>>>>>>>>"+patientMechanisms.size());
		List<Map<String,Object>>data_list = new ArrayList<Map<String,Object>>();
		for (PatientMechanism patientMechanism : patientMechanisms) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			Member patient = patientMechanism.getPatient();
			data_map.put("patientName", patient.getName());
			data_map.put("memberName", patient.getParent().getName());
			data_map.put("memberPhone", patient.getParent().getMobile());
			
			Order order = orderService.getPatientMemberOldOrder(patient);
			data_map.put("createDte",order==null?"-":DateUtil.getDatetoString("yyyy年MM月dd日", order.getCreateDate()));//最后消费时间
//			Member patientMember = memberService.find(patient.getId());
			query_map.put("patientMember", patient);
			List<Order> orders = orderService.getLastDateCoruseHourCountMoney(query_map);
			data_map.put("lastDate", DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss",orders.size()>0?orders.get(0).getCreateDate():new Date()));//最后消费时间
			Double count_coruse_Hour = 0.0;
			BigDecimal count_money = new BigDecimal(0);
			
		    for (Order order1 : orders) {
				for (OrderItem orderItem : order1.getOrderItems()) {
					count_coruse_Hour = count_coruse_Hour + orderItem.getQuantity();
				}
				count_money = count_money.add(order1.getAmountPaid());
			}
			data_map.put("countCoruseHour", count_coruse_Hour);//服务课时
			data_map.put("countMoney", count_money);//消费金额
			data_map.put("patientHealthType", message("PatientMechanism.HealthType."+patient.getPatientMechanism(mechanism).getHealthType()));//患者状态
			data_list.add(data_map);
		}
		
		String filename = "患者状态" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"患者姓名","联系人姓名","联系人电话","建档时间","最后消费时间","服务课时","消费金额(元)","患者状态"};//title
		String[] contents = new String []{"patientName","memberName","memberPhone","createDte","lastDate","countCoruseHour","countMoney","patientHealthType"};//content
		
		String[] memos = new String [3];//memo
		memos[0] = "记录数" + ": " + data_list.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
	}
	
	/**
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public @ResponseBody
	Map<String ,Object> query(Long id) {
		Member member = memberService.find(id);
		Map<String ,Object> data = new HashMap<String, Object>();
		if (member == null) {
			data.put("status", "400");
			data.put("message", "错误消息");
			data.put("data", "");
			return data;
		}
		data.put("status", "200");
		data.put("message", "成功");
		data.put("data", JsonUtils.toJson(member));
		return data;
		
	}
	
}