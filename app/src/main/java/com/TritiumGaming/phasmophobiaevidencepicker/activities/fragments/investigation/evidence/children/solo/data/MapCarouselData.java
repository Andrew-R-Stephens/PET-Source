package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

public class MapCarouselData {

    private final EvidenceViewModel evidenceViewModel;
    private MapSizeData[] mapSizeData;

    private int mapCurrent = 0;

    public MapCarouselData(EvidenceViewModel evidenceViewModel) {
        this.evidenceViewModel = evidenceViewModel;
    }

    /**
     * @param index
     */
    public void setMapCurrentIndex(int index) {
        this.mapCurrent = index;
    }

    /**
     * @return
     */
    public int getMapCurrentIndex() {
        return mapCurrent;
    }

    public void setMapSizeData(String[] allNames, int[] allSizes) {
        if (allNames.length == allSizes.length) {
            mapSizeData = new MapSizeData[allSizes.length];
            for (int i = 0; i < allNames.length; i++) {
                mapSizeData[i] = new MapSizeData(allNames[i], allSizes[i]);
            }
        }
    }

    public boolean hasMapSizeData() {
        return mapSizeData == null;
    }

    public int getMapCount() {
        if (mapSizeData == null) {
            return 0;
        }
        return mapSizeData.length;
    }

    public String getMapCurrentName() {
        if (mapSizeData != null) {
            return mapSizeData[getMapCurrentIndex()].name();
        }
        return "???";
    }

    public int getMapCurrentSize() {
        if (mapSizeData != null) {
            return mapSizeData[getMapCurrentIndex()].size();
        }
        return 1;
    }


    private record MapSizeData(String name, int size) {
    }

}
