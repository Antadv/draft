package com.somelogs.javase;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author LBG - 2018/7/25 0025
 */
public class LoopRetryDemo {

    public static void main(String[] args) {
        List<Integer> outerList = new ArrayList<>();
        outerList.add(1);
        outerList.add(2);
        outerList.add(3);

        List<Integer> innerList = new ArrayList<>();
        innerList.add(4);
        innerList.add(5);
        innerList.add(6);

        retry:
        for (Integer out : outerList) {
            System.out.println("out: " + out);
            for (Integer inner : innerList) {
                if (inner < 5) {
                    System.out.println("inner: " + inner);
                } else if (out == 3) {
                    break retry;
                } else {
                    continue retry;
                }
            }
        }
    }
}
