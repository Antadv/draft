package com.somelogs.utils.watermark.converter;

import com.somelogs.utils.watermark.util.Utils;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * 描述
 *
 * @author LBG - 2019/7/21
 */
public class DctConverter implements Converter {

    @Override
    public Mat start(Mat src) {
        if ((src.cols() & 1) != 0) {
            copyMakeBorder(src, src, 0, 0, 0, 1, BORDER_CONSTANT, opencv_core.Scalar.all(0));
        }
        if ((src.rows() & 1) != 0) {
            copyMakeBorder(src, src, 0, 1, 0, 0, BORDER_CONSTANT, opencv_core.Scalar.all(0));
        }
        src.convertTo(src, CV_32F);
        dct(src, src);
        return src;
    }

    @Override
    public void inverse(Mat com) {
        idct(com, com);
    }

    @Override
    public void addTextWatermark(Mat com, String watermark) {
        putText(com, watermark,
                new Point(com.cols() >> 2, com.rows() >> 2),
                CV_FONT_HERSHEY_COMPLEX, 2.0,
                new Scalar(2, 2, 2, 0), 2, 8, false);
    }

    @Override
    public void addImageWatermark(Mat com, Mat watermark) {
        Mat mask = new Mat();
        inRange(watermark, new Mat(new Scalar(0, 0, 0, 0)), new Mat(new Scalar(0, 0, 0, 0)), mask);
        Mat i2 = new Mat(watermark.size(), watermark.type(), new Scalar(2, 2, 2, 0));
        i2.copyTo(watermark, mask);
        watermark.convertTo(watermark, CV_32F);
        int row = (com.rows() - watermark.rows()) >> 1;
        int col = (com.cols() - watermark.cols()) >> 1;
        copyMakeBorder(watermark, watermark, row, row, col, col, BORDER_CONSTANT, Scalar.all(0));
        Utils.fixSize(watermark, com);
        addWeighted(watermark, 0.03, com, 1, 0.0, com);
    }

    @Override
    public Mat showWatermark(Mat src) {
        src.convertTo(src, COLOR_RGB2HSV);
        inRange(src, new Mat(new Scalar(0, 0, 0, 0)), new Mat(new Scalar(16, 16, 16, 0)), src);
        normalize(src, src, 0, 255, NORM_MINMAX, CV_8UC1, null);
        return src;
    }
}
