package c.b.s.common.cache.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by guiqingqing on 2018/11/17.
 */
@Service
public class ResourceService {
    private static final int REDIS_LOCK_TIME_OUT_MSECS = 10 * 1000; // 分布式锁获取超时时间
    private static final int REDIS_LOCK_EXPIRE_MSECS = 60 * 1000; // 分布式锁超时时间
    private static final boolean SET_RESOURCES_FLAG = true; // 设置资源标记
    private static final int SET_RESOURCES_FLAG_EXPIRE_SECS = 60; // 资源标记的过期时间
    private static final int RESOURCES_EXPIRE_SECS = 12 * 60 * 60; // 资源的过期时间
    private static final int RESOURCE_SET_MAX_TRY_COUNT = 10; // 资源设置最大尝试次数

    @Autowired
    private CacheService cacheService;

    /**
     * 多台服务器同时向缓存中设置资源, 确保只有一台服务器设置成功
     * @param setResourcesFlag 缓存中是否已设置资源
     * @param setResourcesLock 设置资源时加锁的Key
     * @param resourcesCacheKey 资源在缓存中的Key
     * @param resources 要设置的资源
     * @return
     * @throws InterruptedException
     *
     * 判断缓存中是否存在设置资源标记
     * 如果存在设置资源标记, 则直接返回false, 表示此次不需要设置
     * 如果不存在设置资源标记, 则尝试获取分布式锁, 获取锁以后, 再判断缓存中是否存在设置资源标记
     *          (类似单例模式中的双重锁检验)
     *          public static Singleton getSingleton() {
     *              if (singleton == null) {
     *                  synchronized (xxx) {
     *                      if (singleton == null) {
     *                          singleton = new Singleton();
     *                      }
     *                  }
     *              }
     *              return singleton;
     *          }
     * 如果此时存在设置资源标记, 表示其他服务器已经设置过, 则直接返回false, 表示此次不需要设置
     * 如果此时还不存在设置资源标记, 则由当前服务器设置资源, 并设置资源标记, 防止其他服务器再次设置
     */
    public boolean setResources(String setResourcesFlag, String setResourcesLock, String resourcesCacheKey, List<? extends Object> resources) throws InterruptedException {
        // 定义尝试次数, 防止死循环
        int tryCount = 0;
        while (!cacheService.exist(setResourcesFlag)) {
            try (RedisLock redisLock = new RedisLock(setResourcesLock, REDIS_LOCK_TIME_OUT_MSECS, REDIS_LOCK_EXPIRE_MSECS)) {
                if (redisLock.lock()) {
                    if (!cacheService.exist(setResourcesFlag)) {
                        cacheService.set(setResourcesFlag, SET_RESOURCES_FLAG, SET_RESOURCES_FLAG_EXPIRE_SECS);
                        cacheService.set(resourcesCacheKey, resources, RESOURCES_EXPIRE_SECS);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            // 如果循环次数达到10还未成功, 则停止此次资源设置
            if (tryCount++ > RESOURCE_SET_MAX_TRY_COUNT) {
                break;
            }
        }
        return false;
    }

    /**
     * 多台服务器同时从缓存中获取资源, 确保不同服务器获取的资源不重复
     * @param getResourceLock 获取资源时加锁的Key
     * @param resourcesCacheKey 资源在缓存中的Key
     * @return
     * @throws InterruptedException
     *
     * 尝试获取分布式锁, 获取锁以后, 从缓存中获取资源列表
     * 如果资源列表不为空, 则取出资源列表的第一个值, 并从资源列表中移除
     *      如果这时候资源列表为空, 则删除缓存中的资源, 返回取出的资源
     *      如果这时候资源列表不为空, 则重新设置资源列表, 返回取出的资源
     * 如果资源列表不存在或为空, 则删除缓存中的资源, 返回null
     */
    public Object getResource(String getResourceLock, String resourcesCacheKey) throws InterruptedException {
        Object resource = null;
        try (RedisLock redisLock = new RedisLock(getResourceLock, REDIS_LOCK_TIME_OUT_MSECS, REDIS_LOCK_EXPIRE_MSECS)) {
            if (redisLock.lock()) {
                List<Object> resources = cacheService.get(resourcesCacheKey, List.class);
                if (Objects.nonNull(resources) && !resources.isEmpty()) {
                    resource = resources.remove(0);
                    if (resources.isEmpty()) {
                        cacheService.del(resourcesCacheKey);
                    } else {
                        cacheService.set(resourcesCacheKey, resources, RESOURCES_EXPIRE_SECS);
                    }
                } else {
                    cacheService.del(resourcesCacheKey);
                }
            }
        }
        return resource;
    }
}