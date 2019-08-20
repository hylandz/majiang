package com.xlx.majiang.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redis
 *
 * @author xielx on 2019/8/14
 */
@Service
public class RedisService {

	@Resource
	private  StringRedisTemplate stringRedisTemplate;


	/**
	 * 设置带有时效的字符串
	 * @param k key
	 * @param v value
	 * @param seconds 有效期:秒
	 */
	public  void setStringEx(String k,String v,Long seconds){
		ValueOperations<String, String> operations =  stringRedisTemplate.opsForValue();
		operations.set(k,v,seconds, TimeUnit.SECONDS);
	}

	/**
	 * 通过key值获取value值
	 * @param k key
	 * @return str
	 */
	public  String getStringValue(String k){
		ValueOperations<String, String> operations =  stringRedisTemplate.opsForValue();
		return operations.get(k);
	}

	/**
	 * 获取字符串的有效时长
	 * @param str key
	 * @return long
	 */
	public  Long getStringTTL(String str){
		return stringRedisTemplate.getExpire(str);
	}

}
