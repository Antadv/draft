package com.somelogs.javase.javap.file;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U2;
import com.somelogs.javase.javap.datatype.U4;
import lombok.Data;

/**
 * 描述
 *
 * @author LBG - 2018/1/15 0015 16:42
 */
@Data
public class ClassInfo {

    /**
     * magic
     */
    private U4 magic;

    /**
     * minor_version
     */
    private U2 minorVersion;

    /**
     * major_version
     */
    private U2 majorVersion;

    /**
     * constant_pool_count
     */
    private U2 constantPoolCount;

    /**
     * cp_info
     */
    private ConstantPoolInfo cpInfo;


    public void print() {
    }
}
