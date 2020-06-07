package c.b.s.common.ratelimiter.handler;

import c.b.s.common.ratelimiter.annotation.RateLimiter;
import c.b.s.common.util.SpringContextUtil;
import c.b.s.common.util.UserInfoUtil;
import c.p.b.api.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 限流处理器
 */
@Aspect
@Component
public class RateLimterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimterHandler.class);

    @Autowired
    private RedisTemplate redisTemplate;

    private DefaultRedisScript<Long> getRedisScript;

    @PostConstruct
    public void init() {
        getRedisScript = new DefaultRedisScript<>();
        getRedisScript.setResultType(Long.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimter.lua")));
        LOGGER.info("RateLimterHandler[分布式限流处理器]脚本加载完成");
    }

    @Pointcut("@annotation(c.b.s.common.ratelimiter.annotation.RateLimiter)")
    public void rateLimiter() {
    }

    @Around("@annotation(rateLimiter)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, RateLimiter rateLimiter) throws Throwable {
        Signature signature = proceedingJoinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("the Annotation @RateLimter must used on method!");
        }
        /**
         * 获取注解参数
         */
        // 限流模块key
        String limitKey = rateLimiter.key();
        // 限流阈值
        //优先系统配置,如果系统没有配置,则以注解为准
        long limitTimes = rateLimiter.limit();
        String configLimitTimes = SpringContextUtil.getProperty(rateLimiter.key());
        limitTimes = StringUtils.isNotEmpty(configLimitTimes) ? Long.valueOf(configLimitTimes) : limitTimes;
        // 限流超时时间
        long expireTime = rateLimiter.expire();

        LOGGER.info("[分布式限流处理器]参数值为-limitTimes={},limitTimeout={},限流接口:{}", limitTimes, expireTime, limitKey);
        // 限流提示语
        String message = rateLimiter.message();
        /**
         * 执行Lua脚本
         */
        List<String> keyList = new ArrayList();
        // 设置key值为注解中的值
        keyList.add(limitKey);
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(expireTime));
        args.add(String.valueOf(limitTimes));
        /**
         * 调用脚本并执行
         */
        //spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本异常，此处拿到原redis的connection执行脚本
        Long result = (Long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            Object nativeConnection = connection.getNativeConnection();
            // 集群模式和单点模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
            // 集群
            if (nativeConnection instanceof JedisCluster) {
                return (Long) ((JedisCluster) nativeConnection).eval(getRedisScript.getScriptAsString(), keyList, args);
            }

            // 单点
            else if (nativeConnection instanceof Jedis) {
                return (Long) ((Jedis) nativeConnection).eval(getRedisScript.getScriptAsString(), keyList, args);
            }
            return null;
        });

        if (result == 0) {
            LOGGER.info(String.format("由于超过单位时间=%s-允许的请求次数=%s[触发限流],商户Id:%s,用户Id:%s,限流接口:%s", expireTime,
                    limitTimes, UserInfoUtil.getTenantId(), UserInfoUtil.getUserId(), limitKey));
            if (StringUtils.isEmpty(message)) {
                throw new BusinessException(-2, "系统正在处理中,请稍后重试(406)");
            } else {
                return message;
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
