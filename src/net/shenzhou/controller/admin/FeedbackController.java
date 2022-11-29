/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import javax.annotation.Resource;

import net.shenzhou.Message;
import net.shenzhou.Pageable;
import net.shenzhou.service.FeedbackService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 反馈
 * @date 2017-11-17 11:18:47
 * @author wsr
 *
 */
@Controller("adminFeedbackController")
@RequestMapping("/admin/feedback")
public class FeedbackController extends BaseController {
	
	@Resource(name = "feedbackServiceImpl")
	private FeedbackService feedbackService;
	
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", feedbackService.findPage(pageable));
		return "/admin/feedback/list";
	}

	/**
	 * 查看
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		model.addAttribute("feedback", feedbackService.find(id));
		return "/admin/feedback/view";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		feedbackService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
	
	
}


