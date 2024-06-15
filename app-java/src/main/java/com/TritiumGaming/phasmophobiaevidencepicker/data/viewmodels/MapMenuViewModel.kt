package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map.MapModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.mapviewer.MapViewerModel

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
class MapMenuViewModel : ViewModel() {

    var imageDisplayThread: Thread? = null

    private var mapsData: MutableList<MapViewerModel> = mutableListOf()

    var currentMapIndex: Int = 0
        set(currentMapPos) {
            if (currentMapPos < mapsData.size) {
                field = currentMapPos
            }
        }
    val currentMapData: MapViewerModel
        get() {
            return mapsData[currentMapIndex]
        }
    fun incrementFloorIndex() {
        var layerIndex: Int = currentMapData.currentFloor
        if (++layerIndex >= currentMapData.floorCount) {
            layerIndex = 0
        }
        currentMapData.currentFloor = layerIndex
    }
    fun decrementFloorIndex() {
        var layerIndex: Int = currentMapData.currentFloor
        if (--layerIndex < 0) {
            layerIndex = currentMapData.floorCount -1
        }
        currentMapData.currentFloor = layerIndex
    }

    var currentMapModel: MapModel? = null

    /*
    val mapNames: MutableList<String>
        get() {
            val mapNames = mutableListOf<String>()
            mapsData.forEachIndexed { index, it ->
                mapNames.add(index, it.mapName)
            }
            return mapNames
        }
    */

    val mapThumbnails: MutableList<Int>
        @DrawableRes get() {
            @DrawableRes val mapThumbnails = mutableListOf<Int>()
            mapsData.forEachIndexed { index, it ->
                mapThumbnails.add(index, it.thumbnailImage)
            }
            return mapThumbnails
        }

    fun init(context: Context) {
        if (mapsData.isEmpty()) buildMapData(context)
    }

    @SuppressLint("ResourceType")
    fun buildMapData(context: Context) {
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

        this.mapsData = tempMapsData // finaly set maps temp array into maps array
    }

    fun hasCurrentMapData(): Boolean {
        return currentMapIndex < mapsData.size
    }


}
