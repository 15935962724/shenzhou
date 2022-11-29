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
import net.shenzhou.entity.Deposit.Type;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.User;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.DateUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller - 预存款
 * @author wsr
 * @date 2017-7-31 09:43:24
 */
@Controller("mechanismDepositController")
@RequestMapping("/mechanism/deposit")
public class DepositController extends BaseController {

	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,Date startDate,Date endDate,String nameOrmobile,Type type, ModelMap model) {
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
		query_map.put("type", type);
		Page<Deposit> page = depositService.getMechanismDepositFindPage(query_map);
		System.out.println("总共"+page.getTotal()+"条记录");
		model.addAttribute("page", page);
		model.addAttribute("nameOrmobile", nameOrmobile);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("type", type);
		model.addAttribute("types", Type.values());
		return "/mechanism/deposit/list";
	}

	/**
	 * 导出预存款统计
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
		List<Deposit> deposits = depositService.downloadList(query_map);
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		for (Deposit deposit : deposits) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("memberName", deposit.getMember().getName());//监护人
			data_map.put("memberPhone", deposit.getMember().getMobile());//监护人电话
			StringBuffer patientNames = new StringBuffer();
			for (Member patient : deposit.getMember().getChildren()) {
				patientNames.append(patient.getName()).append(",");
			}
			data_map.put("patientNames", patientNames.toString());//患者
			data_map.put("type",message("Deposit.Type."+deposit.getType()) );//类型
			data_map.put("credit", deposit.getCredit());//收入金额
			data_map.put("debit",deposit.getDebit());//支出金额
			data_map.put("balance", deposit.getBalance());//当前余额
			data_map.put("orderSn", deposit.getOrder()!=null?deposit.getOrder().getSn():"-");//订单编号
			data_map.put("memo", deposit.getMemo());//备注
			data_map.put("createDate", DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", deposit.getCreateDate()) );//操作时间
			data_map.put("operator", deposit.getOperator());//操作员
			data.add(data_map);
		}
		
		String filename = "预存款统计" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"用户姓名","用户电话","患者","类型(元)","收入(元)","支出","当前余额(元)","订单编号","备注","操作时间","操作人"};//title
		String[] contents = new String []{"memberName","memberPhone","patientNames","type","credit","debit","balance","orderSn","memo","createDate","operator"};//content
		
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