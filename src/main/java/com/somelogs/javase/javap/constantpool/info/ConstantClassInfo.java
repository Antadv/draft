package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_Class_Info
 *
 * @author LBG - 2018/1/15 0015 17:29
 */
@Setter
@Getter
public class ConstantClassInfo extends ConstantPoolInfo {

    private short index;

    @Override
    public void read(InputStream inputStream) {
        U2 read = U2.read(inputStream);
        index = read.getValue();
    }
}
