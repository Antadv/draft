package com.somelogs.guava.a_base_utilities;

import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

/**
 * 链式风格的比较器
 *
 * @author LBG - 2017/11/16 0016
 */
public class FluentComparator {

    /**
     * 自然排序
     */
    @Test
    public void natural() {
        Ordering<Integer> ordering = Ordering.natural();
        //Set<integer> set = new TreeSet<>(ordering);
        Set<Integer> set = new TreeSet<>();
        set.add(7);
        set.add(5);
        set.add(6);
        set.add(1);
        System.out.println(set);
    }
}
