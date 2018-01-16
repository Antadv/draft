package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import lombok.Data;

import java.io.InputStream;

/**
 * Constant_NameAndType_info
 *
 * @author LBG - 2018/1/15 0015 17:42
 */
@Data
public class ConstantNameAndTypeInfo extends ConstantPoolInfo {

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

    @Override
    public void read(InputStream inputStream) {

    }
}
