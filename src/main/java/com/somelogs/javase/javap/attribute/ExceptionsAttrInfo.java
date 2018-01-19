package com.somelogs.javase.javap.attribute;

import com.somelogs.javase.javap.constantpool.ConstantPool;
import com.somelogs.javase.javap.datatype.U2;
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

    @Override
    public String getPrintContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("{type=Exceptions, exceptions=[");
        for (ExceptionIndexTable exp : exceptionTable) {
            sb.append(exp).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("]}");
        return sb.toString();
    }

    @Getter
    @Setter
    private class ExceptionIndexTable {
        private String exceptionName;

        @Override
        public String toString() {
            return exceptionName;
        }
    }
}
