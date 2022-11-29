package net.shenzhou.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class PushUtil {
	
	public final static String android_Ys_AppKey = "5970713f07fe656bcd000394";
//	protected final static String android_Ys_Umeng_Message_Secret = "e5d52eb63896368acdd7de736c8081ae";
	public final static String android_Ys_App_Master_Secret = "umaz0ymddzelhsnhwua4adgvrerhusso";
	
	public final static String android_Yh_AppKey = "5970704baed1797c0d00023f";
//	protected final static String android_Yh_Umeng_Message_Secret = "5087e4e600e6999992700c822cde0363";
	public final static String android_Yh_App_Master_Secret = "ekwauubijfvnne4xk19sexgyhwufin13";
	
	public final static String ios_Ys_AppKey = "597072adf29d986c1000161d";
	public final static String ios_Ys_App_Master_Secret = "zesbpr1uuhwsvnlowrovjf2k1yip2nsi";
	
	public final static String ios_Yh_AppKey = "5970723c8f4a9d3013000fde";
	public final static String ios_Yh_App_Master_Secret = "lzz7yplpij4kvxdkxvizavgzfjst9vzb";
	
	// The user agent
	protected final static String USER_AGENT = "Mozilla/5.0";

	// This object is used for sending the post request to Umeng
	protected static HttpClient client = new DefaultHttpClient();
	
	// The host
	protected static final String host = "http://msg.umeng.com";
	
	// The upload path
	protected static final String uploadPath = "/upload";
	
	// The post path
	protected static final String postPath = "/api/send";

	public static Map<String ,Object> androidSend(Map<String ,Object> map) throws Exception {
		Map<String ,Object> data = new HashMap<String, Object>();
		
		String appkey = map.get("appkey").toString();//appkey
		String secret =  map.get("secret").toString();//secret
		String device_tokens =  map.get("device_tokens").toString(); //device_tokens 设备唯一识别号
		String ticker =  map.get("ticker").toString();// 通知栏提示文字
		String title =  map.get("title").toString();// 必填 通知标题
		String text =  map.get("text").toString(); // 必填 通知文字描述 
		String after_open =  map.get("after_open").toString();//必填 值为"go_app：打开应用", "go_url: 跳转到URL", "go_activity: 打开特定的activity", "go_custom: 用户自定义内容。"
		
		String url = map.get("url").toString();;    // 可选 当"after_open"为"go_url"时，必填。 通知栏点击后跳转的URL，要求以http或者https开头  
		String activity = map.get("activity").toString();     // 可选 当"after_open"为"go_activity"时，必填。 通知栏点击后打开的Activity 
		String custom =  map.get("custom").toString();// 可选 display_type=message, 或者display_type=notification且"after_open"为"go_custom"时，该字段必填。用户自定义内容, 可以为字符串或者JSON格式
		String extra =  map.get("extra").toString();//用户自定义 extra
		
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
        String endUrl = host + postPath;
        String postBody = "{\"appkey\":\""+appkey+"\",\"type\":\"unicast\",\"device_tokens\":\""+device_tokens+"\",\"payload\":{\"display_type\":\"notification\",\"body\":{\"ticker\":\""+ticker+"\",\"title\":\""+title+"\",\"text\":\""+text+"\",\"sound\":\"message\",\"play_vibrate\":\"true\",\"play_lights\":\"true\",\"play_sound\":\"true\",\"after_open\":\""+after_open+"\",\"url\":\""+url+"\",\"activity\":\""+activity+"\",\"custom\":\""+custom+"\"},\"extra\":"+extra+"},\"production_mode\":\"true\",\"timestamp\":\""+timestamp+"\"}";
        
        System.out.println("postBody:"+postBody);
        String sign = DigestUtils.md5Hex(("POST" + endUrl + postBody + secret).getBytes("utf8"));
        endUrl = endUrl + "?sign=" + sign;
        HttpPost post = new HttpPost(endUrl);
        post.setHeader("User-Agent", USER_AGENT);
        StringEntity se = new StringEntity(postBody, "UTF-8");
        post.setEntity(se);
        // Send the post request and get the response
        HttpResponse response = client.execute(post);
        
//        String status = HttpUtil.getInvoke(url);
//        System.out.println(status);
        int status = response.getStatusLine().getStatusCode();
//        System.out.println("Response Code : " + status);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
//        System.out.println(result.toString());
        
        String httpMessage = getHttpMessage(status);
        data.put("status", status);
        if (status == 200) {
            data.put("message", "请求结果:"+httpMessage);
        } else {
            String code  = JSONObject.fromObject(JSONObject.fromObject(result.toString()).get("data").toString()).getString("error_code").toString();
            String apiMessage = getApiMessage(code);
            data.put("message", "请求结果:"+httpMessage+",推送结果:"+apiMessage);
        }
       return data;
    }

	/**
	 * ios 推送
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Map<String ,Object> iosSend(Map<String ,Object> map) throws Exception {
		Map<String ,Object> data = new HashMap<String, Object>();
		
		try {
			String start_time = DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", DateUtils.addMinutes(new Date(), 1));
			String expire_time = DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", DateUtils.addMinutes(new Date(), 10));
			
			
			 
			String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
			
	        String endUrl = host + postPath;
	        
	        String appkey = map.get("appkey").toString();//appkey
			String secret =  map.get("secret").toString();//secret
			String device_tokens =  map.get("device_tokens").toString(); //device_tokens 设备唯一识别号
	        
			String alias_type = map.get("alias_type").toString(); // 可选 当type=customizedcast时，必填，alias的类型,  alias_type可由开发者自定义,开发者在SDK中 调用setAlias(alias, alias_type)时所设置的alias_type
			String alias = map.get("alias").toString();// 可选 当type=customizedcast时, 开发者填写自己的alias。  要求不超过50个alias,多个alias以英文逗号间隔。 在SDK中调用setAlias(alias, alias_type)时所设置的alias
			String file_id =  map.get("file_id").toString(); // 可选 当type=filecast时，file内容为多条device_token,  device_token以回车符分隔当type=customizedcast时，file内容为多条alias， alias以回车符分隔，注意同一个文件内的alias所对应的alias_type必须和接口参数alias_type一致。注意，使用文件播前需要先调用文件上传接口获取file_id,  具体请参照"2.4文件上传接口"
			String alert =  map.get("alert").toString();  // 必填 iOS10 新增带title，subtile的alert格式如下"alert":{//  "title":"title","subtitle":"subtitle", "body":"body",}                   
			String badge = map.get("badge").toString(); // 可选        
			String sound = map.get("sound").toString(); // 可选        
			String content_available = map.get("content_available").toString(); // 可选        
			String category = map.get("category").toString(); // 可选        
			String max_send_num = map.get("max_send_num").toString();// 可选 发送限速，每秒发送的最大条数。开发者发送的消息如果有请求自己服务器的资源，可以考虑此参数。
			String apns_collapse_id = map.get("apns_collapse_id").toString();//可选，iOS10开始生效。
			String description = map.get("description").toString(); // 可选 发送消息描述，建议填写。
			
			String iOSType = map.get("iOSType").toString(); //1.project 2.docotr
			String iOSValue = map.get("iOSValue").toString();//iOSType 填写时  可填写
			 
			
	        String postBody = "{\"appkey\":\""+appkey+"\",  \"timestamp\":\""+timestamp+"\",\"type\":\"unicast\", \"device_tokens\":\""+device_tokens+"\", \"alias_type\": \""+alias_type+"\",\"alias\":\""+alias+"\",\"file_id\":\""+file_id+"\", \"filter\":\"{}\",\"payload\":{\"aps\":{ \"alert\": \""+alert+"\",\"badge\": \""+badge+"\", \"sound\": \"PushMusic.m4a\",\"content-available\":\""+content_available+" \",\"category\": \""+category+"\"},\"iOSType\":\""+iOSType+"\", \"iOSValue\":\""+iOSValue+"\"}, \"policy\":  { \"start_time\":\""+start_time+"\", \"expire_time\":\""+expire_time+"\",\"max_send_num\": \""+max_send_num+"\"  , \"apns-collapse-id\":\""+apns_collapse_id+"\"},\"production_mode\":\"false\" , \"description\": \""+description+"\"}";
	        System.out.println("postBody:\n"+postBody);
	        String sign = DigestUtils.md5Hex(("POST" + endUrl + postBody + secret).getBytes("utf8"));
			
	        endUrl = endUrl + "?sign=" + sign;
	        HttpPost post = new HttpPost(endUrl);
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
			
	        String httpMessage = getHttpMessage(status);
	        data.put("status", status);
	        if (status == 200) {
	            data.put("message", "请求结果:"+httpMessage);
	        } else {
	            String code  = JSONObject.fromObject(JSONObject.fromObject(result.toString()).get("data").toString()).getString("error_code").toString();
	            String apiMessage = getApiMessage(code);
	            data.put("message", "请求结果:"+httpMessage+",推送结果:"+apiMessage);
	        }
	        
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
        
       
       return data;
    }
	
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
		if(code.equals("2028")){return"时间戳已过期"	;}
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
	
	/**
	 * 根据device_tokens 判断是否安卓设备
	 * @param device_tokens
	 * @return
	 */
	public static boolean getFals(String device_tokens){
		return device_tokens.length()==44;
	}
	
	
	public static void main(String[] args) {
		try {
			//安卓 所传字段
//			String appkey = "5970713f07fe656bcd000394";//appkey
//			String secret = "umaz0ymddzelhsnhwua4adgvrerhusso";//secret
//			String device_tokens = "AhN4X9YFlJgCH5nRT_2jJEFyV1jEct4eWLvkSdEZs6li"; //device_tokens 设备唯一识别号
//			 System.out.println("andiros长度:"+device_tokens.length());  
			String ticker = "通知提示文字";// 通知栏提示文字
			String title = "通知标题";// 必填 通知标题
			String text = " 通知文字描述 ";// 必填 通知文字描述 
			String after_open = "go_app";//必填 值为"go_app：打开应用", "go_url: 跳转到URL", "go_activity: 打开特定的activity", "go_custom: 用户自定义内容。"
		   
			//ios 所传字段
			 String appkey = PushUtil.ios_Ys_AppKey;//appkey
			 String secret = PushUtil.ios_Ys_App_Master_Secret;//secret
			 String device_tokens = "67a01492388240848cef6bf94f6a57a5d6a0143798f185eb03bac414c983a631"; //device_tokens 设备唯一识别号
		     System.out.println("ios长度:"+device_tokens.length());   
			
			String alias_type = "xx1"; // 可选 当type=customizedcast时，必填，alias的类型,  alias_type可由开发者自定义,开发者在SDK中 调用setAlias(alias, alias_type)时所设置的alias_type
			String alias = "xx2";// 可选 当type=customizedcast时, 开发者填写自己的alias。  要求不超过50个alias,多个alias以英文逗号间隔。 在SDK中调用setAlias(alias, alias_type)时所设置的alias
			String file_id =  "xx3"; // 可选 当type=filecast时，file内容为多条device_token,  device_token以回车符分隔当type=customizedcast时，file内容为多条alias， alias以回车符分隔，注意同一个文件内的alias所对应的alias_type必须和接口参数alias_type一致。注意，使用文件播前需要先调用文件上传接口获取file_id,  具体请参照"2.4文件上传接口"
			String alert =  "xx4";  // 必填 iOS10 新增带title，subtile的alert格式如下"alert":{//  "title":"title","subtitle":"subtitle", "body":"body",}                   
			String badge = "dd5"; // 可选        
			String sound = "xx6"; // 可选        
			String content_available = "xx7"; // 可选        
			String category = "xx8"; // 可选        
			String max_send_num = "xx9";// 可选 发送限速，每秒发送的最大条数。开发者发送的消息如果有请求自己服务器的资源，可以考虑此参数。
			String apns_collapse_id = "xx10";//可选，iOS10开始生效。
			String description = "xx11"; // 可选 发送消息描述，建议填写。
			
			String iOSType = "project"; //1.project 2.docotr
			String iOSValue = "1";//iOSType 填写时  可填写
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("appkey", appkey);
			map.put("secret", secret);
			map.put("device_tokens", device_tokens);
			
			map.put("ticker", ticker);
			map.put("title", title);
			map.put("text", text);
			map.put("after_open", after_open);
			
//			Map<String ,Object> data = androidSend(map);
//			System.out.println(JsonUtils.toJson(data));
			
			map.put("alias_type", alias_type);
			map.put("alias", alias);
			map.put("file_id", text);
			map.put("after_open", after_open);
			
			map.put("alias_type",alias_type);//可选当type=customizedcast时，必填，alias的类型, alias_type可由开发者自定义,开发者在SDK中 调用setAlias(alias, alias_type)时所设置的alias_type
			map.put("alias", alias);// 可选 当type=customizedcast时, 开发者填写自己的alias。  要求不超过50个alias,多个alias以英文逗号间隔。 在SDK中调用setAlias(alias, alias_type)时所设置的alias
			map.put("file_id", file_id); // 可选 当type=filecast时，file内容为多条device_token,  device_token以回车符分隔当type=customizedcast时，file内容为多条alias， alias以回车符分隔，注意同一个文件内的alias所对应的alias_type必须和接口参数alias_type一致。注意，使用文件播前需要先调用文件上传接口获取file_id,  具体请参照"2.4文件上传接口"
			map.put("alert", alert);  // 必填 iOS10 新增带title，subtile的alert格式如下"alert":{//  "title":"title","subtitle":"subtitle", "body":"body",}                   
			map.put("badge", badge); // 可选        
			map.put("sound", sound); // 可选        
			map.put("content_available", content_available); // 可选        
			map.put("category", category); // 可选        
			map.put("max_send_num", max_send_num);// 可选 发送限速，每秒发送的最大条数。开发者发送的消息如果有请求自己服务器的资源，可以考虑此参数。
			map.put("apns_collapse_id", apns_collapse_id);//可选，iOS10开始生效。
			map.put("description", description); // 可选 发送消息描述，建议填写。
			
			map.put("iOSType", iOSType); //1.project 2.docotr
			map.put("iOSValue", iOSValue);//iOSType 填写时  可填写

			Map<String ,Object> ios_data = iosSend(map);
			System.out.println(JsonUtils.toJson(ios_data));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
}
