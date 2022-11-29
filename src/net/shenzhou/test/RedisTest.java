
package net.shenzhou.test;

import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {
	
		public static RedisTemplate<String, Object> getRedisTemplate(){
	    	    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
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
//		        jedisPool = new JedisPool(config, ip, port);

		        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		        connectionFactory.setPoolConfig(config);
		        connectionFactory.setHostName(ip);
		        connectionFactory.setPort(port);
		        connectionFactory.setTimeout(10000);
		        connectionFactory.setUsePool(true);
//		        connectionFactory.setPassword(pass);
		        redisTemplate.setConnectionFactory(connectionFactory);
		        RedisCacheManager redisCacheManager =  new RedisCacheManager(redisTemplate);
		        redisCacheManager.setDefaultExpiration(0);
		        return redisTemplate;
		}
	    
	 public static void setCacheObject(String key,Object value)
	 {
		  System.out.println(">>>>>");
		  RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
		  ValueOperations<String, Object>  operation =  redisTemplate.opsForValue(); 
		  operation.set(key,value);
	 }
	
	 public static Object getCacheObject(String key)
	 {
		  RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
		  ValueOperations<String, Object> operation =  redisTemplate.opsForValue(); 
		  return operation.get(key);
	 }
	
	public static void main(String[] args) {
		
		setCacheObject("name", "王双瑞");  
        String name = (String) getCacheObject("name");
        System.out.println(name);
		
        
	}
	
	
}
