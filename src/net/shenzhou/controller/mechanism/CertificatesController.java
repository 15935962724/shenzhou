/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shenzhou.FileInfo.FileType;
import net.shenzhou.Config;
import net.shenzhou.Setting;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.Certificates;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.User;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.CertificatesService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.MechanismCategoryService;
import net.shenzhou.service.MechanismRankService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.SettingUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * 机构认证
 * 2017-06-22 16:40:53
 * @author wsr
 *
 */
@Controller("certificatesController")
@RequestMapping("/mechanism/certificates")
public class CertificatesController extends BaseController {

	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "certificatesServiceImpl")
	private CertificatesService certificatesService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "mechanismCategoryServiceImpl")
	private MechanismCategoryService mechanismCategoryService;
	@Resource(name = "mechanismRankServiceImpl")
	private MechanismRankService mechanismRankService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(ModelMap model) {
//		User user = userService.getCurrent();
//		System.out.println(user.getUsername());
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		
		model.addAttribute("mechanismCategorys", mechanismCategoryService.findAll());
		model.addAttribute("mechanismRanks", mechanismRankService.findAll());
		model.addAttribute("serverProjectCategorys", serverProjectCategoryService.findAll());
		model.addAttribute("areas", areaService.findRoots());
		model.addAttribute("genders", Gender.values());
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		if (mechanism.getCertificates()==null) {
			return "mechanism/certificates/add";
		}
		
		model.addAttribute("mechanism", mechanism);
		model.addAttribute("certificates", mechanism.getCertificates());
		return "mechanism/certificates/edit";
	}

	
	/**
	 * 保存企业认证信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Certificates certificates, Long areaId, ModelMap model) {
//		User user = userService.getCurrent();
//		System.out.println(user.getUsername());
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Area area = areaService.find(areaId);
		certificates.setArea(area);
		certificates.setMechanism(mechanism);
		certificates.setIsDeleted(false);
		certificatesService.save(certificates);
		return "mechanism/certificates/edit";
	}
	
	
	/**
	 * 编辑机构证件
	 * @param certificates
	 * @param areaId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Certificates certificates, Long areaId, @RequestParam(value = "certificatesImg_0", required = false)MultipartFile  file, ModelMap model) {
//		User user = userService.getCurrent();
//		System.out.println(user.getUsername());
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Area area = areaService.find(areaId);
		Certificates certificatesNew = mechanism.getCertificates();
		
		certificatesNew.setArea(area);
		
		certificatesNew.setTitle(certificates.getTitle());
		certificatesNew.setAddress(certificates.getAddress());
		certificatesNew.setName(certificates.getName());
		certificatesNew.setBrank(certificates.getBrank());
		certificatesNew.setAccount(certificates.getAccount());
		String path = Config.certificatesImgUrl;
		String url = fileService.uploadImg(FileType.image, file, path,UUID.randomUUID().toString(), false);
		System.out.println("企业营业执照"+url);
		certificatesNew.setCertificatesImg(file.getSize()==0?certificates.getCertificatesImg():url);
		certificatesService.update(certificatesNew);
		return "redirect:view.jhtml";
	}
	

	
	// 上传图片
		@RequestMapping(value = "/uploadCertificatesImg", method = RequestMethod.POST)
		public void uploadCooperativeLogo(
				@RequestParam(value = "fileName", required = false) MultipartFile file,
				HttpServletRequest request, HttpServletResponse response,
				ModelMap model) throws IOException {
			Setting setting = SettingUtils.get();
			String paths = "/upload/certificates/certificatesImg/";
			String url = fileService.uploadImg(FileType.image, file, paths, UUID.randomUUID().toString(), false);
			System.out.println(url);
			response.getWriter().print(url);
		}
	
}