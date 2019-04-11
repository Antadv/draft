package com.somelogs.distribution.soa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.somelogs.javase.proxy.User;
import com.somelogs.distribution.soa.client.UserClient;
import com.somelogs.distribution.soa.factory.SOAClientFactory;
import com.somelogs.distribution.soa.model.AddUserRequest;
import com.somelogs.distribution.soa.model.Response;
import com.somelogs.utils.JsonUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述
 *
 * @author LBG - 2018/1/4 0004
 */
public class SOATest {

    private static final String SERVER_URL = "http://localhost:8090/";

    @Test
    public void testSOA() {
        SOAClientFactory factory = new SOAClientFactory(SERVER_URL);
        UserClient userClient = factory.create(UserClient.class);

        AddUserRequest param = new AddUserRequest();
        param.setUsername("tom");
        param.setMobile("18978754455");
        param.setList(Arrays.asList(1L, 2L));

        Response<Long> response = userClient.addUser(param);
        System.out.println("--------userId=" + response);
    }

    @Test
    public void cycle() throws Exception {
        List<User> list = new ArrayList<>();
        User user = new User();
        for (int i = 0; i < 10; i++) {
            user.setId(i + 1);
            user.setName("tom" + i);
            list.add(user);
        }
        /*
         * jackson works well
         */
        String json = JsonUtils.writeValueAsString(list);

        /*
         * if list add the same object, fast json need disable circular reference
         */
        String s = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(s);
    }
}
