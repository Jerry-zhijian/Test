package c.b.s.common.util;

import org.springframework.core.NamedThreadLocal;

import java.util.Map;

/**
 * Created by guiqingqing on 2018/9/13.
 */
public class RequestUtil {
    public static final ThreadLocal<Map<String, String>> REQUEST_HEADER_THREAD_LOCAL = new NamedThreadLocal("Request Header");

    public static void setHeaders(Map<String, String> headers) {
        REQUEST_HEADER_THREAD_LOCAL.set(headers);
    }

    public static Map<String, String> getHeaders() {
        return REQUEST_HEADER_THREAD_LOCAL.get();
    }

    public static void clearHeader() {
        REQUEST_HEADER_THREAD_LOCAL.remove();
    }
}