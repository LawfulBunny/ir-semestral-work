package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query.utils;

public class Vector {

    public static void normalize(double[] a) {
        double scale = 0;

        for (double v : a) {
            scale += v * v;
        }
        scale = 1 / Math.sqrt(scale);
        for (int k = 0; k < a.length; k++) {
            a[k] *= scale;
        }
    }
}
