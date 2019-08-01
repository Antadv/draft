package com.somelogs.utils.watermark.dencoder;

import com.somelogs.utils.watermark.converter.Converter;
import com.somelogs.utils.watermark.util.Utils;

import static org.bytedeco.javacpp.opencv_core.CV_8U;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

/**
 * 描述
 *
 * @author LBG - 2019/7/21
 */
public class Decoder {

    private Converter converter;

    public Decoder(Converter converter) {
        this.converter = converter;
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public void decode(String image, String output) {
        imwrite(output, this.converter.showWatermark(this.converter.start(Utils.read(image, CV_8U))));
    }
}
