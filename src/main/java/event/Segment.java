package event;

import dicom.Convert;
import dicom.Information;
import file.FileTool;
import ij.ImagePlus;
import ij.measure.Calibration;
import ij.plugin.FolderOpener;
import imgproc.Threshold;
import model.Slide;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Segment {

    private String path;
    private Threshold threshold = new Threshold();
    private Convert convert = new Convert();
    private Information information = new Information();
    public Calibration calibration;
    public String newPath;

    public Segment(String path) {
        this.path = path;
    }

    public void run(){
        ImagePlus imagePlus = FolderOpener.open(path);
        if (imagePlus != null) {
            try {
                calibration = imagePlus.getCalibration();
                BufferedImage bufferedImage;
                ArrayList<Slide> bufferedImages = new ArrayList<>();

                for (File f : FileTool.getFiles(path)) {
                    bufferedImage = convert.convert(f);
                    bufferedImages.add(new Slide(information.getInstanceNumber(f), threshold.grabFrame(bufferedImage, 254, 255)));
                }
                bufferedImages.sort(Comparator.comparingInt(Slide::getId).reversed());
                newPath = path + "\\segment";
                FileTool.newFolder(newPath);
                int i = 0;
                for (Slide b : bufferedImages) {
                    try {
                        ImageIO.write(b.getBufferedImage(), "png", new File(newPath + "\\" + i + ".png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                JOptionPane.showMessageDialog(null, "Сегментация исходных данных произведена успешна");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Во время выпонения сегментация произошла ошибка, попробуйте ещё раз");
                e.printStackTrace();
            }
        }
    }
}
