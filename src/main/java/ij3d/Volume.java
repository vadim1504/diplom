package ij3d;

import ij.ImagePlus;
import ij.measure.Calibration;
import java.awt.image.IndexColorModel;
import javax.vecmath.Point3d;

public class Volume {

    private int[] rLUT = new int[256];
    private int[] gLUT = new int[256];
    private int[] bLUT = new int[256];
    private int[] aLUT = new int[256];

    private ByteImage image;

    private IntLoader loader;

    public int xDim, yDim, zDim;

    public double pw, ph, pd;

    public final Point3d minCoord = new Point3d();

    private final Point3d maxCoord = new Point3d();

    public Volume(ImagePlus imp) {
        setImage(imp);
    }

    private void setImage(ImagePlus imp) {
        image = new ByteImage(imp);
        IndexColorModel cm = (IndexColorModel)imp.getProcessor().getCurrentColorModel();
        for(int i = 0; i < 256; i++) {
            rLUT[i] = cm.getRed(i);
            gLUT[i] = cm.getGreen(i);
            bLUT[i] = cm.getBlue(i);
            aLUT[i] = Math.min(254, (rLUT[i] + gLUT[i] + bLUT[i]) / 3);
        }
        xDim = imp.getWidth();
        yDim = imp.getHeight();
        zDim = imp.getStackSize();
        Calibration c = imp.getCalibration();
        pw = c.pixelWidth;
        ph = c.pixelHeight;
        pd = c.pixelDepth;
        float xSpace = (float)pw;
        float ySpace = (float)ph;
        float zSpace = (float)pd;
        minCoord.x = c.xOrigin;
        minCoord.y = c.yOrigin;
        minCoord.z = c.zOrigin;
        maxCoord.x = minCoord.x + xDim * xSpace;
        maxCoord.y = minCoord.y + yDim * ySpace;
        maxCoord.z = minCoord.z + zDim * zSpace;
        loader = new IntLoader(image);
    }


    public int load(int x, int y, int z) {
        try {
            return loader.load(x, y, z);
        } catch(NullPointerException e) {
            throw new RuntimeException("No image. Maybe it is swapped");
        }
    }

}


