/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.shenzhou.entity.Versions;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.VersionsService;
import net.shenzhou.util.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 版本
 * @date 2017-12-13 14:16:04
 * @author wsr
 *
 */
@Controller("appVersionsController")
@RequestMapping("/app/versions")
public class VersionsController extends BaseController {
	
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "versionsServiceImpl")
	private VersionsService versionsService;
	
	/**
	 * 获取版本号
	 * @param file
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/getVersions", method = RequestMethod.GET)
	public void getVersions(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try {
     		Map<String ,Object> data = new HashMap<String ,Object>();
     		List<Versions> versions = versionsService.findAll();
     		data.put("doctorVersions", versions.get(0).getDoctorVersions());
     		data.put("memberVersions", versions.get(0).getMemberVersions());
     		map.put("status", "200");
     		map.put("message", "数据加载成功");
     		map.put("data", JSONObject.fromObject(data));
     		printWriter.write(JSONObject.fromObject(map).toString()) ;
     		return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
     		map.put("status", "400");
     		map.put("message", "数据加载成功");
     		map.put("data",new Object());
     		printWriter.write(JSONObject.fromObject(map).toString()) ;
     		return;
		}
       
	}
}


