package c.p.b.api.packaging;

import c.p.b.api.response.Response;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created: 2018-08-13 18:23:54
 * 
 * @author  Michael.Zhang
 */
public class ResponsePackagingReturnValueHandlerProxy implements HandlerMethodReturnValueHandler {

    private HandlerMethodReturnValueHandler proxyHandler;

    public ResponsePackagingReturnValueHandlerProxy(HandlerMethodReturnValueHandler proxyHandler) {
        this.proxyHandler = proxyHandler;
    }


    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return this.proxyHandler.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        final Object response = needAutoPackaging(returnValue, returnType) ? Response.ok(returnValue) : returnValue;

        this.proxyHandler.handleReturnValue(response, returnType, mavContainer, webRequest);
    }

    private boolean needAutoPackaging(Object returnValue, MethodParameter returnType) {
        return hasResponsePackagingAnnotation(returnType) && !(returnValue instanceof Response);
    }

    private boolean hasResponsePackagingAnnotation(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ResponsePackaging.class)
                || AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponsePackaging.class)
                || AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponsePackagingRestController.class);
    }

}
