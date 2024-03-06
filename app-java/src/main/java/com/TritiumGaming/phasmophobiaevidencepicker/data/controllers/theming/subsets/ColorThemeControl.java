package com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.AThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.CustomTheme;

/**
 * ColorSpaceData class
 *
 * @author TritiumGamingStudios
 */
public class ColorThemeControl extends AThemeControl {

    public ColorThemeControl(Context context) {
        super(context);

        defaultStyle = R.style.NonColorblind_Base;
    }

    @Override
    @SuppressLint("Recycle, ResourceType")
    protected void build(Context context) {
        // COLORBLIND DATA
        TypedArray subThemesArray =
                context.getResources().obtainTypedArray(R.array.settings_themes_color_array);

        for (int i = 0; i < subThemesArray.length(); i++) {
            TypedArray themeArray =
                    context.getResources().obtainTypedArray(subThemesArray.getResourceId(i, 0));

            TypedArray themeIDsArray =
                    context.getResources().obtainTypedArray(themeArray.getResourceId(0, 0));
            TypedArray themeNamesArray =
                    context.getResources().obtainTypedArray(themeArray.getResourceId(1, 0));
            TypedArray themeStyleArray =
                    context.getResources().obtainTypedArray(themeArray.getResourceId(2, 0));
            boolean isUnlocked =
                    context.getResources().getBoolean(themeArray.getResourceId(3, 0));

            if((themeNamesArray.length() == themeStyleArray.length()) &&
                    (themeNamesArray.length() == themeIDsArray.length())) {
                int themeCount = themeNamesArray.length();

                for (int k = 0; k < themeCount; k++) {
                    String idRes = themeIDsArray.getString(k);
                    @StringRes int nameRes = themeNamesArray.getResourceId(k, 0);
                    @StyleRes int styleRes = themeStyleArray.getResourceId(k, 0);

                    CustomTheme tempTheme = new CustomTheme(
                            idRes, nameRes, styleRes,
                            isUnlocked);

                    themes.add(tempTheme);
                }
            }

            themeArray.recycle();
        }

        subThemesArray.recycle();
    }


}
