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
import net.shenzhou.entity.Area;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.RecoveryPlan;
import net.shenzhou.entity.RecoveryRecord;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.AssessReportService;
import net.shenzhou.service.MemberService;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 地区
 * @date 2017-6-13 17:06:12
 * @author wsr
 *
 */
@Controller("appAreaController")
@RequestMapping("/app/area")
public class AreaController extends BaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "assessReportServiceImpl")
	private AssessReportService assessReportService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	
	/**
	 *  
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/assessReport/list.jhtml?file={areaId:"18"}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		System.out.println(file);
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);

			Map<String,Object> data_map = new HashMap<String, Object>();
			if (json.getLong("areaId")==0) {
				data_map.put("areas",areaService.findRoots());
			}else{
				data_map.put("areas",areaService.findList(areaService.find(json.getLong("areaId"))));
			}
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data",JsonUtils.toJson(data_map));
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
	
	
	
	
	
	@RequestMapping(value = "/lis", method = RequestMethod.GET)
	public void li(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);

			Map<String,Object> data_map = new HashMap<String, Object>();
			if (json.getLong("areaId")==0) {
				List<Area> list = areaService.findAll();
				System.out.println(list);
				data_map.put("areas",list.size());
			}else{
				data_map.put("areas",areaService.findList(areaService.find(json.getLong("areaId"))));
			}
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data",JsonUtils.toJson(data_map));
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


