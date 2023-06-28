package com.deep.dive.distributed.lock.service;


import com.deep.dive.distributed.lock.model.MoneyWalletDTO;
import com.deep.dive.distributed.lock.service.locker.DistributedLocker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class RedisListCache {

    private ListOperations<String, Object> listOps;
    private final DistributedLocker distributedLocker;
    private final RedisValueCache valueCache;

    public RedisListCache(final RedisTemplate<String, Object> redisTemplate, DistributedLocker distributedLocker,
                          RedisValueCache valueCache) {
        listOps = redisTemplate.opsForList();
        this.distributedLocker = distributedLocker;
        this.valueCache = valueCache;
    }


    // first get cached element from the MoneyWalletDTO id field
    // then get the cached element locked.
    // then the locked element add the balance.
    // then the locked element set the balance.
    // then the locked element unlock with automatic unlock in same thread, after the finally block.
    public void addBalance(final String key, final BigDecimal amount) {
        MoneyWalletDTO cachedWalletDTO = (MoneyWalletDTO) valueCache.getCachedValue(key);
        distributedLocker.lock(cachedWalletDTO.getId(), 5, 6, () -> {
            cachedWalletDTO.setBalance(cachedWalletDTO.getBalance().add(amount));
            valueCache.cache(cachedWalletDTO.getId(), cachedWalletDTO);
            return null;
        });

    }

    // first get cached element from the MoneyWalletDTO id field
    // then get the cached element locked.
    // then the locked element subtract the balance.
    // then the locked element set the balance.
    // then the locked element unlock with automatic unlock in same thread, after the finally block.
    public void subtractBalance(String key, BigDecimal amount) {
        MoneyWalletDTO cachedWalletDTO = (MoneyWalletDTO) valueCache.getCachedValue(key);
        distributedLocker.lock(cachedWalletDTO.getId(), 5, 6, () -> {
            cachedWalletDTO.setBalance(cachedWalletDTO.getBalance().subtract(amount));
            valueCache.cache(cachedWalletDTO.getId(), cachedWalletDTO);
            return null;
        });
    }
}