package com.somelogs.javase.javap.constantpool;

import lombok.Data;

/**
 * Base Constant Reference Info
 *
 * @author LBG - 2018/1/15 0015 17:36
 */
@Data
public abstract class BaseConstantRefInfo {

    /**
     * tag
     */
    protected int tag;

    /**
     * constant_class_info index
     */
    protected int classIndex;

    /**
     * constant_nameAndType index
     */
    protected int nameAndTypeIndex;
}
