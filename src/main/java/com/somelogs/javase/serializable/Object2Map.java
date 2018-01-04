package com.somelogs.javase.serializable;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import net.sf.cglib.beans.BeanMap;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 对象转map
 *
 * @author LBG - 2018/1/3 0003
 */
public class Object2Map {

    @Test
    public void test() throws Exception {
        User user = new User();
        user.setAge(2);
        user.setName("tom");
        user.setHobbies(Arrays.asList("running", "swimming"));

        Map<String, String> map = bean2Map(user);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue());
        }
    }

    @Test
    public void test2() throws Exception {
        User user = new User();
        user.setAge(2);
        user.setName("tom");
        user.setHobbies(Arrays.asList("running", "swimming"));

        System.out.println(user.toString());
    }

    /**
     * Javabean transfer to map
     */
    private static <T> Map<String, String> bean2Map(T bean) {
        Map<String, String> map = Maps.newHashMap();
        if (bean == null) {
            return map;
        }
        BeanMap beanMap = BeanMap.create(bean);
        for (Object key : beanMap.keySet()) {
            if (beanMap.get(key) == null) {
                continue;
            }
            map.put(key.toString(), String.valueOf(beanMap.get(key)));
        }
        return map;
    }

    @Getter
    @Setter
    public class User {
        private Integer age;
        private String name;
        private List<String> hobbies;
    }
}
