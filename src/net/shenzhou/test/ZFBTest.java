
package net.shenzhou.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.shenzhou.util.DateUtil;
import net.shenzhou.util.HttpUtil;
import net.shenzhou.util.RSAUtils;


public class ZFBTest {
	
//	 static String  zfbAppId = "2088031869534126";
	 static String  zfbAppId = "2018041202546547";
	 
	 static String  zfbAesKey = "EFymehcJT9ClsjeOSBP3hQ==";
	
//	 static String order_no = "2018041821001004660545610042";
	 
	 static String order_no = "2018042121001004770549897718";
	 static String out_trade_no = "201804211007210952";
	 
	 static String method = "alipay.trade.query";
	 static String timestamp = DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", new Date());
	 static String format = "json";
	 static String sign_type = "RSA";
	 static String charset = "GBK";
	 static String version = "1.0";
	 static String biz_content = "{\"out_trade_no\":\""+out_trade_no+"\",\"trade_no\":\""+order_no+"\"}";
	 
	
	 
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
	  * 封装map参数
	  * @return
	  */
	 public static Map<String,String> getMap(){
		    Map<String ,String> map = new HashMap<String, String>();
		 	map.put("app_id", zfbAppId);
		 	map.put("method", method);
		 	map.put("format", format);
		 	map.put("charset", charset);
		 	map.put("sign_type", sign_type);
		 	map.put("timestamp", timestamp);
		 	map.put("version", version);
		 	map.put("app_id", zfbAppId);
		 	map.put("biz_content", biz_content);
			return map;
	 }
	 
	 	/** 
	     * 除去map中的空值和签名参数
	     * @param sArray 签名参数组
	     * @return 去掉空值与签名参数后的新签名参数组
	     */
	   public static Map<String, String> paraFilter(Map<String, String> sArray) {

	        Map<String, String> result = new HashMap<String, String>();

	        if (sArray == null || sArray.size() <= 0) {
	            return result;
	        }

	        for (String key : sArray.keySet()) {
	            String value = sArray.get(key);
	            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
	                ) {
	                continue;
	            }
	            result.put(key, value);
	        }

