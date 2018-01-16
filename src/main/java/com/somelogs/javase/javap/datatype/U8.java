package com.somelogs.javase.javap.datatype;

import java.io.IOException;
import java.io.InputStream;

/**
 * u8
 *
 * @author LBG - 2018/1/15 0015 16:19
 */
public class U8 {

    private long value;

    public U8(long value) {
        this.value = value;
    }

    public static U8 read(InputStream inputStream) {
        byte[] bytes = new byte[8];
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value <<= 8;
            value |= bytes[i] & 0xFF;
        }
        U8 u8 = new U8(value);
        return u8;
    }

    public long getValue() {
        return value;
    }

    public String getHexValue() {
        return Long.toHexString(value);
    }
}
