//package com.somelogs.btrace;
//
//import com.sun.btrace.BTraceUtils;
//import com.sun.btrace.annotations.BTrace;
//import com.sun.btrace.annotations.Kind;
//import com.sun.btrace.annotations.Location;
//import com.sun.btrace.annotations.OnMethod;
//
///**
// * 描述
// *
// * @author LBG - 2018/10/31 0031
// */
//@BTrace
//public class ContractSignBtrace {
//
//    @OnMethod(
//            clazz = "com.manyi.fdb.contractbill.soa.core.dal.dataobject.FdbRenterContract",
//            method = "setUpdateTime",
//            location = @Location(Kind.RETURN))
//    public static void findContractSign() {
//        BTraceUtils.jstack();
//    }
//}
