/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Setting;
import net.shenzhou.Config.ImageType;
import net.shenzhou.FileInfo.FileType;
import net.shenzhou.plugin.StoragePlugin;
import net.shenzhou.service.FileService;
import net.shenzhou.service.MemberService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.FreemarkerUtils;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.SettingUtils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传图片
 * @date 2017-6-16 14:13:01
 * @author wsr
 *
 */
@Controller("appUploadController")
@RequestMapping("/app/upload")
public class ImgUploadController extends BaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	
	
	/**
	 *  上传图片
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/upload/uploadImg.jhtml
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	public void uploadImg(String fieldNameHere,@RequestParam(value = "test", required = false)MultipartFile  file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        fieldNameHere = StringEscapeUtils.unescapeHtml(fieldNameHere);
	        JSONObject json = JSONObject.fromObject(fieldNameHere);
	        String path =Config.memberLogoUrl;
	        
	        String url = fileService.uploadImg(FileType.image, file, path, UUID.randomUUID().toString(), false);
			System.out.println(url);
	        
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("url", url);
			map.put("status", "200");
			map.put("message", "上传成功");
			map.put("data", JsonUtils.toJson(data));
			System.out.println(JsonUtils.toString(map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
			
		} catch (RuntimeException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	
	
	/**
	 * 上传图片
	 * @param imageType
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public void uploadImage(String imageType,@RequestParam(value = "test", required = false)MultipartFile  file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			String url="";
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        
	        if(ImageType.valueOf(imageType).equals(ImageType.doctor)){
	            String path = Config.doctorLogoUrl;
		        String name = DateUtil.getStatetime()+path;
		        String fileUrl = fileService.uploadImg(FileType.image, file, path, UUID.randomUUID().toString(), false);
		        System.out.println(url);
		        url=fileUrl;
	        }
	        
	        if(ImageType.valueOf(imageType).equals(ImageType.project)){
	            String path = Config.projectLogoUrl;
		        String name = DateUtil.getStatetime()+path;
		        String fileUrl = fileService.uploadImg(FileType.image, file, path, UUID.randomUUID().toString(), false);
		        System.out.println(url);
		        url=fileUrl;
	        }
	        
	        if(ImageType.valueOf(imageType).equals(ImageType.projectIntroduce)){
	            String path = Config.projectIntroduceImgUrl;
		        String name = DateUtil.getStatetime()+path;
		        String fileUrl = fileService.uploadImg(FileType.image, file, path, UUID.randomUUID().toString(), false);
		        System.out.println(url);
		        url=fileUrl;
	        }
	        
	        if(ImageType.valueOf(imageType).equals(ImageType.aptitude)){
	            String path = Config.doctorAptitudeImgUrl;
		        String name = DateUtil.getStatetime()+path;
		        String fileUrl = fileService.uploadImg(FileType.image, file, path, UUID.randomUUID().toString(), false);
		        System.out.println(url);
		        url=fileUrl;
	        }
	        
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("url", url);
			map.put("status", "200");
			map.put("message", "上传成功");
			map.put("data", JsonUtils.toJson(data));
			System.out.println(JsonUtils.toString(map));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
			
		} catch (RuntimeException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", new Object());
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
	}
	
}


