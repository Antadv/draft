package com.somelogs.ssq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 描述
 *
 * @author LBG - 2018/10/26 0026
 */
public class SSQGenerator {

    private static List<String> redFullList = new ArrayList<>(33);
    private static List<String> blueFullList = new ArrayList<>(16);

    static {
        for (int i = 1; i <= 33; i++) {
            redFullList.add(i < 10 ? "0" + i : i + "");
        }
        for (int i = 1; i <= 16; i++) {
            blueFullList.add(i < 10 ? "0" + i : i + "");
        }
    }

    public static void main(String[] args) {
        List<String> redList = new ArrayList<>(6);
        for (int i = 1; i <= 6; i++) {
            Collections.shuffle(redFullList);
            redList.add(redFullList.get(0));
            redFullList.remove(redFullList.get(0));
        }

        Collections.shuffle(blueFullList);
        String blue = blueFullList.get(0);

        StringBuilder todayNum = new StringBuilder();
        for (String red : redList) {
            todayNum.append(red).append(" ");
        }
        todayNum.append(" ").append(blue);
        System.out.println(todayNum);
    }
}
