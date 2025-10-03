package com.tritiumgaming.data.map.modifiers.source.local

import android.content.Context
import com.tritiumgaming.data.map.modifiers.dto.WorldMapModifierDto
import com.tritiumgaming.data.map.modifiers.source.MapModifiersDataSource
import com.tritiumgaming.shared.operation.domain.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.shared.operation.domain.map.modifier.mappers.MapModifierResources.SizePhaseModifier

class MapModifiersLocalDataSource(
    private val applicationContext: Context
): MapModifiersDataSource {

    private val mapModifiers: List<MapModifierResourceDto> = listOf(

        MapModifierResourceDto(
            mapSize = MapSize.SMALL,
            setupModifier = .09f,
            normalModifier = .12f
        ),
        MapModifierResourceDto(
            mapSize = MapSize.MEDIUM,
            setupModifier = .05f,
            normalModifier = .08f
        ),
        MapModifierResourceDto(
            mapSize = MapSize.LARGE,
            setupModifier = .03f,
            normalModifier = .05f
        ),

    )

    override fun fetchSizeModifiers(): Result<List<WorldMapModifierDto>> {

        val modifiers = mapModifiers.toWorldMapModifierDtoList()

        return Result.success(modifiers)
    }

    private fun MapModifierResourceDto.toWorldMapModifierDto() = WorldMapModifierDto(
        name = mapSize,
        setupModifier = setupModifier,
        normalModifier = normalModifier,
    )

    private fun List<MapModifierResourceDto>.toWorldMapModifierDtoList() =
        map { it.toWorldMapModifierDto() }

    private data class MapModifierResourceDto(
        val mapSize: MapSize,
        val setupModifier: Float,
        val normalModifier: Float
    )

}