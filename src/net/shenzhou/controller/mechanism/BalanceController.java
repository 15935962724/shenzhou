/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.controller.mechanism;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.Message;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Balance;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.RechargeLog;
import net.shenzhou.entity.User;
import net.shenzhou.service.BalanceService;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.RechargeLogService;
import net.shenzhou.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
* @ClassName: BalanceController 
* @Description: TODO(余额操作) 
* @author wsr  
* @date 2017-8-16 15:51:07
 */
@Controller("mechanismBalanceController")
@RequestMapping("/mechanism/balance")
public class BalanceController extends BaseController {

	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
//	@Resource(name = "userServiceImpl")
//	private UserService userService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "rechargeLogServiceImpl")
	private RechargeLogService rechargeLogService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "balanceServiceImpl")
	private BalanceService balanceService;
	
	
	
	
	
	/**
	 * 充值页面
	 * @param memberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.GET)
	public String recharge(Long memberId,ModelMap model) {
		Member member = memberService.find(memberId);
		model.addAttribute("member", member);
		return "mechanism/balance/recharge";
	}
	

	/**
	 * 充值
	 * @param mobile
	 * @param name
	 * @param modifyBalance
	 * @param memo
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String balanceSave(Long memberId, BigDecimal modifyBalance, String memo,Integer pageNumber,Integer pageSize, HttpServletRequest request, RedirectAttributes redirectAttributes) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Member member = memberService.find(memberId);
	    if (member==null) {
	    	addFlashMessage(redirectAttributes,Message.error("用户不存在"));
	    	return "redirect:recharge.jhtml";
		}
		if (modifyBalance != null && modifyBalance.compareTo(new BigDecimal(0)) != 0) {
			
//			member.setBalance(member.getBalance().add(modifyBalance));  原先的用户余额注释掉
			
			//wsr 2018-3-19 15:57:56 start
			Balance balance = member.getBalance(mechanism);
			if (balance==null) {
				if (modifyBalance.compareTo(new BigDecimal(0)) < 0) {
					addFlashMessage(redirectAttributes,Message.success("扣款失败，扣款金额不能大于用户余额！"));
					return "redirect:/mechanism/deposit/list.jhtml";
				}
				balance = new Balance();
				balance.setBalance(modifyBalance);
				balance.setMechanism(mechanism);
				balance.setMember(member);
				balanceService.save(balance);
			}else{
				if (balance.getBalance().add(modifyBalance).compareTo(new BigDecimal(0)) < 0) {
					addFlashMessage(redirectAttributes,Message.success("扣款失败，扣款金额不能大于用户余额！"));
					return "redirect:/mechanism/deposit/list.jhtml";
				}
				balance.setBalance(balance.getBalance().add(modifyBalance));
				balanceService.update(balance);
			}
			//wsr 2018-3-19 15:57:56 end
			
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
			addFlashMessage(redirectAttributes,modifyBalance.compareTo(new BigDecimal(0)) > 0?Message.success("充值成功"):Message.success("扣款成功"));
			if (!(modifyBalance.compareTo(new BigDecimal(0)) > 0)){
				return "redirect:/mechanism/deposit/list.jhtml";
			}
		
		}else{
			addFlashMessage(redirectAttributes,Message.success("扣款失败，扣款金额不能大于用户余额！"));
			return "redirect:/mechanism/deposit/list.jhtml";
		}
		
		return "redirect:/mechanism/member/member_list.jhtml?pageNumber="+pageNumber+"&pageSize="+pageSize;
	}
	
	
}