package com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.StyleRes;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.ColorThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.FontThemeControl;

import java.util.ArrayList;

/**
 * ColorSpaceData class
 *
 * @author TritiumGamingStudios
 */
public abstract class AThemeControl {

    protected @StyleRes int defaultStyle;

    protected ArrayList<CustomTheme> themes = new ArrayList<>();
    protected int savedIndex, selectedIndex;

    public AThemeControl(Context context) {
        build(context);
    }

    public void init() {
        setSavedIndex(getIndexOfID(getDefaultTheme().getID()));
        setSelectedIndex(savedIndex);
    }

    public void init(String savedID) {
        setSavedIndex(getIndexOfID(savedID));
        setSelectedIndex(savedIndex);
    }

    private int getIndexOfID(String savedID) {
        for(int i = 0; i < themes.size(); i++) {
            if(themes.get(i).getID().equals(savedID)) {
                return i;
            }
        }

        return 0;
    }

    @SuppressLint("Recycle, ResourceType")
    protected abstract void build(Context context);

    public void setSavedIndex() {
        this.savedIndex = selectedIndex;
    }

    public void setSavedIndex(int savedIndex) {
        if(savedIndex < 0 || savedIndex >= themes.size()) {
            savedIndex = getIndexOfID(getDefaultTheme().getID());
        }

        this.savedIndex = savedIndex;
    }

    /**
     * @param selectedIndex
     */
    public void setSelectedIndex(int selectedIndex) {
        if(selectedIndex < 0 || selectedIndex >= themes.size()) {
            selectedIndex = getIndexOfID(getDefaultTheme().getID());
        }

        this.selectedIndex = selectedIndex;
    }

    /**
     * @return
     */
    public int getSavedIndex() {
        return savedIndex;
    }

    public void revertSelection() {
        selectedIndex = savedIndex;
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
        iterateSelection(dir, selectedIndex);
    }

    /**
     * @param dir
     */
    public void iterateSelection(int dir, int start) {

        selectedIndex += dir;

        if (selectedIndex < 0) {
            selectedIndex = themes.size() - 1;
        }
        else if (selectedIndex >= themes.size()) {
            selectedIndex = 0;
        }

        if(!getCurrentTheme().isUnlocked()) {
            if(start != selectedIndex) {
                iterateSelection(dir, start);
            }
        }
    }

    public CustomTheme getCurrentTheme() {

        if(selectedIndex >= themes.size() || selectedIndex < 0) {
            return getDefaultTheme();
        }

        CustomTheme theme = themes.get(selectedIndex);
        if(theme == null) {
            return getDefaultTheme();
        }

        return theme;
    }

    /**
     * @return
     */
    public int getCurrentName() throws Exception {
        CustomTheme theme = getCurrentTheme();
        if(theme == null) {
            throw new Exception("No theme set.");
        }
        return getCurrentTheme().getName();
    }

    public String getID() {
        return getCurrentTheme().getID();
    }

    /*
    public @StyleRes int getCurrentStyle() {
        return getCurrentTheme().getStyle();
    }
    */

    public CustomTheme getAppThemeAt(int index) {

        if(index >= themes.size() || selectedIndex < 0)
        {
            return getDefaultTheme();
        }

        CustomTheme theme = themes.get(index);

        if(theme == null) {
            return getDefaultTheme();
        }

        return theme;

    }

    public CustomTheme getThemeByUUID(String uuid) {
        for(CustomTheme customTheme: themes) {
            if(customTheme.getID().equals(uuid)) {
                return customTheme;
            }
        }
        return getDefaultTheme();
    }

    public void revertAllUnlockedStatuses() {
        for(CustomTheme theme: themes) {
            theme.revertUnlockStatus();
        }
    }

    public CustomTheme getDefaultTheme() {
        return new CustomTheme(
                "CzjtxSbXRwIpX8SYR0ttngAND",
                R.string.settings_colorblindnessmode_defaultName,
                defaultStyle);
    }

    /*
    public @StyleRes int getThemeAt(int index) {

        CustomTheme tempTheme = new CustomTheme(0, -1, defaultStyle);

        if(index >= themes.size())
        {
            return tempTheme.getStyle();
        }

        CustomTheme theme = themes.get(index);
        if(theme == null) {
            return tempTheme.getStyle();
        }

        return theme.getStyle();

    }
    */

    /*
    public boolean hasChanges() {
        return savedIndex != selectedIndex;
    }
    */
}
