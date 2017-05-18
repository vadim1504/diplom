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

            //CreateSTL.writeString(var9, file);
            CreateSTL.writeBinary(var9, file);

        }
    }

    public List<Point3f> getNeuropilMesh(ImagePlus var1) {
        float var5 = 0.1F;
        int var6 = 127;
        List var7;
        ImagePlus var8 = Smooth.smooth(var1, true, var5, false);

        var7 = new MCTriangulator().getTriangles(var8, var6, new boolean[]{true, true, true});
        return var7;
    }
}
