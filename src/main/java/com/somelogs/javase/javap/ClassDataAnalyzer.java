package com.somelogs.javase.javap;

import com.somelogs.javase.javap.accessflag.AccessFlag;
import com.somelogs.javase.javap.accessflag.ClassAccessFlag;
import com.somelogs.javase.javap.accessflag.FieldAccessFlag;
import com.somelogs.javase.javap.accessflag.MethodAccessFlag;
import com.somelogs.javase.javap.attribute.AttributeInfo;
import com.somelogs.javase.javap.constantpool.ConstantPool;
import com.somelogs.javase.javap.datatype.U2;
import com.somelogs.javase.javap.datatype.U4;
import com.somelogs.javase.javap.file.ClassInfo;
import com.somelogs.javase.javap.table.FieldMethodInfo;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * class file analyzer via DataInputStream
 *
 * @author LBG - 2018/1/30 0030
 */
public class ClassDataAnalyzer {

    public static ClassInfo analyze(DataInputStream inputStream) throws IOException {
        ClassInfo classInfo = new ClassInfo();

        // magic
        int magic = inputStream.readInt();
        classInfo.setMagic(new U4(magic));

        // version
        classInfo.setMinorVersion(new U2(inputStream.readShort()));
        classInfo.setMajorVersion(new U2(inputStream.readShort()));

        // constant_pool_count
        short cpCount = inputStream.readShort();
        classInfo.setConstantPoolCount(new U2(cpCount));

        // constant_pool
        ConstantPool constantPool = new ConstantPool(cpCount - 1);
        constantPool.analyze(inputStream);
        classInfo.setCpInfo(constantPool);

        // access_flag
        short classAccessFlag = inputStream.readShort();
        classInfo.setAccessFlags(new ClassAccessFlag().getAccessFlags(classAccessFlag));

        // class_index, super_class_index
        String classFQN = ConstantPool.getStringByIndex(inputStream.readShort());
        String superFQN = ConstantPool.getStringByIndex(inputStream.readShort());
        classInfo.setClassFullyQualifiedName(classFQN);
        classInfo.setSuperClassFullyQualifiedName(superFQN);

        // interface list
        short interfaces = inputStream.readShort();
        List<String> interfaceList = new ArrayList<>(interfaces);
        for (int i = 0; i < interfaces; i++) {
            interfaceList.add(ConstantPool.getStringByIndex(inputStream.readShort()));
        }
        classInfo.setInterfaceList(interfaceList);

        // field_info
        FieldMethodInfo[] fieldTable = readFieldOrMethodTable(inputStream, 1);
        classInfo.setFieldTable(fieldTable);

        // method_info
        FieldMethodInfo[] methodTable = readFieldOrMethodTable(inputStream, 2);
        classInfo.setMethodTable(methodTable);

        // attribute_info
        short attrCount = inputStream.readShort();
        AttributeInfo[] attrTable = new AttributeInfo[attrCount];
        for (int j = 0; j < attrCount; j++) {
            AttributeInfo attrInfo = AttributeInfo.readAttributeInfo(inputStream);
            attrTable[j] = attrInfo;
        }
        classInfo.setAttrTable(attrTable);

        return classInfo;
    }

    /**
     * read field or method table
     *
     * @param inputStream {@link DataInputStream}
     * @param type 1 field, 2 method.
     * @return table
     */
    private static FieldMethodInfo[] readFieldOrMethodTable(DataInputStream inputStream, int type) throws IOException {
        short fieldCount = inputStream.readShort();
        FieldMethodInfo[] fieldMethodInfoArray = new FieldMethodInfo[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            FieldMethodInfo table = new FieldMethodInfo();
            AccessFlag accessFlag;
            if (type == 1) {
                accessFlag = new FieldAccessFlag();
            } else {
                accessFlag = new MethodAccessFlag();
            }
            String flagDesc = accessFlag.getAccessFlags(inputStream.readShort());
            table.setAccessFlag(flagDesc);

            String simpleName = ConstantPool.getStringByIndex(inputStream.readShort());
            table.setSimpleName(simpleName);

            String descriptor = ConstantPool.getStringByIndex(inputStream.readShort());
            table.setDescriptor(descriptor);

            // attribute_info
            short attrCount = inputStream.readShort();
            List<AttributeInfo> attributeList = new ArrayList<>(attrCount);
            for (int j = 0; j < attrCount; j++) {
                AttributeInfo attrInfo = AttributeInfo.readAttributeInfo(inputStream);
                attributeList.add(attrInfo);
            }
            table.setAttributeInfoList(attributeList);
            fieldMethodInfoArray[i] = table;
        }
        return fieldMethodInfoArray;
    }
}
