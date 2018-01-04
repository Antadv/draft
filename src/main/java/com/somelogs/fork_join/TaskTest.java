package com.somelogs.fork_join;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * 描述
 * Created by liubingguang on 2017/6/14.
 */
public class TaskTest {

    @Test
    public void test() {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        Future<Integer> result = pool.submit(new CountTask(1, 1000000));
        try {
            System.out.println(result.get());
            System.out.println("fork join total time:" + (System.currentTimeMillis() - startTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2() {
        long startTime = System.currentTimeMillis();
        int sum = 0;
        for (int i = 1; i <= 1000000; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println("loop time:" + (System.currentTimeMillis() - startTime));
    }
}
