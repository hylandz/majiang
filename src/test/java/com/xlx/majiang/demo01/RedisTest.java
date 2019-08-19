package com.xlx.majiang.demo01;

import com.xlx.majiang.common.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
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
	private static StringRedisTemplate stringRedisTemplate;

	@Test
	public void testPing(){
	}



	@Test
	public void testRedisPing(){
		RedisUtil.setStringEx("code","1236",60L);
		String key = RedisUtil.getStringValue("code");
		Long  ttl = RedisUtil.getStringTTL("code");
		System.out.println("key=" + key + ",ttl=" + ttl);

	}

}
