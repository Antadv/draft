package com.somelogs.javase.javap.constantpool.info;

import lombok.Data;

/**
 * Constant_Invoke_Dynamic_info
 *
 * @author LBG - 2018/1/15 0015
 */
@Data
public class ConstantInvokeDynamicInfo {

    /**
     * tag
     */
    private int tag;

    /**
     * bootstrap_method_attr_index
     */
    private int bootstrapMethodAttrIndex;

    /**
     * name_and_type_index
     */
    private int nameAndTypeIndex;
}
