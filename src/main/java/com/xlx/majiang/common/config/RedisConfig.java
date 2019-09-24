package com.xlx.majiang.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 *
 * @author xielx on 2019/8/19
 */
//@Configuration
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
		/*return new KeyGenerator() {
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
		};*/
		
		//lambda表达式
		return (obj,method,params) -> {
			StringBuilder builder = new StringBuilder();
			builder.append(obj.getClass().getName())
							.append(method.getName());
			for (Object object : params) {
				builder.append(object.toString());
			}
			
			return builder.toString();
		};
		
	}


	/**
	 * redis的缓存管理器
	 * @param redisTemplate
	 * @return
	 */
	public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate){
		return null;
	}

	/**
	 * redis的模板RedisTemplate:
	 * key值序列化/反序列化:使用RedisSerializer
	 * value值序列化/反序列化:使用默认的JDK
	 */
	public RedisTemplate<?,?> redisTemplate(RedisConnectionFactory connectionFactory){
		RedisTemplate<?,?> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);

		//使用StringRedisSerializer来序列化和反序列化redis的key值
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		//key
		redisTemplate.setKeySerializer(redisSerializer);
		redisTemplate.setHashKeySerializer(redisSerializer);

		//使用JdkSerializationRedisSerializer来序列化和反序列化redis的value值
		JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();

		//使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
		//Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		//?
		//ObjectMapper objectMapper = new ObjectMapper();
		//objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		//final类型是无法序列化
		//objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		//jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

		//value
		redisTemplate.setValueSerializer(jdkSerializer);
		redisTemplate.setHashValueSerializer(jdkSerializer);

		redisTemplate.afterPropertiesSet();
		return redisTemplate;

	}

	/**
	 * redis的连接JedisConnectionFactory
	 */


	/**
	 * redis的连接池JedisPoolConfig
	 */



}
