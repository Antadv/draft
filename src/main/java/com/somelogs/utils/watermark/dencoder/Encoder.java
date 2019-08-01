package com.somelogs.utils.watermark.dencoder;

import com.somelogs.utils.watermark.converter.Converter;
import com.somelogs.utils.watermark.util.Utils;
import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

/**
 * 描述
 *
 * @author LBG - 2019/7/21
 */
public abstract class Encoder {

    Converter converter;

    Encoder(Converter converter) {
        this.converter = converter;
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public void encode(String image, String watermark, String output) {
        opencv_core.Mat src = Utils.read(image, CV_8S);

        opencv_core.MatVector color = new opencv_core.MatVector(3);
        split(src, color);

        for (int i = 0; i < color.size(); i++) {
            opencv_core.Mat com = this.converter.start(color.get(i));
            this.addWatermark(com, watermark);
            this.converter.inverse(com);
            color.put(i, com);
        }

        opencv_core.Mat res = new opencv_core.Mat();
        merge(color, res);

        if (res.rows() != src.rows() || res.cols() != src.cols()) {
            res = new opencv_core.Mat(res, new opencv_core.Rect(0, 0, src.size().width(), src.size().height()));
        }

        imwrite(output, res);
    }

    protected abstract void addWatermark(opencv_core.Mat com, String watermark);
}
