package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.MapData;

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
public class MapMenuViewModel extends ViewModel {

    private Thread imageDisplayThread;

    private MapData[] mapData;
    private int currentMap = 0;

    /**
     * setMapData method
     * @param context
     */
    @SuppressLint("ResourceType")
    public void setMapData(Context context){

        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.maps_resources_array);
        MapData[] mapData = new MapData[typedArray.length()];
        for(int i = 0; i < typedArray.length(); i++) {
            mapData[i] = new MapData();

            TypedArray mapTypedArray = context.getResources().obtainTypedArray(typedArray.getResourceId(i, 0));

            //Set map name
            mapData[i].setMapName(mapTypedArray.getString(0));

            //Set map layer background images
            TypedArray mapImages = context.getResources().obtainTypedArray(mapTypedArray.getResourceId(2, 0));
            for(int j = 0; j < mapImages.length(); j++){
                mapData[i].addFloorLayer(j, mapImages.getResourceId(j, 0));
            }
            mapImages.recycle(); //cleanup

            //Set map layer outline images
            mapImages = context.getResources().obtainTypedArray(mapTypedArray.getResourceId(1, 0));
            for(int j = 0; j < mapImages.length(); j++)
                mapData[i].addFloorLayer(j, mapImages.getResourceId(j, 0));
            mapImages.recycle(); //cleanup

            //Set map layer door images
            mapImages = context.getResources().obtainTypedArray(mapTypedArray.getResourceId(3, 0));
            for(int j = 0; j < mapImages.length(); j++){
                mapData[i].addFloorLayer(j, mapImages.getResourceId(j, 0));
            }
            mapImages.recycle(); //cleanup

            //Set map layer names
            TypedArray mapLayerNamesIDs = context.getResources().obtainTypedArray(mapTypedArray.getResourceId(4, 0));
            for (int j = 0; j < mapLayerNamesIDs.length(); j++) {
                mapData[i].addFloorName(mapLayerNamesIDs.getResourceId(j, 0));
            }
            mapLayerNamesIDs.recycle(); //cleanup

            //Set map default layer
            mapData[i].setDefaultFloor(mapTypedArray.getInt(5, 0));

            mapTypedArray.recycle();

        }
        typedArray.recycle();
        setMapData(mapData);
    }

    /**
     * setMapData method
     * @param mapData array
     */
    public void setMapData(MapData[] mapData){
        this.mapData = mapData;
    }

    /**
     * hasMapData method
     * @return isMapData null
     */
    public boolean hasMapData(){
        return mapData != null;
    }

    /**
     * setMapData method
     * @return mapData array
     */
    public MapData[] getMapData(){
        return mapData;
    }

    /**
     * getMapDataLength method
     * @return the size of the mapData array
     */
    public int getMapDataLength(){
        if(!hasMapData())
            return 0;
        return mapData.length;
    }

    /**
     * hasCurrentMapData method
     * @return hasMapData
     */
    public boolean hasCurrentMapData(){
        return hasMapData() && currentMap < mapData.length && mapData[currentMap] != null;
    }

    /**
     * setCurrentMapData method
     * @param currentMapPos
     */
    public void setCurrentMapData(int currentMapPos) {
        if(hasMapData() && currentMapPos < mapData.length)
            this.currentMap = currentMapPos;
    }

    /**
     * getCurrentMapData method
     * @return MapData
     */
    public MapData getCurrentMapData() {
        if(!hasMapData())
            return null;
        return mapData[currentMap];
    }

    /**
     * setImageDisplayThread method
     * @param thread
     */
    public void setImageDisplayThread(Thread thread){
        imageDisplayThread = thread;
    }

    /**
     * getImageDisplayThread method
     * @return imageDisplayThread
     */
    public Thread getImageDisplayThread(){
        return imageDisplayThread;
    }

}
