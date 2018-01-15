package com.somelogs.javase.javap.constantpool.info;

import lombok.Data;

/**
 * Constant_Method_Handle_info
 *
 * @author LBG - 2018/1/15 0015
 */
@Data
public class ConstantMethodHandleInfo {

    /**
     * tag
     */
    private int tag;

    /**
     * reference kind
     */
    private int referenceKind;

    /**
     * reference index
     */
    private int referenceIndex;
}
