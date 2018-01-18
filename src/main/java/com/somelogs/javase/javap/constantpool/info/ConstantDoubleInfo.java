package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U8;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_Double_Info
 *
 * @author LBG - 2018/1/15 0015 17:29
 */
@Getter
@Setter
public class ConstantDoubleInfo extends ConstantPoolInfo {

    private double value;

    @Override
    public void read(InputStream inputStream) {
        value = U8.read(inputStream).getValue();
    }
}
