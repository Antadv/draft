package com.somelogs.javase.javap.attribute;

import java.io.InputStream;

/**
 * Attribute: Synthetic
 *
 * @author LBG - 2018/1/17 0017
 */
public class SyntheticAttrInfo extends AttributeInfo {

    @Override
    public void readMore(InputStream inputStream) {
    }

    @Override
    public String getPrintContent() {
        return null;
    }
}
