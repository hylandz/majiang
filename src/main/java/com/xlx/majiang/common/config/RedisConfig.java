package com.xlx.majiang.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

/**
 * redis配置
 *
 * @author xielx on 2019/8/19
 */
@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private Integer port;

	@Value("${spring.redis.timeout}")
	private Integer timeout;

	@Value("${spring.redis.password}")
	private String password;

	@Value("{spring.redis.database}")
	private Integer dataBase;

	@Value("{spring.redis.jedis.pool.max-idle}")
	private Integer maxIdle;

	@Value("{spring.redis.jedis.pool.min-idle}")
	private Integer minIdle;


	/**
	 * key生成规则
	 * @return
	 */
	public KeyGenerator keyGenerator(){
		return new KeyGenerator() {
			@Override
			public Object generate(Object o, Method method, Object... objects) {
				StringBuilder builder = new StringBuilder();
				builder.append(o.getClass().getName())
								.append(method.getName());
				for (Object object : objects) {
					builder.append(object.toString());
				}

				return builder.toString();
			}
		};
	}


	/**
	 * redis的缓存管理器
	 * @param redisTemplate
	 * @return
	 */
	public CacheManager cacheManager(RedisTemplate redisTemplate){
		return null;
	}

	/**
	 * redis的模板RedisTemplate
	 */

	/**
	 * redis的连接JedisConnectionFactory
	 */


	/**
	 * redis的连接池JedisPoolConfig
	 */



}
