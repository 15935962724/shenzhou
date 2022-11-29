/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shenzhou.Setting;
import net.shenzhou.entity.Deposit;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.PlatformRechargeLog;
import net.shenzhou.entity.PlatformRechargeLog.RechargeMode;
import net.shenzhou.service.DepositService;
import net.shenzhou.service.MemberService;
import net.shenzhou.service.PaymentService;
import net.shenzhou.service.PlatformRechargeLogService;
import net.shenzhou.service.SnService;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.HttpUtil;
import net.shenzhou.util.JsonUtils;
import net.shenzhou.util.SettingUtils;
import net.shenzhou.util.WeiXinUtil;
import net.shenzhou.util.XmlUtil;
import net.shenzhou.util.ZhiFuBaoPayUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.internal.util.AlipaySignature;

/**
 * 支付
 * @date 2018-4-20 16:31:09
 * @author wsr
 *
 */
@Controller("appPaymentController")
@RequestMapping("/app/payment")
public class PaymentController extends BaseController {

	/** logger */
	private static final Logger logger = Logger.getLogger(PaymentController.class.getName());
	
	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "snServiceImpl")
	private SnService snService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "platformRechargeLogServiceImpl")
	private PlatformRechargeLogService platformRechargeLogService;
	
	

	/**
	 * 支付宝用户充值
	 */
	//http://localhost:8080/shenzhou/app/payment/alipayRecharge.jhtml?file={safeKeyValue:"cdc65379381863360ece2743030d6d8d",totalAmount:"0.01"}
	@RequestMapping(value = "/alipayRecharge", method = RequestMethod.GET)
	public void alipayRecharge(String file,HttpServletResponse response) throws IOException{
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

			Map<String, String> params = new HashMap<String, String>();
			String outTradeNo = DateUtil.getDatetoString("yyyyMMddHHmmssSSSS", new Date());//随机生成订单号
			String totalAmount =  json.getString("totalAmount"); // 支付金额
			params.put("safeKeyValue", safeKeyValue);
			params.put("memberId", member.getId().toString());
			params.put("memberName", member.getName());
			params.put("outTradeNo", outTradeNo);
			params.put("totalAmount", totalAmount);

			Map<String ,Object> data = new HashMap<String, Object>();
			data.put("data",  new String(ZhiFuBaoPayUtil.getTradeAppPay(params).getBytes("ISO-8859-1"), "utf-8"));
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
	 * 支付宝异步通知
	 */
	//http://localhost:8080/shenzhou/app/payment/alipayAsynchronousNotice.jhtml?file={}
	@RequestMapping(value = "/alipayAsynchronousNotice", method = RequestMethod.POST)
	public void alipayAsynchronousNotice(String file,HttpServletRequest request,HttpServletResponse response) throws IOException{
		System.out.println("支付宝异步通知开始回调");
		Map<String ,Object> map = new HashMap<String ,Object>();
		PrintWriter printWriter = response.getWriter();
		try {
			response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html; charset=utf-8");
	       
			Setting setting = SettingUtils.get();
			String APP_PRIVATE_KEY = ZhiFuBaoPayUtil.getPrivateKeyPkcs8();
			String charset = ZhiFuBaoPayUtil.charset;
			String ALIPAY_PUBLIC_KEY = ZhiFuBaoPayUtil.getZhiFuBaoPublicKey();
//			String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiuW6EDenjTsquGB/400drG9WHn6dKiHRHZiwrnlEGXHntLrX4ed8H9RCfclcSMhIS9sFn98IPVNi5wylR1i1BG5SI1LM+QUAlIe5DOFpJv1ZY2J9i6uz2JHjkDlmgM2IrONl6dDHYxKI/BwszTptdjiPdCNwU65dZMglDAVMnYeDxNO/aBF/Y+jWe5a8UwZ3QkTpsbnxBCE4Cfi3iRF8tQoQO4t1u2uGCFQWdlxv0mQIGmIbWyEF+PdVdjjgzFRmRk1yB/WUwvFcmPuEF3G0gN+8nVqqevXqYT2SsdrlFZyOt3Wp4b6FFBGkowftVrB4GaSDDhZIAd6N+eYvdfuriwIDAQAB";
			
			String sign_type = ZhiFuBaoPayUtil.sign_type;

			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			logger.info("支付宝回调数据转json:>"+JsonUtils.toJson(requestParams));
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			    String name = (String) iter.next();
			    String[] values = (String[]) requestParams.get(name);
			    String valueStr = "";
			    for (int i = 0; i < values.length; i++) {
			        valueStr = (i == values.length - 1) ? valueStr + values[i]
			                    : valueStr + values[i] + ",";
			  	}
//				try {
//					//乱码解决，这段代码在出现乱码时使用。
//					valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				params.put(name, valueStr);
			}
			

			
			boolean flag = false;
			logger.info("异步通知结果响应码:"+params.get("trade_status"));
				if(params.get("trade_status").equals("TRADE_CLOSED")){ //交易关闭
					logger.info("====交易关闭");
				}
				if(params.get("trade_status").equals("TRADE_SUCCESS")){ //支付成功
					logger.info("====支付成功");
					String body = params.get("body");
					JSONObject json = JSONObject.fromObject(body);
//					
					//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
					//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
					flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, charset,sign_type);
					logger.info("支付宝证异步通知结果:"+flag+",结果为:true就给用户加钱,并保存日志");
					if (flag) {
						
						
						String outTradeNo = params.get("out_trade_no");//商户订单号
						String tradeNo = params.get("trade_no");//支付宝订单号(交易号)
						PlatformRechargeLog platformRechargeLog = platformRechargeLogService.getPlatformRechargeLog(outTradeNo, tradeNo); 
						if (platformRechargeLog==null) {//此处判断，为防止处理成功后支付宝多次通知，防止多次给用户加钱
							/*	params.remove("sign");
							params.remove("sign_type");
							
							//除去sign、sign_type两个参数外，凡是通知返回回来的参数皆是待验签的参数。
							String content = ZhiFuBaoPayUtil.getContent(requestParams);
							
							//签名参数
		//					String sign = AlipaySignature.rsaSign(content, APP_PRIVATE_KEY, ZhiFuBaoPayUtil.charset,ZhiFuBaoPayUtil.sign_type);
							String sign = RSAUtils.sign(content, APP_PRIVATE_KEY, ZhiFuBaoPayUtil.charset, ZhiFuBaoPayUtil.sign_type);
							*/
							Long memberId  = json.getLong("memberId");
							BigDecimal totalAmount  = new BigDecimal(json.getDouble("totalAmount")).setScale(2,BigDecimal.ROUND_HALF_UP);//两位之后删除多余的小数位
							Member member = memberService.find(memberId);
							member.setBalance(member.getBalance().add(totalAmount));
							memberService.update(member);
							//保存充值日志
							Deposit deposit = new Deposit();
							deposit.setType(Deposit.Type.adminRecharge);
							deposit.setCredit(totalAmount);
							deposit.setDebit(new BigDecimal(0));
							deposit.setBalance(member.getBalance());
							deposit.setOperator(member.getName());
							deposit.setMemo("支付宝充值："+totalAmount+"元,如有疑问请去支付宝账单查询,支付宝支付商户订单号:"+outTradeNo+",支付宝支付订单号："+tradeNo);
							deposit.setMember(member);
							depositService.save(deposit);
							platformRechargeLog = new PlatformRechargeLog();
							platformRechargeLog.setRechargeMode(RechargeMode.Alipay);
							platformRechargeLog.setOperator(member.getName());
							platformRechargeLog.setMobile(member.getUsername());
							platformRechargeLog.setOutTradeNo(outTradeNo);
							platformRechargeLog.setTradeNo(tradeNo);
							platformRechargeLog.setMoney(totalAmount);
							platformRechargeLog.setRemarks("支付宝充值："+totalAmount+"元,如有疑问请去支付宝账单查询,支付宝支付商户订单号:"+outTradeNo+",支付宝支付订单号："+tradeNo);
							platformRechargeLog.setMember(member);
							platformRechargeLog.setIsDeleted(false);
							platformRechargeLogService.save(platformRechargeLog);
							String success = "输出给支付宝:success";
							logger.info(success);
						}else{
							logger.info("该交易已处理 ,请前往支付宝账单查询,支付宝支付商户订单号:"+outTradeNo+",支付宝支付订单号："+tradeNo);
						}
					
						printWriter.write("success".toString());
						return ;
					}
				}
				if(params.get("trade_status").equals("TRADE_FINISHED")){ //交易完结
					logger.info("====交易完结");
				}
				if(params.get("trade_status").equals("WAIT_BUYER_PAY")){ //交易创建
					logger.info("====交易创建");
				}
		    map.put("status", "200");
		    map.put("data", flag);
		    map.put("message", "数据加载成功");
		    logger.info("=====");
			return ;
		}catch (Exception e) {
			logger.info(">>>>"+e.getMessage());
			return ;
		}
	}
	
	
		/**
		 * 微信充值接口
		 * @param file
		 * @param response
		 * @throws IOException
		 */
		//http://localhost:8080/shenzhou/app/payment/weixinRecharge.jhtml?file={safeKeyValue:"cdc65379381863360ece2743030d6d8d",totalAmount:"0.01",spbill_create_ip:"123.12.12.123"}
		@RequestMapping(value = "/weixinRecharge", method = RequestMethod.GET)
		public void weixinRecharge(String file,HttpServletResponse response) throws IOException{
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

				Setting setting = SettingUtils.get();
				String key = "AyhiJWf2jxDJJ283ZfHlyKrzRs6C6Dbz";//微信商户平台秘钥
				Map<String, String> params = new HashMap<String, String>();
				String outTradeNo = DateUtil.getDatetoString("yyyyMMddHHmmssSSSS", new Date());//随机生成订单号
				String totalAmount =  json.getString("totalAmount"); //支付金额
				String spbill_create_ip =  json.getString("spbill_create_ip"); //终端ip
				Integer total_fee = (int) (Double.valueOf(totalAmount)*100);
				Map<String, String> attach = new HashMap<String, String>();
				attach.put("outTradeNo", outTradeNo);
				attach.put("totalAmount", totalAmount);
				attach.put("total_fee", String.valueOf(Double.valueOf(totalAmount)*100) );
				attach.put("memberId", String.valueOf(member.getId()));
				
				Map<String, String> wxMap = new HashMap<String, String>();
				// 调用获取随机字符串的方法
				String nonce_str = WeiXinUtil.getRandomStr(32);

				wxMap.put("appid",setting.getWxAppId());//应用IDappid是String(32)	wxd678efh567hg6787	微信开放平台审核通过的应用APPID
				wxMap.put("mch_id",setting.getWxMchId());//	商户号	mch_id	是	String(32)	1230000109	分配的商户号
				wxMap.put("device_info","WEB");//	设备号	device_info	否	String(32)	1.3467E+13	终端设备号(门店号或收银设备ID)，默认请传"WEB"
				wxMap.put("nonce_str",nonce_str);//	随机字符串	nonce_str	是	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位。推荐随机数生成算法
				
				wxMap.put("sign_type","MD5");//	签名类型	sign_type	否	String(32)	HMAC-SHA256	签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
				wxMap.put("body","好康护健康账户充值");//	商品描述	body	是	String(128)	腾讯充值中心-QQ会员充值	商品描述交易字段格式根据不同的应用场景按照以下格式：
													//APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
				wxMap.put("detail","好康护健康账户充值");//	商品详情	detail	否	String(8192)		商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
				wxMap.put("attach",JsonUtils.toJson(attach));//	附加数据	attach	否	String(127)	深圳分店	附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
				wxMap.put("out_trade_no",outTradeNo);////	商户订单号	out_trade_no	是	String(32)	2.01508E+13	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一。详见商户订单号
				wxMap.put("fee_type","CNY");//	货币类型	fee_type	否	String(16)		符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
				wxMap.put("total_fee",total_fee.toString());//	总金额	total_fee	是	Int	888	订单总金额，单位为分，详见支付金额
				wxMap.put("spbill_create_ip",spbill_create_ip);//	终端IP	spbill_create_ip	是	String(16)	123.12.12.123	用户端实际ip
//				wxMap.put("time_start",time_start);//	交易起始时间	time_start	否	String(14)	2.00912E+13	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
//				wxMap.put("time_expire",time_expire);//	交易结束时间	time_expire	否	String(14)	2.00912E+13	订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id。其他详见时间规则 建议：最短失效时间间隔大于1分钟
//				wxMap.put("goods_tag",goods_tag);//	订单优惠标记	goods_tag	否	String(32)	WXG	订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
				wxMap.put("notify_url",setting.getSiteUrl()+"/app/payment/weixinAsynchronousNotice.jhtml");//	通知地址	notify_url	是	String(256)	http://www.weixin.qq.com/wxpay/pay.php	接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
				wxMap.put("trade_type","APP");//	交易类型	trade_type	是	String(16)	APP	支付类型
//				wxMap.put("limit_pay",limit_pay);//	指定支付方式	limit_pay	否	String(32)	no_credit	no_credit--指定不能使用信用卡支付
				wxMap.put("scene_info","{}");//	场景信息	scene_info	否	String(256)	{	该字段用于统一下单时上报场景信息，目前支持上报实际门店信息。
															//"store_id": "SZT10000",	{
															//"store_name":"腾讯大厦腾大餐厅"	"store_id": "", //门店唯一标识，选填，String(32)
															//}	"store_name":"”//门店名称，选填，String(64)
															//}
				String content = ZhiFuBaoPayUtil.getContent(wxMap);//得到要签名的字符串
				String stringSignTemp=content+"&key="+key;//注：key为商户平台设置的密钥key
				String sign=DigestUtils.md5Hex(stringSignTemp).toUpperCase();
				wxMap.put("sign",sign);//	签名	sign	是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名生成算法
				String body = XmlUtil.getMapToXml(wxMap);
				
				String xml = HttpUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder", body);
				Map<String,Object> xml_map = XmlUtil.getXmlToMap(xml);
				Map<String,String> data_map = new HashMap<String, String>();
				if (xml_map.get("return_code").equals("SUCCESS")) {
					data_map.put("appid",xml_map.get("appid").toString());
					data_map.put("noncestr", WeiXinUtil.getRandomStr(32));
					data_map.put("package", "Sign=WXPay");
					data_map.put("partnerid", xml_map.get("mch_id").toString());
					data_map.put("prepayid", xml_map.get("prepay_id").toString());
					data_map.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
					
					String content2 = ZhiFuBaoPayUtil.getContent(data_map);//得到要签名的字符串 二次签名
					
					String stringSignTemp2=content2+"&key="+key;//注：key为商户平台设置的密钥key 二次
					
					String sign2=DigestUtils.md5Hex(stringSignTemp2).toUpperCase();
					data_map.put("sign", sign2);
					map.put("status", "200");
				}else{
					logger.info("状态码："+xml_map.get("return_code")+",返回信息:"+xml_map.get("return_msg"));
					map.put("status", "400");
				}

			    map.put("data", JsonUtils.toJson(data_map));
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
		 * 微信支付异步通知
		 * @param file
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		//http://localhost:8080/shenzhou/app/payment/alipayAsynchronousNotice.jhtml?file={}
		@RequestMapping(value = "/weixinAsynchronousNotice", method = RequestMethod.POST)
		public void weixinAsynchronousNotice(String file,HttpServletRequest request,HttpServletResponse response) throws IOException{
			PrintWriter printWriter = response.getWriter();
			logger.info("微信异步通知开始回调");
		        String inputLine;  
		        String notityXml = "";  
	            while ((inputLine = request.getReader().readLine()) != null) {  
	                notityXml += inputLine;  
	            }  
	            request.getReader().close();  
		       
		        System.out.println("接收到的报文：" + notityXml);  
		       
		        try {
					Map<String, Object> notity_map = XmlUtil.getXmlToMap(notityXml);
					 List<String> keys = new ArrayList<String>(notity_map.keySet());
				        Collections.sort(keys);
				        String content = "";
				        for (int i = 0; i < keys.size(); i++) {
				            String key = keys.get(i);
				            String value = (String) notity_map.get(key);

				            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
				            	content = content + key + "=" + value;
				            } else {
				            	content = content + key + "=" + value + "&";
				            }
				        }

					String key = "AyhiJWf2jxDJJ283ZfHlyKrzRs6C6Dbz";
					String stringSignTemp=content+"&key="+key;//注：key为商户平台设置的密钥key
					String sign=DigestUtils.md5Hex(stringSignTemp).toUpperCase();
					logger.info("sign:"+sign);
					logger.info("notity_map_sign:"+notity_map.get("sign"));
					logger.info("验签结果:"+sign.equals(notity_map.get("sign")));
					JSONObject json = JSONObject.fromObject(notity_map.get("attach"));//解析自定义参数
					logger.info("json:"+json);
					Member member = memberService.find(Long.valueOf(json.getString("memberId")));//获取充值的用户
					
//					BigDecimal totalAmount = new BigDecimal(json.getString("totalAmount"));//获取用户充值的金额
//					String outTradeNo =  json.getString("outTradeNo");//获取订单编号
					double tatalfee = Double.valueOf(notity_map.get("total_fee").toString())/100;
					logger.info("tatalfee:"+tatalfee);
					BigDecimal totalAmount = new BigDecimal(tatalfee).setScale(2,BigDecimal.ROUND_HALF_UP);//获取用户充值的金额
					String outTradeNo =  notity_map.get("out_trade_no").toString();//获取商户订单编号
					String tradeNo = notity_map.get("transaction_id").toString();//微信交易单号(交易号)
					
					PlatformRechargeLog platformRechargeLog = platformRechargeLogService.getPlatformRechargeLog(outTradeNo, tradeNo); 
					if (platformRechargeLog==null) {//此处判断，为防止处理成功后微信多次通知，防止多次给用户加钱
						member.setBalance(member.getBalance().add(totalAmount));
						memberService.update(member);
						//保存充值日志
						Deposit deposit = new Deposit();
						deposit.setType( Deposit.Type.adminRecharge );
						deposit.setCredit(totalAmount);
						deposit.setDebit(new BigDecimal(0));
						deposit.setBalance(member.getBalance());
						deposit.setOperator(member.getName());
						deposit.setMemo("微信充值："+totalAmount+"元,如有疑问请去微信账单查询,商户单号："+outTradeNo+",微信支付交易单号:"+tradeNo);
						deposit.setMember(member);
						depositService.save(deposit);
						
						platformRechargeLog = new PlatformRechargeLog();
						platformRechargeLog.setRechargeMode(RechargeMode.WeChat);
						platformRechargeLog.setOperator(member.getName());
						platformRechargeLog.setMobile(member.getUsername());
						platformRechargeLog.setOutTradeNo(outTradeNo);
						platformRechargeLog.setTradeNo(tradeNo);
						platformRechargeLog.setMoney(totalAmount);
						platformRechargeLog.setRemarks("微信充值："+totalAmount+"元,如有疑问请去微信账单查询,微信支付商户单号："+outTradeNo+",微信支付交易单号:"+tradeNo);
						platformRechargeLog.setMember(member);
						platformRechargeLog.setIsDeleted(false);
						platformRechargeLogService.save(platformRechargeLog);
					}
					
					
					Map<String,String> data = new HashMap<String, String>();
					data.put("return_code", "SUCCESS");
					data.put("return_msg", "OK");
					logger.info(XmlUtil.getMapToXml(data));
					printWriter.write(XmlUtil.getMapToXml(data));
					return ;
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        
		       
		}
}
	
	
	
	


