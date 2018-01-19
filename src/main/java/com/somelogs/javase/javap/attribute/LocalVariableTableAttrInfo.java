package com.somelogs.javase.javap.attribute;

import com.somelogs.javase.javap.constantpool.ConstantPool;
import com.somelogs.javase.javap.datatype.U2;
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
        StringBuilder sb = new StringBuilder();
        sb.append("{type=LocalVariableTable, LocalVariableInfo=[");
        for (LocalVariableInfo info : variableInfoList) {
            sb.append(info.toString()).append(", ");
        }
        if (variableInfoList.size() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("]}");
        return sb.toString();
    }

    @Setter
    @Getter
    private class LocalVariableInfo {
        private int start;
        private int length;
        private String name;
        private String descriptor;
        private int index;

        @Override
        public String toString() {
            return "{start=" + start + ", " +
                    "length=" + length + ", " +
                    "name=" + name + ", " +
                    "descriptor=" + descriptor + ", " +
                    "index=" + index + "}";
        }
    }
}
