package c.b.s.common.cache.domain;

import c.b.s.common.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by guiqingqing on 2018/4/21.
 */
public class RedisLock implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);
    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;
    private volatile boolean locked = false;
    private String lockKey;
    /**
     * 获取锁超时时间
     * */
    private int timeoutMsecs = 10 * 1000;
    /**
     * 锁超时时间
     * */
    private int expireMsecs = 60 * 60 * 1000;

    public RedisLock(String lockKey) {
        this.lockKey = lockKey + "_lock";
    }

    public RedisLock(String lockKey, int timeoutMsecs) {
        this(lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    public RedisLock(String lockKey, int timeoutMsecs, int expireMsecs) {
        this(lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    private String get(final String key) {
        Object obj = null;
        try {
            StringRedisTemplate redisTemplate = SpringContextUtil.getBean("redisTemplate", StringRedisTemplate.class);
            obj = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            LOGGER.error(String.format("Get redis error, key : %s", key), e);
        }
        return obj != null ? obj.toString() : null;
    }

    private boolean setNX(final String key, final String value) {
        Object obj = null;
        try {
            StringRedisTemplate redisTemplate = SpringContextUtil.getBean("redisTemplate", StringRedisTemplate.class);
            obj = redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            LOGGER.error(String.format("SetNX redis error, key : %s, value : %s", key, value), e);
        }
        return obj != null ? (Boolean) obj : false;
    }

    private String getSet(final String key, final String value) {
        Object obj = null;
        try {
            StringRedisTemplate redisTemplate = SpringContextUtil.getBean("redisTemplate", StringRedisTemplate.class);
            obj = redisTemplate.opsForValue().getAndSet(key, value);
        } catch (Exception e) {
            LOGGER.error(String.format("GetSet redis error, key : %s", key), e);
        }
        return obj != null ? (String) obj : null;
    }

    public synchronized boolean lock() throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires);
            if (this.setNX(lockKey, expiresStr)) {
                locked = true;
                return true;
            }

            String currentValueStr = this.get(lockKey);
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                String oldValueStr = this.getSet(lockKey, expiresStr);
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    locked = true;
                    return true;
                }
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
            Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
        }
        return false;
    }

    public synchronized void unlock() {
        if (locked) {
            StringRedisTemplate redisTemplate = SpringContextUtil.getBean("redisTemplate", StringRedisTemplate.class);
            redisTemplate.delete(lockKey);
            locked = false;
        }
    }

    @Override
    public synchronized void close() {
        unlock();
    }
}