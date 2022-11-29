/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.ExcelView;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.RechargeLog;
import net.shenzhou.entity.User;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.RechargeLogService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * Controller - 充值日志
 * @author wsr
 * @date 2017-8-1 11:11:49
 */
@Controller("mechanismRechargeLogController")
@RequestMapping("/mechanism/rechargeLog")
public class RechargeLogController extends BaseController {

	
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "rechargeLogServiceImpl")
	private RechargeLogService rechargeLogService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,Date startDate,Date endDate,String nameOrmobile, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		Calendar calendar = Calendar.getInstance();
		if (startDate!=null) {
			calendar.setTime(startDate);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			startDate = calendar.getTime();
		}
		if (endDate!=null) {
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			endDate = calendar.getTime();
		}
		query_map.put("startDate", startDate);
		query_map.put("endDate", endDate);
		query_map.put("nameOrmobile", nameOrmobile);
		query_map.put("pageable", pageable);
		Page<RechargeLog> page = rechargeLogService.getMechanismRechargeLogList(query_map);
		
		System.out.println("总共"+page.getTotal()+"条记录");
		model.addAttribute("page", page);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("nameOrmobile", nameOrmobile);
		return "mechanism/rechargeLog/list";
	}

	
	/**
	 * 导出充值统计
	 * @param startDate
	 * @param endDate
	 * @param nameOrmobile
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/downloadList", method = RequestMethod.GET)
	public ModelAndView downloadCharge(Date startDate,Date endDate,String nameOrmobile, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String,Object>();
		query_map.put("mechanism", mechanism);
		Calendar calendar = Calendar.getInstance();
		if (startDate!=null) {
			calendar.setTime(startDate);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			startDate = calendar.getTime();
		}
		if (endDate!=null) {
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			endDate = calendar.getTime();
		}
		query_map.put("startDate", startDate);
		query_map.put("endDate", endDate);
		query_map.put("nameOrmobile", nameOrmobile);
		List<RechargeLog> rechargeLogs = rechargeLogService.downloadList(query_map);
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		for (RechargeLog rechargeLog : rechargeLogs) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("createDate", DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss",rechargeLog.getCreateDate()));//充值日期
			data_map.put("memberName", rechargeLog.getMember().getName());//用户姓名
			StringBuffer patientNames = new StringBuffer();
			for (Member patient : rechargeLog.getMember().getChildren()) {
				patientNames.append(patient.getName()).append(",");
			}
			data_map.put("patientNames", patientNames.toString());//患者
			data_map.put("memberPhone", rechargeLog.getMember().getMobile());//联系电话
			data_map.put("type",message("Deposit.Type."+rechargeLog.getType()) );//类型
			data_map.put("money", rechargeLog.getMoney());//充值金额
			data_map.put("remark",rechargeLog.getRemarks());//备注
			data_map.put("operator", rechargeLog.getOperator());//操作人
			data.add(data_map);
		}
		
		String filename = "充值统计" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"充值时间","用户姓名","患者","联系电话","充值方式","充值金额(元)","备注","操作人"};//title
		String[] contents = new String []{"createDate","memberName","patientNames","memberPhone","type","money","remark","operator"};//content
		
		String[] memos = new String [4];//memo
		memos[0] = "记录数" + ": " + data.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data, memos), model);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data, memos), model);
	}
	
	
	
}