package c.p.b.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created 2018-09-19 17:28:47
 *
 * @author Michael.Zhang
 */
public class JsonUtils {

    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    public static <T> T toClass(String content, Class<T> type) {
        try {
            return OBJ_MAPPER.readValue(content, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
