package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.source.local

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.dto.WorldMapModifierDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifiers.source.local.MapModifiersDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.WorldMapModifier

class MapModifiersLocalDataSource(
    private val applicationContext: Context
): MapModifiersDataSource {

    private val mapModifiers: List<MapModifierResourceDto> = listOf(

        MapModifierResourceDto(
            name = R.string.maps_size_title_small,
            setupModifier = R.string.maps_size_modifier_small_setup,
            normalModifier = R.string.maps_size_modifier_small_normal
        ),
        MapModifierResourceDto(
            name = R.string.maps_size_title_medium,
            setupModifier = R.string.maps_size_modifier_medium_setup,
            normalModifier = R.string.maps_size_modifier_medium_normal
        ),
        MapModifierResourceDto(
            name = R.string.maps_size_title_large,
            setupModifier = R.string.maps_size_modifier_large_setup,
            normalModifier = R.string.maps_size_modifier_large_normal
        ),

    )

    override fun fetchSizeModifiers(): Result<List<WorldMapModifierDto>> {

        val modifiers = mapModifiers.toWorldMapModifierDtoList()

        return Result.success(modifiers)
    }

    private fun MapModifierResourceDto.toWorldMapModifierDto() = WorldMapModifierDto(
        name = name,
        setupModifier = applicationContext.getString(setupModifier).toDouble().toFloat(),
        normalModifier = applicationContext.getString(normalModifier).toDouble().toFloat(),
    )

    private fun List<MapModifierResourceDto>.toWorldMapModifierDtoList() =
        map { it.toWorldMapModifierDto() }

    private data class MapModifierResourceDto(
        @StringRes val name: Int,
        @StringRes val setupModifier: Int,
        @StringRes val normalModifier: Int
    )

}