package c.p.b.api.validation;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

/**
 * Created: 2018-08-13 20:26:45
 * 
 * @author  Michael.Zhang
 */
public class AppIdValidateHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final String DEFAULT_APP_ID = "X-APP-ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String appId = request.getHeader(DEFAULT_APP_ID);
        if (appId == null || appId.isEmpty()) {
            throw new ValidationException(DEFAULT_APP_ID + " in http header must not be null or empty!");
        }

        return super.preHandle(request, response, handler);
    }

}
