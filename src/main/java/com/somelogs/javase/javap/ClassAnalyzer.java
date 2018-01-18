package com.somelogs.javase.javap;

import com.somelogs.javase.javap.accessflag.AccessFlag;
import com.somelogs.javase.javap.accessflag.ClassAccessFlag;
import com.somelogs.javase.javap.accessflag.FieldAccessFlag;
import com.somelogs.javase.javap.attribute.AttributeInfo;
import com.somelogs.javase.javap.constantpool.ConstantPool;
import com.somelogs.javase.javap.datatype.U2;
import com.somelogs.javase.javap.datatype.U4;
import com.somelogs.javase.javap.file.ClassInfo;
import com.somelogs.javase.javap.table.FieldTable;

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
        U2 interfaces = U2.read(inputStream);
        List<String> interfaceList = new ArrayList<>(interfaces.getValue());
        if (interfaceList.size() > 0) {
            for (int i = 0; i < interfaceList.size(); i++) {
                U2 interfaceRef = U2.read(inputStream);
                interfaceList.add(ConstantPool.getStringByIndex(interfaceRef.getValue()));
            }
        }
        classInfo.setInterfaceCount(interfaceList.size());
        classInfo.setInterfaceList(interfaceList);

        // field_info
        readFieldInfo(classInfo, inputStream);

        return classInfo;
    }

    private static void readFieldInfo(ClassInfo classInfo, InputStream inputStream) {
        U2 fieldCount = U2.read(inputStream);
        List<FieldTable> fieldList = new ArrayList<>(fieldCount.getValue());
        if (fieldList.size() > 0) {
            AccessFlag accessFlag = new FieldAccessFlag();
            for (int i = 0; i < fieldList.size(); i++) {
                FieldTable table = new FieldTable();
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
                U2 attributes = U2.read(inputStream);
                List<AttributeInfo> attributeList = new ArrayList<>(attributes.getValue());
                if (attributeList.size() > 0) {
                    for (int j = 0; j < attributeList.size(); j++) {
                        U2 attrNameIndex = U2.read(inputStream);
                        String attrName = ConstantPool.getStringByIndex(attrNameIndex.getValue());
                        AttributeInfo attributeInfo = AttributeInfo.getAttrByName(attrName);
                        attributeInfo.readMore(inputStream);
                        attributeList.add(attributeInfo);
                    }
                }
                table.setAttributeInfoList(attributeList);
            }
        }
        classInfo.setFieldTableList(fieldList);
    }
}
