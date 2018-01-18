package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U8;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_Long_info
 *
 * @author LBG - 2018/1/15 0015
 */
@Getter
@Setter
public class ConstantLongInfo extends ConstantPoolInfo {

    private long value;

    @Override
    public void read(InputStream inputStream) {
        value = U8.read(inputStream).getValue();
    }
}
