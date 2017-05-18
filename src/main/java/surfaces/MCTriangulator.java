package surfaces;

import ij.ImagePlus;
import ij3d.Volume;
import marchingcubes.MCCube;

import java.util.List;

public class MCTriangulator {

    public List getTriangles(ImagePlus image, int threshold,
                             boolean[] channels) {

        Volume volume = new Volume(image, channels);
        volume.setAverage(true);

        return MCCube.getTriangles(volume, threshold);
    }
}
