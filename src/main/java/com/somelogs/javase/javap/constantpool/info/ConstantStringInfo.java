package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_String_Info
 *
 * @author LBG - 2018/1/15 0015 17:30
 */
@Setter
@Getter
public class ConstantStringInfo extends ConstantPoolInfo {

    private short index;

    @Override
    public void read(InputStream inputStream) {
        index = U2.read(inputStream).getValue();
    }
}
