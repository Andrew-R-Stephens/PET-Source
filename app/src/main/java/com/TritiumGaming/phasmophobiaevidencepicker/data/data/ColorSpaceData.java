package com.TritiumGaming.phasmophobiaevidencepicker.data.data;

/**
 * ColorSpaceData class
 *
 * @author TritiumGamingStudios
 */
public class ColorSpaceData {

    private final String[] colorSpaceNames;
    private int index = 0;

    /**
     *
     * @param colorSpaceNames
     */
    public ColorSpaceData(String[] colorSpaceNames){
        this.colorSpaceNames = colorSpaceNames;
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
            index = colorSpaceNames.length-1;
        else
            if(index >= colorSpaceNames.length)
                index = 0;
    }

    /**
     *
     * @return
     */
    public String getColorSpaceName() {
        return colorSpaceNames[index];
    }

}
