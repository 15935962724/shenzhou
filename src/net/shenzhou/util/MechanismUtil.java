package net.shenzhou.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Project;

public class MechanismUtil {

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
	public static String getJson(List<Mechanism> screenRecrutiments , Integer pageNumber,Integer pagesize){
		List<Mechanism> recrutiments = screenRecrutiments.subList((pageNumber-1)*pagesize, pagesize); 
		JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"doctors","area"});//除去doctors,area属性
        String json_data = JSONArray.fromObject(recrutiments, config).toString();
        System.out.println(json_data);
		return json_data;
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
	public static List<Mechanism> getOrderByDistance(Map <String ,Object> map){
		
		List<Mechanism> mechanisms  =  new ArrayList<Mechanism>();
		
		/** 经度 */
		 double longitude = Double.valueOf(map.get("longitude").toString());
		
		/** 纬度 */
		 double latitude = Double.valueOf(map.get("latitude").toString());
		
		boolean isDesc = (Boolean) map.get("isDesc");//false 升序  true 降序
		
		mechanisms = (List<Mechanism>) map.get("mechanisms");
		
		Map<Mechanism,Integer> farmsSort = new HashMap<Mechanism,Integer>();
		
		List<Mechanism> returnFarmList = new ArrayList<Mechanism>();
//		Map<Recruitment,Integer> farmsSort = new HashMap<Recruitment,Integer>();
		List<Mechanism> returnMechanismList = new ArrayList<Mechanism>();
		
		
		for(Mechanism mechanism : mechanisms){
//			Integer distance = MapUtil.getDistance(member, recruitment.getFarm());
			Integer distance = MapUtil.GetDistance(latitude, longitude, mechanism.getLatitude(),mechanism.getLongitude());
			
			farmsSort.put(mechanism, distance);
			System.out.println("排序前:"+mechanism.getName()+",距离："+distance);
			mechanism.setDistance(distance);
		}
		List<Map.Entry<Mechanism, Integer>> list_Data = new ArrayList<Map.Entry<Mechanism, Integer>>(farmsSort.entrySet());  
		if(isDesc){
			 Collections.sort(list_Data, new Comparator<Map.Entry<Mechanism, Integer>>()  {
					public int compare(Entry<Mechanism, Integer> o1, Entry<Mechanism, Integer> o2) {
						if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0){  
				            return 1;  
				           }else{  
				            return -1;  
				           } 
					}
					 
				 });
		}else{
			 Collections.sort(list_Data, new Comparator<Map.Entry<Mechanism, Integer>>()  {
					public int compare(Entry<Mechanism, Integer> o1, Entry<Mechanism, Integer> o2) {
						if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())<0){  
				            return 1;  
				           }else{  
				            return -1;  
				           } 
					}
					 
				 });
		}
		 for(Map.Entry<Mechanism, Integer> map_data : list_Data){
			 returnFarmList.add(map_data.getKey()) ;
		 }
		 System.out.println("===========================================");
		 for(Mechanism mechanism : returnMechanismList){
//			 Integer distance = MapUtil.getDistance(member, recruitment.getFarm());
			 Integer distance = MapUtil.GetDistance(latitude, longitude, mechanism.getLatitude(),mechanism.getLongitude());
			 System.out.println("排序后:"+mechanism.getName()+",距离："+distance);
			 mechanism.setDistance(distance);
		 }
		 
		 return returnFarmList;
	
	}

}
