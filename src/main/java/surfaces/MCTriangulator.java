package surfaces;

import ij.ImagePlus;
import ij3d.Volume;
import marchingcubes.MCCube;

import javax.vecmath.Point3f;
import java.util.List;

public class MCTriangulator {

    public List<Point3f> getTriangles(ImagePlus image, int threshold) {

        Volume volume = new Volume(image);

        return MCCube.getTriangles(volume, threshold);
    }
}
