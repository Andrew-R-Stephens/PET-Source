package com.TritiumGaming.phasmophobiaevidencepicker.data.persistent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.data.itemdata.equipment.ItemStoreEquipmentItemData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.data.itemdata.possessions.ItemStorePossessionItemData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;

import java.util.ArrayList;

/**
 * ColorSpaceData class
 *
 * @author TritiumGamingStudios
 */
public class AppThemesData {

    private static final ArrayList<AppTheme> themes = new ArrayList<>();
    private static int selectedIndex = 0;

    private final GlobalPreferencesViewModel globalPreferencesViewModel;

    public AppThemesData(Context context, GlobalPreferencesViewModel globalPreferencesViewModel) {
        this.globalPreferencesViewModel = globalPreferencesViewModel;

        init(context);
    }

    @SuppressLint("Recycle, ResourceType")
    private void init(Context context) {
        if(context == null) { return; }

        // COLORBLIND DATA
        TypedArray subThemesArray =
                context.getResources().obtainTypedArray(R.array.settings_themes_array);


        for (int i = 0; i < subThemesArray.length(); i++) {
            TypedArray themeArray =
                    context.getResources().obtainTypedArray(subThemesArray.getResourceId(i, 0));
                TypedArray themeIDsArray =
                        context.getResources().obtainTypedArray(themeArray.getResourceId(0, 0));
                TypedArray themeNamesArray =
                        context.getResources().obtainTypedArray(themeArray.getResourceId(1, 0));
                TypedArray themeStyleArray =
                        context.getResources().obtainTypedArray(themeArray.getResourceId(2, 0));

            if((themeNamesArray.length() == themeStyleArray.length()) &&
                    (themeNamesArray.length() == themeIDsArray.length())) {
                int themeCount = themeNamesArray.length();

                Log.d("Themes", "Do it");
                for (int k = 0; k < themeCount; k++) {
                    @StringRes int idRes = themeIDsArray.getResourceId(k, 0);
                    @StringRes int nameRes = themeNamesArray.getResourceId(k, 0);
                    @StyleRes int styleRes = themeStyleArray.getResourceId(k, 0);

                    AppTheme tempTheme = new AppTheme(idRes, nameRes, styleRes);

                    themes.add(tempTheme);
                }
            }
            themeArray.recycle();
        }
        subThemesArray.recycle();

        int oldIndex = 0;
        if (globalPreferencesViewModel != null) {
            oldIndex = globalPreferencesViewModel.getColorSpace();
        }
        oldIndex = oldIndex < themes.size() ? oldIndex : 0;

        setSelectedIndex(oldIndex);

    }

    /**
     * @param selectedIndex
     */
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    /**
     * @return
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * @param dir
     */
    public void iterateSelection(int dir) {

        selectedIndex += dir;

        if (selectedIndex < 0) {
            selectedIndex = themes.size() - 1;
        }
        else if (selectedIndex >= themes.size()) {
            selectedIndex = 0;
        }
    }

    public static AppTheme getCurrentTheme() {

        AppTheme theme = themes.get(selectedIndex);
        if(theme == null) {
            new AppTheme(0, -1, R.style.NonColorblind_Base);
        }

        return theme;

    }

    /**
     * @return
     */
    public int getCurrentName() {
        return getCurrentTheme().getName();
    }

    /**
     * @return
     */
    public @StyleRes int getCurrentStyle() {
        return getCurrentTheme().getStyle();
    }

    public AppTheme getAppThemeAt(int index) {

        AppTheme tempTheme = new AppTheme(0, -1, R.style.NonColorblind_Base);

        if(index >= themes.size())
        {
            return tempTheme;
        }

        AppTheme theme = themes.get(index);
        if(theme == null) {
            return tempTheme;
        }

        return theme;

    }

    public static @StyleRes int getThemeAt(int index) {

        AppTheme tempTheme = new AppTheme(0, -1, R.style.NonColorblind_Base);

        if(index >= themes.size())
        {
            return tempTheme.getStyle();
        }

        AppTheme theme = themes.get(index);
        if(theme == null) {
            return tempTheme.getStyle();
        }

        return theme.getStyle();

    }

    public static @StyleRes int getFontResource(int font) {
        switch (font) {
            case 1 -> {
                return R.style.Android;
            }
            case 2 -> {
                return R.style.Journal;
            }
            case 3 -> {
                return R.style.Brick;
            }
            case 4 -> {
                return R.style.Clean;
            }
            case 5 -> {
                return R.style.LongCang;
            }
            case 6 -> {
                return R.style.NewTegomin;
            }
            case 7 -> {
                return R.style.Neucha;
            }
            case 8 -> {
                return R.style.JetBrainsMono;
            }
            default -> {
                return R.style.Fonts_Base;
            }
        }
    }

}
