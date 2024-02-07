package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities;

import static androidx.core.graphics.ColorUtils.blendARGB;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;

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

    public static int interpolate(@ColorInt int startColor, @ColorInt int endColor,
                                  @FloatRange(from = 0.0, to = 1.0) float ratio) {
        return blendARGB(endColor, startColor, ratio);
    }

    public static String intToHex(int color) {
        if(String.valueOf(color).length() < 6) { return "#FFFFFF"; }
        return String.format("#%06X", 0xFFFFFF & color);
    }

}
