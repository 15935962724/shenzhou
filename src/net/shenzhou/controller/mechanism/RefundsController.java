/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.ExcelView;
import net.shenzhou.Message;
import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Balance;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Information;
import net.shenzhou.entity.Information.ClassifyType;
import net.shenzhou.entity.Information.DisposeState;
import net.shenzhou.entity.Information.InformationType;
import net.shenzhou.entity.Information.StateType;
import net.shenzhou.entity.Information.UserType;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Order;
import net.shenzhou.entity.Order.AccountType;
import net.shenzhou.entity.Order.PaymentStatus;
import net.shenzhou.entity.Order.ShippingStatus;
import net.shenzhou.entity.OrderLog;
import net.shenzhou.entity.OrderLog.Type;
import net.shenzhou.entity.Refunds;
import net.shenzhou.entity.Refunds.Method;
import net.shenzhou.entity.Refunds.Status;
import net.shenzhou.entity.User;
import net.shenzhou.service.BalanceService;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.InformationService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.OrderLogService;
import net.shenzhou.service.OrderService;
import net.shenzhou.service.RefundsService;
import net.shenzhou.service.UserService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.PushUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller - 退款单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("mechanismRefundsController")
@RequestMapping("/mechanism/refunds")
public class RefundsController extends BaseController {

