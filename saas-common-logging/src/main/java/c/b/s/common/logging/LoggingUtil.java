package c.b.s.common.logging;

import c.b.s.common.logging.domain.LoginLog;
import c.b.s.common.logging.domain.LoginLogRepository;
import c.b.s.common.logging.domain.OperationLog;
import c.b.s.common.logging.domain.OperationLogRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by guiqingqing on 2018/8/18.
 */
public class LoggingUtil implements ApplicationContextAware {
    private static volatile ApplicationContext applicationContext;

    public static void loginLog(Long userId, String userName, String realName, String ip, Byte type) {
        LoginLogRepository loginLogRepository = getLoginLogRepository();
        LoginLog loginLog = LoginLog.builder()
                .userId(userId)
                .userName(userName)
                .realName(realName)
                .ip(ip)
                .type(type)
                .build();
        loginLogRepository.save(loginLog);
    }

    public static void operationLog(Long userId, String userName, String realName, Long applicationId,
                                    String ip, String url, String operationCode, String operationValue) {
        OperationLogRepository operationLogRepository = getOperationLogRepository();
        OperationLog operationLog = OperationLog.builder()
                .userId(userId)
                .userName(userName)
                .realName(realName)
                .applicationId(applicationId)
                .ip(ip)
                .url(url)
                .operationCode(operationCode)
                .operationValue(operationValue)
                .build();
        operationLogRepository.save(operationLog);
    }

    private static LoginLogRepository getLoginLogRepository() {
        return applicationContext.getBean(LoginLogRepository.class);
    }

    private static OperationLogRepository getOperationLogRepository() {
        return applicationContext.getBean(OperationLogRepository.class);
    }

    private static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LoggingUtil.applicationContext = applicationContext;
    }
}