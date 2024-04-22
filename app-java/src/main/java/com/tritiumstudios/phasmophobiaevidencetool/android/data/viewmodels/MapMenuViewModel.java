package com.tritiumstudios.phasmophobiaevidencetool.android.data.viewmodels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.tritiumstudios.phasmophobiaevidencetool.R;
import com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.mapsmenu.data.MapData;
import com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.mapsmenu.mapdisplay.data.models.MapModel;

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
public class MapMenuViewModel extends ViewModel {

    private Thread imageDisplayThread;

    private MapData[] mapData;
    private int currentMap = 0;

    private MapModel currentMapModel;

    public void init(@NonNull Context context) {

        // SET DATA
        if (!hasMapData()) {
            setMapData(context);
        }

    }

    /**
     * setMapData method
     *
     * @param context
     */
    @SuppressLint("ResourceType")
    public void setMapData(@NonNull Context context) {

        TypedArray typedArray =
                context.getResources().obtainTypedArray(R.array.maps_resources_array);
        MapData[] mapData = new MapData[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++) {
            mapData[i] = new MapData();

            TypedArray mapTypedArray =
                    context.getResources().obtainTypedArray(typedArray.getResourceId(i, 0));

            //Set map name
            mapData[i].setMapName(mapTypedArray.getString(0));

            //Set map layer names
            TypedArray mapLayerNamesIDs =
                    context.getResources().obtainTypedArray(mapTypedArray.getResourceId(4, 0));
            for (int j = 0; j < mapLayerNamesIDs.length(); j++) {
                mapData[i].addFloorName(mapLayerNamesIDs.getResourceId(j, 0));
            }
            mapLayerNamesIDs.recycle(); //cleanup

            //Set map default layer
            mapData[i].setDefaultFloor(mapTypedArray.getInt(5, 0));

            //Set map thumbnail resource id
            mapData[i].setThumbnail(mapTypedArray.getResourceId(7, 0));

            //Set map layer primary images
            TypedArray mapImages =
                    context.getResources().obtainTypedArray(mapTypedArray.getResourceId(8, 0));
            for (int j = 0; j < mapImages.length(); j++) {
                mapData[i].addFloorLayer(j, mapImages.getResourceId(j, 0));
            }
            mapImages.recycle(); //cleanup

            mapTypedArray.recycle();
        }
        typedArray.recycle();
        setMapData(mapData);
    }

    /**
     * setMapData method
     *
     * @param mapData array
     */
    public void setMapData(MapData[] mapData) {
        this.mapData = mapData;
    }

    /**
     * hasMapData method
     *
     * @return isMapData null
     */
    public boolean hasMapData() {
        return mapData != null;
    }

    /**
     * setMapData method
     *
     * @return mapData array
     */
    public MapData[] getMapData() {
        return mapData;
    }

    @Nullable
    public String[] getMapNames() {
        if(hasMapData()) {
            String[] mapNames = new String[mapData.length];
            for(int i = 0; i < mapNames.length; i++) {
                mapNames[i] = mapData[i].getMapName();
            }
            return mapNames;
        }
        return null;
    }

    public @DrawableRes int[] getMapThumbnails() {
        if(hasMapData()) {
            @DrawableRes int[] mapThumbnails = new int[mapData.length];
            for(int i = 0; i < mapThumbnails.length; i++) {
                mapThumbnails[i] = mapData[i].getThumbnailImage();
            }
            return mapThumbnails;
        }
        return null;
    }

    /**
     * getMapDataLength method
     *
     * @return the size of the mapData array
     */
    public int getMapDataLength() {
        if (!hasMapData()) {
            return 0;
        }
        return mapData.length;
    }

    /**
     * hasCurrentMapData method
     *
     * @return hasMapData
     */
    public boolean hasCurrentMapData() {
        return hasMapData() && currentMap < mapData.length && mapData[currentMap] != null;
    }

    /**
     * setCurrentMapData method
     *
     * @param currentMapPos
     */
    public void setCurrentMapData(int currentMapPos) {
        if (hasMapData() && currentMapPos < mapData.length) {
            this.currentMap = currentMapPos;
        }
    }

    /**
     * getCurrentMapData method
     *
     * @return MapData
     */
    @Nullable
    public MapData getCurrentMapData() {
        if (!hasMapData()) {
            return null;
        }
        return mapData[currentMap];
    }

    /**
     * setImageDisplayThread method
     *
     * @param thread
     */
    public void setImageDisplayThread(Thread thread) {
        imageDisplayThread = thread;
    }

    /**
     * getImageDisplayThread method
     *
     * @return imageDisplayThread
     */
    public Thread getImageDisplayThread() {
        return imageDisplayThread;
    }

    public void setCurrentMapModel(MapModel currentMapModel) {
        this.currentMapModel = currentMapModel;
    }

    public MapModel getCurrentMapModel() {
        return this.currentMapModel;
    }

}
