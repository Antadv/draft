package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U4;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_Integer_info
 *
 * @author LBG - 2018/1/15 0015 17:22
 */
@Setter
@Getter
public class ConstantIntegerInfo extends ConstantPoolInfo {

    private int value;

    @Override
    public void read(InputStream inputStream) {
        value = U4.read(inputStream).getValue();
    }
}
