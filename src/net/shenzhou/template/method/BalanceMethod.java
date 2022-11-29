/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.template.method;

import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.entity.Balance;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberService;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;


@Component("balanceMethod")
public class BalanceMethod implements TemplateMethodModel {
	@Resource(name="mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name="memberServiceImpl")
	private MemberService memberService;
	
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null) {
			return getBalance(Long.valueOf(arguments.get(0).toString()),Long.valueOf(arguments.get(1).toString()));
		}
		return null;
	}

	
	private String getBalance(Long mechanismId,Long memberId) {
		if (mechanismId != null && memberId !=null) {
			Mechanism mechanism = mechanismService.find(mechanismId);
			Member member = memberService.find(memberId);
			Balance balance = member.getBalance(mechanism);
			if(balance!=null){
				return balance.getBalance().toString();
			}else{
				return "0";
			}
		   
		} else {
			return "0";
		}
	}

}