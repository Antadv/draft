package com.somelogs.utils.watermark.dencoder;

import com.somelogs.utils.watermark.converter.Converter;
import com.somelogs.utils.watermark.util.Utils;
import org.bytedeco.javacpp.opencv_core;

/**
 * 描述
 *
 * @author LBG - 2019/7/21
 */
public class TextEncoder extends Encoder {

    public TextEncoder(Converter converter) {
        super(converter);
    }

    @Override
    public void addWatermark(opencv_core.Mat com, String watermark) {
        if (Utils.isAscii(watermark)) {
            this.converter.addTextWatermark(com, watermark);
        } else {
            this.converter.addImageWatermark(com, Utils.drawNonAscii(watermark));
        }
    }
}
