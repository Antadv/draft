package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_Fieldref_info
 *
 * @author LBG - 2018/1/15 17:31
 */
@Getter
@Setter
public class ConstantFieldRefInfo extends ConstantPoolInfo {

    private int classIndex;
    private int descriptorIndex;

    @Override
    public void read(InputStream inputStream) {
        U2 classInfo = U2.read(inputStream);
        U2 descriptorInfo = U2.read(inputStream);
        classIndex = classInfo.getValue();
        descriptorIndex = descriptorInfo.getValue();
    }
}
