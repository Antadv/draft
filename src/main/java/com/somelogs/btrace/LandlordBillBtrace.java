package com.somelogs.btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

/**
 * 描述
 *
 * @author LBG - 2018/9/28 0028
 */
@BTrace(unsafe = true)
public class LandlordBillBtrace {

    @OnMethod(clazz = "com.manyi.fdb.contractbill.soa.domainservice.service.impl.RenterBillServiceDomainImpl",
              method = "listAllBill4Landlord",
              location=@Location(Kind.RETURN))
    public static void billList(@Duration long time) {
        BTraceUtils.println(time);
        BTraceUtils.println("======================");
    }
}
