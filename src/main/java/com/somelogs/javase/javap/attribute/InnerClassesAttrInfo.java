package com.somelogs.javase.javap.attribute;

import com.somelogs.javase.javap.accessflag.InnerClassAccessFlag;
import com.somelogs.javase.javap.constantpool.ConstantPool;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Attribute: InnerClass
 *
 * @author LBG - 2018/1/17 0017
 */
@Setter
@Getter
public class InnerClassesAttrInfo extends AttributeInfo {

    private int classNumber;
    private InnerClassInfo[] innerClasses;

    @Override
    public void readMore(InputStream inputStream) {
        classNumber = U2.read(inputStream).getValue();
        innerClasses = new InnerClassInfo[classNumber];
        if (classNumber > 0) {
            for (int i = 0; i < classNumber; i++) {
                InnerClassInfo info = new InnerClassInfo();
                info.setInnerClassName(ConstantPool.getStringByIndex(U2.read(inputStream).getValue()));
                info.setOutClassName(ConstantPool.getStringByIndex(U2.read(inputStream).getValue()));
                info.setInnerName(ConstantPool.getStringByIndex(U2.read(inputStream).getValue()));
                info.setFlags(new InnerClassAccessFlag().getAccessFlags(U2.read(inputStream).getValue()));
                innerClasses[i] = info;
            }
        }
    }

    @Override
    public String getPrintContent() {
        return null;
    }

    @Data
    private class InnerClassInfo {
        private String innerClassName;
        private String outClassName;
        private String innerName;
        private String flags;
    }
}
