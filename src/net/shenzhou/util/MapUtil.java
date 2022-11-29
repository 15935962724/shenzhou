package net.shenzhou.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.sun.org.apache.bcel.internal.generic.RET;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import net.sf.json.JSONObject;
import net.shenzhou.Setting;


/**
 *  地图
* @Map 描述
* @add by wsr on 2016-6-29
* @author SHARE Team  
* @version V1.0
 */
public class MapUtil {

	 private final static double PI = 3.14159265358979323; // 圆周率
	 private final static double R = 6371229; // 地球的半径
	
	
	 private static double EARTH_RADIUS = 6378137.0;
	 private static double rad(double d){
	   return d * Math.PI / 180.0;
	 }
	 public static Integer GetDistance(double lat1, double lng1, double lat2, double lng2){
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);
	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
	   s = Math.round(s * 10000) / 10000;
	   
	   return Integer.parseInt(new java.text.DecimalFormat("0").format(s));
	 }

	 /**
		 * @getCoordinate - 根据地址获取坐标
		 * @add by wsr on 2016-6-30
		 * @author SHARE Team
		 * @return Object[]    返回类型 
		 * @version 1.0
		 */
		public static Object[] getCoordinate(String addr) throws IOException { 
			String lng = null;//经度
			String lat = null;//纬度
			String address = null; 
			try { 
				address = java.net.URLEncoder.encode(addr, "UTF-8"); 
			}catch (UnsupportedEncodingException e1) { 
				e1.printStackTrace(); 
			} 
//			String key = setting.getBaiduMapkey(); 
//			String baiduMapApi = setting.getBaiduMapApi();
			String baiduMapApi = "http://api.map.baidu.com/geocoder";
			String key = "Cc4Z1ABC0Eqiql9ZXGWfw0Pt";
			String url = String .format(baiduMapApi+"?address=%s&output=json&key=%s", address, key); 
			URL myURL = null; 
			URLConnection httpsConn = null; 
			try { 
				myURL = new URL(url); 
			} catch (MalformedURLException e) { 
				e.printStackTrace(); 
			} 
			InputStreamReader insr = null;
			BufferedReader br = null;
			try { 
				httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
				if (httpsConn != null) { 
					insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
					br = new BufferedReader(insr); 
					String data = null; 
					int count = 1;
					while((data= br.readLine())!=null){ 
						if(count==5){
							lng = (String)data.subSequence(data.indexOf(":")+1, data.indexOf(","));//经度
							count++;
						}else if(count==6){
							lat = data.substring(data.indexOf(":")+1);//纬度
							count++;
						}else{
							count++;
						}
					} 
				} 
			} catch (Exception e) { 
				e.printStackTrace(); 
			} finally {
				if(insr!=null){
					insr.close();
				}
				if(br!=null){
					br.close();
				}
			}
			return new Object[]{lng,lat}; 
		} 
		
		/**
		 * 通过坐标转换地址
		 * @param lat
		 * @param lng
		 * @return
		 * @throws IOException
		 */
		public static String  getAddress(String lat ,String lng  ) throws IOException {
				String url = "http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location="+lat+","+lng+"&output=json&pois=1&ak=Cc4Z1ABC0Eqiql9ZXGWfw0Pt";
				String data = HttpUtil.getInvoke(url);
				return data;
			
		} 

	 
		public static void main(String[] args) throws IOException {
			String data = getAddress("39.91488908", "116.40387397");
			data = data.substring(29,data.length()-1);
			JSONObject json = JsonUtils.toJSONObject(data);
			
			System.out.println(">>>"+json);
		}
		
}
	
	

