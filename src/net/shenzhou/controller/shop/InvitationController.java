/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sf.json.JSONObject;
import net.shenzhou.dao.MemberDao;
import net.shenzhou.Setting;
import net.shenzhou.entity.BeforehandLogin;
import net.shenzhou.entity.BeforehandLogin.InviteType;
import net.shenzhou.entity.BeforehandLogin.UserType;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Member;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.BeforehandLoginService;
import net.shenzhou.service.BrandService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.GoodsService;
import net.shenzhou.service.ManagementService;
import net.shenzhou.service.MechanismCategoryService;
import net.shenzhou.service.MechanismImageService;
import net.shenzhou.service.MechanismRankService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.ProductCategoryService;
import net.shenzhou.service.ProductImageService;
import net.shenzhou.service.ProductService;
import net.shenzhou.service.PromotionService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.SpecificationService;
import net.shenzhou.service.SpecificationValueService;
import net.shenzhou.service.TagService;
import net.shenzhou.service.UserService;
import net.shenzhou.service.WorkDateService;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.SettingUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * 邀请赠送积分
 * 2017-06-22 16:40:53
 * @author fl
 *
 */
@Controller("invitationController")
@RequestMapping("/shop/invitation")
public class InvitationController extends BaseController {

	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "mechanismCategoryServiceImpl")
	private MechanismCategoryService mechanismCategoryService;
	@Resource(name = "mechanismRankServiceImpl")
	private MechanismRankService mechanismRankService;
	@Resource(name = "serverProjectCategoryServiceImpl")
	private ServerProjectCategoryService serverProjectCategoryService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	@Resource(name = "managementServiceImpl")
	private ManagementService managementService;
	@Resource(name = "workDateServiceImpl")
	private WorkDateService workDateService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "productImageServiceImpl")
	private ProductImageService productImageService;
	@Resource(name = "specificationServiceImpl")
	private SpecificationService specificationService;
	@Resource(name = "specificationValueServiceImpl")
	private SpecificationValueService specificationValueService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "mechanismImageServiceImpl")
	private MechanismImageService mechanismImageService;
	@Resource(name = "beforehandLoginServiceImpl")
	private BeforehandLoginService beforehandLoginService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;

	
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/invitation", method = RequestMethod.GET)
	public String invitation(ModelMap model, HttpServletRequest request,String phone,UserType userType,UserType usersType,String message) {
		Setting setting = SettingUtils.get();
		String doctorInvitationMemberPointExplain = setting.getDoctorInvitationMemberPointExplain();
		Long doctorInvitationMemberPoint = setting.getDoctorInvitationMemberPoint();
		model.addAttribute("doctorInvitationMemberPointExplain",doctorInvitationMemberPointExplain);
		model.addAttribute("doctorInvitationMemberPoint",doctorInvitationMemberPoint);
		model.addAttribute("phone",phone);
		model.addAttribute("userType", userType);
		if (userType.equals(UserType.doctor)) {
			Doctor doctor = doctorService.findByUsername(phone);
			model.addAttribute("name", doctor.getName());
		}
		if (userType.equals(UserType.member)) {
			Member member = memberService.findByUsername(phone);
			model.addAttribute("name", member.getName());
		}
		model.addAttribute("usersType", usersType);
		model.addAttribute("message", message);
		model.addAttribute("fault", "");
		return "shop/invitation/invitation";
	}

	/**
	 * 分享页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/share", method = RequestMethod.GET)
	public String memberShare(ModelMap model,String phone,UserType userType, HttpServletRequest request) {
		Setting setting = SettingUtils.get();
		model.addAttribute("phone",phone);
		model.addAttribute("userType",userType);
		model.addAttribute("setting",setting);
		return "shop/invitation/share";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
//	@RequestMapping(value = "/doctorShare", method = RequestMethod.GET)
//	public String doctorShare(ModelMap model,String phone, HttpServletRequest request) {
//		Setting setting = SettingUtils.get();
//		model.addAttribute("phone",phone);
//		model.addAttribute("setting",setting);
//		return "shop/invitation/doctorShare";
//	}
	
	/*
	@RequestMapping(value = "/doctorShares", method = RequestMethod.GET)
	public String doctorShares(String file, HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
		String mobile = json.getString("mobile");//手机号
		Member member = memberService.findByMobile(mobile);
		System.out.println(member);
		return "shop/invitation/doctorShare";
	}*/
	
	
	/**
	 * 邀请码预注册(记录手机号和双方关系)
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/beforehandLogin", method = RequestMethod.GET)
	public String beforehandLogin(ModelMap model,String file, HttpServletRequest request, HttpServletResponse response,String phone,UserType userType,UserType usersType,String mobile) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		Setting setting = SettingUtils.get();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		try {
			BeforehandLogin beforehandLogins = beforehandLoginService.findByMobile(mobile);
			Member member = memberService.findByMobile(mobile);
			model.addAttribute("setting",setting);
			Long point = 0l;
			if(userType.equals(UserType.doctor)){
				point = setting.getDoctorInvitationMemberPoint();
				Doctor doctor = doctorService.findByUsername(phone);
				model.addAttribute("name", doctor.getName());
			}else{
				point = setting.getFirstMemberInvitationMemberPoint();
				model.addAttribute("name", memberService.findByUsername(phone).getName());
			}
			model.addAttribute("point", point);
			if(member!=null||phone.equals(mobile)||beforehandLogins!=null){//判断如果用户存在或者邀请者和被邀请者手机号相同或者该手机已被邀请过
				model.addAttribute("phone",phone);
				model.addAttribute("userType", userType);
				model.addAttribute("usersType", usersType);
				model.addAttribute("fals",true);
				return "shop/invitation/download";
			}
			if(beforehandLogins!=null){
				if(beforehandLogins.isNotarizeLogin()){
					model.addAttribute("phone",phone);
					model.addAttribute("userType", userType);
					model.addAttribute("usersType", usersType);
					model.addAttribute("fault", "您已注册，不能再次被邀请，请至haokanghu.cn下载“好康护”用户端完善资料享受优惠政策。");
					return "shop/invitation/invitationDownLoad";
				}
				model.addAttribute("phone",phone);
				model.addAttribute("userType", userType);
				model.addAttribute("usersType", usersType);
				model.addAttribute("fault", "您已被邀请，不能再次被邀请，请至haokanghu.cn下载“好康护”用户端。");
				return "shop/invitation/invitationDownLoad";
			}
			     
			BeforehandLogin beforehandLogin = new BeforehandLogin();
			beforehandLogin.setMobile(mobile);
			beforehandLogin.setPhone(phone);
			beforehandLogin.setUserType(userType);
			beforehandLogin.setUsersType(usersType);
			beforehandLogin.setInviteType(InviteType.login);
			beforehandLogin.setNotarizeLogin(false);
			beforehandLogin.setPurchase(false);
			beforehandLogin.setPoint(userType==UserType.member?setting.getMemberInvitationMemberPoint():setting.getDoctorInvitationMemberPoint());
			beforehandLogin.setAccomplish(false);
			beforehandLogin.setIsDeleted(false);
			beforehandLoginService.save(beforehandLogin);
			
			model.addAttribute("phone",phone);
			model.addAttribute("userType", userType);
			model.addAttribute("usersType", usersType);
			model.addAttribute("fals",false);
			return "shop/invitation/download";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			map.put("status", "400");
			map.put("message", e.getMessage());
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return "shop/invitation/download";
		}
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/invitationDownLoad", method = RequestMethod.GET)
	public String invitationDownLoad(ModelMap model, HttpServletRequest request) {
		return "shop/invitation/invitationDownLoadCopy";
	}
	
	
}