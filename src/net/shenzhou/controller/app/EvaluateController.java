/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Setting;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.AssessReport;
import net.shenzhou.entity.BankCard;
import net.shenzhou.entity.BankCard.CardType;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Evaluate;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Member.Gender;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Order.OrderStatus;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Project.Audit;
import net.shenzhou.entity.Project.ServiceGroup;
import net.shenzhou.entity.RecoveryPlan.CheckState;
import net.shenzhou.entity.AssessReportImage;
import net.shenzhou.entity.RecoveryPlan;
import net.shenzhou.entity.RecoveryRecord;
import net.shenzhou.entity.SafeKey;
import net.shenzhou.entity.ServerProjectCategory;
import net.shenzhou.entity.Verification;
import net.shenzhou.entity.WithdrawDeposit;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.AssessReportService;
import net.shenzhou.service.BankCardService;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.CartService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.EvaluateService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.ProjectService;
import net.shenzhou.service.RSAService;
import net.shenzhou.service.ServerProjectCategoryService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.service.WithdrawDepositService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.RSAUtils;
import net.shenzhou.util.SettingUtils;
import net.shenzhou.util.ShortMessageUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 评价
 * @date 2017-06-19 13:37:17
 * @author wsr
 *
 */
@Controller("appEvaluateController")
@RequestMapping("/app/evaluate")
public class EvaluateController extends BaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "evaluateServiceImpl")
	private EvaluateService evaluateService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	
	
	
	
	/**
	 * 评价保存
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/evaluate/save.jhtml?file={safeKeyValue:"18",scoreSort:"2",serverSort:"4",skillSort:"3",communicateSort:"5",content:"不错",isShow:"false",orderId:"3"}
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public void list(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
            Member member = memberService.findBySafeKeyValue(safeKeyValue); 
            if(member == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(member.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			Map<String,Object> data_map = new HashMap<String, Object>();
			
			Integer scoreSort = json.getInt("scoreSort");
			Integer serverSort = json.getInt("serverSort");
			Integer skillSort = json.getInt("skillSort");
			Integer communicateSort = json.getInt("communicateSort");
			String content = json.getString("content");
			Boolean  isShow = json.getBoolean("isShow");
			String ip = request.getRemoteAddr();
			Order order = orderService.find(json.getLong("orderId"));
			Project  project = order.getProject();
			Evaluate evaluate = new Evaluate();
			evaluate.setScoreSort(scoreSort);
			evaluate.setServerSort(serverSort);
			evaluate.setSkillSort(skillSort);
			evaluate.setCommunicateSort(communicateSort);
			evaluate.setContent(content);;
			evaluate.setIsShow(isShow);
			evaluate.setIp(ip);
			evaluate.setProject(project);
			evaluate.setMember(member);
			evaluate.setOrder(order);
			evaluateService.save(evaluate);
			
//			Session session =sessionFactory.openSession();
//			
//			sessionFactory.close();
			order.setEvaluateOrder(evaluate);
			order.setOrderStatus(OrderStatus.completed);
			order.setEvaluate(net.shenzhou.entity.Order.Evaluate.already);
			orderService.update(order);
			
			Object object = evaluateService.findObject(project);
			
			
			JSONArray jsonArray = JSONArray.fromObject(object);
		    Double avgScoreSort = Double.valueOf(jsonArray.get(0).toString());
		    Double avgServerSort = Double.valueOf(jsonArray.get(1).toString());
		    Double avgSkillSort = Double.valueOf(jsonArray.get(2).toString());
		    Double avgCommunicateSort = Double.valueOf(jsonArray.get(3).toString());
			
		    Doctor doctor = project.getDoctor();
		    doctor.setScoreSort(avgScoreSort);
		    doctor.setServerSort(avgServerSort);
		    doctor.setSkillSort(avgSkillSort);
		    doctor.setCommunicateSort(avgCommunicateSort);
		    doctorService.update(doctor);
		    Mechanism mechanism = order.getProject().getMechanism();
		    
		    double avgMechanismScoreSort =  mechanismService.findObject(mechanism);
		    mechanism.setScoreSort(avgMechanismScoreSort);
		    mechanismService.update(mechanism);
			
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
	
	/**
	 * 评价回显
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/evaluate/show.jhtml?file={orderId:"3"}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public void show(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
            Order order = orderService.find(json.getLong("orderId"));
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("orderId", order.getId());
			data_map.put("projectName", order.getProject().getName());
			data_map.put("projectLogo", order.getProject().getLogo());
			data_map.put("mechanismName", order.getMechanism().getName());
			data_map.put("doctorName", order.getProject().getDoctor().getName());
			data_map.put("orderSn", order.getSn());
			data_map.put("orderWorkDayDate", order.getWorkDayItem().getWorkDay().getWorkDayDate());
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("order", data_map);
			
			map.put("status", "200");
			map.put("message", "数据加载成功");
			map.put("data",JsonUtils.toJson(data));
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
	
	
	/**
	 * 医生评价列表
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/evaluate/doctorEvaluateList.jhtml?file={safeKeyValue:"18"}
	@RequestMapping(value = "/doctorEvaluateList", method = RequestMethod.GET)
	public void doctorEvaluateList(String file,HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	        file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("safeKeyValue");
	        Integer page = json.getInt("page");
	        Integer pageSize = 5;//每页条数
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "300");
				map.put("message", "还没登录请先登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
            Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue); 
            if(doctor == null){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "300");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
			Map<String,Object> data_map = new HashMap<String, Object>();
			
			List<Project> projects = projectService.getProjectByDoctor(doctor);
			Map<String,Object> query_map = new HashMap<String, Object>();
			query_map.put("projects", projects);
			query_map.put("page", page);
			
			List<Evaluate> evaluates = evaluateService.getDoctorEvaluateList(query_map);
			

			if(evaluates.size()<=0){
				map.put("status", "500");
				map.put("message", "暂无服务数据");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map));
				return;
			}
			
			String status = "200";
			String message = "第"+page+"页数据加载成功";
			//总页数
			Integer pagecount = ((evaluates.size()+pageSize-1)/pageSize);
					
			//页数
			Integer pagenumber = page>=pagecount?pagecount:page;
			List<Evaluate> evaluate_list = new ArrayList<Evaluate>();
			if (evaluates.size()>0){
				if(page>=pagecount){
					evaluate_list = evaluates.subList((pagenumber-1)*pageSize, evaluates.size());
				}else{
					evaluate_list = evaluates.subList((pagenumber-1)*pageSize, pageSize*pagenumber);
				}
				if (page>pagecount) {
					 status = "500";
					 message = "无更多数据";
				}
			}
			
			List<Map<String,Object>> evaluateList = new ArrayList<Map<String,Object>>();
			for(Evaluate evaluate : evaluate_list){
				Map<String,Object> evaluateMap = new HashMap<String, Object>();
				evaluateMap.put("scoreSort", evaluate.getScoreSort());//综合评分
				evaluateMap.put("serverSort", evaluate.getServerSort());//服务评分
				evaluateMap.put("skillSort", evaluate.getSkillSort());//技能评分
				evaluateMap.put("communicateSort", evaluate.getCommunicateSort());//沟通评分
				evaluateMap.put("content", evaluate.getContent());//内容
				evaluateMap.put("memberName", evaluate.getMember().getName());//用户姓名
				evaluateMap.put("memberLogo", evaluate.getMember().getLogo());//用户头像
				evaluateMap.put("createDate", evaluate.getCreateDate());//评价时间
				Order order = evaluate.getOrder();
				if(order==null){
					evaluateMap.put("projectName", "");//项目名称
					evaluateMap.put("patientMemberName", "");//康复人姓名
					evaluateMap.put("workDayItem","");//康复时间
					evaluateMap.put("orderId",0);//订单id
				}else{
					evaluateMap.put("projectName", evaluate.getOrder().getProject().getName());//项目名称
					evaluateMap.put("patientMemberName", evaluate.getOrder().getPatientMember().getName());//康复人姓名
					evaluateMap.put("orderId",order.getId());//订单id
					if(order.getWorkDayItem()!=null){
						evaluateMap.put("workDayItem",evaluate.getOrder().getWorkDayItem().getWorkDay().getWorkDayDate()+"    "+evaluate.getOrder().getWorkDayItem().getStartTime()+"-"+evaluate.getOrder().getWorkDayItem().getEndTime());//康复时间
					}else{
						evaluateMap.put("workDayItem","");//康复时间
					}
				}
				evaluateList.add(evaluateMap);
			}
			data_map.put("evaluateList", evaluateList);
			data_map.put("scoreSort", doctor.getScoreSort());//综合评分
			data_map.put("serverSort", doctor.getServerSort());//服务评分
			data_map.put("skillSort", doctor.getSkillSort());//技能评分
			data_map.put("communicateSort", doctor.getCommunicateSort());//沟通评分
			
			map.put("status", status);
			map.put("message", message);
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


