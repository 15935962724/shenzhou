/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Setting;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Verification;
import net.shenzhou.entity.Sn.Type;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.PaymentService;
import net.shenzhou.service.RedisCacheService;
import net.shenzhou.service.SnService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.HttpUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.SettingUtils;
import net.shenzhou.util.ShortMessageUtil;
import net.shenzhou.util.WeiXinUtil;
import net.shenzhou.util.XmlUtil;
import net.shenzhou.util.ZhiFuBaoPayUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

/**
 * 提现
 * @date 2018-4-20 16:31:09
 * @author wsr
 *
 */
@Controller("appPutForwardController")
@RequestMapping("/app/putForward")
public class PutForwardController extends BaseController {

	/** logger */
	private static final Logger logger = Logger.getLogger(PutForwardController.class.getName());

	private static final String T = null;
	
	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "snServiceImpl")
	private SnService snService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "redisCacheServiceImpl")
	private RedisCacheService redisCacheService;
	

	/**
	 * 支付宝用户提现
	 */
	//http://localhost:8080/shenzhou/app/putForward/charge.jhtml?file={safeKeyValue:"cdc65379381863360ece2743030d6d8d",amount:"0.01",payee_account:"15935962724",payee_real_name:"王双瑞",paymentPassword:""}
	@RequestMapping(value = "/charge", method = RequestMethod.GET)
	public void charge(String file,HttpServletResponse response) throws IOException{
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
			
			
		 String paymentPassword = json.getString("paymentPassword");//提现人的支付密码
         String payee_account = json.getString("payee_account");//提现人的支付宝账号(账户)为支付宝的登录账号
         String memberAmount = json.getString("amount");//提现金额
         String payee_real_name = json.getString("payee_real_name");//提现人的支付宝实名认证的姓名
         BigDecimal balance = (new BigDecimal(memberAmount).multiply(new BigDecimal(Config.taxRate))).setScale(2,BigDecimal.ROUND_UP);//手续费 (进位取整,向上取整)
         Map<String,Object> data = new HashMap<String, Object>();
         data.put("payee_account", payee_account);
         data.put("amount", memberAmount);
         data.put("payee_real_name", payee_real_name);
         data.put("charge_money", String.valueOf(balance));
         
         if (member.getPaymentPassword()==null||member.getPaymentPassword().equals("")) {
				map.put("status", "400");
				map.put("message", "提现失败,请设置支付密码");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
         
         if((!member.getPaymentPassword().equals(DigestUtils.md5Hex(paymentPassword)))){
				map.put("status", "400");
				map.put("message", "支付密码输入有误,请重新输入");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
		}
         
         if (new BigDecimal(memberAmount).compareTo(member.getBalance())==1) {//判断如果提现金额大于用户的余额，不允许提现
        	 map.put("status", "400");
 			map.put("message", "提现金额不得大于所剩余额");
 			map.put("data", "{}");
 			printWriter.write(JsonUtils.toString(map));
 			return;
		}
         String amount = String.valueOf((new BigDecimal(memberAmount).subtract(balance)).setScale(2, BigDecimal.ROUND_HALF_DOWN));// 实际提现金额 = 提现金额-手续费 (向下取整)
		 Map<String,String> biz_content_map = new HashMap<String, String>();
		 biz_content_map.put("out_biz_no", DateUtil.getDatetoString("yyyyMMddHHmmssSSSS", new Date()));
		 biz_content_map.put("payee_type", "ALIPAY_LOGONID");
		 biz_content_map.put("payee_account", payee_account);
		 biz_content_map.put("amount", amount);
		 biz_content_map.put("payer_show_name", "神州儿女商户");
		 biz_content_map.put("payee_real_name",payee_real_name);
		 biz_content_map.put("remark", "退款转账");
		 AlipayFundTransToaccountTransferResponse transferresponse  = ZhiFuBaoPayUtil.getFundTransToaccountTransfer(biz_content_map);
			if(transferresponse.isSuccess()&&transferresponse.getCode().equals("10000")){
					logger.info("调用成功，success："+transferresponse.isSuccess()+"，code:"+transferresponse.getCode());
					member.setBalance(member.getBalance().subtract(new BigDecimal(memberAmount)));
					memberService.update(member);
					//保存提现日志
					Deposit deposit = new Deposit();
					deposit.setType( Deposit.Type.adminChargeback);
					deposit.setCredit(new BigDecimal(0));
					deposit.setDebit(new BigDecimal("-"+memberAmount));
					deposit.setBalance(member.getBalance());
					deposit.setOperator(member.getName());
					deposit.setMemo("支付宝账号:"+payee_account+",支付宝收款人："+payee_real_name+",提现："+memberAmount+"元,实际到账："+amount+"元，手续费："+balance+"元，已经到账，如有疑问请去支付宝账单查询,商户转账订单号："+transferresponse.getOutBizNo()+",支付宝转账单据号:"+transferresponse.getOrderId());
					deposit.setMember(member);
					depositService.save(deposit);
					logger.info("支付宝账号:"+payee_account+",支付宝收款人："+payee_real_name+",提现："+memberAmount+"元,实际到账："+amount+"元，手续费："+balance+"元，已经到账，如有疑问请去支付宝账单查询,商户转账订单号："+transferresponse.getOutBizNo()+",支付宝转账单据号:"+transferresponse.getOrderId());
					data.put("message", "支付宝账号:"+payee_account+",支付宝收款人："+payee_real_name+",提现："+memberAmount+"元,实际到账："+amount+"元，手续费："+balance+"元，已经到账，如有疑问请去支付宝账单查询,商户转账订单号："+transferresponse.getOutBizNo()+",支付宝转账单据号:"+transferresponse.getOrderId());
					data.put("status","200");
					map.put("status", "200");
				    map.put("data", JsonUtils.toJson(data));
				    map.put("message", "提现成功");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				 
			 } else {
				logger.info("调用失败，code："+transferresponse.getCode()+",message"+transferresponse.getMsg()+",错误描述:"+transferresponse.getSubCode()+",解决方案:"+transferresponse.getSubMsg());
				if(transferresponse.getSubCode().equals("SYSTEM_ERROR")){//判断系统繁忙
					AlipayFundTransOrderQueryRequest orderQueryrequest = new AlipayFundTransOrderQueryRequest();
					orderQueryrequest.setBizContent("{" +
					"\"out_biz_no\":\""+transferresponse.getOutBizNo()+"\"," +
					"\"order_id\":\""+transferresponse.getOrderId()+"\"" +
					"  }");
					AlipayFundTransOrderQueryResponse orderQueryresponse = ZhiFuBaoPayUtil.getAlipayClient().execute(orderQueryrequest);
					if(orderQueryresponse.isSuccess()){
					logger.info("系统繁忙，查询转账，调用成功");
					member.setBalance(member.getBalance().subtract(new BigDecimal(memberAmount)));
					memberService.update(member);
					//保存提现日志
					Deposit deposit = new Deposit();
					deposit.setType( Deposit.Type.adminChargeback);
					deposit.setCredit(new BigDecimal(0));
					deposit.setDebit(new BigDecimal("-"+memberAmount));
					deposit.setBalance(member.getBalance());
					deposit.setOperator(member.getName());
					deposit.setMemo("支付宝账号:"+payee_account+",支付宝收款人："+payee_real_name+",提现："+memberAmount+"元,实际到账："+amount+"元，手续费："+balance+"元，已经到账，如有疑问请去支付宝账单查询,商户转账订单号："+transferresponse.getOutBizNo()+",支付宝转账单据号:"+transferresponse.getOrderId());
					deposit.setMember(member);
					depositService.save(deposit);
					System.out.println("支付宝账号:"+payee_account+",支付宝收款人："+payee_real_name+",提现："+memberAmount+"元,实际到账："+amount+"元，手续费："+balance+"元，已经到账，如有疑问请去支付宝账单查询,商户转账订单号："+transferresponse.getOutBizNo()+",支付宝转账单据号:"+transferresponse.getOrderId());
					data.put("message", "支付宝账号:"+payee_account+",支付宝收款人："+payee_real_name+",提现："+memberAmount+"元,实际到账："+amount+"元，手续费："+balance+"元，已经到账，如有疑问请去支付宝账单查询,商户转账订单号："+transferresponse.getOutBizNo()+",支付宝转账单据号:"+transferresponse.getOrderId());
					data.put("status","200");
					map.put("status", "200");
				    map.put("data", JsonUtils.toJson(data));
				    map.put("message", "提现成功");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
					
					} else {
					System.out.println("系统繁忙，查询转账，调用失败");
					data.put("message",orderQueryresponse.getSubMsg() );
					data.put("status","400");
					map.put("status", "200");
					map.put("message", orderQueryresponse.getSubMsg());
					map.put("data", JsonUtils.toJson(data));
					printWriter.write(JsonUtils.toString(map)) ;
					return;
					}
				}
				data.put("message", transferresponse.getSubMsg());
				data.put("status", "400");
				map.put("status", "200");
				map.put("message", transferresponse.getSubMsg());
				map.put("data", JsonUtils.toJson(data));
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			 }
		 
			
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
	 * 微信用户提现
	 */
	//http://localhost:8080/shenzhou/app/putForward/charge.jhtml?file={safeKeyValue:"cdc65379381863360ece2743030d6d8d",amount:"0.01",payee_account:"15935962724",payee_real_name:"王双瑞",paymentPassword:""}
	@RequestMapping(value = "/weixinCharge", method = RequestMethod.GET)
	public void weixinCharge(String file,HttpServletResponse response) throws IOException{
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
			
			
		 String paymentPassword = json.getString("paymentPassword");//提现人的支付密码。 
         String memberAmount = json.getString("amount");//提现金额
         String re_user_name = json.getString("re_user_name");//提现人的(收款用户)真实姓名。 
         String openid = json.getString("openid");//用户openid
         String spbill_create_ip = json.getString("spbill_create_ip");//用户端或者服务端ip
         String partner_trade_no = DateUtil.getDatetoString("yyyyMMddHHmmssSSSS", new Date());//随机生成订单号
         
         
         BigDecimal balance = (new BigDecimal(memberAmount).multiply(new BigDecimal(Config.taxRate))).setScale(2,BigDecimal.ROUND_UP);//手续费 (进位取整,向上取整)
         Map<String,Object> data = new HashMap<String, Object>();
         data.put("amount", memberAmount);
         data.put("re_user_name", re_user_name);
         data.put("charge_money", String.valueOf(balance));
         data.put("partner_trade_no", partner_trade_no);
         if (member.getPaymentPassword()==null||member.getPaymentPassword().equals("")) {
				map.put("status", "400");
				map.put("message", "提现失败,请设置支付密码");
				map.put("data", new Object());
				printWriter.write(JSONObject.fromObject(map).toString()) ;
				return;
			}
         
         if((!member.getPaymentPassword().equals(DigestUtils.md5Hex(paymentPassword)))){
				map.put("status", "400");
				map.put("message", "支付密码输入有误,请重新输入");
				map.put("data", "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
		}
         
         if (new BigDecimal(memberAmount).compareTo(member.getBalance())==1) {//判断如果提现金额大于用户的余额，不允许提现
        	 map.put("status", "400");
 			map.put("message", "提现金额不得大于所剩余额");
 			map.put("data", "{}");
 			printWriter.write(JsonUtils.toString(map));
 			return;
		}
         Integer amount = (new BigDecimal(memberAmount).subtract(balance)).setScale(2, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100)).intValue();// 实际提现金额 = 提现金额-手续费 (向下取整)
		 Map<String,String> wxMap = new HashMap<String, String>();
			
		 Setting setting = SettingUtils.get(); 
		 String key = "AyhiJWf2jxDJJ283ZfHlyKrzRs6C6Dbz";//微信商户平台秘钥
		 wxMap.put("mch_appid",setting.getWxAppId());//应用IDappid是String(32)	wxd678efh567hg6787	微信开放平台审核通过的应用APPID
		 wxMap.put("mchid",setting.getWxMchId());//	商户号	mch_id	是	String(32)	1230000109	微信支付分配的商户号
		 wxMap.put("device_info","");//微信支付分配的终端设备号
		 wxMap.put("nonce_str",WeiXinUtil.getRandomStr(32));//随机字符串，不长于32位
		 wxMap.put("partner_trade_no",partner_trade_no);//商户订单号，需保持唯一性(只能是字母或者数字，不能包含有符号)
		 wxMap.put("openid",openid);//商户appid下，某用户的openid
		 wxMap.put("check_name","FORCE_CHECK");//NO_CHECK：不校验真实姓名  FORCE_CHECK：强校验真实姓名
		 wxMap.put("re_user_name",re_user_name);//收款用户真实姓名。 如果check_name设置为FORCE_CHECK，则必填用户真实姓名
		 wxMap.put("amount",amount.toString());//企业付款金额，单位为分
		 wxMap.put("desc","提现转账");//企业付款操作说明信息。必填。
		 wxMap.put("spbill_create_ip",spbill_create_ip);//该IP同在商户平台设置的IP白名单中的IP没有关联，该IP可传用户端或者服务端的IP。
		 
		 String content = ZhiFuBaoPayUtil.getContent(wxMap);//得到要签名的字符串
		 String stringSignTemp=content+"&key="+key;//注：key为商户平台设置的密钥key
		 String sign=DigestUtils.md5Hex(stringSignTemp).toUpperCase();
		 wxMap.put("sign",sign);//	签名	sign	是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名生成算法
		 String body = XmlUtil.getMapToXml(wxMap);
		
		 String xml = HttpUtil.post("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", body);
		 Map<String,Object> xml_map = XmlUtil.getXmlToMap(xml);
		 
			if(xml_map.get("return_code").equals("SUCCESS")){//通信标识 
				if(xml_map.get("result_code").equals("SUCCESS")){//业务标识
					logger.info("调用成功，success："+xml_map.get("return_code")+"，result_code:"+xml_map.get("return_code"));
					member.setBalance(member.getBalance().subtract(new BigDecimal(memberAmount)));
					memberService.update(member);
					//保存提现日志
					Deposit deposit = new Deposit();
					deposit.setType(Deposit.Type.adminChargeback);
					deposit.setCredit(new BigDecimal(0));
					deposit.setDebit(new BigDecimal("-"+memberAmount));
					deposit.setBalance(member.getBalance());
					deposit.setOperator(member.getName());
					deposit.setMemo("微信收款人："+re_user_name+",提现："+memberAmount+"元,实际到账："+amount+"元，手续费："+balance+"元，已经到账，如有疑问请去微信账单查询,商户转账订单号："+xml_map.get("partner_trade_no")+",微信转账单据号:"+xml_map.get("payment_no"));
					deposit.setMember(member);
					depositService.save(deposit);
					logger.info("微信收款人："+re_user_name+",提现："+memberAmount+"元,实际到账："+amount+"元，手续费："+balance+"元，已经到账，如有疑问请去微信账单查询,商户转账订单号："+xml_map.get("partner_trade_no")+",微信转账单据号:"+xml_map.get("payment_no"));
					data.put("message", "微信收款人："+re_user_name+",提现："+memberAmount+"元,实际到账："+amount+"元，手续费："+balance+"元，已经到账，如有疑问请去微信账单查询,商户转账订单号："+xml_map.get("partner_trade_no")+",微信转账单据号:"+xml_map.get("payment_no"));
					data.put("status","200");
					map.put("status", "200");
				    map.put("data", JsonUtils.toJson(data));
				    map.put("message", "提现成功");
					printWriter.write(JsonUtils.toString(map)) ;
					return;
				}else{//业务标识 错误的话就从新查询一次
					
					 Map<String,String> wx_query_map = new HashMap<String, String>();
						
					 wx_query_map.put("appid",setting.getWxAppId());//应用IDappid是String(32)	wxd678efh567hg6787	微信开放平台审核通过的应用APPID
					 wx_query_map.put("mch_id",setting.getWxMchId());//	商户号	mch_id	是	String(32)	1230000109	微信支付分配的商户号
					 wx_query_map.put("nonce_str",WeiXinUtil.getRandomStr(32));//随机字符串，不长于32位
					 wx_query_map.put("partner_trade_no",partner_trade_no);//商户订单号，需保持唯一性(只能是字母或者数字，不能包含有符号)
					 
					
					 String queryContent = ZhiFuBaoPayUtil.getContent(wx_query_map);//得到要签名的字符串
					 
					 String stringQuerySignTemp=queryContent+"&key="+key;//注：key为商户平台设置的密钥key
					 
					 String query_sign=DigestUtils.md5Hex(stringQuerySignTemp).toUpperCase();
					 wx_query_map.put("sign",query_sign);//	签名	sign	是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名生成算法
					 String query_body = XmlUtil.getMapToXml(wx_query_map);
					 String query_return_xml = HttpUtil.post("https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo", query_body);
					 Map<String,Object> xml_query_map = XmlUtil.getXmlToMap(query_return_xml);
					 if(xml_query_map.get("return_code").equals("SUCCESS")){
						 if(xml_query_map.get("result_code").equals("SUCCESS")){
							 if(xml_query_map.get("status").equals("SUCCESS")){
								 logger.info("查询成功，success："+xml_map.get("return_code")+"，result_code:"+xml_map.get("return_code"));
									member.setBalance(member.getBalance().subtract(new BigDecimal(memberAmount)));
									memberService.update(member);
									//保存提现日志
									Deposit deposit = new Deposit();
									deposit.setType(Deposit.Type.adminChargeback);
									deposit.setCredit(new BigDecimal(0));
									deposit.setDebit(new BigDecimal("-"+memberAmount));
									deposit.setBalance(member.getBalance());
									deposit.setOperator(member.getName());
									deposit.setMemo("微信收款人："+re_user_name+",提现："+memberAmount+"元,实际到账："+amount+"元，手续费："+balance+"元，已经到账，如有疑问请去微信账单查询,商户转账订单号："+xml_map.get("partner_trade_no")+",微信转账单据号:"+xml_map.get("payment_no"));
									deposit.setMember(member);
									depositService.save(deposit);
									logger.info("微信收款人："+re_user_name+",提现："+memberAmount+"元,实际到账："+amount+"元，手续费："+balance+"元，已经到账，如有疑问请去微信账单查询,商户转账订单号："+xml_map.get("partner_trade_no")+",微信转账单据号:"+xml_map.get("payment_no"));
									data.put("message", "微信收款人："+re_user_name+",提现："+memberAmount+"元,实际到账："+amount+"元，手续费："+balance+"元，已经到账，如有疑问请去微信账单查询,商户转账订单号："+xml_map.get("partner_trade_no")+",微信转账单据号:"+xml_map.get("payment_no"));
									data.put("status","200");
									map.put("status", "200");
								    map.put("data", JsonUtils.toJson(data));
								    map.put("message", "提现成功");
									printWriter.write(JsonUtils.toString(map));
									return;
							 }else{
								logger.info("查询失败:"+xml_query_map.get("status"));
								data.put("message",xml_query_map.get("status") );
								data.put("status","400");
								map.put("status", "200");
							    map.put("data", JsonUtils.toJson(data));
							    map.put("message", "提现失败");
								printWriter.write(JsonUtils.toString(map)) ;
								return;
							 }
							 
						 }else{
							 	logger.info("业务结果:"+xml_query_map.get("err_code"));
								data.put("message",xml_query_map.get("err_code"));
								data.put("status","400");
								map.put("status", "200");
							    map.put("data", JsonUtils.toJson(data));
							    map.put("message", "提现失败");
								printWriter.write(JsonUtils.toString(map)) ;
								return;
							 
						 }
						 
					 }else{
						 	logger.info("状态码:"+xml_query_map.get("return_code")+",结果信息:"+xml_query_map.get("return_msg"));
							data.put("message",xml_query_map.get("return_msg"));
							data.put("status","400");
							map.put("status", "200");
						    map.put("data", JsonUtils.toJson(data));
						    map.put("message", "提现失败");
							printWriter.write(JsonUtils.toString(map)) ;
							return;
						 
					 }
					 
					 
				}
				
				
					
				 
			 } else {
				 logger.info("状态码："+xml_map.get("return_code")+",返回信息:"+xml_map.get("return_msg")+",业务结果:"+xml_map.get("result_code")+",错误代码:"+xml_map.get("err_code"));
			 }
		 
			
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
	 * 提现页面
	 * @param file
	 * @param response
	 * @throws IOException
	 */
	//http://localhost:8080/shenzhou/app/putForward/balance.jhtml?file={safeKeyValue:"cdc65379381863360ece2743030d6d8d"}
	@RequestMapping(value = "/balance", method = RequestMethod.GET)
	public void balance(String file,HttpServletResponse response) throws IOException{
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
	   
		    Map<String ,Object> data = new HashMap<String, Object>();
		    data.put("balance",member.getBalance().setScale(2,BigDecimal.ROUND_HALF_UP));//四舍五入 ，保留两位小数
		    data.put("rate",Config.taxRate);//手续费
	        map.put("status", "200");
		    map.put("data", JsonUtils.toJson(data));
		    map.put("message", "数据加载成功");
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
	 * 提现计算
	 * @param file
	 * @param response
	 * @throws IOException
	 */
	//http://localhost:8080/shenzhou/app/putForward/calculation.jhtml?file={safeKeyValue:"cdc65379381863360ece2743030d6d8d",balance:"0.01"}
	@RequestMapping(value = "/calculation", method = RequestMethod.GET)
	public void calculation(String file,HttpServletResponse response) throws IOException{
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
	   
			String balance = json.getString("balance");
		    Map<String ,Object> data = new HashMap<String, Object>();
		    data.put("balance",member.getBalance().setScale(2,BigDecimal.ROUND_HALF_UP));//返回计算过的 四舍五入保留 两位小数
	        map.put("status", "200");
		    map.put("data", JsonUtils.toJson(data));
		    map.put("message", "数据加载成功");
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
	 * 获取验证码
	 * @param file
	 * @param response
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/putForward/sendCode.jhtml?file={safeKeyValue:"cdc65379381863360ece2743030d6d8d"}
	@RequestMapping(value = "/sendCode", method = RequestMethod.GET)
	public void sendCode(String file,HttpServletResponse response) throws IOException{
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
	   
			String mobile = member.getUsername();//手机号
			int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);// 生成验证码
			
			SendSmsResponse  data = ShortMessageUtil.seng_message(mobile,mobile_code,null);
			
			if(data.getCode().equals("OK")){
				redisCacheService.setCacheObject(mobile,mobile_code,10*60L);
				logger.info("缓存的验证码为:"+redisCacheService.getCacheObject(mobile).toString());
				map.put("status", "200");
				map.put("message", "验证码发送成功");
				map.put("data",  "{}");
				printWriter.write(JsonUtils.toString(map)) ;
				return;
			}
		    
	        map.put("status", "400");
		    map.put("data", data.getMessage());
		    map.put("message", "数据加载成功");
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
	 * 校验验证码
	 * @param file
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
//	http://localhost:8080/shenzhou/app/putForward/sendCode.jhtml?file={safeKeyValue:"cdc65379381863360ece2743030d6d8d",code:"152438"}
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public void check(String file,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        file = StringEscapeUtils.unescapeHtml(file);
        JSONObject json = JSONObject.fromObject(file);
        String safeKeyValue = json.getString("safeKeyValue");
        String code = json.getString("code");
		if(StringUtils.isEmpty(safeKeyValue)){
			map.put("status", "300");
			map.put("message", "还没登录请先登录");
			map.put("data", new Object());
			printWriter.write(JSONObject.fromObject(map).toString()) ;
			return;
		}
		Member member = memberService.findBySafeKeyValue(safeKeyValue);
		if(!redisCacheService.hasKey(member.getUsername())){
			map.put("status", "400");
			map.put("message", "验证码已失效");
			map.put("data", "{}");
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		String key = redisCacheService.getCacheObject(member.getUsername()).toString();
		logger.info("缓存的验证码为:"+key);
		Map<String ,Object> data = new HashMap<String ,Object>();
		if(code.equals(key)){
			map.put("status", "200");
			map.put("message", "校验成功");
			map.put("data", JsonUtils.toJson(data));
			printWriter.write(JsonUtils.toString(map)) ;
			return;
		}
		
		map.put("status", "400");
		map.put("message", "验证码输入有误");
		map.put("data", "{}");
		printWriter.write(JsonUtils.toString(map)) ;
		return;
			
	}
	
	
}
	
	
	
	


