package com.somelogs.javase.javap;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U2;
import com.somelogs.javase.javap.datatype.U4;
import com.somelogs.javase.javap.file.ClassInfo;

import java.io.InputStream;

/**
 * class analyzer
 *
 * @author LBG - 2018/1/15 0015
 */
public class ClassAnalyzer {

    private ClassAnalyzer() {}

    public static ClassInfo analyzer(InputStream input) {
        ClassInfo classInfo = new ClassInfo();

        // magic
        U4 magic = U4.read(input);
        classInfo.setMagic(magic);

        // version
        U2 minorVersion = U2.read(input);
        U2 majorVersion = U2.read(input);
        classInfo.setMinorVersion(minorVersion);
        classInfo.setMajorVersion(majorVersion);

        // constant_pool_count
        U2 cpCount = U2.read(input);
        classInfo.setConstantPoolCount(cpCount);

        // constant_pool
        ConstantPoolInfo cpInfo = getConstantPoolInfo(input, Integer.valueOf(cpCount.toHexString()));
        classInfo.setCpInfo(cpInfo);

        return null;
    }

    /**
     * get constant pool info
     */
    private static ConstantPoolInfo getConstantPoolInfo(InputStream input, int cpCount) {
        int cpIndex = 1;
        return null;
    }
}
