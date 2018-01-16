package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_MethodType_info
 *
 * @author LBG - 2018/1/15 0015
 */
@Getter
@Setter
public class ConstantMethodTypeInfo extends ConstantPoolInfo {

    /**
     * descriptor index
     */
    private int descriptorIndex;

    @Override
    public void read(InputStream inputStream) {
        U2 index = U2.read(inputStream);
        descriptorIndex = index.getValue();
    }
}
