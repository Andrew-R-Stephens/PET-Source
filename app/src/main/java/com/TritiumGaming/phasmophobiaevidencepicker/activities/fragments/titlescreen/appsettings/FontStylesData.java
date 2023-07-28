package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.appsettings;

/**
 * FontStylesData class
 *
 * @author TritiumGamingStudios
 */
public class FontStylesData {

    private final String[] fontNames;
    private int index = 0;

    /**
     * @param fontNames
     */
    public FontStylesData(String[] fontNames) {
        this.fontNames = fontNames;
    }

    /**
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param dir
     */
    public void iterate(int dir) {
        index += dir;

        if (index < 0) {
            index = fontNames.length - 1;
        }
        else if (index >= fontNames.length) {
            index = 0;
        }
    }

    /**
     * @return
     */
    public String getFontStylesName() {
        return fontNames[index];
    }

}
