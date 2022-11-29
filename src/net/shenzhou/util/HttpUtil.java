package net.shenzhou.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;




import java.util.Formatter;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * http请求工具
 */
public class HttpUtil
{
	
	public static String getInvoke(String urlStr){
		// for (int i = 0; i < 3; i++) {
		URL url;
		InputStream inputStream = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		HttpURLConnection connection = null;
		String is;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setReadTimeout(60000);
			System.out.println("Connect, URL:" + urlStr);
			connection.connect();
			inputStream = connection.getInputStream();
			isr = new InputStreamReader(inputStream,"UTF-8");//wsr 修改
			br =   new BufferedReader(isr);
			int c = -1;
			StringBuilder isb = new StringBuilder();
			while ((c = br.read()) >= 0) {
				isb.append((char) c);
			}
			is = isb.toString();
			return is;
//			break;
		} catch (FileNotFoundException e2) {
			System.out.println("read return info error:" + urlStr);
			System.out.println(e2.getMessage());
			return "error";
		} catch (SocketTimeoutException e) {
			System.out.println("Wait timeout,pass it." + urlStr);
			System.out.println(e.getMessage());
			return "error";
		} catch (IOException e1) {
			System.out.println("IOException." + urlStr);
			System.out.println(e1.getMessage());
//			logger.error("------------------------------------try " + (i + 1) + " times--------------------------------------");
			InputStream errStream = connection.getErrorStream();
			if (null == errStream)
				return "error";
			BufferedReader errbr = new BufferedReader(new InputStreamReader(errStream));
			StringBuilder errisb = new StringBuilder();
			int c = -1;
			try {
				while ((c = errbr.read()) >= 0) {
					errisb.append((char) c);
				}
				String errinfo = errisb.toString();
				System.out.println(errinfo);
			} catch (IOException e) {
				System.out.println("get Error info IOException.");
				System.out.println(e);
				return "error";
			} finally {
				try {
					errStream.close();
					errbr.close();
				} catch (IOException e) {
					System.out.println("get Error info close stream IOException.");
					System.out.println(e);
				}
			}
			System.out.println("----------------------------------------------------------------------------------------");
			return "error";
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				System.out.println("Dispacher IOException");
				System.out.println(e);
			}
			connection.disconnect();
		}
		// }
	}
	
	/**
	 * post请求
	 * 
	 * @param url
	 *            功能和操作
	 * @param body
	 *            要post的数据
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, String body)
	{
		System.out.println("url:" + "\n" + url);
		System.out.println("body:" + "\n" + body);

		String result = "";
		try
		{
			OutputStreamWriter out = null;
			BufferedReader in = null;
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();

			// 设置连接参数
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(20000);

			// 提交数据
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(body);
			out.flush();

			// 读取返回数据
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			boolean firstLine = true; // 读第一行不加换行符
			while ((line = in.readLine()) != null)
			{
				if (firstLine)
				{
					firstLine = false;
				} else
				{
					result += "\n";
				}
				result += line;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return result;
	}

	/**
	 * 回调测试工具方法
	 * 
	 * @param url
	 * @param reqStr
	 * @return
	 */
	public static String postHuiDiao(String url, String body)
	{
		String result = "";
		try
		{
			OutputStreamWriter out = null;
			BufferedReader in = null;
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();

			// 设置连接参数
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(20000);

			// 提交数据
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(body);
			out.flush();

			// 读取返回数据
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			boolean firstLine = true; // 读第一行不加换行符
			while ((line = in.readLine()) != null)
			{
				if (firstLine) 
				{
					firstLine = false;
				} else
				{
					result += "\n";
				}
				result += line;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	
	  public static String byteToHex(final byte[] hash) {
	        Formatter formatter = new Formatter();
	        for (byte b : hash) {
	            formatter.format("%02x", b);
	        }
	        String result = formatter.toString();
	        formatter.close();
	        return result;
	    }
	
	
	public static void main(String[] args) {
//		int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);//生成验证码
//
//
//		String json = HttpUtil.postHuiDiao("https://api.miaodiyun.com/20150822/industrySMS/sendSMS",
//				ShortMessageUtil.createCommonParam("18347653917",mobile_code));
//		System.out.println(json);
		String realName = "封雷";
        String card = "230122199409161019";
		String url="https://v.apistore.cn/api/a1";
		String param="key=8163022b92c28d82cb3d973f3042eaf4&cardNo="+card+"&realName="+realName+"&information=";
		String returnStr = HttpUtil.post(url, param);
		System.out.println(returnStr);
		
	}
}
