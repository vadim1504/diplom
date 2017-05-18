package surfaces;

import ij.ImagePlus;
import stl.CreateSTL;
import javax.vecmath.Point3f;
import java.io.File;
import java.util.List;

public class Create_Surfaces {

    private ImagePlus image;
    private String name;

    public Create_Surfaces(ImagePlus var1) {
        this.image = var1;
    }

    public void run(String name) {
        this.name = name;
        this.runFromBinary();
    }

    public void runFromBinary() {
        List var9 = this.getNeuropilMesh(this.image);
        if (var9 != null && var9.size() != 0) {
            File file = new File(name+".stl");
            CreateSTL.writeBinary(var9, file);
        }
    }

    private List<Point3f> getNeuropilMesh(ImagePlus var1) {
        int var6 = 127;
        List var7 = new MCTriangulator().getTriangles(var1, var6, new boolean[]{true, true, true});
        return var7;
    }
}
