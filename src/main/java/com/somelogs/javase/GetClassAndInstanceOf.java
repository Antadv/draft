package com.somelogs.javase;

import java.io.File;
import java.io.IOException;

/**
 * 描述
 *
 * @author LBG - 2018/8/6 0006
 */
public class GetClassAndInstanceOf {

    static class Worker {}

    private static class PCWorker extends Worker {}

    public static void main(String[] args) {
        Worker worker = new PCWorker();
        System.out.println(worker.getClass() == Worker.class);
        System.out.println(worker.getClass() == PCWorker.class);

        File file = new File("");
        try {
            boolean b = file.createNewFile();
        } catch (Exception e) {
            if (e.getClass() == IOException.class) {
                System.out.println("1");
            } else {
                System.out.println("2");
            }
        }
    }
}