	@Resource(name = "refundsServiceImpl")
	private RefundsService refundsService;
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "orderLogServiceImpl")
	private OrderLogService orderLogService;
	@Resource(name = "informationServiceImpl")
	private InformationService informationService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "balanceServiceImpl")
	private BalanceService balanceService;
	
	
	
	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		model.addAttribute("refunds", refundsService.find(id));
		return "/admin/refunds/view";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/downloadList", method = RequestMethod.GET)
	public ModelAndView downloadList(Pageable pageable,Status status, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("status", status);
		query_map.put("pageable", pageable);
		List<Refunds> refunds = refundsService.downloadList(query_map);
		List<Map<String,Object>> data_list = new ArrayList<Map<String,Object>>();
		for (Refunds p_refunds : refunds) {
			Map<String,Object> data_map = new HashMap<String, Object>();
			data_map.put("refundsOrderProjectName",p_refunds.getOrder().getProject().getName());
			data_map.put("refundsOrderSn",p_refunds.getOrder().getSn());
			String refundsOrderServerTime = "-";
			if (p_refunds.getOrder().getWorkDayItem()!=null) {
				refundsOrderServerTime = DateUtil.getDatetoString("yyyy年MM月mm日", p_refunds.getOrder().getWorkDayItem().getWorkDay().getWorkDayDate())+" "+p_refunds.getOrder().getWorkDayItem().getStartTime()+"-"+p_refunds.getOrder().getWorkDayItem().getEndTime();
			}
			data_map.put("refundsOrderServerTime",refundsOrderServerTime);
			data_map.put("refundsOrderConsignee",p_refunds.getOrder().getConsignee());
			data_map.put("refundsOrderPatientName",p_refunds.getOrder().getPatientMember().getName());
			data_map.put("refundsOrderPatientGender", message("Member.Gender."+p_refunds.getOrder().getPatientMember().getGender()));
			Integer age = 0;
			try {
				 age = DateUtil.getAge(p_refunds.getOrder().getPatientMember().getBirth());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data_map.put("refundsOrderPatientAge",  age+"周岁");
			data_map.put("refundsOrderPatientMobile",  p_refunds.getOrder().getPatientMember().getMobile());
			data_map.put("refundsOrderDoctorName",  p_refunds.getOrder().getDoctor().getName());
			data_map.put("refundsOrderDoctorPhone",  p_refunds.getOrder().getDoctor().getMobile());
			data_map.put("refundsOrderCountPrice",  p_refunds.getOrder().getPrice());
			data_map.put("refundsOrderAmountPaid",  p_refunds.getOrder().getAmountPaid());
			data_map.put("refundsAmount",  p_refunds.getAmount());
			data_map.put("refundsStatus", message("Refunds.Status."+p_refunds.getStatus()));
			data_list.add(data_map);
		}
		
		String filename = "退款管理" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		String[] titles = new String []{"项目名称","订单编号","服务时间","下单人","患者姓名","患者性别","患者年龄","患者电话","康复医师","医师电话","订单金额","已付金额","退款金额","状态"};//title
		String[] contents = new String []{"refundsOrderProjectName","refundsOrderSn","refundsOrderServerTime","refundsOrderConsignee","refundsOrderPatientName","refundsOrderPatientGender",
										"refundsOrderPatientAge","refundsOrderPatientMobile","refundsOrderDoctorName","refundsOrderDoctorPhone",
										"refundsOrderCountPrice","refundsOrderAmountPaid","refundsAmount","refundsStatus"};//content
		
		String[] memos = new String [3];//memo
		memos[0] = "记录数" + ": " + data_list.size();
		memos[1] = "操作员" + ": " + doctorC.getUsername();
		memos[2] = "生成日期" + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return new ModelAndView(new ExcelView(filename, null, contents,titles, null, null, data_list, memos), model);
	}

	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,Status status, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Map<String,Object> query_map = new HashMap<String, Object>();
		query_map.put("mechanism", mechanism);
		query_map.put("status", status);
		query_map.put("pageable", pageable);
		Page<Refunds> page = refundsService.findPage(query_map);
		System.out.println(page.getTotal());
		model.addAttribute("statuss", Status.values());
		model.addAttribute("status",status);
		
		model.addAttribute("page", page);
		return "/mechanism/refunds/list";
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		refundsService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
	/**
	 * 审核操作
	 * @param id
	 * @param status
	 * @param memo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	Message update(Long refundsId,Status status,String memo) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		Mechanism mechanism =  doctorC.getDefaultDoctorMechanismRelation().getMechanism();
		Refunds refunds =   refundsService.find(refundsId);
		if(status.equals(Status.complete)){
			Order order =  refunds.getOrder();
			if (refunds.getAmount().compareTo(order.getAmountPaid())==1) {
				return Message.warn("退款金额大于,订单已付金额,系统不予支持");
			}
			
		}
		
		if(status.equals(Status.reject)){
			Order order =  refunds.getOrder();
			Information information_doctor = new Information();
			information_doctor.setMessage("您提交退款申请已被拒绝，详情点击查阅。");
			information_doctor.setInformationId(order.getId());
			information_doctor.setHeadline("退款通知");
			information_doctor.setInformationType(InformationType.order);
			information_doctor.setClassifyType(ClassifyType.business);
			information_doctor.setState(StateType.unread);
			information_doctor.setDoctor(order.getProject().getDoctor());
			information_doctor.setMember(order.getMember()); 
			information_doctor.setIsDeleted(false);
			information_doctor.setDisposeState(DisposeState.unDispose);
			information_doctor.setUserType(UserType.doctor);
			information_doctor.setMechanism(mechanism);
			informationService.save(information_doctor);
		}
		
		refunds.setMemo(memo);
		refunds.setStatus(status);
		refunds.setOperator(status.equals(Status.complete)?doctorC.getUsername():refunds.getOperator());
		refunds.setMechanism(mechanism);
		refundsService.update(refunds);
		if(status.equals(Status.complete)){
			Order order =  refunds.getOrder();
			order.setAmountPaid(order.getAmountPaid().subtract(refunds.getAmount()));
			if(order.getAmountPaid().compareTo(new BigDecimal(0))==0){
				order.setPaymentStatus(PaymentStatus.refunded);
				order.setShippingStatus(ShippingStatus.returned);
			}else{
				order.setPaymentStatus(PaymentStatus.partialRefunds);
				order.setShippingStatus(ShippingStatus.partialReturns);
			}
			orderService.update(order);
			
			if(refunds.getMethod().equals(Method.deposit)){
				Member member = order.getMember();
//				member.setBalance(member.getBalance().add(refunds.getAmount()));
//				memberService.update(member);
				//add wsr 2018-3-20 14:39:42
				if (order.getAccountType().equals(AccountType.mechanism)) {
					Balance balance = member.getBalance(mechanism);
					balance.setBalance(balance.getBalance().add(refunds.getAmount()));
					balanceService.update(balance);
					
					Deposit deposit = new Deposit();
					deposit.setType(Deposit.Type.mechanismRefunds);
					deposit.setCredit(refunds.getAmount());
					deposit.setDebit(new BigDecimal(0));
					
					deposit.setBalance(balance.getBalance());
					deposit.setOperator(doctorC.getUsername());
					deposit.setMember(member);
					deposit.setOrder(order);
					deposit.setMechanism(mechanism);
					deposit.setMemo("医师:<"+order.getDoctor().getName()+">发起退款,退款金额为:"+refunds.getAmount().setScale(2));
					depositService.save(deposit);
				}
				if (order.getAccountType().equals(AccountType.platform)){

					member.setBalance(member.getBalance().add(refunds.getAmount()));
					memberService.update(member);
					
					Deposit deposit = new Deposit();
					deposit.setType(Deposit.Type.adminRefunds);
					deposit.setCredit(refunds.getAmount());
					deposit.setDebit(new BigDecimal(0));
					deposit.setBalance(member.getBalance());
					deposit.setOperator(doctorC.getUsername());
					deposit.setMember(member);
					deposit.setOrder(order);
					deposit.setMechanism(null);
					deposit.setMemo("医师:<"+order.getDoctor().getName()+">发起退款,退款金额为:"+refunds.getAmount().setScale(2));
					depositService.save(deposit);
					
				}
				
				
				
				OrderLog orderLog = new OrderLog();
				orderLog.setType(Type.refunds);
				orderLog.setOperator(doctorC.getName());
				orderLog.setOrder(order);
				orderLog.setContent("医师:<"+order.getDoctor().getName()+">发起退款,机构<"+mechanism.getName()+">同意");
				orderLog.setIsDeleted(false);
				orderLogService.save(orderLog);
				
				//此处消息推送  部分退款消息推送
			    if (member.getDevice_tokens()!=null) {
			    	Boolean fals = PushUtil.getFals(member.getDevice_tokens());
				    String appkey = fals?PushUtil.android_Yh_AppKey:PushUtil.ios_Yh_AppKey;//appkey
					String secret = fals?PushUtil.android_Yh_App_Master_Secret:PushUtil.ios_Yh_App_Master_Secret;//secret
					String device_tokens = member.getDevice_tokens(); //device_tokens 设备唯一识别号
					String ticker = "退款通知";// 通知栏提示文字
					String title = "退款到账通知";// 必填 通知标题
					String text = "您有订单已退款";// 必填 通知文字描述 
					String after_open = "go_activity";//必填 值为"go_app：打开应用", "go_url: 跳转到URL", "go_activity: 打开特定的activity", "go_custom: 用户自定义内容。"
					String url = "";    // 可选 当"after_open"为"go_url"时，必填。 通知栏点击后跳转的URL，要求以http或者https开头  
					String activity = "OrderActivity";     // 可选 当"after_open"为"go_activity"时，必填。 通知栏点击后打开的Activity 
					String custom = "{}";// 可选 display_type=message, 或者display_type=notification且"after_open"为"go_custom"时，该字段必填。用户自定义内容, 可以为字符串或者JSON格式
					String extra =  "{\"orderId\":"+order.getId()+"}";//用户自定义 extra
				    Map<String ,Object> send_map = new HashMap<String, Object>();
				    //android 所需参数
				    send_map.put("appkey", appkey);
				    send_map.put("secret", secret);
				    send_map.put("device_tokens", device_tokens);
				    send_map.put("ticker", ticker);
				    send_map.put("title", title);
				    send_map.put("text", text);
				    send_map.put("after_open", after_open);
				    send_map.put("url", url);
				    send_map.put("activity", activity);
				    send_map.put("custom", custom);
				    send_map.put("extra", extra);
				    
				    //ios 所需参数
				    send_map.put("alias_type","");//可选当type=customizedcast时，必填，alias的类型, alias_type可由开发者自定义,开发者在SDK中 调用setAlias(alias, alias_type)时所设置的alias_type
				    send_map.put("alias", "");// 可选 当type=customizedcast时, 开发者填写自己的alias。  要求不超过50个alias,多个alias以英文逗号间隔。 在SDK中调用setAlias(alias, alias_type)时所设置的alias
				    send_map.put("file_id", ""); // 可选 当type=filecast时，file内容为多条device_token,  device_token以回车符分隔当type=customizedcast时，file内容为多条alias， alias以回车符分隔，注意同一个文件内的alias所对应的alias_type必须和接口参数alias_type一致。注意，使用文件播前需要先调用文件上传接口获取file_id,  具体请参照"2.4文件上传接口"
				    send_map.put("alert", text);  // 必填 iOS10 新增带title，subtile的alert格式如下"alert":{//  "title":"title","subtitle":"subtitle", "body":"body",}                   
				    send_map.put("badge", "1"); // 可选        
				    send_map.put("sound", "default"); // 可选        
				    send_map.put("content_available", ""); // 可选        
				    send_map.put("category", ""); // 可选        
				    send_map.put("max_send_num", "");// 可选 发送限速，每秒发送的最大条数。开发者发送的消息如果有请求自己服务器的资源，可以考虑此参数。
				    send_map.put("apns_collapse_id", "");//可选，iOS10开始生效。
				    send_map.put("description", ""); // 可选 发送消息描述，建议填写。
				    send_map.put("iOSType", "order"); //1.project 2.docotr 3.order 4.healthcare 5. toexamine
				    send_map.put("iOSValue", order.getId());//iOSType 填写时  可填写
				    
					try {
						Map<String, Object> map_data = fals?PushUtil.androidSend(send_map):PushUtil.iosSend(send_map);//这一步 判断android 或者 ios 推送
						System.out.println("status:"+map_data.get("status")+",message:"+map_data.get("message"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				}
		        
			    Information information_member = new Information();
			    information_member.setMessage("您申请退款申请已通过，资金已退还至您的账户，详情点击查阅。");
			    information_member.setInformationId(order.getId());
			    information_member.setHeadline("退款通知");
			    information_member.setInformationType(InformationType.order);
			    information_member.setClassifyType(ClassifyType.business);
			    information_member.setState(StateType.unread);
			    information_member.setDoctor(order.getProject().getDoctor());
			    information_member.setMember(order.getMember()); 
			    information_member.setIsDeleted(false);
			    information_member.setDisposeState(DisposeState.unDispose);
			    information_member.setUserType(UserType.member);
			    information_member.setMechanism(mechanism);
				informationService.save(information_member);
				
				Information information_doctor = new Information();
				information_doctor.setMessage("您申请退款申请已通过，资金已退还至患者的账户，详情点击查阅。");
				information_doctor.setInformationId(order.getId());
				information_doctor.setHeadline("退款通知");
				information_doctor.setInformationType(InformationType.order);
				information_doctor.setClassifyType(ClassifyType.business);
				information_doctor.setState(StateType.unread);
				information_doctor.setDoctor(order.getProject().getDoctor());
				information_doctor.setMember(order.getMember()); 
				information_doctor.setIsDeleted(false);
				information_doctor.setDisposeState(DisposeState.unDispose);
				information_doctor.setUserType(UserType.doctor);
				information_doctor.setMechanism(mechanism);
				informationService.save(information_doctor);
				
				
			}
		}
		
		return SUCCESS_MESSAGE;
	}
	
	

}