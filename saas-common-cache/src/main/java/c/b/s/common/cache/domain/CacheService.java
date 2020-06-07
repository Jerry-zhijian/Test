package c.b.s.common.cache.domain;

import c.b.s.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> clazz) {
        try {
            if (String.class.isAssignableFrom(clazz)) {
                return (T) redisTemplate.opsForValue().get(key);
            }
            return JsonUtil.convertToObject(redisTemplate.opsForValue().get(key), clazz);
        } catch (Exception e) {
            LOGGER.warn(String.format("redis get key:%s 值异常", key), e);
        }
        return null;

    }

    public Boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }

    public boolean set(String key, Object value, int seconds) {
        try {
            if (String.class.isAssignableFrom(value.getClass())) {
                redisTemplate.opsForValue().set(key, (String) value, seconds, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, JsonUtil.convertToJson(value), seconds, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            LOGGER.warn(String.format("redis set key:%s value:%s 值异常", key, value), e);
        }
        return false;
    }

    public boolean del(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            LOGGER.warn(String.format("redis del key:%s 值异常", key), e);
        }
        return false;
    }

    /**
     * @param lockIndentify 锁对象标识
     * @param timeout       锁超时时间
     * @param unit          锁超时时间单位,时、分、秒
     * @return
     */
    public boolean lock(String lockIndentify, long timeout, TimeUnit unit) {
        try {
            long millTimeOut = unit.toMillis(timeout);
            long value = System.currentTimeMillis() + millTimeOut + 1;
            boolean acquired = redisTemplate.opsForValue().setIfAbsent(lockIndentify, String.valueOf(value));
            // SETNX成功，则成功获取一个锁
            if (acquired) {
                long expireTime = timeout * 2; //超过2倍超时时间锁自动删除

                redisTemplate.expire(lockIndentify, expireTime, unit);
                return true;
            } else { // SETNX失败，说明锁仍然被其他对象保持，检查其是否已经超时
                synchronized (lockIndentify) {
                    String oldTimeValueStr = redisTemplate.opsForValue().get(lockIndentify);
                    if (oldTimeValueStr != null && Long.parseLong(oldTimeValueStr) < System.currentTimeMillis()) { // 超时检查
                        String getValue = (String) redisTemplate.opsForValue().getAndSet(lockIndentify, String.valueOf(value));
                        // 检查超时锁是否是原来的超时锁，是则返回获取锁
                        if (getValue != null && getValue.equals(oldTimeValueStr)) { //还没有被其它线程抢先
                            long expireTime = timeout * 2;//超过2倍超时时间锁自动删除
                            redisTemplate.expire(lockIndentify, expireTime, unit);
                            LOGGER.info(String.format("原锁超时后，获取锁[%s]成功,并设置过期秒数为[%s]", lockIndentify, expireTime));
                            return true;
                        } else {
                            LOGGER.warn(String.format("lockIndentify:%s 没有获得锁", lockIndentify));
                            return false;
                        }
                    } else {
                        LOGGER.warn(String.format("lockIndentify:%s 没有获得锁", lockIndentify));
                        return false;
                    }
                }
            }
        } catch (Throwable e) {
            LOGGER.warn(String.format("redis 获得lockIndentify:%s lock 异常", lockIndentify), e);
        }
        return true;
    }

    /**
     * 释放锁
     *
     * @param lock
     */
    public void unlock(String lock) {
        try {
            long current = System.currentTimeMillis();
            // 避免删除非自己获取得到的锁
            String value = redisTemplate.opsForValue().get(lock);
            if (value != null && current < Long.parseLong(value)) {
                redisTemplate.delete(lock);
            }
        } catch (Throwable e) {
            LOGGER.warn(String.format("lockIndentify: %s unlock 异常", lock), e);
        }

    }

    public boolean increment(String key, Object value) {
        try {
            if (Long.class.isAssignableFrom(value.getClass())) {
                redisTemplate.opsForValue().increment(key,(Long) value);
            } else {
                redisTemplate.opsForValue().increment(key,(Double) value);
            }
            return true;
        } catch (Exception e) {
            LOGGER.warn(String.format("redis increment key:%s value:%s 值异常", key, value), e);
        }
        return false;
    }

    public boolean expire(String key, long timeout, TimeUnit unit) {
        try {
            redisTemplate.expire(key, timeout, unit);
            return true;
        } catch (Exception e) {
            LOGGER.warn(String.format("redis expire key:%s 值异常", key), e);
        }
        return false;
    }


}