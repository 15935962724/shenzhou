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

public class SortUtil {

	/**
	 * 自定义排序
	 * @param map_list
	 * @param data
	 * @param isDesc false 升序  true 降序
	 * @return
	 */
	public static List<Map<String,Object>> getOrderByDistance(List<Map<String,Object>> map_list,String data,Boolean isDesc){
		
		Map<Map<String,Object>,Double> farmsSort = new HashMap<Map<String,Object>,Double>();
		
		List<Map<String,Object>> returnFarmList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> returnMechanismList = new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> map : map_list){
//			Integer distance = MapUtil.getDistance(member, recruitment.getFarm());
			Double distance = Double.valueOf(map.get(data).toString());
			
			farmsSort.put(map, distance);
			System.out.println("排序前:"+map.get("doctorName")+",的值"+distance);
//			map.setDistance(distance);
		}
		List<Map.Entry<Map<String,Object>, Double>> list_Data = new ArrayList<Map.Entry<Map<String,Object>, Double>>(farmsSort.entrySet());  
		if(isDesc){
			 Collections.sort(list_Data, new Comparator<Map.Entry<Map<String,Object>, Double>>()  {
					public int compare(Entry<Map<String,Object>, Double> o1, Entry<Map<String,Object>, Double> o2) {
						if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0){  
				            return 1;  
				           }else{  
				            return -1;  
				           } 
					}
					 
				 });
		}else{
			 Collections.sort(list_Data, new Comparator<Map.Entry<Map<String,Object>, Double>>()  {
					public int compare(Entry<Map<String,Object>, Double> o1, Entry<Map<String,Object>, Double> o2) {
						if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())<0){  
				            return 1;  
				           }else{  
				            return -1;  
				           } 
					}
					 
				 });
		}
		 for(Map.Entry<Map<String,Object>, Double> map_data : list_Data){
			 returnFarmList.add(map_data.getKey()) ;
		 }
		 System.out.println("===========================================");
		 for(Map<String,Object> map : returnMechanismList){
//			 Integer distance = MapUtil.getDistance(member, recruitment.getFarm());
			 Double distance = Double.valueOf(map.get(data).toString());
			 System.out.println("排序后:"+map.get("doctorName")+",的值："+distance);
		 }
		 
		 return returnFarmList;
	
	}

}
