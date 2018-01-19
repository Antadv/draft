package com.somelogs.javase.javap.attribute;

import com.somelogs.javase.javap.datatype.U2;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Attribute: Signature
 *
 * @author LBG - 2018/1/17 0017
 */
@Setter
@Getter
public class SignatureAttrInfo extends AttributeInfo {

    private short signatureIndex;

    @Override
    public void readMore(InputStream inputStream) {
        signatureIndex = U2.read(inputStream).getValue();
    }

    @Override
    public String getPrintContent() {
        return null;
    }
}
