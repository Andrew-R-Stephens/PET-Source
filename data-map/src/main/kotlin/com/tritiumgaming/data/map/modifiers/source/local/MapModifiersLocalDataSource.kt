package com.tritiumgaming.data.map.modifiers.source.local

import com.tritiumgaming.data.map.modifiers.dto.WorldMapModifierDto
import com.tritiumgaming.data.map.modifiers.source.MapModifiersDataSource
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier

class MapModifiersLocalDataSource: MapModifiersDataSource {

    private val mapModifiers: List<MapModifierResourceDto> = listOf(

        MapModifierResourceDto(
            mapSize = MapSize.SMALL,
            setupModifier = MapSizePhaseModifier.SETUP_SMALL,
            actionModifier = MapSizePhaseModifier.ACTION_SMALL,
        ),
        MapModifierResourceDto(
            mapSize = MapSize.MEDIUM,
            setupModifier = MapSizePhaseModifier.SETUP_MEDIUM,
            actionModifier = MapSizePhaseModifier.ACTION_MEDIUM,
        ),
        MapModifierResourceDto(
            mapSize = MapSize.LARGE,
            setupModifier = MapSizePhaseModifier.SETUP_LARGE,
            actionModifier = MapSizePhaseModifier.ACTION_LARGE,
        ),

    )

    override fun fetchSizeModifiers(): Result<List<WorldMapModifierDto>> {
        val modifiers = mapModifiers.toWorldMapModifierDtoList()

        return Result.success(modifiers)
    }

    private fun MapModifierResourceDto.toWorldMapModifierDto() = WorldMapModifierDto(
        name = mapSize,
        setupModifier = setupModifier,
        actionModifier = actionModifier,
    )

    private fun List<MapModifierResourceDto>.toWorldMapModifierDtoList() =
        map { it.toWorldMapModifierDto() }

    private data class MapModifierResourceDto(
        val mapSize: MapSize,
        val setupModifier: MapSizePhaseModifier,
        val actionModifier: MapSizePhaseModifier
    )

}