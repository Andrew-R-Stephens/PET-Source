package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.local

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.MapInteractModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.MapSizeModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.source.SimpleMapDataSource

class SimpleMapLocalDataSource(
    private val applicationContext: Context
): SimpleMapDataSource {

    override fun fetchMaps(): Result<List<MapInteractModel>> {

        val resources: Resources = applicationContext.resources

        val mapsTypedArray: TypedArray = resources.obtainTypedArray(R.array.maps_resources_array)

        var tempMaps = mutableListOf<MapInteractModel>()

        val mapIdKey = 0
        val mapNameKey = 1
        val layerNamesKey = 5
        val defaultLayerKey = 6
        val mapSizeKey = 7
        val thumbnailKey = 8
        val layerImagesKey = 9

        for (mapIndex in 0 until mapsTypedArray.length()) {
            val mapTypedArray: TypedArray =
                resources.obtainTypedArray(mapsTypedArray.getResourceId(mapIndex, 0))

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
        mapsTypedArray.recycle()

        return Result.success(tempMaps)
    }

    override fun fetchSizeModifiers(): Result<List<MapSizeModel>> {

        var modifiers = mutableListOf<MapSizeModel>()

        val resources: Resources = applicationContext.resources
        val sizeModifiersTypedArray: TypedArray = resources.obtainTypedArray(R.array.maps_size_arrays)

        val nameIndex = 0
        val setupModifierIndex = 0
        val normalModifierIndex = 1

        for (i in 0 until sizeModifiersTypedArray.length()){
            val modifiersTypedArray: TypedArray =
                resources.obtainTypedArray(sizeModifiersTypedArray.getResourceId(i, 0))

            val name = modifiersTypedArray.getResourceId(nameIndex, 0)
            val setupModifier = modifiersTypedArray.getString(setupModifierIndex)?.toFloat() ?: 0f
            val normalModifier = modifiersTypedArray.getString(normalModifierIndex)?.toFloat() ?: 0f

            modifiers.add(i,
                MapSizeModel(
                    name = name,
                    setupModifier = setupModifier,
                    normalModifier = normalModifier
                )
            )

            modifiersTypedArray.recycle()
        }

        sizeModifiersTypedArray.recycle()

        return Result.success(modifiers)
    }

}