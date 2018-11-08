package com.somelogs.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.lang.reflect.Type;

/**
 * JSON 序列化、反序列化 工具类
 *
 * @author LBG - 2017/12/13 0013
 */
public class JsonUtils {

    private JsonUtils() {}

    private static ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // disabled features:
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /********************** Serialize **********************/

    public static String writeValueAsString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Serialize Object to JSON failed" , e);
        }
    }

    /********************** Deserialize **********************/

    /**
     * 用于具体泛型 readValue(json, new TypeReference<Map<String, Integer>>)
     */
    public static <T> T readValue(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        }
    }

    /**
     * 用于泛型，反射时 method.getGenericReturnType()
     */
    public static <T>T readValue(String json, Type genericType) {
        try {
            return mapper.readValue(json, TypeFactory.defaultInstance().constructType(genericType));
        } catch (Exception e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        }
    }

}
