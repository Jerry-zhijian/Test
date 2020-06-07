package c.p.b.api.packaging;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
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
public class ResponsePackagingReturnValueHandlerConfigurer implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodReturnValueHandler> handlerList = handlerAdapter.getReturnValueHandlers();
        if (CollectionUtils.isEmpty(handlerList)) {
            return;
        }

        List<HandlerMethodReturnValueHandler> newHandlerList = new ArrayList<>(handlerList.size());
        for (HandlerMethodReturnValueHandler handler : handlerList) {
            if (handler instanceof RequestResponseBodyMethodProcessor) {
                handler = new ResponsePackagingReturnValueHandlerProxy(handler);
            }
            newHandlerList.add(handler);
        }

        handlerAdapter.setReturnValueHandlers(newHandlerList);
    }

}
