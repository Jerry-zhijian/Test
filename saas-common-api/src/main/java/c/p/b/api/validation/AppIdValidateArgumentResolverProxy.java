package c.p.b.api.validation;

import c.p.b.api.packaging.ResponsePackaging;
import c.p.b.api.packaging.ResponsePackagingRestController;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;

/**
 * Created: 2018-08-13 19:52:46
 * 
 * @author  Michael.Zhang
 */
public class AppIdValidateArgumentResolverProxy implements HandlerMethodArgumentResolver {

    private HandlerMethodArgumentResolver proxyResolver;


    public AppIdValidateArgumentResolverProxy(HandlerMethodArgumentResolver proxyResolver) {
        this.proxyResolver = proxyResolver;
    }


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return proxyResolver.supportsParameter(methodParameter);
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        return proxyResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }


    private boolean hasResponsePackagingAnnotation(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ResponsePackaging.class)
                || AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponsePackaging.class)
                || AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponsePackagingRestController.class);
    }

}
