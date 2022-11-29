/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.annotation.Resource;

import net.shenzhou.service.RedisCacheService;
import net.shenzhou.util.SerializeUtil;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service("redisCacheServiceImpl")
public class RedisCacheServiceImpl implements RedisCacheService  {

	private static final Logger logger = Logger.getLogger(RedisCacheServiceImpl.class.getName());
    static final byte[] ngHisByte = SerializeUtil.serialize("WANDA_NGHIS");
	
	 @SuppressWarnings("rawtypes")
	 @Resource(name = "redisTemplate")
	 public RedisTemplate redisTemplate;
	 
	 /**
	  * 缓存基本的对象，Integer、String、实体类等
	  * @param key 缓存的键值
	  * @param value 缓存的值
	  * @return  缓存的对象
	  */
	 public <T> ValueOperations<String,T> setCacheObject(String key,T value)
	 {
	   
	  ValueOperations<String,T> operation = redisTemplate.opsForValue(); 
	  operation.set(key,value);
	  return operation;
	 }
	 
	 /**
	  * 缓存基本的对象，Integer、String、实体类等
	  * @param key 缓存的键值
	  * @param value 缓存的值
	  * @return  缓存的对象
	  */
	 @Override
	public <T> ValueOperations<String, T> setCacheObject(String key,T value,
			Long time) {
		  ValueOperations<String,T> operation = redisTemplate.opsForValue(); 
		  operation.set(key,value);
		  redisTemplate.expire(key, time, TimeUnit.SECONDS);
		  return operation;
		  
	}
	 
	  
	 /**
	  * 获得缓存的基本对象。
	  * @param key  缓存键值
	  * @param operation
	  * @return   缓存键值对应的数据
	  */
	 public <T> T getCacheObject(String key/*,ValueOperations<String,T> operation*/)
	 {
	  ValueOperations<String,T> operation = redisTemplate.opsForValue(); 
	  return operation.get(key);
	 }
	  
	 /**
	  * 缓存List数据
	  * @param key  缓存的键值
	  * @param dataList 待缓存的List数据
	  * @return   缓存的对象
	  */
	 @Override
	 public <T> ListOperations<String, T> setCacheList(String key,List<T> dataList)
	 {
	  System.out.println("开始缓存redis数据");
	  ListOperations listOperation = redisTemplate.opsForList();
	  if(null != dataList)
	  {
	   int size = dataList.size();
	   for(int i = 0; i < size ; i ++)
	   {
	     
	    listOperation.rightPush(key,dataList.get(i));
	   }
	  }
	  System.out.println("缓存redis数据完毕");
	  return listOperation;
	 }
	  
	 /**
	  * 获得缓存的list对象
	  * @param key 缓存的键值
	  * @return  缓存键值对应的数据
	  */
	 public <T> List<T> getCacheList(String key)
	 {
	  List<T> dataList = new ArrayList<T>();
	  ListOperations<String,T> listOperation = redisTemplate.opsForList();
	  Long size = listOperation.size(key);
	   
	  for(int i = 0 ; i < size ; i ++)
	  {
	   dataList.add((T) listOperation.leftPop(key));
	  }
	   
	  return dataList;
	 }
	  
	 /**
	  * 缓存Set
	  * @param key  缓存键值
	  * @param dataSet 缓存的数据
	  * @return   缓存数据的对象
	  */
	 public <T> BoundSetOperations<String,T> setCacheSet(String key,Set<T> dataSet)
	 {
	  BoundSetOperations<String,T> setOperation = redisTemplate.boundSetOps(key); 
	  /*T[] t = (T[]) dataSet.toArray();
	    setOperation.add(t);*/
	   
	   
	  Iterator<T> it = dataSet.iterator();
	  while(it.hasNext())
	  {
	   setOperation.add(it.next());
	  }
	   
	  return setOperation;
	 }
	  
	 /**
	  * 获得缓存的set
	 * @param <T>
	  * @param key
	  * @param operation
	  * @return
	  */
	 public <T> Set<T> getCacheSet(String key)
	 {
	  Set<T> dataSet = new HashSet<T>();
	  BoundSetOperations<String,T> operation = redisTemplate.boundSetOps(key); 
	  Long size = operation.size();
	  for(int i = 0 ; i < size ; i++)
	  {
	   dataSet.add(operation.pop());
	  }
	  return dataSet;
	 }
	  
	 /**
	  * 缓存Map
	  * @param key
	  * @param dataMap
	  * @return
	  */
	 public <T> HashOperations<String,String,T> setCacheMap(String key,Map<String,T> dataMap)
	 {
	   
	  HashOperations hashOperations = redisTemplate.opsForHash();
	  if(null != dataMap)
	  {
	    
	   for (Map.Entry<String, T> entry : dataMap.entrySet()) { 
	      
	    /*System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); */
	    hashOperations.put(key,entry.getKey(),entry.getValue());
	   } 
	    
	  }
	   
	  return hashOperations;
	 }
	  
	 /**
	  * 获得缓存的Map
	  * @param key
	  * @param hashOperation
	  * @return
	  */
	 public <T> Map<String,T> getCacheMap(String key/*,HashOperations<String,String,T> hashOperation*/)
	 {
	  Map<String, T> map = redisTemplate.opsForHash().entries(key);
	  /*Map<String, T> map = hashOperation.entries(key);*/
	  return map;
	 }
	  
	 /**
	  * 缓存Map
	  * @param key
	  * @param dataMap
	  * @return
	  */
	 public <T> HashOperations<String,Integer,T> setCacheIntegerMap(String key,Map<Integer,T> dataMap)
	 {
	  HashOperations hashOperations = redisTemplate.opsForHash();
	  if(null != dataMap)
	  {
	    
	   for (Map.Entry<Integer, T> entry : dataMap.entrySet()) { 
	      
	    /*System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); */
	    hashOperations.put(key,entry.getKey(),entry.getValue());
	   } 
	    
	  }
	   
	  return hashOperations;
	 }
	  
	 /**
	  * 获得缓存的Map
	  * @param key
	  * @param hashOperation
	  * @return
	  */
	 public <T> Map<Integer,T> getCacheIntegerMap(String key/*,HashOperations<String,String,T> hashOperation*/)
	 {
	  Map<Integer, T> map = redisTemplate.opsForHash().entries(key);
	  /*Map<String, T> map = hashOperation.entries(key);*/
	  return map;
	 }

	 /**
	  * 判断缓存的key是否存在
	  */
	@Override
	public Boolean hasKey(String key) {
		// TODO Auto-generated method stub
		return redisTemplate.hasKey(key);
	}

}