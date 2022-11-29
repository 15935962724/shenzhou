/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shenzhou.util;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * 微信操作工具类
 * @date 2017-12-12 10:03:10
 * @author wsr
 *
 */
public final class WeiXinUtil {

	private Logger logger = Logger.getLogger(WeiXinUtil.class);
	
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * add by wsr on 2017-12-12 14:17:52
	 * 获取accessToken
	 * @return accessToken
	 */
	public static String getAccess_token(String wxAppId, String secret) {
	       String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ wxAppId +"&secret=" + secret;
	       JSONObject jsonobject = JsonUtils.toJSONObject(HttpUtil.getInvoke(url));
	       return jsonobject.getString("access_token");
	 }
	
	/**
     * 长链接转成短链接 提高扫码速度和成功率
     * add by wsr on 2017-12-12 14:18:02
     * @param longURL
     * @return shortURL
     */
    public static String shortURL(String longURL, String wxAppId, String secret) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
        try {
			requestUrl = requestUrl.replace("ACCESS_TOKEN",getAccess_token(wxAppId, secret));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String jsonMsg = "{\"action\":\"long2short\",\"long_url\":\"%s\"}";
        String.format(jsonMsg, longURL);
        JSONObject jsonobject = JsonUtils.toJSONObject(HttpUtil.post(requestUrl, String.format(jsonMsg, longURL)));
        return jsonobject.getString("short_url");
    }
	

	/**
	 * 获取网页授权accessToken json字符串
	 * add by zl on 2016-1-9 18:43:09
	 * @return accessToken
	 */
	public static String getAccessTokenJson(String code, String wxAppId, String wxSecret){
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ wxAppId + "&secret=" + wxSecret + "&code=" + code + "&grant_type=authorization_code";		
		return HttpUtil.getInvoke(url);
	}
	
	/**
	 * 获取openid
	 * add by wsr on 2017-12-12 14:18:11
	 * @return
	 */
	public static String getOpenid(String code, String wxAppId, String wxSecret){
		JSONObject jsontoken = JsonUtils.toJSONObject(getAccessTokenJson(code, wxAppId, wxSecret));
//		logger.warn(jsontoken);
		System.out.println(jsontoken.getString("openid"));
		return jsontoken.getString("openid");
	}
	
	/**
	 * 获取用户信息
	 * add by wsr on 2017-12-12 14:22:16
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public static String getUserInfo(String code, String wxAppId, String wxSecret) throws IOException {
		JSONObject jsontoken = JsonUtils.toJSONObject(getAccessTokenJson(code, wxAppId, wxSecret));
		try {
			String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+jsontoken.getString("access_token")+"&openid="+ jsontoken.getString("openid") +"&lang=zh_CN";
			String newstr = HttpUtil.getInvoke(url);
			String userinfo = newstr.replace("\"", "'");
			return 	userinfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 获取微信签名signature
	 * @param url jsapi_ticket
	 * add by wsr on 2017-12-12 14:22:28
	 * @return signature
	 * @throws IOException
	 */
	public Map<String, Object> getSignature(String url, String jsapi_ticket) throws IOException {
		Map<String, Object> wxMap = new HashMap<String, Object>();
		// 调用获取随机字符串的方法
		String nonce_str = UUID.randomUUID().toString();
		// 调用获取随机数的方法
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		// 定义签名参数
		String signature = "";
		// 拼接字符串
		String string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
				+ nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			// 用算法获取签名
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		wxMap.put("nonce_str", nonce_str);
		wxMap.put("timestamp", timestamp);
		wxMap.put("signature", signature);
		return wxMap;
	}
	/**
	 * @date 2017-12-12 14:23:01
	 * @author wsr
	 * 生成分享店铺的二维码
	 * @param scene_str
	 * @param access_token
	 * @return
	 */
	public static String qrCodeById(String scene_str,String access_token){
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("expire_seconds", 604800);
		jsonObject.put("action_name", "QR_LIMIT_STR_SCENE");
		JSONObject jsonObject1 = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("scene_str", scene_str);
		jsonObject1.put("scene", jsonObject2);
		jsonObject.put("action_info", jsonObject1);
		
		jsonObject.get("accessToken");
		String URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+access_token;
		
		String ticket = HttpUtil.post(URL, jsonObject.toString());
		JSONObject qrJson = JSONObject.fromObject(ticket);
		String qr = qrJson.get("ticket").toString();
		try {
			qr = URLEncoder.encode(qr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String qrurl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+qr;
		return qrurl;
	}
	
	
	/**
	 * 使用算法获取签名
	 * @param hash
	 * @return
	 */
	    public static String byteToHex(final byte[] hash) {
	        Formatter formatter = new Formatter();
	        for (byte b : hash) {
	            formatter.format("%02x", b);
	        }
	        String result = formatter.toString();
	        formatter.close();
	        return result;
	    }
	    
	    /**
	     * 获取随机字符串
	     * @param length
	     * @return
	     */
	    public static String getRandomStr(int length) {
	        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        int randomNum;
	        char randomChar;
	        Random random = new Random();
	        // StringBuffer类型的可以append增加字符
	        StringBuffer str = new StringBuffer();
	        for (int i = 0; i < length; i++) {
	            // 可生成[0,n)之间的整数，获得随机位置
	            randomNum = random.nextInt(base.length());
	            // 获得随机位置对应的字符
	            randomChar = base.charAt(randomNum);
	            // 组成一个随机字符串
	            str.append(randomChar);
	        }
	        return str.toString();
	    }
	    
}