package com.somelogs.javase.serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 泛型反序列化
 *
 * @author LBG - 2017/12/29 0029
 */
@Data
public class GenericDeserializer {

    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);

        String json = mapper.writeValueAsString(list);
        readValueByType(json);
    }

    private static String list2Json() throws JsonProcessingException {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        return mapper.writeValueAsString(list);
    }

    /**
     * 泛型对象反序列化（不用TypeReference）
     */
    @Test
    public void readValue() throws Exception {
        String json = list2Json();
        List descList = mapper.readValue(json, List.class);
        for (Object obj : descList) {
            //System.out.println(obj.getClass());
            Long e = (Long) obj;
            System.out.println(e);
        }
    }

    /**
     * 普通对象反序列化（对象内有 List<Long>）
     */
    @Test
    public void readValueWithInnerList() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("tom");
        user.setList(Arrays.asList(1L, 2L));

        String json = mapper.writeValueAsString(user);
        User descUser = mapper.readValue(json, new TypeReference<GenericDeserializer.User>(){});
        System.out.println(descUser);

        List<Long> list = descUser.getList();
        for (Long e : list) {
            System.out.println(e);
        }
    }

    /**
     * 泛型对象反序列化
     */
    private static void readValueByType(String json) throws IOException {
        List<Long> list = mapper.readValue(json, new TypeReference<List<Long>>(){});
        for (Long e : list) {
            System.out.println(e);
        }
    }

    @Test
    public void responseTest() throws Exception {
        Response<Long> resp = new Response<>();
        resp.setData(3L);
        String json = mapper.writeValueAsString(resp);
        //Response response = mapper.readValue(json, Response.class);
        Response<Long> response = mapper.readValue(json, new TypeReference<GenericDeserializer.Response<Long>>(){});
        System.out.println(response.getData().getClass());
    }

    @Data
    private static class User {
        private Integer id;
        private String name;
        private List<Long> list;
    }

    @Data
    private static class Response<T> {
        private Integer code;
        private String message;
        private T data;
    }
}
