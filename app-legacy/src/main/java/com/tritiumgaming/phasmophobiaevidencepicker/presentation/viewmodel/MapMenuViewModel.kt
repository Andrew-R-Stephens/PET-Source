package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.AndroidViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.maps.map.MapModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.maps.mapviewer.MapViewerModel

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
class MapMenuViewModel(application: Application): AndroidViewModel(application) {

    var imageDisplayThread: Thread? = null

    private var mapsData: MutableList<MapViewerModel> = mutableListOf()

    var currentMapIndex: Int = 0
        set(currentMapPos) {
            if (currentMapPos < mapsData.size) {
                field = currentMapPos
            }
        }
    val currentMapViewerModel: MapViewerModel
        get() {
            return mapsData[currentMapIndex]
        }
    fun incrementFloorIndex() {
        var layerIndex: Int = currentMapViewerModel.currentFloor
        if (++layerIndex >= currentMapViewerModel.floorCount) {
            layerIndex = 0
        }
        currentMapViewerModel.currentFloor = layerIndex
    }
    fun decrementFloorIndex() {
        var layerIndex: Int = currentMapViewerModel.currentFloor
        if (--layerIndex < 0) {
            layerIndex = currentMapViewerModel.floorCount -1
        }
        currentMapViewerModel.currentFloor = layerIndex
    }

    var currentMapModel: MapModel? = null

    val mapThumbnails: MutableList<Int>
        @DrawableRes get() {
            @DrawableRes val mapThumbnails = mutableListOf<Int>()
            mapsData.forEachIndexed { index, it ->
                mapThumbnails.add(index, it.thumbnailImage)
            }
            return mapThumbnails
        }

    fun init() {
        if (mapsData.isEmpty()) buildMapData(getApplication())
    }

    @SuppressLint("ResourceType")
    fun buildMapData(context: Context) {
        Log.d("Map", "Building map data")

        val resources: Resources = context.resources
        val allMapsTypedArray =
            resources.obtainTypedArray(R.array.maps_resources_array)

        val tempMapsData: MutableList<MapViewerModel> = mutableListOf()
        for (mapIndex in 0 until allMapsTypedArray.length()) {

            val tempMapData = MapViewerModel()

            val nameKey = 0
            val layerNamesKey = 4
            val defaultLayerKey = 5
            val thumbnailKey = 7
            val layerImagesKey = 8

            val mapTypedArray =
                resources.obtainTypedArray(allMapsTypedArray.getResourceId(mapIndex, 0))

            //Set map name
            tempMapData.mapName = mapTypedArray.getString(nameKey) ?: tempMapData.mapName

            //Set map layer names
            val mapLayerNamesIDs =
                resources.obtainTypedArray(mapTypedArray.getResourceId(layerNamesKey, 0))
            for (j in 0 until mapLayerNamesIDs.length()) {
                tempMapData.floorNames.add(j, mapLayerNamesIDs.getResourceId(j, 0))
            }
            mapLayerNamesIDs.recycle() //cleanup

            //Set map default layer
            tempMapData.defaultFloor = mapTypedArray.getInt(defaultLayerKey, 0)

            //Set map thumbnail resource id
            tempMapData.thumbnailImage = mapTypedArray.getResourceId(thumbnailKey, 0)

            //Set map layer primary images
            val mapImages =
                resources.obtainTypedArray(mapTypedArray.getResourceId(layerImagesKey, 0))
            for (j in 0 until mapImages.length()) {
                tempMapData.addFloorLayer(j, mapImages.getResourceId(j, 0))
            }
            mapImages.recycle() //cleanup
            mapTypedArray.recycle()

            tempMapsData.add(tempMapData) // add to temp maps array
        }
        allMapsTypedArray.recycle()

        this.mapsData = tempMapsData // finally, set maps temp array into maps array
    }

    fun hasCurrentMapData(): Boolean {
        return currentMapIndex < mapsData.size
    }


}