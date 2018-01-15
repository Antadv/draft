package com.somelogs.javase.javap.constantpool.info;

import lombok.Data;

/**
 * Constant_MethodType_info
 *
 * @author LBG - 2018/1/15 0015
 */
@Data
public class ConstantMethodTypeInfo {

    /**
     * tag
     */
    private int tag;

    /**
     * descriptor index
     */
    private int descriptorIndex;
}
