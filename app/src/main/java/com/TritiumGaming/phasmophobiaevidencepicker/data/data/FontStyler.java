package com.TritiumGaming.phasmophobiaevidencepicker.data.data;

import android.util.TypedValue;
import android.view.View;

/**
 * FontStyler class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class FontStyler {

    /**
     * setColor
     *
     * TODO
     *
     * @param plainStr
     * @param colorHex
     * @return
     */
    public static String setColor(String plainStr, String colorHex) {
        return "<font color=#" + colorHex +">" + plainStr + "</font>";
    }

    /**
     * dpToSp
     *
     * TODO
     *
     * @param view
     * @param dpActual
     * @return
     */
    public static float dpToSp(View view, float dpActual){
        return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dpActual, view.getContext().getResources().getDisplayMetrics())) / view.getContext().getResources().getDisplayMetrics().scaledDensity;
    }

}
