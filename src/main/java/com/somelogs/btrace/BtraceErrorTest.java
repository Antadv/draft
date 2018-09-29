package com.somelogs.btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;

/**
 * 描述
 *
 * @author LBG - 2018/9/29 0029
 */
@BTrace
public class BtraceErrorTest {

    @OnMethod(
            clazz = "com.somelogs.btrace.ApplicationDemo",
            method = "add",
            location = @Location(Kind.ERROR))
    public static void onError(Throwable throwable) {
        BTraceUtils.Threads.jstack(throwable);
    }
}
