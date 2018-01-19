package com.somelogs.javase.javap.attribute;

import com.somelogs.javase.javap.constantpool.ConstantPool;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Attribute: ConstantValue
 * Constant value type can only be primitive type or String
 *
 * @author LBG - 2018/1/17 0017
 */
@Setter
@Getter
public class ConstantValueAttrInfo extends AttributeInfo {

    private String value;

    @Override
    public void readMore(InputStream inputStream) {
        short cpIndex = U2.read(inputStream).getValue();
        value = ConstantPool.getStringByIndex(cpIndex);
    }

    @Override
    public String getPrintContent() {
        return "{type=" + attributeName + ", value=" + value + "}";
    }
}
