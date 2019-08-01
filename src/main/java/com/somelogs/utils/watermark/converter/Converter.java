package com.somelogs.utils.watermark.converter;

import org.bytedeco.javacpp.opencv_core.Mat;

/**
 * 描述
 *
 * @author LBG - 2019/7/21
 */
public interface Converter {
    Mat start(Mat src);
    void inverse(Mat com);
    void addTextWatermark(Mat com, String watermark);
    void addImageWatermark(Mat com, Mat watermark);
    Mat showWatermark(Mat src);
}
