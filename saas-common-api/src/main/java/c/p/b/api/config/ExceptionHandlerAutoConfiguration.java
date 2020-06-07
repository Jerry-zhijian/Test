package c.p.b.api.config;

import c.p.b.api.exception.ExceptionHandlerAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Auto configuration exception handler advice.
 *
 * Created: 2017-09-10 20:47:40
 *
 * @author Michael.Zhang
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({Controller.class, RequestMapping.class})
@Import({ExceptionHandlerAdvice.class})
public class ExceptionHandlerAutoConfiguration {

}
