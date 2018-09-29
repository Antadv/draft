package com.somelogs.btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.println;

/**
 * 描述
 *
 * @author LBG - 2018/9/27 0027
 */
@BTrace
public class BtraceThrowbleTest {

    //@OnMethod(clazz = "com.somelogs.btrace.ApplicationDemo", method = "add", location=@Location(Kind.ENTRY))
    //public static void onThreadStart(@ProbeClassName String className,
    //                                 @ProbeMethodName String methodName,
    //                                 AnyType[] args,
    //                                 @Return int result,
    //                                 @Duration long time) {
    //
    //    println("=============");
    //    println("className=" + className);
    //    println("methodName=" + methodName);
    //    println("method cost time=" + time);
    //    //BTraceUtils.printArray(args);
    //    //BTraceUtils.println("result=" + result);
    //
    //    BTraceUtils.jstack();
    //    //BTraceUtils.println(exception);
    //}

    @TLS static Throwable currentException;

    // introduce probe into every constructor of java.lang.Throwable
    // class and store "this" in the thread local variable.
    @OnMethod(clazz="java.lang.Throwable", method="<init>")
    public static void onthrow(@Self Throwable self) {
        currentException = self;
    }

    @OnMethod(clazz="java.lang.Throwable", method="<init>")
    public static void onthrow1(@Self Throwable self, String s) {
        currentException = self;
    }

    @OnMethod(clazz="java.lang.Throwable", method="<init>")
    public static void onthrow1(@Self Throwable self, String s, Throwable cause) {
        currentException = self;
    }

    @OnMethod(clazz="java.lang.Throwable", method="<init>")
    public static void onthrow2(@Self Throwable self, Throwable cause) {
        currentException = self;
    }

    // when any constructor of java.lang.Throwable returns
    // print the currentException's stack trace.
    @OnMethod(clazz="java.lang.Throwable", method="<init>", location=@Location(Kind.RETURN))
    public static void onthrowreturn() {
        if (currentException != null) {
            BTraceUtils.Threads.jstack(currentException);
            println("=====================");
            currentException = null;
        }
    }
}
