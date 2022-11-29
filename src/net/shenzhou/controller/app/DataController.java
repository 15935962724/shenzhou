/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.Member.Position;
import net.shenzhou.entity.Project.Mode;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.util.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 筛选
 * @author wsr
 *
 */
@Controller("appDataController")
@RequestMapping("/app/data")
public class DataController extends BaseController {
	
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	
	
	/**
	 * 筛选接口
	 * @param file
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/screen", method = RequestMethod.GET)
	public void screen(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try {
     		Map<String ,Object> data = new HashMap<String ,Object>();
     		data.put("serverProjectCategorys", JsonUtils.toJson(serverProjectCategoryService.findList(null, null, null, null)));
     		data.put("positions", Position.values());
     		data.put("gender", Gender.values());
     		data.put("mode", Mode.values());
     		
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


