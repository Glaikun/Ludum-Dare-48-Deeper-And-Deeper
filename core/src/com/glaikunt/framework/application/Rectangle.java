package com.glaikunt.framework.application;

public class Rectangle extends com.badlogic.gdx.math.Rectangle {

    public boolean intersects(com.badlogic.gdx.math.Rectangle r) {
        int tw = (int) this.width;
        int th = (int) this.height;
        int rw = (int) r.width;
        int rh = (int) r.height;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        int tx = (int) this.x;
        int ty = (int) this.y;
        int rx = (int) r.x;
        int ry = (int) r.y;
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }
}
