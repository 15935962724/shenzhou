package net.shenzhou.push;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import net.sf.json.JSONObject;
import net.shenzhou.util.HttpUtil;
import net.shenzhou.util.PushUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class PushClient {
	
	// The user agent
	protected final String USER_AGENT = "Mozilla/5.0";

	// This object is used for sending the post request to Umeng
	protected HttpClient client = new DefaultHttpClient();
	
	// The host
	protected static final String host = "http://msg.umeng.com";
	
	// The upload path
	protected static final String uploadPath = "/upload";
	
	// The post path
	protected static final String postPath = "/api/send";

	public boolean send(UmengNotification msg) throws Exception {
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		msg.setPredefinedKeyValue("timestamp", timestamp);
		
        String url = host + postPath;
//        String postBody = msg.getPostBody();
//        String postBody = "{\"appkey\":\"5970713f07fe656bcd000394\",\"type\":\"unicast\",\"device_tokens\":\"Asc6_2qfc5CAaeTfld1tBwAaSiqrIojuLo1YwSxIGPMp\",\"payload\":{\"display_type\":\"notification\",\"body\":{\"ticker\":\"aaa\",\"title\":\"sdssss\",\"text\":\"内容\",\"after_open\":\"go_app\"},\"extra\":{}},\"production_mode\":\"true\",\"timestamp\":\""+timestamp+"\"}";

        String appkey = PushUtil.ios_Ys_AppKey;//appkey
		String secret = PushUtil.ios_Ys_App_Master_Secret;//secret
		String device_tokens = "67a01492388240848cef6bf94f6a57a5d6a0143798f185eb03bac414c983a631"; //device_tokens 设备唯一识别号
        
      String postBody ="{\"appkey\":\""+appkey+"\",\"type\":\"unicast\",\"device_tokens\":\""+device_tokens+"\",\"payload\":{\"display_type\":\"notification\",\"body\":{\"ticker\":\"aaa\",\"title\":\"sdssss\",\"text\":\"内容\",\"after_open\":\"go_app\"},\"extra\":{},\"aps\":\"{}\"},\"production_mode\":\"true\",\"timestamp\":\""+timestamp+"\"}";
        
        System.out.println("postBody:"+postBody);
        String sign = DigestUtils.md5Hex(("POST" + url + postBody + secret).getBytes("utf8"));
        url = url + "?sign=" + sign;
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", USER_AGENT);
        StringEntity se = new StringEntity(postBody, "UTF-8");
        post.setEntity(se);
        // Send the post request and get the response
        HttpResponse response = client.execute(post);
        
//        String status = HttpUtil.getInvoke(url);
//        System.out.println(status);
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
        }
        return true;
    }



	// Upload file with device_tokens to Umeng
	public String uploadContents(String appkey,String appMasterSecret,String contents) throws Exception {
		// Construct the json string
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("content", contents);
		// Construct the request
		String url = host + uploadPath;
		String postBody = uploadJson.toString();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		// Decode response string and get file_id from it
		JSONObject respJson = JSONObject.fromObject(result.toString());
//		JSONObject respJson = new JSONObject(result.toString());
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("Failed to upload file");
		}
		JSONObject data = respJson.getJSONObject("data");
		String fileId = data.getString("file_id");
		// Set file_id into rootJson using setPredefinedKeyValue
		
		return fileId;
	}

}
