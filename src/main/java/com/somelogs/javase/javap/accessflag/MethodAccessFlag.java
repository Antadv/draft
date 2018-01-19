package com.somelogs.javase.javap.accessflag;

import lombok.Getter;
import lombok.Setter;

/**
 * method access flag
 *
 * @author LBG - 2018/1/19 0019
 */
@Getter
@Setter
public class MethodAccessFlag extends AccessFlag {

    public static final short ACC_PRIVATE = 0x0002;
    public static final short ACC_PROTECTED = 0x0004;
    public static final short ACC_STATIC = 0x0008;
    public static final short ACC_SYNCHRONIZED = 0x0020;
    public static final short ACC_BRIDGE = 0x0040;
    public static final short ACC_VARARGS = 0x0080;
    public static final short ACC_NATIVE = 0x0100;
    public static final short ACC_ABSTRACT = 0x0400;
    public static final short ACC_STRICTFP = 0x0800;

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

        if ((flag & ACC_ABSTRACT) != 0) {
            flags.append("abstract ");
        }
        if ((flag & ACC_STATIC) != 0) {
            flags.append("static ");
        }
        if ((flag & ACC_FINAL) != 0) {
            flags.append("final ");
        }
        if ((flag & ACC_SYNCHRONIZED) != 0) {
            flags.append("synchronized ");
        }
        if ((flag & ACC_BRIDGE) != 0) {
            flags.append("bridge ");
        }
        if ((flag & ACC_VARARGS) != 0) {
            flags.append("varargs ");
        }
        if ((flag & ACC_NATIVE) != 0) {
            flags.append("native ");
        }
        if ((flag & ACC_STRICTFP) != 0) {
            flags.append("strictfp ");
        }
        return flags.toString();
    }
}
