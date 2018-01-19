package com.somelogs.javase.javap.file;

import com.somelogs.javase.javap.constantpool.ConstantPool;
import com.somelogs.javase.javap.datatype.U2;
import com.somelogs.javase.javap.datatype.U4;
import com.somelogs.javase.javap.table.FieldInfo;
import lombok.Data;

import java.util.List;

/**
 * class info
 *
 * @author LBG - 2018/1/15 0015 16:42
 */
@Data
public class ClassInfo {

    private U4 magic;
    private U2 minorVersion;
    private U2 majorVersion;
    private U2 constantPoolCount;
    private ConstantPool cpInfo;
    private String accessFlags;
    private String classFullyQualifiedName;
    private String superClassFullyQualifiedName;
    private List<String> interfaceList;
    private FieldInfo[] fieldInfoArray;

    public void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("magic: ").append(magic.getHexValue()).append("\n")
                .append("minor version: ").append(minorVersion.getValue()).append("\n")
                .append("major version: ").append(majorVersion.getValue()).append("\n")
                .append("Access flags: ").append(accessFlags).append("\n").append("\n")
                .append("Constant pool:").append("\n")
                .append(cpInfo.getContent()).append("\n")
                .append("Class FQN: ").append(classFullyQualifiedName).append("\n")
                .append("Super class FQN: ").append(superClassFullyQualifiedName).append("\n").append("\n");

        sb.append("Interfaces: ").append(interfaceList.size()).append("\n");
        for (String interfaceName : interfaceList) {
            sb.append(interfaceName).append("\n").append("\n");
        }
        sb.append("Fields Count: ").append(fieldInfoArray.length).append("\n");
        for (FieldInfo info : fieldInfoArray) {
            sb.append(info.toString()).append("\n");
        }
        System.out.println(sb.toString());
    }
}
