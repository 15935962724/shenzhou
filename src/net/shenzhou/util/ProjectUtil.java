package net.shenzhou.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;

import net.sf.json.JSONArray;
import net.shenzhou.Config;
import net.shenzhou.entity.Member;
import net.shenzhou.entity.Project;

public class ProjectUtil {

	/**
	* @Title: getJson 
	* @Description: TODO(分页) 
	* @param @param screenRecrutiments
	* @param @param page
	* @param @param totasize
	* @param @return 设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getJson(List<Project> screenProject , Integer pageNumber,Integer pagesize){
		if(screenProject.size()<=0){
			return JSONArray.fromObject(screenProject).toString();
		}
		List<Project> projects = screenProject.subList((pageNumber-1)*pagesize, pagesize); 
		return JSONArray.fromObject(projects).toString();
	}
	
	
	/**
	* @Title: getOrderByDistance 
	* @Description: TODO(距离排序 ) 
	* @param @param screenRecrutiments
	* @param @param map
	* @param @return 设定文件 
	* @return List<Project>    返回类型 
	* @throws
	 */
	public static List<Project> getOrderByDistance(Map <String ,Object> map){
		
		List<Project> projects  =  new ArrayList<Project>();
		
		/** 经度 */
		 double longitude = Double.valueOf(map.get("longitude").toString().equals("")?Config.longitude:map.get("longitude").toString());
		
		/** 纬度 */
		 double latitude = Double.valueOf(map.get("latitude").toString().equals("")?Config.latitude:map.get("latitude").toString());
		
		boolean isDesc = (Boolean) map.get("isDesc");//false 升序  true 降序
		
		projects = (List<Project>) map.get("projects");
		
		Map<Project,Integer> farmsSort = new HashMap<Project,Integer>();
		
		List<Project> returnFarmList = new ArrayList<Project>();
//		Map<Recruitment,Integer> farmsSort = new HashMap<Recruitment,Integer>();
		List<Project> returnProjectList = new ArrayList<Project>();
		
		
		for(Project project : projects){
//			Integer distance = MapUtil.getDistance(member, recruitment.getFarm());
			Integer distance = MapUtil.GetDistance(latitude, longitude, project.getMechanism().getLatitude(),project.getMechanism().getLongitude());
			
			farmsSort.put(project, distance);
			System.out.println("排序前:"+project.getMechanism().getName()+",距离："+distance);
		}
		List<Map.Entry<Project, Integer>> list_Data = new ArrayList<Map.Entry<Project, Integer>>(farmsSort.entrySet());  
		if(isDesc){
			 Collections.sort(list_Data, new Comparator<Map.Entry<Project, Integer>>()  {
					public int compare(Entry<Project, Integer> o1, Entry<Project, Integer> o2) {
						if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0){  
				            return 1;  
				           }else{  
				            return -1;  
				           } 
					}
					 
				 });
		}else{
			 Collections.sort(list_Data, new Comparator<Map.Entry<Project, Integer>>()  {
					public int compare(Entry<Project, Integer> o1, Entry<Project, Integer> o2) {
						if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())<0){  
				            return 1;  
				           }else{  
				            return -1;  
				           } 
					}
					 
				 });
		}
		 for(Map.Entry<Project, Integer> map_data : list_Data){
			 returnProjectList.add(map_data.getKey()) ;
		 }
		 System.out.println("===========================================");
		 for(Project project : returnProjectList){
//			 Integer distance = MapUtil.getDistance(member, recruitment.getFarm());
			 Integer distance = MapUtil.GetDistance(latitude, longitude, project.getMechanism().getLatitude(),project.getMechanism().getLongitude());
			 System.out.println("排序后:"+project.getMechanism().getName()+",距离："+distance);
		 }
		 
		 return returnProjectList;
	
	}

}
