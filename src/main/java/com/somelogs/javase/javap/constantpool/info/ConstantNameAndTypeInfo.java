package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_NameAndType_info
 *
 * @author LBG - 2018/1/15 0015 17:42
 */
@Getter
@Setter
public class ConstantNameAndTypeInfo extends ConstantPoolInfo {

    /**
     * constant index
     */
    private int constantIndex;

    /**
     * descriptor index
     */
    private int descriptorIndex;

    @Override
    public void read(InputStream inputStream) {
        U2 constant = U2.read(inputStream);
        U2 descriptor = U2.read(inputStream);
        constantIndex = constant.getValue();
        descriptorIndex = descriptor.getValue();
    }
}
