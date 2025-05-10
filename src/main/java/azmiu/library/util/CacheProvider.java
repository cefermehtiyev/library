package azmiu.library.util;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

import static azmiu.library.model.constants.CacheConstants.CACHE_PREFIX;
import static java.util.concurrent.TimeUnit.MINUTES;

@Component
@RequiredArgsConstructor
public class CacheProvider {
    private final RedissonClient redissonClient;




    public <T> T getBucket(String cacheKey) {
        RBucket<T> bucket = redissonClient.getBucket(cacheKey);
        return bucket == null ? null : bucket.get();
    }

    public <T> void saveToCache(String key, T value, Long expireTime, TemporalUnit temporalUnit) {
        var bucket = redissonClient.getBucket(key);
        bucket.set(value);
        bucket.expire(Duration.of(expireTime, temporalUnit));
    }

    public void deleteFromCache(String cacheKey) {
        RBucket<Object> bucket = redissonClient.getBucket(cacheKey);
        bucket.delete();
    }

    public void deleteAllCache() {
        RKeys keys = redissonClient.getKeys();
        Iterable<String> keyIterator = keys.getKeys();

        for (String key : keyIterator) {
            RBucket<Object> bucket = redissonClient.getBucket(key);
            bucket.delete();
        }
    }
}