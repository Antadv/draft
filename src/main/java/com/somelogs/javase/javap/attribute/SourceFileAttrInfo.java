package com.somelogs.javase.javap.attribute;

import com.somelogs.javase.javap.constantpool.ConstantPool;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Attribute: SourceFile
 *
 * @author LBG - 2018/1/17 0017
 */
@Getter
@Setter
public class SourceFileAttrInfo extends AttributeInfo {

    private String sourceFileName;

    @Override
    public void readMore(InputStream inputStream) {
        sourceFileName = ConstantPool.getStringByIndex(U2.read(inputStream).getValue());
    }

    @Override
    public String getPrintContent() {
        return null;
    }
}
