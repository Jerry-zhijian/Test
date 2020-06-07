package c.p.b.api.config;

import c.p.b.api.packaging.ResponsePackagingReturnValueHandlerConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auto configuration response auto packaging advice.
 *
 * Created: 2018-08-10 13:15:53
 * 
 * @author  Michael.Zhang
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({RestController.class, RequestMapping.class})
@Import({ResponsePackagingReturnValueHandlerConfigurer.class})
public class ResponsePackagingAutoConfiguration {
    
}
