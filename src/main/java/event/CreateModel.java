package event;


import file.FileTool;
import ij.ImagePlus;
import ij.measure.Calibration;
import ij.plugin.FolderOpener;
import surfaces.Create_Surfaces;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class CreateModel {

    private String path;
    private Calibration calibration;
    public List triangles;

    public CreateModel(String path,Calibration calibration){
        this.path = path;
        this.calibration=calibration;
    }

    public void run(){
        ImagePlus imagePlus = FolderOpener.open(path);
        imagePlus.setCalibration(calibration);
        FileTool.delete(new File(path));
        Create_Surfaces create_surfaces = new Create_Surfaces(imagePlus);
        triangles = create_surfaces.run();
        JOptionPane.showMessageDialog(null, "Создание модели выполнено");
    }
}
