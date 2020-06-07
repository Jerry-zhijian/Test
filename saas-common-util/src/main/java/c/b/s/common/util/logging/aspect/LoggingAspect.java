package c.b.s.common.util.logging.aspect;

import c.b.s.common.logging.LoggingUtil;
import c.b.s.common.util.SystemUtil;
import c.b.s.common.util.UserInfoUtil;
import c.b.s.common.util.logging.annotation.Logging;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by guiqingqing on 2018/8/18.
 */
@Aspect
public class LoggingAspect {
    @Pointcut("@annotation(logging)")
    public void pc(Logging logging) {}

    @Around("pc(logging)")
    public Object logging(ProceedingJoinPoint pjp, Logging logging) throws Throwable {
        Long userId = getUserId();
        String userName = getUserName();
        String realName = getRealName();
        Long applicationId = getApplicationId();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = SystemUtil.getIPAddress(request);
        String url = request.getRequestURL().toString();

        String operationCode = logging.code();
        StringBuilder buffer = new StringBuilder();
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            buffer.append(arg).append(",");
        }
        String operationValue = buffer.toString().replaceAll(",$", "");

        Object obj = pjp.proceed();
        while (operationValue.length() > 1024) {
            String curValue = operationValue.substring(0, 1024);
            LoggingUtil.operationLog(userId, userName, realName, applicationId, ip, url, operationCode, curValue);
            operationValue = curValue.substring(1024);
        }
        LoggingUtil.operationLog(userId, userName, realName, applicationId, ip, url, operationCode, operationValue);
        return obj;
    }

    private Long getUserId() {
        Long userId = UserInfoUtil.getUserId();
        return Objects.nonNull(userId) ? userId : 0;
    }

    private String getUserName() {
        String userName = UserInfoUtil.getUserName();
        return Objects.nonNull(userName) ? userName : "";
    }

    private String getRealName() {
        String realName = UserInfoUtil.getRealName();
        return Objects.nonNull(realName) ? realName : "";
    }

    private Long getApplicationId() {
        Long applicationId = UserInfoUtil.getApplicationId();
        return Objects.nonNull(applicationId) ? applicationId : 0;
    }
}