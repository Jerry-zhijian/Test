package c.p.b.api.validation;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created: 2018-08-13 19:25:47
 * 
 * @author  Michael.Zhang
 */
@Configuration
public class AppIdValidateHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String DEFAULT_APP_ID = "X-APP-ID";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return false;
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory webDataBinderFactory) throws Exception {

        String appId = webRequest.getHeader(DEFAULT_APP_ID);
        // TODO:
        return null;
    }

}
