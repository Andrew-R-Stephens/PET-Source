package com.TritiumGaming.phasmophobiaevidencepicker.data.data;

import java.util.ArrayList;

/**
 * MapData class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class MapData {

    private String mapName = "Unknown";
    private int defaultFloorIndex = 0;
    private int currentFloorIndex = 0;
    private final ArrayList<Integer> floorNames = new ArrayList<>();
    private final ArrayList<ArrayList<Integer>> floorLayers = new ArrayList<>();

    /**
     * setMapName
     *
     * TODO
     *
     * @param mapName
     */
    public void setMapName(String mapName){
        this.mapName = mapName;
    }

    /**
     * getMapName
     *
     * TODO
     *
     * @return
     */
    public String getMapName(){
        return mapName;
    }

    /**
     * getFloorCount
     *
     * TODO
     *
     * @return
     */
    public int getFloorCount(){ return floorNames.size(); }

    /**
     * setDefaultFloor
     *
     * TODO
     *
     * @param index
     */
    public void setDefaultFloor(int index) { this.currentFloorIndex = this.defaultFloorIndex = index; }

    /**
     * getDefaultFloor
     *
     * TODO
     *
     * @return
     */
    public int getDefaultFloor(){
        return defaultFloorIndex;
    }

    /**
     * setCurrentFloor
     *
     * TODO
     *
     * @param index
     */
    public void setCurrentFloor(int index) { this.currentFloorIndex = index; }

    /**
     * getCurrentFloor
     *
     * TODO
     *
     * @return
     */
    public int getCurrentFloor(){
        return currentFloorIndex;
    }

    /**
     * addFloorName
     *
     * TODO
     *
     * @param id
     */
    public void addFloorName(int id){ floorNames.add(id); }

    /**
     * getFloorName
     *
     * TODO
     *
     * @return
     */
    public int getFloorName(){
        return floorNames.get(currentFloorIndex);
    }

    /**
     * addFloorLayer
     *
     * TODO
     *
     * @param floorIndex
     * @param layer
     */
    public void addFloorLayer(int floorIndex, int layer){
        if(floorLayers.size() <= 0 || floorIndex >= floorLayers.size()) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(layer);
            floorLayers.add(temp);
        }
        else
            floorLayers.get(floorIndex).add(layer);
    }

    /**
     * getAllCurrentFloorLayers
     *
     * TODO
     *
     * @return
     */
    public ArrayList<Integer> getAllCurrentFloorLayers() {
        return floorLayers.get(currentFloorIndex);
    }

    /**
     * getAllFloorLayers
     *
     * TODO
     *
     * @return
     */
    public ArrayList<ArrayList<Integer>> getAllFloorLayers() {
        return floorLayers;
    }

}
