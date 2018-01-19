package com.somelogs.javase.javap.constantpool.info;

import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
import com.somelogs.javase.javap.datatype.U4;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Constant_Double_Info
 *
 * @author LBG - 2018/1/15 0015 17:29
 */
@Getter
@Setter
public class ConstantDoubleInfo extends ConstantPoolInfo {

    private long highValue;
    private long lowValue;

    @Override
    public void read(InputStream inputStream) {
        highValue = U4.read(inputStream).getValue();
        lowValue = U4.read(inputStream).getValue();
        // TODO 解析 Double
        // content = ?
    }
}
