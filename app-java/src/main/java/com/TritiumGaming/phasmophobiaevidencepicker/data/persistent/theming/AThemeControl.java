package com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.StyleRes;

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
    /*
    public void init(String savedID) {
        setSavedIndex(savedID);
        setSelectedIndex(savedIndex);
    }
    */
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
        this.savedIndex = savedIndex < themes.size() ? savedIndex : 0;
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

        CustomTheme theme = themes.get(selectedIndex);
        if(theme == null) {
            new CustomTheme("-1", -1, defaultStyle);
        }

        return theme;

    }

    /**
     * @return
     */
    public int getCurrentName() {
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

        CustomTheme tempTheme = new CustomTheme("-1", -1, defaultStyle);

        if(index >= themes.size())
        {
            return tempTheme;
        }

        CustomTheme theme = themes.get(index);

        if(theme == null) {
            return tempTheme;
        }

        return theme;

    }

    public CustomTheme getThemeByUUID(String uuid) {
        for(CustomTheme customTheme: themes) {
            if(customTheme.getID().equals(uuid)) {
                return customTheme;
            }
        }
        return new CustomTheme("-1", -1, defaultStyle);
    }

    public void revertUnlockStatus() {
        for(CustomTheme theme: themes) {
            theme.revertUnlockStatus();
        }
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
