/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.shop.member;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.CommonAttributes;
import net.shenzhou.Setting;
import net.shenzhou.controller.shop.BaseController;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.MemberAttribute;
import net.shenzhou.entity.MemberAttribute.Type;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.MemberAttributeService;
import net.shenzhou.service.MemberService;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 会员中心 - 个人资料
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("shopMemberProfileController")
@RequestMapping("/member/profile")
public class ProfileController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	/**
	 * 检查E-mail是否唯一
	 */
	@RequestMapping(value = "/check_email", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		Member member = memberService.getCurrent();
		if (memberService.emailUnique(member.getEmail(), email)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(ModelMap model) {
		model.addAttribute("genders", Gender.values());
		model.addAttribute("memberAttributes", memberAttributeService.findList());
		return "shop/member/profile/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(String email, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!isValid(Member.class, "email", email)) {
			return ERROR_VIEW;
		}
		Setting setting = SettingUtils.get();
		Member member = memberService.getCurrent();
		if (!setting.getIsDuplicateEmail() && !memberService.emailUnique(member.getEmail(), email)) {
			return ERROR_VIEW;
		}
		member.setEmail(email);
		List<MemberAttribute> memberAttributes = memberAttributeService.findList();
		for (MemberAttribute memberAttribute : memberAttributes) {
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
		memberService.update(member);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:edit.jhtml";
	}

}