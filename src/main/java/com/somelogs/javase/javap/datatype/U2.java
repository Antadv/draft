package com.somelogs.javase.javap.datatype;

import java.io.IOException;
import java.io.InputStream;

/**
 * u2
 *
 * @author LBG - 2018/1/15 0015 16:19
 */
public class U2 {

    private short value;

    public U2(short value) {
        this.value = value;
    }

    public static U2 read(InputStream inputStream) {
        byte[] bytes = new byte[2];
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        short num = 0;
        for (byte b : bytes) {
            num <<= 8;
            num |= (b & 0xff);
        }
        return new U2((short) (num & 0xFFFF));
    }

    public short getValue() {
        return value;
    }

    public String getHexValue() {
        return Integer.toHexString(value & 0xFFFF);
    }
}
