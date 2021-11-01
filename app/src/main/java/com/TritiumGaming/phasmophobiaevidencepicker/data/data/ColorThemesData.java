package com.TritiumGaming.phasmophobiaevidencepicker.data.data;

/**
 * ColorSpaceData class
 *
 * @author TritiumGamingStudios
 */
public class ColorThemesData {

    private final String[] themeNames;
    private int index = 0;

    /**
     *
     * @param colorSpaceNames
     */
    public ColorThemesData(String[] colorSpaceNames){
        this.themeNames = colorSpaceNames;
    }

    /**
     *
     * @param index
     */
    public void setIndex(int index){
        this.index = index;
    }

    /**
     *
     * @return
     */
    public int getIndex(){
        return index;
    }

    /**
     *
     * @param dir
     */
    public void iterate(int dir){
        index += dir;

        if(index < 0)
            index = themeNames.length-1;
        else
            if(index >= themeNames.length)
                index = 0;
    }

    /**
     *
     * @return
     */
    public String getColorSpaceName() {
        return themeNames[index];
    }

}
