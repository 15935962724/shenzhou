package net.shenzhou.test;

import java.util.ArrayList;
import java.util.List;

import net.shenzhou.entity.Area;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class GetCacheTest {
	
//	  public static JedisPool jedisPool; // 池化管理jedis链接池

//	  public static RedisTemplate redisTemplate; // 池化管理jedis链接池
	  
//	    static {

	        //读取相关的配置
	      /*  ResourceBundle resourceBundle = ResourceBundle.getBundle("redis");
	        int maxActive = Integer.parseInt(resourceBundle.getString("redis.pool.maxActive"));
	        int maxIdle = Integer.parseInt(resourceBundle.getString("redis.pool.maxIdle"));
	        int maxWait = Integer.parseInt(resourceBundle.getString("redis.pool.maxWait"));

	        String ip = resourceBundle.getString("redis.ip");
	        int port = Integer.parseInt(resourceBundle.getString("redis.port"));*/
	    	
//	        int maxActive = 1024;
//	        int maxIdle = 200;
//	        int maxWait = 10000;
//
//	        String ip = "59.110.153.102";
//	        int port = 6375;
//	        String pass = "111111";//密码
//	    	
//
//	        JedisPoolConfig config = new JedisPoolConfig();
//	        //设置最大连接数
//	        config.setMaxTotal(maxActive);
//	        //设置最大空闲数
//	        config.setMaxIdle(maxIdle);
//	        //设置超时时间
//	        config.setMaxWaitMillis(maxWait);
//	        
//	        //初始化连接池
////	        jedisPool = new JedisPool(config, ip, port);
//	        
//	        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
//	        connectionFactory.setPoolConfig(config);
//	        connectionFactory.setHostName(ip);
//	        connectionFactory.setPort(port);
//	        connectionFactory.setTimeout(10000);
//	        connectionFactory.setUsePool(true);
//	        connectionFactory.setPassword(pass);
//	        redisTemplate.setConnectionFactory(connectionFactory);
	        
//	    }

	
	public static void main(String[] args) {

//		 RedisTemplate redisTemplate = null ;
//		RedisTemplate redisTemplate = new RedisTemplate(); // 池化管理jedis链接池
		JedisPool jedisPool; // 池化管理jedis链接池
	    int maxActive = 1024;
        int maxIdle = 200;
        int maxWait = 10000;

        String ip = "59.110.153.102";
        int port = 6375;
        String pass = "111111";//密码
    	

        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数
        config.setMaxTotal(maxActive);
        //设置最大空闲数
        config.setMaxIdle(maxIdle);
        //设置超时时间
        config.setMaxWaitMillis(maxWait);
        
        //初始化连接池
        jedisPool = new JedisPool(config, ip, port);
        
//        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
//        connectionFactory.setPoolConfig(config);
//        connectionFactory.setHostName(ip);
//        connectionFactory.setPort(port);
//        connectionFactory.setTimeout(10000);
//        connectionFactory.setUsePool(true);
//        connectionFactory.setPassword(pass);
//        redisTemplate.setConnectionFactory(connectionFactory);
		
        List<Area> areas = new ArrayList<Area>();
        
        Area area1 = new Area();
        area1.setFullName("北京市");
        area1.setName("北京市");
        area1.setTreePath("1");
        areas.add(area1);
        
        Area area2 = new Area();
        area2.setFullName("天津市");
        area2.setName("天津市");
        area2.setTreePath("2");
        areas.add(area2);
        
        
//       ListOperations<String, Area> listOperation = redisTemplate.opsForList();
//  	  if(null != areas)
//  	  {
//  	   int size = areas.size();
//  	   System.out.println("总共有"+size+"条记录");
//  	   for(int i = 0; i < size ; i ++)
//  	   {
//  	     
//  	    listOperation.rightPush("areas",areas.get(i));
//  	   }
//  	  }
		
//		 List<Area> dataList = new ArrayList<Area>();
////		  ListOperations<String,Area> listOperation = redisTemplate.opsForList();
//		  Long size = listOperation.size("areas");
//		   System.out.println("1111");
//		  for(int i = 0 ; i < size ; i ++)
//		  {
//		   dataList.add((Area) listOperation.leftPop("areas"));
//		  }
//		   
//		  System.out.println(dataList.size());
//		  for (Area area : dataList) {
//			System.out.println(area.getFullName());
//		}
		 
	}
	
	
}
