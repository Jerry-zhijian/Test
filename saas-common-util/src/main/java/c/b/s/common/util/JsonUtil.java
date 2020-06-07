package c.b.s.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by guiqingqing on 2018/9/5.
 */
public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    public static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    public static String convertToJson(Object value) {
        try {
            if (value != null) {
                return objectMapper.writeValueAsString(value);
            }
        } catch (Exception e) {
            LOGGER.error("convertToJson", e);
        }
        return null;
    }

    public static <T> T convertToObject(String jsonStr, Class<T> valueType) {

        try {
            if (jsonStr == null || jsonStr == "") {
                return null;
            }
            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            LOGGER.error("convertToObject错误：", e);
        }
        return null;
    }
}