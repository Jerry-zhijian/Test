package c.b.s.common.cache.configuration;

import c.b.s.common.cache.domain.CacheService;
import c.b.s.common.cache.domain.MultiTaskHandler;
import c.b.s.common.cache.domain.ResourceService;
import c.b.s.common.util.ConfigUtil;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

@Configuration
@DependsOn("springContextUtil")
public class CacheConfiguration extends CachingConfigurerSupport {

    @Bean("redisTemplate")
    public StringRedisTemplate redisTemplate() {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {

        String type = ConfigUtil.getValue("cache.redis.type", "cluster");
        String host = ConfigUtil.getValue("cache.redis.host"
                , "r-uf68ad9221ac95b4.redis.rds.aliyuncs.com");
        int port = ConfigUtil.getIntValue("cache.redis.port", 6379);
        String password = ConfigUtil.getValue("cache.redis.password", "");

        if("cluster".equals(type)){ //集群
            RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
            clusterConfiguration.setPassword(RedisPassword.none());
            RedisNode redisNode = new RedisNode(host, port);
            List<RedisNode> nodeList = new ArrayList<>();
            nodeList.add(redisNode);
            clusterConfiguration.setClusterNodes(nodeList);
            JedisConnectionFactory factory = new JedisConnectionFactory(clusterConfiguration, jedisPoolConfig());
            return factory;
        } else {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(host);
            redisStandaloneConfiguration.setPort(port);
            redisStandaloneConfiguration.setDatabase(0);
            //jedisClientConfiguration.connectTimeout(Duration.ofMillis(0)); //connection timeout
            JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
                    .usePooling().poolConfig(jedisPoolConfig()).build();
            JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration, clientConfig);
            return factory;
        }

    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        int maxActive = ConfigUtil.getIntValue("cache.redis.maxActive", 1000);
        int maxIdle = ConfigUtil.getIntValue("cache.redis.maxIdle", 50);
        int minIdle = ConfigUtil.getIntValue("cache.redis.minIdle", 10);
        // long maxWaitMillis = ConfigUtil.getLongValue("cache.redis.maxWaitMillis", 120000L);
        long maxWaitMillis = 60000;
        boolean testOnBorrow = Boolean.valueOf(ConfigUtil.getValue("cache.redis.testOnBorrow", "true"));
        boolean testOnReturn = Boolean.valueOf(ConfigUtil.getValue("cache.redis.testOnReturn", "true"));
        boolean testWhileIdle = Boolean.valueOf(ConfigUtil.getValue("cache.redis.testWhileIdle", "true"));
        long timeBetweenEvictionRunsMillis = ConfigUtil.getLongValue("cache.redis.timeBetweenEvictionRunsMillis", 30000);
        /*int numTestsPerEvictionRun = ConfigUtil.getIntValue("cache.redis.numTestsPerEvictionRun", 10);*/
        long minEvictableIdleTimeMillis = ConfigUtil.getIntValue("cache.redis.minEvictableIdleTimeMillis", 600000);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // ********** 资源设置和使用 **********
        // 资源池中最大连接数
        poolConfig.setMaxTotal(maxActive);
        // 资源池允许最大空闲的连接数
        poolConfig.setMaxIdle(maxIdle);
        // 资源池确保最少空闲的连接数
        poolConfig.setMinIdle(minIdle);
        // 当资源池连接用尽后，调用者的最大等待时间(单位为毫秒)
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        // 向资源池借用连接时是否做连接有效性检测(ping)，无效连接会被移除
        poolConfig.setTestOnBorrow(testOnBorrow);
        // 向资源池归还连接时是否做连接有效性检测(ping)，无效连接会被移除
        poolConfig.setTestOnReturn(testOnReturn);

        // ********** 空闲资源监测 **********
        // 是否开启空闲资源监测
        poolConfig.setTestWhileIdle(testWhileIdle);
        // 空闲资源的检测周期(单位为毫秒)
        poolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        // 做空闲资源检测时，每次的采样数
        // poolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        // 资源池中资源最小空闲时间(单位为毫秒)，达到此值后空闲资源将被移除
        poolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        poolConfig.setSoftMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        return poolConfig;
    }

    @Bean
    CacheService cacheService() {
        return new CacheService();
    }

    @Bean
    public ResourceService resourceService() {
        return new ResourceService();
    }

    @Bean
    public MultiTaskHandler multiTaskHandler() {
        return new MultiTaskHandler();
    }
}