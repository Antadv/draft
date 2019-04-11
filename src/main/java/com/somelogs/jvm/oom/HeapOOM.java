package com.somelogs.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆oom
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 *
 * Created by liubingguang on 2017/6/23.
 */
public class HeapOOM {

    static class OOMObject {
    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
