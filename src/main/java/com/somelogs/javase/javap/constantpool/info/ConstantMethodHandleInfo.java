package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U1;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_Method_Handle_info
 *
 * @author LBG - 2018/1/15 0015
 */
@Getter
@Setter
public class ConstantMethodHandleInfo extends ConstantPoolInfo {

    /**
     * reference kind
     */
    private byte referenceKind;

    /**
     * reference index
     */
    private short referenceIndex;

    @Override
    public void read(InputStream inputStream) {
        U1 kind = U1.read(inputStream);
        U1 index = U1.read(inputStream);
        referenceKind = kind.getValue();
        referenceIndex = index.getValue();
    }
}
