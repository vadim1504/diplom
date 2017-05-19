package marchingcubes;

import ij3d.Volume;

import javax.vecmath.Point3f;

class Carrier {

    int w, h, d;
    Volume volume;
    float threshold;

    Carrier(int w,int h,int d,Volume v, float t){
        this.w=w;
        this.h=h;
        this.d=d;
        this.volume=v;
        this.threshold=t;
    }

    final int intensity(final Point3f p) {
        if(p.x < 0 || p.y < 0 || p.z < 0
                || p.x >= w || p.y >= h || p.z >= d)
            return 0;
        return volume.load((int)p.x, (int)p.y, (int)p.z);
    }
}