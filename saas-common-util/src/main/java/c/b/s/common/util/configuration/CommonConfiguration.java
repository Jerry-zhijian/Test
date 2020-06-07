package c.b.s.common.util.configuration;

import c.b.s.common.util.SpringContextUtil;
import c.b.s.common.util.exception.GlobalExceptionHandler;
import c.b.s.common.util.logging.aspect.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lingjie
 * @Date: 2018/8/2 18:29
 */
@Configuration
public class CommonConfiguration {
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        return globalExceptionHandler;
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean("springContextUtil")
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }
}