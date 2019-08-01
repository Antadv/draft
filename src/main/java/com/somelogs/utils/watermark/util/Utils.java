package com.somelogs.utils.watermark.util;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_highgui;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

/**
 * 描述
 *
 * @author LBG - 2019/7/21
 */
public class Utils {

    public static Mat read(String image, int type) {
        Mat src = imread(image, type);
        if (src.empty()) {
            System.out.println("File not found!");
            System.exit(-1);
        }
        return src;
    }

    public static void show(Mat mat) {
        opencv_highgui.imshow(Utils.class.toString(), mat);
        opencv_highgui.waitKey(-1);
    }

    public static Mat optimalDft(Mat srcImg) {
        Mat padded = new Mat();
        int opRows = getOptimalDFTSize(srcImg.rows());
        int opCols = getOptimalDFTSize(srcImg.cols());
        copyMakeBorder(srcImg, padded, 0, opRows - srcImg.rows(),
                0, opCols - srcImg.cols(), BORDER_CONSTANT, Scalar.all(0));
        return padded;
    }

    public static boolean isAscii(String str) {
        return str.matches("^[ -~]+$");
    }

    public static Mat drawNonAscii(String watermark) {
        Font font = new Font("Default", Font.PLAIN, 64);
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = 0;
        for (int i = 0; i < watermark.length(); i++) {
            width += metrics.charWidth(watermark.charAt(i));
        }
        int height = metrics.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        graphics.drawString(watermark, 0, metrics.getAscent());
        graphics.dispose();
        byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        return new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CV_8U, new BytePointer(pixels));
    }

    public static void fixSize(Mat src, Mat mirror) {
        if (src.rows() != mirror.rows()) {
            copyMakeBorder(src, src, 0, mirror.rows() - src.rows(),
                    0, 0, BORDER_CONSTANT, Scalar.all(0));
        }
        if (src.cols() != mirror.cols()) {
            copyMakeBorder(src, src, 0, 0,
                    0, mirror.cols() - src.cols(), BORDER_CONSTANT, Scalar.all(0));
        }
    }
}
