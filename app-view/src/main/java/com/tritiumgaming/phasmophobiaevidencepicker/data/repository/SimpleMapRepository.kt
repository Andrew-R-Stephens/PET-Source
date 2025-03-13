package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.maps.mapviewer.MapViewerModel

class SimpleMapRepository(
    context: Context
) {

    companion object MapConstraints {
        val MODIFIER_NORMAL = floatArrayOf(.12f, .08f, .05f)
        val MODIFIER_SETUP = floatArrayOf(.09f, .05f, .03f)
    }

    /* List */
    //var mapsData = mutableListOf<MapSizeData>()
    var mapsData: MutableList<MapViewerModel> = mutableListOf()
    val itemCount: Int
        get() {
            return mapsData.size
        }

    val mapThumbnails: MutableList<Int>
        @DrawableRes get() {
            @DrawableRes val mapThumbnails = mutableListOf<Int>()
            mapsData.forEachIndexed { index, it ->
                mapThumbnails.add(index, it.thumbnailImage)
            }
            return mapThumbnails
        }

    init {
        val resources: Resources = context.resources // NEW
        val typedArray: TypedArray = resources.obtainTypedArray(R.array.maps_resources_array)

        val tempMapsData: MutableList<MapViewerModel> = mutableListOf() // NEW

        val mapNameKey = 0
        val layerNamesKey = 4
        val defaultLayerKey = 5
        val mapSizeKey = 6
        val thumbnailKey = 7
        val layerImagesKey = 8

        for (mapIndex in 0 until typedArray.length()) { //NEW
            val mapTypedArray: TypedArray =
                resources.obtainTypedArray(typedArray.getResourceId(mapIndex, 0))

            val tempMapData = MapViewerModel()

            //Set map name
            tempMapData.mapName = mapTypedArray.getResourceId(mapNameKey, 0)
            tempMapData.mapSize = mapTypedArray.getInt(mapSizeKey, 0)

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
        typedArray.recycle()

        this.mapsData = tempMapsData // finally, set maps temp array into maps array
    }
}