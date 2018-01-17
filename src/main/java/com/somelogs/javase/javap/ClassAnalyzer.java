package com.somelogs.javase.javap;

import com.somelogs.javase.javap.accessflag.ClassAccessFlag;
import com.somelogs.javase.javap.accessflag.FieldAccessFlag;
import com.somelogs.javase.javap.attribute.AttributeInfo;
import com.somelogs.javase.javap.constantpool.ConstantPool;
import com.somelogs.javase.javap.constantpool.ConstantPoolInfo;
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
        int cpActualCount = cpCount.getValue() - 1;
        ConstantPool constantPool = new ConstantPool(cpActualCount);
        constantPool.analyze(inputStream);
        classInfo.setCpInfo(constantPool);
        List<ConstantPoolInfo> cpInfoList = constantPool.getCpInfoList();

        // access_flag
        U2 read = U2.read(inputStream);
        classInfo.setAccessFlags(ClassAccessFlag.getAccessFlags(read.getValue()));

        // class_index, super_class_index
        U2 classIndex = U2.read(inputStream);
        U2 superClassIndex = U2.read(inputStream);
        String classFQN = cpInfoList.get(classIndex.getValue() - 1).getContent();
        String superFQN = cpInfoList.get(superClassIndex.getValue() - 1).getContent();
        classInfo.setClassFullyQualifiedName(classFQN);
        classInfo.setSuperClassFullyQualifiedName(superFQN);

        // interface list
        U2 interfaces = U2.read(inputStream);
        List<String> interfaceList = new ArrayList<>(interfaces.getValue());
        if (interfaceList.size() > 0) {
            for (int i = 0; i < interfaceList.size(); i++) {
                U2 interfaceRef = U2.read(inputStream);
                interfaceList.add(cpInfoList.get(interfaceRef.getValue() - 1).getContent());
            }
        }
        classInfo.setInterfaceCount(interfaceList.size());
        classInfo.setInterfaceList(interfaceList);

        // field_info
        U2 fieldCount = U2.read(inputStream);
        List<FieldTable> fieldList = new ArrayList<>(fieldCount.getValue());
        if (fieldList.size() > 0) {
            for (int i = 0; i < fieldList.size(); i++) {
                FieldTable table = new FieldTable();
                U2 accessFlag = U2.read(inputStream);
                String acc = FieldAccessFlag.getAccessFlags(accessFlag.getValue());
                table.setAccessFlag(acc);

                U2 nameIndex = U2.read(inputStream);
                String simpleName = cpInfoList.get(nameIndex.getValue() - 1).getContent();
                table.setSimpleName(simpleName);

                U2 descriptorIndex = U2.read(inputStream);
                String descriptor = cpInfoList.get(descriptorIndex.getValue() - 1).getContent();
                table.setDescriptor(descriptor);

                // attribute_info
                U2 attributes = U2.read(inputStream);
                List<AttributeInfo> attributeList = new ArrayList<>(attributes.getValue());
                if (attributeList.size() > 0) {
                    for (int j = 0; j < attributeList.size(); j++) {
                        U2 attrNameIndex = U2.read(inputStream);
                        String attrName = cpInfoList.get(attrNameIndex.getValue() - 1).getContent();
                        AttributeInfo attributeInfo = AttributeInfo.getAttrByName(attrName);
                        attributeInfo.analyze(inputStream);
                        attributeList.add(attributeInfo);
                    }
                }
                table.setAttributeInfoList(attributeList);
            }
        }


        return classInfo;
    }
}
