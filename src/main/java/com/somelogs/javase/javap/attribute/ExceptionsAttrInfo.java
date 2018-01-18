package com.somelogs.javase.javap.attribute;

import com.somelogs.javase.javap.constantpool.ConstantPool;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Attribute: Exceptions
 * exceptions throw in method signature
 *
 * @author LBG - 2018/1/17 0017
 */
@Setter
@Getter
public class ExceptionsAttrInfo extends AttributeInfo {

    private int exceptionNumber;
    private ExceptionIndexTable[] exceptionTable;

    @Override
    public void readMore(InputStream inputStream) {
        exceptionNumber = U2.read(inputStream).getValue();
        exceptionTable = new ExceptionIndexTable[exceptionNumber];
        if (exceptionNumber > 0) {
            for (int i = 0; i < exceptionNumber; i++) {
                ExceptionIndexTable table = new ExceptionIndexTable();
                String exceptionName = ConstantPool.getStringByIndex(U2.read(inputStream).getValue());
                table.setExceptionName(exceptionName);
                exceptionTable[i] = table;
            }
        }
    }

    @Data
    private class ExceptionIndexTable {
        private String exceptionName;
    }
}
