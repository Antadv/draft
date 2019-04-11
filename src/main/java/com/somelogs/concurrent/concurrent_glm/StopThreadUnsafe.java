package com.somelogs.concurrent.concurrent_glm;

import lombok.Data;

/**
 * unsafe operation
 * @see java.lang.Thread#stop()
 * @author LBG - 2017/9/21 0021
 */
public class StopThreadUnsafe {

    private static User user = new User();

    private volatile static boolean stopFlag = false;

    @Data
    public static class User {
        private int id;
        private String name;
    }

    public static class ChangeObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                if (stopFlag) {
                    break;
                }
                synchronized (user) {
                    int v = (int) (System.currentTimeMillis() / 1000);
                    user.setId(v);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.setName(String.valueOf(v));
                }
                Thread.yield();
            }
        }
    }

    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (user) {
                    if (!String.valueOf(user.getId()).equals(user.getName())) {
                        System.out.println(user);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadObjectThread().start();
        while (true) {
            Thread t = new ChangeObjectThread();
            t.start();
            Thread.sleep(150);
            t.stop();
        }
    }
}
