package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U4;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_Float_Info
 *
 * @author LBG - 2018/1/15 0015 17:24
 */
@Setter
@Getter
public class ConstantFloatInfo extends ConstantPoolInfo {

    private float value;

    @Override
    public void read(InputStream inputStream) {
        value = U4.read(inputStream).getValue();
        content = String.valueOf(value);
    }
}
