package com.dive.rate.limit.service.impl;

import com.dive.rate.limit.service.RateLimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimitServiceImpl implements RateLimitService {

    private final RedisTemplate<String, String> redisTemplate;

    public boolean isAllowed(String key, int maxRequests, int durationInSeconds) {
        String redisKey = "rate-limiter:" + key;
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        if (Boolean.FALSE.equals(redisTemplate.hasKey(redisKey))) {
            opsForValue.set(redisKey, "1", durationInSeconds, TimeUnit.SECONDS);
            return true;
        }

        Long count = opsForValue.increment(redisKey, 1);

        return !Objects.isNull(count ) && count <= maxRequests;
    }
}
