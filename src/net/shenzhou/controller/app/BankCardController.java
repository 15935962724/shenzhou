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
 * Controller - ????????????
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
	 * ??????(????????????)
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
			map.put("message", "???????????????????????????");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        
		Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
        if(doctor == null){
			map.put("status", "400");
			map.put("message", "????????????,???????????????");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		if(doctor.getSafeKey().hasExpired()){
			map.put("status", "400");
			map.put("message", "????????????,???????????????");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        Map<String,Object> map_data = new HashMap<String, Object>();
		map_data.put("doctor",doctor );
		
		map.put("status", "200");
		map.put("message", "????????????");
		map.put("data", JsonUtils.toJson(map_data));
		printWriter.write(JsonUtils.toString(map)) ;
		return;
        
	}
	
	
	/**
	 * ???????????????
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
        //??????????????????
        if(StringUtils.isEmpty(safeKeyValue)){
			map.put("status", "400");
			map.put("message", "????????????????????????");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
		if(doctor == null){
			map.put("status", "400");
			map.put("message", "????????????,???????????????");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		if(doctor.getSafeKey().hasExpired()){
			map.put("status", "400");
			map.put("message", "????????????,???????????????");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
        //???????????????
        BankCard bankcard = new BankCard();
        bankcard.setCardNo(cardNo);
        bankcard.setCustomerName(doctor.getName());
        bankcard.setMobile(mobile);
        //???????????????????????????
        bankcard.setCardType(CardType.barkCard);
        bankcard.setBank("??????????????????");
        bankcard.setDoctor(doctor);
        bankCardService.save(bankcard);
        doctor.getBankCards().add(bankcard);
        doctorService.update(doctor);
        
        map.put("status", "200");
		map.put("message", "????????????");
		map.put("data","{}" );
		printWriter.write(JsonUtils.toString(map)) ;
		return;
        
	}

	
	
	/**
	 * ??????????????????
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
	        String safeKeyValue = json.getString("SafeKey");//????????????
	        String paymentPassWord = json.getString("paymentPassWord");//??????????????????
	        
	        if(StringUtils.isEmpty(safeKeyValue)){//??????????????????
				map.put("status", "400");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);//????????????????????????
			if(doctor == null){//???????????????????????????
				map.put("status", "400");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){//????????????????????????
				map.put("status", "400");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
	        doctor.setPaymentPassWord(DigestUtils.md5Hex(paymentPassWord));//??????????????????
	        doctorService.update(doctor);//??????????????????
	        map.put("status", "200");
			map.put("message", "??????????????????");
			map.put("data","{}");
			printWriter.write(JsonUtils.toString(map)) ;//???map?????????json
			return;
	}
	
	/**
	 * ??????
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
	        //??????????????????
	        if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "400");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "400");
				map.put("message", "????????????,???????????????");
				map.put("data","{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "400");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
	        //??????????????????
	        if(!DigestUtils.md5Hex(paymentPassWord).equals(doctor.getPaymentPassWord())){
	        	map.put("status", "400");
				map.put("message", "??????????????????");
				map.put("data","{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
	        }
	        //????????????????????????
	        BigDecimal moneys=new BigDecimal(money);
	        if(moneys.compareTo(doctor.getPersonageAccount())>0){
	        	map.put("status", "400");
				map.put("message", "????????????");
				map.put("data","{}" );
				printWriter.write(JsonUtils.toString(map)) ;
				return;

	        }
	        
	        //????????????
	        BigDecimal personageAccount = doctor.getPersonageAccount().subtract(moneys);
	        doctor.setPersonageAccount(personageAccount);
	        
	        //?????????????????????
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
	        //??????????????????????????????
	        withdrawDeposit.setWithdrawState(WithdrawState.operation);
	        withdrawDepositService.save(withdrawDeposit);
	        
	        doctor.getWithdrawDeposits().add(withdrawDeposit);
	        doctorService.update(doctor);
	        map.put("status", "200");
			map.put("message", "????????????");
			map.put("data","{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
	}
	/**
	 * ??????????????????
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
    		//??????????????????
			if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "400");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "400");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "400");
				map.put("message", "????????????,???????????????");
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
    		map.put("message", "????????????");
    		map.put("data",JsonUtils.toJson(map_date));
    		printWriter.write(JsonUtils.toString(map)) ;
    		return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			 map.put("status", "400");
			 map.put("message", "????????????");
			 map.put("data","{}");
			 printWriter.write(JsonUtils.toString(map)) ;
			
		}
		
        
	}
	/**
	 * ??????????????????????????????????????????
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
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
    		Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "400");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "400");
				map.put("message", "????????????,???????????????");
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
    		map.put("message", "????????????");
    		map.put("data",map_date);
    		printWriter.write(JsonUtils.toString(map)) ;
    		return;
        	}catch (Exception e) {
        		System.out.println(e.getMessage());
        		map.put("status", "400");
        		map.put("message", "????????????");
        		map.put("data","{}");
        		printWriter.write(JsonUtils.toString(map));
		}  
	}
	/**
	 * ??????????????????????????????(??????)
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
        	//??????????????????
        	if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "400");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
    		Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "400");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "400");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
        	//??????????????????????????????
        	Map<String,String> dateMap = DateUtil.getDateStartEnd(date);
        	String staterDate= dateMap.get("startDate");
        	String endaDate =dateMap.get("endDate");
        	Date staterDates = DateUtil.getStringtoDate(staterDate, "yyyy-MM-dd HH:mm:ss");
        	Date endaDates = DateUtil.getStringtoDate(endaDate, "yyyy-MM-dd HH:mm:ss");;
        	
        	List<Bill> bills = billService.BillDetails(doctor,billType,staterDates,endaDates);
        	
        	Map<String ,Object> map_date = new HashMap<String ,Object>();
        	map_date.put("date", bills);
        	map.put("status", "200");
    		map.put("message", "????????????");
    		map.put("data",JsonUtils.toJson(map_date));
    		printWriter.write(JsonUtils.toString(map)) ;
    		return;
        	}catch (Exception e) {
        		System.out.println(e.getMessage());
        		map.put("status", "400");
        		map.put("message", "????????????");
        		map.put("data","{}");
        		printWriter.write(JsonUtils.toString(map));
		}  
	}
	/**
	 * //????????????,??????????????????????????????(??????)
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
        	//??????????????????
        	if(StringUtils.isEmpty(safeKeyValue)){
				map.put("status", "400");
				map.put("message", "????????????????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
    		Doctor doctor = doctorService.findBySafeKeyValue(safeKeyValue);
			if(doctor == null){
				map.put("status", "400");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
			if(doctor.getSafeKey().hasExpired()){
				map.put("status", "400");
				map.put("message", "????????????,???????????????");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
        	//??????????????????????????????
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
    		map.put("message", "????????????");
    		map.put("data",JsonUtils.toJson(map_date));
    		printWriter.write(JsonUtils.toString(map)) ;
    		return;
        	}catch (Exception e) {
        		System.out.println(e.getMessage());
        		map.put("status", "400");
        		map.put("message", "????????????");
        		map.put("data","{}");
        		printWriter.write(JsonUtils.toString(map));
		}  
	}

	
	
}



















