package c.b.s.common.util;

import org.springframework.core.NamedThreadLocal;

/**
 * Created by guiqingqing on 2018/8/20.
 */
public class UserInfoUtil {
    private static final ThreadLocal<Long> USER_ID_THREAD_LOCAL = new NamedThreadLocal("USER ID");
    private static final ThreadLocal<String> USER_NAME_THREAD_LOCAL = new NamedThreadLocal("USER NAME");
    private static final ThreadLocal<String> USER_REAL_NAME_THREAD_LOCAL = new NamedThreadLocal("USER REAL NAME");
    private static final ThreadLocal<Long> APPLICATION_ID_THREAD_LOCAL = new NamedThreadLocal("APPLICATION ID");
    private static final ThreadLocal<String> TOKEN_THREAD_LOCAL = new NamedThreadLocal("TOKEN");
    private static final ThreadLocal<Long> TENANT_ID_THREAD_LOCAL = new NamedThreadLocal("TENANT ID");

    public static void setUserId(Long userId) {
        USER_ID_THREAD_LOCAL.set(userId);
    }

    public static void setUserName(String userName) {
        USER_NAME_THREAD_LOCAL.set(userName);
    }

    public static void setRealName(String realName) {
        USER_REAL_NAME_THREAD_LOCAL.set(realName);
    }

    public static void setApplicationId(Long applicationId) {
        APPLICATION_ID_THREAD_LOCAL.set(applicationId);
    }

    public static void setToken(String token) {
        TOKEN_THREAD_LOCAL.set(token);
    }

    public static void setTenantId(Long tenantId) {
        TENANT_ID_THREAD_LOCAL.set(tenantId);
    }

    public static Long getUserId() {
        return USER_ID_THREAD_LOCAL.get();
    }

    public static String getUserName() {
        return USER_NAME_THREAD_LOCAL.get();
    }

    public static String getRealName() {
        return USER_REAL_NAME_THREAD_LOCAL.get();
    }

    public static Long getApplicationId() {
        return APPLICATION_ID_THREAD_LOCAL.get();
    }

    public static String getToken() {
        return TOKEN_THREAD_LOCAL.get();
    }

    public static Long getTenantId() {
        return TENANT_ID_THREAD_LOCAL.get();
    }

    public static void clearUserId() {
        USER_ID_THREAD_LOCAL.remove();
    }

    public static void clearUserName() {
        USER_NAME_THREAD_LOCAL.remove();
    }

    public static void clearRealName() {
        USER_REAL_NAME_THREAD_LOCAL.remove();
    }

    public static void clearApplicationId() {
        APPLICATION_ID_THREAD_LOCAL.remove();
    }

    public static void clearToken() {
        TOKEN_THREAD_LOCAL.remove();
    }

    public static void clearTenant() {
        TENANT_ID_THREAD_LOCAL.remove();
    }

    public static void clear() {
        clearUserId();
        clearUserName();
        clearRealName();
        clearApplicationId();
        clearToken();
        clearTenant();
    }
}