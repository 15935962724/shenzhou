/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.Feedback;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.RecoveryPlan;
import net.shenzhou.entity.RecoveryRecord;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.AssessReportService;
import net.shenzhou.service.FeedbackService;
import net.shenzhou.service.MemberService;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 反馈
 * @date 2017-11-17 11:18:47
 * @author wsr
 *
 */
@Controller("appFeedbackController")
@RequestMapping("/app/feedback")
public class FeedbackController extends BaseController {
	
	@Resource(name = "feedbackServiceImpl")
	private FeedbackService feedbackService;
	
	
	
	
	/**
	 *  意见反馈提交接口
	 *  @author wsr
	 *  @date 2017-11-17 14:23:08
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	//http://localhost:8080/shenzhou/app/feedback/save.jhtml?file={name:"18",phone:"18",content:"18",isAnonymous:"false"}
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public void save(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String name = json.get("name").toString();
	        String phone = json.get("phone").toString();
	        String content = json.get("content").toString();
	        Boolean isAnonymous = Boolean.valueOf(json.get("isAnonymous").toString());
	        Feedback feedback = new Feedback();
	        feedback.setName(name);
	        feedback.setPhone(phone);
	        feedback.setIp(request.getRemoteAddr());
	        feedback.setContent(content);
	        feedback.setIsAnonymous(isAnonymous);
	        feedbackService.save(feedback);;
			map.put("status", "200");
			map.put("message", "数据提交成功");
			map.put("data","{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	
}


