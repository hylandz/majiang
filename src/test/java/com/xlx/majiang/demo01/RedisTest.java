package com.xlx.majiang.demo01;

import com.xlx.majiang.system.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * redis
 *
 * @author xielx on 2019/8/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

	@Resource
	private  RedisService redisService;

	@Test
	public void testString(){

		Long ttl = redisService.getStringTTL("code");

		String str = redisService.getStringValue("code");
		System.out.println("key=" + str + ",ttl=" + ttl);
	}



	@Test
	public void testRedisPing(){
		redisService.setStringEx("code","1236",60L);
		String key = redisService.getStringValue("code");
		Long  ttl = redisService.getStringTTL("code");
		System.out.println("key=" + key + ",ttl=" + ttl);

	}

}
