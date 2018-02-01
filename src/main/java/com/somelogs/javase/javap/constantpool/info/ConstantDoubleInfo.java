package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Constant_Double_Info
 *
 * @author LBG - 2018/1/15 0015 17:29
 */
@Getter
@Setter
public class ConstantDoubleInfo extends ConstantPoolInfo {

    private long highValue;
    private long lowValue;

    @Override
    public void read(InputStream inputStream) {
        DataInputStream dataInput = new DataInputStream(inputStream);
        try {
            double value = dataInput.readDouble();
            content = String.valueOf(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
