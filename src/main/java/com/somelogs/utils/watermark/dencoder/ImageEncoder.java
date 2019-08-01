package com.somelogs.utils.watermark.dencoder;

import com.somelogs.utils.watermark.converter.Converter;
import com.somelogs.utils.watermark.util.Utils;
import org.bytedeco.javacpp.opencv_core.Mat;

import static org.bytedeco.javacpp.opencv_core.CV_8U;

/**
 * 描述
 *
 * @author LBG - 2019/7/21
 */
public class ImageEncoder extends Encoder {

    public ImageEncoder(Converter converter) {
        super(converter);
    }

    @Override
    public void addWatermark(Mat com, String watermark) {
        this.converter.addImageWatermark(com, Utils.read(watermark, CV_8U));
    }

}
