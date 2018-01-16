package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_Methodref_info
 *
 * @author LBG - 2018/1/15 0015 17:39
 */
@Getter
@Setter
public class ConstantMethodRefInfo extends ConstantPoolInfo {

    /**
     * index point to CONSTANT_Class_info
     */
    private short classIndex;

    /**
     * index point to CONSTANT_NameAndType
     */
    private short descriptorIndex;

    @Override
    public void read(InputStream inputStream) {
        U2 classIndex = U2.read(inputStream);
        U2 descriptorIndex = U2.read(inputStream);
        this.classIndex = classIndex.getValue();
        this.descriptorIndex = descriptorIndex.getValue();
    }
}
