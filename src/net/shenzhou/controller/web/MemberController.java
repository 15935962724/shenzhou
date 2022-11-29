/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.FileInfo.FileType;
import net.shenzhou.Setting;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Member.Calendar;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.MemberBill;
import net.shenzhou.entity.Verification;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.EvaluateService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.service.VisitMessageService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.HttpUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.SettingUtils;
import net.shenzhou.util.ShortMessageUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
/**
 * 登录
 * @date 2017-10-25 11:53:31
 * @author fl
 *
 */
@Controller("webMemberController")
@RequestMapping("/web/member")
public class MemberController extends BaseController {

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
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	
	/**
	 * 我的页面
	 */
	@RequestMapping(value = "/toMyself", method = RequestMethod.GET)
	public String list(ModelMap model) {
		Member member = memberService.getCurrent();
		if(member==null){
			return "redirect:/web/login/toLogin.jhtml";
		}
		model.addAttribute("member", member);
		return "/web/member/myself";
	}
	
	/**
	 * 账户设置跳转
	 */
	@RequestMapping(value = "/toSetAccount", method = RequestMethod.GET)
	public String toSetAccount(ModelMap model) {
		return "/web/member/setAccount";
	}
	
	/**
	 * 实名认证跳转
	 */
	@RequestMapping(value = "/toAuthentication", method = RequestMethod.GET)
	public String toAuthentication(ModelMap model) {
		return "/web/member/authentication";
	}

	/**
	 * 实名认证
	 */
	@RequestMapping(value = "/authentication", method = RequestMethod.POST)
	public String authentication(ModelMap model,String realName,String idCard) {
		Setting setting = SettingUtils.get();
		String param="key="+setting.getCardKey()+"&cardNo="+idCard+"&realName="+realName+"&information=";
		String returnStr = HttpUtil.post(setting.getCardUrl(), param);
		
		JSONObject obj = JSONObject.fromObject(returnStr);
        String key = obj.getString("error_code");
        String reason =  obj.getString("reason");
        
        if(!key.equals("0")){
        	model.addAttribute("errorMessage", "实名认证失败,请填写正确信息");
        	return "/web/member/authentication";
        }
        Member member = memberService.getCurrent();
        model.addAttribute("member", member);
        model.addAttribute("errorMessage", "实名认证成功");
        return "redirect:/web/member/toMyself.jhtml";
	}
	
	/**
	 * 设置支付密码跳转
	 */
	@RequestMapping(value = "/toPayPassWord", method = RequestMethod.GET)
	public String toPayPassWord(ModelMap model) {
		return "/web/member/payPassWord";
	}
	
	/**
	 * 我的钱包跳转
	 */
	@RequestMapping(value = "/toMyWallet", method = RequestMethod.GET)
	public String toMyWallet(ModelMap model) {
		return "/web/member/myWallet";
	}
	
	/**
	 * 用户账单跳转
	 */
	@RequestMapping(value = "/toMemberBill", method = RequestMethod.GET)
	public String toMemberBill(ModelMap model) {
		Member member = memberService.getCurrent();
		if(member==null){
			return "redirect:/web/login/toLogin.jhtml";
		}
		
		//获取全部月份(从用户建档到当前月)
		Date date = member.getCreateDate();
		List<String> string_list = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		for(int x = 0;;x++){
			System.out.println(DateUtil.dateFormat(date,x));
			System.out.println(format.format(new Date()));
			if(DateUtil.compare_date_month(DateUtil.dateFormat(new Date(),x), DateUtil.getDatetoString("yyyy-MM", date))==1){
				break;
			}
			string_list.add(DateUtil.dateFormat(new Date(),x));
		}
		
		model.addAttribute("stringList", string_list);
		return "/web/member/memberBill";
	}
	
