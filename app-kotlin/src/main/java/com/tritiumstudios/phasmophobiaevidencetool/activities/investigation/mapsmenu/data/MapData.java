package com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.mapsmenu.data;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * MapData class
 *
 * @author TritiumGamingStudios
 */
public class MapData {

    private String mapName = "Unknown";
    private @DrawableRes int thumbnailImage;

    private int defaultFloorIndex = 0;
    private int currentFloorIndex = 0;
    private final ArrayList<Integer> floorNames = new ArrayList<>();
    private final ArrayList<ArrayList<Integer>> floorLayers = new ArrayList<>();

    /**
     * setMapName
     *
     * @param mapName
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * @return
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * @return
     */
    public int getFloorCount() {
        return floorNames.size();
    }

    /**
     * setDefaultFloor
     *
     * @param index
     */
    public void setDefaultFloor(int index) {
        this.currentFloorIndex = this.defaultFloorIndex = index;
    }

    /**
     * @return
     */
    public int getDefaultFloor() {
        return defaultFloorIndex;
    }

    /**
     * @param index
     */
    public void setCurrentFloor(int index) {
        this.currentFloorIndex = index;
    }

    /**
     * @return
     */
    public int getCurrentFloor() {
        return currentFloorIndex;
    }

    /**
     * @param id
     */
    public void addFloorName(int id) {
        floorNames.add(id);
    }

    /**
     * @return
     */
    public int getFloorName() {
        return floorNames.get(currentFloorIndex);
    }

    /**
     * @param floorIndex
     * @param layer
     */
    public void addFloorLayer(int floorIndex, int layer) {
        if (floorLayers.size() == 0 || floorIndex >= floorLayers.size()) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(layer);
            floorLayers.add(temp);
        } else {
            floorLayers.get(floorIndex).add(layer);
        }
    }

    /**
     * @return
     */
    public ArrayList<Integer> getAllCurrentFloorLayers() {
        return floorLayers.get(currentFloorIndex);
    }

    /**
     * @return
     */
    @NonNull
    public ArrayList<ArrayList<Integer>> getAllFloorLayers() {
        return floorLayers;
    }

    /**
     *
     * @param thumbnailResId
     */
    public void setThumbnail(@DrawableRes int thumbnailResId) {
        thumbnailImage = thumbnailResId;
    }

    /**
     *
     * @return
     */
    public @DrawableRes int getThumbnailImage() {
        return thumbnailImage;
    }
}
