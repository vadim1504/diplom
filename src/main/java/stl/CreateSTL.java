package stl;

import javax.swing.*;
import javax.vecmath.Point3f;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class CreateSTL {

    public static void writeString(List<Point3f> tr,File file) {
        PrintWriter out = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new PrintWriter(file.getAbsoluteFile());
            out.println("solid stl ");
            for(int i=0;i<tr.size();i=i+3){
                out.println("facet normal 1 1 1 ");
                out.println("outer loop");

                out.println("vertex " + tr.get(i).x + " " + tr.get(i).y + " " + tr.get(i).z);
                out.println("vertex " + tr.get(i+1).x + " " + tr.get(i+1).y + " " + tr.get(i+1).z);
                out.println("vertex " + tr.get(i+2).x + " " + tr.get(i+2).y + " " + tr.get(i+2).z);

                out.println("endloop ");
                out.println("endfacet");
            }
            out.println("endsolid");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
            JOptionPane.showMessageDialog(null, "Успешно, файл сохранён: "+file.getAbsolutePath());
        }
    }

    public static void writeBinary(List<Point3f> surfaces,File file) {
        DataOutputStream out = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new DataOutputStream(new BufferedOutputStream(
                    new FileOutputStream(file)));
            String header = "Binary STL file created by DIPLOM Vadim ";
            for (int i = header.length(); i < 80; i++){
                header = header+".";
            }
            int triangles = surfaces.size()/3;
            try {
                out.writeBytes(header);
                out.writeByte(triangles & 0xFF);
                out.writeByte((triangles >> 8) & 0xFF);
                out.writeByte((triangles >> 16) & 0xFF);
                out.writeByte((triangles >> 24) & 0xFF);
                for (int i = 0; i < surfaces.size(); i += 3) {
                    Point3f p0 = surfaces.get(i);
                    Point3f p1 = surfaces.get(i + 1);
                    Point3f p2 = surfaces.get(i + 2);
                    Point3f n = unitNormal(p0, p1, p2);
                    ByteBuffer bb = ByteBuffer.allocate(50);
                    bb.order(ByteOrder.LITTLE_ENDIAN);
                    bb.putFloat(n.x);
                    bb.putFloat(n.y);
                    bb.putFloat(n.z);
                    bb.putFloat(p0.x);
                    bb.putFloat(p0.y);
                    bb.putFloat(p0.z);
                    bb.putFloat(p1.x);
                    bb.putFloat(p1.y);
                    bb.putFloat(p1.z);
                    bb.putFloat(p2.x);
                    bb.putFloat(p2.y);
                    bb.putFloat(p2.z);
                    bb.putShort((short) 0);
                    out.write(bb.array());
                }
                out.flush();
                JOptionPane.showMessageDialog(null, "Успешно, файл сохранён: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Point3f unitNormal(Point3f p0, Point3f p1, Point3f p2) {
        float nx = (p1.y-p0.y) * (p2.z-p0.z) - (p1.z-p0.z) * (p2.y-p0.y);
        float ny = (p1.z-p0.z) * (p2.x-p0.x) - (p1.x-p0.x) * (p2.z-p0.z);
        float nz = (p1.x-p0.x) * (p2.y-p0.y) - (p1.y-p0.y) * (p2.x-p0.x);

        float length = (float)Math.sqrt(nx * nx + ny * ny + nz* nz);
        nx /= length;
        ny /= length;
        nz /= length;
        return new Point3f(nx, ny, nz);
    }

}
