package com.somelogs.javase.javap.accessflag;

/**
 * common access flag
 *
 * @author LBG - 2018/1/18 0018
 */
public abstract class AccessFlag {

    static final short ACC_PUBLIC = 0x0001;
    static final short ACC_FINAL = 0x0010;
    static final short ACC_SYNTHETIC = 0x1000;
    static final short ACC_ENUM = 0x4000;

    public abstract String getAccessFlags(short flag);

}
