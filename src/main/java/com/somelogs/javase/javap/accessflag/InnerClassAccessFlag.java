package com.somelogs.javase.javap.accessflag;

/**
 * Inner class access flag
 *
 * @author LBG - 2018/1/18 0018
 */
public class InnerClassAccessFlag extends ClassAccessFlag {

    private static final short PRIVATE = 0x0002;

    @Override
    public String getAccessFlags(short flag) {
        String flags = super.getAccessFlags(flag);
        if ((flag & PRIVATE) != 0) {
            flags = flags + "ACC_PRIVATE";
        }
        return flags;
    }
}
