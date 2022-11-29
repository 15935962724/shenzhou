/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shenzhou.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

/**
 * 支付宝支付
 * @date 2018-4-13 15:08:50
 * @author wsr
 *
 */
public class ZhiFuBaoPayUtil {
	
	private static final Logger logger = Logger.getLogger(ZhiFuBaoPayUtil.class.getName());

	public static String charset = "utf-8";
	
	public static String sign_type = "RSA2";
	
	/**
	 * 实例化
	 * @return
	 */
	public static AlipayClient getAlipayClient(){
		 String APP_PRIVATE_KEY = getPrivateKeyPkcs8();
		 String ALIPAY_PUBLIC_KEY = getZhiFuBaoPublicKey();
		 AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",SettingUtils.get().getZfbAppId(),APP_PRIVATE_KEY,"json",charset,ALIPAY_PUBLIC_KEY,sign_type);
		return alipayClient;
	}
	
	
	/**
	 * 支付宝私钥(未解码)
	 * @return
	 */
	 public static String getPrivateKey () {
			String privateKey = 
			"MIIEpAIBAAKCAQEAxX1AEkPNJ/gwz4Qwe3OIG8TYmlTc8Jt0G8Uw/9lGMuT8ZRZu"+
			"dL7NucOHVw0k1F8f7jUJYFt0vHdZrELSgG3zuTacg7TYasfS/UY62Dkx1we0Ej72"+
			"8GK1ooBtzhvY7sScBqwMtaOHCQEVuz3mC5EXouGO4LdErMRKLBsF+x8zF8S1Yim1"+
			"66e3SWcjAvn39Z2aV73Onia84Qhvwi4ZQQeAAycvs/bZy5NQtA6/kZYtq9K3ZKP0"+
			"tKaCyn0IL0ZrfIuZI2LGFbI4unHADoUHVGl/To7ffpIYopQWUC3KyHuI+UoGlhGw"+
			"V/sBM03M62EOUteZL7PC+2kr5NQuZ2p3YC1NwQIDAQABAoIBAC7WWLa8O9DJ7RGa"+
			"QUFWEia71b8sd0XdKHlZmBhluF9jrXRwEHxBsFrpQ5TiuiRf9xuwmmuu5Q25pJvV"+
			"MUViTlZT3GTkJBuXDrQgzt3vGqHr4sodAsXaM0e5+9DRC+781CBE97KweNsq1bOV"+
			"p5SAscFgi1lCPdhXqVuygQWgzaZKBy7Lln6uZhxP00+uBJyvIjpxHpK2ojPCl6ui"+
			"4TL51Fk4UdMKrgNNBt5Z83duYXWKCCbo8KcqOIeBA5wIzrS/qydORdcFM495A8Ix"+
			"I9sGrLiGyDSZzmRsfdpS3KUJymRbBK+Gi21C0Vp8cnYXYajfoELReY6r8sZPlCqb"+
			"iJSQadECgYEA4iXf2m7Y88OyVsL7XOf6P5CJXj6vtKoG9NZMSCv9gnVQIyny3WjN"+
			"6H/N535c5Zh5KcRXoU4x9fHLNrqIK+HwdzRuV6kuOsSn/5RDQW0sdrlpafEMLTv5"+
			"7wzaxUODAMkuDveDkASBckgMEaAPNsvR/wLVvrYXLWTOJIKXQ+7rN90CgYEA347s"+
			"qbFmR1s/Gzens+20w+p6keBkY5HknerV3KWVRozbdlsRB5HPXm5QhuLR1SE1t/j9"+
			"bFF5WR4K69+x/6xKADPPiQCZwaJfUOt+K1vCDY2pFDCUi58UyaXnkabLP2cUr8hn"+
			"G8zGjCWdY/WHHISUXaObYzhunMbXNJDQcoGQYTUCgYBWuCVWEsmY+EMr6AsRvlcF"+
			"BvzWKf2grs6KFp8b0dqdqzS2t0BNTQIglNm1Woxu8oAL1yGvVlfsMvM1ImRTR4m5"+
			"fF6PuO1kVcMCS61aqm2xfToCCRAdTHF0DJ7bpB7ZL0w9KwbFNCUbWKtgGtuarJ/z"+
			"RPgH8LWEj7JI59bvku+Z9QKBgQDDWQ2+PnDkO3yKWPinBjil1ZfjLpQqWYrO3yyf"+
			"kOU+78i5xFu+JJBysKyXIU5AEbPyHZW89/i0gccDU8YjZraHNL7NtYOlqy/k8tKe"+
			"KqEH3Nh49vZmhszQY7NVF82UiouOCuzmYuq//gJpHVxB9Cv9IwCeE+q7/hiBK2Wq"+
			"GUNDXQKBgQDSR3pXoU4aKm4SalKRQFJc7pDiBvER68k5vIUncOMToLmjK2Gye/8c"+
			"IFlvWct7y5arj/DP+Ir1cAQNq464i0jOQH9FZZgQYngW6iai54WMdhNq9cuOvyTD"+
			"Vr76Wt9KkO0cq994+W+HhYVqi5lFd8dI/kDwfCqGMXscjPR8HWtV4g==";		
			return privateKey;
		 }
	 
	 
	 /**
	  * 支付宝私钥(已经解码)
	  * @return
	  */
	 public static String getPrivateKeyPkcs8 () {
			String privateKeyPkcs8 = 
				"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDFfUASQ80n+DDP"+
				"hDB7c4gbxNiaVNzwm3QbxTD/2UYy5PxlFm50vs25w4dXDSTUXx/uNQlgW3S8d1ms"+
				"QtKAbfO5NpyDtNhqx9L9RjrYOTHXB7QSPvbwYrWigG3OG9juxJwGrAy1o4cJARW7"+
				"PeYLkRei4Y7gt0SsxEosGwX7HzMXxLViKbXrp7dJZyMC+ff1nZpXvc6eJrzhCG/C"+
				"LhlBB4ADJy+z9tnLk1C0Dr+Rli2r0rdko/S0poLKfQgvRmt8i5kjYsYVsji6ccAO"+
				"hQdUaX9Ojt9+khiilBZQLcrIe4j5SgaWEbBX+wEzTczrYQ5S15kvs8L7aSvk1C5n"+
				"andgLU3BAgMBAAECggEALtZYtrw70MntEZpBQVYSJrvVvyx3Rd0oeVmYGGW4X2Ot"+
				"dHAQfEGwWulDlOK6JF/3G7Caa67lDbmkm9UxRWJOVlPcZOQkG5cOtCDO3e8aoevi"+
				"yh0CxdozR7n70NEL7vzUIET3srB42yrVs5WnlICxwWCLWUI92FepW7KBBaDNpkoH"+
				"LsuWfq5mHE/TT64EnK8iOnEekraiM8KXq6LhMvnUWThR0wquA00G3lnzd25hdYoI"+
				"Jujwpyo4h4EDnAjOtL+rJ05F1wUzj3kDwjEj2wasuIbINJnOZGx92lLcpQnKZFsE"+
				"r4aLbULRWnxydhdhqN+gQtF5jqvyxk+UKpuIlJBp0QKBgQDiJd/abtjzw7JWwvtc"+
				"5/o/kIlePq+0qgb01kxIK/2CdVAjKfLdaM3of83nflzlmHkpxFehTjH18cs2uogr"+
				"4fB3NG5XqS46xKf/lENBbSx2uWlp8QwtO/nvDNrFQ4MAyS4O94OQBIFySAwRoA82"+
				"y9H/AtW+thctZM4kgpdD7us33QKBgQDfjuypsWZHWz8bN6ez7bTD6nqR4GRjkeSd"+
				"6tXcpZVGjNt2WxEHkc9eblCG4tHVITW3+P1sUXlZHgrr37H/rEoAM8+JAJnBol9Q"+
				"634rW8INjakUMJSLnxTJpeeRpss/ZxSvyGcbzMaMJZ1j9YcchJRdo5tjOG6cxtc0"+
				"kNBygZBhNQKBgFa4JVYSyZj4QyvoCxG+VwUG/NYp/aCuzooWnxvR2p2rNLa3QE1N"+
				"AiCU2bVajG7ygAvXIa9WV+wy8zUiZFNHibl8Xo+47WRVwwJLrVqqbbF9OgIJEB1M"+
				"cXQMntukHtkvTD0rBsU0JRtYq2Aa25qsn/NE+AfwtYSPskjn1u+S75n1AoGBAMNZ"+
				"Db4+cOQ7fIpY+KcGOKXVl+MulCpZis7fLJ+Q5T7vyLnEW74kkHKwrJchTkARs/Id"+
				"lbz3+LSBxwNTxiNmtoc0vs21g6WrL+Ty0p4qoQfc2Hj29maGzNBjs1UXzZSKi44K"+
				"7OZi6r/+AmkdXEH0K/0jAJ4T6rv+GIErZaoZQ0NdAoGBANJHelehThoqbhJqUpFA"+
				"UlzukOIG8RHryTm8hSdw4xOguaMrYbJ7/xwgWW9Zy3vLlquP8M/4ivVwBA2rjriL"+
				"SM5Af0VlmBBieBbqJqLnhYx2E2r1y46/JMNWvvpa30qQ7Ryr33j5b4eFhWqLmUV3"+
				"x0j+QPB8KoYxexyM9Hwda1Xi";	
			return privateKeyPkcs8;
		 }
	 
