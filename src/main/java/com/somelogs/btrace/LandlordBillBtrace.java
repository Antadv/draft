package com.somelogs.btrace;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

/**
 * 描述
 *
 * @author LBG - 2018/9/28 0028
 */
@BTrace
public class LandlordBillBtrace {

    @OnMethod(clazz = "com.manyi.fdb.contractbill.soa.domainservice.service.impl.RenterBillServiceDomainImpl",
              method = "postProcessBillResult", location=@Location(Kind.RETURN))
    public static void billList(AnyType[] args, @Duration long time, @Return AnyType anyType) {
        BTraceUtils.printArray(args);
        BTraceUtils.println(time);
        BTraceUtils.println(anyType);
    }
}
