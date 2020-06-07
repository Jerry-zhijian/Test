package c.p.b.api.config;

import c.p.b.api.validation.AppIdValidateHandlerInterceptorConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Auto configuration jsr 303 validation.
 *
 * Created: 2017-09-12 10:16:32
 *
 * @author Michael.Zhang
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({RequestMapping.class, Valid.class})
@Import({AppIdValidateHandlerInterceptorConfigurer.class})
public class Jsr303ValidationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MethodValidationPostProcessor getMethodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}