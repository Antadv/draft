package com.somelogs.utils.watermark;

import com.somelogs.utils.watermark.converter.Converter;
import com.somelogs.utils.watermark.converter.DctConverter;
import com.somelogs.utils.watermark.dencoder.Encoder;
import com.somelogs.utils.watermark.dencoder.TextEncoder;

/**
 * 描述
 *
 * @author LBG - 2019/7/21
 */
public class BlindWaterMarkTest {

    public static void main(String[] args) {
        Converter converter = new DctConverter();

        String sourceImg = "D:\\imant\\file\\pic\\bindingKey.png";
        String markText = "china red star";
        String destImg = "D:\\imant\\file\\pic\\bindingKeyDct.png";
        Encoder encoder = new TextEncoder(converter);
        encoder.encode(sourceImg, markText, destImg);
    }
}
