package com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import java.util.ArrayList;

/**
 * ColorSpaceData class
 *
 * @author TritiumGamingStudios
 */
public class LanguageControl {

    protected @StyleRes int defaultStyle;

    @NonNull
    protected ArrayList<CustomTheme> themes = new ArrayList<>();
    protected int savedIndex, selectedIndex;

    public LanguageControl() {
    }

    public void init() {
        setSavedIndex(getIndexOfID(CustomTheme.getDefaultTheme().getID()));
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

    public void setSavedIndex() {
        this.savedIndex = selectedIndex;
    }

    public void setSavedIndex(int savedIndex) {
        if(savedIndex < 0 || savedIndex >= themes.size()) {
            savedIndex = getIndexOfID(CustomTheme.getDefaultTheme().getID());
        }

        this.savedIndex = savedIndex;
    }

    /**
     * @param selectedIndex
     */
    public void setSelectedIndex(int selectedIndex) {
        if(selectedIndex < 0 || selectedIndex >= themes.size()) {
            selectedIndex = getIndexOfID(CustomTheme.getDefaultTheme().getID());
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

    @NonNull
    public CustomTheme getCurrentTheme() {

        if(selectedIndex >= themes.size() || selectedIndex < 0) {
            return CustomTheme.getDefaultTheme();
        }

        CustomTheme theme = themes.get(selectedIndex);
        if(theme == null) {
            return CustomTheme.getDefaultTheme();
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

    @NonNull
    public CustomTheme getThemeAtIndex(int index) {

        if(index >= themes.size() || selectedIndex < 0)
        {
            return CustomTheme.getDefaultTheme();
        }

        CustomTheme theme = themes.get(index);

        if(theme == null) {
            return CustomTheme.getDefaultTheme();
        }

        return theme;

    }

    @NonNull
    public CustomTheme getThemeByUUID(String uuid) {
        for(CustomTheme customTheme: themes) {
            if(customTheme.getID().equals(uuid)) {
                return customTheme;
            }
        }
        return CustomTheme.getDefaultTheme();
    }

    public void revertAllUnlockedStatuses() {
        for(CustomTheme theme: themes) {
            theme.revertUnlockStatus();
        }
    }

}
