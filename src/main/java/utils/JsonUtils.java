package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * JSON 工具类
 *
 * @author LBG - 2017/12/13 0013
 */
public class JsonUtils {

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {}

    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T readValue(String text, Class<T> clazz) {
        try {
            return mapper.readValue(text, clazz);
        } catch (IOException e) {
            logger.warn("readValue {} failed", text, e);
            return null;
        }
    }

    public static String writeValueAsString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.warn("writeValueAsString {} faild", obj.toString(), e);
            return "";
        }
    }
}
