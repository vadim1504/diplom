package ij3d;

class IntLoader {
    private ByteImage image;

    IntLoader(ByteImage imp) {
        this.image = imp;
    }

    final int load(int x, int y, int z) {
        return image.get(x, y, z);
    }

}