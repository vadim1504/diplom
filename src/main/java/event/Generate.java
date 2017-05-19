package event;

import dicom.Convert;
import dicom.Information;
import file.FileTool;
import ij.ImagePlus;
import ij.ImageStack;
import ij.measure.Calibration;
import ij.plugin.FolderOpener;
import imgproc.Threshold;
import model.Slide;
import surfaces.Create_Surfaces;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Generate{

    private String path;
    private Information information = new Information();

    public Generate(String path) {
        this.path = path;
    }

    public void run() {
        ImagePlus imagePlus = FolderOpener.open(path);
        if (imagePlus != null) {
            Calibration calibration = imagePlus.getCalibration();
            ArrayList<Slide> bufferedImages = new ArrayList<>();
            Threshold threshold = new Threshold();
            BufferedImage bufferedImage;
            for (File f : FileTool.getFiles(path)) {
                bufferedImage = Convert.convert(f);
                bufferedImages.add(new Slide(information.getInstanceNumber(f), threshold.grabFrame(bufferedImage, 254, 255)));
            }
            bufferedImages.sort(Comparator.comparingInt(Slide::getId));
            String newPath = path + "\\trash";
            File folder_new = FileTool.newFolder(newPath);
            int i = 0;
            for (Slide b : bufferedImages) {
                try {
                    ImageIO.write(b.getBufferedImage(), "png", new File(newPath + "\\" + i + ".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                i++;
            }
            ImagePlus imagePlus2 = FolderOpener.open(newPath);
            FileTool.delete(folder_new);
            imagePlus2.setCalibration(calibration);
            Create_Surfaces create_surfaces = new Create_Surfaces(imagePlus2);
            create_surfaces.run(imagePlus.getTitle());
        }
    }
}
