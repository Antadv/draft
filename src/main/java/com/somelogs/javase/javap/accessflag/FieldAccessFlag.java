package com.somelogs.javase.javap.accessflag;

/**
 * Field access flag
 *
 * @author LBG - 2018/1/17 0017
 */
public class FieldAccessFlag extends AccessFlag {

    private static final short ACC_PRIVATE = 0x0002;
    private static final short ACC_PROTECTED = 0x0004;
    private static final short ACC_STATIC = 0x0008;
    private static final short ACC_VOLATILE = 0x0040;
    private static final short ACC_TRANSIENT = 0x0080;

    @Override
    public String getAccessFlags(short flag) {
        StringBuilder flags = new StringBuilder();
        if ((flag & ACC_PUBLIC) != 0) {
            flags.append("public ");
        } else if ((flag & ACC_PRIVATE) != 0) {
            flags.append("private ");
        } else if ((flag & ACC_PROTECTED) != 0) {
            flags.append("protected ");
        }

        if ((flag & ACC_STATIC) != 0) {
            flags.append("static ");
        }
        if ((flag & ACC_TRANSIENT) != 0) {
            flags.append("transient ");
        }

        if ((flag & ACC_FINAL) != 0) {
            flags.append("final ");
        } else if ((flag & ACC_VOLATILE) != 0) {
            flags.append("volatile ");
        }
        return flags.toString();
    }
}
