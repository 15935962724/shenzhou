package net.shenzhou.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.DoctorMechanismRelation.Audit;
import net.shenzhou.entity.Mechanism;

public class DoctorUtil {

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
	public static String getJson(List<Doctor> screenDoctors , Integer pageNumber,Integer pagesize){
		List<Doctor> doctors = screenDoctors.subList((pageNumber-1)*pagesize, pagesize); 
		return JsonUtils.toJson(doctors);
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
	public static List<Doctor> getOrderByDistance(Map <String ,Object> map){
		
		List<Doctor> doctors  =  new ArrayList<Doctor>();
		
		/** 经度 */
		 double longitude = Double.valueOf(map.get("longitude").toString());
		
		/** 纬度 */
		 double latitude = Double.valueOf(map.get("latitude").toString());
		
		boolean isDesc = (Boolean) map.get("isDesc");//false 升序  true 降序
		
		doctors = (List<Doctor>) map.get("doctors");
		
		Map<Doctor,Integer> doctorsSort = new HashMap<Doctor,Integer>();
		
		List<Doctor> returnDoctorist = new ArrayList<Doctor>();
		
		
		for(Doctor doctor : doctors){
			System.out.println(doctor.getId());
			List<Mechanism> mechanisms = new ArrayList<Mechanism>();
			for(DoctorMechanismRelation dmr : doctor.getDoctorMechanismRelations(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed)){
				mechanisms.add(dmr.getMechanism());
			}
			Map<String,Object> mechanisms_map = new HashMap<String, Object>();
			mechanisms_map.put("longitude", longitude);
			mechanisms_map.put("latitude", latitude);
			mechanisms_map.put("mechanisms", mechanisms);
			mechanisms_map.put("isDesc", false);
			List<Mechanism> list_mechanism = MechanismUtil.getOrderByDistance(mechanisms_map);
			
//			Integer distance = MapUtil.getDistance(member, recruitment.getFarm());
			Integer distance = MapUtil.GetDistance(latitude, longitude, list_mechanism.get(0).getLatitude(),list_mechanism.get(0).getLongitude());
			if(list_mechanism.get(0)!=null){
				doctorsSort.put(doctor, distance);
				System.out.println("排序前:"+doctor.getName()+",距离："+distance);
			}
		}
		List<Map.Entry<Doctor, Integer>> list_Data = new ArrayList<Map.Entry<Doctor, Integer>>(doctorsSort.entrySet());  
		if(isDesc){
			 Collections.sort(list_Data, new Comparator<Map.Entry<Doctor, Integer>>()  {
					public int compare(Entry<Doctor, Integer> o1, Entry<Doctor, Integer> o2) {
						if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0){  
				            return 1;  
				           }else{  
				            return -1;  
				           } 
					}
					 
				 });
		}else{
			 Collections.sort(list_Data, new Comparator<Map.Entry<Doctor, Integer>>()  {
					public int compare(Entry<Doctor, Integer> o1, Entry<Doctor, Integer> o2) {
						if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())<0){  
				            return 1;  
				           }else{  
				            return -1;  
				           } 
					}
					 
				 });
		}
		 for(Map.Entry<Doctor, Integer> map_data : list_Data){
			 returnDoctorist.add(map_data.getKey()) ;
		 }
		 System.out.println("===========================================");
		 for(Doctor doctor : returnDoctorist){
			 List<Mechanism> mechanisms = new ArrayList<Mechanism>();
				for(DoctorMechanismRelation dmr : doctor.getDoctorMechanismRelations(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed)){
						mechanisms.add(dmr.getMechanism());
				}
				Map<String,Object> mechanisms_map = new HashMap<String, Object>();
				mechanisms_map.put("longitude", longitude);
				mechanisms_map.put("latitude", latitude);
				mechanisms_map.put("mechanisms", mechanisms);
				mechanisms_map.put("isDesc", false);
				List<Mechanism> list_mechanism = MechanismUtil.getOrderByDistance(mechanisms_map);
			// Integer distance = MapUtil.getDistance(member, recruitment.getFarm());
			 Integer distance = MapUtil.GetDistance(latitude, longitude, list_mechanism.get(0).getLatitude(),list_mechanism.get(0).getLongitude());
			 System.out.println("排序后:"+doctor.getName()+",距离："+distance);
		 }
		 
		 return returnDoctorist;
	
	}

	/**备用排序方法
	* @Title: getOrderByDistance 
	* @Description: TODO(距离排序 ) 
	* @param @param screenRecrutiments
	* @param @param map
	* @param @return 设定文件 
	* @return List<Project>    返回类型 
	* @throws
	 *//*
	public static List<Doctor> getOrderByDistance(Map <String ,Object> map){
		
		List<Doctor> doctors  =  new ArrayList<Doctor>();
		
		*//** 经度 *//*
		 double longitude = Double.valueOf(map.get("longitude").toString());
		
		*//** 纬度 *//*
		 double latitude = Double.valueOf(map.get("latitude").toString());
		
		boolean isDesc = (Boolean) map.get("isDesc");//false 升序  true 降序
		
		doctors = (List<Doctor>) map.get("doctors");
		
		Map<Doctor,Integer> doctorsSort = new HashMap<Doctor,Integer>();
		
		List<Doctor> returnDoctorist = new ArrayList<Doctor>();
		
		
		for(Doctor doctor : doctors){
			doctor.getDoctorMechanismRelation();
//			Integer distance = MapUtil.getDistance(member, recruitment.getFarm());
			Integer distance = MapUtil.GetDistance(latitude, longitude, doctor.getMechanism().getLatitude(),doctor.getMechanism().getLongitude());
			if(doctor.getMechanism()!=null){
				doctorsSort.put(doctor, distance);
				System.out.println("排序前:"+doctor.getName()+",距离："+distance);
			}
		}
		List<Map.Entry<Doctor, Integer>> list_Data = new ArrayList<Map.Entry<Doctor, Integer>>(doctorsSort.entrySet());  
		if(isDesc){
			 Collections.sort(list_Data, new Comparator<Map.Entry<Doctor, Integer>>()  {
					public int compare(Entry<Doctor, Integer> o1, Entry<Doctor, Integer> o2) {
						if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0){  
				            return 1;  
				           }else{  
				            return -1;  
				           } 
					}
					 
				 });
		}else{
			 Collections.sort(list_Data, new Comparator<Map.Entry<Doctor, Integer>>()  {
					public int compare(Entry<Doctor, Integer> o1, Entry<Doctor, Integer> o2) {
						if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())<0){  
				            return 1;  
				           }else{  
				            return -1;  
				           } 
					}
					 
				 });
		}
		 for(Map.Entry<Doctor, Integer> map_data : list_Data){
			 returnDoctorist.add(map_data.getKey()) ;
		 }
		 System.out.println("===========================================");
		 for(Doctor doctor : returnDoctorist){
//			 Integer distance = MapUtil.getDistance(member, recruitment.getFarm());
			 Integer distance = MapUtil.GetDistance(latitude, longitude, doctor.getMechanism().getLatitude(),doctor.getMechanism().getLongitude());
			 System.out.println("排序后:"+doctor.getName()+",距离："+distance);
		 }
		 
		 return returnDoctorist;
	
	}

	*/
	
}
