package com.somelogs.utils.watermark.converter;

import com.somelogs.utils.watermark.util.Utils;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.CV_FONT_HERSHEY_COMPLEX;
import static org.bytedeco.javacpp.opencv_imgproc.putText;

/**
 * 描述
 *
 * @author LBG - 2019/7/21
 */
public class DftConverter implements Converter {

    @Override
    public Mat start(Mat src) {
        src.convertTo(src, CV_32F);
        opencv_core.MatVector planes = new opencv_core.MatVector(2);
        Mat com = new Mat();
        planes.put(0, src);
        planes.put(1, Mat.zeros(src.size(), CV_32F).asMat());
        merge(planes, com);
        dft(com, com);
        return com;
    }

    @Override
    public void inverse(Mat com) {
        MatVector planes = new MatVector(2);
        idft(com, com);
        split(com, planes);
        normalize(planes.get(0), com, 0, 255, NORM_MINMAX, CV_8UC3, null);
    }

    @Override
    public void addTextWatermark(Mat com, String watermark) {
        Scalar s = new Scalar(0, 0, 0, 0);
        Point p = new Point(com.cols() / 3, com.rows() / 3);
        putText(com, watermark, p, CV_FONT_HERSHEY_COMPLEX, 1.0, s, 3,
                8, false);
        flip(com, com, -1);
        putText(com, watermark, p, CV_FONT_HERSHEY_COMPLEX, 1.0, s, 3,
                8, false);
        flip(com, com, -1);
    }

    @Override
    public void addImageWatermark(Mat com, Mat watermark) {
        MatVector planes = new MatVector(2);
        watermark.convertTo(watermark, CV_32F);
        Mat temp = new Mat();
        int col = (com.cols() - watermark.cols()) >> 1;
        int row = ((com.rows() >> 1) - watermark.rows()) >> 1;
        copyMakeBorder(watermark, watermark, row, row, col, col, BORDER_CONSTANT, Scalar.all(0));
        planes.put(0, watermark);
        flip(watermark, temp, -1);
        planes.put(1, temp);
        vconcat(planes, watermark);

        planes.put(0, watermark);
        planes.put(1, watermark);
        merge(planes, watermark);
        Utils.fixSize(watermark, com);
        addWeighted(watermark, 8, com, 1, 0.0, com);

        split(com, planes);
    }

    @Override
    public Mat showWatermark(Mat src) {
        MatVector newPlanes = new MatVector(2);
        Mat mag = new Mat();
        split(src, newPlanes);
        magnitude(newPlanes.get(0), newPlanes.get(1), mag);
        add(Mat.ones(mag.size(), CV_32F).asMat(), mag, mag);
        log(mag, mag);
        mag.convertTo(mag, CV_8UC1);
        normalize(mag, mag, 0, 255, NORM_MINMAX, CV_8UC1, null);
        return mag;
    }
}
