package com.somelogs.javase.javap.constantpool;

import lombok.Data;

/**
 * Base constant num info
 *
 * @author LBG - 2018/1/15 0015 17:26
 */
@Data
public abstract class BaseConstantNumInfo {

    /**
     * tag
     */
    protected int tag;

    /**
     * bytes
     */
    protected byte[] bytes;
}
