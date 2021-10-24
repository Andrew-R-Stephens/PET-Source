package com.TritiumGaming.phasmophobiaevidencepicker.data.data;

/**
 * ColorSpaceData class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class ColorSpaceData {

    private final String[] colorSpaceNames;
    private int index = 0;

    /**
     * ColorSpaceData
     *
     * TODO
     *
     * @param colorSpaceNames
     */
    public ColorSpaceData(String[] colorSpaceNames){
        this.colorSpaceNames = colorSpaceNames;
    }

    /**
     * setIndex
     *
     * TODO
     *
     * @param index
     */
    public void setIndex(int index){
        this.index = index;
    }

    /**
     * getIndex
     *
     * TODO
     *
     * @return
     */
    public int getIndex(){
        return index;
    }

    /**
     * iterate
     *
     * TODO
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
     * getColorSpaceName
     *
     * TODO
     *
     * @return
     */
    public String getColorSpaceName() {
        return colorSpaceNames[index];
    }

}
