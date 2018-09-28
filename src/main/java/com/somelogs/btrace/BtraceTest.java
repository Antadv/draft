package com.somelogs.btrace;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

/**
 * 描述
 *
 * @author LBG - 2018/9/27 0027
 */
@BTrace
public class BtraceTest {

    @OnMethod(clazz = "com.somelogs.btrace.ApplicationDemo", method = "add", location=@Location(Kind.RETURN))
    public static void onThreadStart(@ProbeClassName String classnName,
                                     @ProbeMethodName String methodName,
                                     AnyType[] args,
                                     @Return int result,
                                     @Duration long time) {

        BTraceUtils.println("className=" + classnName);
        BTraceUtils.println("methodName=" + methodName);
        BTraceUtils.println("method cost time=" + time);
        BTraceUtils.printArray(args);
        BTraceUtils.println("result=" + result);
    }
}
