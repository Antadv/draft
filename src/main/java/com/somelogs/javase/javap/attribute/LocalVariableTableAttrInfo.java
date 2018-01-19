package com.somelogs.javase.javap.attribute;

import com.somelogs.javase.javap.constantpool.ConstantPool;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Attribute: LocalVariableTable
 *
 * @author LBG - 2018/1/17 0017
 */
@Setter
@Getter
public class LocalVariableTableAttrInfo extends AttributeInfo {

    private int localVariableTableLength;
    private List<LocalVariableInfo> variableInfoList;

    @Override
    public void readMore(InputStream inputStream) {
        localVariableTableLength = U2.read(inputStream).getValue();
        variableInfoList = new ArrayList<>(localVariableTableLength);
        if (localVariableTableLength > 0) {
            for (int i = 0; i < localVariableTableLength; i++) {
                LocalVariableInfo info = new LocalVariableInfo();
                info.setStart(U2.read(inputStream).getValue());
                info.setLength(U2.read(inputStream).getValue());
                info.setName(ConstantPool.getStringByIndex(U2.read(inputStream).getValue()));
                info.setDescriptor(ConstantPool.getStringByIndex(U2.read(inputStream).getValue()));
                info.setIndex(U2.read(inputStream).getValue());
                variableInfoList.add(info);
            }
        }
    }

    @Override
    public String getPrintContent() {
        return null;
    }

    @Data
    private class LocalVariableInfo {
        private int start;
        private int length;
        private String name;
        private String descriptor;
        private int index;
    }
}
