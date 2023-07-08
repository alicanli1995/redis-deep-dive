package com.deep.dive.session.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

@Configuration
@EnableRedisHttpSession
public class RedisConfiguration {

    @Bean
    public RedisOperations<Object, Object> redisOperations(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisOperationsSessionRepository sessionRepository(RedisOperations<Object, Object> redisOperations) {
        return new RedisOperationsSessionRepository(redisOperations);
    }

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
