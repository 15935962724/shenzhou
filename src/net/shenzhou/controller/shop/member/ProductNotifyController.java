/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.shop.member;

import javax.annotation.Resource;

import net.shenzhou.Message;
import net.shenzhou.Pageable;
import net.shenzhou.controller.shop.BaseController;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.ProductNotify;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.ProductNotifyService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 会员中心 - 到货通知
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("shopMemberProductNotifyController")
@RequestMapping("/member/product_notify")
public class ProductNotifyController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 10;

	@Resource(name = "productNotifyServiceImpl")
	ProductNotifyService productNotifyService;
	@Resource(name = "memberServiceImpl")
	MemberService memberService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Integer pageNumber, Model model) {
		Member member = memberService.getCurrent();
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("page", productNotifyService.findPage(member, null, null, null, pageable));
		return "/shop/member/product_notify/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete")
	public @ResponseBody
	Message delete(Long id) {
		ProductNotify productNotify = productNotifyService.find(id);
		if (productNotify == null) {
			return ERROR_MESSAGE;
		}
		Member member = memberService.getCurrent();
		if (!member.getProductNotifies().contains(productNotify)) {
			return ERROR_MESSAGE;
		}
		productNotifyService.delete(productNotify);
		return SUCCESS_MESSAGE;
	}

}