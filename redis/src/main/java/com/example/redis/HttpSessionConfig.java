package com.example.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;


/**
 * Created by Java Developer Zone on 13-11-2017.
 */
@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig extends AbstractHttpSessionApplicationInitializer {

    @Bean
    public JedisConnectionFactory connectionFactory() {     // It will create filter for Redis store which will override default Tomcat Session
        return new JedisConnectionFactory();
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
      RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
      redisTemplate.setConnectionFactory(connectionFactory());
      redisTemplate.setExposeConnection(true);
      redisTemplate.setEnableTransactionSupport(true);
      // No serializer required all serialization done during impl
      redisTemplate.setKeySerializer(stringRedisSerializer());
      //`redisTemplate.setHashKeySerializer(stringRedisSerializer());
     // redisTemplate.setHashValueSerializer(new GenericSnappyRedisSerializer());
      redisTemplate.afterPropertiesSet();
      return redisTemplate;
    }

@Bean
public StringRedisSerializer stringRedisSerializer() {
  StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
  return stringRedisSerializer;
}
    
}
