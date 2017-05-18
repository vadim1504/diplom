package imgproc;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.awt.image.BufferedImage;

public class Threshold {

    private OpenCVFrameConverter.ToIplImage cv;
    private Java2DFrameConverter jcv;

    public BufferedImage grabFrame(BufferedImage bf, int min, int max) {
        cv = new OpenCVFrameConverter.ToIplImage();
        jcv = new Java2DFrameConverter();
        Mat frame  = matify(bf);
        frame = threshold(frame,min,max);
        return mat2Image(frame);
    }

    private Mat threshold(Mat mat,int min,int max) {
        Mat grayImage = new Mat();
        opencv_imgproc.threshold(mat, grayImage, min, max, opencv_imgproc.CV_THRESH_BINARY);
        return grayImage;
    }

    private BufferedImage mat2Image(Mat frame) {
        return jcv.convert(cv.convert(frame));
    }

    private Mat matify(BufferedImage im) {
        return cv.convertToMat(jcv.convert(im));
    }

}