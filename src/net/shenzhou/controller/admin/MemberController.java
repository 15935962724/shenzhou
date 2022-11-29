/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.CommonAttributes;
import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.Setting;
import net.shenzhou.entity.Admin;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.BaseEntity.Save;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.MemberAttribute;
import net.shenzhou.entity.MemberAttribute.Type;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.RechargeLog;
import net.shenzhou.entity.VisitMessage;
import net.shenzhou.service.AdminService;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.MemberAttributeService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.RechargeLogService;
import net.shenzhou.service.VisitMessageService;
import net.shenzhou.util.DateUtil;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 会员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "visitMessageServiceImpl")
	private VisitMessageService visitMessageService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "rechargeLogServiceImpl")
	private RechargeLogService rechargeLogService;
	
	
	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id,Pageable pageable, ModelMap model) {
		pageable.setPageSize(1000);
		Member member = memberService.find(id);
		Map<String , Object> query_map = new HashMap<String, Object>();
		query_map.put("member", member);
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
		
		
		List<Map<String,Object>> dateDays = DateUtil.getDateDays();
		List<Map<String,Object>> dateLists = new ArrayList<Map<String,Object>>();//获取本周的所有日期
		dateLists = DateUtil.getDates(DateUtil.getWeekNum());
		int startTime = 0;
		int endTiemt = 23;
		List<Map<String,Object>> workDates = new ArrayList<Map<String,Object>>();
		int count = 0;
		for (int i = startTime; i <= endTiemt; i++) {//////此处问题比较严重后期需要修改(待定)
			Map<String,Object> wordDate_map = new HashMap<String, Object>();
			wordDate_map.put("workDateTime", (startTime+count));
			count++;
			workDates.add(wordDate_map);
		}
		
		
		
		query_map.put("pageable", pageable);
		Page<VisitMessage> visitMessagePage = visitMessageService.getPage(query_map);//回访信息
		
		model.addAttribute("visitMessagePage", visitMessagePage);
		model.addAttribute("orders", orders);
		model.addAttribute("workDates", workDates);
		model.addAttribute("dateDays", dateDays);
		model.addAttribute("dateLists", dateLists);
		model.addAttribute("member", member);
		return "/admin/member/view";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("genders", Gender.values());
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("memberAttributes", memberAttributeService.findList());
		return "/admin/member/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Member member, Long memberRankId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		member.setMemberRank(memberRankService.find(memberRankId));
		if (!isValid(member, Save.class)) {
			return ERROR_VIEW;
		}
		Setting setting = SettingUtils.get();
		if (member.getUsername().length() < setting.getUsernameMinLength() || member.getUsername().length() > setting.getUsernameMaxLength()) {
			return ERROR_VIEW;
		}
		if (member.getPassword().length() < setting.getPasswordMinLength() || member.getPassword().length() > setting.getPasswordMaxLength()) {
			return ERROR_VIEW;
		}
		if (memberService.usernameDisabled(member.getUsername()) || memberService.usernameExists(member.getUsername())) {
			return ERROR_VIEW;
		}
		if (!setting.getIsDuplicateEmail() && memberService.emailExists(member.getEmail())) {
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
		member.setUsername(member.getUsername().toLowerCase());
		member.setPassword(DigestUtils.md5Hex(member.getPassword()));
		member.setAmount(new BigDecimal(0));
		member.setIsLocked(false);
		member.setLoginFailureCount(0);
		member.setLockedDate(null);
		member.setRegisterIp(request.getRemoteAddr());
		member.setLoginIp(null);
		member.setLoginDate(null);
		member.setSafeKey(null);
		member.setCart(null);
		member.setOrders(null);
		member.setDeposits(null);
		member.setPayments(null);
		member.setCouponCodes(null);
		member.setReceivers(null);
		member.setReviews(null);
		member.setConsultations(null);
		member.setFavoriteProducts(null);
		member.setProductNotifies(null);
		member.setInMessages(null);
		member.setOutMessages(null);
		memberService.save(member, adminService.getCurrent());
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
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
		return "/admin/member/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Member member, Long memberRankId, Integer modifyPoint, BigDecimal modifyBalance, String depositMemo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		member.setMemberRank(memberRankService.find(memberRankId));
		member.setIsDefault(false);
		if (!isValid(member)) {
			return ERROR_VIEW;
		}
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
		BeanUtils.copyProperties(member, pMember, new String[] { "username", "point", "amount", "balance", "registerIp", "loginIp", "loginDate", "safeKey", "cart", "orders", "deposits", "payments", "couponCodes", "receivers", "reviews", "consultations", "favoriteProducts", "productNotifies", "inMessages", "outMessages","memberMechanisms","address","mobile","logo","calendar","nation","nowArea","phone","medicalInsuranceId","longitude","latitude","householdAddress","cardQQ","cardId","cardWX","remarks","paymentPassword","isReal","areaAddress","zipCode" });
		memberService.update(pMember, modifyPoint, modifyBalance, depositMemo, adminService.getCurrent());
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	
	/**
	 * 
	 * @param memberId
	 * @param modifyBalance
	 * @param memo
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/updateBalance", method = RequestMethod.POST)
	public String updateBalance(Long id, BigDecimal modifyBalance, String depositMemo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Admin admin = adminService.getCurrent();
		Member member = memberService.find(id);
	    if (member==null) {
	    	addFlashMessage(redirectAttributes,Message.error("用户不存在"));
	    	return "redirect:list.jhtml";
		}
		if (modifyBalance != null && modifyBalance.compareTo(new BigDecimal(0)) != 0 && member.getBalance().add(modifyBalance).compareTo(new BigDecimal(0)) >= 0) {
			member.setBalance(member.getBalance().add(modifyBalance));
			Deposit deposit = new Deposit();
			if (modifyBalance.compareTo(new BigDecimal(0)) > 0) {//判断是否充值还是扣钱
				deposit.setType( Deposit.Type.adminRecharge );
				deposit.setCredit(modifyBalance);
				deposit.setDebit(new BigDecimal(0));
			} else {
				deposit.setType(Deposit.Type.adminChargeback );
				deposit.setCredit(new BigDecimal(0));
				deposit.setDebit(modifyBalance);
			}
			deposit.setBalance(member.getBalance());
			deposit.setOperator(admin.getUsername());
			deposit.setMemo(depositMemo);
			deposit.setMember(member);
			depositService.save(deposit);
			
			if (modifyBalance.compareTo(new BigDecimal(0)) > 0){//判断如果是充值金额大于0,就保存充值日志
				RechargeLog  rechargeLog = new RechargeLog();
				rechargeLog.setIsDeleted(false);
				rechargeLog.setOperator(admin.getName());
				rechargeLog.setMoney(modifyBalance);
				rechargeLog.setRemarks(depositMemo);
				rechargeLog.setType(net.shenzhou.entity.Deposit.Type.adminRecharge);
				rechargeLog.setMember(member);
				rechargeLog.setMechanism(null);
				rechargeLog.setMobile(admin.getUsername());//add wsr 2018-3-27 11:14:07
				rechargeLogService.save(rechargeLog);
			}
			memberService.update(member);
			addFlashMessage(redirectAttributes,modifyBalance.compareTo(new BigDecimal(0)) > 0?Message.success("充值成功"):Message.success("扣款成功"));
			if (!(modifyBalance.compareTo(new BigDecimal(0)) > 0)){
				return "redirect:list.jhtml";
			}
		
		}else{
			addFlashMessage(redirectAttributes,Message.success("扣款失败，扣款金额不能大于用户余额！"));
			return "redirect:list.jhtml";
		}
		return "redirect:list.jhtml";
	}
	
	
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("pageable", pageable);
		Page<Member> page = memberService.getMembers(query_map);
		model.addAttribute("page", page);
		return "/admin/member/list";
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