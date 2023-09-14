package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities;

import android.graphics.Color;

public class ColorUtils {

    enum ARGB { A, R, G, B}

    public static int[] getRGB(int color) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return new int[]{a, r, g, b};
    }

    public static int setColor(int color, int a, int r, int g, int b) {
        int[] rgb = getRGB(color);

        if(a < 0) {
            a = rgb[ARGB.A.ordinal()];
        }
        if(r < 0) {
            r = rgb[ARGB.R.ordinal()];
        }
        if(g < 0) {
            g = rgb[ARGB.G.ordinal()];
        }
        if(b < 0) {
            b = rgb[ARGB.B.ordinal()];
        }

        return Color.argb(
                rgb[ARGB.A.ordinal()] = a,
                rgb[ARGB.R.ordinal()] = r,
                rgb[ARGB.G.ordinal()] = g,
                rgb[ARGB.B.ordinal()] = b
                );
    }
}
