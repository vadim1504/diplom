package ij3d;

import ij.ImagePlus;
import ij.ImageStack;

final class ByteImage {
    private byte[][] fData;
    private int w;

    ByteImage(ImagePlus imp) {
        ImageStack stack = imp.getStack();
        w = imp.getWidth();
        int d = imp.getStackSize();
        fData = new byte[d][];
        for (int z = 0; z < d; z++)
            fData[z] = (byte[])stack.getPixels(z + 1);
    }

    int get(int x, int y, int z) {
        return fData[z][y * w + x] & 0xff;
    }
}