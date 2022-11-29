package net.shenzhou.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.time.DateUtils;

import net.shenzhou.entity.WorkDay;
import net.shenzhou.entity.WorkDay.WorkType;
import net.shenzhou.util.DateUtil;
import net.shenzhou.util.HttpUtil;
import net.shenzhou.util.JsonUtils;

import com.sun.mail.util.MailSSLSocketFactory;
import com.sun.net.ssl.internal.ssl.Provider;

public class Test {

	private static Date formatDate(String date) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");  
        try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;  
    }  
	
	private static List<Date> getAllTheDateOftheMonth(Date date) {
		List<Date> list = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		int month = cal.get(Calendar.MONTH);
		while(cal.get(Calendar.MONTH) == month){
			list.add(cal.getTime());
			cal.add(Calendar.DATE, 1);
		}
		return list;
	}
	
	public static void getSum(){
		int Abaiwei = 0 ;
		int Ashiwei = 0 ;
		int Agewei = 0 ;
		
		int Bbaiwei = 0 ;
		int Bshiwei = 0 ;
		int Bgewei = 0 ;
		for (int i = 1; i < 10; i++) {
			Abaiwei = i*100;
			for (int j = 0; j < 10; j++) {
				Ashiwei = j*10 ;
				for (int k = 0; k < 10; k++) {
					Agewei = k ;
					int a = Abaiwei + Ashiwei + Agewei ;
					for (int x = 1; x < 10; x++) {
						Bbaiwei = x*100;
						for (int y = 0; y < 10; y++) {
							Bshiwei = y*10;
							for (int z = 0; z < 10; z++) {
								Bgewei = z;
								int b = Bbaiwei + Bshiwei + Bgewei ;
//								System.out.println(a  +  "" + b );
								int sum = Bbaiwei*10 + Bshiwei*100 + Agewei*10 + Ashiwei;
								for (int l = 1000; l < 9999; l++) {
									if ((a+b) == l) {
//										System.out.println(a+"+"+b+"="+l);
										int hq = l/1000;
										int hb = l/100%10;
										int hs = l/10%10;
										int hg = l%10;
										int ab = a/100;
										int as = a/10%10;
										int ag = a%10;
										int bb = b/100;
										int bs = b/10%10;
										int bg = b%10;
//										System.out.println("A的百位 是："+ab+"，十位是："+as +"，个位是:"+ag);
//										System.out.println("B的百位 是："+bb+"，十位是："+bs +"，个位是:"+bg);
//										System.out.println("和的千位 是："+hq+"，百位是:" +hb+"，十位是："+hs +"，个位是:"+hg);
										if (bb==hq&&bs==hb&&bg==hs&&ag==hs&&hs==ab&&as==hg) {
											System.out.println(a+"+"+b+"="+l);
											System.out.println("好="+ab+"，是="+as +"，好="+ag);
											System.out.println("要="+bb+"，做="+bs +"，好="+bg);
											System.out.println("要="+hq+"，做=" +hb+"，好="+hs +"，事="+hg);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	public static void getSum1(){
		int Abaiwei = 0 ;
		int Ashiwei = 0 ;
		int Agewei = 0 ;
		
		int Bbaiwei = 0 ;
		int Bshiwei = 0 ;
		int Bgewei = 0 ;
		
		for (int i = 1; i < 10; i++) {
			Abaiwei = i*100;
			Bbaiwei = i*100;
			for (int j = 0; j < i; j++) {
				Ashiwei = j*10 ;
				Bshiwei = j*10;
				for (int k = 0; k < 10; k++) {
					Agewei = k ;
					Bgewei = k;
					int a = Abaiwei + Ashiwei + Agewei ;
					int b = Bbaiwei + Bshiwei + Bgewei ;
					int sum = Bbaiwei*10 + Bshiwei*100 + Agewei*10 + Ashiwei;
					for (int l = 1000; l < 9999; l++) {
						if ((a+b) == l) {
							
							System.out.println("A数"+ Abaiwei/100 +" "+" " +Ashiwei/10 +" " +Agewei +" B数: " +Bbaiwei/100 +" " +Bshiwei/10+" "+ Bgewei );
							if ((l/1000)==(Bbaiwei/100)&&(l/100%10)==(Bshiwei/10)&&(l/10%10)==Agewei&&(l%10)==(Ashiwei/10)) {
								System.out.println( Abaiwei/100 +" "+" " +Ashiwei/10 +" " +Agewei +" B数: " +Bbaiwei/100 +" " +Bshiwei/10+" "+ Bgewei );
								
							}
							
							
							
						}
					}
					
					
					
					
				}
			}
			
			
			
		}
		
		
		
	}
	
	public static BufferedImage getReadImg(String source) throws IOException {
		URL url = new URL(source);
//		URLConnection con = url.openConnection();
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		// 不超时
		con.setConnectTimeout(0);
		// 不允许缓存
		con.setUseCaches(false);
		con.setDefaultUseCaches(false);
		InputStream is = null;
		System.out.println(con.getResponseCode());
		if (con.getResponseCode()==200) {
			 is = con.getInputStream();
		}
		
		// 先读入内存
		ByteArrayOutputStream buf = new ByteArrayOutputStream(8192);
		byte[] b = new byte[1024];
		int len;
		while ((len = is.read(b)) != -1) {
			buf.write(b, 0, len);
		}
		// 读图像
		is = new ByteArrayInputStream(buf.toByteArray());
		BufferedImage buffer = ImageIO.read(is);
		return buffer;

	}
	
	public static void getInsert(){
		String arrayList = "脑瘫 & 智力障碍 & 发育迟缓 & 发育障碍 & 运动发育迟缓 & 精神发育迟缓 & 言语发育迟缓 &  认知障碍 & 感觉统合失调 & 自闭症 & 孤独症 & 听力障碍 & 构音障碍 & 失语症 & 口吃 & 吞咽障碍 & 飞机手 & 肩关节不稳 & 翼状肩 & 腰椎前凸 & 脊柱侧凸 & 盆骨前倾 & 盆骨后倾 & 抓握困难 & 腕关节下垂 & 拇指内收 & 髋关节发育不良 & 膝关节内外翻 & 尖足 & 剪刀步 & 裸关节障碍 & 足内翻 & 足外翻 & 扁平足 & 刻板行为 & 抽动症 & 多动症 & 中枢神经损伤 & 臂丛神经损伤 & 腋神经损伤 & 肌皮神经损伤 & 正中神经损伤 &坐骨神经损伤 &  桡神经损伤 & 尺神经损伤 & 股神经损伤 & 腓总神经损伤 & 视神经损伤 & 周围神经损伤 & 脊髓神经损伤 & 面神经炎 & 面瘫 & 格林巴利 & 小儿麻痹综合征  & 先天性脊柱裂 & 骨骼异常 & 脑炎 & 脑外伤 & 脑梗塞 & 脑积水 & 偏瘫 &";
		String [] arrarylist = arrayList.split("&");
		for (String string : arrarylist) {
			System.out.println("insert into `xx_tag` ( `create_date`, `modify_date`, `orders`, `icon`, `memo`, `name`, `type`, `is_deleted`) values('2015-11-10 00:00:49','2015-11-10 00:00:49','4',NULL,NULL,'"+string.trim()+"');");
		}
	}
	
	//发送邮件方法
	public static MimeMessage createTextEmail(Session session) throws AddressException, MessagingException{
		//创建邮件对象
		MimeMessage message = new MimeMessage(session);
		//指明邮件的发送方
		message.setFrom(new InternetAddress("493051854@qq.com"));
		//指明 邮件 收件人
		message.setRecipients(Message.RecipientType.TO, "1263451851@qq.com");
		//设置邮件标题
		message.setSubject("测试标题");
		//设置邮件内容  ，参数 1.邮件文本内容   2.字符集编码格式
		message.setContent("测试内容", "text/html;charset=utf-8");
		
		return message;
	}
	
	public static void main1() throws MessagingException, GeneralSecurityException{
		
		Properties prop = new Properties();
		prop.setProperty("mail.host", "smtp.qq.com");
		prop.setProperty("mail.transport.protocol", "smtp");
		//是否验证  true 为是  false为 否
		prop.setProperty("mail.smtp.auth", "false");
		prop.setProperty("mail.smtp.ssl.enable", "false");
		
		Security.addProvider(new Provider()); 
		prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.setProperty("mail.smtp.port", "465");
		prop.setProperty("mail.smtp.socketFactory.port", "465");
		
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.socketFactory", sf);
		
		//1.开启dubug 模式 ，这样我们就可以看到程序运行流程
		Session session = Session.getInstance(prop);
		session.setDebug(true);
		//2.通过session 得到 transport 对象
		Transport ts = session.getTransport();
		//3.我们通过登录邮箱账号和密码链接服务器
//		krfsjrbmqflbjcfc
		ts.connect("smtp.qq.com", "493051854@qq.com", "krfsjrbmqflbjcfc");
//		ts.connect("smtp.qq.com", "493051854@qq.com", "wangshuangrui123");
		//4.创建邮件
		Message message = createTextEmail(session);
		//5.发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		//6.关闭transport对象
		ts.close();
		
	}
	
	
	public static void main(String[] args) throws IOException, MessagingException, GeneralSecurityException {
//		String aa = "{a:\"6:30\",b:\"7:30\"}";
//		JSONObject json = JSONObject.fromObject(aa);
//	    System.out.println(json.get("a"));;
//	    Map<String,Object> map = new HashMap<String,Object>();
//	    map.put("c","7:30");
//	    map.put("d", "8:40");
//	    System.out.println(JsonUtils.toJson(map));
//	    
//		
//		System.out.println(aa.replaceAll(":", ""));
		
		
//		List lists = new ArrayList();	
//		lists.add(formatDate("6:30"));
//		lists.add(formatDate("16:30"));
//		lists.add(formatDate("12:30"));
//		lists.add(formatDate("10:30"));
//		System.out.println("排序前");
//		for (Object list : lists) {
//			System.out.println(DateUtil.getDatetoString("HH:mm", (Date)list));
//		}
////		Collections.sort(lists,Collator.getInstance(java.util.Locale.CHINA));
//		ComparatorDate c = new ComparatorDate();
//		Collections.sort(lists, c);  
//		System.out.println("================================");
//		System.out.println("排序后");
//		
//		String end = "";
//		for (int i = 0; i < lists.size(); i++) {
//			String date1 ="12:30";
//			if (DateUtil.compare_date(date1, DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)))==0) {
//				end = DateUtil.getDatetoString("HH:mm", (Date)lists.get(i+1));
//			}
//			System.out.println(DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)));
//		}
//		
//		System.out.println("end:"+end);
//		for (Object list : lists) {
//			Date date1 = formatDate("12:30");
//			System.out.println(DateUtil.getDatetoString("HH:mm", (Date)list));
//		}
/*		
		Date date= DateUtil.getStringtoDate("2017-08", "yyyy-MM");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY,00);
		calendar.set(Calendar.MINUTE,00);
		calendar.set(Calendar.SECOND,00);
		Date firstDayOfMonth = calendar.getTime();  
		System.out.println(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", firstDayOfMonth));
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		Date lastDayOfMonth = calendar.getTime();
		System.out.println(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", lastDayOfMonth));
		
//		
//	List<Date> list = getAllTheDateOftheMonth(date1);
//	
//	for(Date date: list){
//		System.out.println(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", date));
//	}
//		
	
		BigDecimal decimal = new BigDecimal(123);
		decimal = decimal.multiply(new BigDecimal(-1));
		System.out.println(decimal);
	*/
		
//		getSum();
//		System.out.println(getReadImg("http://www.sharestar.cn/upload/image/bg-share.png"));;
//		getInsert();
		
//		main1();
		
		
		 String[] array = {
//				 二级权限码表
//				机构管理
				    "authentication",//企业认证
				    "mechanism",//企业资料
				    "mechanismrolelist",//角色设置
				    "serviceTime",//服务时间
				    "serverProjectCotegorylist",//服务项目(原先的服务分类)
				    "achievements",//绩效管理
//				员工管理
					"doctorlist",//员工管理
					"doctorCategoryList",//职级设置
//				预约管理
					"projectlist",//项目审核
					"workDaylist",//排班管理
					"orderlist",//订单管理
//				用户管理
					"memberlist",//用户信息
					"patientlist",//患者信息
//				运营管理
					"couponlist",//优惠券管理
//				财务管理
					"memberrecharge",//账户充值
					"refundslist",//退款管理
					"rechargeLoglist",//充值统计
					"statisticscharge",//收费统计
					"statisticslist",//收费月报
//				统计报表
					"ordercharge",//预约统计
					"projectcharge",//项目统计
					"memberpatienthealthType",//患者状态
					"evaluatelist"//评价统计(原先评价信息)
					};
//	     System.out.println(array.length);
//	        for (String string : array) {
//	        	System.out.print(string+",");
////				System.out.println("insert into `xx_mechanism_role_authority` (`mechanism_role`, `authorities`) values('9','"+string+"');");
//			}
		 
		 	String key="5f819d9fb1b706c23e4bae8c93b7df60";
//	        String key="8163022b92c28d82cb3d973f3042eaf4";
	        String card="431102199104082048";
	        String realName="朱兰兰";
//	        String card="130432200009031726";
//	        String realName="程可可";
//	        String cardUrl = "http://v.apistore.cn/api/a1";
	        String cardUrl = "http://v.apistore.cn/api/v4/idcard_info";
	        
	        String param="key="+key+"&cardNo="+card+"&realName="+realName+"&information=1";
//	        String param="key="+key+"&cardNo="+card;
//			String returnStr = HttpUtil.post(cardUrl, param);
//			System.out.println(returnStr);
			
	        long startTime = System.currentTimeMillis();
	        for(int x = 0;x<5;x++){
				System.out.println("当前日期加"+x+"天为："+DateUtil.dataAdd(new Date(), x));
	        }
	        long endTime = System.currentTimeMillis();
//	        System.out.println(endTime-startTime);
	        
	        Date date = DateUtils.addMinutes(DateUtil.getStringtoDate("2018-06-04 16:54", "yyyy-MM-dd HH:mm"), 168*60);
	        System.out.println(DateUtil.getDatetoString("yyyy-MM-dd HH:mm", date));
	        
	}
	
}
