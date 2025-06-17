package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.dto.WorldMapModifierDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.mappers.MapModifierResources.SizePhaseModifier
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.source.MapModifiersDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toFractionResource

class MapModifiersLocalDataSource(
    private val applicationContext: Context
): MapModifiersDataSource {

    private val mapModifiers: List<MapModifierResourceDto> = listOf(

        MapModifierResourceDto(
            mapSize = MapSize.SMALL,
            setupModifier = SizePhaseModifier.SETUP_SMALL,
            normalModifier = SizePhaseModifier.ACTION_SMALL
        ),
        MapModifierResourceDto(
            mapSize = MapSize.MEDIUM,
            setupModifier = SizePhaseModifier.SETUP_MEDIUM,
            normalModifier = SizePhaseModifier.ACTION_MEDIUM
        ),
        MapModifierResourceDto(
            mapSize = MapSize.LARGE,
            setupModifier = SizePhaseModifier.SETUP_LARGE,
            normalModifier = SizePhaseModifier.ACTION_LARGE
        ),

    )

    override fun fetchSizeModifiers(): Result<List<WorldMapModifierDto>> {

        val modifiers = mapModifiers.toWorldMapModifierDtoList()

        return Result.success(modifiers)
    }

    private fun MapModifierResourceDto.toWorldMapModifierDto() = WorldMapModifierDto(
        name = mapSize,
        setupModifier = applicationContext.resources
            .getFraction(setupModifier.toFractionResource(), 1, 1),
        normalModifier = applicationContext.resources
            .getFraction(normalModifier.toFractionResource(), 1, 1),
    )

    private fun List<MapModifierResourceDto>.toWorldMapModifierDtoList() =
        map { it.toWorldMapModifierDto() }

    private data class MapModifierResourceDto(
        val mapSize: MapSize,
        val setupModifier: SizePhaseModifier,
        val normalModifier: SizePhaseModifier
    )

}