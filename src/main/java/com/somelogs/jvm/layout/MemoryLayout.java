package com.somelogs.jvm.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Hotspot VM 对象内存布局
 *
 * @author LBG - 2019/5/7
 */
public class MemoryLayout {

    public static void main(String[] args) throws InterruptedException {
        List<AAAA> aaaa = new ArrayList<>(100000);
        List<BBBB> bbbb = new ArrayList<>(100000);
        List<CCCC> cccc = new ArrayList<>(100000);
        List<DDDD> dddd = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            aaaa.add(new AAAA());
            bbbb.add(new BBBB());
            cccc.add(new CCCC());
            dddd.add(new DDDD());
        }
        System.out.println("============dfd=");
        TimeUnit.MINUTES.sleep(1);
    }

    static class AAAA {
    }

    static class BBBB {
        int a = 1;
    }

    static class CCCC {
        long b = 1L;
    }

    static class DDDD {
        String d = "hello";
    }
}