	 /**
	  * 支付宝公钥
	  * @return
	  */
	 public static String getZhiFuBaoPublicKey () {
			String zhiFuBaopublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiuW6EDenjTsquGB/400drG9WHn6dKiHRHZiwrnlEGXHntLrX4ed8H9RCfclcSMhIS9sFn98IPVNi5wylR1i1BG5SI1LM+QUAlIe5DOFpJv1ZY2J9i6uz2JHjkDlmgM2IrONl6dDHYxKI/BwszTptdjiPdCNwU65dZMglDAVMnYeDxNO/aBF/Y+jWe5a8UwZ3QkTpsbnxBCE4Cfi3iRF8tQoQO4t1u2uGCFQWdlxv0mQIGmIbWyEF+PdVdjjgzFRmRk1yB/WUwvFcmPuEF3G0gN+8nVqqevXqYT2SsdrlFZyOt3Wp4b6FFBGkowftVrB4GaSDDhZIAd6N+eYvdfuriwIDAQAB";		
			return zhiFuBaopublicKey;
		 }
	 
	 /**
	  * 应用公钥
	  * @return
	  */
	 public static String getYingYongPublicKey () {
		 								
			String yingYongPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxX1AEkPNJ/gwz4Qwe3OI"+
								"G8TYmlTc8Jt0G8Uw/9lGMuT8ZRZudL7NucOHVw0k1F8f7jUJYFt0vHdZrELSgG3z"+
								"uTacg7TYasfS/UY62Dkx1we0Ej728GK1ooBtzhvY7sScBqwMtaOHCQEVuz3mC5EX"+
								"ouGO4LdErMRKLBsF+x8zF8S1Yim166e3SWcjAvn39Z2aV73Onia84Qhvwi4ZQQeA"+
								"Aycvs/bZy5NQtA6/kZYtq9K3ZKP0tKaCyn0IL0ZrfIuZI2LGFbI4unHADoUHVGl/"+
								"To7ffpIYopQWUC3KyHuI+UoGlhGwV/sBM03M62EOUteZL7PC+2kr5NQuZ2p3YC1N"+
								"wQIDAQAB";		
			return yingYongPublicKey;
		 }
	
	 
	    /** 
	     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	     * @param params 需要排序并参与字符拼接的参数组
	     * @return 拼接后字符串
	     */
	    public static String getContent(Map<String, String> params) {

	        List<String> keys = new ArrayList<String>(params.keySet());
	        Collections.sort(keys);
	        String prestr = "";
	        for (int i = 0; i < keys.size(); i++) {
	            String key = keys.get(i);
	            String value = params.get(key);

	            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
	                prestr = prestr + key + "=" + value;
	            } else {
	                prestr = prestr + key + "=" + value + "&";
	            }
	        }

