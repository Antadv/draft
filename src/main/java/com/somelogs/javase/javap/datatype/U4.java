package com.somelogs.javase.javap.datatype;

import java.io.IOException;
import java.io.InputStream;

/**
 * u4
 *
 * @author LBG - 2018/1/15 0015 16:19
 */
public class U4 extends BaseType {

    public U4(int value) {
        super(value);
    }

    public static U4 read(InputStream inputStream) {
        byte[] bytes = new byte[4];
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int num = 0;
        for (byte b : bytes) {
            num <<= 8;
            num |= b & 0xFF;
        }
        return new U4(num);
    }
}
