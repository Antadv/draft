package com.somelogs.javase.javap.datatype;

import java.io.IOException;
import java.io.InputStream;

/**
 * u1 type
 *
 * @author LBG - 2018/1/15 0015 15:56
 */
public class U1 {

    private byte value;

    public U1(byte value) {
        this.value = value;
    }

    public static U1 read(InputStream input) {
        byte[] bytes = new byte[1];
        try {
            input.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new U1(bytes[0]);
    }

    public byte getValue() {
        return value;
    }

    public String getHexValue() {
        return Integer.toHexString(value & 0xFF);
    }
}