	        return prestr;
	    }
	
	    
	    /**
	     * 支付宝支付成功之后的查询
	     * @param params
	     * @return
	     */
	    public static Boolean getTradeQuery(Map<String, String> params) {
	    	AlipayClient alipayClient =  getAlipayClient();
	    	AlipayTradeQueryRequest tradeQueryrequest = new AlipayTradeQueryRequest();
	  		tradeQueryrequest.setBizContent("{" +
	  		"\"out_trade_no\":\""+params.get("out_trade_no")+"\"," +
	  		"\"trade_no\":\""+params.get("trade_no")+"\"" +
	  		"  }");
	  		AlipayTradeQueryResponse tradeQueryresponse = null;
			try {
				tradeQueryresponse = alipayClient.execute(tradeQueryrequest);
				logger.info("body:"+tradeQueryresponse.getBody()+",查询结果:"+tradeQueryresponse.isSuccess()+",问题描述:"+tradeQueryresponse.getSubCode()+",解决方案:"+tradeQueryresponse.getSubMsg());
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	        return tradeQueryresponse.isSuccess();
	    }
	    

	    /**
	     * 支付  返回支付orderString
	     * @param params
	     * @return
	     */
	    public static String getTradeAppPay(Map<String, String> params) {
	    	AlipayClient alipayClient =  getAlipayClient();
	    	//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
			AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
			//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setBody(JsonUtils.toJson(params));//此处放用户信息，待异步通知回来之后做查询使用
			model.setSubject("好康护健康账户充值");
			model.setOutTradeNo(params.get("outTradeNo"));
			model.setTimeoutExpress("30m");
			model.setTotalAmount(params.get("totalAmount"));
			model.setProductCode("QUICK_MSECURITY_PAY");
			request.setBizModel(model);//request.setBizContent(bizContent);
			request.setNotifyUrl(SettingUtils.get().getSiteUrl()+"/app/payment/alipayAsynchronousNotice.jhtml");
			 //这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse alipayTradeAppPayResponse  = null;
	        try {
				 alipayTradeAppPayResponse = alipayClient.sdkExecute(request);
				 System.out.println("支付返回orderString："+alipayTradeAppPayResponse.getBody());
//				 logger.info("支付返回orderString："+StringEscapeUtils.unescapeHtml(alipayTradeAppPayResponse.getBody()));
				return StringEscapeUtils.unescapeHtml(alipayTradeAppPayResponse.getBody());
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return StringEscapeUtils.unescapeHtml(alipayTradeAppPayResponse.getBody());
	    }
	
	   
	   /**
	    * 提现 
	    * @param params
	    * @return
	    */
	    public static AlipayFundTransToaccountTransferResponse getFundTransToaccountTransfer(Map<String, String> params) {
	    	AlipayClient alipayClient =  getAlipayClient();
	    	AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
			String	biz_content = JsonUtils.toJson(params);
			request.setBizContent(biz_content);
			AlipayFundTransToaccountTransferResponse transferresponse = null;
			try {
				transferresponse = alipayClient.execute(request);
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return transferresponse;
	    }
	    
}