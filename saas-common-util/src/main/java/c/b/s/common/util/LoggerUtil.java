package c.b.s.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: lingjie
 * @Date: 2018/8/3 13:24
 */
public class LoggerUtil {

    private static Logger getLogger() {
        return  LoggerFactory.getLogger(getClassName());
    }

    public static void error(String msg) {
        Logger logger = getLogger();
        if (logger.isErrorEnabled()) {
            logger.error(msg);
        }
    }

    public static void error(String msg, Object... obj) {
        Logger logger = getLogger();
        if (logger.isErrorEnabled()) {
            logger.error(msg, obj);
        }
    }

    public static void warn(String msg) {
        Logger logger = getLogger();
        if (logger.isWarnEnabled()) {
            logger.warn(msg);
        }
    }

    public static void warn(String msg, Object... obj) {
        Logger logger = getLogger();
        if (logger.isWarnEnabled()) {
            logger.warn(msg, obj);
        }
    }

    public static void info(String msg) {
        Logger logger = getLogger();
        if (logger.isInfoEnabled()) {
            logger.info(msg);
        }
    }

    public static void info(String msg, Object... obj) {
        Logger logger = getLogger();
        if (logger.isInfoEnabled()) {
            logger.info(msg, obj);
        }
    }

    public static void debug(String msg) {
        Logger logger = getLogger();
        if (logger.isDebugEnabled()) {
            logger.debug(msg);
        }
    }

    public static void debug(String msg, Object... obj) {
        Logger logger = getLogger();
        if (logger.isDebugEnabled()) {
            logger.debug(msg, obj);
        }
    }


    //获取调用日志方法的静态类类名
    private static String getClassName() {
        return new SecurityManager() {
            public String getClassName() {
                Class[] classes = getClassContext();
                return classes[4].getName();
            }
        }.getClassName();
    }
}
