/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.shenzhou.entity.BankCard;
import net.shenzhou.entity.BankCard.CardType;
import net.shenzhou.entity.Bill;
import net.shenzhou.entity.Bill.BillType;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.WithdrawDeposit;
import net.shenzhou.entity.WithdrawDeposit.WithdrawState;
import net.shenzhou.service.AreaService;
import net.shenzhou.service.BankCardService;
import net.shenzhou.service.BillService;
import net.shenzhou.service.CaptchaService;
import net.shenzhou.service.CartService;
import net.shenzhou.service.DoctorService;
import net.shenzhou.service.MechanismService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.RSAService;
import net.shenzhou.service.VerificationService;
import net.shenzhou.service.WithdrawDepositService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 医生账户
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("appBankCardController")
@RequestMapping("/app/bankCard")
public class BankCardController extends BaseController {
	
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	@Resource(name = "verificationServiceImpl")
	private VerificationService verificationService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "bankCardServiceImpl")
	private BankCardService bankCardService;
	@Resource(name = "withdrawDepositServiceImpl")
	private WithdrawDepositService withdrawDepositService;
	@Resource(name = "billServiceImpl")
	private BillService billService;
	@Resource(name = "mechanismServiceImpl")
	private MechanismService mechanismService;
	/**
	 * 余额(钱包页面)
	 * @throws IOException 
	 */
	@RequestMapping(value = "/balance", method = RequestMethod.GET)
	public void balance(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
        String safeKeyValue = json.getString("safeKeyValue");
        
        if(StringUtils.isEmpty(safeKeyValue)){
			map.put("status", "400");
			map.put("message", "还没登录请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        
		Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
        if(doctor == null){
			map.put("status", "400");
			map.put("message", "秘钥失效,请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		if(doctor.getSafeKey().hasExpired()){
			map.put("status", "400");
			map.put("message", "秘钥失效,请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        Map<String,Object> map_data = new HashMap<String, Object>();
		map_data.put("doctor",doctor );
		
		map.put("status", "200");
		map.put("message", "查詢成功");
		map.put("data", JsonUtils.toJson(map_data));
		printWriter.write(JsonUtils.toString(map)) ;
		return;
        
	}
	
	
	/**
	 * 添加银行卡
	 * @throws IOException 
	 */
	@RequestMapping(value = "/addBankCard", method = RequestMethod.GET)
	public void addBankCard(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
        String safeKeyValue = json.getString("SafeKey");
        String cardNo = json.getString("cardNo");
        String mobile = json.getString("mobile");
        //获取当前医生
        if(StringUtils.isEmpty(safeKeyValue)){
			map.put("status", "400");
			map.put("message", "还没登录请先登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
		if(doctor == null){
			map.put("status", "400");
			map.put("message", "秘钥失效,请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		if(doctor.getSafeKey().hasExpired()){
			map.put("status", "400");
			map.put("message", "秘钥失效,请重新登录");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        //创建银行卡
        BankCard bankcard = new BankCard();
        bankcard.setCardNo(cardNo);
        bankcard.setCustomerName(doctor.getName());
        bankcard.setMobile(mobile);
        //类型暂时默认储蓄卡
        bankcard.setCardType(CardType.barkCard);
        bankcard.setBank("中国建设银行");
        bankcard.setDoctor(doctor);
        bankCardService.save(bankcard);
        doctor.getBankCards().add(bankcard);
        doctorService.update(doctor);
        
        map.put("status", "200");
		map.put("message", "绑定成功");
		map.put("data","{}" );
		printWriter.write(JsonUtils.toString(map)) ;
		return;
        
	}

	
	
	/**
	 * 设置支付密码
	 * @throws IOException 
	 */
	@RequestMapping(value = "/paymentCode", method = RequestMethod.GET)
	public void paymentCode(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
			Map<String ,Object> map = new HashMap<String ,Object>();
			PrintWriter printWriter = response.getWriter();
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
			file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("SafeKey");//获取密匙
	        String paymentPassWord = json.getString("paymentPassWord");//获取支付密码
	        
	        if(StringUtils.isEmpty(safeKeyValue)){//判断是否登录
				map.put("status", "400");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);//根据密钥查找医生
			if(doctor == null){//判断是否查询到医生
				map.put("status", "400");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){//判断密匙是否过期
				map.put("status", "400");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
	        doctor.setPaymentPassWord(DigestUtils.md5Hex(paymentPassWord));//设置支付密码
	        doctorService.update(doctor);//修改支付密码
	        map.put("status", "200");
			map.put("message", "密码设置成功");
			map.put("data","{}");
			printWriter.write(JsonUtils.toString(map)) ;//把map转换成json
			return;
	}
	
	/**
	 * 提现
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/checkPaymentCode", method = RequestMethod.GET)
	public void checkPaymentCode(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ParseException {
			Map<String ,Object> map = new HashMap<String ,Object>();
			PrintWriter printWriter = response.getWriter();
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
			file = StringEscapeUtils.unescapeHtml(file);
	        JSONObject json = JSONObject.fromObject(file);
	        String safeKeyValue = json.getString("SafeKey");
	        String paymentPassWord = json.getString("paymentPassWord");
	        String money = json.getString("money");
	        String cardNo = json.getString("cardNo");
	        //获取当前医生
	        if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "400");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "400");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data","{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "400");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
	        //验证支付密码
	        if(!DigestUtils.md5Hex(paymentPassWord).equals(doctor.getPaymentPassWord())){
	        	map.put("status", "400");
				map.put("message", "支付密码错误");
				map.put("data","{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
	        }
	        //查看余额是否足够
	        BigDecimal moneys=new BigDecimal(money);
	        if(moneys.compareTo(doctor.getPersonageAccount())>0){
	        	map.put("status", "400");
				map.put("message", "余额不足");
				map.put("data","{}" );
				printWriter.write(JsonUtils.toString(map)) ;
				return;

	        }
	        
	        //计算余额
	        BigDecimal personageAccount = doctor.getPersonageAccount().subtract(moneys);
	        doctor.setPersonageAccount(personageAccount);
	        
	        //生成提现明细单
	        WithdrawDeposit withdrawDeposit = new WithdrawDeposit();
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String now = df.format(new Date());
	        String delayed = DateUtil.getStatetime();
	        
	        Date nows = df.parse(now); 
	        Date delayeds = df.parse(delayed);
	        
	        withdrawDeposit.setDoctor(doctor);
	        withdrawDeposit.setDate(nows);
	        withdrawDeposit.setMoney(moneys);
	        withdrawDeposit.setCardType(CardType.barkCard);
	        withdrawDeposit.setCardNo(cardNo);
	        withdrawDeposit.setPredictDate(delayeds);
	        //默认提现状态为处理中
	        withdrawDeposit.setWithdrawState(WithdrawState.operation);
	        withdrawDepositService.save(withdrawDeposit);
	        
	        doctor.getWithdrawDeposits().add(withdrawDeposit);
	        doctorService.update(doctor);
	        map.put("status", "200");
			map.put("message", "操作成功");
			map.put("data","{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
	}
	/**
	 * 获取账单日期
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/billDate", method = RequestMethod.GET)
	public void dateClassify(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ParseException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try {
        	file = StringEscapeUtils.unescapeHtml(file);
            JSONObject json = JSONObject.fromObject(file);
            BillType billType = BillType.valueOf(json.getString("billType"));
            String safeKeyValue = json.getString("safeKeyValue");
    		//获取当前医生
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "400");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "400");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "400");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
            List<Bill> bills = billService.getBillDatePack(doctor,billType);
            List<String> billDates = new ArrayList<String>();
            for(int i=0;i<bills.size();i++){
            	Date date = bills.get(i).getCreateDate();
            	String billDate = DateUtil.getDatetoString("yyyy-MM", date);
            	billDates.add(billDate);
            }
            
            Map<String ,Object> map_date = new HashMap<String ,Object>();
        	map_date.put("date", billDates);
        	map.put("status", "200");
    		map.put("message", "获取成功");
    		map.put("data",JsonUtils.toJson(map_date));
    		printWriter.write(JsonUtils.toString(map)) ;
    		return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			 map.put("status", "400");
			 map.put("message", "获取失败");
			 map.put("data","{}");
			 printWriter.write(JsonUtils.toString(map)) ;
			
		}
		
        
	}
	/**
	 * 获取经过机构筛选后的账单日期
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/filtrateBillDate", method = RequestMethod.GET)
	public void filtrateBillDate(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ParseException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try {
        	file = StringEscapeUtils.unescapeHtml(file);
        	JSONObject json = JSONObject.fromObject(file);
        	String safeKeyValue = json.getString("SafeKey");
        	BillType billType = BillType.valueOf(json.getString("billType"));
        	String mechanism = json.getString("mechanism");
        	if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "400");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
    		Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "400");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "400");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			
        	Mechanism mechanisms = mechanismService.find(Long.parseLong(mechanism));
        	List<Bill> bills = billService.filtrateBillDate(doctor,mechanisms,billType);
        	List<String> billDates = new ArrayList<String>();
        	for(int i=0;i<bills.size();i++){
        		Date date = bills.get(i).getCreateDate();
        		String billDate = DateUtil.getDatetoString("yyyy-MM", date);
        		billDates.add(billDate);
        	}
        	Map<String ,Object> map_date = new HashMap<String ,Object>();
        	map_date.put("date", billDates);
        	map.put("status", "200");
    		map.put("message", "获取成功");
    		map.put("data",map_date);
    		printWriter.write(JsonUtils.toString(map)) ;
    		return;
        	}catch (Exception e) {
        		System.out.println(e.getMessage());
        		map.put("status", "400");
        		map.put("message", "获取失败");
        		map.put("data","{}");
        		printWriter.write(JsonUtils.toString(map));
		}  
	}
	/**
	 * 根据日期获取账单明细(详细)
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/BillDetails", method = RequestMethod.GET)
	public void BillDetails(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ParseException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try {
        	file = StringEscapeUtils.unescapeHtml(file);
        	JSONObject json = JSONObject.fromObject(file);
        	String safeKeyValue = json.getString("SafeKey");
        	BillType billType = BillType.valueOf(json.getString("billType"));
        	String date = json.getString("date");
        	//获取当前医生
        	if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "400");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
    		Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "400");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "400");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
        	//获取当前月份时间区间
        	Map<String,String> dateMap = DateUtil.getDateStartEnd(date);
        	String staterDate= dateMap.get("startDate");
        	String endaDate =dateMap.get("endDate");
        	Date staterDates = DateUtil.getStringtoDate(staterDate, "yyyy-MM-dd HH:mm:ss");
        	Date endaDates = DateUtil.getStringtoDate(endaDate, "yyyy-MM-dd HH:mm:ss");;
        	
        	List<Bill> bills = billService.BillDetails(doctor,billType,staterDates,endaDates);
        	
        	Map<String ,Object> map_date = new HashMap<String ,Object>();
        	map_date.put("date", bills);
        	map.put("status", "200");
    		map.put("message", "获取成功");
    		map.put("data",JsonUtils.toJson(map_date));
    		printWriter.write(JsonUtils.toString(map)) ;
    		return;
        	}catch (Exception e) {
        		System.out.println(e.getMessage());
        		map.put("status", "400");
        		map.put("message", "获取失败");
        		map.put("data","{}");
        		printWriter.write(JsonUtils.toString(map));
		}  
	}
	/**
	 * //根据日期,所选机构获取账单明细(详细)
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/mechanismBillDetails", method = RequestMethod.GET)
	public void mechanismBillDetails(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ParseException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try {
        	file = StringEscapeUtils.unescapeHtml(file);
        	JSONObject json = JSONObject.fromObject(file);
        	String safeKeyValue = json.getString("SafeKey");
        	BillType billType = BillType.valueOf(json.getString("billType"));
        	String date = json.getString("date");
        	String mechanism = json.getString("mechanism");
        	//获取当前医生
        	if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "400");
				map.put("message", "还没登录请先登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
    		Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "400");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "400");
				map.put("message", "秘钥失效,请重新登录");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
        	//获取当前月份时间区间
        	Map<String,String> dateMap = DateUtil.getDateStartEnd(date);
        	String staterDate= dateMap.get("startDate");
        	String endaDate =dateMap.get("endDate");
        	Date staterDates = DateUtil.getStringtoDate(staterDate, "yyyy-MM-dd HH:mm:ss");
        	Date endaDates = DateUtil.getStringtoDate(endaDate, "yyyy-MM-dd HH:mm:ss");
        	
        	Mechanism mechanisms = mechanismService.find(Long.parseLong(mechanism));
        	List<Bill> bills = billService.mechanismBillDetails(doctor,billType,staterDates,endaDates,mechanisms);
        	
        	Map<String ,Object> map_date = new HashMap<String ,Object>();
        	map_date.put("date", bills);
        	map.put("status", "200");
    		map.put("message", "获取成功");
    		map.put("data",JsonUtils.toJson(map_date));
    		printWriter.write(JsonUtils.toString(map)) ;
    		return;
        	}catch (Exception e) {
        		System.out.println(e.getMessage());
        		map.put("status", "400");
        		map.put("message", "获取失败");
        		map.put("data","{}");
        		printWriter.write(JsonUtils.toString(map));
		}  
	}

	
	
}



















