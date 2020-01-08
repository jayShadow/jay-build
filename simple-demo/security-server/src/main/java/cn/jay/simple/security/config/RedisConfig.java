package cn.jay.simple.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: Jay
 * @Date: 2019/12/31 17:04
 */
@Slf4j
@Configuration
public class RedisConfig {

//    /**
//     * 缓存管理
//     * @param connectionFactory 连接工厂
//     * @param properties 缓存配置参数
//     * @return redis 缓存管理器
//     */
//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, CacheProperties properties) {
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(properties.getRedis().getTimeToLive())
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer())).disableCachingNullValues();
//        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory).cacheDefaults(config).transactionAware().build();
//        log.info("自定义RedisCacheManager加载完成");
//        return redisCacheManager;
//    }

    /**
     * redis 操作工具类
     *
     * @param redisConnectionFactory redis 连接工厂
     * @return redis 操作工具类
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        log.info("自定义RedisTemplate加载完成");
        return redisTemplate;
    }


    /**
     * key 序列化规则
     *
     * @return key 序列化规则
     */
//    @Nonnull
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    /**
     * value 序列化规则
     *
     * @return value 序列化规则
     */
//    @Nonnull
    private RedisSerializer<Object> valueSerializer() {
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
//        objectMapper.registerModule(javaTimeModule);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        return jackson2JsonRedisSerializer;
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        return jdkSerializationRedisSerializer;
    }

}
