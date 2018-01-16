package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_Invoke_Dynamic_info
 *
 * @author LBG - 2018/1/15 0015
 */
@Setter
@Getter
public class ConstantInvokeDynamicInfo extends ConstantPoolInfo {

    /**
     * bootstrap_method_attr_index
     */
    private int bootstrapMethodAttrIndex;

    /**
     * name_and_type_index
     */
    private int nameAndTypeIndex;

    @Override
    public void read(InputStream inputStream) {
        U2 bootstrapIndex = U2.read(inputStream);
        U2 typeIndex = U2.read(inputStream);
        bootstrapMethodAttrIndex = bootstrapIndex.getValue();
        nameAndTypeIndex = typeIndex.getValue();
    }
}