	/**
	 * 用户账单数据
	 */
	@RequestMapping(value = "/memberBill", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String memberBill(ModelMap model,Integer page,String month) {
		
		Member member = memberService.getCurrent();
		int pageSize = Config.pageSize.intValue(); 
		
		Map<String,Object> data_map = new HashMap<String, Object>();
		List<MemberBill> memberBill_list = depositService.getMemberBillByMonth(month,member);
		List<MemberBill> memberBills_list = new ArrayList<MemberBill>();
		List<String> string_list = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		//获取全部月份(从用户建档到当前月)
		Date date = member.getCreateDate();
		
		for(int x = 0;;x++){
			System.out.println(DateUtil.dateFormat(date,x));
			System.out.println(format.format(new Date()));
			if(DateUtil.compare_date_month(DateUtil.dateFormat(new Date(),x), DateUtil.getDatetoString("yyyy-MM", date))==1){
				break;
			}
			string_list.add(DateUtil.dateFormat(new Date(),x));
		}
		
		
		//总充值
		BigDecimal income = new BigDecimal(0);
		//总支出
		BigDecimal expend = new BigDecimal(0);
		
		for(MemberBill memberBill : memberBill_list){
			BigDecimal incomes = new BigDecimal(memberBill.getTotalAddress());   
			BigDecimal expends = new BigDecimal(memberBill.getTotalRecharge());   
			income = income.add(incomes);
			expend = expend.add(expends);
		}
		
		//没有数据(一条没有)
		if(memberBill_list.size()<=0){
			data_map.put("balance", member.getBalance());
			data_map.put("date", string_list);
			data_map.put("memberBillList", memberBills_list);
			data_map.put("key", "500");
			data_map.put("income", income);
			data_map.put("expend", expend);
			
			return JsonUtils.toJson(data_map);
		}
		
		//总页数
		Integer pagecount = (memberBill_list.size()+pageSize-1)/pageSize;
		//页数
		Integer pagenumber = page>=pagecount?pagecount:page;
		
		if (page>pagecount) {//无更多数据
			data_map.put("balance", member.getBalance());
			data_map.put("date", string_list);
			data_map.put("memberBillList", memberBills_list);
			data_map.put("key", "400");
			data_map.put("income", income);
			data_map.put("expend", expend);
			
			return JsonUtils.toJson(data_map);
		}
		if(page==pagecount){
			data_map.put("memberBillList",memberBill_list.subList((pagenumber-1)*pageSize, memberBill_list.size()));
			data_map.put("sum", memberBill_list.size());
		}else{
			data_map.put("memberBillList", memberBill_list.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
			data_map.put("sum", pageSize*pagenumber);
		}
		
		
		//data_map.put("memberBillList", memberBill_list);
		data_map.put("balance", member.getBalance());
		data_map.put("date", string_list);
		data_map.put("key", "200");
		data_map.put("income", income);
		data_map.put("expend", expend);
		return JsonUtils.toJson(data_map);
	}
	
	/**
	 * 发送验证码
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/send_code", method = RequestMethod.GET)
	public @ResponseBody
	boolean sendCode(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		try {
			int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);// 生成验证码
			Member member = memberService.getCurrent();
			if(member==null){
				return false;
			}
			String phone = member.getPhone();
			if (verificationService.mobileExists(phone)) {
				SendSmsResponse data = new SendSmsResponse();
				data = ShortMessageUtil.seng_message(phone,mobile_code,"");
				if(data.getCode().equals("OK")){
					Verification verification = verificationService.findByMobile(phone);
					verification.setCode(String.valueOf(mobile_code));
					Date afterDate = DateUtils.addMinutes(new Date(), 10);
					verification.setValid(afterDate);//设置验证码有效期
					verificationService.update(verification);
					return true;
				}
				return false;
			} else{
				SendSmsResponse data = ShortMessageUtil.seng_message(phone,mobile_code,"");
				if(data.getCode().equals("OK")){
					Verification verification = new Verification();
					verification.setMobile(phone);
					verification.setCode(String.valueOf(mobile_code));
					Date afterDate = DateUtils.addMinutes(new Date(), 10);
					verification.setValid(afterDate);
					verificationService.save(verification);
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	/**
	 * 设置支付密码
	 */
	@RequestMapping(value = "/payPassWord", method = RequestMethod.POST)
	public String payPassWord(ModelMap model,String payPassword,String code) {
		Member member = memberService.getCurrent();
		if(member==null){
			return "redirect:/web/login/toLogin.jhtml";
		}
		Pattern pattern = Pattern.compile("[0-9]{6}");
		Matcher matcher = pattern.matcher(payPassword);
		boolean rs = matcher.find();
		if(rs==false){
			model.addAttribute("errorMessage", "支付密码只能输入0-9的6位数字");
			return "/web/member/payPassWord";
		}
		
		Verification verification = verificationService.findByMobile(member.getMobile());
		if(verification==null){
			model.addAttribute("errorMessage", "验证码输入有误");
			return "/web/member/payPassWord";
		}
		
		if(new Date().after(verification.getValid())){
			model.addAttribute("errorMessage", "验证码已失效");
			return "/web/member/payPassWord";
		}
		if(!code.equals(verification.getCode())){
			model.addAttribute("errorMessage", "验证码输入有误");
			return "/web/member/payPassWord";
		}	
		
		member.setPaymentPassword(DigestUtils.md5Hex(payPassword));
		memberService.update(member);
		return "redirect:/web/member/toMyself.jhtml";
	}
	
	
	/**
	 * 完善资料跳转
	 */
	@RequestMapping(value = "/toPerfect", method = RequestMethod.GET)
	public String toPerfect(ModelMap model) {
		List<Area> area_list = areaService.findRoots();
		model.addAttribute("top_area", area_list);
		return "/web/login/perfectData";
	}
	
	
	/**
	 * 获取地区
	 */
	@RequestMapping(value = "/getArea", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getArea(ModelMap model,Long areaId) {
		Set<Area> areas = areaService.find(areaId).getChildren();
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for(Area area : areas){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", area.getId());
			map.put("areaName", area.getName());
			data_list.add(map);
		}
		
		return JsonUtils.toJson(data_list);
	}
	
	/**
	 * 完善资料提交
	 */
	@RequestMapping(value = "/perfectData", method = RequestMethod.POST)
	public String perfectData(ModelMap model ,Long nowAreaId, Long regAreaId,String phone,String passWord,String realName,String sex,String nation,
			String birthDay,String calendar,String householdAddress,String address,String healthCard,String memberIphone,HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "upfile", required = false)MultipartFile  upfile) {
		
		String path = Config.memberLogoUrl;
	    String logo = fileService.uploadImg(FileType.image, upfile, path, UUID.randomUUID().toString(), false);
		
	    Gender gender = Gender.valueOf(sex);
	    
		Member member = new Member();
		member.setName(realName);
		member.setLogo(logo==null||logo.equals("")?Config.getMemberDefaultLogo(gender.toString()):logo);
		member.setGender(gender);
		member.setNation(nation);
		member.setBirth(DateUtil.getStringtoDate(birthDay+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		member.setCalendar(Calendar.valueOf(calendar));
		member.setMedicalInsuranceId(healthCard);
		member.setRelationship(null);
		member.setArea(areaService.find(regAreaId));
		member.setAreaAddress(householdAddress);
		member.setAddress(address);
		member.setNowArea(areaService.find(nowAreaId));
		member.setRegisterIp(request.getRemoteAddr());
		member.setUsername(phone);
		member.setMobile(phone);
		member.setPassword(DigestUtils.md5Hex(passWord));
		member.setEmail(null);
		member.setPoint(0L);
		member.setAmount(new BigDecimal(0));
		member.setBalance(new BigDecimal(0));
		member.setIsEnabled(true);
		member.setIsLocked(false);
		member.setLoginFailureCount(0);
		member.setLockedDate(null);
		member.setRegisterIp(request.getRemoteAddr());
		member.setLoginIp(request.getRemoteAddr());
		member.setLoginDate(new Date());
		member.setSafeKey(null);
		member.setMemberRank(memberRankService.findDefault());
		member.setFavoriteProducts(null);
		member.setIsDeleted(false);
		member.setIsDefault(false);
		member.setIsReal(false);
		member.setPhone(memberIphone);
		
		memberService.save(member);
		return "redirect:/web/login/toLogin.jhtml";
	}
	
	/**
	 * 就诊人管理跳转
	 */
	@RequestMapping(value = "/toPatientList", method = RequestMethod.GET)
	public String toPatientList(ModelMap model) {
		Member member = memberService.getCurrent();
		if(member==null){
			return "redirect:/web/login/toLogin.jhtml";
		}
		List<Member> patientMember_List = new ArrayList<Member>();
		for (Member member2 : member.getChildren()) {
			if (!member2.getIsDeleted()) {
				patientMember_List.add(member2);
			}
		}
		
		model.addAttribute("patientMember_List", patientMember_List);
		
		return "/web/member/patientList";
	}
	
	/**
	 * 设置默认患者
	 */
	@RequestMapping(value = "/memberIsDefault", method = RequestMethod.GET)
	public String memberIsDefault(ModelMap model,Long patientMemberId) {
		Member member = memberService.getCurrent();
		if(member==null){
			return "redirect:/web/login/toLogin.jhtml";
		}
		for (Member patientMember: member.getChildren()) {
			patientMember.setIsDefault(false);
			memberService.update(patientMember);
		}
		
		Member patientMember = memberService.find(patientMemberId);
		patientMember.setIsDefault(true);
		memberService.update(patientMember);
		return "redirect:/web/member/toPatientList.jhtml";
	}
	
	/**
	 * 新建患者跳转
	 */
	@RequestMapping(value = "/toCreationPatient", method = RequestMethod.GET)
	public String toCreationPatient(ModelMap model) {
		List<Area> area_list = areaService.findRoots();
		model.addAttribute("top_area", area_list);
		return "/web/member/creationPatient";
	}
	
	/**
	 * 新建患者
	 */
	@RequestMapping(value = "/creationPatient", method = RequestMethod.POST)
	public String creationPatient(ModelMap model ,Long nowAreaId, Long regAreaId,String realName,String sex,String nation,
			String birthDay,String relationship,String calendar,String householdAddress,String address,String healthCard,String memberIphone,HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "upfile", required = false)MultipartFile  upfile) {
		
		Member member = memberService.getCurrent();
		if(member==null){
			return "redirect:/web/login/toLogin.jhtml";
		}
		String path = Config.memberLogoUrl;
	    String logo = fileService.uploadImg(FileType.image, upfile, path, UUID.randomUUID().toString(), false);
	    Gender gender = Gender.valueOf(sex);
		
		Member patientMember = new Member();
		patientMember.setName(realName);
		patientMember.setLogo(logo==null||logo.equals("")?Config.getMemberDefaultLogo(gender.toString()):logo);
		patientMember.setGender(gender);
		patientMember.setNation(nation);
		patientMember.setBirth(DateUtil.getStringtoDate(birthDay+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		patientMember.setCalendar(Calendar.valueOf(calendar));
		
		//patientMember.setRelationship(relationship);
		
		patientMember.setMobile(memberIphone);
		patientMember.setMedicalInsuranceId(healthCard);
		patientMember.setArea(areaService.find(regAreaId));
		patientMember.setAreaAddress(householdAddress);
		patientMember.setAddress(address);
		patientMember.setNowArea(areaService.find(nowAreaId));
		patientMember.setParent(member);
		patientMember.setPoint(0l);
		patientMember.setAmount(new BigDecimal(0));
		patientMember.setBalance(new BigDecimal(0));
		patientMember.setIsEnabled(true);
		patientMember.setIsLocked(false);
		patientMember.setLoginFailureCount(0);
		patientMember.setRegisterIp(request.getRemoteAddr());
		patientMember.setMemberRank(memberRankService.find(1l));
		patientMember.setIsDefault(false);
		patientMember.setIsDeleted(false);
		patientMember.setIsReal(false);
//		patientMember.setHealthType(HealthType.health);// wsr 注释  2018-3-24 14:33:00
		patientMember.setPhone(memberIphone);
		memberService.save(patientMember);
		return "redirect:/web/member/toPatientList.jhtml";
	}
	
	/**
	 * 删除患者
	 */
	@RequestMapping(value = "/deletePatient", method = RequestMethod.GET)
	public String deletePatient(ModelMap model,Long patientId) {
		Member patientMember = memberService.find(patientId);
//		patientMember.setParent(null);
		patientMember.setIsDeleted(true);
		memberService.update(patientMember);
		return "redirect:/web/member/toPatientList.jhtml";
	}
	
	/**
	 * 编辑联系人资料跳转
	 */
	@RequestMapping(value = "/toEditMemberData", method = RequestMethod.GET)
	public String editMemberData(ModelMap model) {
		Member member = memberService.getCurrent();
		if(member==null){
			return "redirect:/web/login/toLogin.jhtml";
		}
		List<Area> area_list = areaService.findRoots();
		model.addAttribute("top_area", area_list);
		model.addAttribute("member", member);
		model.addAttribute("treePath", member.getArea().getTreePath());
		return "/web/member/editMemberData";
	}
	
	/**
	 * 编辑联系人资料
	 */
	@RequestMapping(value = "/editMemberData", method = RequestMethod.POST)
	public String editMemberData(ModelMap model ,Long nowAreaId, Long regAreaId,String realName,String sex,String nation,
			String birthDay,String calendar,String householdAddress,String img,String address,String healthCard,String memberIphone,HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "upfile", required = false)MultipartFile  upfile) {
		
		String path = Config.memberLogoUrl;
		
		String logo = upfile.getSize()==0?img:fileService.uploadImg(FileType.image, upfile, path, UUID.randomUUID().toString(), false);
		
	    Gender gender = Gender.valueOf(sex);
	    
		Member member = memberService.getCurrent();
		member.setName(realName);
		member.setLogo(logo==null||logo.equals("")?Config.getMemberDefaultLogo(gender.toString()):logo);
		member.setGender(gender);
		member.setNation(nation);
		member.setBirth(DateUtil.getStringtoDate(birthDay+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		member.setCalendar(Calendar.valueOf(calendar));
		member.setMedicalInsuranceId(healthCard);
		member.setAddress(address);
		
		memberService.update(member);
		return "redirect:/web/member/toEditMemberData.jhtml";
	}
	
	/**
	 * 编辑患者资料跳转
	 */
	@RequestMapping(value = "/toEditPatientData", method = RequestMethod.GET)
	public String editPatientData(ModelMap model,Long patientId) {
		
		Member member = memberService.find(patientId);
		
		List<Area> area_list = areaService.findRoots();
		model.addAttribute("top_area", area_list);
		model.addAttribute("member", member);
		model.addAttribute("treePath", member.getArea().getTreePath());
		return "/web/member/editPatientData";
	}
	
	
	/**
	 * 编辑患者资料
	 */
	@RequestMapping(value = "/editPatientData", method = RequestMethod.POST)
	public String editPatientData(ModelMap model ,Long patientId,Long nowAreaId, Long regAreaId,String realName,String sex,String nation,
			String birthDay,String calendar,String householdAddress,String img,String address,String healthCard,String memberIphone,HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "upfile", required = false)MultipartFile  upfile) {
		
		String path = Config.memberLogoUrl;
		
		String logo = upfile.getSize()==0?img:fileService.uploadImg(FileType.image, upfile, path, UUID.randomUUID().toString(), false);
		
	    Gender gender = Gender.valueOf(sex);
	    
		Member member = memberService.find(patientId);
		member.setName(realName);
		member.setLogo(logo==null||logo.equals("")?Config.getMemberDefaultLogo(gender.toString()):logo);
		member.setGender(gender);
		member.setNation(nation);
		member.setBirth(DateUtil.getStringtoDate(birthDay+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		member.setCalendar(Calendar.valueOf(calendar));
		member.setMedicalInsuranceId(healthCard);
		member.setAddress(address);
		
		memberService.update(member);
		return "redirect:/web/member/toPatientList.jhtml";
	}
	
	/**
	 * 我的医生跳转
	 */
	@RequestMapping(value = "/toMyDoctorList", method = RequestMethod.GET)
	public String toMyDoctorList(ModelMap model) {
		
		Member member = memberService.getCurrent();
		if(member==null){
			return "redirect:/web/login/toLogin.jhtml";
		}
		List<Doctor> doctor_list = member.getDoctors();
		
		model.addAttribute("member", member);
		model.addAttribute("doctors", doctor_list);
		return "/web/member/myDoctor";
	}
}