package com.somelogs.javase.javap.constantpool;

import com.somelogs.javase.javap.constantpool.info.*;
import com.somelogs.javase.javap.datatype.U1;
import lombok.Data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * constant pool
 *
 * @author LBG - 2018/1/15 0015 17:19
 */
@Data
public class ConstantPool {

    private int cpCount;
    private static List<ConstantPoolInfo> cpInfoList;

    public ConstantPool(int cpCount) {
        this.cpCount = cpCount;
        cpInfoList = new ArrayList<>(cpCount);
    }

    public static String getStringByIndex(short index) {
        return cpInfoList.get(index - 1).getContent();
    }

    public String getContent() {
        StringBuilder builder = new StringBuilder();
        ConstantPoolInfo info;
        for (int i = 0, size = cpInfoList.size(); i < size; i++) {
            info = cpInfoList.get(i);
            builder.append("#")
                    .append(i + 1)
                    .append("=")
                    .append(ConstantTypeEnum.getNameByTag(info.getTag()))
                    .append(" ")
                    .append(info.getContent())
                    .append("\n");
        }
        return builder.toString();
    }

    public void analyze(InputStream inputStream) {
        addCpInfo2List(inputStream);
        parseIndexWithConstant();
        parseIndexWithRef();
    }

    private void addCpInfo2List(InputStream inputStream) {
        for (int i = 1; i <= cpCount; i++) {
            System.out.println(i);
            U1 tag = U1.read(inputStream);
            ConstantPoolInfo info = ConstantPoolInfo.getCpInfoByTag(tag.getValue());
            info.setTag(tag.getValue());
            info.read(inputStream);
            cpInfoList.add(info);
        }
    }

    private void parseIndexWithConstant() {
        for (ConstantPoolInfo poolInfo : cpInfoList) {
            if (poolInfo instanceof ConstantClassInfo) {
                ConstantClassInfo info = (ConstantClassInfo) poolInfo;
                info.setContent(getStringByIndex(info.getIndex()));
            } else if (poolInfo instanceof ConstantStringInfo) {
                ConstantStringInfo info = (ConstantStringInfo) poolInfo;
                info.setContent(getStringByIndex(info.getIndex()));
            } else if (poolInfo instanceof ConstantNameAndTypeInfo) {
                ConstantNameAndTypeInfo info = (ConstantNameAndTypeInfo) poolInfo;
                String simpleName = getStringByIndex(info.getConstantIndex());
                String descriptor = getStringByIndex(info.getDescriptorIndex());
                info.setContent(simpleName + ":" + descriptor);
            }
        }
    }

    private void parseIndexWithRef() {
        for (ConstantPoolInfo poolInfo : cpInfoList) {
            if (poolInfo instanceof ConstantRefInfo) {
                ConstantRefInfo info = (ConstantRefInfo) poolInfo;
                String classDesc = getStringByIndex(info.getClassIndex());
                String descriptor = getStringByIndex(info.getDescriptorIndex());
                poolInfo.setContent(classDesc + "." + descriptor);

            } else if (poolInfo instanceof ConstantMethodTypeInfo) {

            } else if (poolInfo instanceof ConstantMethodHandleInfo) {

            } else if (poolInfo instanceof ConstantInvokeDynamicInfo) {

            }
        }
    }
}

