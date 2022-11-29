/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.shenzhou.entity.WorkDayItem;

/**
 * Utils - Web
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public final class DateUtil {
	
	/**
	 * 当前时间往后延i个月
	 * @param i
	 * @return
	 */
	public static Date getMonth(int i){
		Calendar cal = Calendar.getInstance();
		//下面的就是把当前日期加一个月
		cal.add(Calendar.MONTH, i);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("today is:" + format.format(Calendar.getInstance().getTime()));
		System.out.println("yesterday is:" + format.format(cal.getTime()));
		Date date = cal.getTime();
		return date;
	}
	
	/**
	 * 当前时间往后延7天
	 * @param i
	 * @return
	 */
	public static String getStatetime() {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  
		  Calendar c = Calendar.getInstance();  
		  c.add(Calendar.DATE, + 7);  
		  Date monday = c.getTime();
		  String preMonday = sdf.format(monday);
		  return preMonday;
	}
	
	/**
	 * 当前时间往前延7天
	 * @param i
	 * @return
	 */
	public static String getWeekTime(int x) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  
		  Calendar c = Calendar.getInstance();  
		  c.add(Calendar.DATE, -x);  
		  Date monday = c.getTime();
		  String preMonday = sdf.format(monday);
		  return preMonday;
	}
	
	/**
	 * String 转 Date
	 * @param data
	 * @param simpleDateFormat
	 * @return
	 */
	public static Date getStringtoDate(String data,String simpleDateFormat){
	    try {
	    	SimpleDateFormat sdf = new SimpleDateFormat(simpleDateFormat); 
			Date date = sdf.parse(data);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print(e.getMessage());
		}  
		return null;
	}
	
	/**
	 * Date 转 String
	 * @param simpleDateFormat
	 * @param ddate
	 * @return
	 */
	public static String getDatetoString (String simpleDateFormat ,Date date ){
		try {
			return (new SimpleDateFormat(simpleDateFormat)).format(date);  
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;
		
	}

	/**
	 * date 时间往后延i分钟
	 * @param date
	 * @param i
	 * @return
	 */
	public static String getMinute(Date date,Integer i){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		  Date now = new Date();
		  System.out.println("当前时间：" + sdf.format(date));
		Calendar nowTime = Calendar.getInstance();
		  nowTime.add(Calendar.MINUTE, i);
		  System.out.println(sdf.format(nowTime.getTime()));
		
		return sdf.format(nowTime.getTime());
	}
	
	/**
	 * 时间往后延迟i分钟  时间格式为  HH:mm
	 * @param date
	 * @param i
	 * @return
	 */
	public static String getMinute(Date date,Integer i,String simpleDate){
		
		
		 SimpleDateFormat sdf = new SimpleDateFormat(simpleDate);
//		  Date now = new Date();
		  System.out.println("当前时间：" + sdf.format(date));
		  Calendar nowTime = Calendar.getInstance();
		  nowTime.setTime(date);
		  
		  nowTime.add(Calendar.MINUTE, i);
		  System.out.println("延迟后为:"+sdf.format(nowTime.getTime()));
		  return sdf.format(nowTime.getTime());
	}
	
	
	
	
	/**
	 * Date 获取传入月份的第一天和最后一天
	 * @param simpleDateFormat
	 * @param ddate
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, String> getDateStartEnd (String date) throws ParseException{
		String datas = date+"-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date ss = sdf.parse(datas);
		 Calendar lastDate = Calendar.getInstance();
	        lastDate.setTime(ss);
	        // 设置为这个月的第 1 天
	        lastDate.set(Calendar.DATE, 1);
	        System.out.println("第 1 步 - 设置为这个月的第 1 天"+lastDate);
	        String startDates = sdf.format(lastDate.getTime());
	        lastDate.add(Calendar.MONTH, 1);
	        System.out.println("第 2 步 - 设置为加上 1 个月"+lastDate);
	        lastDate.add(Calendar.DATE, -1);
	        String endDates = sdf.format(lastDate.getTime());
	        System.out.println("第 3 步 - 设置为减去 1 天，得到【最终结果】"+lastDate);
	        
	        String startDate = startDates+" 00:00:00";
	        String endDate = endDates+" 23:59:59";
	        System.out.println("第一天"+startDate+"=============="+"最后一天"+endDate);
	        
	        Map<String,String> map = new HashMap<String,String>();
	        map.put("startDate", startDate);
	        map.put("endDate", endDate);
	        
		return map;
		
	}
	
	
	/**
	 * 判断date1  是否在  date2之后 -1 之后 1之前
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	 public static int compare_date(String DATE1, String DATE2) {
		 SimpleDateFormat df = new SimpleDateFormat("HH:mm");
	        try {
	            Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);
	            if (dt1.getTime() < dt2.getTime()) {
	                System.out.println("dt1 在dt2前");
	                return 1;
	            } else if (dt1.getTime() > dt2.getTime()) {
	                System.out.println("dt1在dt2后");
	                return -1;
	            } else {
	                return 0;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return 0;
	    }
	
	 
	 /**
	  * 计算两个时间之间相差多少分钟
	  * @param date1
	  * @param date2
	  * @param simpleDateFormat
	  * @return
	  */
	 public static int  getMinute (String date1,String date2,String simpleDateFormat){
		 SimpleDateFormat dfs = new SimpleDateFormat(simpleDateFormat);
		   
		try {
			Date begin = dfs.parse(date1);
			Date end  =  dfs.parse(date2);
			   long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
			   long min=between/60;
			   return Integer.valueOf(String.valueOf(min));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		   
		 
		 
		 return 0;
		 
	 }
	 

	/**
	 * 根据日期获取年龄
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static int getAge(Date birthDay) throws Exception {  
        Calendar cal = Calendar.getInstance();  
  
        if (cal.before(birthDay)) {  
            throw new IllegalArgumentException(  
                    "The birthDay is before Now.It's unbelievable!");  
        }  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        cal.setTime(birthDay);  
  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
  
        int age = yearNow - yearBirth;  
  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }  
        return age;  
    }  
	
	/**
	 * 当前时间往后延6天
	 * @param i
	 * @return
	 */
	public static String getStatestime(int x) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  
		  Calendar c = Calendar.getInstance();  
		  c.add(Calendar.DATE, + x);  
		  Date monday = c.getTime();
		  String preMonday = sdf.format(monday);
		  return preMonday;
	}
	
	/**
	 * 输入的时间往1天
	 * @param i
	 * @return
	 */
	public static String dataAdd(Date date,int x) {
		     Calendar  calendar  =  new GregorianCalendar(); 
		     calendar.setTime(date); 
		     calendar.add(Calendar.DATE,x);//把日期往后增加一天.整数往后推,负数往前移动 
		     date=calendar.getTime();   //这个时间就是日期往后推一天的结果 
		     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		     String preMonday = sdf.format(date);
			return preMonday;


	}
	
	/**
	 * 时间戳转时间
	 * @param i
	 * @return
	 */
	public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
	
	
	/**
	 * 获得当前第几周的开始日期和结束日期
	 * @param weekNum
	 * @return
	 */
	public static Map< String ,Date> getStartDateAndEndDate(Integer weekNum){
		 Map< String ,Date> data = new HashMap<String, Date>();
		 Date InputDate = new Date(); ;

		 Calendar cDate = Calendar.getInstance();
		 cDate.setFirstDayOfWeek(Calendar.MONDAY);
		 cDate.setTime(InputDate);
		
		 Calendar firstDate = Calendar.getInstance();
		 
		 firstDate.setFirstDayOfWeek(Calendar.MONDAY);
		 firstDate.setTime(InputDate);
		 
		 
		 Calendar lastDate = Calendar.getInstance();
		 lastDate.setFirstDayOfWeek(Calendar.MONDAY);
		 lastDate.setTime(InputDate);
		 
		 if(cDate.get(Calendar.WEEK_OF_YEAR)==1&&cDate.get(Calendar.MONTH)==11){
			 firstDate.set(Calendar.YEAR, cDate.get(Calendar.YEAR)+1);
			 lastDate.set(Calendar.YEAR, cDate.get(Calendar.YEAR)+1);
		 }
		 
//		 typeNum  = cDate.get(Calendar.WEEK_OF_YEAR);
		 System.out.println(weekNum);
		
		 firstDate.set(Calendar.WEEK_OF_YEAR, weekNum);
		 firstDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		 firstDate.set(Calendar.HOUR_OF_DAY,00);
		 firstDate.set(Calendar.MINUTE,00);
		 firstDate.set(Calendar.SECOND,00);
		 //所在周开始日期
		 System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(firstDate.getTime()));
		 
		 lastDate.set(Calendar.WEEK_OF_YEAR, weekNum);
		 lastDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		 lastDate.set(Calendar.HOUR_OF_DAY,23);
		 lastDate.set(Calendar.MINUTE,59);
		 lastDate.set(Calendar.SECOND,59);
		 //所在周结束日期
		 System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastDate.getTime()));

		 data.put("startDate",firstDate.getTime());
		 data.put("endDate",lastDate.getTime());
		 return data;
		
		
		
	}
	
	
	/**
	 * 获得当前日期在今年的第几周
	 * @return
	 */
	public static Integer getWeekNum(){
		
		Date date = new Date(); 

		Calendar calendar = Calendar.getInstance();  
		
		calendar.setFirstDayOfWeek(Calendar.MONDAY);  
		calendar.setTime(date);  
		  
		System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));  
		return calendar.get(Calendar.WEEK_OF_YEAR);
		
	}
	
	/**
	 * 周转换
	 * @param calendar
	 * @return
	 */
	 public static String getCurrentWeekOfMonth(Calendar calendar) {
   	  String strWeek = "";
   	  int dw = calendar.get(Calendar.DAY_OF_WEEK);
   	  if (dw == 1) {
   	   strWeek = "星期日";
   	  } else if (dw == 2) {
   	   strWeek = "星期一";
   	  } else if (dw == 3) {
   	   strWeek = "星期二";
   	  } else if (dw == 4) {
   	   strWeek = "星期三";
   	  } else if (dw == 5) {
   	   strWeek = "星期四";
   	  } else if (dw == 6) {
   	   strWeek = "星期五";
   	  } else if (dw == 7) {
   	   strWeek = "星期六";
   	  }
   	  return strWeek;
   	 }
		/**
		 * 根据周获得一周的所有日期   
		 * @param week
		 * @return
		 */
		public static List<Map<String,Object>> getDates(int week){
			List<Map<String,Object>> dates = new ArrayList<Map<String,Object>>();
		       for (int i = 0; i < 7; i++) {
		       	Map<String,Object> map = new HashMap<String, Object>();
		       	Calendar c = new GregorianCalendar();
			        Date date = new Date(); 
					Calendar calendar = Calendar.getInstance();  
					calendar.setFirstDayOfWeek(Calendar.MONDAY);  
					calendar.setTime(date); 
			        c.set(Calendar.YEAR,calendar.get(Calendar.YEAR));
			        c.set(Calendar.MONTH, Calendar.JANUARY);
			        c.set(Calendar.DATE, 1);
			        Calendar cal = (GregorianCalendar) c.clone();
			        cal.add(Calendar.DATE, (week-1) * 7);
			        c.setFirstDayOfWeek(Calendar.MONDAY);
			        c.setTime(cal.getTime());
			        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		       	c.add(Calendar.DATE, i);
		       	map.put("workDate", c.getTime());
		       	map.put("week", getCurrentWeekOfMonth(c));
		       	dates.add(map);
		       	System.out.println("key:"+getCurrentWeekOfMonth(c) +",value:"+DateUtil.getDatetoString("yyyy-MM-dd", c.getTime()));
				}
			
			    return dates;
		}
		 
		/**
		 * 判断是否需要获取今天  明天 后台
		 * @param i
		 * @return
		 */
		public static boolean getBoolean(int i){
			if (i==0||i==1||i==2) {
				return true;
			}
			return false;
		}
		/**
		 * 获取 今天  明天  后台
		 * @param i
		 * @return
		 */
		public static String getDay(int i) {
			if (i==0) {
				return "今天";
			}
			if (i==1) {
				return "明天";
			}
			if (i==2) {
				return "后天";
			}
			return null;
		   	  
		   	
		   }
		
		
		/**
		 * 获取当前日期往后的一周所有日期
		 * @return
		 */
		public static List<Map<String,Object>> getDateDays(){
			List<Map<String,Object>> dateDays = new ArrayList<Map<String,Object>>();
			 for (int i = 0; i < 7; i++) {
				 Map<String,Object> map = new HashMap<String, Object>();
				 Date date=new Date();//取时间
				 Calendar calendar = new GregorianCalendar();
				 calendar.setTime(date);
				 calendar.add(calendar.DATE,i);//把日期往后增加一天.整数往后推,负数往前移动
				 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
				 map.put("week",getBoolean(i)?getDay(i):getCurrentWeekOfMonth(calendar));
				 map.put("dateDay", date);
				 dateDays.add(map);
				 System.out.println("week:"+map.get("week")+",dateDay:"+DateUtil.getDatetoString("yyyy-MM-dd", (Date)map.get("dateDay")));
			}
			 return dateDays;
			
		}
	
		/**
		 * 排班信息色块绘制公式
		 * @param startTime
		 * @param endTime
		 * @return
		 */
		public static Double getJ(String workDateStartTime,String workDateEndTime){
			 double start = Double.valueOf(workDateStartTime.split(":")[0]);
			 double end = Double.valueOf(workDateEndTime.split(":")[1])>0?(Double.valueOf(workDateEndTime.split(":")[0])+1):Double.valueOf(workDateEndTime.split(":")[0]);
			 return (100/(end-start)/12);
			
		}
		
		/**
		 * 传入时间往前延n天
		 * @param i
		 * @return
		 */
		public static String getLastDate(Date date , int key) {
			  Date dBefore = new Date();
			  Calendar calendar = Calendar.getInstance(); //得到日历
			  calendar.setTime(date);//把当前时间赋给日历
			  calendar.add(Calendar.DAY_OF_MONTH, -key);  //设置为前一天
			  dBefore = calendar.getTime();   //得到前一天的时间
			  return getDatetoString("yyyy-MM-dd",dBefore);
		}
	
		/**
		 * 获取该月份下的所有日期
		 * @param date
		 * @return
		 */
		public static List<Date> getAllTheDateOftheMonth(Date date) {
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
		
		
		/**
		 * 根据月份获取该月份的第一天和最后一天
		 * @param date
		 * @return
		 */
		public static Map<String,Object> getStartDateAndEndDate(Date date) {
			Map<String,Object> data = new HashMap<String, Object>();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY,00);
			calendar.set(Calendar.MINUTE,00);
			calendar.set(Calendar.SECOND,00);
			Date firstDayOfMonth = calendar.getTime();  
			data.put("startDate", firstDayOfMonth);
			System.out.println(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", firstDayOfMonth));
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			Date lastDayOfMonth = calendar.getTime();
			data.put("endDate", lastDayOfMonth);
			System.out.println(DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss", lastDayOfMonth));
			return data;
			
		}
		
		
		/**
		 * 判断date1  是否在  date2之后 -1 之后 1之前
		 * @param DATE1
		 * @param DATE2
		 * @return
		 */
		 public static int compare_date_day(String DATE1, String DATE2) {
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		        try {
		            Date dt1 = df.parse(DATE1);
		            Date dt2 = df.parse(DATE2);
		            if (dt1.getTime() < dt2.getTime()) {
		                System.out.println("dt1 在dt2前");
		                return 1;
		            } else if (dt1.getTime() > dt2.getTime()) {
		                System.out.println("dt1在dt2后");
		                return -1;
		            } else {
		                return 0;
		            }
		        } catch (Exception exception) {
		            exception.printStackTrace();
		        }
		        return 0;
		    }
			
		 
			/**
			 * 判断date1  是否在  date2之后 -1 之后 1之前
			 * @param DATE1
			 * @param DATE2
			 * @return
			 */
			 public static int compare_date_month(String DATE1, String DATE2) {
				 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			        try {
			            Date dt1 = df.parse(DATE1);
			            Date dt2 = df.parse(DATE2);
			            if (dt1.getTime() < dt2.getTime()) {
			                System.out.println("dt1 在dt2前");
			                return 1;
			            } else if (dt1.getTime() > dt2.getTime()) {
			                System.out.println("dt1在dt2后");
			                return -1;
			            } else {
			                return 0;
			            }
			        } catch (Exception exception) {
			            exception.printStackTrace();
			        }
			        return 0;
			    }
				
			 /**
				 * 判断date1  是否在  date2之后 -1 之后 1之前
				 * @param DATE1
				 * @param DATE2
				 * @return
				 */
				 public static int compare_date_days(String DATE1, String DATE2) {
					 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				        try {
				            Date dt1 = df.parse(DATE1);
				            Date dt2 = df.parse(DATE2);
				            if (dt1.getTime() < dt2.getTime()) {
				                System.out.println("dt1 在dt2前");
				                return 1;
				            } else if (dt1.getTime() > dt2.getTime()) {
				                System.out.println("dt1在dt2后");
				                return -1;
				            } else {
				                return 0;
				            }
				        } catch (Exception exception) {
				            exception.printStackTrace();
				        }
				        return 0;
				    }
					
		 
		 
		/**
		 * 当前时间往前延i个月
		 * @param i
		 * @return
		 */
		 public static String dateFormat(Date datetime , int Key) {  
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
		        Calendar cl = Calendar.getInstance();  
		        cl.setTime(datetime);  
		        cl.add(Calendar.MONTH, -Key);  
		        datetime = cl.getTime();  
		        return sdf.format(datetime);  
		    }  
		  
		 
		 /**
			 * 判断当前时间点后有无医生机构时间
			 * @param start
			 * @param workDay
			 * @return
			 */
			public static String isEndData(String startTime,List<WorkDayItem> workDayItems){
				List<Date> lists = new ArrayList<Date>();
				for (WorkDayItem workDayItem : workDayItems){//将所有已占用的时间加到lists里面
					lists.add(DateUtil.getStringtoDate(workDayItem.getStartTime(), "HH:mm"));
					lists.add(DateUtil.getStringtoDate(workDayItem.getEndTime(), "HH:mm"));
				}
				
				for(Date date : lists){
					if(DateUtil.compare_date(DateUtil.getDatetoString("HH:mm", date), startTime)==0){
						startTime = DateUtil.getMinute(DateUtil.getStringtoDate(startTime, "HH:mm"), 1, "HH:mm");
					}
				}
				
				lists.add(DateUtil.getStringtoDate(startTime, "HH:mm"));//将开始时间插入lists集合并转成Date类型
				ComparatorDate c = new ComparatorDate();
				Collections.sort(lists, c);  
				
				String workDayItemStartTime = "";
				for (int i = 0; i < lists.size(); i++) {
					if (DateUtil.compare_date(startTime, DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)))==0) {
						if(i+2>=lists.size()){
							return "false";
						}else{
							return "true";
						}
					}
					System.out.println(DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)));
				}
				
				
				return workDayItemStartTime;
			}
			/**
			 * 返回下一个机构时间开始时间
			 * @param start
			 * @param workDay
			 * @return
			 */
			public static String getMechanismEndData(String startTime,List<WorkDayItem> workDayItems){
				List<Date> lists = new ArrayList<Date>();
				for (WorkDayItem workDayItem : workDayItems){//将所有已占用的时间加到lists里面
					lists.add(DateUtil.getStringtoDate(workDayItem.getStartTime(), "HH:mm"));
					lists.add(DateUtil.getStringtoDate(workDayItem.getEndTime(), "HH:mm"));
				}
				
				for(Date date : lists){
					if(DateUtil.compare_date(DateUtil.getDatetoString("HH:mm", date), startTime)==0){
						startTime = DateUtil.getMinute(DateUtil.getStringtoDate(startTime, "HH:mm"), 1, "HH:mm");
					}
				}
				
				lists.add(DateUtil.getStringtoDate(startTime, "HH:mm"));//将开始时间插入lists集合并转成Date类型
				ComparatorDate c = new ComparatorDate();
				Collections.sort(lists, c);  
				
				String workDayItemStartTime = "";
				for (int i = 0; i < lists.size(); i++) {
					if (DateUtil.compare_date(startTime, DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)))==0) {
						workDayItemStartTime = DateUtil.getDatetoString("HH:mm", (Date)lists.get(i+2));
						break;
					}
					System.out.println(DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)));
				}
				
				
				return workDayItemStartTime;
			}
			
			
			/**
			 * 返回下一个预约时间开始时间
			 * @param start
			 * @param workDay
			 * @return
			 */
			public static String getEndData(String startTime,List<WorkDayItem> workDayItems){
				List<Date> lists = new ArrayList<Date>();
				for (WorkDayItem workDayItem : workDayItems){//将所有已占用的时间加到lists里面
					lists.add(DateUtil.getStringtoDate(workDayItem.getStartTime(), "HH:mm"));
					lists.add(DateUtil.getStringtoDate(workDayItem.getEndTime(), "HH:mm"));
				}
				
				for(Date date : lists){
					if(DateUtil.compare_date(DateUtil.getDatetoString("HH:mm", date), startTime)==0){
						startTime = DateUtil.getMinute(DateUtil.getStringtoDate(startTime, "HH:mm"), 1, "HH:mm");
					}
				}
				
				lists.add(DateUtil.getStringtoDate(startTime, "HH:mm"));//将开始时间插入lists集合并转成Date类型
				ComparatorDate c = new ComparatorDate();
				Collections.sort(lists, c);  
				
				String workDayItemStartTime = "";
				for (int i = 0; i < lists.size(); i++) {
					if (DateUtil.compare_date(startTime, DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)))==0) {
						workDayItemStartTime = DateUtil.getDatetoString("HH:mm", (Date)lists.get(i+1));
						break;
					}
					System.out.println(DateUtil.getDatetoString("HH:mm", (Date)lists.get(i)));
				}
				
				
				return workDayItemStartTime;
			}
					
			/**
			 * 返回按时间顺序排序后的所有时间段的最后一个时间点
			 * @param start
			 * @param workDay
			 * @return
			 */
			public static String getLastTime(List<WorkDayItem> workDayItems){
				List lists = new ArrayList();
				for (WorkDayItem workDayItem : workDayItems){//将所有已占用的时间加到lists里面
					lists.add(DateUtil.getStringtoDate(workDayItem.getStartTime(), "HH:mm"));
					lists.add(DateUtil.getStringtoDate(workDayItem.getEndTime(), "HH:mm"));
				}
				ComparatorDate c = new ComparatorDate();
				Collections.sort(lists, c);  
				
				String workDayItemStartTime = DateUtil.getDatetoString("HH:mm",(Date)lists.get(lists.size()-1));
				
				return workDayItemStartTime;
			}		 
		 
			/**
			 * @author wsr
			 * @date 2017-9-30 10:17:57
			 * 判断某一时间是否在一个区间内
			 * 
			 * @param sourceTime
			 *            时间区间,半闭合,如[10:00-20:00)
			 * @param curTime
			 *            需要判断的时间 如10:00
			 * @return 
			 * @throws IllegalArgumentException
			 */
			public static boolean isInTime(String sourceTime, String curTime) {
			    if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
			        throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
			    }
			    if (curTime == null || !curTime.contains(":")) {
			        throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
			    }
			    String[] args = sourceTime.split("-");
			    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			    try {
			        long now = sdf.parse(curTime).getTime();
			        long start = sdf.parse(args[0]).getTime();
			        long end = sdf.parse(args[1]).getTime();
			        if (args[1].equals("00:00")) {
			            args[1] = "24:00";
			        }
			        if (end < start) {
			            if (now >= end && now < start) {
			                return false;
			            } else {
			                return true;
			            }
			        } 
			        else {
			            if (now >= start && now < end) {
			                return true;
			            } else {
			                return false;
			            }
			        }
			    } catch (ParseException e) {
			        e.printStackTrace();
			        throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
			    }

			}
			
			/**
			 * 比较两个日期是否同一天
			 * @param date1
			 * @param date2
			 * @return
			 */
			public static boolean isSameDate(Date date1, Date date2) {
			       Calendar cal1 = Calendar.getInstance();
			       cal1.setTime(date1);

			       Calendar cal2 = Calendar.getInstance();
			       cal2.setTime(date2);

			       boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
			               .get(Calendar.YEAR);
			       boolean isSameMonth = isSameYear
			               && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
			       boolean isSameDate = isSameMonth
			               && cal1.get(Calendar.DAY_OF_MONTH) == cal2
			                       .get(Calendar.DAY_OF_MONTH);

			       return isSameDate;
			   }
			
			 /**
			  * 获取两个日期之间的所有日期
			  * @param dBegin
			  * @param dEnd
			  * @return
			  */
			 public static List<Date> findDates(Date dBegin, Date dEnd)  
				 {  
				  List lDate = new ArrayList();  
				  lDate.add(dBegin);  
				  Calendar calBegin = Calendar.getInstance();  
				  // 使用给定的 Date 设置此 Calendar 的时间  
				  calBegin.setTime(dBegin);  
				  Calendar calEnd = Calendar.getInstance();  
				  // 使用给定的 Date 设置此 Calendar 的时间  
				  calEnd.setTime(dEnd);  
				  // 测试此日期是否在指定日期之后  
				  while (dEnd.after(calBegin.getTime()))  
				  {  
				   // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
				   calBegin.add(Calendar.DAY_OF_MONTH, 1);  
				   lDate.add(calBegin.getTime());  
				  }  
				  return lDate;  
				 } 
			
			public static void main(String[] args) {
//				System.out.println(getMinute(new Date(), 5));
				
//				System.out.println(getStringtoDate("6:30", "HH:mm"));
//				
//				Date date = getStringtoDate("6:30", "HH:mm");
//				
//				 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
////				  Date now = new Date();
//				  System.out.println("当前时间：" + sdf.format(date));
//				  Calendar nowTime = Calendar.getInstance();
//				  nowTime.setTime(date);
//				  
////				  Calendar nowTime = date;
//				  nowTime.add(Calendar.MINUTE, 90);
//				  System.out.println(">>>>"+sdf.format(nowTime.getTime()));
				  
		/*		  String date1 = "12:30";
				  String date2 = "14:50";
				  System.out.println(getMinute(date1, date2, "HH:mm"));*/
//				  System.out.println(compare_date(date1, date2));
//				   String orderStartTime = "2017-10-09 19:59";
//					Date orderStart = DateUtil.getStringtoDate(orderStartTime, "yyyy-MM-dd HH:mm");
//					String date =  DateUtil.getDatetoString("yyyy-MM-dd HH:mm", new Date());
//					System.out.println(date);
//					Date dd = DateUtil.getStringtoDate(date, "yyyy-MM-dd HH:mm");
//					Long diff = (dd.getTime() - orderStart.getTime());
//					
//				    System.out.println(diff);
//					
//				    // 计算差多少分钟
//					Long min = diff/(1000*60);
//					
//				     System.out.println(min);
//				     
//				     Date toDaydate = new Date();
//				     
//				     Date date2 = DateUtil.getStringtoDate("2017-10-09", "yyyy-MM-dd");
//				     
//				     System.out.println(isSameDate(toDaydate,date2));
//				  
//				     int marginLeft =(DateUtil.getMinute(String.valueOf("06:30"), String.valueOf("12:00"), "HH:mm")) ;
//					 System.out.println("marginLeft:"+marginLeft);
			
				Date date = getStringtoDate("2018-04-23", "yyyy-MM-dd");
				int keys = 1;
				for (int i = 2; i < 7; i++) {
					System.out.println(dataAdd(date,keys));;
					keys++;
				}
				
				int count = 5;
				
				System.out.println(count-1);
				System.out.println(count);
						
				
				
				
			}
			
			/**
		     * 获取两个日期之间的所有日期（yyyy-MM-dd）
		     * @Description TODO
		     * @param begin
		     * @param end
		     * @return
		     * @author XuJD
		     * @date 2017-4-1
		     */
		    public static List<Date> getBetweenDates(Date begin, Date end) {
		            List<Date> result = new ArrayList<Date>();
		            Calendar tempStart = Calendar.getInstance();
		            tempStart.setTime(begin);
		            /* Calendar tempEnd = Calendar.getInstance();
		            tempStart.add(Calendar.DAY_OF_YEAR, 1);
		            tempEnd.setTime(end);
		            while (tempStart.before(tempEnd)) {
		                result.add(tempStart.getTime());
		                tempStart.add(Calendar.DAY_OF_YEAR, 1);
		            }*/
		         while(begin.getTime()<=end.getTime()){
		             result.add(tempStart.getTime());
		             tempStart.add(Calendar.DAY_OF_YEAR, 1);
		             begin = tempStart.getTime();
		         }
		            return result;
		    }
		    
		    
		    
		    /**
		     * 获取两个日期之间的全部日期
		     * @param minDate
		     * @param maxDate
		     * @return
		     * @throws ParseException
		     */
		    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		    	      ArrayList<String> result = new ArrayList<String>();
		    	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
		    	 
		    	      Calendar min = Calendar.getInstance();
		    	      Calendar max = Calendar.getInstance();
		    	  
		    	      min.setTime(sdf.parse(minDate));
		    	      min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
		    	 
		    	     max.setTime(sdf.parse(maxDate));
		    	     max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		    	 
		    	     Calendar curr = min;
		    	     while (curr.before(max)) {
		    	     result.add(sdf.format(curr.getTime()));
		    	      curr.add(Calendar.MONTH, 1);
		    	     }
		    	 
		    	     return result;
		    	   }
		      
}