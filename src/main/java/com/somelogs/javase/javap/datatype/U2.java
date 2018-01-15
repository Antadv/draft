package com.somelogs.javase.javap.datatype;

import java.io.IOException;
import java.io.InputStream;

/**
 * u2
 *
 * @author LBG - 2018/1/15 0015 16:19
 */
public class U2 extends BaseType {

    public U2(int value) {
        super(value);
    }

    public static U2 read(InputStream inputStream) {
        byte[] bytes = new byte[2];
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int num = 0;
        for (byte b : bytes) {
            num <<= 8;
            num |= (b & 0xff);
        }
        return new U2(num);
    }
}
