package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.maps.mapviewer.MapInteractModel

class SimpleMapRepository(
    context: Context
) {

    companion object MapConstraints {
        val MODIFIER_NORMAL = floatArrayOf(.12f, .08f, .05f)
        val MODIFIER_SETUP = floatArrayOf(.09f, .05f, .03f)
    }


    /* List */
    var maps: MutableList<MapInteractModel> = mutableListOf()

    val mapThumbnails: MutableList<Int>
        @DrawableRes get() {
            @DrawableRes val mapThumbnails = mutableListOf<Int>()
            maps.forEachIndexed { index, it ->
                mapThumbnails.add(index, it.thumbnailImage)
            }
            return mapThumbnails
        }

    init {
        val resources: Resources = context.resources // NEW
        val typedArray: TypedArray = resources.obtainTypedArray(R.array.maps_resources_array)

        val tempMaps: MutableList<MapInteractModel> = mutableListOf() // NEW

        val mapIdKey = 0
        val mapNameKey = 1
        val layerNamesKey = 5
        val defaultLayerKey = 6
        val mapSizeKey = 7
        val thumbnailKey = 8
        val layerImagesKey = 9

        for (mapIndex in 0 until typedArray.length()) { //NEW
            val mapTypedArray: TypedArray =
                resources.obtainTypedArray(typedArray.getResourceId(mapIndex, 0))

            val tempMapData = MapInteractModel()

            //Set map name
            tempMapData.mapId = mapTypedArray.getResourceId(mapIdKey, 0)
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

            tempMaps.add(tempMapData) // add to temp maps array
        }
        typedArray.recycle()

        this.maps = tempMaps // finally, set maps temp array into maps array
    }
}