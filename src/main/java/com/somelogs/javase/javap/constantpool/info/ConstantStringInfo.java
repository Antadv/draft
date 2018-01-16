package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
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

    private int index;

    @Override
    public void read(InputStream inputStream) {

    }
}
