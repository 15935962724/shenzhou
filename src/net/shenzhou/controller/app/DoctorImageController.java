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
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorImage;
import net.shenzhou.service.DoctorImageService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller - 医师登录
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appDoctorImageController")
@RequestMapping("/app/doctorImage")
public class DoctorImageController extends BaseController {
	

	
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "doctorImageServiceImpl")
	private DoctorImageService doctorImageService;
	
	/**
	 * 医生上传资质证书
	 * @param file
	 * @param files
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public void save(String file,@RequestParam(value = "doctorImages", required = false) List<MultipartFile>  files,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
		try {
			String safeKeyValue = json.getString("safeKeyValue");
	        if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
	        
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
	        if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
		
			//此处上传医生的资质图片(多图上传)
			for (MultipartFile multipartFile : files) {
				DoctorImage doctorImage = new DoctorImage();
				doctorImage.setFile(multipartFile);
				doctorImageService.build(doctorImage);
				doctor.getDoctorImages().add(doctorImage);
			}
			
			doctorService.update(doctor);
			map.put("status", "200");
			map.put("message", "注册成功");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;

		} catch (Exception e) {
			map.put("status", "400");
			map.put("message","注册失败");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	/**
	 * 医生资质证书回显
	 * @param file
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public void show(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
		try {
			String safeKeyValue = json.getString("safeKeyValue");
	        if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
	        
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
	        if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
		    Map<String ,Object> data_map = new HashMap<String, Object>();
		    data_map.put("doctorImages", doctor.getDoctorImages());
			map.put("status", "200");
			map.put("message", "注册成功");
			map.put("data", JsonUtils.toJson(data_map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;

		} catch (Exception e) {
			map.put("status", "400");
			map.put("message","注册失败");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}
	
	
	/**
	 * 编辑医生资质证书
	 * @param file
	 * @param files
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public void update(String file,@RequestParam(value = "doctorImages", required = false) List<MultipartFile>  files,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
		try {
			String safeKeyValue = json.getString("safeKeyValue");
	        if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
	        
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
	        if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
		
			//此处上传医生的资质图片(多图上传)
			for (MultipartFile multipartFile : files) {
				DoctorImage doctorImage = new DoctorImage();
				doctorImage.setFile(multipartFile);
				doctorImageService.build(doctorImage);
				doctor.getDoctorImages().add(doctorImage);
			}
			
			doctorService.update(doctor);
			map.put("status", "200");
			map.put("message", "注册成功");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;

		} catch (Exception e) {
			map.put("status", "400");
			map.put("message","注册失败");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
	}

}
 