	        return result;
	    }
	 
	    /** 
	     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	     * @param params 需要排序并参与字符拼接的参数组
	     * @return 拼接后字符串
	     */
	    public static String createLinkString(Map<String, String> params) {

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
	   
	 public static String getFlag (Map<String,String> data_map) {
		
		    String content = "https://openapi.alipay.com/gateway.do";
		    String body =createLinkString(data_map);
		    System.out.println(content+"?"+body);
		    String data = HttpUtil.post(content, body);
			System.out.println(">>>"+data.trim());
			return data;
		 }
	 
	  /**
	     * 签名字符串
	     * @param text 需要签名的字符串
	     * @param key 密钥
	     * @param input_charset 编码格式
	     * @return 签名结果
	     */
//	    public static String sign(String text, String key, String input_charset) {
//	    	text = text + key;
//	        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
//	    }
	 
	    /**
	     * @param content
	     * @param charset
	     * @return
	     * @throws SignatureException
	     * @throws UnsupportedEncodingException 
	     */
//	    private static byte[] getContentBytes(String content, String charset) {
//	        if (charset == null || "".equals(charset)) {
//	            return content.getBytes();
//	        }
//	        try {
//	            return content.getBytes(charset);
//	        } catch (UnsupportedEncodingException e) {
//	            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
//	        }
//	    }
	    
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
			String zhiFuBaopublicKey = 
				"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiuW6EDenjTsquGB/400drG"
			  + "9WHn6dKiHRHZiwrnlEGXHntLrX4ed8H9RCfclcSMhIS9sFn98IPVNi5wylR1i1BG5S"
			  + "I1LM+QUAlIe5DOFpJv1ZY2J9i6uz2JHjkDlmgM2IrONl6dDHYxKI/BwszTptdjiPdC"
			  + "NwU65dZMglDAVMnYeDxNO/aBF/Y+jWe5a8UwZ3QkTpsbnxBCE4Cfi3iRF8tQoQO4t1"
			  + "u2uGCFQWdlxv0mQIGmIbWyEF+PdVdjjgzFRmRk1yB/WUwvFcmPuEF3G0gN+8nVqqev"
			  + "XqYT2SsdrlFZyOt3Wp4b6FFBGkowftVrB4GaSDDhZIAd6N+eYvdfuriwIDAQAB";		
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
	 
	public static void main(String[] args) {
		  String  privateKey = getPrivateKey();
		  String  privateKeyPkcs8 = getPrivateKeyPkcs8();
		  String  zhiFuBaopublicKey = getZhiFuBaoPublicKey();	//支付宝公钥
		  String  yingYongpublicKey = getYingYongPublicKey();	//应用公钥
		  
		  String  shangHuYingYongpublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmO5yazr0M1wjGcS8v3UZUcdtZjmUfNNqpd1APHUqA7AfKpxLH3owS4q1RocxojP6so7VFMwC9Fs+C6JVm1NNISP5zbILvnxD0AXjMiXzLd4Gw3OexPOEw7hdUc2vAnDptmiY1Lj/ouCn3adkfql5RE8F/b3pQXKL2XRJAhmrw/UBCIKnisfIPIFJEgLTsb+nEvc5lZcDg5lgeF7JrZN11WrjzvIKwTORo6WYnnWY6hyL+e1+wmtm+yaZadyZRQVD6jZAglwlJbLIsZTngf0JWNVTDP6Iitc0ZPoGmVcuI5vnI7Pu6RnbbR3/2nY0wv8e8jmRyDj24SS11cn0MHcAJQIDAQAB";	//商户应用公钥
		  String  shangHuyingYongPribateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCY7nJrOvQzXCMZxLy/dRlRx21mOZR802ql3UA8dSoDsB8qnEsfejBLirVGhzGiM/qyjtUUzAL0Wz4LolWbU00hI/nNsgu+fEPQBeMyJfMt3gbDc57E84TDuF1Rza8CcOm2aJjUuP+i4Kfdp2R+qXlETwX9velBcovZdEkCGavD9QEIgqeKx8g8gUkSAtOxv6cS9zmVlwODmWB4Xsmtk3XVauPO8grBM5GjpZiedZjqHIv57X7Ca2b7Jplp3JlFBUPqNkCCXCUlssixlOeB/QlY1VMM/oiK1zRk+gaZVy4jm+cjs+7pGdttHf/adjTC/x7yOZHIOPbhJLXVyfQwdwAlAgMBAAECggEAcOcAo7d3V397DmgXLIWHtr8zPdfejrUKM/9+j+Ozq9tXdv7B2zA6/Voe8D2Y3/Xd6/rUxrDCsaG20aH771r1QzUrE7eRQgS9j5L5IymvOTqD5cGzAhYB+vheNKlTvcqcUCUyR0F4wMd4RoRxBa2aMFriwmx1CvNE2bomuB05mMVg2pnbkXAbPGAD6v167z9xR0zBnV0M62QjTsjcKlfX+JJsyBL5+X2aP6ZwEyqs/0Boa1i/VrkXmUfgY3XJFESXIawU1XMnXbz7L7Ydu/lN/jlU9fKJzBQtrQdyCD6Sq93jcff8jrjQRmA0Q3fFaSbq5lNQhysL53NMHkihXa4YQQKBgQDIljtHNmOwFyjOo1zk4C4R27UMZ1DwTvz3B8EyjKolFJ+Sdsj/V/T/TN8ZEVpoXRBpXn6PssRgEjAsnYNDsimdMRVOAB8fdNAa89z6xlJ/cZTQ0OvVlOlXO9Q+13FUs8wImMBeU0UaVrPyReDrYzqrtp1ibu0DtumrT4VXA54AtQKBgQDDLfYit+W9KvwP5UXaGELipbtDm/HGhjs9/Vu8xhWFYwyn5iofqxbbn/a3tpLxTSZWqZYQM8MUKuXgSnjrXAs3XW68Dhk1KHqLbgm3vJ5M7lch71VXjar5lWBuloCROgJMGfJh0DjLGkY1KOCFiNMfQvavWVHxvot35N/eIe9XsQKBgQDCpJ2TJeEoX+DWW8npwuyRobyW866j8lIguKKuFbeJUfDipGe4+DYigwq+fYtYWG5zgSGY+aT7gMrEkHhu+XTON3HwBcgtsyG59cgKhOZLCwGYfBNNZzniz4jPDirIQcyMzwVkwrL8swPc5hgy8lBdjlA8010lTJqFBR5j6HurPQKBgClhqkjsIop7/ZtFKg02jZ/OMakA/D7yIMdg2z1n5hI00O8cLpwuQxdnkACddgQxLkq7g/SFdPvmqHxobcDA8CPZ7Gt0tcV+gpTjTK4nEI0RD2pDex2oDLMEB8EKqdGgsfH+olQ4uIHfXTlSJGBLhS+gm/rLfkM5+gn+fbR0D3HRAoGBAL4n5UWUrTVGIS7fCGO41tFiz8xiqnuba5TOapRsFd44/gLRfawMLUQ3urkMoj66E5M2VMYPWSCOXBeFUr3lKXuJbPV2ceSODFIcxMXY91dgGFf7R7j/FDcrQNpl8/caHpF06wHKclc614TmwazSF4BQMRIOkcw71clmPzuYByG9";	//商户应用私钥
		  
		  
//		  zfbAppPrivateKey = readInputStream2String("rsa_private_key_pkcs8.pem");
//		  zfbAlipaypublicKey = readInputStream2String("rsa_public_key.pem");
		  
		 String AlipaypublicKey =  "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiuW6EDenjTsquGB/400drG9WHn6dKiHRHZiwrnlEGXHntLrX4ed8H9RCfclcSMhIS9sFn98IPVNi5wylR1i1BG5SI1LM+QUAlIe5DOFpJv1ZY2J9i6uz2JHjkDlmgM2IrONl6dDHYxKI/BwszTptdjiPdCNwU65dZMglDAVMnYeDxNO/aBF/Y+jWe5a8UwZ3QkTpsbnxBCE4Cfi3iRF8tQoQO4t1u2uGCFQWdlxv0mQIGmIbWyEF+PdVdjjgzFRmRk1yB/WUwvFcmPuEF3G0gN+8nVqqevXqYT2SsdrlFZyOt3Wp4b6FFBGkowftVrB4GaSDDhZIAd6N+eYvdfuriwIDAQAB";
		  
	 	String signature =  createLinkString(paraFilter(getMap()));
		System.out.println("签名内容:"+signature);
//		String sign = RSAUtils.sign(signature, AlipaypublicKey, charset,sign_type);
		String sign = "Y6dMcnNGw5lYesOaQqkSSUQ7LgIG9DEwcjJ+tq+19Jvnv8lC3apmoz6fgMsybI6CvGumZ3xa2rN0mNMofBD2lqAoGlCVVkRK0Ax80QuL6X6Xx4p22qp6UF+aXuoCKLxYZ+jSJ7Z6DWqaZzVACgJoNXt0N+t/ZOOVlOxB6LFfjoRSLP2qlpGNe0aa4HiFcYUSnosqBPbz9DjK9nREzA8BSw8XJHmlbn6AaeSidC2FUqdxgt8CDG2pciMZ+r6nQDVkZZTxNLNeeSwtOLxc5mdRnA6WaS5WhMYh4MHWXN+mhH5QLobQQv6WTUMDrx+ozWv4pffIpglubY4POcsxPZbtxA==";
		System.out.println("签名结果为:"+sign);
		Map<String,String> data = getMap();
		data.put("sign", sign);
		
		getFlag(data);
		
		
		
	}
}
