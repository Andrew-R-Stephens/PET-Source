package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.local

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto.LocalWorldMapDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto.LocalWorldMapFloorDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto.WorldMapSizeTypeDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.local.SimpleMapLocalDataSource.MapSizeTypeResourceDto.LARGE
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.local.SimpleMapLocalDataSource.MapSizeTypeResourceDto.MEDIUM
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.local.SimpleMapLocalDataSource.MapSizeTypeResourceDto.SMALL
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.source.SimpleMapDataSource

class SimpleMapLocalDataSource(
    private val applicationContext: Context
): SimpleMapDataSource {

    private val mapsResourceDto: List<MapResourceDto>
        get() = listOf(
            // Sunny Meadows
            MapResourceDto(
                mapId = R.string.map_id_meadows,
                mapName = R.string.map_name_meadows,
                thumbnailImage = R.drawable.thumbnail_meadows,
                mapSize = SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_basement,
                        image = R.drawable.map_sunnymeadows_basement
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_first,
                        image = R.drawable.map_sunnymeadows_firstfloor
                    ),
                ),
                defaultFloor = 1
            ),
            // Sunny Meadows Restricted
            MapResourceDto(
                mapId = R.string.map_id_meadows_r,
                mapName = R.string.map_name_meadows_r,
                thumbnailImage = R.drawable.thumbnail_meadows,
                mapSize = SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_basement,
                        image = R.drawable.map_sunnymeadowsrestricted_basement
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_first,
                        image = R.drawable.map_sunnymeadowsrestricted_firstfloor
                    ),
                ),
                defaultFloor = 1
            ),
            // Point Hope
            MapResourceDto(
                mapId = R.string.map_id_pointhope,
                mapName = R.string.map_name_pointhope,
                thumbnailImage = R.drawable.thumbnail_pointhope,
                mapSize = SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_ground,
                        image = R.drawable.map_sunnymeadowsrestricted_firstfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_second,
                        image = R.drawable.map_pointhope_secondfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_third,
                        image = R.drawable.map_pointhope_thirdfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_fourth,
                        image = R.drawable.map_pointhope_fourthfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_fifth,
                        image = R.drawable.map_pointhope_fifthfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_sixth,
                        image = R.drawable.map_pointhope_sixthfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_seventh,
                        image = R.drawable.map_pointhope_seventhfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_eighth,
                        image = R.drawable.map_pointhope_eighthfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_ninth,
                        image = R.drawable.map_pointhope_ninthfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_tenth,
                        image = R.drawable.map_pointhope_tenthfloor
                    ),
                ),
                defaultFloor = 0
            ),
            // Camp Woodwind
            MapResourceDto(
                mapId = R.string.map_id_campwoodwind,
                mapName = R.string.map_name_campwoodwind,
                thumbnailImage = R.drawable.thumbnail_campmaple,
                mapSize = SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_ground,
                        image = R.drawable.map_woodwind_firstfloor
                    ),
                ),
                defaultFloor = 0
            ),
            // Camp Maple
            MapResourceDto(
                mapId = R.string.map_id_campmaple,
                mapName = R.string.map_name_campmaple,
                thumbnailImage = R.drawable.thumbnail_campmaple,
                mapSize = MEDIUM,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_basement,
                        image = R.drawable.map_maple_basement
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_first,
                        image = R.drawable.map_maple_firstfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_second,
                        image = R.drawable.map_maple_secondfloor
                    ),
                ),
                defaultFloor = 0
            ),
            // Bleasdale Farmhouse
            MapResourceDto(
                mapId = R.string.map_id_bleasdale,
                mapName = R.string.map_name_bleasdale,
                thumbnailImage = R.drawable.thumbnail_bleasedale,
                mapSize = SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_first,
                        image = R.drawable.map_bleasdale_firstfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_second,
                        image = R.drawable.map_bleasdale_secondfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_attic,
                        image = R.drawable.map_bleasdale_thirdfloor
                    ),
                ),
                defaultFloor = 0
            ),
            // Grafton Farmhouse
            MapResourceDto(
                mapId = R.string.map_id_grafton,
                mapName = R.string.map_name_grafton,
                thumbnailImage = R.drawable.thumbnail_grafton,
                mapSize = SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_first,
                        image = R.drawable.map_grafton_firstfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_second,
                        image = R.drawable.map_grafton_secondfloor
                    ),
                ),
                defaultFloor = 0
            ),
            // Brownstone Highschool
            MapResourceDto(
                mapId = R.string.map_id_brownstone,
                mapName = R.string.map_name_brownstone,
                thumbnailImage = R.drawable.thumbnail_highschool,
                mapSize = LARGE,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_first,
                        image = R.drawable.map_brownstone_firstfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_second,
                        image = R.drawable.map_brownstone_secondfloor
                    ),
                ),
                defaultFloor = 0
            ),
            // Edgefield
            MapResourceDto(
                mapId = R.string.map_id_edgefield,
                mapName = R.string.map_name_edgefield,
                thumbnailImage = R.drawable.thumbnail_edgefield,
                mapSize = SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_basement,
                        image = R.drawable.map_edgefield_basement
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_first,
                        image = R.drawable.map_edgefield_firstfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_second,
                        image = R.drawable.map_edgefield_secondfloor
                    ),
                ),
                defaultFloor = 1
            ),
            // Tanglewood
            MapResourceDto(
                mapId = R.string.map_id_tanglewood,
                mapName = R.string.map_name_tanglewood,
                thumbnailImage = R.drawable.thumbnail_tanglewood,
                mapSize = SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_basement,
                        image = R.drawable.map_tanglewood_basement
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_first,
                        image = R.drawable.map_tanglewood_firstfloor
                    ),
                ),
                defaultFloor = 1
            ),
            // Willow
            MapResourceDto(
                mapId = R.string.map_id_willow,
                mapName = R.string.map_name_willow,
                thumbnailImage = R.drawable.thumbnail_willow,
                mapSize = SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_basement,
                        image = R.drawable.map_willow_basement
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_first,
                        image = R.drawable.map_willow_firstfloor
                    ),
                ),
                defaultFloor = 1
            ),
            // Ridgeview
            MapResourceDto(
                mapId = R.string.map_id_ridgeview,
                mapName = R.string.map_name_ridgeview,
                thumbnailImage = R.drawable.thumbnail_ridgeview,
                mapSize = SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_basement,
                        image = R.drawable.map_ridgeview_basement
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_first,
                        image = R.drawable.map_ridgeview_firstfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_second,
                        image = R.drawable.map_ridgeview_secondfloor
                    ),
                ),
                defaultFloor = 1
            ),
            //Prison
            MapResourceDto(
                mapId = R.string.map_id_prison,
                mapName = R.string.map_name_prison,
                thumbnailImage = R.drawable.thumbnail_prison,
                mapSize = MEDIUM,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_first,
                        image = R.drawable.map_prison_firstfloor
                    ),
                    MapFloorResourceDto(
                        layerName = R.string.map_floor_name_second,
                        image = R.drawable.map_prison_secondfloor
                    )
                ),
                defaultFloor = 0
            )
        )

    override fun fetchMaps(): Result<List<LocalWorldMapDto>> {

        val simpleMaps = mapsResourceDto.toWorldMapDto()

        return Result.success(simpleMaps)
    }

    private fun MapResourceDto.toWorldMapDto() = LocalWorldMapDto(
        mapId = applicationContext.getString(mapId),
        mapName = mapName,
        thumbnailImage = thumbnailImage,
        mapSize = mapSize.toWorldMapSizeTypeDto(),
        mapFloors = mapFloors.toWorldMapFloorDto(),
        defaultFloor = defaultFloor
    )

    private fun MapFloorResourceDto.toWorldMapFloorDto() = LocalWorldMapFloorDto(
        layerName = layerName,
        image = image
    )

    private fun MapSizeTypeResourceDto.toWorldMapSizeTypeDto() = WorldMapSizeTypeDto.valueOf(name)

    private fun List<MapResourceDto>.toWorldMapDto() = map { it.toWorldMapDto() }

    private fun List<MapFloorResourceDto>.toWorldMapFloorDto() = map { it.toWorldMapFloorDto() }

    private data class MapResourceDto(
        val mapId: Int,
        @StringRes val mapName: Int,
        val mapSize: MapSizeTypeResourceDto,
        @DrawableRes val thumbnailImage: Int,
        val mapFloors: List<MapFloorResourceDto>,
        val defaultFloor: Int
    )

    private data class MapFloorResourceDto(
        @StringRes val layerName: Int,
        @DrawableRes val image: Int
    )

    internal enum class MapSizeTypeResourceDto {
        SMALL, MEDIUM, LARGE
    }

}
