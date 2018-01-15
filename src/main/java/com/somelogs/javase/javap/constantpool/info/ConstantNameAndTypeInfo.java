package com.somelogs.javase.javap.constantpool.info;

import lombok.Data;

/**
 * Constant_NameAndType_info
 *
 * @author LBG - 2018/1/15 0015 17:42
 */
@Data
public class ConstantNameAndTypeInfo {

    /**
     * tag
     */
    private int tag;

    /**
     * constant index
     */
    private int constantIndex;

    /**
     * descriptor index
     */
    private int descriptorIndex;
}
