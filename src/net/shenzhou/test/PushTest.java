package net.shenzhou.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.PushUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class PushTest {
	

	// The user agent
	protected static final String USER_AGENT = "Mozilla/5.0";

	// This object is used for sending the post request to Umeng
	protected static HttpClient client = new DefaultHttpClient();
	
	// The host
	protected static final String host = "http://msg.umeng.com";
	
	// The upload path
	protected static final String uploadPath = "/upload";
	
	// The post path
	protected static final String postPath = "/api/send";
	
	
	/**
	 * 请求码
	 * @param code
	 * @return
	 */
	public static String getHttpMessage(Integer status){
		switch (status) {
		case 200:
			return "请求成功";
		case 201:
			return "创建成功";
		case 202:
			return "更新成功";
		case 400:
			return "请求的地址不存在或者包含不支持的参数";
		case 401:
			return "未授权";
		case 403:
			return "被禁止访问";
		case 404:
			return "请求的资源不存在";
		case 500:
			return "内部错误";
		}
		return "未知的请求码";
		
	}
	
	
	/**
	 * api错误码
	 * @param code
	 * @return
	 */
	public static String getApiMessage(String code){
		if(code.equals("1000")){return"请求参数没有appkey"	;}
		if(code.equals("1001")){return"请求参数没有payload"	;}
		if(code.equals("1002")){return"请求参数payload中没有body"	;}
		if(code.equals("1003")){return"display_type为message时，请求参数没有custom"	;}
		if(code.equals("1004")){return"请求参数没有display_type"	;}
		if(code.equals("1005")){return"img url格式不对，请以https或者http开始"	;}
		if(code.equals("1006")){return"sound url格式不对，请以https或者http开始"	;}
		if(code.equals("1007")){return"url格式不对，请以https或者http开始"	;}
		if(code.equals("1008")){return"display_type为notification时，body中ticker不能为空"	;}
		if(code.equals("1009")){return"display_type为notification时，body中title不能为空"	;}
		if(code.equals("1010")){return"display_type为notification时，body中text不能为空"	;}
		if(code.equals("1011")){return"play_vibrate的值只能为true或者false"	;}
		if(code.equals("1012")){return"play_lights的值只能为true或者false"	;}
		if(code.equals("1013")){return"play_sound的值只能为true或者false"	;}
		if(code.equals("1013")){return"play_sound的值只能为true或者false"	;}
		if(code.equals("1014")){return"task-id没有找到"	;}
		if(code.equals("1015")){return"请求参数中没有device_tokens"	;}
		if(code.equals("1016")){return"请求参数没有type"	;}
		if(code.equals("1017")){return"production_mode只能为true或者false"	;}
		if(code.equals("2000")){return"该应用已被禁用"	;}
		if(code.equals("2001")){return"过期时间必须大于当前时间"	;}
		if(code.equals("2002")){return"定时发送时间必须大于当前时间"	;}
		if(code.equals("2003")){return"过期时间必须大于定时发送时间"	;}
		if(code.equals("2004")){return"IP白名单尚未添加, 请到网站后台添加您的服务器IP白名单。"	;}
		if(code.equals("2005")){return"该消息不存在"	;}
		if(code.equals("2006")){return"validation token错误"	;}
		if(code.equals("2007")){return"appkey或app_master_secret错误"	;}
		if(code.equals("2008")){return"json解析错误"	;}
		if(code.equals("2009")){return"请填写alias或者file_id"	;}
		if(code.equals("2010")){return"与alias对应的device_tokens为空"	;}
		if(code.equals("2011")){return"alias个数已超过50"	;}
		if(code.equals("2012")){return"此appkey今天的广播数已超过限制"	;}
		if(code.equals("2013")){return"消息还在排队，请稍候再查询"	;}
		if(code.equals("2014")){return"消息取消失败，请稍候再试"	;}
		if(code.equals("2015")){return"device_tokens个数已超过50"	;}
		if(code.equals("2016")){return"请填写filter"	;}
		if(code.equals("2017")){return"添加tag失败"	;}
		if(code.equals("2018")){return"请填写file_id"	;}
		if(code.equals("2019")){return"与此file_id对应的文件不存在"	;}
		if(code.equals("2020")){return"服务正在升级中，请稍候再试"	;}
		if(code.equals("2021")){return"appkey不存在"	;}
		if(code.equals("2022")){return"payload长度过长"	;}
		if(code.equals("2023")){return"文件上传失败，请重试"	;}
		if(code.equals("2024")){return"限速值必须为正整数"	;}
		if(code.equals("2025")){return"aps字段不能为空"	;}
		if(code.equals("2026")){return"1分钟内发送次数超出限制"	;}
		if(code.equals("2027")){return"签名不正确"	;}
		if(code.equals("2028")){return"时间戳已过期";}
		if(code.equals("2029")){return"content内容不能为空";}
			
		if(code.equals("2030")){return"launch_from/not_launch_from条件中的日期须小于发送日期";}	
		if(code.equals("2031")){return"filter格式不正确";}	
		if(code.equals("2032")){return"未上传生产证书，请到Web后台上传";}	
		if(code.equals("2033")){return"未上传开发证书，请到Web后台上传";}	
		if(code.equals("2034")){return"证书已过期	400";}
		if(code.equals("2035")){return"定时任务证书过期";}	
		if(code.equals("2036")){return"时间戳格式错误";}	
		if(code.equals("2038")){return"文件上传失败";}	
		if(code.equals("2039")){return"时间格式必须是yyyy-MM-dd HH:mm:ss";}	
		if(code.equals("2040")){return"过期时间不能超过7天";}	
		if(code.equals("2046")){return"定时推送时间不能超过创建推送时间+7天";}	
		
		if(code.equals("3000")){return"数据库错误"	;}
		if(code.equals("3001")){return"数据库错误"	;}
		if(code.equals("3002")){return"数据库错误"	;}
		if(code.equals("3003")){return"数据库错误"	;}
		if(code.equals("3004")){return"数据库错误"	;}
		if(code.equals("4000")){return"系统错误"	;}
		if(code.equals("4001")){return"系统忙"	;}
		if(code.equals("4002")){return"操作失败"	;}
		if(code.equals("4003")){return"appkey格式错误"	;}
		if(code.equals("4004")){return"消息类型格式错误"	;}
		if(code.equals("4005")){return"msg格式错误"	;}
		if(code.equals("4006")){return"body格式错误"	;}
		if(code.equals("4007")){return"deliverPolicy格式错误"	;}
		if(code.equals("4008")){return"失效时间格式错误"	;}
		if(code.equals("4009")){return"单个服务器队列已满"	;}
		if(code.equals("4010")){return"设备号格式错误"	;}
		if(code.equals("4011")){return"消息扩展字段无效"	;}
		if(code.equals("4012")){return"没有权限访问"	;}
		if(code.equals("4013")){return"异步发送消息失败"	;}
		if(code.equals("4014")){return"appkey和device_tokens不对应"	;}
		if(code.equals("4015")){return"没有找到应用信息"	;}
		if(code.equals("4016")){return"文件编码有误"	;}
		if(code.equals("4017")){return"文件类型有误"	;}
		if(code.equals("4018")){return"文件远程地址有误"	;}
		if(code.equals("4019")){return"文件描述信息有误"	;}
		if(code.equals("4020")){return"device_token有误(注意，友盟的device_token是严格的44位字符串)"	;}
		if(code.equals("4021")){return"HSF异步服务超时"	;}
		if(code.equals("4022")){return"appkey已经注册"	;}
		if(code.equals("4023")){return"服务器网络异常"	;}
		if(code.equals("4024")){return"非法访问"	;}
		if(code.equals("4025")){return"device-token全部失败"	;}
		if(code.equals("4026")){return"device-token部分失败"	;}
		if(code.equals("4027")){return"TPNS拉取文件失败"	;}
		if(code.equals("5000")){return"device_token错误"	;}
		if(code.equals("5001")){return"证书不存在"	;}
		if(code.equals("5002")){return"p,d是umeng保留字段"	;}
		if(code.equals("5003")){return"alert字段不能为空"	;}
		if(code.equals("5004")){return"alert只能是String类型"	;}
		if(code.equals("5005")){return"device_token格式错误"	;}
		if(code.equals("5006")){return"创建socket错误"	;}
		if(code.equals("5007")){return"certificate_revoked错误"	;}
		if(code.equals("5008")){return"certificate_unkown错误"	;}
		if(code.equals("5009")){return"handshake_failure错误"	;}
		
		return "";
	}
	
	
	
	public static void main(String[] args) {
		try {
			String start_time = DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", DateUtils.addMinutes(new Date(),1));
			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			 Date dateStart = format.parse(start_time);

			 String  startDay = Integer.toString((int)(dateStart.getTime() / 1000));
			String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
			
	        String url = host + postPath;
//	        String postBody = msg.getPostBody();
//	        String postBody = "{\"appkey\":\"5970713f07fe656bcd000394\",\"type\":\"unicast\",\"device_tokens\":\"Asc6_2qfc5CAaeTfld1tBwAaSiqrIojuLo1YwSxIGPMp\",\"payload\":{\"display_type\":\"notification\",\"body\":{\"ticker\":\"aaa\",\"title\":\"sdssss\",\"text\":\"内容\",\"after_open\":\"go_app\"},\"extra\":{}},\"production_mode\":\"true\",\"timestamp\":\""+timestamp+"\"}";

	        String appkey = PushUtil.ios_Ys_AppKey;//appkey
			String secret = PushUtil.ios_Ys_App_Master_Secret;//secret
			String device_tokens = "67a01492388240848cef6bf94f6a57a5d6a0143798f185eb03bac414c983a631"; //device_tokens 设备唯一识别号
	        
			String iOSType = ""; //1.project 2.docotr
			
			String iOSValue = "";

			
	        String postBody = "{\"appkey\":\""+appkey+"\",  \"timestamp\":\""+timestamp+"\",\"type\":\"unicast\", \"device_tokens\":\""+device_tokens+"\", \"alias_type\": \"xx\",\"alias\":\"xx\",\"file_id\":\"xx\", \"filter\":\"{}\",\"payload\":{\"aps\":{ \"alert\": \"xx\",\"badge\": \"dd\", \"sound\": \"xx\",\"content-available\":\"xx \",\"category\": \"xx\"},\"iOSType\":\""+iOSType+"\", \"iOSValue\":\""+iOSValue+"\"}, \"policy\":  { \"start_time\":\""+start_time+"\", \"expire_time\":\"2017-11-16 17:01:06\",\"max_send_num\": \"xx\"  , \"apns-collapse-id\":\"xx\"},\"production_mode\":\"false\" , \"description\": \"xx\"}";
	        System.out.println("postBody:\n"+postBody);
	        String sign = DigestUtils.md5Hex(("POST" + url + postBody + secret).getBytes("utf8"));
			
	        url = url + "?sign=" + sign;
	        HttpPost post = new HttpPost(url);
	        post.setHeader("User-Agent", USER_AGENT);
	        StringEntity se = new StringEntity(postBody, "UTF-8");
	        post.setEntity(se);
	        // Send the post request and get the response
	        HttpResponse response = client.execute(post);
	        
//	        String status = HttpUtil.getInvoke(url);
//	        System.out.println(status);
	        int status = response.getStatusLine().getStatusCode();
	        System.out.println("Response Code : " + status);
	        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        StringBuffer result = new StringBuffer();
	        String line = "";
	        while ((line = rd.readLine()) != null) {
	            result.append(line);
	        }
	        System.out.println("data:"+result.toString());
	        
	        if (status == 200) {
	            System.out.println("Notification sent successfully.");
	        } else {
	            System.out.println("Failed to send the notification!");
	            String code  = JSONObject.fromObject(JSONObject.fromObject(result.toString()).get("data").toString()).getString("error_code").toString();
	            System.out.println(code);
	            String apiMessage = getApiMessage(code);
	            System.out.println(apiMessage);
	        }
			
	        
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	
		
	}
}
