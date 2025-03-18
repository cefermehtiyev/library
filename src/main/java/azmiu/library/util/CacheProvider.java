package azmiu.library.util;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static azmiu.library.model.constants.CacheConstants.CACHE_PREFIX;
import static java.util.concurrent.TimeUnit.MINUTES;

@Component
@RequiredArgsConstructor
public class CacheProvider {
    private final RedissonClient redissonClient;

    public <T> T getBucket(String cacheKey) {
        RBucket<T> bucket = getRBucket(cacheKey);
        return bucket == null ? null : bucket.get();
    }

    public <T> void updateToCache(T value, String key, Long expireTime) {
        var bucket = getRBucket(key);
        bucket.set(value, Duration.ofMinutes(expireTime));

    }

    private <T> RBucket<T> getRBucket(String cacheKey) {
        var key = CACHE_PREFIX.formatted(cacheKey);
        return redissonClient.getBucket(key);
    }
}