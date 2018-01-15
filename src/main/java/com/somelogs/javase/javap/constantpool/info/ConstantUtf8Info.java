package com.somelogs.javase.javap.constantpool.info;

import lombok.Data;

/**
 * Constant_Utf8_Info
 *
 * @author LBG - 2018/1/15 0015
 */
@Data
public class ConstantUtf8Info {

    /**
     * tag
     */
    private int tag;

    /**
     * length
     */
    private int length;

    /**
     * bytes
     */
    private byte[] bytes;
}
