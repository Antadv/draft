package com.somelogs.javase.javap.attribute;

import com.somelogs.javase.javap.datatype.U1;
import com.somelogs.javase.javap.datatype.U2;
import com.somelogs.javase.javap.datatype.U4;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Attribute: Code
 *
 * @author LBG - 2018/1/17 0017
 */
@Setter
@Getter
@ToString
public class CodeAttrInfo extends AttributeInfo {

    private int maxStack;
    private int maxLocals;
    private int codeLength;
    private String[] opcodes;
    private List<ExceptionInfo> exceptionInfoList;
    private List<AttributeInfo> attributeInfoList;

    @Override
    public void readMore(InputStream inputStream) {
        U2 maxStackU2 = U2.read(inputStream);
        maxStack = maxStackU2.getValue();
        U2 maxLocalsU2 = U2.read(inputStream);
        maxLocals = maxLocalsU2.getValue();

        U4 codeLengthU4 = U4.read(inputStream);
        codeLength = codeLengthU4.getValue();
        opcodes = new String[codeLength];
        if (codeLength > 0) {
            for (int i = 0; i < codeLength; i++) {
                U1 opcode = U1.read(inputStream);
                opcodes[i] = opcode.getHexValue();
            }
        }

        U2 exceptionTable = U2.read(inputStream);
        exceptionInfoList = new ArrayList<>(exceptionTable.getValue());
        if (exceptionInfoList.size() > 0) {
            ExceptionInfo exceptionInfo = new ExceptionInfo();
            U2 startPcU2 = U2.read(inputStream);
            U2 endPcU2 = U2.read(inputStream);
            U2 handlerU2 = U2.read(inputStream);
            U2 typeU2 = U2.read(inputStream);
            exceptionInfo.setStart(startPcU2.getValue());
            exceptionInfo.setEnd(endPcU2.getValue());
            exceptionInfo.setHandler(handlerU2.getValue());
            exceptionInfo.setType(typeU2.getValue());
            exceptionInfoList.add(exceptionInfo);
        }

        short attrCount = U2.read(inputStream).getValue();
        attributeInfoList = new ArrayList<>(attrCount);
        for (int i = 0; i < attrCount; i++) {
            AttributeInfo attrInfo = AttributeInfo.readAttributeInfo(inputStream);
            attributeInfoList.add(attrInfo);
        }
    }

    @Override
    public String getPrintContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("{type=Code, ")
            .append("maxStack=").append(maxStack)
            .append(", maxLocals=").append(maxLocals)
            .append(", codeLength=").append(codeLength);

        sb.append(", opcodes=[");
        for (String opcode : opcodes) {
            sb.append(opcode).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("], ");
        sb.append("exceptions=[");
        for (ExceptionInfo info : exceptionInfoList) {
            sb.append(info.toString());
        }
        sb.append("], ");
        sb.append("attribute=[");
        for (AttributeInfo info : attributeInfoList) {
            sb.append(info.getPrintContent()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("]}");
        return sb.toString();
    }

    @Getter
    @Setter
    private class ExceptionInfo {
        private int start;
        private int end;
        private int handler;
        private int type;

        @Override
        public String toString() {
            return "{start=" + start + ", " +
                    "end=" + end + ", " +
                    "handler=" + handler + ", " +
                    "type=" + type + "}";
        }
    }
}
