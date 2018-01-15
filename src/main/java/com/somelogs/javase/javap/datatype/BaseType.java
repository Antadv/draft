package com.somelogs.javase.javap.datatype;

/**
 * 描述
 *
 * @author LBG - 2018/1/15 0015 17:06
 */
public abstract class BaseType {

    private int value;

    public BaseType(int value) {
        this.value = value;
    }

    public String toHexString() {
        return Integer.toHexString(value);
    }
}
