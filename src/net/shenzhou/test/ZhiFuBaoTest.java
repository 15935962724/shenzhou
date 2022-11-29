
package net.shenzhou.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;

import net.sf.json.JSONObject;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.HttpUtil;
import net.shenzhou.util.RSAUtils;


public class ZhiFuBaoTest {
	
	 static String  zfbAppId = "2088031869534126";
	
	 static String  zfbAesKey = "0Mf6tQgeGNlolYNDV8RG6A";
	
	 
	
	 
	 public static String readInputStream2String(String inputStream){
		//1、利用System.getProperty()函数获取当前路径： 
			System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径 
			
			 File file = new File(System.getProperty("user.dir")+"/src/"+inputStream);  
			 InputStream in = null;  
	        // 根据文件创建文件的输入流  
	        try {
				in = new FileInputStream(file);
				// 创建字节数组  
		        byte[] datas = new byte[1024];  
		        // 读取内容，放到字节数组里面  
		        in.read(datas);  
		        return new String(datas); 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        
		return null; 
	 }

	 /**
	  * 回调参数
	  * @return
	  */
	 public static Map<String,String> getMap(){
		    Map<String ,String> map = new HashMap<String, String>();
		 	map.put("total_amount", "2.00");
		 	map.put("buyer_id", "2088102116773037");
		 	map.put("body", "大乐透2.1");
		 	map.put("trade_no", "2016071921001003030200089909");
		 	map.put("refund_fee", "0.00");
		 	map.put("notify_time", "2016-07-19 14:10:49");
		 	map.put("subject", "大乐透2.1");
		 	map.put("sign_type", "RSA");
		 	map.put("charset", "utf-8");
		 	map.put("notify_type", "trade_status_sync");
		 	map.put("out_trade_no", "0719141034-6418");
		 	map.put("gmt_close", "2016-07-19 14:10:46");
		 	map.put("gmt_payment", "2016-07-19 14:10:47");
		 	map.put("trade_status", "TRADE_SUCCESS");
		 	map.put("version", "1.0");
		 	map.put("sign", "kPbQIjX+xQc8F0/A6/AocELIjhhZnGbcBN6G4MM/HmfWL4ZiHM6fWl5NQhzXJusaklZ1LFuMo+lHQUELAYeugH8LYFvxnNajOvZhuxNFbN2LhF0l/KL8ANtj8oyPM4NN7Qft2kWJTDJUpQOzCzNnV9hDxh5AaT9FPqRS6ZKxnzM");
		 	map.put("gmt_create", "2016-07-19 14:10:44");
		 	map.put("app_id", "2015102700040153");
		 	map.put("seller_id", "2088102119685838");
		 	map.put("notify_id", "4a91b7a78a503640467525113fb7d8bg8e");
			return map;
	 }
	 
	public static void main(String[] args) {
		  String  zfbAppPrivateKey = null;
		  String  zfbAlipaypublicKey = null;
		  zfbAppPrivateKey = readInputStream2String("rsa_private_key_pkcs8.pem");
			
			zfbAlipaypublicKey = readInputStream2String("rsa_public_key.pem");
      
//       System.out.println("zfbAppPrivateKey:"+zfbAppPrivateKey);
//       System.out.println("zfbAlipaypublicKey:"+zfbAlipaypublicKey);
		
		// 定义签名参数
				String signature =
						"service=mobile.securitypay.pay"//固定值（手机快捷支付）
					  + "&noncestr="+zfbAppId//合作身份者ID（16位）
					  + "&_input_charset=utf-8"
					  + "&notify_url=https://www.baidu.com"//通知地址
					  + "&out_trade_no=11111111111111111"//商户内部订单号
					  + "&subject=测试"//测试
					  + "&payment_type=1"//固定值
					  + "&seller_id=1263451851@qq.com"//账户邮箱
					  + "&total_fee=0.01"//支付金额（元）
					  + "&body=订单说明"//订单说明            
					  + "&it_b_pay=30m";//（订单过期时间 30分钟过期无效）
		
//		String sign = RSAUtils.sign(signature, zfbAppPrivateKey, "UTF-8");
//		System.out.println("签名结果为:"+sign);
		JSONObject json = new JSONObject();
		json.put("method", "koubei.trade.itemorder.query");
		json.put("timestamp", DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", new Date()));
//		json.put("sign", sign);
		json.put("order_no", "2014112611001004680");
		json.put("app_id", zfbAppId);
		
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",json.getString("app_id"),zfbAppPrivateKey,"json","urf-8",zfbAlipaypublicKey,"RSA2");
		
		System.out.println(">>>"+alipayClient.toString());
		String content = "https://openapi.alipay.com/gateway.do?timestamp="+json.getString("timestamp")+"&method="+json.getString("method")+"&app_id="+json.getString("app_id")+"&sign_type=RSA2&sign="+json.getString("sign")+"&version=1.0&biz_content={\"order_no\":\""+json.getString("order_no")+"\"}";
		
//		String content = "https://openapi.alipay.com/gateway.do?timestamp="+json.getString("timestamp")+"&method="+json.getString("method")+"&app_id="+json.getString("app_id")+"&sign_type=RSA2&sign="+json.getString("sign")+"&version=1.0&biz_content={\"out_trade_no\":\""+json.getString("out_trade_no")+"\",\"trade_no\":\""+json.getString("trade_no")+"\"}";
		String data = HttpUtil.getInvoke(content);
		System.out.println(data);
		
		//回调异步通知验签
		try {
			boolean fals =  AlipaySignature.rsaCheckV1(getMap(),  zfbAlipaypublicKey,  "utf-8");
			System.out.println(fals);
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("回调异步通知验签出错:"+e.getMessage());
		}
		
		
	}
}
