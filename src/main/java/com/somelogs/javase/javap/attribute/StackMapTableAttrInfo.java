package com.somelogs.javase.javap.attribute;

import com.somelogs.javase.javap.datatype.U1;
import com.somelogs.javase.javap.datatype.U2;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.List;

/**
 * Attribute: StackMapTable
 *
 * @author LBG - 2018/1/17 0017
 */
@Getter
@Setter
public class StackMapTableAttrInfo extends AttributeInfo {

    private int entryNumber;
    private List<StackMapFrame> frameList;

    @Override
    public void readMore(InputStream inputStream) {
        entryNumber = U2.read(inputStream).getValue();
        for (int i = 0; i < attributeLength - 2; i++) {
            U1.read(inputStream);
        }
    }

    @Override
    public String getPrintContent() {
        return null;
    }

    @Data
    private class StackMapFrame {
    }
}
