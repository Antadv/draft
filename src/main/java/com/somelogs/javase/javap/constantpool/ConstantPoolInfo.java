package com.somelogs.javase.javap.constantpool;

import com.somelogs.javase.javap.constantpool.info.*;
import lombok.Data;

/**
 * constant pool
 *
 * @author LBG - 2018/1/15 0015 17:19
 */
@Data
public class ConstantPoolInfo {

    private ConstantUtf8Info utf8Info;
    private ConstantIntegerInfo integerInfo;
    private ConstantFloatInfo floatInfo;
    private ConstantLongInfo longInfo;
    private ConstantDoubleInfo doubleInfo;
    private ConstantClassInfo classInfo;
    private ConstantStringInfo stringInfo;
    private ConstantFieldRefInfo fieldRefInfo;
    private ConstantMethodRefInfo methodRefInfo;
    private ConstantInterfaceMethodRefInfo interfaceMethodRefInfo;
    private ConstantNameAndTypeInfo nameAndTypeInfo;
    private ConstantMethodHandleInfo handleInfo;
    private ConstantMethodTypeInfo methodTypeInfo;
    private ConstantInvokeDynamicInfo dynamicInfo;

}
