/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public interface RedisCacheService{
	
	
	  
	 /**
	  * 缓存基本的对象，Integer、String、实体类等
	  * @param key 缓存的键值
	  * @param value 缓存的值
	  * @return  缓存的对象
	  */
	 public <T> ValueOperations<String,T> setCacheObject(String key,T value);
	
	 
	 /**
	  * 缓存基本的对象，Integer、String、实体类等
	  * @param key 缓存的键值
	  * @param value 缓存的值
	  * @param time 有效期
	  * @return  缓存的对象
	  */
	 public <T> ValueOperations<String,T> setCacheObject(String key,T value,Long time);
	 
	  
	 /**
	  * 获得缓存的基本对象。
	  * @param key  缓存键值
	  * @param operation
	  * @return   缓存键值对应的数据
	  */
	 public <T> T getCacheObject(String key);
	  
	 /**
	  * 缓存List数据
	  * @param key  缓存的键值
	  * @param dataList 待缓存的List数据
	  * @return   缓存的对象
	  */
	 public <T> ListOperations<String, T> setCacheList(String key,List<T> dataList);
	  
	 /**
	  * 获得缓存的list对象
	  * @param key 缓存的键值
	  * @return  缓存键值对应的数据
	  */
	 public <T> List<T> getCacheList(String key);
	 
	  
	 /**
	  * 缓存Set
	  * @param key  缓存键值
	  * @param dataSet 缓存的数据
	  * @return   缓存数据的对象
	  */
	 public <T> BoundSetOperations<String,T> setCacheSet(String key,Set<T> dataSet);
	  
	 /**
	  * 获得缓存的set
	  * @param key
	  * @param operation
	  * @return
	  */
	 public <T> Set<T> getCacheSet(String key);
	  
	 /**
	  * 缓存Map
	  * @param key
	  * @param dataMap
	  * @return
	  */
	 public <T> HashOperations<String,String,T> setCacheMap(String key,Map<String,T> dataMap);
	  
	 /**
	  * 获得缓存的Map
	  * @param key
	  * @param hashOperation
	  * @return
	  */
	 public <T> Map<String,T> getCacheMap(String key);
	  
	  
	  
	 /**
	  * 缓存Map
	  * @param key
	  * @param dataMap
	  * @return
	  */
	 public <T> HashOperations<String,Integer,T> setCacheIntegerMap(String key,Map<Integer,T> dataMap);
	  
	 /**
	  * 获得缓存的Map
	  * @param key
	  * @param hashOperation
	  * @return
	  */
	 public <T> Map<Integer,T> getCacheIntegerMap(String key);
	 
	 
	 /**
	  * 判断key是否存在
	  * @return
	  */
	 public Boolean hasKey(String key);
	 
	
}