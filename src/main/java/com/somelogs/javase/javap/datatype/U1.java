package com.somelogs.javase.javap.datatype;

import java.io.IOException;
import java.io.InputStream;

/**
 * u1
 *
 * @author LBG - 2018/1/15 0015 15:56
 */
public class U1 extends BaseType {

    public U1(int value) {
        super(value);
    }

    public static short read(InputStream input) {
        byte[] bytes = new byte[1];
        try {
            input.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (short) (bytes[0] & 0xFF);
    }
}
