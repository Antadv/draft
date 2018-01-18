package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * this class type contains three kinds of Constant info
 *
 *  <li>Constant_Fieldref_info</li>
 *  <li>Constant_Methodref_info</li>
 *  <li>Constant_Interface_Methodref_info</li>
 *
 * @author LBG - 2018/1/18 0018
 */
@Setter
@Getter
public class ConstantRefInfo extends ConstantPoolInfo {

    private short classIndex;
    private short descriptorIndex;

    @Override
    public void read(InputStream inputStream) {
        classIndex = U2.read(inputStream).getValue();
        descriptorIndex = U2.read(inputStream).getValue();
    }
}
