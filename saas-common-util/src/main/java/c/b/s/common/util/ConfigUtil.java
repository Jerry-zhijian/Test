package c.b.s.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);

     public static String getValue(String key, String defaultValue){
         try {
                return SpringContextUtil.getProperty(key, String.class, defaultValue);
         } catch (Exception e) {
             LOGGER.error(String.format("读取配置异常 key:%s", key), e);
         }
        return defaultValue;
     }

     public static int getIntValue(String key, int defaultValue){
         try {
             return SpringContextUtil.getProperty(key, int.class, defaultValue);
         } catch (Exception e) {
             LOGGER.error(String.format("读取配置异常 key:%s", key), e);
         }
         return defaultValue;
     }

    public static long getLongValue(String key, long defaultValue){
        try {
            return SpringContextUtil.getProperty(key, long.class, defaultValue);
        } catch (Exception e) {
            LOGGER.error(String.format("读取配置异常 key:%s", key), e);
        }
        return defaultValue;
    }

}
