package com.somelogs.javase.javap.attribute;

import com.somelogs.javase.javap.datatype.U2;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Attribute: LineNumberTable
 *
 * @author LBG - 2018/1/17 0017
 */
@Getter
@Setter
public class LineNumberTableAttrInfo extends AttributeInfo {

    private int lineNumberTableLength;
    private List<LineNumberInfo> lineNumberInfoList;

    @Override
    public void readMore(InputStream inputStream) {
        U2 lineNumTableLengthU2 = U2.read(inputStream);
        lineNumberTableLength = lineNumTableLengthU2.getValue();
        lineNumberInfoList = new ArrayList<>(lineNumberTableLength);
        if (lineNumberTableLength > 0) {
            for (int i = 0; i < lineNumberTableLength; i++) {
                LineNumberInfo numberInfo = new LineNumberInfo();
                numberInfo.setStart(U2.read(inputStream).getValue());
                numberInfo.setLineNumber(U2.read(inputStream).getValue());
                lineNumberInfoList.add(numberInfo);
            }
        }
    }

    @Data
    private class LineNumberInfo {

        /**
         * byte code line number
         */
        private int start;

        /**
         * java source file line number
         */
        private int lineNumber;
    }
}
