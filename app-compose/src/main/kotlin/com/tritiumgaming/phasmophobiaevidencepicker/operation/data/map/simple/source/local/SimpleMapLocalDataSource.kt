package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto.SimpleWorldMapDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto.SimpleWorldMapFloorDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapFloorImage
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapFloorTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapThumbnail
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.SimpleMapDataSource


class SimpleMapLocalDataSource(
    private val applicationContext: Context
): SimpleMapDataSource {

    private val mapsResourceDto: List<MapResourceDto>
        get() = listOf(
            // Sunny Meadows
            MapResourceDto(
                mapId = R.string.map_id_meadows,
                mapName = MapTitle.SUNNY_MEADOWS,
                thumbnailImage = MapThumbnail.SUNNY_MEADOWS,
                mapSize = MapSize.SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.BASEMENT,
                        image = MapFloorImage.SUNNY_MEADOWS_BASEMENT
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FIRST_FLOOR,
                        image = MapFloorImage.SUNNY_MEADOWS_FIRST_FLOOR
                    ),
                ),
                defaultFloor = 1
            ),
            // Sunny Meadows Restricted
            MapResourceDto(
                mapId = R.string.map_id_meadows_r,
                mapName = MapTitle.SUNNY_MEADOWS_RESTRICTED,
                thumbnailImage = MapThumbnail.SUNNY_MEADOWS_RESTRICTED,
                mapSize = MapSize.SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.BASEMENT,
                        image = MapFloorImage.SUNNY_MEADOWS_RESTRICTED_BASEMENT
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FIRST_FLOOR,
                        image = MapFloorImage.SUNNY_MEADOWS_RESTRICTED_FIRST_FLOOR
                    ),
                ),
                defaultFloor = 1
            ),
            // Point Hope
            MapResourceDto(
                mapId = R.string.map_id_pointhope,
                mapName = MapTitle.POINT_HOPE,
                thumbnailImage = MapThumbnail.POINT_HOPE,
                mapSize = MapSize.SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.GROUND_FLOOR,
                        image = MapFloorImage.POINT_HOPE_GROUND_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.SECOND_FLOOR,
                        image = MapFloorImage.POINT_HOPE_SECOND_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.THIRD_FLOOR,
                        image = MapFloorImage.POINT_HOPE_THIRD_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FOURTH_FLOOR,
                        image =MapFloorImage.POINT_HOPE_FOURTH_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FIFTH_FLOOR,
                        image = MapFloorImage.POINT_HOPE_FIFTH_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.SIXTH_FLOOR,
                        image = MapFloorImage.POINT_HOPE_SIXTH_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.SEVENTH_FLOOR,
                        image = MapFloorImage.POINT_HOPE_SEVENTH_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.EIGHTH_FLOOR,
                        image = MapFloorImage.POINT_HOPE_EIGHTH_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.NINTH_FLOOR,
                        image = MapFloorImage.POINT_HOPE_NINTH_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.TENTH_FLOOR,
                        image = MapFloorImage.POINT_HOPE_TENTH_FLOOR
                    ),
                ),
                defaultFloor = 0
            ),
            // Camp Woodwind
            MapResourceDto(
                mapId = R.string.map_id_campwoodwind,
                mapName = MapTitle.CAMP_WOODWIND,
                thumbnailImage = MapThumbnail.CAMP_WOODWIND,
                mapSize = MapSize.SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.GROUND_FLOOR,
                        image = MapFloorImage.CAMP_WOODWIND_GROUND_FLOOR
                    ),
                ),
                defaultFloor = 0
            ),
            // Camp Maple
            MapResourceDto(
                mapId = R.string.map_id_campmaple,
                mapName = MapTitle.CAMP_MAPLE,
                thumbnailImage = MapThumbnail.CAMP_MAPLE,
                mapSize = MapSize.MEDIUM,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.BASEMENT,
                        image = MapFloorImage.CAMP_MAPLE_BASEMENT
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FIRST_FLOOR,
                        image = MapFloorImage.CAMP_MAPLE_FIRST_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.SECOND_FLOOR,
                        image = MapFloorImage.CAMP_MAPLE_SECOND_FLOOR
                    ),
                ),
                defaultFloor = 0
            ),
            // Bleasdale Farmhouse
            MapResourceDto(
                mapId = R.string.map_id_bleasdale,
                mapName = MapTitle.BLEASDALE_FARMHOUSE,
                thumbnailImage = MapThumbnail.BLEASDALE_FARMHOUSE,
                mapSize = MapSize.SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FIRST_FLOOR,
                        image = MapFloorImage.BLEASDALE_FARMHOUSE_FIRST_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.SECOND_FLOOR,
                        image = MapFloorImage.BLEASDALE_FARMHOUSE_SECOND_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.ATTIC,
                        image = MapFloorImage.BLEASDALE_FARMHOUSE_ATTIC
                    ),
                ),
                defaultFloor = 0
            ),
            // Grafton Farmhouse
            MapResourceDto(
                mapId = R.string.map_id_grafton,
                mapName = MapTitle.GRAFTON_FARMHOUSE,
                thumbnailImage = MapThumbnail.GRAFTON_FARMHOUSE,
                mapSize = MapSize.SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FIRST_FLOOR,
                        image = MapFloorImage.GRAFTON_FARMHOUSE_FIRST_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.SECOND_FLOOR,
                        image = MapFloorImage.GRAFTON_FARMHOUSE_SECOND_FLOOR
                    ),
                ),
                defaultFloor = 0
            ),
            // Brownstone Highschool
            MapResourceDto(
                mapId = R.string.map_id_brownstone,
                mapName = MapTitle.BROWNSTONE_HIGHSCHOOL,
                thumbnailImage = MapThumbnail.BROWNSTONE_HIGHSCHOOL,
                mapSize = MapSize.LARGE,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FIRST_FLOOR,
                        image = MapFloorImage.BROWNSTONE_HIGHSCHOOL_FIRST_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.SECOND_FLOOR,
                        image = MapFloorImage.BROWNSTONE_HIGHSCHOOL_SECOND_FLOOR
                    ),
                ),
                defaultFloor = 0
            ),
            // Edgefield
            MapResourceDto(
                mapId = R.string.map_id_edgefield,
                mapName = MapTitle.EDGEFIELD,
                thumbnailImage = MapThumbnail.EDGEFIELD,
                mapSize = MapSize.SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.BASEMENT,
                        image = MapFloorImage.EDGEFIELD_BASEMENT
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FIRST_FLOOR,
                        image = MapFloorImage.EDGEFIELD_FIRST_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.SECOND_FLOOR,
                        image = MapFloorImage.EDGEFIELD_SECOND_FLOOR
                    ),
                ),
                defaultFloor = 1
            ),
            // Tanglewood
            MapResourceDto(
                mapId = R.string.map_id_tanglewood,
                mapName = MapTitle.TANGLEWOOD,
                thumbnailImage = MapThumbnail.TANGLEWOOD,
                mapSize = MapSize.SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.BASEMENT,
                        image = MapFloorImage.TANGLEWOOD_BASEMENT
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FIRST_FLOOR,
                        image = MapFloorImage.TANGLEWOOD_FIRST_FLOOR
                    ),
                ),
                defaultFloor = 1
            ),
            // Willow
            MapResourceDto(
                mapId = R.string.map_id_willow,
                mapName = MapTitle.WILLOW,
                thumbnailImage = MapThumbnail.WILLOW,
                mapSize = MapSize.SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.BASEMENT,
                        image = MapFloorImage.WILLOW_BASEMENT
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FIRST_FLOOR,
                        image = MapFloorImage.WILLOW_FIRST_FLOOR
                    ),
                ),
                defaultFloor = 1
            ),
            // Ridgeview
            MapResourceDto(
                mapId = R.string.map_id_ridgeview,
                mapName = MapTitle.RIDGEVIEW,
                thumbnailImage = MapThumbnail.RIDGEVIEW,
                mapSize = MapSize.SMALL,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.BASEMENT,
                        image = MapFloorImage.RIDGEVIEW_BASEMENT
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FIRST_FLOOR,
                        image = MapFloorImage.RIDGEVIEW_FIRST_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.SECOND_FLOOR,
                        image = MapFloorImage.RIDGEVIEW_SECOND_FLOOR
                    ),
                ),
                defaultFloor = 1
            ),
            //Prison
            MapResourceDto(
                mapId = R.string.map_id_prison,
                mapName = MapTitle.PRISON,
                thumbnailImage = MapThumbnail.PRISON,
                mapSize = MapSize.MEDIUM,
                mapFloors = listOf(
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.FIRST_FLOOR,
                        image = MapFloorImage.PRISON_FIRST_FLOOR
                    ),
                    MapFloorResourceDto(
                        layerName = MapFloorTitle.SECOND_FLOOR,
                        image = MapFloorImage.PRISON_SECOND_FLOOR
                    )
                ),
                defaultFloor = 0
            )
        )

    override fun fetchMaps(): Result<List<SimpleWorldMapDto>> {

        val simpleMaps = mapsResourceDto.toWorldMapDto()

        return Result.success(simpleMaps)
    }

    private fun MapResourceDto.toWorldMapDto() = SimpleWorldMapDto(
        mapId = applicationContext.getString(mapId),
        mapName = mapName,
        thumbnailImage = thumbnailImage,
        mapSize = mapSize,
        mapFloors = mapFloors.toWorldMapFloorDto(),
        defaultFloor = defaultFloor
    )

    private fun MapFloorResourceDto.toWorldMapFloorDto() = SimpleWorldMapFloorDto(
        layerName = layerName,
        image = image
    )

    private fun List<MapResourceDto>.toWorldMapDto() = map { it.toWorldMapDto() }

    private fun List<MapFloorResourceDto>.toWorldMapFloorDto() = map { it.toWorldMapFloorDto() }

    private data class MapResourceDto(
        val mapId: Int,
        val mapName: MapTitle,
        val mapSize: MapSize,
        val thumbnailImage: MapThumbnail,
        val mapFloors: List<MapFloorResourceDto>,
        val defaultFloor: Int
    )

    private data class MapFloorResourceDto(
        val layerName: MapFloorTitle,
        val image: MapFloorImage
    )

}
