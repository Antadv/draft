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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * class analyze
 *
 * @author LBG - 2018/1/15 0015
 */
public class ClassAnalyzer {

    private ClassAnalyzer() {}

    public static ClassInfo analyze(InputStream inputStream) {
        ClassInfo classInfo = new ClassInfo();

        // magic
        U4 magic = U4.read(inputStream);
        classInfo.setMagic(magic);

        // version
        U2 minorVersion = U2.read(inputStream);
        U2 majorVersion = U2.read(inputStream);
        classInfo.setMinorVersion(minorVersion);
        classInfo.setMajorVersion(majorVersion);

        // constant_pool_count
        U2 cpCount = U2.read(inputStream);
        classInfo.setConstantPoolCount(cpCount);

        // constant_pool
        ConstantPool constantPool = new ConstantPool(cpCount.getValue() - 1);
        constantPool.analyze(inputStream);
        classInfo.setCpInfo(constantPool);

        // access_flag
        U2 classAccessFlag = U2.read(inputStream);
        classInfo.setAccessFlags(new ClassAccessFlag().getAccessFlags(classAccessFlag.getValue()));

        // class_index, super_class_index
        U2 classIndex = U2.read(inputStream);
        U2 superClassIndex = U2.read(inputStream);
        String classFQN = ConstantPool.getStringByIndex(classIndex.getValue());
        String superFQN = ConstantPool.getStringByIndex(superClassIndex.getValue());
        classInfo.setClassFullyQualifiedName(classFQN);
        classInfo.setSuperClassFullyQualifiedName(superFQN);

        // interface list
        short interfaces = U2.read(inputStream).getValue();
        List<String> interfaceList = new ArrayList<>(interfaces);
        for (int i = 0; i < interfaces; i++) {
            U2 interfaceRef = U2.read(inputStream);
            interfaceList.add(ConstantPool.getStringByIndex(interfaceRef.getValue()));
        }
        classInfo.setInterfaceList(interfaceList);

        // field_info
        FieldMethodInfo[] fieldTable = readFieldOrMethodTable(inputStream, 1);
        classInfo.setFieldTable(fieldTable);

        // method_info
        FieldMethodInfo[] methodTable = readFieldOrMethodTable(inputStream, 2);
        classInfo.setMethodTable(methodTable);

        // attribute_info
        short attrCount = U2.read(inputStream).getValue();
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
     * @param inputStream {@link InputStream}
     * @param type 1 field, 2 method.
     * @return table
     */
    private static FieldMethodInfo[] readFieldOrMethodTable(InputStream inputStream, int type) {
        short fieldCount = U2.read(inputStream).getValue();
        FieldMethodInfo[] fieldMethodInfoArray = new FieldMethodInfo[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            FieldMethodInfo table = new FieldMethodInfo();
            AccessFlag accessFlag;
            if (type == 1) {
                accessFlag = new FieldAccessFlag();
            } else {
                accessFlag = new MethodAccessFlag();
            }
            U2 accessFlagU2 = U2.read(inputStream);
            String flagDesc = accessFlag.getAccessFlags(accessFlagU2.getValue());
            table.setAccessFlag(flagDesc);

            U2 nameIndex = U2.read(inputStream);
            String simpleName = ConstantPool.getStringByIndex(nameIndex.getValue());
            table.setSimpleName(simpleName);

            U2 descriptorIndex = U2.read(inputStream);
            String descriptor = ConstantPool.getStringByIndex(descriptorIndex.getValue());
            table.setDescriptor(descriptor);

            // attribute_info
            short attrCount = U2.read(inputStream).getValue();
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
