package com.TritiumGaming.phasmophobiaevidencepicker.data.persistent;

import androidx.annotation.StyleRes;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

/**
 * ColorSpaceData class
 *
 * @author TritiumGamingStudios
 */
public class AppThemeData {

    private final String[] themeNames;
    private int index = 0;

    /**
     * @param colorSpaceNames
     */
    public AppThemeData(String[] colorSpaceNames) {
        this.themeNames = colorSpaceNames;
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
            index = themeNames.length - 1;
        }
        else if (index >= themeNames.length) {
            index = 0;
        }
    }

    /**
     * @return
     */
    public String getColorSpaceName() {
        return themeNames[index];
    }

    public static @StyleRes int getColorSpace(int colorSpace) {
        switch (colorSpace) {
            case 1 -> {
                return R.style.Monochromacy;
            }
            case 2 -> {
                return R.style.Deuteranomaly;
            }
            case 3 -> {
                return R.style.Protanomaly;
            }
            case 4 -> {
                return R.style.Tritanomaly;
            }
            case 5 -> {
                return R.style.Funhouse;
            }
            default -> {
                return R.style.Colorblind_Base;
            }
        }
    }

    public static @StyleRes int getFontResource(int fontType) {
        switch (fontType) {
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
