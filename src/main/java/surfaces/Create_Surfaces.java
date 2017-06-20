package surfaces;

import ij.ImagePlus;
import stl.CreateSTL;
import javax.vecmath.Point3f;
import java.io.File;
import java.util.List;

public class Create_Surfaces {

    private ImagePlus image;

    public Create_Surfaces(ImagePlus var1) {
        this.image = var1;
    }

    public List run() {
        List var = this.getNeuropilMesh(this.image);
        if (var != null && var.size() != 0) {
            return var;
        }
        return null;
    }

    private List<Point3f> getNeuropilMesh(ImagePlus var1) {
        int var6 = 127;
        List var7 = new MCTriangulator().getTriangles(var1, var6);
        return var7;
    }
}
