package com.somelogs.javase.a_base_utilities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import lombok.Data;
import org.junit.Test;

/**
 * Object 常用方法
 *
 * @author LBG - 2017/11/16 0016
 */
public class CommonObjectMethod {

    /**
     * equals
     * JDK7 {@link java.util.Objects#equals(Object)} 效果一样
     */
    @Test
    public void equals() {
        // false
        System.out.println(Objects.equal(null, "a"));
        // true
        System.out.println(Objects.equal(null, null));
    }

    /**
     * hashCode
     * JDK7 {@link java.util.Objects#hashCode(Object)} 效果一样
     */
    @Test
    public void hashcode() {
        System.out.println(Objects.hashCode("i", "am", "ant"));
    }

    /**
     * toString
     */
    @Test
    public void toStringTest() {
        User user = new User("tom", 12);
        String userStr = MoreObjects.toStringHelper(user)
                                    .add("name", user.getName())
                                    .add("agent", user.getAge())
                                    .toString();
        System.out.println(userStr);
        System.out.println(user);
    }

    /**
     * comparator
     */
    @Test
    public void compareTo() {
        User tom = new User("tom", 3);
        User jerry = new User("jerry", 3);
        System.out.println(tom.compareTo(jerry));

    }

    @Data
    private static class User implements Comparable<User> {
        private String name;
        private Integer age;

        User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(User that) {
            return ComparisonChain.start()
                    .compare(name, that.getName())
                    .compare(age, that.getAge())
                    .result();
        }
    }
}
