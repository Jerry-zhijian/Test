package c.p.b.api.validation;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created: 2018-08-13 18:39:17
 * 
 * @author  Michael.Zhang
 */
@Configuration
public class AppIdValidateArgumentResolverConfigurer implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodArgumentResolver> handlerList = handlerAdapter.getArgumentResolvers();
        if (CollectionUtils.isEmpty(handlerList)) {
            return;
        }

        List<HandlerMethodArgumentResolver> newHandlerList = new ArrayList<>(handlerList.size());
        for (HandlerMethodArgumentResolver handler : handlerList) {
            if (handler instanceof RequestResponseBodyMethodProcessor) {
                handler = new AppIdValidateArgumentResolverProxy(handler);
            }
            newHandlerList.add(handler);
        }

        handlerAdapter.setArgumentResolvers(newHandlerList);
    }

}
