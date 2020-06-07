package c.p.b.api.validation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created: 2018-08-13 20:34:17
 * 
 * @author  Michael.Zhang
 */
@EnableWebMvc
@Configuration
public class AppIdValidateHandlerInterceptorConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AppIdValidateHandlerInterceptor());
    }

}
